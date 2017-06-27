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
package com.github.jonathanxd.iutils.collections.impl.predef;

import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorW2<E> implements IteratorW<E> {

    protected final E element1;
    protected final E element2;
    protected int next = 0;

    public IteratorW2(E element1, E element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    @Override
    public Iterator<E> asJavaIterator() {
        return new WBackedJavaIterator<>(this);
    }

    @Override
    public boolean hasNext() {
        return this.next < 2;
    }

    @Override
    public E next() {
        if (!this.hasNext())
            throw new NoSuchElementException();

        if(this.next == 0) {
            this.next = 1;
            return this.element1;
        } else if(this.next == 1) {
            this.next = 2;
            return this.element2;
        }

        throw new NoSuchElementException();
    }

    @Override
    public IteratorW<E> copy() {
        IteratorW2<E> eIteratorW2 = new IteratorW2<>(this.element1, this.element2);

        eIteratorW2.next = this.next;

        return eIteratorW2;
    }
}
