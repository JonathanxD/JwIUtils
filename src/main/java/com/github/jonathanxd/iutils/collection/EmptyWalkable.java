/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.jonathanxd.iutils.collection;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Empty walkable.
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
    public int getRemainingElementsAmount() {
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
