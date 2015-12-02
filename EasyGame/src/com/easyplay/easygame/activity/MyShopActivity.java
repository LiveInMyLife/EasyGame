package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
import com.easyplay.easygame.adapter.MyShopSuggestionListAdapter;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.model.ShopSuggestion;
import com.easyplay.easygame.tools.AppLog;

public class MyShopActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "ShopDetailActivity";
  private TextView myShopName;
  private TextView myShopGame;
  private TextView mtShopDescription;
  private ImageView myShopLogo;
  private ShopInfo myShopInfo;

  private ImageView addOrder;

  private LinearLayout my_shop_header, my_shop_footer;
  private MyShopOrderListAdapter orderAdapter;
  private ListView orderList;
  private final List<ShopOrder> orderListInfo = new ArrayList<ShopOrder>();

  private MyShopSuggestionListAdapter suggestionAdapter;
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
    my_shop_header = (LinearLayout) View.inflate(this,
        R.layout.item_my_shop_list_header, null);
    my_shop_footer = (LinearLayout) View.inflate(this,
        R.layout.item_my_shop_list_footer, null);
    addOrder = (ImageView) my_shop_footer
        .findViewById(R.id.my_shop_order_add_btn);

    myShopName = (TextView) my_shop_header.findViewById(R.id.my_shop_shopname);
    myShopGame = (TextView) my_shop_header.findViewById(R.id.my_shop_gamename);
    mtShopDescription = (TextView) my_shop_header
        .findViewById(R.id.my_shop_notice_tv);
    myShopLogo = (ImageView) my_shop_header.findViewById(R.id.my_shop_img);

    orderList = (ListView) findViewById(R.id.my_shop_order_list);
    orderList.addHeaderView(my_shop_header);
    orderList.addFooterView(my_shop_footer);

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

    suggestionAdapter = new MyShopSuggestionListAdapter(this,
        suggestionListInfo);
    suggestionList.setAdapter(suggestionAdapter);
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

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.my_shop_order_add_btn:

      break;
    }

  }
}
