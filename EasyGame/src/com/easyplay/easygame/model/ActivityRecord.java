package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class ActivityRecord extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private BmobUser playerId;
  private int selection;
  private double partAmount;
  private Activity activityId;
  private int recordState;

  public BmobUser getPlayerId() {
    return playerId;
  }

  public void setPlayerId(BmobUser playerId) {
    this.playerId = playerId;
  }

  public int getSelection() {
    return selection;
  }

  public void setSelection(int selection) {
    this.selection = selection;
  }

  public double getPartAmount() {
    return partAmount;
  }

  public void setPartAmount(double partAmount) {
    this.partAmount = partAmount;
  }

  public Activity getActivityId() {
    return activityId;
  }

  public void setActivityId(Activity activityId) {
    this.activityId = activityId;
  }

  public int getRecordState() {
    return recordState;
  }

  public void setRecordState(int recordState) {
    this.recordState = recordState;
  }

}
