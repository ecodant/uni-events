package com.uniquindio.finalproject.unievents;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminTest {

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = Admin.getInstance();
    }

    @Test
    void testCreateEvent() {
        String name = "Test Event";
        String description = "This is a test event";
        EventType type = EventType.FESTIVAL;
        String image = "test_image.jpg";
        LocalDate date = LocalDate.now();
        String address = "123 Test St";
        String city = "Test City";

        Event event = admin.createEvent(name, description, type, image, date, address, city);
        assertNotNull(event);
        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
        assertEquals(type, event.getEventType());
        assertEquals(image, event.getImage());
        assertEquals(date, event.getDate());
        assertEquals(address, event.getAddress());
        assertEquals(city, event.getCity());
    }

    // Add more test cases as needed
}
