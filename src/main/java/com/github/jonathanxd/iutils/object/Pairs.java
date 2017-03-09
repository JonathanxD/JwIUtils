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
package com.github.jonathanxd.iutils.object;

import java.util.function.Supplier;

public enum  Pairs {
    ;

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new ObjectPair<>(a, b);
    }

    public static <A, B> Pair<A, B> ofSupplier(Supplier<A> aSupplier, Supplier<B> bSupplier) {
        return new SupplierPair<>(aSupplier, bSupplier);
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

        private SupplierPair(Supplier<A> aSupplier, Supplier<B> bSupplier) {
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
}
