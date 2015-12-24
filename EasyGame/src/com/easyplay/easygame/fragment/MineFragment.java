package com.easyplay.easygame.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.Cash;
import com.easyplay.easygame.tools.ActivityUtils;
import com.easyplay.easygame.tools.AppLog;

/**
 * 个人主页
 * 
 * @author chuwe1
 * 
 */
public class MineFragment extends Fragment implements OnClickListener {
  private Context mContext;
  private Cash cash;
  private TextView tv_cash;
  private TextView draw_money;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mContext = this.getActivity().getApplicationContext();
    View view = inflater.inflate(R.layout.fragment_mine, null);
    tv_cash = (TextView) view.findViewById(R.id.tv_cash);
    draw_money = (TextView) view.findViewById(R.id.draw_money);
    draw_money.setOnClickListener(this);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (BaseApplication.userManager.getCurrentUser() != null) {
      queryCashInfo();
    } else {
      ActivityUtils.toLoginActivity(mContext);
    }
  }

  public void queryCashInfo() {
    BmobQuery<Cash> query = new BmobQuery<Cash>();
    query.addWhereEqualTo("userId", BaseApplication.userManager
        .getCurrentUser().getObjectId());
    query.findObjects(mContext, new FindListener<Cash>() {
      @Override
      public void onSuccess(List<Cash> object) {
        // TODO Auto-generated method stub
        AppLog.d("MineFragment", "ShopInfo查询成功：记录条数：" + object.size());
        if (object.size() > 0) {
          for (Cash cashInfo : object) {
            cash = cashInfo;
            tv_cash.setText(cash.getUserAmount());
          }
        }
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d("MineFragment", "查询失败：" + code + msg);
      }
    });
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.draw_money:
      break;
    }
  }
}
