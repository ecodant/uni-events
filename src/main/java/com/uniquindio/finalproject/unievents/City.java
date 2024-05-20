package com.uniquindio.finalproject.unievents;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class City implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private final Collection<Event> events;
    public City (String name){
        this.name = name;
        this.events = new LinkedList<>();
    }
}
