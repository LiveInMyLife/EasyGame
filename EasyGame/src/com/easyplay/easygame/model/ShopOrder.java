package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class ShopOrder extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;

  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public String getOrderLogo() {
    return orderLogo;
  }

  public void setOrderLogo(String orderLogo) {
    this.orderLogo = orderLogo;
  }

  public Integer getOrderSell() {
    return orderSell;
  }

  public void setOrderSell(Integer orderSell) {
    this.orderSell = orderSell;
  }

  public String getOrderDescription() {
    return orderDescription;
  }

  public void setOrderDescription(String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public Integer getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(Integer orderPrice) {
    this.orderPrice = orderPrice;
  }

  public String getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(String orderTime) {
    this.orderTime = orderTime;
  }

  private String orderName;
  private String orderLogo;
  private Integer orderSell;
  private String orderDescription;
  private Integer orderPrice;
  private String orderTime;

}
