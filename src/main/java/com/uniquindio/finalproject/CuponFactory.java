
package com.uniquindio.finalproject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CuponFactory {
        private final static Map<CuponType, Cupon> cupons = new HashMap<>(){{
        put(CuponType.REGISTERED, new CuponRegistered((float) 0.15, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(1)));
        put(CuponType.FIRST_PURCHASE, new CuponFirstPurchase((float) 0.10, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(1)));

    }};
    public Cupon getCupon(CuponType cuponType) throws NoSuchFieldException{
        return cupons.get(cuponType);
    }
  }
  