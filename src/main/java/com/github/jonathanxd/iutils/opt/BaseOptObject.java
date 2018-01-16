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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.object.Lazy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BaseOptObject<T, O extends BaseOptObject<T, O>> extends Opt<O> {

    /**
     * Gets the value holden by this optional.
     *
     * @return Value.
     * @throws java.util.NoSuchElementException If this {@link Opt} is {@code None}.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    T getValue();

    @Override
    @NotNull
    default Object getObjectValue() {
        return this.getValue();
    }

    /**
     * Calls {@code consumer} if value is present.
     *
     * @param consumer Consumer to accept value if is present.
     */
    void ifPresent(@NotNull Consumer<? super T> consumer);

    /**
     * Calls {@code consumer} with value if present, or calls {@code elseRunnable} if value is not
     * present.
     *
     * @param consumer     Consumer to accept value if is present.
     * @param elseRunnable Runnable to call if value is not present.
     */
    void ifPresent(@NotNull Consumer<? super T> consumer, @NotNull Runnable elseRunnable);

    /**
     * Calls {@code consumer} to accept value (if present) and return {@code this}.
     *
     * @param consumer Consumer of value.
     * @return {@code this};
     */
    @SuppressWarnings("unchecked")
    @NotNull
    default O onPresent(@NotNull Consumer<? super T> consumer) {
        this.ifPresent(consumer);
        return (O) this;
    }

    /**
     * Calls {@code consumer} to accept value (if present) and return {@code this}.
     *
     * @param consumer     Consumer of value.
     * @param elseRunnable Runnable to invoke if value is not present.
     * @return {@code this};
     */
    @SuppressWarnings("unchecked")
    @NotNull
    default O onPresent(@NotNull Consumer<? super T> consumer, @NotNull Runnable elseRunnable) {
        this.ifPresent(consumer, elseRunnable);
        return (O) this;
    }

    /**
     * Test value against {@code predicate} if value is present.
     *
     * @param predicate Predicate to test value.
     * @return An {@link Opt} of {@code None} if value does not match predicate, or return same
     * {@link Opt} if either value is not present or value matches predicate.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    O filter(@NotNull Predicate<? super T> predicate);

    /**
     * Converts this {@link BaseOptObject} of either {@code Some} or {@code None} to an opt of
     * {@code None}.
     *
     * @return {@link O} of {@code None}.
     */
    @NotNull
    O toNone();

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of type {@link O2}.
     *
     * @param mapper Flat mapper to map value.
     * @param none   Supplier of instance of {@link O2} with {@code None} value.
     * @param <O2>   Type of opt.
     * @return An {@link Opt} supplied by {@code none} if value is not present, or {@link Opt}
     * returned by {@code mapper}.
     */
    @NotNull <O2 extends Opt<O2>> O2 flatMapTo(@NotNull Function<? super T, ? extends O2> mapper, @NotNull Supplier<O2> none);

    /**
     * Returns the value of this {@link Opt} if present, or {@code value} if not.
     *
     * @param value Other value to return if value of this {@link Opt} is not present (cannot be
     *              null).
     * @return Value of this {@link Opt} if present, or {@code value} if not.
     */
    @Nullable
    T orElse(@NotNull T value);

    /**
     * Returns the value of this {@link Opt} if present, or value supplied by {@code supplier} if
     * not.
     *
     * @param supplier Supplier of other value to return if value of this {@link Opt} is not present
     *                 (cannot be null).
     * @return Value of this {@link Opt} if present, or value supplied by {@code supplier} if not.
     */
    @Nullable
    T orElseGet(@NotNull Supplier<? extends T> supplier);

    /**
     * Returns the value of this {@link Opt} if present, or value returned by {@code lazy}.
     *
     * @param lazy Lazy provider of value to return if value is not present.
     * @return Value of this {@link Opt} if present, or value returned by {@code lazy}.
     */
    @Nullable
    T orElseLazy(@NotNull Lazy<? extends T> lazy);

    /**
     * Returns the value of this {@link Opt} if present, or fail stupidly if value is not present.
     *
     * I recommend to not do things stupidly, you have been warned.
     *
     * @param supplier Supplier of stupid exception to throw if value is not present.
     * @param <E>      Type of stupid exception.
     * @return Value if present.
     * @throws E If value is not present.
     */
    @NotNull <E extends Throwable> T orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E;

    /**
     * Returns a {@link Stream} with value of this {@link Opt} or an empty {@link Stream} if value
     * is not present..
     *
     * @return {@link Stream} with value of this {@link Opt} or an empty {@link Stream} if value is
     * not present.
     */
    @NotNull
    Stream<T> stream();

    /**
     * Returns an iterator of this value.
     *
     * @return Iterator of this value.
     */
    @NotNull
    Iterator<T> iterator();

    /**
     * Returns an iterator of this value.
     *
     * @return Iterator of this value.
     */
    @NotNull
    Spliterator<T> spliterator();

    /**
     * Creates a {@link Optional} from this {@link Opt}.
     *
     * @return {@link Optional} of value if it is not null, or {@link Optional#empty()} if this opt
     * is either empty or has a null value.
     */
    @NotNull
    Optional<T> toOptional();
}
