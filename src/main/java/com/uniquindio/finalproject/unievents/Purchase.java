package com.uniquindio.finalproject.unievents;

public class Purchase {

  private final String id;
  private final Seat seat;
  private final float valuePurchase;
  private final Cupon cupon;
  private Purchase(String id, Seat seat, float valuePurchase, Cupon cupon) {
    this.id = id;
    this.seat = seat;
    this.valuePurchase = valuePurchase;
    this.cupon = cupon;
  }

  public static class PurchaseBuilder {
    private String id;
    private Seat seat;
    private float valuePurchase;
    private Cupon cupon;

    public PurchaseBuilder idBill(String id) {
      this.id = id;
      return this;
    }

    public PurchaseBuilder seat(Seat seat) {
      this.seat = seat;
      return this;
    }

    public PurchaseBuilder valuePurchase(float valuePurchase) {
      this.valuePurchase = valuePurchase;
      return this;
    }

    public PurchaseBuilder cupon(Cupon cupon) {
      this.cupon = cupon;
      return this;
    }

    public Purchase build() {
      if (id == null || seat == null || valuePurchase <= 0) {
        throw new IllegalArgumentException("Missing required attributes: id, seat, and valuePurchase");
      }
      return new Purchase(id, seat, valuePurchase, cupon);
    }
  }

  public String getId() {
    return id;
  }

  public Seat getLocation() {
    return seat;
  }

  public float getValuePurchase() {
    return valuePurchase;
  }

  public Cupon getCupon() {
    return cupon;
  }
}
