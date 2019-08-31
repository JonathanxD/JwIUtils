/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Mutable version of {@link BaseContainer}.
 *
 * @param <T> Type of value.
 */
public class MutableContainer<T> implements IMutableContainer<T> {

    /**
     * Empty container instance.
     */
    private static BaseContainer<?> empty = new MutableContainer<>();

    /**
     * Value history list.
     */
    private final List<T> valueHistory = new ArrayList<>();

    /**
     * Immutable list of value history.
     */
    private final List<T> immutableValueHistory = Collections.unmodifiableList(this.valueHistory);

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
    private BiFunction<BaseContainer<T>, T, T> applier = null;

    public MutableContainer() {
        this.value = null;
    }

    public MutableContainer(T value) {
        setValue(value);
    }

    /**
     * Creates a mutable history with default {@code value}.
     *
     * @param value Default value.
     * @param <T>   Type of value.
     * @return Mutable container with default {@code value}.
     */
    public static <T> MutableContainer<T> of(T value) {
        Objects.requireNonNull(value);
        return new MutableContainer<>(value);
    }

    /**
     * Returns a empty container.
     *
     * @param <T> Type of container.
     * @return Empty container.
     */
    @SuppressWarnings("unchecked")
    public static <T> T empty() {
        return (T) MutableContainer.empty;
    }

    @Override
    public void setMapper(BiFunction<BaseContainer<T>, T, T> mapper) {
        this.applier = mapper;
    }

    @Override
    public void setMapped(T value) {
        this.setValue(this.applier.apply(this, value));
    }

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        addToHistory(value);
    }

    @Override
    public boolean isPresent() {
        return getValue() != null;
    }

    @Override
    public T getOrElse(T another) {
        Objects.requireNonNull(another);
        return (isPresent() ? this.getValue() : another);
    }

    @Override
    public T getOr(BaseContainer<T> another) {
        return (isPresent() ? this.getValue() : another.getValue());
    }

    @Override
    public BaseContainer<T> getOrContainer(BaseContainer<T> another) {
        return (isPresent() ? this : another);
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        if (isPresent()) {
            consumer.accept(getValue());
        }
    }

    @Override
    public BaseContainer<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : MutableContainer.empty();
        }
    }

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
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

        if (!(obj instanceof MutableContainer)) {
            return false;
        }

        BaseContainer<?> other = (BaseContainer<?>) obj;

        return Objects.equals(this.value, other.getValue());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Container[%s]", (isPresent() ? this.value.toString() : "null"));
    }

    @Override
    public void addToHistory(T value) {
        if (value != null && historyEnabled)
            this.valueHistory.add(value);
    }

    @Override
    public List<T> getValueHistory() {
        return this.immutableValueHistory;
    }

    @Override
    public boolean setHistoryEnabled(boolean enable) {
        boolean old = this.historyEnabled;
        this.historyEnabled = enable;
        return old;
    }

    @Override
    public void clearHistory() {
        this.valueHistory.clear();
    }
}
