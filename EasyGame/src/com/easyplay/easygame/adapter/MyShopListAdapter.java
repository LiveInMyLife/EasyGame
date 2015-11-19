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
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyShopListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<ShopInfo> shopList;
  private ViewHolder holder;

  class ViewHolder {
    ImageView shop_logo;
    TextView shop_name;
    TextView game_name;
    TextView shop_score;
    TextView shop_sell;
    TextView shop_description;
  }

  public MyShopListAdapter(Context context, List<ShopInfo> data) {
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
          R.layout.item_sparing_list, null);
      holder.game_name = (TextView) convertView
          .findViewById(R.id.sparing_list_item_gamename);
      holder.shop_name = (TextView) convertView
          .findViewById(R.id.sparing_list_item_shopname);
      holder.shop_sell = (TextView) convertView
          .findViewById(R.id.sparing_list_item_sellnum);
      holder.shop_description = (TextView) convertView
          .findViewById(R.id.item_shop_description);
      holder.shop_logo = (ImageView) convertView
          .findViewById(R.id.sparing_list_item_img);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (shop != null) {
      holder.shop_name.setText(Tools.checkString(shop.getShopName()));
      holder.game_name.setText(Tools.checkString(shop.getShopGame()));
      holder.shop_sell.setText(Tools.checkString(shop.getShopSell()));
      holder.shop_description.setText(Tools.checkString(shop
          .getShopDescription()));
      if (shop.getShopLogo() != null) {
        ImageLoader.getInstance().displayImage(shop.getShopLogo(),
            holder.shop_logo);
      }
    }
    return convertView;
  }

}
