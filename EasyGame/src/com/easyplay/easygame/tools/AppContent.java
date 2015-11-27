package com.easyplay.easygame.tools;

public class AppContent {
  public static final int ORDER_STATE_FINISH = 0;
  public static final int ORDER_STATE_GOING = 1;
  public static final int ORDER_STATE_ORDERED = 2;

  public static String getOrderStateString(int state) {
    String result = "";
    switch (state) {
    case ORDER_STATE_FINISH:
      return "已完成";
    case ORDER_STATE_GOING:
      return "进行中";
    case ORDER_STATE_ORDERED:
      return "已预约";
    default:
      return "";
    }
  }
}
