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
import com.easyplay.easygame.model.GameInfo;
import com.easyplay.easygame.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GameListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<GameInfo> gameList;
  private ViewHolder holder;

  class ViewHolder {
    ImageView gameLogo;
    TextView gameName;
  }

  public GameListAdapter(Context context, List<GameInfo> data) {
    mContext = context;
    gameList = data;
  }

  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return gameList.size();
  }

  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return gameList.get(position);
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
    GameInfo game = gameList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_game_select, null);
      holder.gameName = (TextView) convertView
          .findViewById(R.id.item_game_name);
      holder.gameLogo = (ImageView) convertView
          .findViewById(R.id.item_game_logo);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (game != null) {
      holder.gameName.setText(Tools.checkString(game.getGameName()));
      if (game.getGameLogo() != null) {
        ImageLoader.getInstance().displayImage(game.getGameLogo(),
            holder.gameLogo);
      }
    }
    return convertView;
  }

}
