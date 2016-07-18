package com.github.jonathanxd.iutils.collection;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by jonathan on 18/07/16.
 */
public class EmptyWalkable implements Walkable {
    @Override
    public Object next() {
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkIndex() {
        return false;
    }

    @Override
    public void sort(Comparator c) {
    }

    @Override
    public void walkToEnd() {
    }

    @Override
    public Walkable newWithoutState() {
        return new EmptyWalkable();
    }

    @Override
    public void forEach(Consumer consumer) {
    }

    @Override
    public void forEach(Consumer consumer, Predicate until) {
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int computeSize() {
        return 0;
    }

    @Override
    public boolean hasCurrent() {
        return false;
    }

    @Override
    public Object getCurrent() {
        return null;
    }

    @Override
    public Walkable clone() {
        return new EmptyWalkable();
    }

    @Override
    public void resetIndex() {

    }

    @Override
    public Walkable distinct() {
        return new EmptyWalkable();
    }

    @Override
    public void distinctInternal() {

    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Walkable map(Function map) {
        return new EmptyWalkable();
    }

    @Override
    public void distinctInternal(Function function) {

    }

    @Override
    public Walkable distinct(Function function) {
        return new EmptyWalkable();
    }
}
