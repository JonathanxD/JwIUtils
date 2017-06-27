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

import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.ListW;
import com.github.jonathanxd.iutils.collections.SetW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterable;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class SetW0<E> implements SetW<E> {

    private static final SetW0<?> EMPTY = new SetW0<>();

    private SetW0() {
    }

    @SuppressWarnings("unchecked")
    public static <E> SetW0<E> empty() {
        return (SetW0<E>) EMPTY;
    }

    @Override
    public Set<E> asJavaSet() {
        return Collections.emptySet();
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return Collections.emptyList();
    }

    @Override
    public IteratorW0<E> iterator() {
        return IteratorW0.empty();
    }

    @Override
    public E first() {
        throw new NoSuchElementException();
    }

    @Override
    public SetW<E> head() {
        return this;
    }

    @Override
    public E last() {
        throw new NoSuchElementException();
    }

    @Override
    public SetW<E> tail() {
        return this;
    }

    @Override
    public SetW<E> prepend(E e) {
        return null; // TODO
    }

    @Override
    public SetW<E> prepend(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public SetW<E> append(E e) {
        return null; // TODO
    }

    @Override
    public SetW<E> append(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(E o) {
        return false;
    }

    @Override
    public SetW<E> add(E e) {
        return null; // TODO
    }

    @Override
    public SetW<E> remove(E e) {
        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return false;
    }

    @Override
    public SetW<E> addAll(CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public SetW<E> removeAll(CollectionW<? extends E> c) {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> SetW<R> map(Function<? super E, ? extends R> mapper) {
        return SetW0.empty();
    }

    @Override
    public <R> SetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return SetW0.empty();
    }

    @Override
    public SetW<E> filter(Predicate<? super E> filter) {
        return this;
    }

    @Override
    public SetW<E> copy() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListW<?>)
            return ((ListW) obj).isEmpty();

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // Singleton
    }

    @Override
    public String toString() {
        return "[]";
    }
}
