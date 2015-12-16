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

  public String getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(String orderTime) {
    this.orderTime = orderTime;
  }

  public Integer getStartTime() {
    return startTime;
  }

  public void setStartTime(Integer startTime) {
    this.startTime = startTime;
  }

  public Integer getEndTime() {
    return endTime;
  }

  public void setEndTime(Integer endTime) {
    this.endTime = endTime;
  }

  public ShopInfo getOrderShop() {
    return orderShop;
  }

  public void setOrderShop(ShopInfo orderShop) {
    this.orderShop = orderShop;
  }

  public Float getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(Float orderPrice) {
    this.orderPrice = orderPrice;
  }

  private String orderName;
  private String orderLogo;
  private Integer orderSell;
  private String orderDescription;
  private Float orderPrice;
  private String orderTime;
  private Integer startTime;
  private Integer endTime;
  private ShopInfo orderShop;

}
