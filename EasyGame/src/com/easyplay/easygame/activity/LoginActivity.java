package com.easyplay.easygame.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.utils.BmobLog;
import com.easyplay.easygame.R;
import com.easyplay.easygame.config.AppConstants;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.User;
import com.easyplay.easygame.tools.CommonUtils;
import com.easyplay.easygame.view.DialogTips;

/**
 * @ClassName: LoginActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-3 下午4:41:42
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

  EditText et_username, et_password;
  Button btn_login;
  TextView btn_register;

  private final MyBroadcastReceiver receiver = new MyBroadcastReceiver();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    init();
    // 注册退出广播
    IntentFilter filter = new IntentFilter();
    filter.addAction(AppConstants.ACTION_REGISTER_SUCCESS_FINISH);
    registerReceiver(receiver, filter);
    // showNotice();
  }

  public void showNotice() {
    DialogTips dialog = new DialogTips(this, "提示", getResources().getString(
        R.string.show_notice), "确定", true, true);
    // 设置成功事件
    dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int userId) {

      }
    });
    // 显示确认对话框
    dialog.show();
    dialog = null;
  }

  @Override
  protected void init() {
    et_username = (EditText) findViewById(R.id.et_username);
    et_password = (EditText) findViewById(R.id.et_password);
    btn_login = (Button) findViewById(R.id.btn_login);
    btn_register = (TextView) findViewById(R.id.btn_register);
    btn_login.setOnClickListener(this);
    btn_register.setOnClickListener(this);
  }

  public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent != null
          && AppConstants.ACTION_REGISTER_SUCCESS_FINISH.equals(intent
              .getAction())) {
        finish();
      }
    }

  }

  @Override
  public void onClick(View v) {
    if (v == btn_register) {
      Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
      startActivity(intent);
    } else {
      boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
      if (!isNetConnected) {
        showToast(R.string.network_tips);
        return;
      }
      login();
    }
  }

  private void login() {
    String name = et_username.getText().toString();
    String password = et_password.getText().toString();

    if (TextUtils.isEmpty(name)) {
      showToast(R.string.toast_error_username_null);
      return;
    }

    if (TextUtils.isEmpty(password)) {
      showToast(R.string.toast_error_password_null);
      return;
    }

    final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
    progress.setMessage("正在登陆...");
    progress.setCanceledOnTouchOutside(false);
    progress.show();
    User user = new User();
    user.setUsername(name);
    user.setPassword(password);
    user.setNick(name);
    BaseApplication.userManager.login(user, new SaveListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            // TODO Auto-generated method stub
            progress.setMessage("正在获取好友列表...");
          }
        });
        // 更新用户的地理位置以及好友的资料
        // updateUserInfos();
        progress.dismiss();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }

      @Override
      public void onFailure(int errorcode, String arg0) {
        // TODO Auto-generated method stub
        progress.dismiss();
        BmobLog.i(arg0);
        showToast(arg0);
      }
    });

  }

  @Override
  protected void bindViews() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub

  }

}
