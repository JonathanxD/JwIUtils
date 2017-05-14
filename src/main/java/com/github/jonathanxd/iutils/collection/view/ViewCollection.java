/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.collection.view.ViewUtils.BackingIterator;
import com.github.jonathanxd.iutils.collection.view.ViewUtils.FakeCachedIterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class ViewCollection<E, Y> implements Collection<Y> {

    private final Iterable<E> iterable;
    private final Function<E, Iterable<Y>> mapper;
    private final BiPredicate<Y, BackingIterator<E, Y>> add;
    private final BiPredicate<Object, BackingIterator<E, Y>> remove;

    // Cached mapping iterable to avoid object creation.
    private final FakeCachedIterable<E, Y> syntheticIterable;

    public ViewCollection(Iterable<E> iterable, Function<E, Iterable<Y>> mapper,
                          BiPredicate<Y, BackingIterator<E, Y>> add,
                          BiPredicate<Object, BackingIterator<E, Y>> remove) {
        this.iterable = iterable;
        this.mapper = mapper;
        this.add = add;
        this.remove = remove;
        this.syntheticIterable = ViewUtils.iterable(iterable, mapper);
    }

    @Override
    public int size() {
        int size = 0;

        while (this.getSyntheticIterable().iterator().hasNext()) {
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
    public boolean add(Y e) {
        return this.add.test(e, this.syntheticIterable.getBackingIterator());
    }

    @Override
    public boolean remove(Object o) {
        return this.remove.test(o, this.syntheticIterable.getBackingIterator());
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

        while(iterator.hasNext()) {
            Y next = iterator.next();

            if(!c.contains(next)) {
                iterator.remove();
                any = true;
            }
        }

        return any;
    }

    @Override
    public void clear() {
        ((Collection<Y>) iterable).clear();
    }

    public Iterable<Y> getSyntheticIterable() {
        return this.syntheticIterable;
    }
}
