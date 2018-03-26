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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

/**
 * Lazily evaluated opt.
 *
 * This class implements the same logic as {@link OptObject}, but change some functions to work
 * better with lazy value.
 *
 * Note that {@link #hashCode()} and {@link #equals(Object)} requires evaluation of the value, so if
 * the value is not evaluated, these operations will trigger the evaluation.
 *
 * @param <T> Type of value.
 */
public abstract class OptLazy<T> implements BaseOptObject<T, OptLazy<T>> {

    OptLazy() {
    }

    /**
     * Creates an lazy {@link Opt}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Type of value.
     * @return Lazy {@link Opt}.
     */
    @NotNull
    public static <T> OptLazy<T> optLazy(@NotNull Lazy<T> lazy) {
        return new SomeLazy<>(Objects.requireNonNull(lazy));
    }

    /**
     * Creates an lazy {@link Opt}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Type of value.
     * @return Lazy {@link Opt}.
     */
    @NotNull
    public static <T> OptLazy<T> some(@NotNull Lazy<T> lazy) {
        return new SomeLazy<>(Objects.requireNonNull(lazy));
    }

    /**
     * Creates an lazy {@link Opt}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Type of value.
     * @return Lazy {@link Opt}.
     */
    @NotNull
    public static <T> OptLazy<T> optLazyNullable(@Nullable Lazy<T> lazy) {
        return lazy == null ? none() : new SomeLazy<>(lazy);
    }

    /**
     * Gets a {@code None} {@link Lazy lazy} {@link Opt opt}.
     *
     * @param <T> Type of value.
     * @return {@code None} {@link Lazy lazy} {@link Opt opt}.
     */
    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    @NotNull
    public static <T> OptLazy<T> none() {
        return (OptLazy<T>) NoneLazy.NONE;
    }

    /**
     * Returns true if value is evaluated (or if is not present).
     *
     * @return True if value is evaluated (or if is not present).
     */
    @SuppressWarnings("unchecked")
    public abstract boolean isEvaluated();

    @NotNull
    @Override
    public OptLazy<T> toNone() {
        return OptLazy.none();
    }

    /**
     * Maps the value of {@code this} {@link Opt} to a value of type {@link R} using {@code
     * mapper}.
     *
     * @param mapper Mapper to map value.
     * @param <R>    Type of result value.
     * @return An {@link Opt} of mapped value if present, or an {@link Opt} of {@code None} if no
     * value is present.
     */
    @NotNull
    public abstract <R> OptLazy<R> map(@NotNull Function<? super T, ? extends R> mapper);

    /**
     * Maps the value of {@code this} {@link Opt} to a lazy of type {@link R}.
     *
     * @param mapper Mapper to map value.
     * @param <R>    Type of result value.
     * @return An {@link Opt} of mapped value if present, or an {@link Opt} of {@code None} if no
     * value is present.
     */
    @NotNull
    public abstract <R> OptLazy<R> mapLazy(@NotNull Function<? super T, Lazy<R>> mapper);

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of value of type {@link
     * R}.
     *
     * @param mapper Flat mapper to map value.
     * @param <R>    Type of result value.
     * @return An {@link Opt} of {@code None} if the value is not present, or the {@link Opt}
     * returned by {@code mapper} if value is present.
     */
    @NotNull
    public abstract <R> OptLazy<R> flatMap(@NotNull Function<? super T, ? extends OptLazy<R>> mapper);

    /**
     * Returns the {@link Opt} of {@link Lazy}.
     *
     * @return {@link Opt} of {@link Lazy} (or empty if value is not present).
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public abstract OptObject<Lazy<T>> toObjectOpt();
}
