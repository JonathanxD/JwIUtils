/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
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
package com.github.jonathanxd.iutils.collections.impl;

import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An BiIterator switch between iterators when an position is reached.
 *
 * @param <E> Element type.
 */
public class BiSwitchingIteratorW<E> implements IteratorW<E> {

    private final IteratorW<? extends E> first;
    private final IteratorW<? extends E> second;
    private final int positionSwitch;
    private IteratorW<? extends E> current;
    private int pos = -1;

    /**
     * Creates a bi switching iterator.
     *
     * @param first          First iterator.
     * @param second         Second iterator.
     * @param positionSwitch Position where this iterator should switch to {@code second}, this
     *                       iterator only switches back when the end of {@code second} is reached.
     */
    public BiSwitchingIteratorW(IteratorW<? extends E> first, IteratorW<? extends E> second, int positionSwitch) {
        this.first = first;
        this.second = second;
        this.current = first;
        this.positionSwitch = positionSwitch;
    }

    @Override
    public Iterator<E> asJavaIterator() {
        return new WBackedJavaIterator<>(this);
    }

    @Override
    public boolean hasNext() {

        if (this.current == this.second && !this.current.hasNext())
            this.current = this.first;

        return this.current.hasNext();
    }

    @Override
    public E next() {
        if (!this.hasNext())
            throw new NoSuchElementException();

        ++this.pos;

        if (this.pos == positionSwitch) {
            this.current = second;
        }

        return this.current.next();
    }

    @Override
    public IteratorW<E> copy() {
        return new BiSwitchingIteratorW<>(this.first, this.second, this.positionSwitch);
    }
}
