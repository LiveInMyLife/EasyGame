package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class ChargeRecord extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;
  private BmobUser userId;
  private double chargeAmount;
  private int chargeState;

  public BmobUser getUserId() {
    return userId;
  }

  public void setUserId(BmobUser userId) {
    this.userId = userId;
  }

  public double getChargeAmount() {
    return chargeAmount;
  }

  public void setChargeAmount(double chargeAmount) {
    this.chargeAmount = chargeAmount;
  }

  public int getChargeState() {
    return chargeState;
  }

  public void setChargeState(int chargeState) {
    this.chargeState = chargeState;
  }

}
