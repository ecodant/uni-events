package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;

public class CuponFactory {

    public Cupon createCupon(CuponType cuponType, float discountValue, LocalDate dateOfExpiry) {
        switch (cuponType) {
            case REGISTERED:
                return new CuponRegistered(LocalDate.now(), dateOfExpiry);
            case FIRST_PURCHASE:
                return new CuponFirstPurchase(LocalDate.now(), dateOfExpiry);
            case WEEKEND:
                return new CuponWeekend(discountValue, LocalDate.now(), dateOfExpiry);
            case BLACKFRIDAY:
                return new CuponBlackFriday(discountValue, LocalDate.now(), dateOfExpiry);
            case SPECIAL:
                return new CuponSpecial(discountValue, LocalDate.now(), dateOfExpiry);
            default:
                throw new IllegalArgumentException("Invalid CuponType: " + cuponType);
        }
    }
}
