package com.uniquindio.finalproject;

import java.time.LocalDate;
// import java.util.Date;

public abstract class Cupon {

    private final float discountValue;
    private final LocalDate dateOfExpiry;
    private final LocalDate dateOfEmission;
  
    public Cupon(float discountValue, LocalDate dateOfEmission, LocalDate dateOfExpiry) {
      // Validate input values (optional)
      if (discountValue < 0) {
        throw new IllegalArgumentException("Coupon value cannot be negative.");
      }

      this.discountValue = discountValue;
      this.dateOfExpiry = dateOfExpiry;
      this.dateOfEmission = dateOfEmission;
    }
  
    // Getters for attributes
    public float getValue() {
      return discountValue;
    }
  
    public LocalDate getDateOfExpiry() {
      return dateOfExpiry;
    }
  
    public LocalDate getDateOfEmission() {
      return dateOfEmission;
    }

  }
  