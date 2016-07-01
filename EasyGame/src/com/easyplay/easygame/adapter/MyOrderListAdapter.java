package com.easyplay.easygame.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.activity.ChatActivity;
import com.easyplay.easygame.activity.SuggestionActivity;
import com.easyplay.easygame.model.PayOrder;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.tools.AppContent;
import com.easyplay.easygame.tools.Tools;

public class MyOrderListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<PayOrder> suggestionList;
  private ViewHolder holder;

  class ViewHolder {
    TextView shopName;
    TextView orderDate;
    TextView orderDescription;
    TextView orderState;
    TextView orderTotal;
    TextView complaint;
    TextView suggest;
    TextView contact;
    // TextView completetConfirm;
  }

  public MyOrderListAdapter(Context context, List<PayOrder> data) {
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
    final PayOrder order = suggestionList.get(position);
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
      holder.contact = (TextView) convertView
          .findViewById(R.id.item_myorder_contact_server);
      // holder.completetConfirm = (TextView) convertView
      // .findViewById(R.id.item_myorder_complete_confirm);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (order != null) {
      holder.shopName.setText(Tools.checkString(order.getServerShop()
          .getShopName()));
      holder.orderDate.setText(Tools.checkString(order.getCreatedAt()));
      holder.orderDescription
          .setText(Tools.checkString(order.getDescription()));
      holder.orderState.setText(Tools.checkString(AppContent
          .getOrderStateString(order.getState())));
      if (order.getState() == AppContent.ORDER_STATE_FINISH) {
        holder.complaint.setVisibility(View.VISIBLE);
        holder.suggest.setVisibility(View.VISIBLE);
        holder.contact.setVisibility(View.GONE);
        // holder.completetConfirm.setVisibility(View.GONE);
        holder.suggest.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub
            ShopInfo shop = order.getServerShop();
            Intent intent = new Intent(mContext, SuggestionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable("tag_shop", shop);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
          }

        });
      } else {
        holder.complaint.setVisibility(View.GONE);
        holder.suggest.setVisibility(View.GONE);
        holder.contact.setVisibility(View.VISIBLE);
        // holder.completetConfirm.setVisibility(View.VISIBLE);
        // holder.completetConfirm.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // ShopInfo shop = order.getServerShop();
        // Intent intent = new Intent(mContext, SuggestionActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Bundle bundle = new Bundle();
        // bundle.putSerializable("tag_shop", shop);
        // intent.putExtras(bundle);
        // mContext.startActivity(intent);
        // }
        //
        // });
        holder.contact.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("userId", order.getServer().getObjectId());
            mContext.startActivity(intent);
          }
        });
      }
      if (order.getTotal() >= 0) {
        holder.orderTotal.setText(order.getTotal() + "");
      }
    }
    return convertView;
  }

}
