package com.easyplay.easygame.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.Topic;
import com.easyplay.easygame.tools.Tools;

public class TopicAddActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "MyShopAddOrderActivity";
  private EditText et_activity_title, et_activity_description,
      et_activity_select1, et_activity_select2;
  private TextView selectTime;
  private RelativeLayout btnAddActivity;
  private TextView btnSelectTime;

  private String activity_title, activity_description, activity_select1,
      activity_select2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_activity_add);
    initActionBar(this.getResources().getString(R.string.order_add));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    et_activity_title = (EditText) this.findViewById(R.id.et_activity_title);
    et_activity_description = (EditText) this
        .findViewById(R.id.et_activity_description);
    et_activity_select1 = (EditText) this
        .findViewById(R.id.et_activity_select1);
    et_activity_select2 = (EditText) this
        .findViewById(R.id.et_activity_select2);
    selectTime = (TextView) this.findViewById(R.id.tv_new_select_time);
    btnAddActivity = (RelativeLayout) this.findViewById(R.id.btn_new_order_add);
    btnSelectTime = (TextView) this.findViewById(R.id.btn_new_select_time);
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
    btnSelectTime.setOnClickListener(this);
    btnAddActivity.setOnClickListener(this);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
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
    if (Tools.isEmpty(et_activity_title.getEditableText().toString())) {
      showErrorToast("请填写标题");
      return false;
    } else if (Tools.isEmpty(et_activity_select1.getEditableText().toString())) {
      showErrorToast("请填写选项1");
      return false;
    } else if (Tools.isEmpty(et_activity_select2.getEditableText().toString())) {
      activity_description = "请填写选项2";
      return false;
    }
    activity_title = et_activity_title.getEditableText().toString();
    activity_description = et_activity_description.getEditableText().toString();
    activity_select1 = et_activity_select1.getEditableText().toString();
    activity_select2 = et_activity_select2.getEditableText().toString();

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

    final Topic newActivity = new Topic();
    newActivity.setTopicCreator(BaseApplication.userManager.getCurrentUser());
    newActivity.setTopicTitle(activity_title);
    newActivity.setTopicDescription(activity_description);
    newActivity.setTopicSelect1(activity_select1);
    newActivity.setTopicSelect2(activity_select2);
    newActivity.save(this, new SaveListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        showSuccessToast("^_^添加成功");
        // Intent intent = new Intent(TopicAddActivity.this,
        // MyShopActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Bundle bundle = new Bundle();
        // bundle.putSerializable("shop_order", newActivity);
        // intent.putExtras(bundle);
        // TopicAddActivity.this.setResult(RESULT_OK);
        TopicAddActivity.this.finish();
      }

      @Override
      public void onFailure(int code, String msg) {
        // TODO Auto-generated method stub
        showErrorToast("T_T添加失败，请检查网络后重试");
      }
    });
  }
}
