package com.easyplay.easygame.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyplay.easygame.R;
import com.easyplay.easygame.model.Topic;
import com.easyplay.easygame.tools.Tools;
import com.easyplay.easygame.view.CustomDialog;
import com.easyplay.easygame.view.CustomDialog.AnimationDirection;

public class TopicListAdapter extends BaseAdapter {
  private final Context mContext;
  private final List<Topic> topicList;
  private ViewHolder holder;
  private CustomDialog exitDialog;

  class ViewHolder {
    ImageView activity_logo;
    TextView topic_title;
    TextView topic_description;
    TextView topic_positive;
    TextView topic_negative;
    TextView topic_time_limit;
    TextView topic_state;
  }

  public TopicListAdapter(Context context, List<Topic> data) {
    mContext = context;
    topicList = data;
  }

  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return topicList.size();
  }

  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return topicList.get(position);
  }

  @Override
  public long getItemId(int position) {
    // TODO Auto-generated method stub
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // TODO Auto-generated method stub
    holder = null;
    Topic topic = topicList.get(position);
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(
          R.layout.item_activity_list, null);
      holder.activity_logo = (ImageView) convertView
          .findViewById(R.id.item_topic_logo);
      holder.topic_title = (TextView) convertView
          .findViewById(R.id.item_topic_title);
      holder.topic_description = (TextView) convertView
          .findViewById(R.id.item_topic_content);
      holder.topic_time_limit = (TextView) convertView
          .findViewById(R.id.item_topic_end_time);
      holder.topic_state = (TextView) convertView
          .findViewById(R.id.item_topic_state);
      holder.topic_positive = (TextView) convertView
          .findViewById(R.id.item_topic_positive);
      holder.topic_negative = (TextView) convertView
          .findViewById(R.id.item_topic_negative);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (topic != null) {
      if (!Tools.isEmpty(topic.getTopicTitle())) {
        holder.topic_title.setText(topic.getTopicTitle());
      } else {
        holder.topic_title.setText("");
      }

      if (!Tools.isEmpty(topic.getTopicTitle())) {
        holder.topic_description.setVisibility(View.VISIBLE);
        holder.topic_description.setText(topic.getTopicTitle());
      } else {
        holder.topic_description.setVisibility(View.GONE);
      }

      if (!Tools.isEmpty(topic.getTopicSelect1())) {
        holder.topic_positive.setText(topic.getTopicSelect1());
      } else {
        holder.topic_positive.setText("--");
      }

      if (!Tools.isEmpty(topic.getTopicSelect2())) {
        holder.topic_negative.setText(topic.getTopicSelect2());
      } else {
        holder.topic_negative.setText("--");
      }

      if (!Tools.isEmpty(topic.getTopicStateStr())) {
        holder.topic_state.setText(topic.getTopicStateStr());
      }

      if (topic.getTopicState() == 0) {
        holder.topic_positive.setClickable(true);
        holder.topic_negative.setClickable(true);
        holder.topic_positive.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub

          }

        });

        holder.topic_negative.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub

          }

        });
      } else {
        holder.topic_positive.setClickable(false);
        holder.topic_negative.setClickable(false);
      }
    }

    return convertView;
  }

  private void select(int whitch) {
    switch (whitch) {
    case 0:
      break;
    case 1:
      break;
    }
  }

  private void initExitDialog() {
    exitDialog = new CustomDialog.Builder(mContext).setGravity(Gravity.CENTER)
        .setAnimationDirection(AnimationDirection.HORIZONAL)
        .setCancledOnTouchOutside(false).createDialog();
    exitDialog.setContentView(R.layout.dialog_topic_attend);
    TextView btnConfirm, btnCancle, selection;
    EditText attendAmount;

    btnConfirm = (TextView) exitDialog.findViewById(R.id.see_un_pay_order);
    btnCancle = (TextView) exitDialog.findViewById(R.id.order_un_pay_cancel);
    selection = (TextView) exitDialog.findViewById(R.id.order_un_pay_cancel);
    attendAmount = (EditText) exitDialog.findViewById(R.id.order_un_pay_cancel);

    btnExitConfirm.setText(getResources().getString(R.string.exit_confirm));
    btnExitCancel.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub

      }

    });
    btnExitConfirm.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub

      }

    });
  }
}
