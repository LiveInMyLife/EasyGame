package com.example.myview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyplay.easygame.R;

public class ShopTabActivity extends Activity {

  private ViewPager viewPager;
  private ImageView imageView;
  private final List<View> lists = new ArrayList<View>();
  private MyAdapter myAdapter;
  private Bitmap cursor;
  private int offSet;
  private int currentItem;
  private final Matrix matrix = new Matrix();
  private int bmWidth;
  private Animation animation;
  private TextView textView1;
  private TextView textView2;
  private TextView textView3;
  private int textWidth;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_detail);

    imageView = (ImageView) findViewById(R.id.cursor);
    textView1 = (TextView) findViewById(R.id.textView1);
    textView2 = (TextView) findViewById(R.id.textView2);
    textView3 = (TextView) findViewById(R.id.textView3);
    textView1
        .setTextColor(getResources().getColor(R.color.text_color_sky_blue));

    lists.add(getLayoutInflater().inflate(R.layout.layout_shoptab1, null));
    lists.add(getLayoutInflater().inflate(R.layout.layout_shoptab2, null));
    lists.add(getLayoutInflater().inflate(R.layout.layout_shoptab3, null));

    initeCursor();

    myAdapter = new MyAdapter(lists);
    viewPager = (ViewPager) findViewById(R.id.viewPager);
    viewPager.setAdapter(myAdapter);
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
        case 0:

          textView1.setTextColor(getResources().getColor(
              R.color.text_color_sky_blue));
          textView2.setTextColor(getResources().getColor(
              R.color.text_color_black));
          textView3.setTextColor(getResources().getColor(
              R.color.text_color_black));
          if (currentItem == 1) {
            animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
          } else if (currentItem == 2) {
            animation = new TranslateAnimation(offSet * 4 + 2 * bmWidth, 0, 0,
                0);
          }
          break;
        case 1:
          textView1.setTextColor(getResources().getColor(
              R.color.text_color_black));
          textView2.setTextColor(getResources().getColor(
              R.color.text_color_sky_blue));
          textView3.setTextColor(getResources().getColor(
              R.color.text_color_black));
          if (currentItem == 0) {
            animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
          } else if (currentItem == 2) {
            // TODO
            animation = new TranslateAnimation(2 * offSet + 2 * bmWidth, offSet
                * 2 + bmWidth, 0, 0);
          }
          break;
        case 2:
          textView1.setTextColor(getResources().getColor(
              R.color.text_color_black));
          textView2.setTextColor(getResources().getColor(
              R.color.text_color_black));
          textView3.setTextColor(getResources().getColor(
              R.color.text_color_sky_blue));
          if (currentItem == 0) {
            animation = new TranslateAnimation(0, 4 * offSet + 2 * bmWidth, 0,
                0);
          } else if (currentItem == 1) {
            animation = new TranslateAnimation(offSet * 2 + bmWidth, 4 * offSet
                + 2 * bmWidth, 0, 0);
          }
        }
        currentItem = arg0;

        animation.setDuration(500);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);

      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

      }
    });

    textView1.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(0);
      }
    });

    textView2.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(1);
      }
    });

    textView3.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(2);
      }
    });
  }

  private void initeCursor() {
    WindowManager wm = (WindowManager) this
        .getSystemService(Context.WINDOW_SERVICE);

    int width = wm.getDefaultDisplay().getWidth();
    textWidth = width / 3;
    cursor = BitmapFactory.decodeResource(getResources(),
        R.drawable.document_btn_blue_bg);
    Log.v("main",
        "wight=" + cursor.getWidth() + ";height=" + cursor.getHeight());
    cursor = resizeImage(cursor, textWidth, 5);
    bmWidth = cursor.getWidth();
    imageView.setImageBitmap(cursor);

    DisplayMetrics dm;
    dm = getResources().getDisplayMetrics();

    offSet = (dm.widthPixels - 3 * bmWidth) / 6;
    matrix.setTranslate(offSet, 0);
    // matrix.setScale(5, textWidth);
    imageView.setImageMatrix(matrix);
    currentItem = 0;
  }

  public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
    Bitmap BitmapOrg = bitmap;
    int width = BitmapOrg.getWidth();
    int height = BitmapOrg.getHeight();
    int newWidth = w;
    int newHeight = h;

    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;

    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight);
    // if you want to rotate the Bitmap
    // matrix.postRotate(45);
    Log.v("main",
        "wight=" + BitmapOrg.getWidth() + ";height=" + BitmapOrg.getHeight()
            + "w=" + w + ";h=" + h);
    Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height,
        matrix, true);
    return resizedBitmap;
  }
}