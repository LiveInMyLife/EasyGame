package com.easyplay.easygame.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.activity.GameSelectActivity;
import com.easyplay.easygame.activity.ShopDetailActivity;
import com.easyplay.easygame.adapter.ShopListAdapter;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.view.SlideShowView;

/**
 * 首页-陪练页
 * 
 * @author chuwe1
 * 
 */
public class SparringFragment extends Fragment implements OnItemClickListener {
  private static final String TAG = "SparringFragment";

  private View rootView;
  private Context mContext;
  private ListView shopListView;
  private LinearLayout sparring_header;
  private final List<ShopInfo> shopListInfo = new ArrayList<ShopInfo>();
  private ShopListAdapter shopAdapter;
  private RelativeLayout gameSelect;

  private SlideShowView slideShowView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_sparring, null);
    mContext = this.getActivity().getApplicationContext();
    bindView();
    bindListener();
    init();
    return rootView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  public void bindView() {

    shopListView = (ListView) rootView.findViewById(R.id.list_car_brand_select);
    sparring_header = (LinearLayout) View.inflate(mContext,
        R.layout.item_sparring_list_header, null);
    slideShowView = (SlideShowView) sparring_header
        .findViewById(R.id.slideshowView);
    shopListView.addHeaderView(sparring_header);
    gameSelect = (RelativeLayout) shopListView.findViewById(R.id.select_game);
  }

  public void bindListener() {
    gameSelect.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(mContext, GameSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("from_where", "SparringFragment");
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      }
    });
  }

  public void init() {
    shopAdapter = new ShopListAdapter(mContext, shopListInfo);
    shopListView.setAdapter(shopAdapter);
    shopListView.setVisibility(View.VISIBLE);
    shopListView.setOnItemClickListener(this);
    queryShopInfo();
  }

  public void queryShopInfo() {
    BmobQuery<ShopInfo> query = new BmobQuery<ShopInfo>();
    query.findObjects(mContext, new FindListener<ShopInfo>() {
      @Override
      public void onSuccess(List<ShopInfo> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        for (ShopInfo shopInfo : object) {
          shopListInfo.add(shopInfo);
        }
        shopAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询失败：" + code + msg);
      }
    });
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
      long id) {
    // TODO Auto-generated method stub
    if (position <= shopListInfo.size() + 1) {
      ShopInfo shop = shopListInfo.get(position - 1);
      Intent intent = new Intent(mContext, ShopDetailActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      Bundle bundle = new Bundle();
      bundle.putSerializable("shop_info", shop);
      intent.putExtras(bundle);
      mContext.startActivity(intent);
    }
  }
}
