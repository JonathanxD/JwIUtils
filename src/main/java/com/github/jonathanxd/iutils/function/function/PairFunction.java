/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.function.function;

import com.github.jonathanxd.iutils.object.Pair;

/**
 * A function which receives {@link I1 input 1} and {@link I2 input 2} and maps to a {@link Pair} of
 * {@link O1} and {@link O2}.
 *
 * @param <I1> Input 1.
 * @param <I2> Input 2.
 * @param <O1> Output 1.
 * @param <O2> Output 2.
 */
@FunctionalInterface
public interface PairFunction<I1, I2, O1, O2> {

    /**
     * Apply function to {@code i1} and {@code i2} and return a pair with mapped values.
     *
     * @param i1 Input 1.
     * @param i2 Input 2.
     * @return Pair of mapped input 1 and input 2.
     */
    Pair<O1, O2> apply(I1 i1, I2 i2);

}
