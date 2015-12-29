package com.easyplay.easygame.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.easyplay.easygame.R;
import com.easyplay.easygame.config.AppConstants;
import com.easyplay.easygame.context.BaseApplication;
import com.easyplay.easygame.model.Cash;
import com.easyplay.easygame.model.User;
import com.easyplay.easygame.tools.AppLog;
import com.easyplay.easygame.tools.ImageLoadOptions;
import com.easyplay.easygame.util.ActivityUtils;
import com.easyplay.easygame.util.PhotoUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人主页
 * 
 * @author chuwe1
 * 
 */
public class MineFragment extends Fragment implements OnClickListener {
  private Context mContext;
  private Toast mToast;

  private Cash cash;
  private TextView tv_cash;
  private TextView draw_money;
  ImageView iv_set_avator;
  LinearLayout layout_all;
  RelativeLayout layout_head;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mContext = this.getActivity().getApplicationContext();
    View view = inflater.inflate(R.layout.fragment_mine, null);
    tv_cash = (TextView) view.findViewById(R.id.tv_cash);
    draw_money = (TextView) view.findViewById(R.id.draw_money);
    iv_set_avator = (ImageView) view.findViewById(R.id.iv_set_avator);
    layout_all = (LinearLayout) view.findViewById(R.id.layout_all);
    layout_head = (RelativeLayout) view.findViewById(R.id.layout_head);
    layout_head.setOnClickListener(this);
    draw_money.setOnClickListener(this);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (BaseApplication.userManager.getCurrentUser() != null) {
      queryCashInfo();
    } else {
      ActivityUtils.toLoginActivity(mContext);
    }
  }

  public void queryCashInfo() {
    BmobQuery<Cash> query = new BmobQuery<Cash>();
    query.addWhereEqualTo("userId", BaseApplication.userManager
        .getCurrentUser().getObjectId());
    query.findObjects(mContext, new FindListener<Cash>() {
      @Override
      public void onSuccess(List<Cash> object) {
        // TODO Auto-generated method stub
        AppLog.d("MineFragment", "ShopInfo查询成功：记录条数：" + object.size());
        if (object.size() > 0) {
          for (Cash cashInfo : object) {
            cash = cashInfo;
            tv_cash.setText(cash.getUserAmount());
          }
        }
      }

      @Override
      public void onError(int code, String msg) {
        // TODO Auto-generated method stub
        AppLog.d("MineFragment", "查询失败：" + code + msg);
      }
    });
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.draw_money:
      break;
    case R.id.layout_head:
      showAvatarPop();
      break;
    case R.id.layout_nick:
      // startAnimActivity(UpdateInfoActivity.class);
      break;
    }
  }

  /**
   * 更新头像 refreshAvatar
   * 
   * @return void
   * @throws
   */
  private void refreshAvatar(String avatar) {
    if (avatar != null && !avatar.equals("")) {
      ImageLoader.getInstance().displayImage(avatar, iv_set_avator,
          ImageLoadOptions.getOptions());
    } else {
      iv_set_avator.setImageResource(R.drawable.default_head);
    }
  }

  RelativeLayout layout_choose;
  RelativeLayout layout_photo;
  PopupWindow avatorPop;

  public String filePath = "";

  @SuppressWarnings("deprecation")
  private void showAvatarPop() {
    View view = LayoutInflater.from(mContext).inflate(R.layout.pop_showavator,
        null);
    layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
    layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
    layout_photo.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
        layout_choose.setBackgroundColor(getResources().getColor(
            R.color.base_color_text_white));
        layout_photo.setBackgroundDrawable(getResources().getDrawable(
            R.drawable.pop_bg_press));
        File dir = new File(AppConstants.MyAvatarDir);
        if (!dir.exists()) {
          dir.mkdirs();
        }
        // 原图
        File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss")
            .format(new Date()));
        filePath = file.getAbsolutePath();// 获取相片的保存路径
        Uri imageUri = Uri.fromFile(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,
            AppConstants.REQUESTCODE_UPLOADAVATAR_CAMERA);
      }
    });
    layout_choose.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
        layout_photo.setBackgroundColor(getResources().getColor(
            R.color.base_color_text_white));
        layout_choose.setBackgroundDrawable(getResources().getDrawable(
            R.drawable.pop_bg_press));
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*");
        startActivityForResult(intent,
            AppConstants.REQUESTCODE_UPLOADAVATAR_LOCATION);
      }
    });

    avatorPop = new PopupWindow(view);
    avatorPop.setTouchInterceptor(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
          avatorPop.dismiss();
          return true;
        }
        return false;
      }
    });

    avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
    avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    avatorPop.setTouchable(true);
    avatorPop.setFocusable(true);
    avatorPop.setOutsideTouchable(true);
    avatorPop.setBackgroundDrawable(new BitmapDrawable());
    // 动画效果 从底部弹起
    avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
    avatorPop.showAtLocation(layout_all, Gravity.BOTTOM, 0, 0);
  }

  /**
   * @Title: startImageAction
   * @return void
   * @throws
   */
  private void startImageAction(Uri uri, int outputX, int outputY,
      int requestCode, boolean isCrop) {
    Intent intent = null;
    if (isCrop) {
      intent = new Intent("com.android.camera.action.CROP");
    } else {
      intent = new Intent(Intent.ACTION_GET_CONTENT, null);
    }
    intent.setDataAndType(uri, "image/*");
    intent.putExtra("crop", "true");
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    intent.putExtra("outputX", outputX);
    intent.putExtra("outputY", outputY);
    intent.putExtra("scale", true);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    intent.putExtra("return-data", true);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    intent.putExtra("noFaceDetection", true); // no face detection
    startActivityForResult(intent, requestCode);
  }

  Bitmap newBitmap;
  boolean isFromCamera = false;// 区分拍照旋转
  int degree = 0;

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
    case AppConstants.REQUESTCODE_UPLOADAVATAR_CAMERA:// 拍照修改头像
      if (resultCode == Activity.RESULT_OK) {
        if (!Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED)) {
          ShowToast("SD不可用");
          return;
        }
        isFromCamera = true;
        File file = new File(filePath);
        degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
        Log.i("life", "拍照后的角度：" + degree);
        startImageAction(Uri.fromFile(file), 200, 200,
            AppConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
      }
      break;
    case AppConstants.REQUESTCODE_UPLOADAVATAR_LOCATION:// 本地修改头像
      if (avatorPop != null) {
        avatorPop.dismiss();
      }
      Uri uri = null;
      if (data == null) {
        return;
      }
      if (resultCode == Activity.RESULT_OK) {
        if (!Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED)) {
          ShowToast("SD不可用");
          return;
        }
        isFromCamera = false;
        uri = data.getData();
        startImageAction(uri, 200, 200,
            AppConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
      } else {
        ShowToast("照片获取失败");
      }

      break;
    case AppConstants.REQUESTCODE_UPLOADAVATAR_CROP:// 裁剪头像返回
      // TODO sent to crop
      if (avatorPop != null) {
        avatorPop.dismiss();
      }
      if (data == null) {
        // Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
        return;
      } else {
        // saveCropAvator(data);
      }
      // 初始化文件路径
      filePath = "";
      // 上传头像
      uploadAvatar();
      break;
    default:
      break;

    }
  }

  private void uploadAvatar() {
    BmobLog.i("头像地址：" + path);
    final BmobFile bmobFile = new BmobFile(new File(path));
    bmobFile.upload(mContext, new UploadFileListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        String url = bmobFile.getFileUrl(mContext);
        // 更新BmobUser对象
        updateUserAvatar(url);
      }

      @Override
      public void onProgress(Integer arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onFailure(int arg0, String msg) {
        // TODO Auto-generated method stub
        ShowToast("头像上传失败：" + msg);
      }
    });
  }

  private void updateUserAvatar(final String url) {
    User u = new User();
    u.setAvatar(url);
    updateUserData(u, new UpdateListener() {
      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        ShowToast("头像更新成功！");
        // 更新头像
        refreshAvatar(url);
      }

      @Override
      public void onFailure(int code, String msg) {
        // TODO Auto-generated method stub
        ShowToast("头像更新失败：" + msg);
      }
    });
  }

  String path;

  /**
   * 保存裁剪的头像
   * 
   * @param data
   */
  private void saveCropAvator(Intent data) {
    Bundle extras = data.getExtras();
    if (extras != null) {
      Bitmap bitmap = extras.getParcelable("data");
      Log.i("life", "avatar - bitmap = " + bitmap);
      if (bitmap != null) {
        bitmap = PhotoUtil.toRoundCorner(bitmap, 10);
        if (isFromCamera && degree != 0) {
          bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
        }
        iv_set_avator.setImageBitmap(bitmap);
        // 保存图片
        String filename = new SimpleDateFormat("yyMMddHHmmss")
            .format(new Date()) + ".png";
        path = AppConstants.MyAvatarDir + filename;
        PhotoUtil.saveBitmap(AppConstants.MyAvatarDir, filename, bitmap, true);
        // 上传头像
        if (bitmap != null && bitmap.isRecycled()) {
          bitmap.recycle();
        }
      }
    }
  }

  private void updateUserData(User user, UpdateListener listener) {
    User current = BaseApplication.userManager.getCurrentUser(User.class);
    user.setObjectId(current.getObjectId());
    user.update(mContext, listener);
  }

  public void ShowToast(String msg) {

    if (mToast == null) {
      mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
    } else {
      mToast.setText(msg);
    }
    mToast.show();
  }

  /**
   * 修改昵称 updateInfo
   * 
   * @Title: updateInfo
   * @return void
   * @throws
   */
  private void updateInfo(String nick) {
    final User user = BaseApplication.userManager.getCurrentUser(User.class);
    User u = new User();
    u.setNick(nick);
    u.setHight(110);
    u.setObjectId(user.getObjectId());
    u.update(mContext, new UpdateListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        final User c = BaseApplication.userManager.getCurrentUser(User.class);
        ShowToast("修改成功:" + c.getNick() + ",height = " + c.getHight());
      }

      @Override
      public void onFailure(int arg0, String arg1) {
        // TODO Auto-generated method stub
        ShowToast("onFailure:" + arg1);
      }
    });
  }
}
