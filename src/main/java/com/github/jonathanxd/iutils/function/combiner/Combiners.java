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

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.object.Pair;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class Combiners {

    public static <F, S, C> Combiner<F, S, C> biFunctionAsCombiner(BiFunction<@Nullable F, @Nullable S, C> successCombiner) {
        return new Combiner<F, S, C>() {
            @Override
            public C combine(F f, S s) {
                return successCombiner.apply(f, s);
            }

            @Override
            public C combineFirst(F f) {
                return successCombiner.apply(f, null);
            }

            @Override
            public C combineSecond(S s) {
                return successCombiner.apply(null, s);
            }
        };
    }

    public static <F, S> Combiner<F, S, Pair<@Nullable F, @Nullable S>> pair() {
        return new CombinerImpl<>(Pair::of, f -> Pair.of(f, null), s -> Pair.of(null, s));
    }

    public static <F extends B, S extends B, B> Combiner<F, S, List<B>> list() {
        return new CombinerImpl<>((f, s) -> Collections3.listOf(f, s), Collections::singletonList, Collections::singletonList);
    }

}
