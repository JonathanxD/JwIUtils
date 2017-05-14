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
package com.github.jonathanxd.iutils.condition;

import com.github.jonathanxd.iutils.exception.ArraySizeException;
import com.github.jonathanxd.iutils.string.JString;

import java.util.function.Predicate;

/**
 * Conditions.
 */
public class Conditions {

    /**
     * Requires that {@code t} matches the {@code predicate}. If fails, throws an exception supplied
     * by {@link Def#INSTANCE} with default message.
     *
     * @param t         Element to check.
     * @param predicate Predicate to check element.
     * @param <T>       Element type.
     */
    public static <T> void require(T t, Predicate<? super T> predicate) {
        Conditions.require(t, predicate, "Provided value does not matches the requirement", Def.INSTANCE);
    }

    /**
     * Requires that {@code t} matches the {@code predicate}. If fails, throws an exception supplied
     * by {@link Def#INSTANCE} with {@code message}.
     *
     * @param t         Element to check.
     * @param predicate Predicate to check element.
     * @param message   Message of exception.
     * @param <T>       Element type.
     */
    public static <T> void require(T t, Predicate<? super T> predicate, String message) {
        Conditions.require(t, predicate, message, Def.INSTANCE);
    }

    /**
     * Requires that {@code t} matches the {@code predicate}. If fails, throws an exception supplied
     * by {@code exceptionProvider} with {@code message}.
     *
     * @param t                 Element to check.
     * @param predicate         Predicate to check element.
     * @param message           Message of exception.
     * @param exceptionProvider Function to create exception.
     * @param <T>               Element type.
     */
    public static <T> void require(T t, Predicate<? super T> predicate, String message, RuntimeExceptionFunction exceptionProvider) {
        if (!predicate.test(t))
            throw exceptionProvider.apply(message);
    }

    /**
     * Requires that {@code bool} returns true.
     *
     * @param bool Condition.
     */
    public static void require(boolean bool) {
        Conditions.require(bool, "Condition failed!", Def.INSTANCE);
    }

    /**
     * Requires that {@code bool} returns true.
     *
     * @param bool    Condition
     * @param message Message to pass to {@link Def#INSTANCE Default exception provider}.
     */
    public static void require(boolean bool, String message) {
        Conditions.require(bool, message, Def.INSTANCE);
    }

    /**
     * Requires that {@code bool} returns true.
     *
     * @param bool              Condition
     * @param message           Message to pass to {@code exceptionProvider}.
     * @param exceptionProvider Exception provider.
     */
    public static void require(boolean bool, String message, RuntimeExceptionFunction exceptionProvider) {
        if (!bool) {
            throw exceptionProvider.apply(message);
        }
    }

    /**
     * Requires that {@code o} do not be {@code null}.
     *
     * @param o   Object to check.
     * @param <T> Type of object.
     * @return Non-null {@code o}.
     */
    public static <T> T checkNotNull(T o) {
        return checkNotNull(o, "Null object value!", Def.INSTANCE);
    }

    /**
     * Requires that {@code o} do not be {@code null}.
     *
     * @param o       Object to check.
     * @param message Message to pass to {@link Def#INSTANCE Default exception provider}.
     * @param <T>     Type of object.
     * @return Non-null {@code o}.
     */
    public static <T> T checkNotNull(T o, String message) {
        return checkNotNull(o, message, Def.INSTANCE);
    }

    /**
     * Requires that {@code o} do not be {@code null}.
     *
     * @param o                 Object to check.
     * @param message           Message to pass to {@code exceptionProvider}.
     * @param exceptionProvider Exception provider.
     * @param <T>               Type of object.
     * @return Non-null {@code o}.
     */
    public static <T> T checkNotNull(T o, String message, RuntimeExceptionFunction exceptionProvider) {
        if (o == null) {
            throw exceptionProvider.apply(message);
        }

        return o;
    }

    /**
     * Requires that {@code o} be {@code null}.
     *
     * @param o   Object to check.
     * @param <T> Type of object.
     * @return Non-null {@code o}.
     */
    public static <T> T checkNull(T o) {
        return checkNull(o, "Not null object!", Def.INSTANCE);
    }

    /**
     * Requires that {@code o} be {@code null}.
     *
     * @param o       Object to check.
     * @param message Message to pass to {@link Def#INSTANCE Default exception provider}.
     * @param <T>     Type of object.
     * @return Non-null {@code o}.
     */
    public static <T> T checkNull(T o, String message) {
        return checkNull(o, message, Def.INSTANCE);
    }

    /**
     * Requires that {@code o} be {@code null}.
     *
     * @param o                 Object to check.
     * @param message           Message to pass to {@code exceptionProvider}.
     * @param exceptionProvider Exception provider.
     * @param <T>               Type of object.
     * @return Non-null {@code o}.
     */
    public static <T> T checkNull(Object o, String message, RuntimeExceptionFunction exceptionProvider) {
        if (o != null) {
            throw exceptionProvider.apply(message);
        }

        return null;
    }

    /**
     * Requires that {@code array} size is between {@code minimumSize} and {@code maximumSize}.
     *
     * @param array       Array.
     * @param minimumSize Minimum size.
     * @param maximumSize Maximum size.
     * @param <T>         Type of array.
     * @return {@code array}.
     */
    public static <T> T[] checkSize(T[] array, int minimumSize, int maximumSize) {
        return checkSize(array, minimumSize, maximumSize, JString.of("Array size doesn't match requirements. Minimum size: $min. Maximum size: $max. Current: ${length}",
                "min", minimumSize,
                "max", maximumSize,
                "length", array.length)
                .toString());
    }

    /**
     * Requires that {@code array} size is between {@code minimumSize} and {@code maximumSize}.
     *
     * @param array       Array.
     * @param minimumSize Minimum size.
     * @param maximumSize Maximum size.
     * @param message     Message to pass to {@link Def#INSTANCE Default exception provider}.
     * @param <T>         Type of array.
     * @return {@code array}.
     */
    public static <T> T[] checkSize(T[] array, int minimumSize, int maximumSize, String message) {
        return checkSize(array, minimumSize, maximumSize, message, ArraySizeException::new);
    }

    /**
     * Requires that {@code array} size is between {@code minimumSize} and {@code maximumSize}.
     *
     * @param array             Array.
     * @param minimumSize       Minimum size.
     * @param maximumSize       Maximum size.
     * @param message           Message to pass to {@code exceptionProvider}.
     * @param exceptionProvider Exception provider.
     * @param <T>               Type of array.
     * @return {@code array}.
     */
    public static <T> T[] checkSize(T[] array, int minimumSize, int maximumSize, String message, RuntimeExceptionFunction exceptionProvider) {
        if (array.length < minimumSize || array.length > maximumSize)
            throw exceptionProvider.apply(message);

        return array;
    }

    /**
     * Default exception provider.
     */
    private static class Def implements RuntimeExceptionFunction {

        static final Def INSTANCE = new Def();

        private Def() {
        }

        @Override
        public RuntimeException apply(String message) {
            return new IllegalArgumentException(message);
        }
    }
}
