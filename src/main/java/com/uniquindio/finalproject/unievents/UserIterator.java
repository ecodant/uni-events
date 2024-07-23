package com.uniquindio.finalproject.unievents;
import java.util.Collection;
import java.util.Iterator;

public class UserIterator implements UniEventIterator<User> {
    private Iterator<User> iterator;

    public UserIterator(Collection<User> users) {
        this.iterator = users.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public User next() {
        return iterator.next();
    }
}
