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

import com.github.jonathanxd.iutils.function.checked.supplier.CSupplier;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.ValueHolder;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Object specialized {@link Opt}.
 *
 * @param <T> Type of value.
 */
public final class OptObject<T> extends AbstractOptObject<T, ValueHolder.ObjectValueHolder<T>, OptObject<T>> {

    private static final OptObject<?> NONE = new OptObject<>(ValueHolder.ObjectValueHolder.None.getInstance());
    private final ValueHolder.ObjectValueHolder<T> holder;

    private OptObject(ValueHolder.ObjectValueHolder<T> holder) {
        this.holder = holder;
    }

    private OptObject(T value) {
        this(new ValueHolder.ObjectValueHolder.Some<>(value));
    }

    /**
     * Creates an {@link OptObject} from {@code optional}.
     *
     * @param optional Optional to create {@link OptObject}.
     * @param <T>      Type of value.
     * @return An {@link Opt} of {@code Some} if {@link Optional#isPresent() Optional value is
     * present}, or {@code None} otherwise.
     */
    @SuppressWarnings({"unchecked", "OptionalUsedAsFieldOrParameterType"})
    public static <T> OptObject<T> fromOptional(Optional<T> optional) {
        return optional.map(OptObject::some).orElseGet(OptObject::none);
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @param <T>   Type of value.
     * @return An {@link Opt} of {@code Some} {@code value}.
     */
    @SuppressWarnings("unchecked")
    public static <T> OptObject<T> optObject(T value) {
        return new OptObject<>(value);
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @param <T>   Type of value.
     * @return An {@link Opt} of {@code Some} {@code value}.
     */
    @SuppressWarnings("unchecked")
    public static <T> OptObject<T> some(T value) {
        return new OptObject<>(value);
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @param <T>   Type of value.
     * @return An {@link Opt} of {@code Some} if value is not null, or an {@link Opt} of {@code
     * None} if value is null.
     */
    @SuppressWarnings("unchecked")
    public static <T> OptObject<T> optObjectNullable(T value) {
        return value == null ? (OptObject<T>) OptObject.NONE : new OptObject<>(value);
    }

    /**
     * Returns an {@link Opt} of {@link T} if {@code supplier} returns a value, returns a {@link
     * Opt} with {@code None} value if {@code supplier} throws an exception.
     *
     * @param supplier Supplier of value.
     * @param <T>      Type of value.
     * @return {@link Opt} of {@link T} if {@code supplier} returns a value, returns a {@link Opt}
     * with {@code None} value if {@code supplier} throws an exception.
     */
    public static <T> OptObject<T> $try(CSupplier<T> supplier) {
        try {
            return OptObject.optObjectNullable(supplier.getChecked());
        } catch (Throwable t) {
            return OptObject.none();
        }
    }

    /**
     * Creates an {@link Opt} from {@code value}.
     *
     * @param value Value to create {@link Opt}.
     * @param <T>   Type of value.
     * @return An {@link Opt} of {@code Some} if value is not null, or an {@link Opt} of {@code
     * None} if value is null.
     */
    @SuppressWarnings("unchecked")
    public static <T> OptObject<T> optObjectNotNull(T value) {
        return new OptObject<>(Objects.requireNonNull(value));
    }

    /**
     * Creates a {@link Opt} with {@code None} value.
     *
     * @param <T> Type of value.
     * @return {@link Opt} with {@code None} value.
     */
    @SuppressWarnings("unchecked")
    public static <T> OptObject<T> none() {
        return (OptObject<T>) NONE;
    }

    @Override
    public ValueHolder.ObjectValueHolder<T> getValueHolder() {
        return this.holder;
    }

    @Override
    public OptObject<T> toNone() {
        return OptObject.none();
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
    public <R> OptObject<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptObject.none();

        return OptObject.optObjectNotNull(Objects.requireNonNull(mapper.apply(this.getValue())));
    }

    /**
     * Flat maps the value of {@code this} {@link Opt} to an {@link Opt} of value of type {@link
     * R}.
     *
     * @param mapper Flat mapper to map value.
     * @param <R>    Type of result value.
     * @return An {@link Opt} of {@code None} if the value is not present, or the {@link Opt}
     * returned by {@code mapper} if value is present.
     */
    public <R> OptObject<R> flatMap(Function<? super T, ? extends OptObject<R>> mapper) {
        Objects.requireNonNull(mapper);

        if (!this.isPresent())
            return OptObject.none();

        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

}
