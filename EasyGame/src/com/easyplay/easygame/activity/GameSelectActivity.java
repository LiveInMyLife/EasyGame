package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.GameListAdapter;
import com.easyplay.easygame.model.GameInfo;
import com.easyplay.easygame.tools.AppLog;

public class GameSelectActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "GameSelectActivity";

  private GameListAdapter gameListAdapter;
  private ListView gameList;
  private final List<GameInfo> gameListInfo = new ArrayList<GameInfo>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_detail);
    initActionBar(this.getResources().getString(R.string.game_select));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    gameList = (ListView) this.findViewById(R.id.game_select_list);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    gameListAdapter = new GameListAdapter(this, gameListInfo);
    gameList.setAdapter(gameListAdapter);
    queryGameList();
  }

  public void queryGameList() {
    BmobQuery<GameInfo> query = new BmobQuery<GameInfo>();
    GameInfo test = new GameInfo();
    query.findObjects(this, new FindListener<GameInfo>() {
      @Override
      public void onSuccess(List<GameInfo> object) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询成功：记录条数：" + object.size());
        for (GameInfo gameInfo : object) {
          gameListInfo.add(gameInfo);
        }
        gameListAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d(TAG, "查询失败：" + code + msg);
      }
    });
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.tv_shop_order:

      break;
    }

  }
}
