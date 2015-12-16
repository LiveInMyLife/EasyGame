package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyAdapter;
import com.easyplay.easygame.adapter.ShopOrderListAdapter;
import com.easyplay.easygame.adapter.ShopSuggestionListAdapter;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.model.ShopSuggestion;
import com.easyplay.easygame.tools.ActivityUtils;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.tools.ImageUtils;
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShopDetailActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "ShopDetailActivity";
  private TextView shopName;
  private TextView shopGame;
  private TextView gameServer;
  private TextView shopDescription;
  private ImageView shopLogo;
  private ShopInfo shopInfo;

  // private LinearLayout shopTabLayout;
  private ViewPager viewPager;
  private ImageView imageView;
  private final List<View> lists = new ArrayList<View>();
  private MyAdapter myAdapter;
  private Bitmap cursor;
  private int offSet;
  private int currentItem;
  private final Matrix matrix = new Matrix();
  private int bmWidth;
  private Animation animation;
  private TextView textView1;
  private TextView textView2;
  private TextView textView3;
  private int textWidth;

  private LinearLayout layout_ordertab, layout_suggestiontab, layout_shoptab;
  private ShopOrderListAdapter orderAdapter;
  private ListView orderList;
  private final List<ShopOrder> orderListInfo = new ArrayList<ShopOrder>();

  private ShopSuggestionListAdapter suggestionAdapter;
  private ListView suggestionList;
  private final List<ShopSuggestion> suggestionListInfo = new ArrayList<ShopSuggestion>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_detail);
    initActionBar(this.getResources().getString(R.string.shop_detail));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    shopName = (TextView) findViewById(R.id.shop_detail_shopname);
    shopGame = (TextView) findViewById(R.id.shop_detail_gamename);
    shopDescription = (TextView) findViewById(R.id.shop_detail_notice);
    shopLogo = (ImageView) findViewById(R.id.shop_detail_img);
    gameServer = (TextView) findViewById(R.id.shop_detail_gameserver);

    // shopTabLayout = (LinearLayout)
    // this.findViewById(R.id.layout_shoptabview);
    imageView = (ImageView) findViewById(R.id.shop_cursor);
    textView1 = (TextView) findViewById(R.id.tv_shop_order);
    textView2 = (TextView) findViewById(R.id.tv_shop_suggesstion);
    textView3 = (TextView) findViewById(R.id.tv_shop_info);
    textView1.setTextColor(this.getResources().getColor(
        R.color.text_color_sky_blue));

    layout_ordertab = (LinearLayout) getLayoutInflater().inflate(
        R.layout.layout_shoptab1, null);
    layout_suggestiontab = (LinearLayout) getLayoutInflater().inflate(
        R.layout.layout_shoptab2, null);
    layout_shoptab = (LinearLayout) getLayoutInflater().inflate(
        R.layout.layout_shoptab3, null);

    lists.add(layout_ordertab);
    lists.add(layout_suggestiontab);
    lists.add(layout_shoptab);
    orderList = (ListView) layout_ordertab.findViewById(R.id.shop_order_list);
    suggestionList = (ListView) layout_suggestiontab
        .findViewById(R.id.shop_suggestion_list);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    textView1.setOnClickListener(this);
    textView2.setOnClickListener(this);
    textView3.setOnClickListener(this);
    orderList.setOnItemClickListener(new OrderClickListener());
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    shopInfo = (ShopInfo) this.getIntent().getSerializableExtra("shop_info");
    if (shopInfo != null) {
      shopName.setText(Tools.checkString(shopInfo.getShopName()));
      shopGame.setText(Tools.checkString(shopInfo.getShopGame()));
      gameServer.setText(Tools.checkString(shopInfo.getGameServer()));
      shopDescription.setText(Tools.checkString(shopInfo.getShopDescription()));
      if (shopInfo.getShopLogo() != null) {
        ImageLoader.getInstance()
            .displayImage(shopInfo.getShopLogo(), shopLogo);
      }
    }

    initeCursor();
    myAdapter = new MyAdapter(lists);
    viewPager = (ViewPager) findViewById(R.id.viewPager);
    viewPager.setAdapter(myAdapter);
    viewPager.setOnPageChangeListener(new OnPageChangeListener());

    orderAdapter = new ShopOrderListAdapter(this, orderListInfo);
    orderList.setAdapter(orderAdapter);

    suggestionAdapter = new ShopSuggestionListAdapter(this, suggestionListInfo);
    suggestionList.setAdapter(suggestionAdapter);
    queryShopOrder();
  }

  public void queryShopOrder() {
    BmobQuery<ShopOrder> query = new BmobQuery<ShopOrder>();
    query.addWhereEqualTo("orderShop", shopInfo);
    query.findObjects(this, new FindListener<ShopOrder>() {
      @Override
      public void onSuccess(List<ShopOrder> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        for (ShopOrder shopOrder : object) {
          orderListInfo.add(shopOrder);
        }
        orderAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询失败：" + code + msg);
      }
    });
  }

  public void queryShopSuggestion() {
    BmobQuery<ShopSuggestion> query = new BmobQuery<ShopSuggestion>();
    query.findObjects(this, new FindListener<ShopSuggestion>() {
      @Override
      public void onSuccess(List<ShopSuggestion> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        suggestionListInfo.clear();
        for (ShopSuggestion shopSuggestion : object) {
          suggestionListInfo.add(shopSuggestion);
        }
        suggestionAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询失败：" + code + msg);
      }
    });
  }

  private void initeCursor() {
    WindowManager wm = (WindowManager) this
        .getSystemService(Context.WINDOW_SERVICE);

    int width = wm.getDefaultDisplay().getWidth();
    textWidth = width / 3;
    cursor = BitmapFactory.decodeResource(getResources(),
        R.drawable.document_btn_blue_bg);
    Log.v("main",
        "wight=" + cursor.getWidth() + ";height=" + cursor.getHeight());
    cursor = ImageUtils.resizeImage(cursor, textWidth, 5);
    bmWidth = cursor.getWidth();
    imageView.setImageBitmap(cursor);

    DisplayMetrics dm;
    dm = getResources().getDisplayMetrics();

    offSet = (dm.widthPixels - 3 * bmWidth) / 6;
    matrix.setTranslate(offSet, 0);
    // matrix.setScale(5, textWidth);
    imageView.setImageMatrix(matrix);
    currentItem = 0;
  }

  private class OnPageChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageSelected(int arg0) {
      // TODO Auto-generated method stub
      switch (arg0) {
      case 0:

        textView1.setTextColor(getResources().getColor(
            R.color.text_color_sky_blue));
        textView2.setTextColor(getResources()
            .getColor(R.color.text_color_black));
        textView3.setTextColor(getResources()
            .getColor(R.color.text_color_black));
        if (currentItem == 1) {
          animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
        } else if (currentItem == 2) {
          animation = new TranslateAnimation(offSet * 4 + 2 * bmWidth, 0, 0, 0);
        }
        break;
      case 1:
        textView1.setTextColor(getResources()
            .getColor(R.color.text_color_black));
        textView2.setTextColor(getResources().getColor(
            R.color.text_color_sky_blue));
        textView3.setTextColor(getResources()
            .getColor(R.color.text_color_black));
        if (currentItem == 0) {
          animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
        } else if (currentItem == 2) {
          // TODO
          animation = new TranslateAnimation(2 * offSet + 2 * bmWidth, offSet
              * 2 + bmWidth, 0, 0);
        }
        queryShopSuggestion();
        break;
      case 2:
        textView1.setTextColor(getResources()
            .getColor(R.color.text_color_black));
        textView2.setTextColor(getResources()
            .getColor(R.color.text_color_black));
        textView3.setTextColor(getResources().getColor(
            R.color.text_color_sky_blue));
        if (currentItem == 0) {
          animation = new TranslateAnimation(0, 4 * offSet + 2 * bmWidth, 0, 0);
        } else if (currentItem == 1) {
          animation = new TranslateAnimation(offSet * 2 + bmWidth, 4 * offSet
              + 2 * bmWidth, 0, 0);
        }
      }
      currentItem = arg0;

      animation.setDuration(500);
      animation.setFillAfter(true);
      imageView.startAnimation(animation);

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
      // TODO Auto-generated method stub

    }
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.tv_shop_order:
      if (viewPager.getCurrentItem() != 0) {
        viewPager.setCurrentItem(0);
      }
      break;
    case R.id.tv_shop_suggesstion:
      if (viewPager.getCurrentItem() != 1) {
        viewPager.setCurrentItem(1);
      }
      break;
    case R.id.tv_shop_info:
      if (viewPager.getCurrentItem() != 2) {
        viewPager.setCurrentItem(2);
      }
      break;
    }

  }

  private class OrderClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {
      // TODO Auto-generated method stub
      if (BaseApplication.userManager.getCurrentUser() == null) {
        ActivityUtils.toLoginActivity(ShopDetailActivity.this);
      } else {
        toOrderConfirmActivity(orderListInfo.get(position));
      }
    }
  }

  private void toOrderConfirmActivity(ShopOrder orderInfo) {
    Intent intent = new Intent(this, ShopOrderConfirmActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Bundle bundle = new Bundle();
    bundle.putSerializable("order_info", orderInfo);
    intent.putExtras(bundle);
    this.startActivity(intent);
  }
}
