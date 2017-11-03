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
package com.github.jonathanxd.iutils.box;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Mutable version of {@link BaseBox}.
 *
 * @param <T> Type of value.
 */
public class MutableBox<T> implements IMutableBox<T> {

    /**
     * Empty container instance.
     */
    private static BaseBox<?> empty = new MutableBox<>();

    /**
     * Current value.
     */
    private T value;

    /**
     * Is the value history enabled.
     */
    private boolean historyEnabled = false;

    /**
     * Applier
     */
    private BiFunction<BaseBox<T>, T, T> applier = null;

    public MutableBox() {
        this.value = null;
    }

    public MutableBox(T value) {
        this.value = value;
    }

    /**
     * Creates a mutable box with default {@code value}.
     *
     * @param value Default value.
     * @param <T>   Type of value.
     * @return Mutable box with default {@code value}.
     */
    public static <T> MutableBox<T> of(T value) {
        Objects.requireNonNull(value);

        return new MutableBox<>(value);
    }

    /**
     * Returns a empty box.
     *
     * @param <T> Type of box.
     * @return Empty box.
     */
    @SuppressWarnings("unchecked")
    public static <T> T empty() {
        return (T) MutableBox.empty;
    }

    @Override
    public void setMapper(BiFunction<BaseBox<T>, T, T> mapper) {
        this.applier = mapper;
    }

    @Override
    public void setMapped(T value) {
        this.setValue(this.applier.apply(this, value));
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean isPresent() {
        return this.getValue() != null;
    }

    @Override
    public T getOrElse(T another) {
        Objects.requireNonNull(another);

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
            consumer.accept(getValue());
        }
    }

    @Override
    public BaseBox<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);

        if (!this.isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : MutableBox.empty();
        }
    }

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        T value = this.getValue();
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof MutableBox)) {
            return false;
        }

        BaseBox<?> other = (BaseBox<?>) obj;

        return Objects.equals(this.getValue(), other.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(2, this.getValue());
    }

    @Override
    public String toString() {
        return String.format("Box[%s]", (this.isPresent() ? this.getValue() : "null"));
    }

}
