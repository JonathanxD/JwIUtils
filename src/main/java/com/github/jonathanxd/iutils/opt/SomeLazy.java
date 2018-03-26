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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.NonNullLazy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public final class SomeLazy<T> extends OptLazy<T> implements DefBaseSomeObj<T, OptLazy<T>> {

    @NotNull
    private final NonNullLazy<T> lazy;

    SomeLazy(@NotNull Lazy<T> lazy) {
        Objects.requireNonNull(lazy, "Lazy cannot be null.");
        this.lazy = Lazy.nonNull(lazy);
    }

    @NotNull
    @Override
    public T getValue() {
        return Objects.requireNonNull(this.lazy.get(), "Lazy evaluation result cannot be null");
    }

    @NotNull
    @Override
    public <R> OptLazy<R> map(@NotNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        return some(Lazy.lazy(() -> mapper.apply(this.getValue())));
    }

    @NotNull
    @Override
    public <R> OptLazy<R> flatMap(@NotNull Function<? super T, ? extends OptLazy<R>> mapper) {
        Objects.requireNonNull(mapper);
        return mapper.andThen(Objects::requireNonNull).apply(this.getValue());
    }

    @NotNull
    @Override
    public <R> OptLazy<R> mapLazy(@NotNull Function<? super T, Lazy<R>> mapper) {
        Objects.requireNonNull(mapper);
        return some(Lazy.lazy(() -> mapper.apply(this.getValue()).get()));
    }

    @Override
    public boolean isEvaluated() {
        return this.lazy.isEvaluated();
    }

    @NotNull
    @Override
    public OptObject<Lazy<T>> toObjectOpt() {
        return OptObject.optObjectNotNull(this.lazy.copy());
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeLazy<?> && Objects.equals(this.getValue(), ((SomeLazy) obj).getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(1, Objects.hashCode(this.getValue()));
    }

}
