package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;
import java.util.List;

public class Admin {

    private static Admin instance;  // Holds the single instance
    private String username;
    private String password;
  
    private Admin(String username, String password) {
      this.username = username;
      this.password = password;
    }
  
    public static Admin getInstance(String username, String password) {
      if (instance == null) {
        synchronized (Admin.class) {
          if (instance == null) {
            instance = new Admin(username, password);
          }
        }
      }
      return instance;
    }

    public Cupon createCupon(CuponType cuponType) throws NoSuchFieldException {
      CuponFactory cuponFactory = new CuponFactory();
      return cuponFactory.createCupon(cuponType);
    }
  
    public Event createEvent(String name, String description, EventType type, String image, LocalDate date, String address, List<Seat> seats) {
        return new Event(name, description, type, image, date, address, seats);
    }

    public String getUsername() {
      return username;
    }
  
    public void setUsername(String username) {
      this.username = username;
    }

  }
  