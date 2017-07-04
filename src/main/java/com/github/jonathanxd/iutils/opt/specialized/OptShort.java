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
package com.github.jonathanxd.iutils.opt.specialized;

import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.function.ShortToShortFunction;
import com.github.jonathanxd.iutils.function.predicate.ShortPredicate;
import com.github.jonathanxd.iutils.function.supplier.ShortSupplier;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.AbstractOpt;
import com.github.jonathanxd.iutils.opt.None;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.Some;
import com.github.jonathanxd.iutils.opt.ValueHolder;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Short specialized {@link Opt}.
 */
public final class OptShort extends AbstractOpt<OptShort> {

    private static final OptShort NONE = new OptShort();

    private OptShort() {
        super(None.INSTANCE);
    }

    private OptShort(short value) {
        super(new SomeShort(value));
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @return An {@link Opt} of {@link Some} if value is not null, or an {@link Opt} of {@link
     * None} if value is null.
     */
    @SuppressWarnings("unchecked")
    public static OptShort optShort(short value) {
        return new OptShort(value);
    }

    /**
     * Creates a {@link Opt} with {@link None} value.
     *
     * @return {@link Opt} with {@link None} value.
     */
    @SuppressWarnings("unchecked")
    public static OptShort none() {
        return NONE;
    }

    /**
     * Gets the value holden by this optional.
     *
     * @return Value.
     * @throws NullPointerException If this {@link Opt} holds an {@link None}.
     */
    @SuppressWarnings("unchecked")
    public short getValue() {
        ValueHolder valueHolder = this.getValueHolder();

        if (valueHolder instanceof None)
            throw new NullPointerException("No value found!");

        return ((SomeShort) valueHolder).getValue();
    }

    /**
     * Calls {@code consumer} if value is present.
     *
     * @param consumer Consumer to accept value if is present.
     */
    public void ifPresent(ShortConsumer consumer) {
        Objects.requireNonNull(consumer);

        if (this.isPresent())
            consumer.accept(this.getValue());
    }

    /**
     * Calls {@code consumer} with value if present, or calls {@code elseRunnable} if value is not
     * present.
     *
     * @param consumer     Consumer to accept value if is present.
     * @param elseRunnable Runnable to call if value is not present.
     */
    public void ifPresent(ShortConsumer consumer, Runnable elseRunnable) {
        Objects.requireNonNull(consumer);

        if (this.isPresent())
            consumer.accept(this.getValue());
        else
            elseRunnable.run();
    }

    /**
     * Test value against {@code predicate} if value is present.
     *
     * @param predicate Predicate to test value.
     * @return An {@link Opt} of {@link None} if value does not match predicate, or return same
     * {@link Opt} if either value is not present or value matches predicate.
     */
    public OptShort filter(ShortPredicate predicate) {
        Objects.requireNonNull(predicate);

        if (this.isPresent())
            if (!predicate.test(this.getValue()))
                return OptShort.none();

        return this;
    }

    /**
     * Maps the value of {@code this} {@link Opt} to a another value using {@code mapper}.
     *
     * @param mapper Mapper to map value.
     * @return An {@link Opt} of mapped value if present, or an {@link Opt} of {@link None} if no
     * value is present.
     */
    public OptShort map(ShortToShortFunction mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptShort.none();

        return OptShort.optShort(mapper.apply(this.getValue()));
    }

    /**
     * Flat maps the value of {@code this} {@link Opt} to another {@link Opt}.
     *
     * @param mapper Flat mapper to map value.
     * @return An {@link Opt} of {@link None} if the value is not present, or the {@link Opt}
     * returned by {@code mapper} if value is present.
     */
    public OptShort flatMap(ShortFunction<? extends OptShort> mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptShort.none();

        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of type {@link O}.
     *
     * @param mapper Flat mapper to map value.
     * @param none   Supplier of instance of {@link O} with {@link None} value.
     * @param <O>    Type of opt.
     * @return An {@link Opt} supplied by {@code none} if value is not present, or {@link Opt}
     * returned by {@code mapper}.
     */
    public <O extends Opt<O>> O flatMapTo(ShortFunction<? extends O> mapper, Supplier<O> none) {
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(none);

        if (!this.isPresent())
            return Objects.requireNonNull(none.get());

        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

    /**
     * Returns the value of this {@link Opt} if present, or {@code value} if not.
     *
     * @param value Other value to return if value of this {@link Opt} is not present (cannot be
     *              null).
     * @return Value of this {@link Opt} if present, or {@code value} if not.
     */
    public short orElse(short value) {

        if (this.isPresent())
            return this.getValue();

        return value;
    }

    /**
     * Returns the value of this {@link Opt} if present, or value supplied by {@code supplier} if
     * not.
     *
     * @param supplier Supplier of other value to return if value of this {@link Opt} is not present
     *                 (cannot be null).
     * @return Value of this {@link Opt} if present, or value supplied by {@code supplier} if not.
     */
    public short orElse(ShortSupplier supplier) {
        Objects.requireNonNull(supplier);

        if (this.isPresent())
            return this.getValue();

        return supplier.get();
    }

    /**
     * Returns the value of this {@link Opt} if present, or value returned by {@code lazy}.
     *
     * @param lazy Lazy provider of value to return if value is not present.
     * @return Value of this {@link Opt} if present, or value returned by {@code lazy}.
     */
    public short orElse(Lazy<? extends Short> lazy) { // :(, No specialized Lazy instances...
        Objects.requireNonNull(lazy);

        if (this.isPresent())
            return this.getValue();

        return Objects.requireNonNull(lazy.get());
    }

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
    public <E extends Throwable> short orElseFailStupidly(Supplier<? extends E> supplier) throws E {
        Objects.requireNonNull(supplier);

        if (this.isPresent())
            return this.getValue();

        throw Objects.requireNonNull(supplier.get());
    }

    /**
     * Returns a {@link IntStream} with value of this {@link Opt} or an empty {@link IntStream} if
     * value is not present.
     *
     * This uses {@code 1} for {@code true} and {@code 0} for {@code false} because there are no
     * BooleanStream (there is no reason to have too).
     *
     * @return {@link IntStream} with value of this {@link Opt} or an empty {@link IntStream} if
     * value is not present.
     */
    public IntStream stream() {
        if (this.isPresent())
            return IntStream.of(this.getValue());

        return IntStream.empty();
    }

    @Override
    public Object getObjectValue() {
        return this.getValue();
    }

    /**
     * Creates a {@link OptionalInt} from this {@link Opt}.
     *
     * This uses {@code 1} for {@code true} and {@code 0} for {@code false} because there are no
     * OptionalBoolean (there is no reason to have too).
     *
     * @return {@link OptionalInt} of value if it is present, or {@link OptionalInt#empty()}.
     */
    public OptionalInt toOptional() {
        return this.isPresent() ? OptionalInt.of(this.getValue()) : OptionalInt.empty();
    }

    /**
     * Creates a {@link Optional} from this {@link Opt}.
     *
     * @return {@link Optional} of value if it is not null, or {@link Optional#empty()} if this opt
     * is either empty or has a null value.
     */
    public Optional<Short> toBoxedOptional() {
        return this.isPresent() ? Optional.of(this.getValue()) : Optional.empty();
    }
}
