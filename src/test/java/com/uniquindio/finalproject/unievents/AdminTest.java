package com.uniquindio.finalproject.unievents;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class AdminTest {

    @Test
    void testSingletonInstance() {
        Admin instance1 = Admin.getInstance();
        Admin instance2 = Admin.getInstance();
        assertSame(instance1, instance2);  // Verify both references point to the same instance
    }

    @Test
    void testCreateEvent() {
        Admin admin = Admin.getInstance();
        Event event = admin.createEvent("Event1", "Description1", EventType.CONCERT, "image.jpg", LocalDate.now(), "Address1", new LinkedList<>());
        assertNotNull(event);
        assertEquals("Event1", event.getName());
    }

    @Test
    void testCreateCupon() {
        Admin admin = Admin.getInstance();
        try {
            Cupon cupon = admin.createCupon(CuponType.REGISTERED);
            assertNotNull(cupon);
        } catch (NoSuchFieldException e) {
            fail("Cupon creation failed");
        }
    }
}
