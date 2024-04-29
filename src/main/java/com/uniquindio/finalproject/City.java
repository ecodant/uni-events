package com.uniquindio.finalproject;

import java.util.Collection;
import java.util.LinkedList;

public class City {
    private String name;
    private final Collection<Event> events;
    public City (String name){
        this.name = name;
        this.events = new LinkedList<>();
    }
}
