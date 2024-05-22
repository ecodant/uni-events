package com.uniquindio.finalproject.unievents;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "John", "Doe", "123456789", "john.doe@example.com", "password123");
    }

    @Test
    void testAddCupon() {
        assertFalse(user.getCupons().isEmpty());
    }


    @Test
    void testFirstCuponValidation() {
        user.firstCuponValidation();
        assertEquals(2, user.getCupons().size());
    }

}
