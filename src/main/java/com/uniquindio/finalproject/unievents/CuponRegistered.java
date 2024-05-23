
package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;


public class CuponRegistered extends Cupon {
    public CuponRegistered(LocalDate dateOfExpiry, LocalDate dateOfEmission) {
        super((float)0.15, dateOfExpiry, dateOfEmission);  
    }
    @Override
    protected CuponType getType() {
      return CuponType.REGISTERED;
    }
}