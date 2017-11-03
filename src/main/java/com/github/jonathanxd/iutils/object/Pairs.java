/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.object;

import java.util.function.Supplier;

public enum Pairs {
    ;

    /**
     * Denotes a null pair constant.
     */
    private static final Pair<?, ?> NULL_PAIR = new ObjectPair<>(null, null);

    /**
     * Gets null pair constant.
     *
     * @param <A> Type of first value.
     * @param <B> Type of second value.
     * @return null pair constant.
     */
    @SuppressWarnings("unchecked")
    public static <A, B> Pair<A, B> nullPair() {
        return (Pair<A, B>) NULL_PAIR;
    }

    /**
     * Creates a object pair of {@code a} and {@code b}.
     *
     * If both, {@code a} and {@code b} is null, returns a {@link #nullPair()}.
     *
     * @param a   First value.
     * @param b   Second value.
     * @param <A> First value type.
     * @param <B> Second value type.
     * @return Object pair of {@code a} and {@code b}.
     */
    public static <A, B> Pair<A, B> of(A a, B b) {
        if (a == null && b == null)
            return Pairs.nullPair();

        return new ObjectPair<>(a, b);
    }

    /**
     * Creates a supplier pair.
     *
     * Supplier pair always call {@link Supplier#get()} when a value is accessed.
     *
     * @param aSupplier First value supplier.
     * @param bSupplier Second value supplier.
     * @param <A>       First value type.
     * @param <B>       Second value type.
     * @return Supplier pair.
     */
    public static <A, B> Pair<A, B> ofSupplier(Supplier<A> aSupplier, Supplier<B> bSupplier) {
        return new SupplierPair<>(aSupplier, bSupplier);
    }

    /**
     * Creates a lazy pair.
     *
     * Lazy is the same as a object pair with two lazy instances, but provides methods with better
     * signatures and encapsulation.
     *
     * @param aLazy First value lazy accessor.
     * @param bLazy Second value lazy accessor.
     * @param <A>   First value type.
     * @param <B>   Second value type.
     * @return Lazy pair.
     */
    public static <A, B> Pair<A, B> ofLazy(Lazy<A> aLazy, Lazy<B> bLazy) {
        return new LazyPair<>(aLazy, bLazy);
    }

    private static final class ObjectPair<A, B> extends Pair<A, B> {
        private final A first;
        private final B second;

        ObjectPair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public A getFirst() {
            return this.first;
        }

        @Override
        public B getSecond() {
            return this.second;
        }

    }

    private static final class SupplierPair<A, B> extends Pair<A, B> {

        private final Supplier<A> aSupplier;
        private final Supplier<B> bSupplier;

        SupplierPair(Supplier<A> aSupplier, Supplier<B> bSupplier) {
            this.aSupplier = aSupplier;
            this.bSupplier = bSupplier;
        }

        @Override
        public A getFirst() {
            return this.aSupplier.get();
        }

        @Override
        public B getSecond() {
            return this.bSupplier.get();
        }
    }

    private static final class LazyPair<A, B> extends Pair<A, B> {

        private final Lazy<A> firstLazy;
        private final Lazy<B> secondLazy;

        LazyPair(Lazy<A> firstLazy, Lazy<B> secondLazy) {
            this.firstLazy = firstLazy;
            this.secondLazy = secondLazy;
        }

        @Override
        public A getFirst() {
            return this.firstLazy.get();
        }

        @Override
        public B getSecond() {
            return this.secondLazy.get();
        }
    }
}
