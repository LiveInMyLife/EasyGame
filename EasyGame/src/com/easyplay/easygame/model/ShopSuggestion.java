package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class ShopSuggestion extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String userName;
  private String suggestionContent;
  private Float suggestionScore;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSuggestionContent() {
    return suggestionContent;
  }

  public void setSuggestionContent(String suggestionContent) {
    this.suggestionContent = suggestionContent;
  }

  public Float getSuggestionScore() {
    return suggestionScore;
  }

  public void setSuggestionScore(Float suggestionScore) {
    this.suggestionScore = suggestionScore;
  }

}
