package com.easyplay.easygame.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.tools.Tools;

public class MyShopAddOrderActivity extends BaseActivity implements
    OnClickListener {
  private static final String TAG = "MyShopAddOrderActivity";
  private EditText orderName, orderDescroption, price, unit;
  private TextView selectTime;
  private RelativeLayout btnAddOrder;
  private TextView btnSelectTime;

  private String order_name, order_description, order_price, order_unit;
  private int start_time, end_time;
  private ShopInfo mShopInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_shop_order_add);
    initActionBar(this.getResources().getString(R.string.order_add));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    orderName = (EditText) this.findViewById(R.id.et_new_order_name);
    orderDescroption = (EditText) this
        .findViewById(R.id.et_new_order_description);
    price = (EditText) this.findViewById(R.id.et_new_order_price);
    unit = (EditText) this.findViewById(R.id.et_new_order_unit);
    selectTime = (TextView) this.findViewById(R.id.tv_new_select_time);
    btnAddOrder = (RelativeLayout) this.findViewById(R.id.btn_new_order_add);
    btnSelectTime = (TextView) this.findViewById(R.id.btn_new_select_time);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    btnSelectTime.setOnClickListener(this);
    btnAddOrder.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    // queryShopInfo();
    mShopInfo = (ShopInfo) this.getIntent().getSerializableExtra("shop_info");
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.btn_new_order_add:
      saveOrder();
      break;
    }

  }

  private void saveOrder() {
    if (checkOrder()) {
      addOrder();
    } else {

    }
  }

  private boolean checkOrder() {
    if (Tools.isEmpty(orderName.getEditableText().toString())) {
      showErrorToast("请填写订单名");
      return false;
    } else if (Tools.isEmpty(price.getEditableText().toString())) {
      showErrorToast("别忘了填写单价哟");
      return false;
    } else if (Tools.isEmpty(unit.getEditableText().toString())) {
      showErrorToast("请填写计费单位");
      return false;
    } else if (Tools.isEmpty(orderDescroption.getEditableText().toString())) {
      order_description = "欢迎支持~";
      return false;
    }
    order_name = orderName.getEditableText().toString();
    order_description = orderDescroption.getEditableText().toString();
    order_price = price.getEditableText().toString();
    order_unit = unit.getEditableText().toString();
    start_time = 0;
    end_time = 24;
    return true;
  }

  // public void queryShopInfo() {
  // BmobQuery<ShopInfo> query = new BmobQuery<ShopInfo>();
  // query.getObject(this, "zHE9CCCn", new GetListener<ShopInfo>() {
  //
  // @Override
  // public void onFailure(int code, String arg0) {
  // // TODO Auto-generated method stub
  // AppLog.d(TAG, "查询Shop失败");
  // }
  //
  // @Override
  // public void onSuccess(ShopInfo arg0) {
  // // TODO Auto-generated method stub
  // AppLog.d(TAG, "查询Shop成功");
  // orderShop = arg0;
  // }
  //
  // });
  // }

  private void addOrder() {

    final ShopOrder shopOrder = new ShopOrder();
    shopOrder.setOrderShop(mShopInfo);
    shopOrder.setServer(BaseApplication.userManager.getCurrentUser());
    shopOrder.setOrderName(order_name);
    shopOrder.setOrderDescription(order_description);
    shopOrder.setOrderPrice(Double.parseDouble(order_price));
    shopOrder.setStartTime(start_time);
    shopOrder.setEndTime(end_time);
    shopOrder.setOrderTime(order_unit);
    shopOrder.save(this, new SaveListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        showSuccessToast("^_^添加成功");
        Intent intent = new Intent(MyShopAddOrderActivity.this,
            MyShopActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("shop_order", shopOrder);
        intent.putExtras(bundle);
        MyShopAddOrderActivity.this.setResult(RESULT_OK);
        MyShopAddOrderActivity.this.finish();
      }

      @Override
      public void onFailure(int code, String msg) {
        // TODO Auto-generated method stub
        showErrorToast("T_T添加失败，请检查网络后重试");
      }
    });
  }
}
