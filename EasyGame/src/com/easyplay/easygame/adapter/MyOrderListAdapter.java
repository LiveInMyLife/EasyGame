package com.easyplay.easygame.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.model.MyOrder;
import com.easyplay.easygame.tools.AppContent;
import com.easyplay.easygame.tools.Tools;

public class MyOrderListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<MyOrder> suggestionList;
  private ViewHolder holder;

  class ViewHolder {
    TextView shopName;
    TextView orderDate;
    TextView orderPrice;
    TextView orderNum;
    TextView orderDescription;
    TextView orderState;
    TextView orderTotal;
    TextView complaint;
    TextView suggest;
  }

  public MyOrderListAdapter(Context context, List<MyOrder> data) {
    mContext = context;
    suggestionList = data;
  }

  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return suggestionList.size();
  }

  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return suggestionList.get(position);
  }

  @Override
  public long getItemId(int position) {
    // TODO Auto-generated method stub
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // TODO Auto-generated method stub
    holder = null;
    MyOrder order = suggestionList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_my_order_list, null);
      // holder.order_game_name = (TextView) convertView
      // .findViewById(R.id.sparing_list_item_gamename);
      holder.shopName = (TextView) convertView
          .findViewById(R.id.item_myorder_ordername);
      holder.orderDate = (TextView) convertView
          .findViewById(R.id.order_list_item_date);
      holder.orderPrice = (TextView) convertView
          .findViewById(R.id.item_myorder_price);
      holder.orderNum = (TextView) convertView
          .findViewById(R.id.order_list_item_time);
      holder.orderDescription = (TextView) convertView
          .findViewById(R.id.item_myorder_description);
      holder.orderState = (TextView) convertView
          .findViewById(R.id.item_myorder_orderstate);
      holder.orderTotal = (TextView) convertView
          .findViewById(R.id.item_myorder_total);
      holder.complaint = (TextView) convertView
          .findViewById(R.id.item_myorder_complaint);
      holder.suggest = (TextView) convertView
          .findViewById(R.id.item_myorder_suggestion);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (order != null) {
      holder.shopName.setText(Tools.checkString(order.getShopName()));
      holder.orderDate.setText(Tools.checkString(order.getCreatedAt()));
      if (order.getOrderPrice() > 0 && order.getOrderUnit() != null) {
        holder.orderPrice.setText(order.getOrderPrice() + "/"
            + order.getOrderUnit());
      }
      if (order.getOrderNum() > 0 && order.getOrderUnit() != null) {
        holder.orderNum.setText(order.getOrderNum() + order.getOrderUnit());
      }
      holder.orderDescription.setText(Tools.checkString(order
          .getOrderDescription()));
      holder.orderState.setText(Tools.checkString(AppContent
          .getOrderStateString(order.getOrderState())));
      if (order.getOrderTotal() >= 0) {
        holder.orderTotal.setText(order.getOrderTotal() + "");
      }
    }
    return convertView;
  }

}
