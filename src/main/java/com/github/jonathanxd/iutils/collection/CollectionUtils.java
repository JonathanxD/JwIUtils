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
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.exception.BiException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Created by jonathan on 05/03/16.
 */
public class CollectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> same(Collection<?> collection) {
        try {
            return (Collection<T>) collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            try {
                return collection.getClass().getConstructor(Collection.class).newInstance(Collections.emptyList());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> sameFilled(Collection<T> collection) {
        try {
            return collection.getClass().getConstructor(Collection.class).newInstance(collection);
        } catch (Throwable t) {
            try {
                Collection rmk = collection.getClass().newInstance();

                rmk.addAll(collection);

                return (Collection<T>) rmk;
            } catch (InstantiationException | IllegalAccessException e1) {
                throw new BiException(t, e1);
            }
        }

    }

    /**
     * Create a {@link T Sequence} of {@link E}.
     *
     * @param elements Array Elements to add to sequence.
     * @param factory  Factory method to create sequence.
     * @param adder    Sequence adding method.
     * @param <E>      Element type.
     * @param <T>      Sequence type (non-java)
     * @return Created {@link T Sequence} with {@code elements}.
     */
    public static <E, T> T sequenceOf(E[] elements, Supplier<T> factory, BiConsumer<T, E> adder) {
        T sequence = factory.get();

        for (E element : elements) {
            adder.accept(sequence, element);
        }

        return sequence;
    }

    /**
     * Create a {@link Collection} of {@link E} and add {@code elements} to the {@link Collection}.
     *
     * @param elements Elements to add to collection.
     * @param factory  Collection factory method.
     * @param <E>      Element type.
     * @param <T>      Collection type.
     * @return {@link T Collection} of {@link E} with {@code elements}.
     */
    public static <E, T extends Collection<E>> T collectionOf(E[] elements, Supplier<T> factory) {
        T collection = factory.get();

        Collections.addAll(collection, elements);

        return collection;
    }

    /**
     * Create a {@link List} of {@link E} and add {@code elements} to the {@link List}.
     *
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link List} of {@link E} with {@code elements}.
     */
    public static <E> List<E> listOf(E[] elements) {
        List<E> list = new ArrayList<>();

        Collections.addAll(list, elements);

        return list;
    }

    /**
     * Create a {@link Set} of {@link E} and add {@code elements} to the {@link Set}.
     *
     * @param elements Elements to add to set.
     * @param <E>      Element type.
     * @return {@link Set} of {@link E} with {@code elements}.
     */
    public static <E> Set<E> setOf(E[] elements) {
        Set<E> set = new HashSet<>();

        Collections.addAll(set, elements);

        return set;
    }

    /**
     * Create a {@link LinkedHashSet} of {@link E} and add {@code elements} to the {@link
     * LinkedHashSet}.
     *
     * @param elements Elements to add to set.
     * @param <E>      Element type.
     * @return {@link LinkedHashSet} of {@link E} with {@code elements}.
     */
    public static <E> LinkedHashSet<E> linkedSetOf(E[] elements) {
        LinkedHashSet<E> set = new LinkedHashSet<>();

        Collections.addAll(set, elements);

        return set;
    }
}
