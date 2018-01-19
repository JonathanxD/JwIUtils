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
package com.github.jonathanxd.iutils.function;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

public class UnaryOperators {

    /**
     * Same as {@link java.util.function.Function#compose(Function)}, but returns an {@link
     * UnaryOperator} instead of {@link Function}.
     *
     * Runs {@code before} and then runs {@code receiver} with value returned by {@code before}.
     * (Same as calling {@code before.andThen(receiver)}.
     *
     * @param receiver Function to run after {@code before}.
     * @param before   Function to run before {@code receiver}.
     * @param <T>      Value type.
     * @return Unary compose function.
     */
    public static <T> UnaryOperator<T> compose(UnaryOperator<T> receiver, UnaryOperator<T> before) {
        return f -> receiver.apply(before.apply(f));
    }

    /**
     * Same as {@link java.util.function.Function#andThen(Function)}, but returns an {@link
     * UnaryOperator} instead of {@link Function}.
     *
     * Runs {@code receiver} and then runs {@code after} with value returned by {@code receiver}.
     *
     * @param receiver Function to run before {@code after}.
     * @param after    Function to run after {@code receiver}.
     * @param <T>      Value type.
     * @return Unary compose function.
     */
    public static <T> UnaryOperator<T> andThen(UnaryOperator<T> receiver, UnaryOperator<T> after) {
        return f -> after.apply(receiver.apply(f));
    }

    /**
     * Returns an unary operator that increment the value by {@code value}.
     *
     * @param value Value to increment.
     * @return Unary operator that increment the value by {@code value}.
     */
    public static IntUnaryOperator increment(int value) {
        return t -> t + value;
    }

    /**
     * Returns an unary operator that increment the value by {@code value}.
     *
     * @param value Value to increment.
     * @return Unary operator that increment the value by {@code value}.
     */
    public static IntUnaryOperator increment(IntUnaryOperator value) {
        return t -> t + value.applyAsInt(t);
    }

    /**
     * Returns an unary operator that decrement the value by {@code value}.
     *
     * @param value Value to decrement.
     * @return Unary operator that decrement the value by {@code value}.
     */
    public static IntUnaryOperator decrement(int value) {
        return t -> t - value;
    }

    /**
     * Returns an unary operator that decrement the value by {@code value}.
     *
     * @param value Value to decrement.
     * @return Unary operator that decrement the value by {@code value}.
     */
    public static IntUnaryOperator decrement(IntUnaryOperator value) {
        return t -> t - value.applyAsInt(t);
    }
    
    /**
     * Returns an unary operator that multiple the value by {@code value}.
     *
     * @param value Value to multiply.
     * @return Unary operator that multiple the value by {@code value}.
     */
    public static IntUnaryOperator multiply(int value) {
        return t -> t * value;
    }

    /**
     * Returns an unary operator that multiple the value by {@code value}.
     *
     * @param value Value to multiply.
     * @return Unary operator that multiple the value by {@code value}.
     */
    public static IntUnaryOperator multiply(IntUnaryOperator value) {
        return t -> t * value.applyAsInt(t);
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static IntUnaryOperator divide(int value) {
        return t -> t / value;
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static IntUnaryOperator divide(IntUnaryOperator value) {
        return t -> t / value.applyAsInt(t);
    }

    // Long

    /**
     * Returns an unary operator that increment the value by {@code value}.
     *
     * @param value Value to increment.
     * @return Unary operator that increment the value by {@code value}.
     */
    public static LongUnaryOperator increment(long value) {
        return t -> t + value;
    }

    /**
     * Returns an unary operator that increment the value by {@code value}.
     *
     * @param value Value to increment.
     * @return Unary operator that increment the value by {@code value}.
     */
    public static LongUnaryOperator increment(LongUnaryOperator value) {
        return t -> t + value.applyAsLong(t);
    }

    /**
     * Returns an unary operator that decrement the value by {@code value}.
     *
     * @param value Value to decrement.
     * @return Unary operator that decrement the value by {@code value}.
     */
    public static LongUnaryOperator decrement(long value) {
        return t -> t - value;
    }

    /**
     * Returns an unary operator that decrement the value by {@code value}.
     *
     * @param value Value to decrement.
     * @return Unary operator that decrement the value by {@code value}.
     */
    public static LongUnaryOperator decrement(LongUnaryOperator value) {
        return t -> t - value.applyAsLong(t);
    }

    /**
     * Returns an unary operator that multiple the value by {@code value}.
     *
     * @param value Value to multiply.
     * @return Unary operator that multiple the value by {@code value}.
     */
    public static LongUnaryOperator multiply(long value) {
        return t -> t * value;
    }

    /**
     * Returns an unary operator that multiple the value by {@code value}.
     *
     * @param value Value to multiply.
     * @return Unary operator that multiple the value by {@code value}.
     */
    public static LongUnaryOperator multiply(LongUnaryOperator value) {
        return t -> t * value.applyAsLong(t);
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static LongUnaryOperator divide(long value) {
        return t -> t / value;
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static LongUnaryOperator divide(LongUnaryOperator value) {
        return t -> t / value.applyAsLong(t);
    }


    // Double

    /**
     * Returns an unary operator that increment the value by {@code value}.
     *
     * @param value Value to increment.
     * @return Unary operator that increment the value by {@code value}.
     */
    public static DoubleUnaryOperator increment(double value) {
        return t -> t + value;
    }

    /**
     * Returns an unary operator that increment the value by {@code value}.
     *
     * @param value Value to increment.
     * @return Unary operator that increment the value by {@code value}.
     */
    public static DoubleUnaryOperator increment(DoubleUnaryOperator value) {
        return t -> t + value.applyAsDouble(t);
    }

    /**
     * Returns an unary operator that decrement the value by {@code value}.
     *
     * @param value Value to decrement.
     * @return Unary operator that decrement the value by {@code value}.
     */
    public static DoubleUnaryOperator decrement(double value) {
        return t -> t - value;
    }

    /**
     * Returns an unary operator that decrement the value by {@code value}.
     *
     * @param value Value to decrement.
     * @return Unary operator that decrement the value by {@code value}.
     */
    public static DoubleUnaryOperator decrement(DoubleUnaryOperator value) {
        return t -> t - value.applyAsDouble(t);
    }

    /**
     * Returns an unary operator that multiple the value by {@code value}.
     *
     * @param value Value to multiply.
     * @return Unary operator that multiple the value by {@code value}.
     */
    public static DoubleUnaryOperator multiply(double value) {
        return t -> t * value;
    }

    /**
     * Returns an unary operator that multiple the value by {@code value}.
     *
     * @param value Value to multiply.
     * @return Unary operator that multiple the value by {@code value}.
     */
    public static DoubleUnaryOperator multiply(DoubleUnaryOperator value) {
        return t -> t * value.applyAsDouble(t);
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static DoubleUnaryOperator divide(double value) {
        return t -> t / value;
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static DoubleUnaryOperator divide(DoubleUnaryOperator value) {
        return t -> t / value.applyAsDouble(t);
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static DoubleUnaryOperator pow(double value, double power) {
        return t -> Math.pow(value, power);
    }

    /**
     * Returns an unary operator that divide the value by {@code value}.
     *
     * @param value Value to divide.
     * @return Unary operator that divide the value by {@code value}.
     */
    public static DoubleUnaryOperator pow(DoubleUnaryOperator value, DoubleUnaryOperator power) {
        return t -> Math.pow(value.applyAsDouble(t), power.applyAsDouble(t));
    }
}
