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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.object.Lazy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.function.Function;

public final class NoneLazy<T> extends OptLazy<T> implements DefBaseNoneObj<T, OptLazy<T>> {
    static final OptLazy NONE = new NoneLazy();

    private NoneLazy() {
    }

    @Contract(pure = true)
    @Override
    public boolean isEvaluated() {
        return true;
    }

    @NotNull
    @Override
    public <R> OptLazy<R> map(@NotNull Function<? super T, ? extends R> mapper) {
        return OptLazy.none();
    }

    @NotNull
    @Override
    public <R> OptLazy<R> flatMap(@NotNull Function<? super T, ? extends OptLazy<R>> mapper) {
        return OptLazy.none();
    }

    @NotNull
    @Override
    public <R> OptLazy<R> mapLazy(@NotNull Function<? super T, Lazy<R>> mapper) {
        return OptLazy.none();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public OptObject<Lazy<T>> toObjectOpt() {
        return OptObject.none();
    }

    @NotNull
    @Contract(" -> fail")
    @Override
    public T getValue() {
        throw new NoSuchElementException("None");
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneLazy<?>;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
