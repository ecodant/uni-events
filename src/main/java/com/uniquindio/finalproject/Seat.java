package com.uniquindio.finalproject;
public class Seat {

    private float price;
    private short capacity;
  
    public Seat(float price, short capacity) {
      this.price = price;
      this.capacity = capacity;
    }
  
    // Getters for all attributes
    public float getPrice() {
      return price;
    }
    public short getCapacity() {
        return capacity;
      }

  }
  