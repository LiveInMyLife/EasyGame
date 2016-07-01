package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

public class Activity extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  public BmobUser getStartUser() {
    return startUser;
  }

  public void setStartUser(BmobUser startUser) {
    this.startUser = startUser;
  }

  public String getActivityDescription() {
    return activityDescription;
  }

  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }

  public String getLeftSide() {
    return leftSide;
  }

  public void setLeftSide(String leftSide) {
    this.leftSide = leftSide;
  }

  public String getRightSide() {
    return rightSide;
  }

  public void setRightSide(String rightSide) {
    this.rightSide = rightSide;
  }

  public double getLeftAccount() {
    return leftAccount;
  }

  public void setLeftAccount(double leftAccount) {
    this.leftAccount = leftAccount;
  }

  public double getRightAccount() {
    return rightAccount;
  }

  public void setRightAccount(double rightAccount) {
    this.rightAccount = rightAccount;
  }

  public int getActivityType() {
    return activityType;
  }

  public void setActivityType(int activityType) {
    this.activityType = activityType;
  }

  public int getActivityState() {
    return activityState;
  }

  public void setActivityState(int activityState) {
    this.activityState = activityState;
  }

  public BmobDate getActivityOverTime() {
    return activityOverTime;
  }

  public void setActivityOverTime(BmobDate activityOverTime) {
    this.activityOverTime = activityOverTime;
  }

  public String getActivityTitle() {
    return activityTitle;
  }

  public void setActivityTitle(String activityTitle) {
    this.activityTitle = activityTitle;
  }

  private BmobUser startUser;
  private String activityTitle;
  private String activityDescription;
  private String leftSide;
  private String rightSide;
  private double leftAccount;
  private double rightAccount;
  private int activityType;
  private int activityState;
  private BmobDate activityOverTime;

}
