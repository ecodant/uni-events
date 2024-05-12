
package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;


public class CuponFirstPurchase extends Cupon {
    public CuponFirstPurchase(float discountValue, LocalDate dateOfExpiry, LocalDate dateOfEmission) {
        super(discountValue, dateOfExpiry, dateOfEmission);  
      }
}