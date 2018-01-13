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

import com.github.jonathanxd.iutils.function.function.DoubleToDoubleFunction;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.AbstractOpt;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.ValueHolder;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

/**
 * Double specialized {@link Opt}.
 */
public final class OptDouble extends AbstractOpt<OptDouble, ValueHolder.DoubleValueHolder> {

    private static final OptDouble NONE = new OptDouble(ValueHolder.DoubleValueHolder.None.getInstance());
    private final ValueHolder.DoubleValueHolder holder;

    private OptDouble(ValueHolder.DoubleValueHolder holder) {
        this.holder = holder;
    }

    private OptDouble(double value) {
        this(new ValueHolder.DoubleValueHolder.Some(value));
    }

    /**
     * Creates an {@link OptDouble} from {@code optional}.
     *
     * @param optional Optional to create {@link OptDouble}.
     * @return An {@link Opt} of {@code Some} if {@link Optional#isPresent() Optional value is
     * present}, or {@code None} otherwise.
     */
    @SuppressWarnings({"unchecked", "OptionalUsedAsFieldOrParameterType"})
    public static OptDouble fromOptional(OptionalDouble optional) {
        if (optional.isPresent()) {
            return OptDouble.some(optional.getAsDouble());
        } else {
            return OptDouble.none();
        }
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @return An {@link Opt} of {@code Some} {@code value}.
     */
    @SuppressWarnings("unchecked")
    public static OptDouble optDouble(double value) {
        return new OptDouble(value);
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @return An {@link Opt} of {@code Some} {@code value}.
     */
    @SuppressWarnings("unchecked")
    public static OptDouble some(double value) {
        return new OptDouble(value);
    }

    /**
     * Creates a {@link Opt} with {@code None} value.
     *
     * @return {@link Opt} with {@code None} value.
     */
    @SuppressWarnings("unchecked")
    public static OptDouble none() {
        return NONE;
    }

    @Override
    public ValueHolder.DoubleValueHolder getValueHolder() {
        return this.holder;
    }

    /**
     * Gets the value holden by this optional.
     *
     * @return Value.
     * @throws NullPointerException If this {@link Opt} holds an {@code None}.
     */
    @SuppressWarnings("unchecked")
    public double getValue() {
        ValueHolder.DoubleValueHolder valueHolder = this.getValueHolder();

        if (!valueHolder.hasSome())
            throw new NullPointerException("No value found!");

        return valueHolder.getValue();
    }

    /**
     * Calls {@code consumer} if value is present.
     *
     * @param consumer Consumer to accept value if is present.
     */
    public void ifPresent(DoubleConsumer consumer) {
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
    public void ifPresent(DoubleConsumer consumer, Runnable elseRunnable) {
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
     * @return An {@link Opt} of {@code None} if value does not match predicate, or return same
     * {@link Opt} if either value is not present or value matches predicate.
     */
    public OptDouble filter(DoublePredicate predicate) {
        Objects.requireNonNull(predicate);

        if (this.isPresent())
            if (!predicate.test(this.getValue()))
                return OptDouble.none();

        return this;
    }

    /**
     * Maps the value of {@code this} {@link Opt} to a another value using {@code mapper}.
     *
     * @param mapper Mapper to map value.
     * @return An {@link Opt} of mapped value if present, or an {@link Opt} of {@code None} if no
     * value is present.
     */
    public OptDouble map(DoubleToDoubleFunction mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptDouble.none();

        return OptDouble.optDouble(mapper.apply(this.getValue()));
    }

    /**
     * Flat maps the value of {@code this} {@link Opt} to another {@link Opt}.
     *
     * @param mapper Flat mapper to map value.
     * @return An {@link Opt} of {@code None} if the value is not present, or the {@link Opt}
     * returned by {@code mapper} if value is present.
     */
    public OptDouble flatMap(DoubleFunction<? extends OptDouble> mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptDouble.none();

        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of type {@link O}.
     *
     * @param mapper Flat mapper to map value.
     * @param none   Supplier of instance of {@link O} with {@code None} value.
     * @param <O>    Type of opt.
     * @return An {@link Opt} supplied by {@code none} if value is not present, or {@link Opt}
     * returned by {@code mapper}.
     */
    public <O extends Opt<O, V>, V extends ValueHolder>
    O flatMapTo(DoubleFunction<? extends O> mapper, Supplier<O> none) {
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
    public double orElse(double value) {
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
    public double orElse(DoubleSupplier supplier) {
        Objects.requireNonNull(supplier);

        if (this.isPresent())
            return this.getValue();

        return supplier.getAsDouble();
    }

    /**
     * Returns the value of this {@link Opt} if present, or value returned by {@code lazy}.
     *
     * @param lazy Lazy provider of value to return if value is not present.
     * @return Value of this {@link Opt} if present, or value returned by {@code lazy}.
     */
    public double orElse(Lazy<? extends Double> lazy) { // :(, No specialized Lazy instances...
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
    public <E extends Throwable> double orElseFailStupidly(Supplier<? extends E> supplier) throws E {
        Objects.requireNonNull(supplier);

        if (this.isPresent())
            return this.getValue();

        throw Objects.requireNonNull(supplier.get());
    }

    /**
     * Returns a {@link DoubleStream} with value of this {@link Opt} or an empty {@link
     * DoubleStream} if value is not present.
     *
     * This uses {@code 1} for {@code true} and {@code 0} for {@code false} because there are no
     * BooleanStream (there is no reason to have too).
     *
     * @return {@link DoubleStream} with value of this {@link Opt} or an empty {@link DoubleStream}
     * if value is not present.
     */
    public DoubleStream stream() {
        if (this.isPresent())
            return DoubleStream.of(this.getValue());

        return DoubleStream.empty();
    }

    @Override
    public Object getObjectValue() {
        return this.getValue();
    }

    /**
     * Creates a {@link OptionalDouble} from this {@link Opt}.
     *
     * This uses {@code 1} for {@code true} and {@code 0} for {@code false} because there are no
     * OptionalBoolean (there is no reason to have too).
     *
     * @return {@link OptionalDouble} of value if it is present, or {@link OptionalDouble#empty()}.
     */
    public OptionalDouble toOptional() {
        return this.isPresent() ? OptionalDouble.of(this.getValue()) : OptionalDouble.empty();
    }

    /**
     * Creates a {@link Optional} from this {@link Opt}.
     *
     * @return {@link Optional} of value if it is not null, or {@link Optional#empty()} if this opt
     * is either empty or has a null value.
     */
    public Optional<Double> toBoxedOptional() {
        return this.isPresent() ? Optional.of(this.getValue()) : Optional.empty();
    }
}
