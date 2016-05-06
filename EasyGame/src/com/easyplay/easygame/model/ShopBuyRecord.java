package com.easyplay.easygame.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class ShopBuyRecord extends BmobObject implements Serializable {
  private static final long serialVersionUID = 1L;
  private String productPic;
  private double productPrice;
  private int productState;
  private int productStock;// 库存
  private String productName;

  public String getProductPic() {
    return productPic;
  }

  public void setProductPic(String productPic) {
    this.productPic = productPic;
  }

  public double getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(double productPrice) {
    this.productPrice = productPrice;
  }

  public int getProductState() {
    return productState;
  }

  public void setProductState(int productState) {
    this.productState = productState;
  }

  public int getProductStock() {
    return productStock;
  }

  public void setProductStock(int productStock) {
    this.productStock = productStock;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

}
