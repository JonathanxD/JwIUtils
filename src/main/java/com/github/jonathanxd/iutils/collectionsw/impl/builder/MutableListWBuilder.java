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
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableListW;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Builder implementation backed by a {@link List Java List}.
 *
 * This constructs a {@link JavaBackedMutListW} backed by a {@link LinkedList}.
 *
 * @param <E> Element type.
 */
public class MutableListWBuilder<E> implements MutableListW.Builder<E> {

    private final List<E> list = new LinkedList<>();

    private MutableListWBuilder() {
    }

    /**
     * Creates an builder instance without elements.
     *
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableListWBuilder<E> builder() {
        return new MutableListWBuilder<>();
    }

    /**
     * Creates an builder instance with a single element.
     *
     * @param e1  First element.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableListWBuilder<E> builder(E e1) {
        MutableListWBuilder<E> builder = new MutableListWBuilder<>();
        builder.add(e1);
        return builder;
    }

    /**
     * Creates an builder instance with a two elements.
     *
     * @param e1  First element.
     * @param e2  Second element.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableListWBuilder<E> builder(E e1, E e2) {
        MutableListWBuilder<E> builder = new MutableListWBuilder<>();
        builder.add(e1);
        builder.add(e2);
        return builder;
    }

    /**
     * Creates an builder instance with {@code n} elements.
     *
     * @param elements Elements.
     * @param <E> Element type.
     * @return Builder.
     */
    @SafeVarargs
    public static <E> MutableListWBuilder<E> builder(E... elements) {
        MutableListWBuilder<E> builder = new MutableListWBuilder<>();

        for (E element : elements) {
            builder.add(element);
        }

        return builder;
    }

    /**
     * Creates an builder instance with {@code n} elements.
     *
     * @param elements Elements.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableListWBuilder<E> builder(Collection<E> elements) {
        MutableListWBuilder<E> builder = new MutableListWBuilder<>();

        for (E element : elements) {
            builder.add(element);
        }

        return builder;
    }

    /**
     * Creates an builder instance with {@code n} elements.
     *
     * @param elements Elements.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableListWBuilder<E> builder(CollectionW<E> elements) {
        MutableListWBuilder<E> builder = new MutableListWBuilder<>();

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
    public MutableListW.Builder<E> add(E element) {
        this.list.add(element);
        return this;
    }

    @Override
    public MutableListW.Builder<E> remove(E element) {
        this.list.remove(element);
        return this;
    }

    @Override
    public MutableListW.Builder<E> clear() {
        this.list.clear();
        return this;
    }

    @Override
    public MutableListW<E> build() {
        return new JavaBackedMutListW<>(new LinkedList<>(this.list));
    }
}
