package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class TopicRecord extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private BmobUser operationUser;
  private Topic topicId;
  private int operationType;
  private double operationAmount;
  private int operationSelection;

  public BmobUser getOperationUser() {
    return operationUser;
  }

  public void setOperationUser(BmobUser operationUser) {
    this.operationUser = operationUser;
  }

  public Topic getTopicId() {
    return topicId;
  }

  public void setTopicId(Topic topicId) {
    this.topicId = topicId;
  }

  public int getOperationType() {
    return operationType;
  }

  public void setOperationType(int operationType) {
    this.operationType = operationType;
  }

  public double getOperationAmount() {
    return operationAmount;
  }

  public void setOperationAmount(double operationAmount) {
    this.operationAmount = operationAmount;
  }

  public int getOperationSelection() {
    return operationSelection;
  }

  public void setOperationSelection(int operationSelection) {
    this.operationSelection = operationSelection;
  }

}
