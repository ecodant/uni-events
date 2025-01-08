package com.uniquindio.finalproject.unievents;
import java.util.Collection;
import java.util.Iterator;

public class EventIterator implements UniEventIterator<Event> {
    private Iterator<Event> iterator;

    public EventIterator(Collection<Event> events) {
        this.iterator = events.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Event next() {
        return iterator.next();
    }
}
