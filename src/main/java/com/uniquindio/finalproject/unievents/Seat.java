package com.uniquindio.finalproject.unievents;

import java.io.Serializable;

public class Seat implements Serializable {
    // private static final long serialVersionUID = 1L;
    private SeatType seatType;
    private float price;
    private short capacity;
  
    public Seat(float price, short capacity, SeatType seatType) {
      this.price = price;
      this.capacity = capacity;
      this.seatType = seatType;
    }
  
    // Getters for all attributes
    public float getPrice() {
      return price;
    }
    public short getCapacity() {
        return capacity;
      }

  }
  