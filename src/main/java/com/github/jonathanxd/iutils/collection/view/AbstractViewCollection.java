/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.collection.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * View of a collection.
 *
 * Changes to this {@link Collection} are delegate to {@link #add} and {@link #remove} suppliers.
 *
 * This class uses {@link Iterable iterables} and utilities methods to map and iterable through the
 * list correctly, the mapper function should return an {@link Iterator} instance.
 *
 * Lookup methods such as {@link #contains(Object)}, {@link #size()}, etc... will always iterate
 * through {@link Iterator} instance provided by mapper function.
 *
 * All values are computed using iterators (it includes {@link #size()}).
 *
 * The add and remove operation should be achieved by implementation.
 *
 * @param <E> Input element type.
 * @param <Y> Output element type.
 */
public abstract class AbstractViewCollection<E, Y> implements Collection<Y> {

    /**
     * Wrapper collection.
     */
    private final Collection<E> collection;

    /**
     * Constructs a view of a collection.
     *
     * @param collection Collection.
     */
    public AbstractViewCollection(Collection<E> collection) {
        Objects.requireNonNull(collection);
        this.collection = collection;
    }

    @Override
    public int size() {
        int size = 0;

        for (Y ignored : this) {
            ++size;
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return !this.getSyntheticIterable().iterator().hasNext();
    }

    @Override
    public boolean contains(Object o) {

        for (Y e : this.getSyntheticIterable()) {
            if (Objects.equals(e, o))
                return true;
        }

        return false;
    }

    @Override
    public Iterator<Y> iterator() {
        return this.getSyntheticIterable().iterator();
    }

    @Override
    public Object[] toArray() {
        List<Y> list = new ArrayList<>();

        this.getSyntheticIterable().forEach(list::add);

        return list.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T> T[] toArray(T[] a) {
        List<Y> list = new ArrayList<>();

        this.getSyntheticIterable().forEach(list::add);

        return list.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        List<Y> all = new ArrayList<>();

        this.getSyntheticIterable().forEach(all::add);

        return all.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Y> c) {

        boolean any = false;

        for (Y e : c) {
            any |= this.add(e);
        }

        return any;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean any = false;

        for (Object e : c) {
            any |= this.remove(e);
        }

        return any;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean any = false;
        Iterator<Y> iterator = this.getSyntheticIterable().iterator();

        while (iterator.hasNext()) {
            Y next = iterator.next();

            if (!c.contains(next)) {
                iterator.remove();
                any = true;
            }
        }

        return any;
    }

    @Override
    public void clear() {
        this.collection.clear();
    }

    public abstract Iterable<Y> getSyntheticIterable();

    protected Collection<E> getCollection() {
        return this.collection;
    }

    @Override
    public String toString() {
        return this.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

}
