package com.uniquindio.finalproject.unievents;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Event implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private final Collection<City> cities;
    private String description;
    private EventType eventType;
    private String image;
    private LocalDate date;
    private String address;
    private Collection<Seat> seats;
  
    public Event(String name, String description, EventType eventType, String image, LocalDate date, String address, List<Seat> seats) {
      this.name = name;
      this.eventType = eventType;
      this.cities = new LinkedList<>();
      this.description = description;
      this.image = image;
      this.date = date;
      this.address = address;
      this.seats = seats;
    }
    
    // public void addSeats(Seat seat)
    // {
    //   //Exeption Fro the future!
    //   if(!existingSeat(seat).isPresent()) seats.add(seat);
    // }
    // private Optional<Seat> existingSeat(Seat seat){
    //     Predicate<Seat> condition = s-> s == seat;
    //     return seats.stream().filter(condition).findAny();
    // }
    public void setCity(City city)
    {
      //Exeption Fro the future!
      if(!cityValidation(city).isPresent()) cities.add(city);
    }
    private Optional<City> cityValidation(City city){
        Predicate<City> condition = c-> c == city;
        return cities.stream().filter(condition).findAny();
    }
    public String getName() {
      return name;
    }

  }
  