package com.easyplay.easygame.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;

import com.easyplay.easygame.R;
import com.easyplay.easygame.tools.Tools;

public abstract class BaseActivity extends ActionBarActivity {

  protected ViewGroup mTitleHeader;
  protected ImageView mIvBack;
  protected TextView mTvTitle;
  protected TextView mTvOperate;
  protected ImageView mIvRightOperate;
  protected ProgressDialog progress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().hide();
    // checkLogin();
  }

  @Override
  protected void onResume() {
    super.onResume();
    // checkLogin();
  }

  protected abstract void bindViews();

  protected abstract void setListener();

  protected abstract void init();

  protected void initActionBar(String title) {
    mTitleHeader = (ViewGroup) findViewById(R.id.rl_actionbar);
    mIvBack = (ImageView) findViewById(R.id.iv_back);
    mTvTitle = (TextView) findViewById(R.id.tv_title);
    mTvTitle.setText(title);
    mTvOperate = (TextView) findViewById(R.id.tv_operate);
    mIvRightOperate = (ImageView) findViewById(R.id.iv_right_operator);
    mIvBack.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
        onBack();
      }
    });
  }

  protected void onBack() {
    finish();
  }

  @Override
  public void onBackPressed() {
    onBack();
  }

  protected void setTitle(String title) {
    mTvTitle.setText(title);
  }

  @Override
  public void setTitle(int title) {
    setTitle(getResources().getString(title));
  }

  protected void setOperate(String operate) {
    if (mTvOperate == null) {
      mTvOperate = (TextView) findViewById(R.id.tv_operate);
    }
    mTvOperate.setVisibility(View.VISIBLE);
    mTvOperate.setText(operate);
  }

  protected void setRightOperate(Drawable drawable) {
    mIvRightOperate.setVisibility(View.VISIBLE);
    mIvRightOperate.setImageDrawable(drawable);
  }

  protected void showToast(int tipResId) {
    Toast.makeText(this, getString(tipResId), Toast.LENGTH_SHORT).show();
  }

  protected void showToast(String tip) {
    Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
  }

  public void showToast(int imgResId, String tip) {
    Toast toast = Toast.makeText(this, tip, Toast.LENGTH_SHORT);
    LinearLayout toastLayout = (LinearLayout) toast.getView();
    toastLayout.setOrientation(LinearLayout.VERTICAL);
    ImageView imageView = new ImageView(this);
    imageView.setImageResource(imgResId);
    imageView.setPadding(0, 0, 0, Tools.dip2px(this, 10));
    toastLayout.addView(imageView, 0);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }

  protected void showSuccessToast(String tip) {
    showToast(R.drawable.toast_success, tip);
  }

  protected void showSuccessToast(int tipResId) {
    showToast(R.drawable.toast_success, getString(tipResId));
  }

  protected void showErrorToast(String tip) {
    showToast(R.drawable.toast_error, tip);
  }

  protected void showErrorToast(int tipResId) {
    showToast(R.drawable.toast_error, getString(tipResId));
  }

  public void showProgressDialog(String message) {

    if (progress == null) {
      progress = new ProgressDialog(this);
      progress.setCanceledOnTouchOutside(false);
      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    progress.setMessage(message);
    progress.show();

  }

  public void dismissProgressDialog() {
    if (progress != null && progress.isShowing()) {
      progress.dismiss();
    }
  }

  // 检测是否有其他设备登录了同一账号
  public void checkLogin() {
    BmobUserManager userManager = BmobUserManager.getInstance(this);
    if (userManager.getCurrentUser() == null) {
      showErrorToast("您的账号已在其他设备上登录!");
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
  }

  public void startAnimActivity(Class<?> cla) {
    this.startActivity(new Intent(this, cla));
  }

  public void startAnimActivity(Intent intent) {
    this.startActivity(intent);
  }
}
