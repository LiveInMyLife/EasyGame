package com.easyplay.easygame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyplay.easygame.R;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.tools.ActivityUtils;

/**
 * 个人主页
 * 
 * @author chuwe1
 * 
 */
public class MineFragment extends Fragment {
  private Context mContext;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mContext = this.getActivity().getApplicationContext();
    View view = inflater.inflate(R.layout.fragment_mine, null);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (BaseApplication.userManager.getCurrentUser() != null) {
    } else {
      ActivityUtils.toLoginActivity(mContext);
    }
  }
}
