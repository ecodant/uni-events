package com.uniquindio.finalproject.unievents;

import java.io.Serializable;

public class Purchase implements Serializable{

  private final String id;
  private final SeatType seat;
  private final float valuePurchase;

  private Purchase(String id, SeatType seat, float valuePurchase) {
    this.id = id;
    this.seat = seat;
    this.valuePurchase = valuePurchase;

  }

  public static class PurchaseBuilder {
    private String id;
    private SeatType seat;
    private float valuePurchase;

    public PurchaseBuilder idBill(String id) {
      this.id = id;
      return this;
    }

    public PurchaseBuilder seat(SeatType seat) {
      this.seat = seat;
      return this;
    }

    public PurchaseBuilder valuePurchase(float valuePurchase) {
      this.valuePurchase = valuePurchase;
      return this;
    }



    public Purchase build() {
      if (id == null || seat == null || valuePurchase <= 0) {
        throw new IllegalArgumentException("Missing required attributes: id, seat, and valuePurchase");
      }
      return new Purchase(id, seat, valuePurchase);
    }
  }

  public String getId() {
    return id;
  }

  public SeatType getLocation() {
    return seat;
  }

  public float getValuePurchase() {
    return valuePurchase;
  }

}
