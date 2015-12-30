package com.easyplay.easygame.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobRecent;
import cn.bmob.im.db.BmobDB;

import com.easyplay.easygame.R;
import com.easyplay.easygame.adapter.MessageRecentAdapter;
import com.easyplay.easygame.view.ClearEditText;
import com.easyplay.easygame.view.DialogTips;

public class MessageActivity extends BaseActivity implements
    OnItemClickListener, OnItemLongClickListener {
  private static final String TAG = "MessageActivity";
  ClearEditText mClearEditText;

  ListView listview;

  MessageRecentAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);
    initActionBar(this.getResources().getString(R.string.message_acitvity));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    // TODO Auto-generated method stub
    listview = (ListView) findViewById(R.id.list);
    listview.setOnItemClickListener(this);
    listview.setOnItemLongClickListener(this);
    adapter = new MessageRecentAdapter(this, R.layout.item_conversation, BmobDB
        .create(this).queryRecents());
    listview.setAdapter(adapter);

    mClearEditText = (ClearEditText) findViewById(R.id.et_msg_search);
    mClearEditText.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
          int after) {

      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
  }

  /**
   * 删除会话 deleteRecent
   * 
   * @param @param recent
   * @return void
   * @throws
   */
  private void deleteRecent(BmobRecent recent) {
    adapter.remove(recent);
    BmobDB.create(this).deleteRecent(recent.getTargetid());
    BmobDB.create(this).deleteMessages(recent.getTargetid());
  }

  @Override
  protected void setListener() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
  }

  @Override
  public void onItemClick(AdapterView<?> arg0, View arg1, int position,
      long arg3) {
    // TODO Auto-generated method stub
    BmobRecent recent = adapter.getItem(position);
    // 重置未读消息
    BmobDB.create(this).resetUnread(recent.getTargetid());
    // 组装聊天对象
    BmobChatUser user = new BmobChatUser();
    user.setAvatar(recent.getAvatar());
    user.setNick(recent.getNick());
    user.setUsername(recent.getUserName());
    user.setObjectId(recent.getTargetid());
    Intent intent = new Intent(this, ChatActivity.class);
    intent.putExtra("user", user);
    startAnimActivity(intent);
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
      long arg3) {
    // TODO Auto-generated method stub
    BmobRecent recent = adapter.getItem(position);
    showDeleteDialog(recent);
    return true;
  }

  public void showDeleteDialog(final BmobRecent recent) {
    DialogTips dialog = new DialogTips(this, recent.getUserName(), "删除会话",
        "确定", true, true);
    // 设置成功事件
    dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int userId) {
        deleteRecent(recent);
      }
    });
    // 显示确认对话框
    dialog.show();
    dialog = null;
  }

  public void refresh() {
    try {
      this.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          adapter = new MessageRecentAdapter(MessageActivity.this,
              R.layout.item_conversation, BmobDB.create(MessageActivity.this)
                  .queryRecents());
          listview.setAdapter(adapter);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    refresh();
  }

}
