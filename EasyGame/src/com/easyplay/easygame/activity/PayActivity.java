package com.easyplay.easygame.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.OrderQueryListener;
import com.bmob.pay.tool.PayListener;
import com.easyplay.easygame.R;
import com.easyplay.easygame.config.Config;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.PayOrder;
import com.easyplay.easygame.model.ShopOrder;
import com.easyplay.easygame.tools.AppContent;

public class PayActivity extends BaseActivity implements
    OnCheckedChangeListener {
  private final String testOrderId = "078b8e43231caf898ef7a5a5c1b23c92";
  String APPID = Config.applicationId;
  BmobPay bmobPay;

  TextView name, price, body, order;
  Button go;
  RadioGroup type;
  TextView tv;

  ProgressDialog dialog;

  private ShopOrder mShopOrder;
  private int buyNum;
  private double total;
  private String payOrderID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pay);
    // 必须先初始化
    BmobPay.init(this, APPID);
    // 初始化BmobPay对象,可以在支付时再初始化
    bmobPay = new BmobPay(PayActivity.this);
    initActionBar(this.getResources().getString(R.string.order_confirm));
    bindViews();
    setListener();
    init();
  }

  // 调用支付宝支付
  void payByAli() {
    showDialog("正在获取订单");
    final String name = getName();

    bmobPay.pay(getPrice(), name, getBody(), new PayListener() {

      // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
      @Override
      public void unknow() {
        tv.append(name + "'s pay status is unknow\n\n");
        orderPayedConfirm(payOrderID);
        // hideDialog();
      }

      // 支付成功,如果金额较大请手动查询确认
      @Override
      public void succeed() {
        // Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
        showSuccessToast("支付成功!");
        tv.append(name + "'s pay status is success\n\n");
        orderPayedConfirm(payOrderID);
      }

      // 无论成功与否,返回订单号
      @Override
      public void orderId(String orderId) {
        // 此处应该保存订单号,比如保存进数据库等,以便以后查询
        payOrderID = orderId;
        order.setText(orderId);
        tv.append(name + "'s orderid is " + orderId + "\n\n");
        showDialog("获取订单成功!请等待跳转到支付页面~");
      }

      // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
      @Override
      public void fail(int code, String reason) {
        Toast.makeText(PayActivity.this, "支付中断!", Toast.LENGTH_SHORT).show();
        tv.append(name + "'s pay status is fail, error code is " + code
            + " ,reason is " + reason + "\n\n");
        // hideDialog();
        addPayOrder(AppContent.ORDER_STATE_UNPAYED);
      }
    });
  }

  // 调用微信支付
  void payByWeiXin() {
    showDialog("正在获取订单");
    final String name = getName();
    final String order_Id = null;
    bmobPay.payByWX(getPrice(), name, getBody(), new PayListener() {

      // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
      @Override
      public void unknow() {

        tv.append(name + "'s pay status is unknow\n\n");
        // query(getOrder());
        // hideDialog();
        orderPayedConfirm(payOrderID);
      }

      // 支付成功,如果金额较大请手动查询确认
      @Override
      public void succeed() {
        // Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
        showSuccessToast("支付成功!");
        tv.append(name + "'s pay status is success\n\n");
        orderPayedConfirm(payOrderID);
      }

      // 无论成功与否,返回订单号
      @Override
      public void orderId(String orderId) {
        // 此处应该保存订单号,比如保存进数据库等,以便以后查询
        payOrderID = orderId;
        order.setText(orderId);
        tv.append(name + "'s orderid is " + orderId + "\n\n");
        showDialog("获取订单成功!请等待跳转到支付页面~");
      }

      // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
      @Override
      public void fail(int code, String reason) {

        // 当code为-2,意味着用户中断了操作
        // code为-3意味着没有安装BmobPlugin插件
        if (code == -3) {
          new AlertDialog.Builder(PayActivity.this)
              .setMessage("监测到你尚未安装支付插件,无法进行微信支付,请选择安装插件(已打包在本地,无流量消耗)还是用支付宝支付")
              .setPositiveButton("安装", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                  installBmobPayPlugin("BmobPayPlugin.apk");
                }
              })
              .setNegativeButton("支付宝支付",
                  new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      payByAli();
                    }
                  }).create().show();
        } else {
          // Toast.makeText(PayActivity.this, "支付中断!",
          // Toast.LENGTH_SHORT).show();
          showErrorToast("支付中断!");
        }
        tv.append(name + "'s pay status is fail, error code is " + code
            + " ,reason is " + reason + "\n\n");
        addPayOrder(AppContent.ORDER_STATE_UNPAYED);
      }
    });
  }

  // 执行订单查询
  void query(final String orderId) {
    showDialog("正在查询订单");
    // final String orderId = getOrder();

    bmobPay.query(orderId, new OrderQueryListener() {

      @Override
      public void succeed(String status) {
        // Toast.makeText(PayActivity.this, "查询成功!该订单状态为 : " + status,
        // Toast.LENGTH_SHORT).show();
        showSuccessToast("查询成功!该订单状态为 : " + status);
        tv.append("pay status of" + orderId + " is " + status + "\n\n");
        hideDialog();
      }

      @Override
      public void fail(int code, String reason) {
        // Toast.makeText(PayActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
        showErrorToast("查询失败");
        tv.append("query order fail, error code is " + code + " ,reason is "
            + reason + "\n\n");
        hideDialog();
      }
    });
  }

  // 确保已经支付成功
  private void orderPayedConfirm(final String orderId) {
    if (payOrderID == null) {
      return;
    }
    // showDialog("正在查询订单");
    // final String orderId = getOrder();

    bmobPay.query(orderId, new OrderQueryListener() {

      @Override
      public void succeed(String status) {
        // Toast.makeText(PayActivity.this, "查询成功!该订单状态为 : " + status,
        // Toast.LENGTH_SHORT).show();

        tv.append("pay status of" + orderId + " is " + status + "\n\n");
        // hideDialog();
        if (status.equalsIgnoreCase(AppContent.PAY_SUCCESS)) {
          showSuccessToast("支付成功！");
          addPayOrder(AppContent.ORDER_STATE_ORDERED);
        } else {
          showErrorToast("支付失败,请再次确认");
          addPayOrder(AppContent.ORDER_STATE_UNPAYED);
        }
      }

      @Override
      public void fail(int code, String reason) {
        // Toast.makeText(PayActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
        showErrorToast("查询失败,请检查网络连接后再确认");
        tv.append("query order fail, error code is " + code + " ,reason is "
            + reason + "\n\n");
        // hideDialog();
        addPayOrder(AppContent.ORDER_STATE_UNPAYED);
      }
    });
  }

  // 以下仅为控件操作，可以略过
  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {
    switch (checkedId) {
    case R.id.pay_alipay:
      // 以下仅为控件操作，可以略过
      name.setVisibility(View.VISIBLE);
      price.setVisibility(View.VISIBLE);
      body.setVisibility(View.VISIBLE);
      order.setVisibility(View.GONE);
      go.setText("支付宝支付");
      break;
    case R.id.pay_wxpay:
      // 以下仅为控件操作，可以略过
      name.setVisibility(View.VISIBLE);
      price.setVisibility(View.VISIBLE);
      body.setVisibility(View.VISIBLE);
      order.setVisibility(View.GONE);
      go.setText("微信支付");
      break;
    case R.id.pay_query:
      // 以下仅为控件操作，可以略过
      name.setVisibility(View.GONE);
      price.setVisibility(View.GONE);
      body.setVisibility(View.GONE);
      order.setVisibility(View.VISIBLE);
      go.setText("订单查询");
      break;

    default:
      break;
    }
  }

  // 默认为0.02
  double getPrice() {
    double price = 0.02;
    try {
      price = Double.parseDouble(this.price.getText().toString());
    } catch (NumberFormatException e) {
    }
    return price;
  }

  // 商品详情(可不填)
  String getName() {
    return this.name.getText().toString();
  }

  // 商品详情(可不填)
  String getBody() {
    return this.body.getText().toString();
  }

  // 支付订单号(查询时必填)
  String getOrder() {
    return this.order.getText().toString();
  }

  void showDialog(String message) {
    try {
      if (dialog == null) {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
      }
      dialog.setMessage(message);
      dialog.show();
    } catch (Exception e) {
      // 在其他线程调用dialog会报错
    }
  }

  void hideDialog() {
    if (dialog != null && dialog.isShowing())
      try {
        dialog.dismiss();
      } catch (Exception e) {
      }
  }

  void installBmobPayPlugin(String fileName) {
    try {
      InputStream is = getAssets().open(fileName);
      File file = new File(Environment.getExternalStorageDirectory()
          + File.separator + fileName);
      file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file);
      byte[] temp = new byte[1024];
      int i = 0;
      while ((i = is.read(temp)) > 0) {
        fos.write(temp, 0, i);
      }
      fos.close();
      is.close();

      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setDataAndType(Uri.parse("file://" + file),
          "application/vnd.android.package-archive");
      startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void bindViews() {
    // TODO Auto-generated method stub
    name = (TextView) findViewById(R.id.pay_name);
    price = (TextView) findViewById(R.id.pay_price);
    body = (TextView) findViewById(R.id.pay_body);
    order = (TextView) findViewById(R.id.pay_order);
    go = (Button) findViewById(R.id.pay_go);
    type = (RadioGroup) findViewById(R.id.type);
    tv = (TextView) findViewById(R.id.pay_log_tv);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    type.setOnCheckedChangeListener(this);
    go.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // 检查订单的合法条件，失败则不能支付
        if (!checkOrder()) {
          return;
        }
        addPayOrder(AppContent.ORDER_STATE_ORDERED);
        // 当选择的是支付宝支付时
        // if (type.getCheckedRadioButtonId() == R.id.pay_alipay) {
        // payByAli();
        // } else if (type.getCheckedRadioButtonId() == R.id.pay_wxpay) {
        // // 调用插件用微信支付
        // payByWeiXin();
        // } else if (type.getCheckedRadioButtonId() == R.id.pay_query) {
        // // 选择查询时
        // query(getOrder());
        // }
      }
    });
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    mShopOrder = (ShopOrder) this.getIntent()
        .getSerializableExtra("order_info");
    buyNum = this.getIntent().getIntExtra("buy_num", 0);
    total = this.getIntent().getDoubleExtra("total", 0);
  }

  private void addPayOrder(int state) {
    final PayOrder payOrder = new PayOrder();
    payOrder.setTagOrder(mShopOrder);
    payOrder.setBuyNum(buyNum);
    payOrder.setCustomer(BaseApplication.userManager.getCurrentUser());
    payOrder.setServer(mShopOrder.getServer());
    // payOrder.setServer(BaseApplication.userManager.getCurrentUser());
    payOrder.setServerShop(mShopOrder.getOrderShop());
    // payOrder.setPayOrderID(payOrderID);
    payOrder.setPayOrderID(testOrderId);
    payOrder.setTotal(total);
    payOrder.setState(state);
    payOrder.setDescription(mShopOrder.getOrderDescription());
    payOrder.setPrice(mShopOrder.getOrderPrice());
    payOrder.setUnit(mShopOrder.getOrderTime());
    payOrder.save(this, new SaveListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        hideDialog();
        showSuccessToast("^_^订单提交成功");
        // Intent intent = new Intent(PayActivity.this, MyShopActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Bundle bundle = new Bundle();
        // bundle.putSerializable("shop_order", shopOrder);
        // intent.putExtras(bundle);
        // PayActivity.this.setResult(RESULT_OK);
        // PayActivity.this.finish();
      }

      @Override
      public void onFailure(int code, String msg) {
        // TODO Auto-generated method stub
        hideDialog();
        showErrorToast("提交订单失败," + msg);
      }
    });
  }

  private boolean checkOrder() {
    if (mShopOrder == null || buyNum == 0 || total == 0
        || mShopOrder.getOrderShop() == null
        || BaseApplication.userManager.getCurrentUser() == null) {
      return false;
    }
    return true;
  }

}
