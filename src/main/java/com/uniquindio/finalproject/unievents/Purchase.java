package com.uniquindio.finalproject.unievents;

public class Purchase {

  private final String id;
  private final String seat;
  private final float valuePurchase;
  private final Cupon cupon;
  private Purchase(String id, String seat, float valuePurchase, Cupon cupon) {
    this.id = id;
    this.seat = seat;
    this.valuePurchase = valuePurchase;
    this.cupon = cupon;
  }

  public static class PurchaseBuilder {
    private String id;
    private String seat;
    private float valuePurchase;
    private Cupon cupon;

    public PurchaseBuilder idBill(String id) {
      this.id = id;
      return this;
    }

    public PurchaseBuilder seat(String seat) {
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

  public String getLocation() {
    return seat;
  }

  public float getValuePurchase() {
    return valuePurchase;
  }

  public Cupon getCupon() {
    return cupon;
  }
}
