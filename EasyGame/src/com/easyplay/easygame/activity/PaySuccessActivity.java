package com.easyplay.easygame.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.easyplay.easygame.R;
import com.easyplay.easygame.config.Config;

public class PaySuccessActivity extends BaseActivity implements OnClickListener {

  String APPID = Config.applicationId;
  Button btn_order_detail, btn_contact_server;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pay_success);
    initActionBar(this.getResources().getString(R.string.pay_success));
    bindViews();
    setListener();
    init();
  }

  @Override
  protected void bindViews() {
    // TODO Auto-generated method stub
    btn_order_detail = (Button) this
        .findViewById(R.id.pay_success_order_detail);
    btn_contact_server = (Button) this
        .findViewById(R.id.pay_success_contact_server);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    btn_order_detail.setOnClickListener(this);
    btn_contact_server.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.pay_success_order_detail:
      goToOrderDetailActivity();
      break;
    case R.id.pay_success_contact_server:
      goToIMActivity();
    }
  }

  private void goToOrderDetailActivity() {
  }

  private void goToIMActivity() {
  }

}
