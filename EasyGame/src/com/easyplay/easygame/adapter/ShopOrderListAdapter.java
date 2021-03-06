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
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShopOrderListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<ShopOrder> orderList;
  private ViewHolder holder;

  class ViewHolder {
    ImageView order_logo;
    TextView order_name;
    TextView order_sell;
    TextView order_description;
    TextView order_price;
    TextView order_time;
  }

  public ShopOrderListAdapter(Context context, List<ShopOrder> data) {
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
    ShopOrder order = orderList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_shop_order_list, null);
      // holder.order_game_name = (TextView) convertView
      // .findViewById(R.id.sparing_list_item_gamename);
      holder.order_name = (TextView) convertView
          .findViewById(R.id.order_list_item_ordername);
      holder.order_sell = (TextView) convertView
          .findViewById(R.id.order_list_item_sellnum);
      holder.order_description = (TextView) convertView
          .findViewById(R.id.order_list_item_description);
      holder.order_logo = (ImageView) convertView
          .findViewById(R.id.order_list_item_img);
      holder.order_price = (TextView) convertView
          .findViewById(R.id.order_list_item_price);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (order != null) {
      if (order.getOrderName() != null) {
        holder.order_name.setVisibility(View.VISIBLE);
        holder.order_name.setText(Tools.checkString(order.getOrderName()));
      } else {
        holder.order_name.setVisibility(View.GONE);
      }
      if (order.getOrderDescription() != null) {
        holder.order_description.setVisibility(View.VISIBLE);
        holder.order_description.setText(Tools.checkString(order
            .getOrderDescription()));
      } else {
        holder.order_description.setVisibility(View.GONE);
      }
      if (order.getOrderSell() != null) {
        holder.order_sell.setVisibility(View.VISIBLE);
        holder.order_sell.setText("月售"
            + Tools.checkString(order.getOrderSell().toString()));
      } else {
        holder.order_sell.setVisibility(View.GONE);
      }
      if (order.getOrderPrice() != null) {
        holder.order_price.setVisibility(View.VISIBLE);
        holder.order_price.setText(Tools.checkString(order.getOrderPrice()
            .toString()) + "/" + Tools.checkString(order.getOrderTime()));
      } else {
        holder.order_price.setVisibility(View.GONE);
      }
      if (order.getOrderLogo() != null) {
        holder.order_logo.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(order.getOrderLogo(),
            holder.order_logo);
      } else {
        holder.order_logo.setVisibility(View.GONE);
      }
    }
    return convertView;
  }

}
