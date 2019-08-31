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

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CombinerImpl<F, S, C> implements Combiner<F, S, C> {

    private final BiFunction<@NotNull F, @NotNull S, @NotNull C> bothCombiner;
    private final Function<@NotNull F, @NotNull C> firstCombiner;
    private final Function<@NotNull S, @NotNull C> secondCombiner;

    public CombinerImpl(BiFunction<@NotNull F, @NotNull S, @NotNull C> bothCombiner,
                        Function<@NotNull F, @NotNull C> firstCombiner,
                        Function<@NotNull S, @NotNull C> secondCombiner) {
        this.bothCombiner = bothCombiner;
        this.firstCombiner = firstCombiner;
        this.secondCombiner = secondCombiner;
    }

    @Override
    public C combine(F f, S s) {
        if ((f == null) != (s == null) || (f == null)) {
            throw new IllegalArgumentException("Both arguments must be either null or not null.");
        } else {
            return bothCombiner.apply(f, s);
        }
    }

    @Override
    public C combineFirst(F f) {
        if (f == null) {
            return null;
        } else {
            return this.firstCombiner.apply(f);
        }
    }

    @Override
    public C combineSecond(S s) {
        if (s == null) {
            return null;
        } else {
            return this.secondCombiner.apply(s);
        }
    }
}
