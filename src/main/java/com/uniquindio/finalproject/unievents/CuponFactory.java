package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;

public class CuponFactory {

    public Cupon createCupon(CuponType cuponType) {
        switch (cuponType) {
            case REGISTERED:
                return new CuponRegistered((float) 0.15, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(1));
            case FIRST_PURCHASE:
                return new CuponFirstPurchase((float) 0.10, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(1));
            default:
                throw new IllegalArgumentException("Invalid CuponType: " + cuponType);
        }
    }
}
