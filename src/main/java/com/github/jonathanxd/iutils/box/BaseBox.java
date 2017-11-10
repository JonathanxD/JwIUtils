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

import com.github.jonathanxd.iutils.box.primitiveboxes.IntBox;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Box is a value holder, boxes are commonly used to modify variables from different scopes (or
 * methods), example:
 * <pre>
 * {@code
 * int x = 0;
 * Consumer<Integer> consumer = (i) -> {
 *     x += 1;
 * };
 * }
 * </pre>
 * The value X needs to be "Local variable x defined in an enclosing scope must be final or
 * effectively final"<br> A way to solve this is using Box, see example using {@link MutableBox}
 *
 * <pre>
 * {@code
 * final MutableBox<String> x = MutableBox.of("Hey");
 * Consumer<Integer> consumer = (i) -> {
 *     x.set("Hi");
 * };
 * }
 * </pre>
 * For primitive types you can also use {@link IntBox} (or other variants).
 * <pre>
 * {@code
 * final IntBox x = new IntBox(0);
 * Consumer<Integer> consumer = (i) -> {
 *     x.add();
 * };
 * }
 * </pre>
 *
 * Box shares the same logic and code, but does not have {@link com.github.jonathanxd.iutils.container.HistoryContainer},
 * this will probably replace the old container.
 */
public interface BaseBox<T> extends Comparable<BaseBox<T>>, UnknownBox<T> {

    /**
     * Maps this box value to another value.
     *
     * @param function Mapper.
     * @param <R>      Other type.
     * @return Mapped value.
     */
    default <R> R map(Function<T, R> function) {
        return function.apply(this.get());
    }

    /**
     * Maps this box value to a box of another value.
     *
     * @param function Mapper.
     * @param <R>      Other type.
     * @return Mapped value.
     */
    <R> BaseBox<R> mapBox(Function<T, R> function);

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
     * @param another Another box to get value if the value of current box is not {@link
     *                #isPresent() present}.
     * @return Current value if present, or the value of {@code another} otherwise.
     */
    T getOr(BaseBox<T> another);

    /**
     * Returns {@code this} box if value is present, or the {@code another} box otherwise.
     *
     * @param another Box to return if the value of {@code this} box is not {@link #isPresent()
     *                present}.
     * @return {@code this} box if value is present, or the {@code another} box otherwise.
     */
    BaseBox<T> getOrBox(BaseBox<T> another);

    /**
     * Calls the {@code consumer} with the current value if present.
     *
     * @param consumer Consumer to call.
     */
    void ifPresent(Consumer<? super T> consumer);

    /**
     * Returns a empty box if the current value is not present or does not match {@code predicate},
     * or {@code this} box if is present and matches predicate.
     *
     * @param predicate Predicate.
     * @return Empty box if the current value is not present or does not match {@code predicate}, or
     * {@code this} box if is present and matches predicate.
     */
    BaseBox<T> filter(Predicate<? super T> predicate);

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
    default int compareTo(BaseBox<T> o) {
        if (getValue() instanceof Comparable) {
            @SuppressWarnings("unchecked")
            Comparable<T> comparable = (Comparable<T>) getValue();
            return comparable.compareTo(o.get());
        }
        return -1;
    }

    @Override
    default BaseBox<T> box() {
        return this;
    }

    @Override
    default Class<?> type() {
        return Object.class;
    }
}