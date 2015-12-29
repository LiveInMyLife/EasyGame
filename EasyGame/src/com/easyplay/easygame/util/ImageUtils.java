package com.easyplay.easygame.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class ImageUtils {
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
