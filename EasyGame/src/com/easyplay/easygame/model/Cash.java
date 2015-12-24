package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Cash extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String userId;
  private String userAmount;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserAmount() {
    return userAmount;
  }

  public void setUserAmount(String userAmount) {
    this.userAmount = userAmount;
  }

}
