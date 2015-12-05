package com.easyplay.easygame.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyShopOrderListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<ShopOrder> orderList;
  private ViewHolder holder;

  class ViewHolder {
    ImageView order_logo;
    TextView order_name;
    TextView order_description;
    TextView order_price;
    TextView order_service_time;
  }

  public MyShopOrderListAdapter(Context context, List<ShopOrder> data) {
    mContext = context;
    orderList = data;
  }

  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return orderList.size();
  }

  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return orderList.get(position);
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
    AppLog.d("orderAdapter", "position=" + position);
    ShopOrder order = orderList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_my_shop_order_list, null);
      holder.order_name = (TextView) convertView
          .findViewById(R.id.my_shop_order_ordername);
      holder.order_description = (TextView) convertView
          .findViewById(R.id.my_shop_order_description);
      holder.order_logo = (ImageView) convertView
          .findViewById(R.id.my_shop_order_img);
      holder.order_price = (TextView) convertView
          .findViewById(R.id.my_shop_order_price);
      holder.order_service_time = (TextView) convertView
          .findViewById(R.id.my_shop_order_servicetime);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (order != null) {
      holder.order_name.setText(Tools.checkString(order.getOrderName()));
      holder.order_description.setText(Tools.checkString(order
          .getOrderDescription()));

      holder.order_price.setText(Tools.checkString(order.getOrderPrice()
          .toString()) + "/" + Tools.checkString(order.getOrderTime()));
      if (order.getStartTime() == null || order.getEndTime() == null) {
        holder.order_service_time.setText(order.getStartTime() + ":00-"
            + order.getEndTime() + ":00");
      } else {
        holder.order_service_time.setText("");
      }
      if (order.getOrderLogo() != null) {
        ImageLoader.getInstance().displayImage(order.getOrderLogo(),
            holder.order_logo);
      }
    }
    return convertView;
  }

}
