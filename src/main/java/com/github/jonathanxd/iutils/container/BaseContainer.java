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
package com.github.jonathanxd.iutils.container;

import com.github.jonathanxd.iutils.container.primitivecontainers.IntContainer;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Container is a value holder, containers are commonly used to modify variables from different
 * scopes (or methods), example:
 * <pre>
 * {@code
 * int x = 0;
 * Consumer<Integer> consumer = (i) -> {
 *     x += 1;
 * };
 * }
 * </pre>
 * The value X needs to be "Local variable x defined in an enclosing scope must be final or
 * effectively final"<br> A way to solve this is using Container, see example using {@link
 * MutableContainer}
 *
 * <pre>
 * {@code
 * final MutableContainer<String> x = MutableContainer.of("Hey");
 * Consumer<Integer> consumer = (i) -> {
 *     x.set("Hi");
 * };
 * }
 * </pre>
 * For primitive types you can also use {@link IntContainer} (or other variants).
 * <pre>
 * {@code
 * final IntContainer x = new IntContainer(0);
 * Consumer<Integer> consumer = (i) -> {
 *     x.add();
 * };
 * }
 * </pre>
 */
public interface BaseContainer<T> extends Comparable<BaseContainer<T>>, UnknownContainer<T> {

    /**
     * Maps this container value to another value.
     *
     * @param function Mapper.
     * @param <R>      Other type.
     * @return Mapped value.
     */
    default <R> R map(Function<T, R> function) {
        return function.apply(this.get());
    }

    /**
     * Gets the current value.
     *
     * @return Current value.
     */
    T getValue();

    /**
     * Returns true if value is present, false otherwise.
     *
     * @return True if value is present, false otherwise.
     */
    boolean isPresent();

    /**
     * Returns current value if present, {@code another} otherwise.
     *
     * @param another Value to return if the current value is not {@link #isPresent() present}.
     * @return Current value if present, {@code another} otherwise.
     */
    T getOrElse(T another);

    /**
     * Returns current value if present, or the value of {@code another} otherwise.
     *
     * @param another Another container to get value if the value of current container is not {@link
     *                #isPresent() present}.
     * @return Current value if present, or the value of {@code another} otherwise.
     */
    T getOr(BaseContainer<T> another);

    /**
     * Returns {@code this} container if value is present, or the {@code another} container
     * otherwise.
     *
     * @param another Container to return if the value of {@code this} container is not {@link
     *                #isPresent() present}.
     * @return {@code this} container if value is present, or the {@code another} container
     * otherwise.
     */
    BaseContainer<T> getOrContainer(BaseContainer<T> another);

    /**
     * Calls the {@code consumer} with the current value if present.
     *
     * @param consumer Consumer to call.
     */
    void ifPresent(Consumer<? super T> consumer);

    /**
     * Returns a empty container if the current value is not present or does not match {@code
     * predicate}, or {@code this} container if is present and matches predicate.
     *
     * @param predicate Predicate.
     * @return Empty container if the current value is not present or does not match {@code
     * predicate}, or {@code this} container if is present and matches predicate.
     */
    BaseContainer<T> filter(Predicate<? super T> predicate);

    /**
     * Returns the value is present, or throws exception supplied by the {@code exceptionSupplier}
     * if the value is not {@link #isPresent() present}.
     *
     * @param exceptionSupplier Exception supplier.
     * @param <X>               Exception type.
     * @return Value if present.
     * @throws X If value is not {@link #isPresent() present}.
     */
    <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * Gets the current value.
     *
     * @return Current value.
     */
    default T get() {
        return this.getValue();
    }

    default Optional<T> toOptional() {
        if (!isPresent())
            return Optional.empty();
        return Optional.of(get());
    }

    @Override
    default int compareTo(BaseContainer<T> o) {
        if (getValue() instanceof Comparable) {
            @SuppressWarnings("unchecked")
            Comparable<T> comparable = (Comparable<T>) getValue();
            return comparable.compareTo(o.get());
        }
        return -1;
    }

    @Override
    default BaseContainer<T> box() {
        return this;
    }

    @Override
    default Class<?> type() {
        return Object.class;
    }
}