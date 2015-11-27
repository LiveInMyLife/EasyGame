package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class MyOrder extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String shopName;

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getOrderDescription() {
    return orderDescription;
  }

  public void setOrderDescription(String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public int getOrderState() {
    return orderState;
  }

  public void setOrderState(int orderState) {
    this.orderState = orderState;
  }

  public int getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(int orderNum) {
    this.orderNum = orderNum;
  }

  public int getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(int orderPrice) {
    this.orderPrice = orderPrice;
  }

  public int getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(int orderTotal) {
    this.orderTotal = orderTotal;
  }

  public String getOrderUnit() {
    return orderUnit;
  }

  public void setOrderUnit(String orderUnit) {
    this.orderUnit = orderUnit;
  }

  private String orderDescription;
  private int orderState;
  private int orderNum;
  private int orderPrice;
  private int orderTotal;
  private String orderUnit;

}
