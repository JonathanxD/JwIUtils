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
package com.github.jonathanxd.iutils.testing;

import com.github.jonathanxd.iutils.opt.Opt;

/**
 * Testing base interface
 */
public interface Testing {

    /**
     * Returns next available value, or {@link Opt} of {@link com.github.jonathanxd.iutils.opt.None}
     * if there is no more values.
     *
     * @return Next available value.
     */
    Opt<?> nextValue();

    /**
     * Expects the available value to be {@link Object#equals(Object)} to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    void expect(Object value) throws AssertionError;

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(boolean value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(byte value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(char value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(short value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(int value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(float value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(long value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects the available value to be equals ({@code ==}) to {@code value}.
     *
     * @param value Value to check equality.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(double value) throws AssertionError {
        this.expect((Object) value);
    }

    /**
     * Expects to available value be an array and all values of array to be equals to all values of
     * {@code value} in the same order.
     *
     * @param array Array to check value.
     * @throws AssertionError if equality assertion fail.
     */
    default void expect(Object[] array) throws AssertionError {

    }

}
