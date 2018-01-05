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
package com.github.jonathanxd.iutils.collection.wrapper;

import com.github.jonathanxd.iutils.collection.immutable.ImmutableList;
import com.github.jonathanxd.iutils.collection.immutable.ImmutableSet;
import com.github.jonathanxd.iutils.collection.wrapper.impl.ImmutableWrapperList;
import com.github.jonathanxd.iutils.collection.wrapper.impl.ImmutableWrapperSet;
import com.github.jonathanxd.iutils.collection.wrapper.impl.SuppliedWrapperList;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class WrapperCollections {

    /**
     * Creates a wrapper list that wrap calls to list supplied by {@code supplier}.
     *
     * Every list operation calls {@link Supplier#get()} on {@code supplier} to get list to delegate
     * calls, this means that wrapped list reference can be changed.
     *
     * @param supplier Supplier of list to delegate calls.
     * @param <T>      Element type.
     * @return Wrapper list that wrap calls to list supplied by {@code supplier}.
     */
    public static <T> WrapperList<T> fromSupplier(Supplier<List<T>> supplier) {
        return new SuppliedWrapperList<>(supplier);
    }

    /**
     * Creates an immutable list wrapping {@code list}.
     *
     * @param list List to wrap.
     * @param <E>  Element type.
     * @return Immutable list wrapping {@code list}.
     */
    public static <E> ImmutableList<E> immutableList(List<E> list) {
        return new ImmutableWrapperList<>(list);
    }

    /**
     * Creates an immutable set wrapping {@code set}.
     *
     * @param set Set to wrap.
     * @param <E> Element type.
     * @return Immutable set wrapping {@code set}.
     */
    public static <E> ImmutableSet<E> immutableSet(Set<E> set) {
        return new ImmutableWrapperSet<>(set);
    }
}
