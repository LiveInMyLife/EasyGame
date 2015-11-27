package com.easyplay.easygame.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * ViewPager适配器
 * 
 * @author chuwe1
 * 
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

  private final Context context;
  private final List<Class<?>> mList;

  public MyFragmentPagerAdapter(FragmentManager fm, Context context,
      List<Class<?>> fragments) {
    super(fm);
    this.context = context;
    mList = fragments;
  }

  @Override
  public Fragment getItem(int arg0) {
    return Fragment.instantiate(context, mList.get(arg0).getName());
  }

  @Override
  public int getCount() {
    return mList == null ? 0 : mList.size();
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    // 删除super.destoryItem(container, postion, object);是为了不让fragment被回收
    // 下次划过来再create一次
    // super.destroyItem(container, position, object);
  }

}
