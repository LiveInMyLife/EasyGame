package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.myview.MyAdapter;
import com.easyplay.easygame.tools.ImageUtils;
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShopDetailActivity extends BaseActivity implements OnClickListener {
  private TextView shopName;
  private TextView shopGame;
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

    // shopTabLayout = (LinearLayout)
    // this.findViewById(R.id.layout_shoptabview);
    imageView = (ImageView) findViewById(R.id.shop_cursor);
    textView1 = (TextView) findViewById(R.id.tv_shop_order);
    textView2 = (TextView) findViewById(R.id.tv_shop_suggesstion);
    textView3 = (TextView) findViewById(R.id.tv_shop_info);
    textView1.setTextColor(this.getResources().getColor(
        R.color.text_color_sky_blue));

    lists.add(getLayoutInflater().inflate(R.layout.layout_shoptab1, null));
    lists.add(getLayoutInflater().inflate(R.layout.layout_shoptab2, null));
    lists.add(getLayoutInflater().inflate(R.layout.layout_shoptab3, null));
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    textView1.setOnClickListener(this);
    textView2.setOnClickListener(this);
    textView3.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    shopInfo = (ShopInfo) this.getIntent().getSerializableExtra("shop_info");
    if (shopInfo != null) {
      shopName.setText(Tools.checkString(shopInfo.getShopName()));
      shopGame.setText(Tools.checkString(shopInfo.getShopGame()));
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
}
