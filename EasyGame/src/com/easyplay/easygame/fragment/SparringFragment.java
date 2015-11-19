package com.easyplay.easygame.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyShopListAdapter;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.view.SlideShowView;

/**
 * 首页-陪练页
 * 
 * @author chuwe1
 * 
 */
public class SparringFragment extends Fragment {
  private static final String TAG = "SparringFragment";

  private View rootView;
  private Context mContext;
  private ListView shopListView;
  private LinearLayout sparring_header;
  private final List<ShopInfo> shopListInfo = new ArrayList<ShopInfo>();
  private MyShopListAdapter shopAdapter;

  private SlideShowView slideShowView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.sparring_fragment, null);
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
        R.layout.sparring_list_header, null);
    slideShowView = (SlideShowView) sparring_header
        .findViewById(R.id.slideshowView);
    shopListView.addHeaderView(sparring_header);
  }

  public void bindListener() {
  }

  public void init() {
    shopAdapter = new MyShopListAdapter(mContext, shopListInfo);
    shopListView.setAdapter(shopAdapter);
    shopListView.setVisibility(View.VISIBLE);
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
}
