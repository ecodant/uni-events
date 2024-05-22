package com.uniquindio.finalproject.unievents;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient SimpleStringProperty name;
    private transient SimpleStringProperty description;
    private transient SimpleObjectProperty<EventType> eventType;
    private transient SimpleStringProperty image;
    private transient SimpleObjectProperty<LocalDate> date;
    private transient SimpleStringProperty address;
    private Collection<Seat> seats;

    public Event(String name, String description, EventType eventType, String image, LocalDate date, String address) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.eventType = new SimpleObjectProperty<>(eventType);
        this.image = new SimpleStringProperty(image);
        this.date = new SimpleObjectProperty<>(date);
        this.address = new SimpleStringProperty(address);
        this.seats = new LinkedList<>();;
    }

    public SimpleStringProperty nameProperty() { return name; }
    public SimpleStringProperty descriptionProperty() { return description; }
    public SimpleObjectProperty<EventType> eventTypeProperty() { return eventType; }
    public SimpleStringProperty imageProperty() { return image; }
    public SimpleObjectProperty<LocalDate> dateProperty() { return date; }
    public SimpleStringProperty addressProperty() { return address; }

    // Standard getter methods for serialization
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }
    public EventType getEventType() { return eventType.get(); }
    public String getImage() { return image.get(); }
    public LocalDate getDate() { return date.get(); }
    public String getAddress() { return address.get(); }
    public Collection<Seat> getSeats() { return seats; }
    
    public void addSeat(float price, short capacity, SeatType seatType) {
        Seat newSeat = new Seat(price, capacity, seatType);
        seats.add(newSeat);
    }
    // Custom serialization methods
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(getName());
        oos.writeObject(getDescription());
        oos.writeObject(getEventType());
        oos.writeObject(getImage());
        oos.writeObject(getDate());
        oos.writeObject(getAddress());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        name = new SimpleStringProperty((String) ois.readObject());
        description = new SimpleStringProperty((String) ois.readObject());
        eventType = new SimpleObjectProperty<>((EventType) ois.readObject());
        image = new SimpleStringProperty((String) ois.readObject());
        date = new SimpleObjectProperty<>((LocalDate) ois.readObject());
        address = new SimpleStringProperty((String) ois.readObject());
    }
}
