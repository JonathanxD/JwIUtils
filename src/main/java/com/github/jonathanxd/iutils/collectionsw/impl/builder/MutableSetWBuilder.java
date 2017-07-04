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
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutSetW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSetW;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Builder implementation backed by a {@link Set Java Set}.
 *
 * This constructs a {@link JavaBackedMutSetW} backed by a {@link HashSet}.
 *
 * @param <E> Element type.
 */
public final class MutableSetWBuilder<E> implements MutableSetW.Builder<E> {

    private final Set<E> set = new LinkedHashSet<>();

    private MutableSetWBuilder() {
    }

    /**
     * Creates an builder instance without elements.
     *
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableSetWBuilder<E> builder() {
        return new MutableSetWBuilder<>();
    }

    /**
     * Creates an builder instance with a single element.
     *
     * @param e1  First element.
     * @param <E> Element type.
     * @return Builder.
     */
    public static <E> MutableSetWBuilder<E> builder(E e1) {
        MutableSetWBuilder<E> builder = new MutableSetWBuilder<>();
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
    public static <E> MutableSetWBuilder<E> builder(E e1, E e2) {
        MutableSetWBuilder<E> builder = new MutableSetWBuilder<>();
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
    public static <E> MutableSetWBuilder<E> builder(E... elements) {
        MutableSetWBuilder<E> builder = new MutableSetWBuilder<>();

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
    public static <E> MutableSetWBuilder<E> builder(Collection<E> elements) {
        MutableSetWBuilder<E> builder = new MutableSetWBuilder<>();

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
    public static <E> MutableSetWBuilder<E> builder(CollectionW<E> elements) {
        MutableSetWBuilder<E> builder = new MutableSetWBuilder<>();

        elements.forEach(builder::add);

        return builder;
    }

    @Override
    public Stream<E> elements() {
        return set.stream();
    }

    @Override
    public int size() {
        return this.set.size();
    }

    @Override
    public MutableSetW.Builder<E> add(E element) {
        this.set.add(element);
        return this;
    }

    @Override
    public MutableSetW.Builder<E> remove(E element) {
        this.set.remove(element);
        return this;
    }

    @Override
    public MutableSetW.Builder<E> clear() {
        this.set.clear();
        return this;
    }

    @Override
    public MutableSetW<E> build() {
        return new JavaBackedMutSetW<>(new HashSet<>(this.set));
    }
}
