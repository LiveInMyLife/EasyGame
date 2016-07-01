package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.TopicListAdapter;
import com.easyplay.easygame.model.Activity;
import com.easyplay.easygame.model.GameInfo;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.tools.AppLog;

public class ShopSelectActivity extends BaseActivity implements
    OnClickListener, OnItemClickListener {
  private static final String TAG = "GameSelectActivity";

  private TopicListAdapter shopListAdapter;
  private ListView shopList;
  private List<Activity> shopListInfo;
  private ShopInfo shopSelected;
  private GameInfo gameInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_select);
    initActionBar(this.getResources().getString(R.string.game_select));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    shopList = (ListView) this.findViewById(R.id.shop_select_list);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    gameInfo = (GameInfo) this.getIntent().getSerializableExtra("game_info");
    shopListInfo = new ArrayList<Activity>();
    // shopListAdapter = new TopicListAdapter(this, shopListInfo);
    shopList.setAdapter(shopListAdapter);
    queryShopInfo();
    shopList.setOnItemClickListener(this);
  }

  public void queryShopInfo() {
    BmobQuery<Activity> query = new BmobQuery<Activity>();
    query.addWhereEqualTo("shopGame", gameInfo.getGameName());
    query.findObjects(ShopSelectActivity.this, new FindListener<Activity>() {
      @Override
      public void onSuccess(List<Activity> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        for (Activity shopInfo : object) {
          shopListInfo.add(shopInfo);
        }
        shopListAdapter.notifyDataSetChanged();
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
    case R.id.tv_shop_order:

      break;
    }

  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
      long id) {
    // TODO Auto-generated method stub
    if (position <= shopListInfo.size()) {
      Activity shop = shopListInfo.get(position);
      Intent intent = new Intent(ShopSelectActivity.this,
          ShopDetailActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      Bundle bundle = new Bundle();
      bundle.putSerializable("shop_info", shop);
      intent.putExtras(bundle);
      ShopSelectActivity.this.startActivity(intent);
    }
  }
}
