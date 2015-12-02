package com.easyplay.easygame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyplay.easygame.R;

/**
 * 个人主页
 * 
 * @author chuwe1
 * 
 */
public class MineFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_mine, null);

    return view;
  }
}
