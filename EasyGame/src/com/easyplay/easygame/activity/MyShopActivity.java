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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyShopOrderListAdapter;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.tools.AppLog;

public class MyShopActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "myShopActivity";

  private ImageView addOrder;

  private LinearLayout my_shop_header, my_shop_footer;
  private MyShopOrderListAdapter orderAdapter;
  private ListView orderList;
  private final List<ShopOrder> orderListInfo = new ArrayList<ShopOrder>();

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

  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    addOrder.setOnClickListener(this);
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
    this.startActivity(intent);
  }
}
