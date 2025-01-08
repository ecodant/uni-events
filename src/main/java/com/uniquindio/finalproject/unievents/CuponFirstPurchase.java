
package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;


public class CuponFirstPurchase extends Cupon {
    public CuponFirstPurchase(LocalDate dateOfExpiry, LocalDate dateOfEmission) {
        super((float) 0.10, dateOfExpiry, dateOfEmission);  
    }
    @Override
    protected CuponType getType() {
      return CuponType.FIRST_PURCHASE;
    }
}