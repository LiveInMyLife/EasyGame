/**
 * AlertMessage.java
 *
 * @author tianli
 *
 * @date 2011-4-7
 *
 * Copyright 2011 NetEase. All rights reserved.
 */
package com.easyplay.easygame.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.easyplay.easygame.R;

/**
 * @author tianli
 * 
 */
public class AlertMessage {

  public static void show(Context context, String message) {
    show(context, message, false);
  }

  public static void show(Context context, int resId) {
    if (context == null) {
      return;
    }
    show(context, context.getString(resId), false);
  }

  public static void show(Context context, String message, boolean isLong) {
    try {
      if (Tools.isEmpty(message)) {
        return;
      }
      TextView textView;

      textView = new TextView(context);
      int padding = Tools.getPixelByDip(context, 15);
      textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
          LayoutParams.WRAP_CONTENT));
      textView.setBackgroundResource(R.drawable.toast_bg);
      textView.setPadding(padding, padding, padding, padding);
      textView.setText(message);
      textView.setGravity(Gravity.CENTER);
      textView.setTextColor(context.getResources().getColor(
          R.color.color_std_white));

      Toast toast = new Toast(context);
      textView.setText(message);
      toast.setView(textView);
      toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
      toast.show();
    } catch (Exception e) {
    }
  }

  public static void show(Context context, int resId, boolean isLong) {
    if (context == null) {
      return;
    }
    show(context, context.getString(resId), isLong);
  }

  public static void show(Context context, View view, boolean isLong) {
    try {
      Toast toast = new Toast(context);
      toast.setView(view);
      toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
      toast.show();
    } catch (Exception e) {
    }
  }

  public static void show(Context context, View view, boolean isLong,
      boolean isCenter) {
    try {
      Toast toast = new Toast(context);
      toast.setView(view);
      toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
      if (isCenter) {
        toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
      }
      toast.show();
    } catch (Exception e) {
    }
  }

  public static void show(Context context, String message, boolean isLong,
      int bitmapId) {
    try {
      if (Tools.isEmpty(message)) {
        return;
      }
      TextView textView;

      textView = new TextView(context);
      int padding = Tools.getPixelByDip(context, 15);
      textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
          LayoutParams.WRAP_CONTENT));
      textView.setBackgroundResource(R.drawable.toast_bg);
      textView.setPadding(padding, padding, padding, padding);
      textView.setText(message);
      textView.setTextSize(18.0f);
      textView.setGravity(Gravity.CENTER);
      textView.setTextColor(context.getResources().getColor(
          R.color.color_std_white));
      textView.setCompoundDrawablesWithIntrinsicBounds(context.getResources()
          .getDrawable(bitmapId), null, null, null);
      textView.setCompoundDrawablePadding(6);

      Toast toast = new Toast(context);
      textView.setText(message);
      toast.setView(textView);
      toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
      toast.show();
    } catch (Exception e) {
    }
  }

  // public static void showLivehMessag(final Context context,
  // ArrayList<LiveMessage> events) {
  // try {
  // LinearLayout layout;
  // layout = new LinearLayout(context);
  // layout.setOrientation(LinearLayout.VERTICAL);
  // layout.setBackgroundDrawable(context.getResources().getDrawable(
  // R.drawable.live_match_message_bg));
  // WindowManager wm = (WindowManager) context
  // .getSystemService(Context.WINDOW_SERVICE);
  // int width = wm.getDefaultDisplay().getWidth() * 7 / 10;
  //
  // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
  // LayoutParams.WRAP_CONTENT);
  // params.gravity = Gravity.CENTER;
  // layout.setLayoutParams(params);
  //
  // final Dialog toast = new Dialog(context, R.style.AlertDialogStyle);
  // Window dialogWindow = toast.getWindow();
  // WindowManager.LayoutParams lp = dialogWindow.getAttributes();
  // lp.width = LayoutParams.WRAP_CONTENT;
  // lp.height = LayoutParams.WRAP_CONTENT;
  // lp.y = Tools.getPixelByDip(context, 120);
  // dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND
  // | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
  // dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
  // toast.setContentView(layout);
  // toast.setCanceledOnTouchOutside(true);
  //
  // layout.removeAllViews();
  // int i = 0;
  // for (LiveMessage messag : events) {
  // if (TextUtils.isEmpty(messag.getMessage())
  // || TextUtils.isEmpty(messag.getUrl())) {
  // continue;
  // }
  //
  // TextView textView = new TextView(context);
  // textView.setText(messag.getMessage());
  // textView.setTextSize(15);
  // textView.setTextColor(context.getResources().getColor(R.color.black));
  // LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
  // LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
  // textParam.gravity = Gravity.CENTER_HORIZONTAL | Gravity.LEFT;
  // textParam.setMargins(10, 4, 10, 4);
  // textView.setLayoutParams(textParam);
  // layout.addView(textView);
  // textView.setTag(messag.getUrl());
  // textView.setOnClickListener(new OnClickListener() {
  // @Override
  // public void onClick(View v) {
  // if (v.getTag() instanceof String) {
  // String url = (String) v.getTag();
  // if (!TextUtils.isEmpty(url)) {
  // Uri uri = Uri.parse(url);
  // if (uri != null && BetUtils.isMyScheme(uri.getScheme())) {
  // if (toast != null && toast.isShowing()) {
  // toast.cancel();
  // }
  // Intent intent = BetUtils.getIntentFromURI(context, uri);
  // context.startActivity(intent);
  // }
  // }
  // }
  // }
  // });
  // i++;
  // if (i > 4) {
  // break;
  // }
  // }
  // if (i > 0) {
  // toast.show();
  // }
  // Handler handler = new Handler();
  // handler.postDelayed(new Runnable() {
  // @Override
  // public void run() {
  // if (toast != null && toast.isShowing()) {
  // try {
  // toast.cancel();
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
  // }
  // }, 3000);
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
}
