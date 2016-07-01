package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

public class Topic extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private BmobUser topicCreator;
  private String topicTitle;
  private String topicDescription;
  private String topicSelect1;
  private String topicSelect2;
  private double topicLeftAccount;
  private double topicRightAccount;
  private int topicType;
  private String topicTypeStr;
  private int topicState;
  private String topicStateStr;
  private BmobDate topicEndDate;

  public BmobUser getTopicCreator() {
    return topicCreator;
  }

  public void setTopicCreator(BmobUser topicCreator) {
    this.topicCreator = topicCreator;
  }

  public String getTopicTitle() {
    return topicTitle;
  }

  public void setTopicTitle(String topicTitle) {
    this.topicTitle = topicTitle;
  }

  public String getTopicDescription() {
    return topicDescription;
  }

  public void setTopicDescription(String topicDescription) {
    this.topicDescription = topicDescription;
  }

  public String getTopicSelect1() {
    return topicSelect1;
  }

  public void setTopicSelect1(String topicSelect1) {
    this.topicSelect1 = topicSelect1;
  }

  public String getTopicSelect2() {
    return topicSelect2;
  }

  public void setTopicSelect2(String topicSelect2) {
    this.topicSelect2 = topicSelect2;
  }

  public double getTopicLeftAccount() {
    return topicLeftAccount;
  }

  public void setTopicLeftAccount(double topicLeftAccount) {
    this.topicLeftAccount = topicLeftAccount;
  }

  public double getTopicRightAccount() {
    return topicRightAccount;
  }

  public void setTopicRightAccount(double topicRightAccount) {
    this.topicRightAccount = topicRightAccount;
  }

  public int getTopicType() {
    return topicType;
  }

  public void setTopicType(int topicType) {
    this.topicType = topicType;
  }

  public int getTopicState() {
    return topicState;
  }

  public void setTopicState(int topicState) {
    this.topicState = topicState;
  }

  public BmobDate getTopicEndDate() {
    return topicEndDate;
  }

  public void setTopicEndDate(BmobDate topicEndDate) {
    this.topicEndDate = topicEndDate;
  }

  public String getTopicTypeStr() {
    return topicTypeStr;
  }

  public void setTopicTypeStr(String topicTypeStr) {
    this.topicTypeStr = topicTypeStr;
  }

  public String getTopicStateStr() {
    return topicStateStr;
  }

  public void setTopicStateStr(String topicStateStr) {
    this.topicStateStr = topicStateStr;
  }

}
