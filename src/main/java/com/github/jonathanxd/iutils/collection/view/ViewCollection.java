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
package com.github.jonathanxd.iutils.collection.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;


/**
 * View of a collection.
 *
 * Add methods such as {@link #add(Object)}, {@link #addAll(Collection)}, etc... will always be
 * delegated to {@link #add} function.
 *
 * Remove methods such as {@link #remove(Object)}, {@link #retainAll(Collection)}, etc... will
 * always be delegated to {@link #remove} function.
 *
 * The behavior remains the same.
 */
public class ViewCollection<E, Y> extends AbstractViewCollection<E, Y> {

    private final Predicate<Y> add;
    private final Predicate<Object> remove;

    /**
     * Synthetic iterable to emulate a iterable of element of type {@link Y}.
     */
    private final Iterable<Y> syntheticIterable;

    /**
     * Constructs a view of a collection.
     *
     * @param collection Collection.
     * @param mapper     Mapper function. Maps elements to a new iterable to query.
     * @param add        Handle add operation.
     * @param remove     Handle remove operator.
     */
    public ViewCollection(Collection<E> collection, BiFunction<E, Iterator<E>, Iterator<Y>> mapper,
                          Predicate<Y> add,
                          Predicate<Object> remove) {
        super(collection);

        Objects.requireNonNull(add);
        Objects.requireNonNull(remove);

        this.add = add;
        this.remove = remove;
        this.syntheticIterable = ViewUtils.iterable(collection, mapper);
    }

    @Override
    public boolean add(Y y) {
        return this.add.test(y);
    }

    @Override
    public boolean remove(Object o) {
        return this.remove.test(o);
    }

    @Override
    public Iterable<Y> getSyntheticIterable() {
        return this.syntheticIterable;
    }
}
