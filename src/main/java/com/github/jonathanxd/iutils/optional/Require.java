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
package com.github.jonathanxd.iutils.optional;

import java.util.function.Function;

/**
 * Optional require.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class Require {

    private Require() {
        throw new UnsupportedOperationException();
    }

    /**
     * Require that {@code optional} have a present value.
     *
     * @param optional Optional.
     * @param message  Message to show if the optional does not have a value.
     * @param <T>      Type of the value.
     * @return Value if present.
     * @throws IllegalStateException If there is not value in the {@code optional}.
     */
    public static <T> T require(java.util.Optional<T> optional, String message) {
        if (optional == null || !optional.isPresent())
            throw new IllegalStateException(message);

        return optional.get();
    }

    /**
     * Require that {@code optional} have a present value.
     *
     * @param optional Optional.
     * @param <T>      Type of the value.
     * @return Value if present.
     * @throws IllegalStateException If there is not value in the {@code optional}.
     */
    public static <T> T require(java.util.Optional<T> optional) {
        return require(optional, "Optional cannot be empty!");
    }

}
