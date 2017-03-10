/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.object;

import java.util.function.Supplier;

/**
 * Lazy evaluation.
 *
 * @param <T> Type of value.
 */
public class Lazy<T> {

    private final Supplier<T> supplier;
    private boolean isEvaluated = false;
    private T value = null;

    Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Creates a {@link Lazy} from a supplier.
     *
     * @param supplier Value supplier.
     * @param <T>      Value type.
     * @return {@link Lazy} with {@code supplier} as evaluator.
     */
    public static <T> Lazy<T> lazy(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    /**
     * Creates a evaluated {@link Lazy}.
     *
     * @param value Evaluated value.
     * @param <T>   Value type.
     * @return Evaluated {@link Lazy}.
     */
    public static <T> Lazy<T> evaluated(T value) {
        Lazy<T> lazy = new Lazy<>(() -> value);
        lazy.isEvaluated = true;
        lazy.value = value;

        return lazy;
    }

    /**
     * Gets the value.
     *
     * @return Value.
     */
    public T get() {
        if (!this.isEvaluated) {
            this.value = this.supplier.get();
            this.isEvaluated = true;
        }

        return this.value;
    }

    /**
     * Returns true if this lazy instance is already evaluated.
     *
     * @return True if this lazy instance is already evaluated.
     */
    public boolean isEvaluated() {
        return this.isEvaluated;
    }
}
