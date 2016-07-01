package com.easyplay.easygame.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.easyplay.easygame.R;

public class CustomDialog extends Dialog {
  protected static final int DEFAULT_GRAVITY = Gravity.BOTTOM;

  protected static final AnimationDirection DEFAULT_ANIMATION_DIRECTION = AnimationDirection.VERTICLE;

  public CustomDialog(Context context, int gravity, float marginVerticle,
      AnimationDirection animationDirection, boolean cancledOnTouchOutside) {
    super(context, R.style.CustomDialog);
    init(gravity, marginVerticle, animationDirection, cancledOnTouchOutside);
  }

  private void init(int gravity, float marginVerticle,
      AnimationDirection animationDirection, boolean cancledOnTouchOutside) {
    setCanceledOnTouchOutside(true);
    Window dialogWindow = getWindow();
    if (animationDirection == AnimationDirection.VERTICLE) {
      dialogWindow.setWindowAnimations(R.style.DialogVerticleWindowAnim);
    } else if (animationDirection == AnimationDirection.SCALEOUT) {
      dialogWindow.setWindowAnimations(R.style.DialogWindowScaleOutAnim);
    } else {
      dialogWindow.setWindowAnimations(R.style.DialogHorizonalWindowAnim);
    }
    dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
    LayoutParams layoutParams = dialogWindow.getAttributes();
    layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    layoutParams.gravity = gravity;
    layoutParams.verticalMargin = marginVerticle;
    dialogWindow.setAttributes(layoutParams);
    setCanceledOnTouchOutside(cancledOnTouchOutside);
  }

  public enum AnimationDirection {
    HORIZONAL, VERTICLE, SCALEOUT
  }

  public static class Builder {

    protected Context context;
    protected int gravity = DEFAULT_GRAVITY;
    protected AnimationDirection animationDirection = DEFAULT_ANIMATION_DIRECTION;
    protected float marginVerticle;
    protected boolean cancledOnTouchOutside = true;

    public Builder(Context context) {
      super();
      this.context = context;
    }

    public Builder setGravity(int gravity) {
      this.gravity = gravity;
      return this;
    }

    public Builder setAnimationDirection(AnimationDirection animationDirection) {
      this.animationDirection = animationDirection;
      return this;
    }

    public Builder setMarginVerticle(float marginVerticle) {
      this.marginVerticle = marginVerticle;
      return this;
    }

    public Builder setCancledOnTouchOutside(boolean cancledOnTouchOutside) {
      this.cancledOnTouchOutside = cancledOnTouchOutside;
      return this;
    }

    public CustomDialog createDialog() {
      return new CustomDialog(context, gravity, marginVerticle,
          animationDirection, cancledOnTouchOutside);
    }

  }

  public static abstract class DialogListener {

    public abstract void onConfirmClick();

    public abstract void onCancelClick();

  }

}
