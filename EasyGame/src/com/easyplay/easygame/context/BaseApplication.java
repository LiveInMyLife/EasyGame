package com.easyplay.easygame.context;

import android.app.Application;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.Bmob;

import com.easyplay.easygame.config.Config;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseApplication extends Application {

  private static BaseApplication instance;
  public static BmobUserManager userManager;

  @Override
  public void onCreate() {
    super.onCreate();
    Bmob.initialize(this, Config.applicationId);
    userManager = BmobUserManager.getInstance(this);
    instance = this;

    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
    ImageLoader.getInstance().init(config);
  }

  public static BaseApplication getInstance() {
    return instance;
  }
}
