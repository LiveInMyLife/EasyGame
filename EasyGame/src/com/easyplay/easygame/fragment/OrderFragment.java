package com.easyplay.easygame.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyOrderListAdapter;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.PayOrder;
import com.easyplay.easygame.tools.AppLog;

public class OrderFragment extends Fragment implements OnItemClickListener {
  private static final String TAG = "OrderFragment";

  private View rootView;
  private Context mContext;
  private ListView orderListView;
  private MyOrderListAdapter orderAdapter;
  private final List<PayOrder> orderListInfo = new ArrayList<PayOrder>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_myorder, null);
    mContext = this.getActivity().getApplicationContext();
    bindView();
    bindListener();
    init();
    return rootView;
  }

  public void bindView() {

    orderListView = (ListView) rootView.findViewById(R.id.list_my_order);
  }

  public void bindListener() {
  }

  public void init() {
    orderAdapter = new MyOrderListAdapter(mContext, orderListInfo);
    orderListView.setAdapter(orderAdapter);
    orderListView.setVisibility(View.VISIBLE);
    orderListView.setOnItemClickListener(this);
    // queryOrderInfo();
  }

  public void queryOrderInfo() {
    BmobQuery<PayOrder> query = new BmobQuery<PayOrder>();
    query.addWhereEqualTo("customer",
        BaseApplication.userManager.getCurrentUser());
    query.findObjects(mContext, new FindListener<PayOrder>() {
      @Override
      public void onSuccess(List<PayOrder> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        orderListInfo.clear();
        for (PayOrder orderInfo : object) {
          orderListInfo.add(orderInfo);
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
  public void onItemClick(AdapterView<?> parent, View view, int position,
      long id) {
    // TODO Auto-generated method stub
    if (position <= orderListInfo.size() + 1) {
      // ShopInfo shop = orderListInfo.get(position - 1);
      // Intent intent = new Intent(mContext, ShopDetailActivity.class);
      // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      // Bundle bundle = new Bundle();
      // bundle.putSerializable("shop_info", shop);
      // intent.putExtras(bundle);
      // mContext.startActivity(intent);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    queryOrderInfo();
  }
}
