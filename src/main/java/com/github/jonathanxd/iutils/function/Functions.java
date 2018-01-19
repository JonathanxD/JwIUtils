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

import com.github.jonathanxd.iutils.object.LateInit;
import com.github.jonathanxd.iutils.object.Lazy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Functions {

    /**
     * Returns a function that computes the value one time (lazily) and then return always return
     * this value.
     *
     * @param function Function that computes the value.
     * @param <T>      Input type.
     * @param <R>      Return type.
     * @return Single computation function.
     */
    public static <T, R> Function<T, R> once(Function<T, R> function) {
        LateInit.Ref<R> lateInit = LateInit.lateRef("once function");
        return f -> {
            if (!lateInit.isInitialized())
                lateInit.init(function.apply(f));
            return lateInit.getValue();
        };
    }

    /**
     * Returns a function that associates {@link T input} to {@link R output} in a map and reuse the
     * value instead of invoking {@code function}.
     *
     * @param function Function that computes values.
     * @param <T>      Input type.
     * @param <R>      Return type.
     * @return Caching function.
     */
    public static <T, R> Function<T, R> cached(Function<T, R> function) {
        final Map<T, R> map = new HashMap<>();
        return Functions.cachedIn(map, function);
    }

    /**
     * Returns a function that associates {@link T input} to {@link R output} in {@code cache} and
     * reuse the value instead of invoking {@code function}.
     *
     * @param cache    Cache to store values.
     * @param function Function that computes values.
     * @param <T>      Input type.
     * @param <R>      Return type.
     * @return Caching function.
     */
    public static <T, R> Function<T, R> cachedIn(Map<T, R> cache, Function<T, R> function) {
        return t -> cache.computeIfAbsent(t, function);
    }

}
