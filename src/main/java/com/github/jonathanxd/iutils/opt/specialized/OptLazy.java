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

import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.None;
import com.github.jonathanxd.iutils.opt.Opt;

import java.util.Objects;
import java.util.function.Function;

/**
 * This class implements the same logic as {@link OptObject}, but change some functions to work
 * better with lazy value.
 *
 * @param <T> Type of value.
 */
public final class OptLazy<T> extends AbstractOptObject<T, OptLazy<T>> {

    private static final OptLazy<?> NONE = new OptLazy<>(null);

    private OptLazy(Lazy<T> value) {
        super(value == null ? None.INSTANCE : new SomeLazy<>(value));
    }

    /**
     * Creates an lazy {@link com.github.jonathanxd.iutils.opt.Opt}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Type of value.
     * @return Lazy {@link com.github.jonathanxd.iutils.opt.Opt}.
     */
    public static <T> OptLazy<T> optLazy(Lazy<T> lazy) {
        return new OptLazy<>(Objects.requireNonNull(lazy));
    }

    /**
     * Creates an lazy {@link com.github.jonathanxd.iutils.opt.Opt}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Type of value.
     * @return Lazy {@link com.github.jonathanxd.iutils.opt.Opt}.
     */
    public static <T> OptLazy<T> optLazyNullable(Lazy<T> lazy) {
        return lazy == null ? none() : new OptLazy<>(lazy);
    }

    /**
     * Gets a {@link None} {@link Lazy lazy} {@link com.github.jonathanxd.iutils.opt.Opt opt}.
     *
     * @param <T> Type of value.
     * @return {@link None} {@link Lazy lazy} {@link com.github.jonathanxd.iutils.opt.Opt opt}.
     */
    @SuppressWarnings("unchecked")
    public static <T> OptLazy<T> none() {
        return (OptLazy<T>) NONE;
    }

    /**
     * Returns true if value is evaluated (or if is not present).
     *
     * @return True if value is evaluated (or if is not present).
     */
    @SuppressWarnings("unchecked")
    public boolean isEvaluated() {
        return !this.isPresent() || ((SomeLazy<T>) this.getValueHolder()).getLazy().isEvaluated();
    }

    @Override
    public OptLazy<T> toNone() {
        return OptLazy.none();
    }

    /**
     * Maps the value of {@code this} {@link Opt} to a value of type {@link R} using {@code mapper}.
     *
     * @param mapper Mapper to map value.
     * @param <R>    Type of result value.
     * @return An {@link Opt} of mapped value if present, or an {@link Opt} of {@link None} if no
     * value is present.
     */
    public <R> OptLazy<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptLazy.none();

        return OptLazy.optLazy(Lazy.lazy(() -> Objects.requireNonNull(mapper.apply(this.getValue()))));
    }

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of value of type {@link R}.
     *
     * @param mapper Flat mapper to map value.
     * @param <R>    Type of result value.
     * @return An {@link Opt} of {@link None} if the value is not present, or the {@link Opt}
     * returned by {@code mapper} if value is present.
     */
    public <R> OptLazy<R> flatMap(Function<? super T, ? extends OptLazy<R>> mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptLazy.none();

        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

    /**
     * Returns the {@link Opt} of {@link Lazy}.
     *
     * @return {@link Opt} of {@link Lazy} (or empty if value is not present).
     */
    @SuppressWarnings("unchecked")
    public OptObject<Lazy<T>> toObjectOpt() {
        if(!this.isPresent())
            return OptObject.none();

        return OptObject.optObject(((SomeLazy<T>) this.getValueHolder()).getLazy().copy());
    }
}
