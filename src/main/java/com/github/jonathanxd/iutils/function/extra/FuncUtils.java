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
package com.github.jonathanxd.iutils.function.extra;

import com.github.jonathanxd.iutils.function.function.NodeFunction;
import com.github.jonathanxd.iutils.object.Node;
import com.github.jonathanxd.iutils.object.Pair;

import java.util.function.BiPredicate;

/**
 * Created by jonathan on 28/05/16.
 */
public final class FuncUtils {

    private FuncUtils() {
    }

    public static <T, U> boolean test(Pair<T, U> pair, BiPredicate<? super T, ? super U> biPredicate) {
        return biPredicate.test(pair._1(), pair._2());
    }

    public static <T, U, RK, RV> Node<? extends RK, ? extends RV> from(Pair<T, U> pair, NodeFunction<? super T, ? super U, ? extends RK, ? extends RV> mapper) {
        return mapper.apply(pair._1(), pair._2());
    }
}
