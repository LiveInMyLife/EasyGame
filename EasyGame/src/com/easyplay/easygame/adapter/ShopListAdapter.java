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
import com.easyplay.easygame.model.ShopInfo;

public class ShopListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<ShopInfo> shopList;
  private ViewHolder holder;

  class ViewHolder {
    ImageView activity_logo;
    TextView activity_description;
    TextView activity_positive;
    TextView activity_negative;
    TextView activity_time_limit;
    TextView activity_state;
  }

  public ShopListAdapter(Context context, List<ShopInfo> data) {
    mContext = context;
    shopList = data;
  }

  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return shopList.size();
  }

  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return shopList.get(position);
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
    ShopInfo shop = shopList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_activity_list, null);
      holder.activity_logo = (ImageView) convertView
          .findViewById(R.id.item_activity_logo);
      holder.activity_description = (TextView) convertView
          .findViewById(R.id.item_activity_title);
      holder.activity_time_limit = (TextView) convertView
          .findViewById(R.id.item_activity_end_time);
      holder.activity_state = (TextView) convertView
          .findViewById(R.id.item_activity_state);
      holder.activity_positive = (TextView) convertView
          .findViewById(R.id.item_activity_positive);
      holder.activity_negative = (TextView) convertView
          .findViewById(R.id.item_activity_negative);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (shop != null) {

    }
    return convertView;
  }
}
