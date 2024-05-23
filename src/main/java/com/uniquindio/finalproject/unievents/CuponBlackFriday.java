package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;

public class CuponBlackFriday extends Cupon {
    public CuponBlackFriday(float discountValue, LocalDate dateOfExpiry, LocalDate dateOfEmission) {
        super(discountValue, dateOfExpiry, dateOfEmission);  
    }

    @Override
    protected CuponType getType() {
      return CuponType.BLACKFRIDAY;
    }
    
}