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

public class ShopSettingActivity extends BaseActivity implements
    OnClickListener {
  private static final String TAG = "ShopSettingActivity";
  private ShopInfo shopInfo;
  private TextView gameName;
  private EditText gameServer, shopName, shopNotice, contactPhone, contactQQ;
  private RelativeLayout btnAddOrder;

  private String gameNameStr, gameServerStr, shopNameStr, shopNoticeStr,
      contactPhoneStr, contactQQStr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_detail);
    initActionBar(this.getResources().getString(R.string.shop_detail));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    gameName = (TextView) findViewById(R.id.my_shop_gamename);
    gameServer = (EditText) findViewById(R.id.my_shop_gameserver);
    shopName = (EditText) findViewById(R.id.my_shop_shopname);
    shopNotice = (EditText) findViewById(R.id.my_shop_notice_tv);
    contactPhone = (EditText) findViewById(R.id.my_shop_contact_phone);
    contactQQ = (EditText) findViewById(R.id.my_shop_contact_QQ);
    btnAddOrder = (RelativeLayout) findViewById(R.id.my_shop_add_order);
  }

  @Override
  protected void setListener() {
    gameName.setOnClickListener(this);
    btnAddOrder.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    shopInfo = (ShopInfo) this.getIntent().getSerializableExtra("shop_info");
    if (shopInfo != null) {
      if (shopInfo.getShopGame() != null) {
        gameName.setText(shopInfo.getShopGame());
      }
      if (shopInfo.getGameServer() != null) {
        gameServer.setText(shopInfo.getGameServer());
      }
      if (shopInfo.getShopName() != null) {
        shopName.setText(shopInfo.getShopName());
      }
      if (shopInfo.getShopDescription() != null) {
        shopNotice.setText(shopInfo.getShopDescription());
      }
      if (shopInfo.getContactPhone() != null) {
        contactPhone.setText(shopInfo.getContactPhone());
      }
      if (shopInfo.getContactQQ() != null) {
        contactQQ.setText(shopInfo.getContactQQ());
      }
    }
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.my_shop_add_order:
      saveInfo();
      break;
    }

  }

  private void saveInfo() {
    if (checkInput()) {
      final ShopInfo info;
      if (shopInfo != null) {
        info = shopInfo;
      } else {
        info = new ShopInfo();
        info.setOwner(BaseApplication.userManager.getCurrentUser());
      }
      info.setShopLogo("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3043342069,229207001&fm=116&gp=0.jpg");
      info.setShopGame(gameNameStr);
      info.setShopName(shopNameStr);
      info.setShopDescription(shopNoticeStr);
      info.setContactPhone(contactPhoneStr);
      info.setContactQQ(contactQQStr);
      info.save(this, new SaveListener() {

        @Override
        public void onSuccess() {
          // TODO Auto-generated method stub
          showSuccessToast("^_^操作成功");
          Intent intent = new Intent(ShopSettingActivity.this,
              MyShopActivity.class);
          Bundle bundle = new Bundle();
          bundle.putSerializable("shop_info", info);
          intent.putExtras(bundle);
          ShopSettingActivity.this.setResult(RESULT_OK, intent);
          ShopSettingActivity.this.finish();
        }

        @Override
        public void onFailure(int code, String msg) {
          // TODO Auto-generated method stub
          showErrorToast("T_T操作失败，请检查网络后重试");
        }
      });
    }

  }

  private boolean checkInput() {
    boolean flag = true;
    gameServerStr = gameServer.getEditableText().toString();
    shopNameStr = shopName.getEditableText().toString();
    shopNoticeStr = shopNotice.getEditableText().toString();
    contactPhoneStr = contactPhone.getEditableText().toString();
    contactQQStr = contactQQ.getEditableText().toString();
    if (gameNameStr == null) {
      flag = false;
      showErrorToast("请选择游戏");
    } else if (gameServerStr == null) {
      flag = false;
      showErrorToast("请输入区服信息");
    } else if (shopNameStr == null) {
      flag = false;
      showErrorToast("请输入店铺名");
    } else if (shopNoticeStr == null) {
      flag = false;
      showErrorToast("请输入店铺简介");
    } else if (contactPhoneStr == null && contactQQStr == null) {
      flag = false;
      showErrorToast("请至少填写一种联系方式");
    }
    return flag;
  }
}
