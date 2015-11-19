package com.easyplay.easygame.context;

import android.content.Context;

public class AppContext {
  private static AppContext instance;
  private static Context context;

  public static AppContext getInstance() {
    if (instance == null) {
      instance = new AppContext();
    }
    return instance;
  }

  public Context getContext() {
    return context;
  }

  public Context setContext(Context context1) {
    return context = context1;
  }
}
