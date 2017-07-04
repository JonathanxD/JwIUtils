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
package com.github.jonathanxd.iutils.collectionsw.impl.builder;

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutListW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.WBackedSizedListW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSizedListW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Builder implementation backed by a {@link List Java List}.
 *
 * This constructs a {@link WBackedSizedListW} backed by a {@link JavaBackedMutListW} backed by an
 * {@link ArrayList} (backed by.... an Array?).
 *
 * @param <E> Element type.
 */
public final class SizedListWBuilder<E> implements MutableSizedListW.Builder<E> {

    private final List<E> list = new LinkedList<>();
    private int maxSize;

    private SizedListWBuilder(int maxSize) {
        this.maxSize = maxSize;
    }


    /**
     * Creates an builder instance without elements.
     *
     * @param initialSize Initial value of maxSize variable. This can be changed later using {@link #setLimit(int)}.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> SizedListWBuilder<E> builder(int initialSize) {
        return new SizedListWBuilder<>(initialSize);
    }

    /**
     * Creates an builder instance with a single element.
     *
     * @param initialSize Initial value of maxSize variable. This can be changed later using {@link #setLimit(int)}.
     * @param e1  First element.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> SizedListWBuilder<E> builder(int initialSize, E e1) {
        SizedListWBuilder<E> builder = new SizedListWBuilder<>(initialSize);
        builder.add(e1);
        return builder;
    }

    /**
     * Creates an builder instance with a two elements.
     *
     * @param initialSize Initial value of maxSize variable. This can be changed later using {@link #setLimit(int)}.
     * @param e1  First element.
     * @param e2  Second element.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> SizedListWBuilder<E> builder(int initialSize, E e1, E e2) {
        SizedListWBuilder<E> builder = new SizedListWBuilder<>(initialSize);
        builder.add(e1);
        builder.add(e2);
        return builder;
    }

    /**
     * Creates an builder instance with {@code n} elements.
     *
     * @param initialSize Initial value of maxSize variable. This can be changed later using {@link #setLimit(int)}.
     * @param elements Elements.
     * @param <E> Element type.
     * @return Builder.
     */
    @SafeVarargs
    public static <E> SizedListWBuilder<E> builder(int initialSize, E... elements) {
        SizedListWBuilder<E> builder = new SizedListWBuilder<>(initialSize);

        for (E element : elements) {
            builder.add(element);
        }

        return builder;
    }

    /**
     * Creates an builder instance with {@code n} elements.
     *
     * @param initialSize Initial value of maxSize variable. This can be changed later using {@link #setLimit(int)}.
     * @param elements Elements.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> SizedListWBuilder<E> builder(int initialSize, Collection<E> elements) {
        SizedListWBuilder<E> builder = new SizedListWBuilder<>(initialSize);

        for (E element : elements) {
            builder.add(element);
        }

        return builder;
    }

    /**
     * Creates an builder instance with {@code n} elements.
     *
     * @param initialSize Initial value of maxSize variable. This can be changed later using {@link #setLimit(int)}.
     * @param elements Elements.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> SizedListWBuilder<E> builder(int initialSize, CollectionW<E> elements) {
        SizedListWBuilder<E> builder = new SizedListWBuilder<>(initialSize);

        elements.forEach(builder::add);

        return builder;
    }

    @Override
    public Stream<E> elements() {
        return list.stream();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public MutableSizedListW.Builder<E> setLimit(int limit) {
        this.maxSize = limit;
        return this;
    }

    @Override
    public MutableSizedListW.Builder<E> add(E element) {
        this.list.add(element);
        return this;
    }

    @Override
    public MutableSizedListW.Builder<E> remove(E element) {
        this.list.remove(element);
        return this;
    }

    @Override
    public MutableSizedListW.Builder<E> clear() {
        this.list.clear();
        return this;
    }

    @Override
    public MutableSizedListW<E> build() {
        return new WBackedSizedListW<>(new JavaBackedMutListW<>(new ArrayList<>(this.list)), this.maxSize,
                () -> new JavaBackedMutListW<>(new ArrayList<>()));
    }
}
