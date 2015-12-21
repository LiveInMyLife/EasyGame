package com.easyplay.easygame.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.tools.Tools;

public class ShopOrderConfirmActivity extends BaseActivity implements
    OnClickListener {
  private static final String TAG = "ShopOrderPayActivity";
  private TextView tv_pay_order_name, pay_order_description,
      tv_pay_order_price;

  private TextView pay_order_num, pay_order_num_cut, pay_order_num_add;

  private TextView tv_select_service_time, btn_select_service_time;

  private TextView tv_pay_order_account;

  private RelativeLayout btn_pay_order_account;

  private ShopOrder mShopOrder;
  private int orderNum = 1;
  private double total;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_order_pay);
    initActionBar(this.getResources().getString(R.string.order_confirm));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    tv_pay_order_name = (TextView) this.findViewById(R.id.tv_pay_order_name);
    pay_order_description = (TextView) this
        .findViewById(R.id.pay_order_description);
    tv_pay_order_price = (TextView) this.findViewById(R.id.tv_pay_order_price);
    pay_order_num = (TextView) this.findViewById(R.id.pay_order_num);
    pay_order_num_cut = (TextView) this.findViewById(R.id.pay_order_num_cut);
    pay_order_num_add = (TextView) this.findViewById(R.id.pay_order_num_add);
    tv_select_service_time = (TextView) this
        .findViewById(R.id.tv_select_service_time);
    btn_select_service_time = (TextView) this
        .findViewById(R.id.btn_select_service_time);
    tv_pay_order_account = (TextView) this
        .findViewById(R.id.tv_pay_order_account);
    btn_pay_order_account = (RelativeLayout) this
        .findViewById(R.id.btn_pay_order_account);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    pay_order_num_cut.setOnClickListener(this);
    pay_order_num_add.setOnClickListener(this);
    btn_pay_order_account.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    mShopOrder = (ShopOrder) this.getIntent()
        .getSerializableExtra("order_info");
    if (mShopOrder != null) {
      tv_pay_order_name.setText(Tools.checkString(mShopOrder.getOrderName()));
      pay_order_description.setText(Tools.checkString(mShopOrder
          .getOrderDescription()));
      tv_pay_order_price.setText(Tools.checkString(mShopOrder.getOrderPrice()
          .toString()));
    } else {
      this.showErrorToast("订单信息获取失败，请返回重试");
      tv_pay_order_account.setText("¥" + 0 + "结算");
      disableAccount();
    }

    countTotal();
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.pay_order_num_cut:
      if (orderNum > 0) {
        orderNum--;
        countTotal();
      }
      break;
    case R.id.pay_order_num_add:
      orderNum++;
      countTotal();
      break;
    case R.id.btn_pay_order_account:
      toPayActivity();
      break;
    }

  }

  private void countTotal() {
    if (orderNum > 0 && mShopOrder != null) {
      btn_pay_order_account.setClickable(true);
      total = orderNum * mShopOrder.getOrderPrice();
      tv_pay_order_account.setText("¥" + total + "结算");
      pay_order_num.setText(orderNum + "");
    } else if (mShopOrder == null) {
      this.showErrorToast("订单信息获取失败，请返回重试");
      disableAccount();
    } else if (orderNum <= 0) {
      orderNum = 0;
      tv_pay_order_account.setText("¥" + 0.0 + "结算");
      pay_order_num.setText("0");
      disableAccount();
    }
  }

  @SuppressLint("NewApi")
  private void disableAccount() {
    Drawable bg = this.getResources().getDrawable(
        R.drawable.vehicle_policy_policy_add_button_bg);
    // btn_pay_order_account.setBackground(bg);
    btn_pay_order_account.setClickable(false);
  }

  private void toPayActivity() {
    Intent intent = new Intent(this, PayActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Bundle bundle = new Bundle();
    bundle.putSerializable("order_info", mShopOrder);
    bundle.putInt("buy_num", orderNum);
    bundle.putDouble("total", total);
    intent.putExtras(bundle);
    this.startActivity(intent);
  }

}
