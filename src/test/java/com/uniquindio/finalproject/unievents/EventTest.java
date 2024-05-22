package com.uniquindio.finalproject.unievents;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("Event1", "Description1", EventType.CONCERT, "image.jpg", LocalDate.now(), "Address1", new LinkedList<>());
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        String filePath = "event.ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(event);
        }
        Event deserializedEvent;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            deserializedEvent = (Event) in.readObject();
        }
        assertNotNull(deserializedEvent);
        assertEquals(event.getName(), deserializedEvent.getName());
        assertEquals(event.getDescription(), deserializedEvent.getDescription());
        assertEquals(event.getEventType(), deserializedEvent.getEventType());
        assertEquals(event.getImage(), deserializedEvent.getImage());
        assertEquals(event.getDate(), deserializedEvent.getDate());
        assertEquals(event.getAddress(), deserializedEvent.getAddress());
    }
}
