/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.function.combiner;

import org.jetbrains.annotations.Contract;

public interface Combiner<F, S, C> {

    /**
     * Combines {@code f} and {@code s} into an object of type {@link C}.
     *
     * @param f First object to combine.
     * @param s Second object to combine.
     * @return Combined instance of {@code f} and {@code s}.
     */
    @Contract("null, null -> null; _, null -> fail; null, _ -> fail; !null, !null -> !null")
    C combine(F f, S s);

    /**
     * Combines {@code f} into an object of type {@link C}.
     *
     * @param f Object to combine.
     * @return Combined instance of {@code f}.
     */
    @Contract("null -> null; !null -> !null")
    C combineFirst(F f);

    /**
     * Combines {@code s} into an object of type {@link C}.
     *
     * @param s Object to combine.
     * @return Combined instance of {@code s}.
     */
    @Contract("null -> null; !null -> !null")
    C combineSecond(S s);
}
