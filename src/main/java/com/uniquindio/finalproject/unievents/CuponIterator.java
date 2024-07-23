package com.uniquindio.finalproject.unievents;
import java.util.Collection;
import java.util.Iterator;

public class CuponIterator implements UniEventIterator<Cupon> {
    private Iterator<Cupon> iterator;

    public CuponIterator(Collection<Cupon> cupons) {
        this.iterator = cupons.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Cupon next() {
        return iterator.next();
    }
}
