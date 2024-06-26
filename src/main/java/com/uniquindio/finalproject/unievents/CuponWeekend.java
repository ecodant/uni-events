
package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;


public class CuponWeekend extends Cupon {
    public CuponWeekend(float discountValue, LocalDate dateOfExpiry, LocalDate dateOfEmission) {
        super(discountValue, dateOfExpiry, dateOfEmission);  
    }
    @Override
    protected CuponType getType() {
      return CuponType.WEEKEND;
    }
}