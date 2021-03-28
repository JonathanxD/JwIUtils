/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Utilities to work with recursion.
 */
public final class RecursionUtil {

    private RecursionUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Recursively foreach elements of {@code list}.
     *
     * Recursive loop stops when {@code function} returns a null value for {@link
     * Function#apply(Object)} method.
     *
     * @param list     List.
     * @param function Function to extract list from the list value.
     * @param <T>      Type of values.
     */
    public static <T> void recursiveForEach(List<? extends T> list, Function<T, List<? extends T>> function) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(function);

        for (T t : list) {
            List<? extends T> apply = function.apply(t);

            if (apply != null) {
                RecursionUtil.recursiveForEach(apply, function);
            }
        }
    }

    /**
     * Recursively foreach a value.
     *
     * Recursive loop stops when {@code func} returns a null value for {@link
     * Function#apply(Object)} method.
     *
     * @param value Value.
     * @param func  Function to extract new value (the value returned by the function is nullable).
     * @param <T>   Type of value.
     */
    public static <T> void recursiveValForeach(T value, Function<T, T> func) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(func);

        T other = func.apply(value);

        if (other != null)
            RecursionUtil.recursiveValForeach(other, func);
    }


    /**
     * Recursively loop values and add them to list.
     *
     * Recursive loop stops when {@code func} returns a null value for {@link
     * Function#apply(Object)} method.
     *
     * @param value Value.
     * @param func  Function to extract new value (the value returned by the function is nullable).
     * @param <T>   Type of value.
     * @return A list with all values.
     */
    public static <T> List<T> recursiveValToList(T value, Function<T, T> func) {
        List<T> list = new ArrayList<>();

        RecursionUtil.recursiveValForeach(value, t -> {
            list.add(t);
            return func.apply(t);
        });

        return list;
    }

}
