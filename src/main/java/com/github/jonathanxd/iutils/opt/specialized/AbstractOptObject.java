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

import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.AbstractOpt;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.ValueHolder;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class AbstractOptObject<T,
        V extends ValueHolder.ObjectValueHolder<T>,
        O extends AbstractOptObject<T, V, O>> extends AbstractOpt<O, V> {

    /**
     * Gets the value holden by this optional.
     *
     * @return Value.
     * @throws NullPointerException If this {@link Opt} holds an {@code None}.
     */
    @SuppressWarnings("unchecked")
    public final T getValue() {
        V valueHolder = this.getValueHolder();

        if (!valueHolder.hasSome())
            throw new NullPointerException("No value found!");

        return valueHolder.getValue();
    }

    @Override
    public final Object getObjectValue() {
        return this.getValue();
    }

    /**
     * Calls {@code consumer} if value is present.
     *
     * @param consumer Consumer to accept value if is present.
     */
    public final void ifPresent(Consumer<? super T> consumer) {
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
    public final void ifPresent(Consumer<? super T> consumer, Runnable elseRunnable) {
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
    @SuppressWarnings("unchecked")
    public final O filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);

        if (this.isPresent())
            if (!predicate.test(this.getValue()))
                return this.toNone();

        return (O) this;
    }

    /**
     * Converts this {@link AbstractOptObject} of {@code Some} to {@link AbstractOptObject} with
     * {@code None} value.
     *
     * @return {@link AbstractOptObject} with {@code None} value.
     */
    public abstract O toNone();

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of type {@link O2}.
     *
     * @param mapper Flat mapper to map value.
     * @param none   Supplier of instance of {@link O2} with {@code None} value.
     * @param <O2>   Type of opt.
     * @return An {@link Opt} supplied by {@code none} if value is not present, or {@link Opt}
     * returned by {@code mapper}.
     */
    public final <O2 extends Opt<O2, V2>, V2 extends ValueHolder>
    O2 flatMapTo(Function<? super T, ? extends O2> mapper, Supplier<O2> none) {
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
    public final T orElse(T value) {
        Objects.requireNonNull(value);

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
    public final T orElseGet(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);

        if (this.isPresent())
            return this.getValue();

        return Objects.requireNonNull(supplier.get());
    }

    /**
     * Returns the value of this {@link Opt} if present, or value returned by {@code lazy}.
     *
     * @param lazy Lazy provider of value to return if value is not present.
     * @return Value of this {@link Opt} if present, or value returned by {@code lazy}.
     */
    public final T orElseLazy(Lazy<? extends T> lazy) {
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
    public final <E extends Throwable> T orElseFailStupidly(Supplier<? extends E> supplier) throws E {
        Objects.requireNonNull(supplier);

        if (this.isPresent())
            return this.getValue();

        throw Objects.requireNonNull(supplier.get());
    }

    /**
     * Returns a {@link Stream} with value of this {@link Opt} or an empty {@link Stream} if value
     * is not present..
     *
     * @return {@link Stream} with value of this {@link Opt} or an empty {@link Stream} if value is
     * not present.
     */
    public final Stream<T> stream() {
        if (this.isPresent())
            return StreamSupport.stream(this.spliterator(), false);

        return Stream.empty();
    }

    /**
     * Returns an iterator of this value.
     *
     * @return Iterator of this value.
     */
    public final Iterator<T> iterator() {
        return IteratorUtil.singleSupplied(this::getValue);
    }

    /**
     * Returns an iterator of this value.
     *
     * @return Iterator of this value.
     */
    public final Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), 1, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
    }

    /**
     * Creates a {@link Optional} from this {@link Opt}.
     *
     * @return {@link Optional} of value if it is not null, or {@link Optional#empty()} if this opt
     * is either empty or has a null value.
     */
    public final Optional<T> toOptional() {
        return this.isPresent() ? Optional.ofNullable(this.getValue()) : Optional.empty();
    }
}
