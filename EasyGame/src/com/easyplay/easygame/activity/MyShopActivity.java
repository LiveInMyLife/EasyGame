package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyShopOrderListAdapter;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyShopActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "myShopActivity";
  private final int SHOP_SETTING = 10;
  private final int ORDER_ADD = 20;

  private ImageView addOrder;

  private LinearLayout my_shop_header, my_shop_footer;
  private MyShopOrderListAdapter orderAdapter;
  private ListView orderList;
  private final List<ShopOrder> orderListInfo = new ArrayList<ShopOrder>();

  private TextView shopName, gameName, gameServer, shopNotice;
  private ImageView shopLogo;

  private ShopInfo mShopInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_shop);
    initActionBar(this.getResources().getString(R.string.shop_detail));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    my_shop_header = (LinearLayout) View.inflate(this,
        R.layout.item_my_shop_list_header, null);
    my_shop_footer = (LinearLayout) View.inflate(this,
        R.layout.item_my_shop_list_footer, null);
    addOrder = (ImageView) findViewById(R.id.my_shop_order_add_btn);

    orderList = (ListView) findViewById(R.id.my_shop_order_list);
    orderList.addHeaderView(my_shop_header);
    // orderList.addFooterView(my_shop_footer);
    shopName = (TextView) my_shop_header.findViewById(R.id.my_shop_shopname);
    gameName = (TextView) my_shop_header.findViewById(R.id.my_shop_gamename);
    gameServer = (TextView) my_shop_header
        .findViewById(R.id.my_shop_gameserver);
    shopNotice = (TextView) my_shop_header.findViewById(R.id.my_shop_notice_tv);
    shopLogo = (ImageView) my_shop_header.findViewById(R.id.my_shop_img);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    addOrder.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        toShopSetting();
      }
    });
    my_shop_header.setOnClickListener(this);
    my_shop_footer.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub

    orderAdapter = new MyShopOrderListAdapter(this, orderListInfo);
    orderList.setAdapter(orderAdapter);

    queryShopOrder();
  }

  public void queryShopOrder() {
    BmobQuery<ShopOrder> query = new BmobQuery<ShopOrder>();
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

  public void queryShopInfo() {
    BmobQuery<ShopInfo> query = new BmobQuery<ShopInfo>();
    query
        .addWhereEqualTo("owner", BaseApplication.userManager.getCurrentUser());
    query.findObjects(this, new FindListener<ShopInfo>() {
      @Override
      public void onSuccess(List<ShopInfo> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        if (object.size() > 0) {
          for (ShopInfo shopInfo : object) {
            mShopInfo = shopInfo;
            updateData(mShopInfo);
          }
        } else {
          toShopSetting();
        }
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询失败：" + code + msg);
      }
    });
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.my_shop_order_add_btn:
      toAddOrderActicvity();
      break;
    }

  }

  private void toAddOrderActicvity() {
    Intent intent = new Intent(this, MyShopAddOrderActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Bundle bundle = new Bundle();
    bundle.putSerializable("shop_info", mShopInfo);
    intent.putExtras(bundle);
    this.startActivityForResult(intent, ORDER_ADD);
  }

  private void toShopSetting() {
    Intent intent = new Intent(this, ShopSettingActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Bundle bundle = new Bundle();
    bundle.putSerializable("shop_info", mShopInfo);
    intent.putExtras(bundle);
    this.startActivityForResult(intent, SHOP_SETTING);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      switch (requestCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
      case SHOP_SETTING:
        ShopInfo result = (ShopInfo) data.getSerializableExtra("shop_info");
        if (result != null) {
          mShopInfo = result;
          updateData(result);
          if (orderListInfo.isEmpty()) {
            toAddOrderActicvity();
          }
        } else {

        }
        break;
      case ORDER_ADD:
        ShopOrder orderResult = (ShopOrder) data
            .getSerializableExtra("shop_order");
        if (orderResult != null) {
          orderListInfo.add(orderResult);
          orderAdapter.notifyDataSetChanged();
        }
        break;
      default:
        break;
      }
    }
  }

  private void updateData(ShopInfo data) {
    shopName.setText(Tools.checkString(data.getShopName()));
    gameName.setText(Tools.checkString(data.getShopGame()));
    gameServer.setText(Tools.checkString(data.getGameServer()));
    shopNotice.setText(Tools.checkString(data.getShopDescription()));
    if (data.getShopLogo() != null) {
      ImageLoader.getInstance().displayImage(data.getShopLogo(), shopLogo);
    }
  }
}
