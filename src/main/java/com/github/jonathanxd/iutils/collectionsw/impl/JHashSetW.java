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
package com.github.jonathanxd.iutils.collectionsw.impl;

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.IteratorW;
import com.github.jonathanxd.iutils.collectionsw.SetW;
import com.github.jonathanxd.iutils.collectionsw.impl.builder.SetWBuilder;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaBackedIteratorW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaCollection;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaIterable;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link SetW} backed by a {@link java.util.HashMap}.
 *
 * @param <E> Element type.
 */
public class JHashSetW<E> implements SetW<E> {

    protected static final Object DEF = new Object();
    protected final Map<E, Object> map;

    /**
     * Creates an empty hash set.
     */
    public JHashSetW() {
        this.map = new HashMap<>();
    }

    /**
     * Creates a hash set with only one element.
     *
     * @param element Element.
     */
    public JHashSetW(E element) {
        this();
        this.map.put(element, DEF);
    }

    /**
     * Creates a hash set with {@code elements} as initial elements.
     *
     * @param elements Elements.
     */
    public JHashSetW(SetW<E> elements) {
        this();
        elements.forEach(e -> this.map.put(e, DEF));
    }

    /**
     * Creates a hash set with {@code elements} as initial elements.
     *
     * @param elements Elements.
     */
    public JHashSetW(E[] elements) {
        this();
        for (E element : elements) {
            this.map.put(element, DEF);
        }
    }

    protected JHashSetW(Map<E, Object> map) {
        this();
        this.map.putAll(map);
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Set<E> asJavaSet() {
        return new WBackedJavaSet<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return new WBackedJavaCollection<>(this);
    }

    @Override
    public IteratorW<E> iterator() {
        return new JavaBackedIteratorW<>(this.map.keySet().iterator());
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.iterator().next();
    }

    @Override
    public SetW<E> head() {
        return this.isEmpty() ? new JHashSetW<>() : new JHashSetW<>(this.first());
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!iterator.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public SetW<E> tail() {
        return this.isEmpty() ? new JHashSetW<>() : new JHashSetW<>(this.last());
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.map.containsKey(o);
    }

    @Override
    public SetW<E> prepend(E e) {
        Map<E, Object> lmap = this.newMap();

        lmap.put(e, DEF);

        this.map.forEach((x, unused) -> lmap.put(x, DEF));

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> prepend(CollectionW<? extends E> es) {
        Map<E, Object> lmap = this.newMap();

        es.forEach(x -> lmap.put(x, DEF));

        this.map.forEach((x, unused) -> lmap.put(x, DEF));

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> append(E e) {
        Map<E, Object> lmap = this.newMap(this.map);

        lmap.put(e, DEF);

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> append(CollectionW<? extends E> es) {
        Map<E, Object> lmap = this.newMap(this.map);

        es.forEach(o -> lmap.put(o, DEF));

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> add(E e) {
        Map<E, Object> lmap = this.newMap(this.map);

        lmap.put(e, DEF);

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> remove(E e) {
        Map<E, Object> map = newMap(this.map);

        map.remove(e, DEF);

        return new JHashSetW<>(map);
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.iterator().allOfRemaining(this.map::containsKey);
    }

    @Override
    public SetW<E> addAll(CollectionW<? extends E> c) {
        Map<E, Object> lmap = this.newMap(this.map);

        c.forEach(o -> lmap.put(o, DEF));

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> removeAll(CollectionW<? extends E> c) {
        Map<E, Object> lmap = this.newMap(this.map);

        c.forEach(o -> lmap.remove(o, DEF));

        return new JHashSetW<>(lmap);
    }

    @Override
    public <R> SetW<R> map(Function<? super E, ? extends R> mapper) {
        Map<R, Object> lmap = this.newMap();

        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            lmap.put(mapper.apply(iterator.next()), DEF);
        }

        return new JHashSetW<>(lmap);
    }

    @Override
    public <R> SetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        Map<R, Object> lmap = this.newMap();

        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            CollectionW<? extends R> apply = mapper.apply(iterator.next());

            apply.iterator().forEachRemaining(o -> lmap.put(o, DEF));
        }

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> filter(Predicate<? super E> filter) {
        Map<E, Object> lmap = this.newMap();

        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next;

            if (filter.test(next = iterator.next()))
                lmap.put(next, DEF);
        }

        return new JHashSetW<>(lmap);
    }

    @Override
    public SetW<E> copy() {
        return new JHashSetW<>(this.newMap(this.map));
    }

    protected <V> Map<V, Object> newMap() {
        return new HashMap<>();
    }

    protected <V> Map<V, Object> newMap(Map<V, Object> defaults) {
        return new HashMap<>(defaults);
    }

    @Override
    public String toString() {
        return this.map.keySet().toString();
    }

    @Override
    public Builder<E> builder() {
        return SetWBuilder.builder(this);
    }
}
