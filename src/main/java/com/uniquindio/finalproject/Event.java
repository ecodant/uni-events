
package com.uniquindio.finalproject;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class Event {

    private String name;
    private String city;
    private String description;
    private EventType type;
    private String image;
    private LocalDate date;
    private String address;
    private final Collection<Seat> seats;
  
    public Event(String name, String city, String description, EventType type, String image, LocalDate date, String address, List<Seat> seats) {
      this.name = name;
      this.city = city;
      this.description = description;
      this.type = type;
      this.image = image;
      this.date = date;
      this.address = address;
      this.seats = seats;
    }
  
    // Getters for all attributes
    public String getName() {
      return name;
    }

  }
  