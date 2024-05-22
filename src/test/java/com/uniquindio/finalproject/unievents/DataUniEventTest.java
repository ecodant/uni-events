package com.uniquindio.finalproject.unievents;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class DataUniEventTest {

    private DataUniEvent dataUniEvent;
    private Event testEvent;
    private User testUser;

    @BeforeEach
    void setUp() {
        dataUniEvent = new DataUniEvent();
        testEvent = new Event("Event1", "Description1", EventType.CONCERT, "image.jpg", LocalDate.now(), "Address1", new LinkedList<>());
        testUser = new User(1, "John", "Doe", "123456789", "john.doe@example.com", "password123");
    }

    @Test
    void testAddEvent() {
        dataUniEvent.addEvent(testEvent);
        assertTrue(dataUniEvent.getEvents().contains(testEvent));
    }

    @Test
    void testAddUser() {
        dataUniEvent.addUser(testUser);
        assertTrue(dataUniEvent.getUsers().contains(testUser));
    }

    @Test
    void testDuplicateEvent() {
        dataUniEvent.addEvent(testEvent);
        dataUniEvent.addEvent(testEvent);  // Adding the same event again
        assertEquals(1, dataUniEvent.getEvents().size());
    }

    @Test
    void testDuplicateUser() {
        dataUniEvent.addUser(testUser);
        dataUniEvent.addUser(testUser);  // Adding the same user again
        assertEquals(1, dataUniEvent.getUsers().size());
    }


    @Test
    void testSerialization() {
        String filePath = "dataUniEvent.ser";
        dataUniEvent.addEvent(testEvent);
        dataUniEvent.addUser(testUser);
        dataUniEvent.saveToFile(filePath);
        DataUniEvent loadedData = DataUniEvent.loadFromFile(filePath);
        assertNotNull(loadedData);
        assertEquals(1, loadedData.getEvents().size());
        assertEquals(1, loadedData.getUsers().size());
    }
}
