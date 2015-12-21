package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class ShopSuggestion extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String userName;
  private String suggestionContent;
  private int suggestionScore;
  private BmobUser auther;
  private ShopInfo tagShop;

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

  public int getSuggestionScore() {
    return suggestionScore;
  }

  public void setSuggestionScore(int suggestionScore) {
    this.suggestionScore = suggestionScore;
  }

  public BmobUser getAuther() {
    return auther;
  }

  public void setAuther(BmobUser auther) {
    this.auther = auther;
  }

  public ShopInfo getTagShop() {
    return tagShop;
  }

  public void setTagShop(ShopInfo tagShop) {
    this.tagShop = tagShop;
  }

}
