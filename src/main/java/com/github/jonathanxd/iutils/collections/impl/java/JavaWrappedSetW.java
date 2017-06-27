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
package com.github.jonathanxd.iutils.collections.impl.java;

import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.SetW;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class only casts the results of {@link JavaWrappedCollectionW}, by the contract, Jw Wrapped
 * collections should only creates a new instance via member factory function which must be visible
 * for implementation ({@code protected}).
 *
 * @param <E> Type of elements.
 */
@SuppressWarnings("unchecked")
public class JavaWrappedSetW<E> extends JavaWrappedCollectionW<E> implements SetW<E> {

    private final Set<E> unmod;
    private Function<Collection<?>, Set<?>> setFactory;

    /**
     * Constructs a Java wrapped collection from {@code mapped} and use {@code collectionFactory} to
     * create new collections.
     *
     * @param wrapped           Wrapped collection.
     * @param collectionFactory Factory of collection, make sure that the factory returns a new
     *                          collection, otherwise this collection will be mutated, which is a
     *                          unexpected behavior. This class may throw an exception if it can
     *                          detect that the {@code collectionFactory} returned {@code wrapped}.
     *                          This factory receives a collection as argument to create a new
     *                          collection with same elements as received collection, but it is not
     *                          guaranteed that the {@code wrapped} collection will be passed or not
     *                          as an argument nor that the passed collection is of the same type.
     *                          Returned collection should be generic and not type checked because
     */
    public JavaWrappedSetW(Set<E> wrapped, Function<Collection<?>, Set<?>> collectionFactory) {
        super(wrapped, collectionFactory);
        this.setFactory = collectionFactory;
        this.unmod = Collections.unmodifiableSet(wrapped);
    }

    @Override
    public SetW<E> filterNot(Predicate<? super E> filter) {
        return (SetW<E>) super.filterNot(filter);
    }

    @Override
    public Set<E> asJavaSet() {
        return this.unmod;
    }

    @Override
    public SetW<E> head() {
        return (SetW<E>) super.head();
    }

    @Override
    public SetW<E> tail() {
        return (SetW<E>) super.tail();
    }

    @Override
    public SetW<E> prepend(E e) {
        return (SetW<E>) super.prepend(e);
    }

    @Override
    public SetW<E> append(E e) {
        return (SetW<E>) super.append(e);
    }

    @Override
    public SetW<E> prepend(CollectionW<? extends E> es) {
        return (SetW<E>) super.prepend(es);
    }

    @Override
    public SetW<E> append(CollectionW<? extends E> es) {
        return (SetW<E>) super.append(es);
    }

    @Override
    public SetW<E> add(E e) {
        return (SetW<E>) super.add(e);
    }

    @Override
    public SetW<E> remove(E e) {
        return (SetW<E>) super.remove(e);
    }

    @Override
    public SetW<E> addAll(CollectionW<? extends E> c) {
        return (SetW<E>) super.addAll(c);
    }

    @Override
    public SetW<E> removeAll(CollectionW<? extends E> c) {
        return (SetW<E>) super.removeAll(c);
    }

    @Override
    public <R> SetW<R> map(Function<? super E, ? extends R> mapper) {
        return (SetW<R>) super.map(mapper);
    }

    @Override
    public <R> SetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return (SetW<R>) super.flatMap(mapper);
    }

    @Override
    public SetW<E> filter(Predicate<? super E> filter) {
        return (SetW<E>) super.filter(filter);
    }

    @Override
    public SetW<E> copy() {
        return (SetW<E>) super.copy();
    }

    @Override
    protected <R> CollectionW<R> newFrom(Collection<R> collection) {
        if(collection instanceof Set<?>) {
            return new JavaWrappedSetW<>((Set<R>) collection, this.setFactory);
        }

        return new JavaWrappedSetW<>((Set<R>) this.setFactory.apply(collection), this.setFactory);
    }
}
