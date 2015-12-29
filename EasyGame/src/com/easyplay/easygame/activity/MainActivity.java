package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyFragmentPagerAdapter;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.fragment.MineFragment;
import com.easyplay.easygame.fragment.OrderFragment;
import com.easyplay.easygame.fragment.SparringFragment;
import com.easyplay.easygame.util.ActivityUtils;

public class MainActivity extends FragmentActivity {

  private ViewPager viewPager;
  private MyFragmentPagerAdapter adapter;
  private List<Class<?>> mList;
  // 底部三个按钮
  private Button btn_home, btn_teamwork, btn_more;
  private ImageView myShop;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewPager = (ViewPager) findViewById(R.id.viewPager);

    btn_home = (Button) findViewById(R.id.btn_home);
    btn_teamwork = (Button) findViewById(R.id.btn_teamwork);
    btn_more = (Button) findViewById(R.id.btn_more);

    viewPager.setOnPageChangeListener(pageChangeListener);
    btn_home.setOnClickListener(clickListener);
    btn_teamwork.setOnClickListener(clickListener);
    btn_more.setOnClickListener(clickListener);

    myShop = (ImageView) findViewById(R.id.img_my_shop);
    myShop.setOnClickListener(clickListener);

    initData();
    adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this,
        mList);
    viewPager.setAdapter(adapter);
    btn_home.setSelected(true);
  }

  private void initData() {
    mList = new ArrayList<Class<?>>();
    mList.add(SparringFragment.class);
    // mList.add(LevelingFragment.class);
    mList.add(OrderFragment.class);
    mList.add(MineFragment.class);
  }

  private final OnClickListener clickListener = new OnClickListener() {

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
      case R.id.btn_home:
        viewPager.setCurrentItem(0);
        break;
      case R.id.btn_teamwork:
        viewPager.setCurrentItem(1);
        break;
      case R.id.btn_more:
        viewPager.setCurrentItem(2);
        break;
      case R.id.img_my_shop:
        toMyShop();
      }
    }
  };

  private final OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

    /**
     * 滑动停止时调用
     * 
     * @param position
     *          当前页面位置
     */
    @Override
    public void onPageSelected(int position) {
      setSelectedPosition(position);
    }

    /**
     * 页面开始滑动到停止之前一直得到调用
     * 
     * @param arg0
     *          当前可见第一个页面的position
     * @param arg1
     *          当前页面偏移百分比
     * @param arg2
     *          当前页面偏移像素位置
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    /**
     * 滑动状态改变时被调用
     * 
     * @param atate
     *          有3个值，0：滑动结束 1：手指按下 2：手指抬起
     */
    @Override
    public void onPageScrollStateChanged(int atate) {
    }
  };

  private void setSelectedPosition(int position) {
    switch (position) {
    case 0:
      btn_home.setSelected(true);
      btn_teamwork.setSelected(false);
      btn_more.setSelected(false);
      break;
    case 1:
      btn_home.setSelected(false);
      btn_teamwork.setSelected(true);
      btn_more.setSelected(false);
      break;
    case 2:
      btn_home.setSelected(false);
      btn_teamwork.setSelected(false);
      btn_more.setSelected(true);
      break;
    }
  }

  private void toMyShop() {
    if (BaseApplication.userManager.getCurrentUser() != null) {
      Intent intent = new Intent(this, MyShopActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      this.startActivity(intent);
    } else {
      ActivityUtils.toLoginActivity(this);
    }
  }
}
