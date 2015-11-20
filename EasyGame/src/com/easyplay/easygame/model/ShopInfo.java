package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class ShopInfo extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getShopLogo() {
    return shopLogo;
  }

  public void setShopLogo(String shopLogo) {
    this.shopLogo = shopLogo;
  }

  public String getShopSell() {
    return shopSell;
  }

  public void setShopSell(String shopSell) {
    this.shopSell = shopSell;
  }

  public String getShopGame() {
    return shopGame;
  }

  public void setShopGame(String shopGame) {
    this.shopGame = shopGame;
  }

  public String getShopScore() {
    return shopScore;
  }

  public void setShopScore(String shopScore) {
    this.shopScore = shopScore;
  }

  public String getShopDescription() {
    return shopDescription;
  }

  public void setShopDescription(String shopDescription) {
    this.shopDescription = shopDescription;
  }

  private String shopName;
  private String shopLogo;
  private String shopSell;
  private String shopGame;
  private String shopScore;
  private String shopDescription;

}
