package com.uniquindio.finalproject.unievents;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1234, "Juanda", "Gordillo", "123456789", "juandolopeor@gmail.com", "mypassword");
    }

    @Test
    void testFirstCuponValidation() {
        user.firstCuponValidation();
        assertEquals(2, user.getCupons().size());
    }

}
