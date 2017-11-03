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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Pairs of values.
 *
 * @param <A> First value type.
 * @param <B> Second value type.
 */
public abstract class Pair<A, B> {

    /**
     * Gets null pair constant.
     *
     * @param <A> Type of first value.
     * @param <B> Type of second value.
     * @return null pair constant.
     */
    public static <A, B> Pair<A, B> nullPair() {
        return Pairs.nullPair();
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
        return Pairs.of(a, b);
    }

    /**
     * Creates a supplier pair.
     *
     * @param aSupplier First value supplier.
     * @param bSupplier Second value supplier.
     * @param <A>       First value type.
     * @param <B>       Second value type.
     * @return Supplier pair, see {@link Pairs#ofSupplier(Supplier, Supplier)}.
     */
    public static <A, B> Pair<A, B> ofSupplier(Supplier<A> aSupplier, Supplier<B> bSupplier) {
        return Pairs.ofSupplier(aSupplier, bSupplier);
    }

    /**
     * Creates a {@link Optional} of this pair. if pair {@link #isNullPair()}, returns a empty
     * optional, if not, returns a optional of {@code this} pair.
     *
     * @return {@link Optional} of this pair. If pair {@link #isNullPair()}, returns a empty
     * optional, if not, returns a optional of {@code this} pair.
     */
    public final Optional<Pair<A, B>> toOptional() {
        if (this.isNullPair())
            return Optional.empty();

        return Optional.of(this);
    }

    /**
     * Gets first value.
     *
     * @return First value.
     */
    public abstract A getFirst();

    /**
     * Gets second value.
     *
     * @return Second value.
     */
    public abstract B getSecond();

    /**
     * First value (Kotlin compatibility purpose).
     */
    public final A component1() {
        return this.getFirst();
    }

    /**
     * Second value (Kotlin compatibility purpose).
     */
    public final B component2() {
        return this.getSecond();
    }

    /**
     * Returns true if both {@link #getFirst()} and {@link #getSecond()} returns {@code null}.
     *
     * @return True if both {@link #getFirst()} and {@link #getSecond()} returns {@code null}.
     */
    public final boolean isNullPair() {
        return this.getFirst() == null && this.getSecond() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getFirst(), this.getSecond());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair<?, ?>))
            return super.equals(obj);

        Pair<?, ?> pair = (Pair<?, ?>) obj;

        return Objects.equals(this.getFirst(), pair.getFirst())
                && Objects.equals(this.getSecond(), pair.getSecond());
    }
}
