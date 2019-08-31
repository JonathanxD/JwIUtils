/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.opt.specialized;

import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SpecializedOpt {
    private SpecializedOpt() {
    }

    /**
     * Creates a boolean opt of {@code value}.
     *
     * @param value Boolean value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    public static OptBoolean someBoolean(boolean value) {
        return OptBoolean.optBoolean(value);
    }

    /**
     * Creates a {@code None} {@link OptBoolean}.
     *
     * @return {@code None} {@link OptBoolean}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptBoolean noneBoolean() {
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
    public static OptByte someByte(byte value) {
        return OptByte.optByte(value);
    }

    /**
     * Creates a {@code None} {@link OptByte}.
     *
     * @return {@code None} {@link OptByte}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptByte noneByte() {
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
    public static OptChar someChar(char value) {
        return OptChar.optChar(value);
    }

    /**
     * Creates a {@code None} {@link OptChar}.
     *
     * @return {@code None} {@link OptChar}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptChar noneChar() {
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
    public static OptDouble someDouble(double value) {
        return OptDouble.optDouble(value);
    }

    /**
     * Creates a {@code None} {@link OptDouble}.
     *
     * @return {@code None} {@link OptDouble}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptDouble noneDouble() {
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
    public static OptFloat someFloat(float value) {
        return OptFloat.optFloat(value);
    }

    /**
     * Creates a {@code None} {@link OptFloat}.
     *
     * @return {@code None} {@link OptFloat}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptFloat noneFloat() {
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
    public static OptInt someInt(int value) {
        return OptInt.optInt(value);
    }

    /**
     * Creates a {@code None} {@link OptInt}.
     *
     * @return {@code None} {@link OptInt}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptInt noneInt() {
        return OptInt.none();
    }

    /**
     * Creates a long opt of {@code value}.
     *
     * @param value Value.
     * @return {@link Opt} of {@code Some} {@code value}
     */
    @Contract(pure = true)
    @NotNull
    public static OptLong someLong(long value) {
        return OptLong.optLong(value);
    }

    /**
     * Creates a {@code None} {@link OptLong}.
     *
     * @return {@code None} {@link OptLong}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptLong noneLong() {
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
    public static OptShort someShort(short value) {
        return OptShort.optShort(value);
    }

    /**
     * Creates a {@code None} {@link OptShort}.
     *
     * @return {@code None} {@link OptShort}.
     */
    @Contract(pure = true)
    @NotNull
    public static OptShort noneShort() {
        return OptShort.none();
    }
}
