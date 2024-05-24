package com.uniquindio.finalproject.unievents;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1234, "Juanda", "Gordillo", "123456789", "juandolopeor@gmail.com", "mypassword");
    }

    @Test
    void testRawPurchase() {
        SeatType seatType = SeatType.BLEACHER;
        float purchaseValue = 100.0f;

        Purchase purchase = user.rawPurchase(seatType, purchaseValue);
        assertNotNull(purchase);
        assertEquals(seatType, purchase.getLocation());
        assertEquals(purchaseValue, purchase.getValuePurchase());
    }

    @Test
    void testCuponPurchase() {
        SeatType seatType = SeatType.VIP;
        float purchaseValue = 200.0f;
        float cuponValue = 0.1f; // 10% 

        Purchase purchase = user.cuponPurchase(seatType, purchaseValue, cuponValue);
        assertNotNull(purchase);
        assertEquals(seatType, purchase.getLocation());
        assertEquals(purchaseValue - (purchaseValue * cuponValue), purchase.getValuePurchase());
    }

    @Test
    void testAddPurchase() {
        SeatType seatType = SeatType.VIP;
        float purchaseValue = 200.0f;
        Purchase purchase = new Purchase.PurchaseBuilder().idBill("12345").seat(seatType).valuePurchase(purchaseValue).build();

        user.addPurchase(purchase);
        Collection<Purchase> purchases = user.getLogPurchase();
        assertTrue(purchases.contains(purchase));
    }

    @Test
    void testSearchCupon() {
        float discountValue = 0.15f; // 15% 
        LocalDate dateOfExpiry = LocalDate.now().plusDays(30);

        Optional<Cupon> cupon = user.searchCupon(discountValue, dateOfExpiry);
        assertTrue(cupon.isPresent());
        assertEquals(discountValue, cupon.get().getValue());
        assertEquals(dateOfExpiry, cupon.get().getDateOfExpiry());
    }

    @Test
    void testRemoveCupon() {
        float discountValue = 0.15f; // 15% 

        user.removeCupon(discountValue);
        List<Float> discountValues = user.getAllDiscountValues();
        assertFalse(discountValues.contains(discountValue));
    }

}
