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
import com.github.jonathanxd.iutils.opt.specialized.OptBoolean;
import com.github.jonathanxd.iutils.opt.specialized.OptByte;
import com.github.jonathanxd.iutils.opt.specialized.OptChar;
import com.github.jonathanxd.iutils.opt.specialized.OptDouble;
import com.github.jonathanxd.iutils.opt.specialized.OptFloat;
import com.github.jonathanxd.iutils.opt.specialized.OptInt;
import com.github.jonathanxd.iutils.opt.specialized.OptLazy;
import com.github.jonathanxd.iutils.opt.specialized.OptLong;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;
import com.github.jonathanxd.iutils.opt.specialized.OptShort;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Improved Optional utility. {@link Opt} has an specialized version for each primitive type
 * (excluding void type). This {@link Opt Optional class} also uses a concept of that when you have
 * a value, you have {@code Some} value, when you do not have a value, you have {@code None}. All
 * classes are built with this concept in mind.
 *
 * This class is named {@code Opt} to avoid confusion with {@code Option} and {@code Optional} of
 * other libraries.
 *
 * @param <O> Type of this {@link Opt}. Implementations must only pass the same class type to this
 *            parameter.
 */
public interface Opt<O extends Opt<O>> {

    /**
     * Creates an Object Opt from a nullable {@code value}.
     *
     * @param value Nullable value.
     * @param <T>   Type of value.
     * @return Opt of {@code Some} if value is not null, or opt of {@code None} if value is null.
     */
    @NotNull
    static <T> OptObject<T> someNullable(@Nullable T value) {
        return OptObject.optObjectNullable(value);
    }

    /**
     * Creates an Object Opt from a non-nullable {@code value}.
     *
     * @param value Value.
     * @param <T>   Type of value.
     * @return {@link Opt} of {@code Some}.
     */
    @NotNull
    static <T> OptObject<T> someNotNull(@NotNull T value) {
        return OptObject.optObjectNotNull(value);
    }

    /**
     * Creates an Object Opt of {@code Some} {@code value}.
     *
     * @param value Value.
     * @param <T>   Type of value.
     * @return {@link Opt} of {@code Some} {@code value}.
     */
    @NotNull
    static <T> OptObject<T> some(@Nullable T value) {
        return OptObject.optObject(value);
    }

    /**
     * Creates an Object Opt of {@code None} value.
     *
     * @param <T> Type of value.
     * @return {@link Opt} of {@code None} value.
     */
    @Contract(pure = true)
    @NotNull
    static <T> OptObject<T> none() {
        return OptObject.none();
    }

    /**
     * Creates a boolean opt of {@code value}.
     *
     * @param value Boolean value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptBoolean someBoolean(boolean value) {
        return OptBoolean.optBoolean(value);
    }

    /**
     * Creates a {@code None} {@link OptBoolean}.
     *
     * @return {@code None} {@link OptBoolean}.
     */
    @Contract(pure = true)
    @NotNull
    static OptBoolean noneBoolean() {
        return OptBoolean.none();
    }

    /**
     * Creates a byte opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptByte someByte(byte value) {
        return OptByte.optByte(value);
    }

    /**
     * Creates a {@code None} {@link OptByte}.
     *
     * @return {@code None} {@link OptByte}.
     */
    @Contract(pure = true)
    @NotNull
    static OptByte noneByte() {
        return OptByte.none();
    }

    /**
     * Creates a char opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptChar someChar(char value) {
        return OptChar.optChar(value);
    }

    /**
     * Creates a {@code None} {@link OptChar}.
     *
     * @return {@code None} {@link OptChar}.
     */
    @Contract(pure = true)
    @NotNull
    static OptChar noneChar() {
        return OptChar.none();
    }

    /**
     * Creates a double opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptDouble someDouble(double value) {
        return OptDouble.optDouble(value);
    }

    /**
     * Creates a {@code None} {@link OptDouble}.
     *
     * @return {@code None} {@link OptDouble}.
     */
    @Contract(pure = true)
    @NotNull
    static OptDouble noneDouble() {
        return OptDouble.none();
    }

    /**
     * Creates a float opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptFloat someFloat(float value) {
        return OptFloat.optFloat(value);
    }

    /**
     * Creates a {@code None} {@link OptFloat}.
     *
     * @return {@code None} {@link OptFloat}.
     */
    @Contract(pure = true)
    @NotNull
    static OptFloat noneFloat() {
        return OptFloat.none();
    }

    /**
     * Creates a int opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptInt someInt(int value) {
        return OptInt.optInt(value);
    }

    /**
     * Creates a {@code None} {@link OptInt}.
     *
     * @return {@code None} {@link OptInt}.
     */
    @Contract(pure = true)
    @NotNull
    static OptInt noneInt() {
        return OptInt.none();
    }

    /**
     * Creates a Lazy.
     *
     * @param lazy Lazy initializer.
     * @return {@link Opt} of {@code Some} {@code lazy} initializer.
     */
    @Contract(pure = true)
    @NotNull
    static <T> OptLazy<T> someLazy(Lazy<T> lazy) {
        return OptLazy.optLazy(lazy);
    }

    /**
     * Creates a {@code None} {@link OptLazy}.
     *
     * @return {@code None} {@link OptLazy}.
     */
    @Contract(pure = true)
    @NotNull
    static OptLazy noneLazy() {
        return OptLazy.none();
    }

    /**
     * Creates a long opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptLong someLong(long value) {
        return OptLong.optLong(value);
    }

    /**
     * Creates a {@code None} {@link OptLong}.
     *
     * @return {@code None} {@link OptLong}.
     */
    @Contract(pure = true)
    @NotNull
    static OptLong noneLong() {
        return OptLong.none();
    }

    /**
     * Creates a short opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    static OptShort someShort(short value) {
        return OptShort.optShort(value);
    }

    /**
     * Creates a {@code None} {@link OptShort}.
     *
     * @return {@code None} {@link OptShort}.
     */
    @Contract(pure = true)
    @NotNull
    static OptShort noneShort() {
        return OptShort.none();
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
    @NotNull
    Opt<O> $or(@NotNull Supplier<? extends Opt<O>> supplier);

    /**
     * Returns this {@link Opt} if value is present, or returns {@link Opt} supplied by {@code
     * supplier} if not.
     *
     * @param supplier Supplier of other {@link Opt}
     * @return This {@link Opt} if value is present, or returns {@link Opt} supplied by {@code
     * supplier} if not.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    O or(@NotNull Supplier<? extends O> supplier);

    /**
     * Returns true if this {@link Opt optional} holds an instance of {@code Some}, false if holds
     * an instance of {@code None}.
     *
     * @return True if this {@link Opt optional} holds an instance of {@code Some}, false if holds
     * an instance of {@code None}.
     */
    boolean isPresent();

    /**
     * Casts this {@link Opt} to {@link O}. As the documentation says, all implementation of {@link
     * Opt} must only provide a {@link O} of the same implementing type.
     *
     * @return {@link O} instance.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    default O cast() {
        return (O) this;
    }

    /**
     * Gets object value.
     *
     * For primitive implementations, the value will be wrapped and returned.
     *
     * @return Object value.
     */
    @Nullable
    Object getObjectValue();

}
