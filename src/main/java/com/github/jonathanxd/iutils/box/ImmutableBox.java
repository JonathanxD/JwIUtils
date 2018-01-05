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
package com.github.jonathanxd.iutils.box;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A box that hold reference cannot be changed.
 *
 * @param <T> Type of value.
 */
public final class ImmutableBox<T> implements BaseBox<T> {

    /**
     * Empty box.
     */
    private static ImmutableBox<?> empty = new ImmutableBox<>();

    /**
     * Reference
     */
    private final T value;

    /**
     * Creates a immutable box of {@code null}.
     */
    private ImmutableBox() {
        this.value = null;
    }

    /**
     * Creates a immutable box that holds reference to {@code value}.
     *
     * @param value Value to hold reference to.
     */
    private ImmutableBox(T value) {
        this.value = value;
    }

    /**
     * Creates a box that holds reference to {@code value}.
     *
     * If the {@code value} is {@code null}, then {@link #empty()} is returned.
     *
     * @param value Value to hold reference to.
     * @param <T>   Type of value.
     * @return Box that holds reference to {@code value}.
     */
    public static <T> ImmutableBox<T> of(T value) {
        if (value == null)
            return ImmutableBox.empty();

        return new ImmutableBox<>(value);
    }

    /**
     * Returns an empty immutable box (aka a box that references to {@code null}).
     *
     * @param <T> Type of value.
     * @return Empty immutable box (aka a box that references to {@code null}).
     */
    @SuppressWarnings("unchecked")
    public static <T> T empty() {
        return (T) ImmutableBox.empty;
    }

    @Override
    public <R> ImmutableBox<R> mapBox(Function<T, R> function) {
        return new ImmutableBox<>(this.map(function));
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public boolean isPresent() {
        return this.getValue() != null;
    }

    @Override
    public T getOrElse(T another) {
        return (this.isPresent() ? this.getValue() : another);
    }

    @Override
    public T getOr(BaseBox<T> another) {
        return (this.isPresent() ? this.getValue() : another.getValue());
    }

    @Override
    public BaseBox<T> getOrBox(BaseBox<T> another) {
        return (this.isPresent() ? this : another);
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);

        if (this.isPresent()) {
            consumer.accept(this.getValue());
        }
    }

    @Override
    public ImmutableBox<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!this.isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : ImmutableBox.empty();
        }
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isPresent()) {
            return this.getValue();
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ImmutableBox)) {
            return false;
        }

        ImmutableBox<?> other = (ImmutableBox<?>) obj;
        return Objects.equals(this.getValue(), other.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(1, this.getValue());
    }

    @Override
    public String toString() {
        return String.format("Box[%s]", (this.isPresent() ? this.getValue() : "null"));
    }
}
