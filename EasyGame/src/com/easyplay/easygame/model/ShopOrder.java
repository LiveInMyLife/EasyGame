package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

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

  public Double getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(Double orderPrice) {
    this.orderPrice = orderPrice;
  }

  public BmobUser getServer() {
    return server;
  }

  public void setServer(BmobUser server) {
    this.server = server;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getTimeNeed() {
    return timeNeed;
  }

  public void setTimeNeed(int timeNeed) {
    this.timeNeed = timeNeed;
  }

  private String orderName;
  private String orderLogo;
  private Integer orderSell;
  private String orderDescription;
  private Double orderPrice;
  private String orderTime;
  private Integer startTime;
  private Integer endTime;
  private ShopInfo orderShop;
  private BmobUser server;
  private int type;
  private int timeNeed;
}
