
package com.uniquindio.finalproject;

public class Purchase {

  private String id;
  private String seat;
  private float valuePurchase;

  public Purchase(String id, String seat, float valuePurchase) {
    this.id = id;
    this.seat = seat;
    this.valuePurchase = valuePurchase;
  }
  public String getId(){
    return this.id;
  }
  public String getLocation() {
    return seat;
  }
  public float getValuePurchase() {
    return valuePurchase;
  }
  
  // public boolean hasSameId(Purchase otherPurchase) {
  //   return this.id.equals(otherPurchase.getId());
  // }

}