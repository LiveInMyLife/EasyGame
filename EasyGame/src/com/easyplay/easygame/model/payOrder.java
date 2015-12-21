package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class PayOrder extends BmobObject implements Serializable {
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

  public double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  private int buyNum;
  private Double total;
  private Double price;
  private String unit;
  private BmobUser customer;
  private BmobUser server;
  private String payOrderID;
  private int state;
  private ShopInfo serverShop;
  private String description;
}
