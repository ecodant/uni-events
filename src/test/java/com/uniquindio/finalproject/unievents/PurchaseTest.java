package com.uniquindio.finalproject.unievents;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PurchaseTest {

    @Test
    void testPurchaseBuilder() {
        String id = "ABC123";
        SeatType seat = SeatType.CONCERT;
        float valuePurchase = 50.0f;

        Purchase purchase = new Purchase.PurchaseBuilder()
                                    .idBill(id)
                                    .seat(seat)
                                    .valuePurchase(valuePurchase)
                                    .build();

        assertNotNull(purchase);
        assertEquals(id, purchase.getId());
        assertEquals(seat, purchase.getLocation());
        assertEquals(valuePurchase, purchase.getValuePurchase());

        assertThrows(IllegalArgumentException.class, () -> {
            new Purchase.PurchaseBuilder()
                .idBill(null)
                .seat(null)
                .valuePurchase(0)
                .build();
        });
    }

    // Add more test cases as needed
}
