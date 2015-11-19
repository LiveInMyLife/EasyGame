package com.easyplay.easygame.tools;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.easyplay.easygame.BuildConfig;
import com.easyplay.easygame.R;
import com.easyplay.easygame.context.AppContext;

public class Tools {
  private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6',
      '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static boolean isEmpty(CharSequence string) {
    if (string == null || string.length() == 0) {
      return true;
    }
    return false;
  }

  public static String getMD5(String message) {
    String digest = "";
    try {
      byte[] bytes = getMD5Bytes(message);
      digest = toHexString2(bytes);
      digest = digest.toLowerCase(Locale.CHINA);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return digest;
  }

  public static byte[] getMD5Bytes(String message) {
    byte[] digest = null;
    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(message.getBytes("UTF-8"));
      digest = algorithm.digest();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return digest;
  }

  public static String toHexString2(byte[] b) {
    /*
     * String str = new String(b); System.out.println(str); try { //b =
     * str.getBytes("UTF-8"); } catch (UnsupportedEncodingException e) { // TODO
     * Auto-generated catch block e.printStackTrace(); }
     */
    StringBuilder sb = new StringBuilder(b.length * 2);
    for (int i = 0; i < b.length; i++) {
      sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
      sb.append(HEX_DIGITS[b[i] & 0x0f]);
    }
    return sb.toString();
  }

  /**
   * convert dip to pixels
   */
  public static int getPixelByDip(Context context, int dip) {
    return (int) (context.getResources().getDisplayMetrics().density * dip + 0.5f);
  }

  public static int getPixelBySp(Context context, int sp) {
    return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity);
  }

  /**
   * get the width of the device screen
   * 
   * @param context
   * @return
   */
  public static int getScreenWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  public static int getScreenWidth() {
    return getScreenWidth(AppContext.getInstance().getContext());
  }

  /**
   * get the height of the device screen
   * 
   * @param context
   * @return
   */
  public static int getScreenHeight(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  public static int getScreenHeight() {
    return getScreenHeight(AppContext.getInstance().getContext());
  }

  /**
   * get the density of the device screen
   * 
   * @param context
   * @return
   */
  public static float getScreenDensity(Context context) {
    return context.getResources().getDisplayMetrics().density;
  }

  /**
   * dip to px
   * 
   * @param context
   * @param px
   * @return
   */
  public static int dip2px(Context context, float px) {
    final float scale = getScreenDensity(context);
    return (int) (px * scale + 0.5);
  }

  public static int dip2px(float px) {
    return dip2px(AppContext.getInstance().getContext(), px);
  }

  /**
   * 将px值转换为sp值，保证文字大小不变
   * 
   * @param pxValue
   * @param fontScale
   *          （DisplayMetrics类中属性scaledDensity）
   * @return
   */
  public static int px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   * 
   * @param spValue
   * @param fontScale
   *          （DisplayMetrics类中属性scaledDensity）
   * @return
   */
  public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }

  public static String getChannel(Context context) {
    ApplicationInfo appinfo = context.getApplicationInfo();
    String sourceDir = appinfo.sourceDir;
    String ret = "";
    ZipFile zipfile = null;
    try {
      zipfile = new ZipFile(sourceDir);
      Enumeration<?> entries = zipfile.entries();
      while (entries.hasMoreElements()) {
        ZipEntry entry = ((ZipEntry) entries.nextElement());
        String entryName = entry.getName();
        if (entryName.startsWith("META-INF") && entryName.contains("channel_")) {
          ret = entryName;
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (zipfile != null) {
        try {
          zipfile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    String[] split = ret.split("_");
    if (split != null && split.length >= 2) {
      return ret.substring(split[0].length() + 1);

    } else {

      return "";
    }
  }

  public static String getModifiedTimeTextNew(Context context, Long time) {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
    return format.format(new Date(time));

  }

  public static HashMap<String, String> parseParams(Uri uri) {
    if (uri == null) {
      return new HashMap<String, String>();
    }
    HashMap<String, String> temp = new HashMap<String, String>();
    Set<String> keys = getQueryParameterNames(uri);
    for (String key : keys) {
      temp.put(key, uri.getQueryParameter(key));
    }
    return temp;
  }

  public static Set<String> getQueryParameterNames(Uri uri) {
    String query = uri.getEncodedQuery();
    if (query == null) {
      return Collections.emptySet();
    }

    Set<String> names = new LinkedHashSet<String>();
    int start = 0;
    do {
      int next = query.indexOf('&', start);
      int end = (next == -1) ? query.length() : next;

      int separator = query.indexOf('=', start);
      if (separator > end || separator == -1) {
        separator = end;
      }

      String name = query.substring(start, separator);
      names.add(URLDecoder.decode(name));

      start = end + 1;
    } while (start < query.length());

    return Collections.unmodifiableSet(names);
  }

  public static boolean isSdkVersionGreaterThan10() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  public static void showToastDebug(int tipResId) {
    Context context = AppContext.getInstance().getContext();
    if (BuildConfig.DEBUG) {
      Toast.makeText(context, context.getString(tipResId), Toast.LENGTH_SHORT)
          .show();
    }
  }

  public static void showToastDebug(String tip) {
    Context context = AppContext.getInstance().getContext();
    if (BuildConfig.DEBUG) {
      Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }
  }

  public static void showToast(Context context, int tipResId) {
    Toast.makeText(context, context.getString(tipResId), Toast.LENGTH_SHORT)
        .show();
  }

  public static void showToast(Context context, String tip) {
    Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
  }

  private static void showToast(Context context, int imgResId, String tip) {
    Toast toast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
    LinearLayout toastLayout = (LinearLayout) toast.getView();
    toastLayout.setOrientation(LinearLayout.VERTICAL);
    ImageView imageView = new ImageView(context);
    imageView.setImageResource(imgResId);
    imageView.setPadding(0, 0, 0, Tools.dip2px(context, 10));
    toastLayout.addView(imageView, 0);
    toast.show();
  }

  public static void showSuccessToast(Context context, String tip) {
    showToast(context, R.drawable.toast_success, tip);
  }

  public static void showSuccessToast(Context context, int tipResId) {
    showToast(context, R.drawable.toast_success, context.getString(tipResId));
  }

  public static void showErrorToast(Context context, String tip) {
    showToast(context, R.drawable.toast_error, tip);
  }

  public static void showErrorToast(Context context, int tipResId) {
    showToast(context, R.drawable.toast_error, context.getString(tipResId));
  }

  public static boolean isDouble(String str) {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static void initListViewHeight(ListView listView, Adapter adapter) {
    initListViewHeight(listView, adapter, 0);
  }

  public static void initListViewHeight(ListView listView, Adapter adapter,
      int paddingOrMarginHeight) {
    int totalHeight = paddingOrMarginHeight;
    for (int i = 0; i < adapter.getCount(); i++) {
      View listItem = adapter.getView(i, null, listView);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }
    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight
        + (listView.getDividerHeight() * (adapter.getCount() - 1));
    listView.setLayoutParams(params);
  }

  public static void getScreenArgs(Activity activity) {
    DisplayMetrics metric = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
    int width = metric.widthPixels;
    int height = metric.heightPixels;
    float density = metric.density;
    int densityDpi = metric.densityDpi;
    Log.e("screen args", width + "*" + height + "," + densityDpi + "dpi");
  }

  public static int getInternetState(Context context) {
    int state = -1;
    ConnectivityManager connectMgr = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo mobNetInfo = connectMgr
        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    NetworkInfo wifiNetInfo = connectMgr
        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {// unconnect
      // network
      state = -1;
    } else if (wifiNetInfo.isConnected()) {// connect network
      state = 0;
    } else if (mobNetInfo.isConnected()) {
      state = 1;
    }
    return state;
  }

  // 判断网络链接是否可用
  public static boolean isNetAvailable(Context context) {
    ConnectivityManager manager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    return (info != null && info.isAvailable());
  }

  public static String getCurVersion() {
    PackageInfo pi = null;
    Context context = AppContext.getInstance().getContext();
    try {
      pi = context.getPackageManager().getPackageInfo(context.getPackageName(),
          0);
      String version = pi.versionName;
      if (version.equals("1.4")) {
        version = "1.4.1";
      }
      return version;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String NicknameCut(String nickname) {
    String pattern = "@";
    Pattern pat = Pattern.compile(pattern);
    String[] rs = pat.split(nickname);
    if (rs.length > 0) {
      return rs[0];
    } else {
      return nickname;
    }
  }

  public static String checkString(String data) {
    return data == null ? "" : data;
  }
}
