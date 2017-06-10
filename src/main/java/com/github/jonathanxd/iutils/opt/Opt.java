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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Improved Optional utility. {@link Opt} has an specialized version for each primitive type
 * (excluding void type) and is intended to NEVER return {@code null}, Java {@link
 * java.util.Optional} supports {@code null} when using {@link java.util.Optional#orElse(Object)} or
 * {@link java.util.Optional#orElseGet(Supplier)} and does support {@link
 * com.github.jonathanxd.iutils.object.Lazy Lazy evaluated values}. This {@link Opt Optional class}
 * also uses a concept of that when you have a value, you have {@link Some} value, when you do not
 * have a value, you have {@link None}. All classes are built with this concept in mind.
 *
 * All {@link Opt} specialized classes extends {@link AbstractOpt} and holds a {@link ValueHolder},
 * a {@link ValueHolder} is a value-based class which have specialized versions, one for each
 * primitive, one for objects and one for {@link None}, the {@code None} value holder may be present
 * in any {@link Opt} even if it is a primitive specialized {@link Opt} (remember {@link Some} and
 * {@link None}?), all specialized versions extends {@link Some}, and {@link None} version is
 * singleton. {@link ValueHolder ValueHolders} are only like boxed values and does not provide any
 * operation.
 *
 * Specialized value holder exists to provide zero-boxing-overhead operations, but it is not more
 * faster than plain primitive types.
 *
 * This class is named {@code Opt} to avoid confusion with {@link com.github.jonathanxd.iutils.option
 * Option utility}.
 *
 * This class has specialized version for objects and primitive types.
 *
 * @param <O> Type of this {@link Opt}. Implementations must only pass the same class type to this
 *            parameter.
 */
public interface Opt<O extends Opt<O>> {

    /**
     * Creates an Opt from a nullable {@code value}.
     *
     * @param value Nullable value.
     * @param <T>   Type of value.
     * @return Opt of {@link Some} if value is not null, or opt of {@link None} if value is null.
     */
    static <T> OptObject<T> optNullable(T value) {
        return OptObject.optObject(value);
    }

    /**
     * Creates an Opt from a non-nullable {@code value}.
     *
     * @param value Value.
     * @param <T>   Type of value.
     * @return {@link Opt} of {@link Some}.
     */
    static <T> OptObject<T> opt(T value) {
        return OptObject.optObject(value);
    }

    /**
     * Returns this {@link Opt} if value is present, or returns {@link Opt} supplied by {@code
     * supplier} if not.
     *
     * Returns {@link Opt} instead of {@link O} (useful in some cases where types are partially
     * erased).
     *
     * @param supplier Supplier of other {@link Opt}
     * @return This {@link Opt} if value is present, or returns {@link Opt} supplied by {@code
     * supplier} if not.
     */
    @SuppressWarnings("unchecked")
    default Opt<O> $or(Supplier<? extends Opt<O>> supplier) {
        Objects.requireNonNull(supplier);
        if (this.isPresent())
            return this;

        return Objects.requireNonNull(supplier.get());
    }

    /**
     * Returns this {@link Opt} if value is present, or returns {@link Opt} supplied by {@code
     * supplier} if not.
     *
     * @param supplier Supplier of other {@link Opt}
     * @return This {@link Opt} if value is present, or returns {@link Opt} supplied by {@code
     * supplier} if not.
     */
    @SuppressWarnings("unchecked")
    default O or(Supplier<? extends O> supplier) {
        Objects.requireNonNull(supplier);
        if (this.isPresent())
            return (O) this;

        return Objects.requireNonNull(supplier.get());
    }

    /**
     * Returns true if this {@link Opt optional} holds an instance of {@link Some}, false if holds
     * an instance of {@link None}.
     *
     * @return True if this {@link Opt optional} holds an instance of {@link Some}, false if holds
     * an instance of {@link None}.
     */
    default boolean isPresent() {
        return this.getValueHolder() instanceof Some;
    }

    /**
     * Returns the value holder with the value of this {@link Opt}.
     *
     * @return The value holder with the value of this {@link Opt}.
     */
    ValueHolder getValueHolder();

    /**
     * Casts this {@link Opt} to {@link O}. As the documentation says, all implementation of {@link
     * Opt} must only provide a {@link O} of the same type.
     *
     * @return {@link O} instance.
     */
    @SuppressWarnings("unchecked")
    default O cast() {
        return (O) this;
    }

    /**
     * Gets object value.
     *
     * @return Object value.
     * @implNote Boxing should be made in the method implementation and not cached.
     */
    Object getObjectValue();
}
