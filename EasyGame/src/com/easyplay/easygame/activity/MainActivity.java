package com.easyplay.easygame.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.inteface.EventListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MyFragmentPagerAdapter;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.fragment.MineFragment;
import com.easyplay.easygame.fragment.OrderFragment;
import com.easyplay.easygame.fragment.SparringFragment;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.util.ActivityUtils;
import com.easyplay.easygame.view.DialogTips;

public class MainActivity extends FragmentActivity implements EventListener,
    OnClickListener {
  private static final String TAG = "MainActivity";

  private ViewPager viewPager;
  private MyFragmentPagerAdapter adapter;
  private List<Class<?>> mList;
  // 底部三个按钮
  private LinearLayout btn_home, btn_order, btn_user;
  private TextView myShop;
  private TextView tv_sparing, tv_leveling;
  private LinearLayout orderTypeLayout;

  private TextView fragmentTitle;
  private TextView appTitle;

  private ImageView iv_home, iv_order, iv_user;
  private TextView tv_home, tv_order, tv_user;

  private int orderType = 0;// 用来标记用户选择的订单类型 0陪练，1代练

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewPager = (ViewPager) findViewById(R.id.viewPager);

    btn_home = (LinearLayout) findViewById(R.id.btn_home);
    btn_order = (LinearLayout) findViewById(R.id.btn_order);
    btn_user = (LinearLayout) findViewById(R.id.btn_user);

    iv_home = (ImageView) findViewById(R.id.iv_home);
    iv_order = (ImageView) findViewById(R.id.iv_order);
    iv_user = (ImageView) findViewById(R.id.iv_user);

    tv_home = (TextView) findViewById(R.id.tv_home);
    tv_order = (TextView) findViewById(R.id.tv_order);
    tv_user = (TextView) findViewById(R.id.tv_user);

    viewPager.setOnPageChangeListener(pageChangeListener);
    btn_home.setOnClickListener(this);
    btn_order.setOnClickListener(this);
    btn_user.setOnClickListener(this);

    myShop = (TextView) findViewById(R.id.tv_my_shop);
    tv_sparing = (TextView) findViewById(R.id.tv_order_type_sparing);
    tv_leveling = (TextView) findViewById(R.id.tv_order_type_leveling);
    orderTypeLayout = (LinearLayout) findViewById(R.id.ll_order_type_switch);
    orderTypeLayout.setOnClickListener(this);

    fragmentTitle = (TextView) findViewById(R.id.tv_fragment_title);
    appTitle = (TextView) findViewById(R.id.action_bar_title);

    myShop.setOnClickListener(this);

    initData();
    adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this,
        mList);
    viewPager.setAdapter(adapter);
    btn_home.setSelected(true);

    initNewMessageBroadCast();
    initTagMessageBroadCast();
  }

  private void initData() {
    mList = new ArrayList<Class<?>>();
    mList.add(SparringFragment.class);
    // mList.add(LevelingFragment.class);
    mList.add(OrderFragment.class);
    mList.add(MineFragment.class);
  }

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

  @SuppressLint("NewApi")
  private void setSelectedPosition(int position) {
    switch (position) {
    case 0:
      btn_home.setSelected(true);
      btn_order.setSelected(false);
      btn_user.setSelected(false);

      iv_home.setBackground(this.getResources().getDrawable(
          R.drawable.icon_home_pressed));
      tv_home.setTextColor(this.getResources().getColor(
          R.color.tab_text_pressed));
      iv_order.setBackground(this.getResources().getDrawable(
          R.drawable.icon_order));
      tv_order.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      iv_user.setBackground(this.getResources().getDrawable(
          R.drawable.icon_user));
      tv_user.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      break;
    case 1:
      btn_home.setSelected(false);
      btn_order.setSelected(true);
      btn_user.setSelected(false);

      iv_home.setBackground(this.getResources().getDrawable(
          R.drawable.icon_home));
      tv_home.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      iv_order.setBackground(this.getResources().getDrawable(
          R.drawable.icon_order_pressed));
      tv_order.setTextColor(this.getResources().getColor(
          R.color.tab_text_pressed));
      iv_user.setBackground(this.getResources().getDrawable(
          R.drawable.icon_user));
      tv_user.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      break;
    case 2:
      btn_home.setSelected(false);
      btn_order.setSelected(false);
      btn_user.setPressed(true);

      iv_home.setBackground(this.getResources().getDrawable(
          R.drawable.icon_home));
      tv_home.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      iv_order.setBackground(this.getResources().getDrawable(
          R.drawable.icon_order));
      tv_order.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      iv_user.setBackground(this.getResources().getDrawable(
          R.drawable.icon_user_pressed));
      tv_user.setTextColor(this.getResources().getColor(
          R.color.tab_text_pressed));
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

  /**
   * 刷新界面
   * 
   * @Title: refreshNewMsg
   * @Description: TODO
   * @param @param message
   * @return void
   * @throws
   */
  private void refreshNewMsg(BmobMsg message) {
    // 声音提示
    boolean isAllow = BaseApplication.getInstance().getSpUtil().isAllowVoice();
    if (isAllow) {
      BaseApplication.getInstance().getMediaPlayer().start();
    }
    // iv_recent_tips.setVisibility(View.VISIBLE);
    // 也要存储起来
    if (message != null) {
      BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true,
          message);
    }
    // if (currentTabIndex == 0) {
    // // 当前页面如果为会话页面，刷新此页面
    // if (recentFragment != null) {
    // recentFragment.refresh();
    // }
    // }
  }

  NewBroadcastReceiver newReceiver;

  private void initNewMessageBroadCast() {
    // 注册接收消息广播
    newReceiver = new NewBroadcastReceiver();
    IntentFilter intentFilter = new IntentFilter(
        BmobConfig.BROADCAST_NEW_MESSAGE);
    // 优先级要低于ChatActivity
    intentFilter.setPriority(3);
    registerReceiver(newReceiver, intentFilter);
  }

  /**
   * 新消息广播接收者
   * 
   */
  private class NewBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      // 刷新界面
      refreshNewMsg(null);
      // 记得把广播给终结掉
      abortBroadcast();
    }
  }

  TagBroadcastReceiver userReceiver;

  private void initTagMessageBroadCast() {
    // 注册接收消息广播
    userReceiver = new TagBroadcastReceiver();
    IntentFilter intentFilter = new IntentFilter(
        BmobConfig.BROADCAST_ADD_USER_MESSAGE);
    // 优先级要低于ChatActivity
    intentFilter.setPriority(3);
    registerReceiver(userReceiver, intentFilter);
  }

  /**
   * 标签消息广播接收者
   */
  private class TagBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      BmobInvitation message = (BmobInvitation) intent
          .getSerializableExtra("invite");
      // refreshInvite(message);
      // 记得把广播给终结掉
      AppLog.d(TAG, "接收到消息：" + message.toString());
      abortBroadcast();
    }
  }

  @Override
  public void onNetChange(boolean isNetConnected) {
    // TODO Auto-generated method stub
    if (isNetConnected) {
      ShowToast(R.string.network_tips);
    }
  }

  @Override
  public void onAddUser(BmobInvitation message) {
    // TODO Auto-generated method stub
    // refreshInvite(message);
  }

  /**
   * 刷新好友请求
   * 
   * @Title: notifyAddUser
   * @Description: TODO
   * @param @param message
   * @return void
   * @throws
   */
  // private void refreshInvite(BmobInvitation message) {
  // boolean isAllow = BaseApplication.getInstance().getSpUtil().isAllowVoice();
  // if (isAllow) {
  // BaseApplication.getInstance().getMediaPlayer().start();
  // }
  // iv_contact_tips.setVisibility(View.VISIBLE);
  // if (currentTabIndex == 1) {
  // if (contactFragment != null) {
  // contactFragment.refresh();
  // }
  // } else {
  // // 同时提醒通知
  // String tickerText = message.getFromname() + "请求添加好友";
  // boolean isAllowVibrate = BaseApplication.getInstance().getSpUtil()
  // .isAllowVibrate();
  // BmobNotifyManager.getInstance(this).showNotify(isAllow, isAllowVibrate,
  // R.drawable.ic_launcher, tickerText, message.getFromname(),
  // tickerText.toString(), NewFriendActivity.class);
  // }
  // }

  @Override
  public void onOffline() {
    // TODO Auto-generated method stub
    showOfflineDialog(this);
  }

  @Override
  public void onMessage(BmobMsg message) {
    // TODO Auto-generated method stub
    refreshNewMsg(message);
  }

  @Override
  public void onReaded(String conversionId, String msgTime) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
    try {
      unregisterReceiver(newReceiver);
    } catch (Exception e) {
    }
    try {
      unregisterReceiver(userReceiver);
    } catch (Exception e) {
    }
    // 取消定时检测服务
    // BmobChat.getInstance(this).stopPollService();
  }

  protected void ShowToast(int tipResId) {
    Toast.makeText(this, getString(tipResId), Toast.LENGTH_SHORT).show();
  }

  /**
   * 显示下线的对话框 showOfflineDialog
   * 
   * @return void
   * @throws
   */
  public void showOfflineDialog(final Context context) {
    DialogTips dialog = new DialogTips(this, "您的账号已在其他设备上登录!", "重新登录");
    // 设置成功事件
    dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int userId) {
        BaseApplication.getInstance().logout();
        startActivity(new Intent(context, LoginActivity.class));
        finish();
        dialogInterface.dismiss();
      }
    });
    // 显示确认对话框
    dialog.show();
    dialog = null;
  }

  @SuppressLint("NewApi")
  private void switchOrderType() {
    if (orderType == 0) {
      orderType = 1;
      tv_sparing.setBackgroundColor(Color.TRANSPARENT);
      tv_sparing.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));
      tv_leveling.setBackground(this.getResources().getDrawable(
          R.drawable.bg_sky_blue_button));
      tv_leveling.setTextColor(this.getResources().getColor(
          R.color.text_color_white));
    } else {
      orderType = 0;
      tv_sparing.setBackground(this.getResources().getDrawable(
          R.drawable.bg_sky_blue_button));
      tv_sparing.setTextColor(this.getResources().getColor(
          R.color.text_color_white));
      tv_leveling.setBackgroundColor(Color.TRANSPARENT);
      tv_leveling.setTextColor(this.getResources().getColor(
          R.color.text_color_light_grey2));

    }
  }

  private void setSwitchVisible(boolean visible) {
    if (visible) {
      orderTypeLayout.setVisibility(View.VISIBLE);
      myShop.setVisibility(View.VISIBLE);
    } else {
      orderTypeLayout.setVisibility(View.GONE);
      myShop.setVisibility(View.GONE);
    }
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.btn_home:
      viewPager.setCurrentItem(0);
      fragmentTitle.setVisibility(View.GONE);
      setSwitchVisible(true);
      appTitle.setVisibility(View.VISIBLE);
      break;
    case R.id.btn_order:
      viewPager.setCurrentItem(1);
      fragmentTitle.setVisibility(View.VISIBLE);
      fragmentTitle.setText("订单");
      setSwitchVisible(false);
      appTitle.setVisibility(View.GONE);
      break;
    case R.id.btn_user:
      viewPager.setCurrentItem(2);
      fragmentTitle.setVisibility(View.VISIBLE);
      fragmentTitle.setText("我");
      setSwitchVisible(false);
      appTitle.setVisibility(View.GONE);
      break;
    case R.id.tv_my_shop:
      toMyShop();
      break;
    case R.id.ll_order_type_switch:
      switchOrderType();
    }
  }

}
