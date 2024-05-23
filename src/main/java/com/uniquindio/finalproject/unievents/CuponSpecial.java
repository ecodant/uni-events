package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;

public class CuponSpecial extends Cupon {
    public CuponSpecial(float discountValue, LocalDate dateOfExpiry, LocalDate dateOfEmission) {
        super(discountValue, dateOfExpiry, dateOfEmission);  
    }
    @Override
    protected CuponType getType() {
      return CuponType.SPECIAL;
    }
    
}