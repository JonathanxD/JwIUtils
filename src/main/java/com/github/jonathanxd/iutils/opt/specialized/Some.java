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
package com.github.jonathanxd.iutils.opt.specialized;

import com.github.jonathanxd.iutils.opt.DefBaseSomeObj;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public final class Some<T> extends OptObject<T> implements DefBaseSomeObj<T, OptObject<T>> {

    private final T value;

    Some(T value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public T getValue() {
        return this.value;
    }


    @NotNull
    @Override
    public <R> OptObject<R> map(@NotNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        return OptObject.some(mapper.apply(this.getValue()));
    }

    @NotNull
    @Override
    public <R> OptObject<R> flatMap(@NotNull Function<? super T, ? extends OptObject<R>> mapper) {
        Objects.requireNonNull(mapper);
        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Some<?> && Objects.equals(this.getValue(), ((Some) obj).getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getValue());
    }
}
