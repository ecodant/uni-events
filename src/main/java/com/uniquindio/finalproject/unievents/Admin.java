package com.uniquindio.finalproject.unievents;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class Admin {

    private static Admin instance;  // Holds the single instance
    private String username;
    private String password;
  
    private Admin() {
      this.username = "admin@unieventos.com";
      this.password = "admin123";
    }
  
    public static Admin getInstance() {
      if (instance == null) {
        synchronized (Admin.class) {
          if (instance == null) {
            instance = new Admin();
          }
        }
      }
      return instance;
    }

    public Cupon createCupon(CuponType cuponType, float discountValue, LocalDate dateOfExpiry) throws NoSuchFieldException {
      CuponFactory cuponFactory = new CuponFactory();
      return cuponFactory.createCupon(cuponType, discountValue, dateOfExpiry);
    }
  
    public Event createEvent(String name, String description, EventType type, String image, LocalDate date, String address) {
        return new Event(name, description, type, image, date, address);
    }

    public String getUsername() {
      return username;
    }
  
    public void setUsername(String username) {
      this.username = username;
    }

  }
  