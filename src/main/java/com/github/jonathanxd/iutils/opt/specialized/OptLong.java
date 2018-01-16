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
package com.github.jonathanxd.iutils.opt.specialized;

import com.github.jonathanxd.iutils.function.function.LongToLongFunction;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.LongStream;

/**
 * {@code long} specialized {@link Opt}.
 */
public abstract class OptLong implements Opt<OptLong> {

    OptLong() {
    }

    /**
     * Creates an {@link OptLong} from {@code optional}.
     *
     * @param optional Optional to create {@link OptLong}.
     * @return An {@link Opt} of {@code Some} if {@link Optional#isPresent() Optional value is
     * present}, or {@code None} otherwise.
     */
    @SuppressWarnings({"unchecked", "OptionalUsedAsFieldOrParameterType"})
    @NotNull
    public static OptLong fromOptional(@NotNull OptionalLong optional) {
        if (optional.isPresent()) {
            return OptLong.some(optional.getAsLong());
        } else {
            return OptLong.none();
        }
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @return An {@link Opt} of {@code Some} {@code value}
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public static OptLong optLong(long value) {
        return new SomeLong(value);
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @return An {@link Opt} of {@code Some} {@code value}
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public static OptLong some(long value) {
        return new SomeLong(value);
    }

    /**
     * Creates a {@link Opt} with {@code None} value.
     *
     * @return {@link Opt} with {@code None} value.
     */
    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    @NotNull
    public static OptLong none() {
        return NoneLong.NONE;
    }

    /**
     * Gets the value holden by this optional.
     *
     * @return Value.
     * @throws java.util.NoSuchElementException If this {@link Opt} is {@code None}.
     */
    @SuppressWarnings("unchecked")
    public abstract long getValue();

    /**
     * Calls {@code consumer} if value is present.
     *
     * @param consumer Consumer to accept value if is present.
     */
    public abstract void ifPresent(@NotNull LongConsumer consumer);

    /**
     * Calls {@code consumer} with value if present, or calls {@code elseRunnable} if value is not
     * present.
     *
     * @param consumer     Consumer to accept value if is present.
     * @param elseRunnable Runnable to call if value is not present.
     */
    public abstract void ifPresent(@NotNull LongConsumer consumer, @NotNull Runnable elseRunnable);

    /**
     * Calls {@code consumer} to accept value (if present) and return {@code this}.
     *
     * @param consumer Consumer of value.
     * @return {@code this};
     */
    @NotNull
    public OptLong onPresent(@NotNull LongConsumer consumer) {
        this.ifPresent(consumer);
        return this;
    }

    /**
     * Calls {@code consumer} to accept value (if present) and return {@code this}.
     *
     * @param consumer     Consumer of value.
     * @param elseRunnable Runnable to invoke if value is not present.
     * @return {@code this};
     */
    @NotNull
    public OptLong onPresent(@NotNull LongConsumer consumer, @NotNull Runnable elseRunnable) {
        this.ifPresent(consumer, elseRunnable);
        return this;
    }

    /**
     * Test value against {@code predicate} if value is present.
     *
     * @param predicate Predicate to test value.
     * @return An {@link Opt} of {@code None} if value does not match predicate, or return same
     * {@link Opt} if either value is not present or value matches predicate.
     */
    @NotNull
    public abstract OptLong filter(@NotNull LongPredicate predicate);

    /**
     * Maps the value of {@code this} {@link Opt} to a another value using {@code mapper}.
     *
     * @param mapper Mapper to map value.
     * @return An {@link Opt} of mapped value if present, or an {@link Opt} of {@code None} if no
     * value is present.
     */
    @NotNull
    public abstract OptLong map(@NotNull LongToLongFunction mapper);

    /**
     * Flat maps the value of {@code this} {@link Opt} to another {@link Opt}.
     *
     * @param mapper Flat mapper to map value.
     * @return An {@link Opt} of {@code None} if the value is not present, or the {@link Opt}
     * returned by {@code mapper} if value is present.
     */
    @NotNull
    public abstract OptLong flatMap(@NotNull LongFunction<? extends OptLong> mapper);

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of type {@link O}.
     *
     * @param mapper Flat mapper to map value.
     * @param none   Supplier of instance of {@link O} with {@code None} value.
     * @param <O>    Type of opt.
     * @return An {@link Opt} supplied by {@code none} if value is not present, or {@link Opt}
     * returned by {@code mapper}.
     */
    @NotNull
    public abstract <O extends Opt<O>> O flatMapTo(@NotNull LongFunction<? extends O> mapper, @NotNull Supplier<O> none);

    /**
     * Returns the value of this {@link Opt} if present, or {@code value} if not.
     *
     * @param value Other value to return if value of this {@link Opt} is not present (cannot be
     *              null).
     * @return Value of this {@link Opt} if present, or {@code value} if not.
     */
    public abstract long orElse(long value);

    /**
     * Returns the value of this {@link Opt} if present, or value supplied by {@code supplier} if
     * not.
     *
     * @param supplier Supplier of other value to return if value of this {@link Opt} is not present
     *                 (cannot be null).
     * @return Value of this {@link Opt} if present, or value supplied by {@code supplier} if not.
     */
    public abstract long orElseGet(@NotNull LongSupplier supplier);

    /**
     * Returns the value of this {@link Opt} if present, or value returned by {@code lazy}.
     *
     * @param lazy Lazy provider of value to return if value is not present.
     * @return Value of this {@link Opt} if present, or value returned by {@code lazy}.
     */
    public abstract long orElseLazy(@NotNull Lazy<? extends Long> lazy);

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
    public abstract <E extends Throwable> long orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E;

    /**
     * Returns a {@link LongStream} with value of this {@link Opt} or an empty {@link LongStream} if
     * value is not present.
     *
     * This uses {@code 1} for {@code true} and {@code 0} for {@code false} because there are no
     * BooleanStream (there is no reason to have too).
     *
     * @return {@link LongStream} with value of this {@link Opt} or an empty {@link LongStream} if
     * value is not present.
     */
    @NotNull
    public abstract LongStream stream();

    @Nullable
    @Override
    public Object getObjectValue() {
        return this.getValue();
    }

    /**
     * Creates a {@link OptionalLong} from this {@link Opt}.
     *
     * This uses {@code 1} for {@code true} and {@code 0} for {@code false} because there are no
     * OptionalBoolean (there is no reason to have too).
     *
     * @return {@link OptionalLong} of value if it is present, or {@link OptionalLong#empty()}.
     */
    @NotNull
    public abstract OptionalLong toOptional();

    /**
     * Creates a {@link Optional} from this {@link Opt}.
     *
     * @return {@link Optional} of value if it is not null, or {@link Optional#empty()} if this opt
     * is either empty or has a null value.
     */
    @NotNull
    public abstract Optional<Long> toBoxedOptional();
}
