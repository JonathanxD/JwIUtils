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
import com.github.jonathanxd.iutils.opt.BaseSome;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class SomeInt extends OptInt implements BaseSome<OptInt> {

    private final int value;

    SomeInt(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void ifPresent(@NotNull IntConsumer consumer) {
        consumer.accept(this.getValue());
    }

    @Override
    public void ifPresent(@NotNull IntConsumer consumer, @NotNull Runnable elseRunnable) {
        consumer.accept(this.getValue());
    }

    @Override
    public @NotNull OptInt filter(@NotNull IntPredicate predicate) {
        return predicate.test(this.getValue()) ? this : OptInt.none();
    }

    @Override
    public @NotNull OptInt map(@NotNull IntToIntFunction mapper) {
        return OptInt.some(mapper.apply(this.getValue()));
    }

    @Override
    public @NotNull OptInt flatMap(@NotNull IntFunction<? extends OptInt> mapper) {
        return mapper.apply(this.getValue());
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull IntFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return mapper.apply(this.getValue());
    }

    @Override
    public int orElse(int value) {
        return this.getValue();
    }

    @Override
    public int orElseGet(@NotNull IntSupplier supplier) {
        return this.getValue();
    }

    @Override
    public int orElseLazy(@NotNull Lazy<? extends Integer> lazy) {
        return this.getValue();
    }

    @Override
    public <E extends Throwable> int orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        return this.getValue();
    }

    @Override
    public @NotNull IntStream stream() {
        return IntStream.of(this.getValue());
    }

    @Override
    public @NotNull OptionalInt toOptional() {
        return OptionalInt.of(this.getValue());
    }

    @Override
    public @NotNull Optional<Integer> toBoxedOptional() {
        return Optional.of(this.getValue());
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeInt && this.getValue() == ((SomeInt) obj).getValue();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.getValue());
    }

    @NotNull
    @Override
    public Opt<OptInt> $or(@NotNull Supplier<? extends Opt<OptInt>> supplier) {
        return this;
    }

    @NotNull
    @Override
    public OptInt or(@NotNull Supplier<? extends OptInt> supplier) {
        return this;
    }
}
