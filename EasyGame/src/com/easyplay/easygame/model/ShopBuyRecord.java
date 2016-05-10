package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class ShopBuyRecord extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;
  private BmobUser buyUser;
  private String deliveryAddress;
  private ShopProduct buyProduct;
  private String buyProductName;
  private String buyProductPic;
  private String buyPrice;

  public BmobUser getBuyUser() {
    return buyUser;
  }

  public void setBuyUser(BmobUser buyUser) {
    this.buyUser = buyUser;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public ShopProduct getBuyProduct() {
    return buyProduct;
  }

  public void setBuyProduct(ShopProduct buyProduct) {
    this.buyProduct = buyProduct;
  }

  public String getBuyProductName() {
    return buyProductName;
  }

  public void setBuyProductName(String buyProductName) {
    this.buyProductName = buyProductName;
  }

  public String getBuyProductPic() {
    return buyProductPic;
  }

  public void setBuyProductPic(String buyProductPic) {
    this.buyProductPic = buyProductPic;
  }

  public String getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(String buyPrice) {
    this.buyPrice = buyPrice;
  }

}
