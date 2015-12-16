package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class payOrder extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private ShopOrder tagOrder;

  public ShopOrder getTagOrder() {
    return tagOrder;
  }

  public void setTagOrder(ShopOrder tagOrder) {
    this.tagOrder = tagOrder;
  }

  public int getBuyNum() {
    return buyNum;
  }

  public void setBuyNum(int buyNum) {
    this.buyNum = buyNum;
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public BmobUser getCustomer() {
    return customer;
  }

  public void setCustomer(BmobUser customer) {
    this.customer = customer;
  }

  public BmobUser getServer() {
    return server;
  }

  public void setServer(BmobUser server) {
    this.server = server;
  }

  public String getPayOrderID() {
    return payOrderID;
  }

  public void setPayOrderID(String payOrderID) {
    this.payOrderID = payOrderID;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public ShopInfo getServerShop() {
    return serverShop;
  }

  public void setServerShop(ShopInfo serverShop) {
    this.serverShop = serverShop;
  }

  private int buyNum;
  private float total;
  private BmobUser customer;
  private BmobUser server;
  private String payOrderID;
  private int state;
  private ShopInfo serverShop;
}
