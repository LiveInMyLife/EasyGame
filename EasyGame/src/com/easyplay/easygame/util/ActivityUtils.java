package com.easyplay.easygame.util;

import android.content.Context;
import android.content.Intent;

import com.easyplay.easygame.activity.LoginActivity;

public class ActivityUtils {
  public static void toLoginActivity(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

}
