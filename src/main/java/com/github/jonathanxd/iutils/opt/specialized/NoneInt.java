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

import com.github.jonathanxd.iutils.function.function.IntToIntFunction;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseNone;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class NoneInt extends OptInt implements BaseNone<OptInt> {
    static final OptInt NONE = new NoneInt();

    private NoneInt() {
    }

    @Contract(" -> fail")
    @Override
    public int getValue() {
        throw new NoSuchElementException("None");
    }

    @Override
    public void ifPresent(@NotNull IntConsumer consumer) {
    }

    @Override
    public void ifPresent(@NotNull IntConsumer consumer, @NotNull Runnable elseRunnable) {
        elseRunnable.run();
    }

    @Override
    public @NotNull OptInt filter(@NotNull IntPredicate predicate) {
        return OptInt.none();
    }

    @Override
    public @NotNull OptInt map(@NotNull IntToIntFunction mapper) {
        return OptInt.none();
    }

    @Override
    public @NotNull OptInt flatMap(@NotNull IntFunction<? extends OptInt> mapper) {
        return OptInt.none();
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull IntFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return none.get();
    }

    @Override
    public int orElse(int value) {
        return value;
    }

    @Override
    public int orElseGet(@NotNull IntSupplier supplier) {
        return supplier.getAsInt();
    }

    @Override
    public int orElseLazy(@NotNull Lazy<? extends Integer> lazy) {
        return lazy.get();
    }

    @Override
    public <E extends Throwable> int orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        throw Objects.<E>requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @Override
    public @NotNull IntStream stream() {
        return IntStream.empty();
    }

    @Override
    public @NotNull OptionalInt toOptional() {
        return OptionalInt.empty();
    }

    @Override
    public @NotNull Optional<Integer> toBoxedOptional() {
        return Optional.empty();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneInt;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @NotNull
    @Override
    public Opt<OptInt> $or(@NotNull Supplier<? extends Opt<OptInt>> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @NotNull
    @Override
    public OptInt or(@NotNull Supplier<? extends OptInt> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }
}
