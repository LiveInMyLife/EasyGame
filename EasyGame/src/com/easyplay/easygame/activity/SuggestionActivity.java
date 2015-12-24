package com.easyplay.easygame.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.ShopInfo;
import com.easyplay.easygame.model.ShopSuggestion;
import com.easyplay.easygame.tools.AppLog;

public class SuggestionActivity extends BaseActivity implements OnClickListener {
  private static final String TAG = "SuggestionActivity";
  private TextView suggestion_edit_score;
  private EditText suggestion_edit_et;
  private TextView suggestion_edit_limit;
  private SeekBar suggestion_eidt_progress;
  private RelativeLayout suggestion_send;

  private int score;
  private String suggestion_content;
  private ShopSuggestion suggestion;
  private ShopInfo tagShop;
  private final int lengthLimit = 140;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_suggestion);
    initActionBar(this.getResources().getString(R.string.suggestion_edit));
    bindViews();
    setListener();
    init();
  }

  @Override
  @SuppressLint("InflateParams")
  protected void bindViews() {
    suggestion_edit_score = (TextView) this
        .findViewById(R.id.suggestion_edit_score);
    suggestion_edit_et = (EditText) this.findViewById(R.id.suggestion_edit_et);
    suggestion_edit_limit = (TextView) this
        .findViewById(R.id.suggestion_edit_limit);
    suggestion_eidt_progress = (SeekBar) this
        .findViewById(R.id.suggestion_eidt_progress);
    suggestion_send = (RelativeLayout) this.findViewById(R.id.suggestion_send);
  }

  @Override
  protected void setListener() {
    suggestion_eidt_progress.setOnSeekBarChangeListener(sChangeListener);
    suggestion_send.setOnClickListener(this);
    suggestion_edit_et.addTextChangedListener(textWatcher);
  }

  @Override
  protected void init() {
    // TODO Auto-generated method stub
    tagShop = (ShopInfo) this.getIntent().getSerializableExtra("tag_shop");
    suggestion_eidt_progress.setMax(5);
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.suggestion_send:
      saveSuggestion();
      break;
    }

  }

  private void saveSuggestion() {
    if (checkInput()) {
      suggestion = new ShopSuggestion();
      suggestion.setAuther(BaseApplication.userManager.getCurrentUser());
      suggestion.setSuggestionContent(suggestion_content);
      suggestion.setSuggestionScore(score);
      AppLog.d(TAG, "userName:"
          + BaseApplication.userManager.getCurrentUser().getNick());
      suggestion.setUserName(BaseApplication.userManager.getCurrentUser()
          .getNick());
      suggestion.setTagShop(tagShop);
      suggestion.save(this, new SaveListener() {

        @Override
        public void onSuccess() {
          // TODO Auto-generated method stub
          showSuccessToast("^_^操作成功");
          SuggestionActivity.this.finish();
        }

        @Override
        public void onFailure(int code, String msg) {
          // TODO Auto-generated method stub
          AppLog.d(TAG, "code:" + code + "; msg:" + msg);
          showErrorToast("T_T操作失败，请检查网络后重试");
        }
      });
    }

  }

  private boolean checkInput() {
    suggestion_content = suggestion_edit_et.getEditableText().toString();
    if (score == 0) {
      showErrorToast("请赏个评分吧");
      return false;
    }
    if (tagShop == null) {
      showErrorToast("获取信息失败，请尝试重进此页面");
      return false;
    }
    return true;
  }

  OnSeekBarChangeListener sChangeListener = new OnSeekBarChangeListener() {
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      // TODO Auto-generated method stub
      score = seekBar.getProgress();
      suggestion_edit_score.setText(score + "分");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
      // TODO Auto-generated method stub
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
        boolean fromUser) {
      // TODO Auto-generated method stub

    }
  };

  TextWatcher textWatcher = new TextWatcher() {
    private CharSequence temp;
    private int selectionStart;
    private int selectionEnd;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
        int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      temp = s;
    }

    @Override
    public void afterTextChanged(Editable s) {
      int number = lengthLimit - s.length();
      suggestion_edit_limit.setText(s.length() + "/" + lengthLimit);
      selectionStart = suggestion_edit_et.getSelectionStart();
      selectionEnd = suggestion_edit_et.getSelectionEnd();
      if (temp.length() > lengthLimit) {
        s.delete(selectionStart - 1, selectionEnd);
        int tempSelection = selectionEnd;
        suggestion_edit_et.setText(s);
        suggestion_edit_et.setSelection(tempSelection);// 设置光标在最后
      }
    }
  };
}
