
package com.uniquindio.finalproject;

public class Purchase {

  private String id;
  private String location;
  private float valuePurchase;

  public Purchase(String id, String location, float valuePurchase) {
    this.id = id;
    this.location = location;
    this.valuePurchase = valuePurchase;
  }
  public String getId(){
    return this.id;
  }
  public String getLocation() {
    return location;
  }
  public float getValuePurchase() {
    return valuePurchase;
  }
  
  // public boolean hasSameId(Purchase otherPurchase) {
  //   return this.id.equals(otherPurchase.getId());
  // }

}