package com.easyplay.easygame.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.model.ShopSuggestion;
import com.easyplay.easygame.tools.Tools;

public class ShopSuggestionListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<ShopSuggestion> suggestionList;
  private ViewHolder holder;

  class ViewHolder {
    TextView userName;
    TextView suggestionContent;
    TextView suggestionTime;
    TextView suggestionScore;
  }

  public ShopSuggestionListAdapter(Context context, List<ShopSuggestion> data) {
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
    ShopSuggestion suggestion = suggestionList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_shop_suggestion, null);
      // holder.order_game_name = (TextView) convertView
      // .findViewById(R.id.sparing_list_item_gamename);
      holder.userName = (TextView) convertView
          .findViewById(R.id.shop_suggestion_username);
      holder.suggestionContent = (TextView) convertView
          .findViewById(R.id.shop_suggestion_content);
      holder.suggestionTime = (TextView) convertView
          .findViewById(R.id.shop_suggestion_time);
      holder.suggestionScore = (TextView) convertView
          .findViewById(R.id.shop_suggestion_score_tv);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (suggestion != null) {
      holder.userName.setText(Tools.checkString(suggestion.getUserName()));
      holder.suggestionContent.setText(Tools.checkString(suggestion
          .getSuggestionContent()));
      holder.suggestionTime
          .setText(Tools.checkString(suggestion.getCreatedAt()));
      holder.suggestionScore.setText(suggestion.getSuggestionScore());

    }
    return convertView;
  }

}
