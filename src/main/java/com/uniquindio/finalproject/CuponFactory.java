package com.uniquindio.finalproject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CuponFactory {

    // private final static Map<CuponType, Cupon> cupons;

    // static { // Static initializer block
    //     cupons = new HashMap<>();
    //     cupons.put(CuponType.REGISTERED, new CuponRegistered((float) 0.15, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(1)));
    //     cupons.put(CuponType.FIRST_PURCHASE, new CuponFirstPurchase((float) 0.10, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(1)));
    // }

    // public Cupon getCupon(CuponType cuponType) {
    //     return cupons.get(cuponType); // Return existing cupon, no need for try/catch
    // }

    // Alternative approach for creating new cupons (if needed):

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
