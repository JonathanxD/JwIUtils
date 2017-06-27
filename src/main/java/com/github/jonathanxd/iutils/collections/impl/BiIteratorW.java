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
 * An BiIterator which allows iterating over two {@link IteratorW Iterators}.
 *
 * @param <E> Element type.
 */
public class BiIteratorW<E> implements IteratorW<E> {

    private final IteratorW<E> first;
    private final IteratorW<E> second;
    private IteratorW<E> current;

    public BiIteratorW(IteratorW<E> first, IteratorW<E> second) {
        this.first = first;
        this.second = second;
        this.current = first;
    }

    @Override
    public Iterator<E> asJavaIterator() {
        return new WBackedJavaIterator<>(this);
    }

    @Override
    public boolean hasNext() {
        if (this.current == first && !this.current.hasNext())
            this.current = second;

        return this.current.hasNext();
    }

    @Override
    public E next() {
        if (!this.hasNext())
            throw new NoSuchElementException();

        if (this.current == first && !this.current.hasNext())
            this.current = second;

        return this.current.next();
    }

    @Override
    public IteratorW<E> copy() {
        return new BiIteratorW<>(this.first, this.second);
    }
}
