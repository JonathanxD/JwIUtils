/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.function.function.LongToLongFunction;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseNone;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public final class NoneLong extends OptLong implements BaseNone<OptLong> {
    static final OptLong NONE = new NoneLong();

    private NoneLong() {
    }

    @Contract(" -> fail")
    @Override
    public long getValue() {
        throw new NoSuchElementException("None");
    }

    @Override
    public void ifPresent(@NotNull LongConsumer consumer) {
    }

    @Override
    public void ifPresent(@NotNull LongConsumer consumer, @NotNull Runnable elseRunnable) {
        elseRunnable.run();
    }

    @Override
    public @NotNull OptLong filter(@NotNull LongPredicate predicate) {
        return none();
    }

    @Override
    public @NotNull OptLong map(@NotNull LongToLongFunction mapper) {
        return none();
    }

    @Override
    public @NotNull OptLong flatMap(@NotNull LongFunction<? extends OptLong> mapper) {
        return none();
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull LongFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return none.get();
    }

    @Override
    public long orElse(long value) {
        return value;
    }

    @Override
    public long orElseGet(@NotNull LongSupplier supplier) {
        return supplier.getAsLong();
    }

    @Override
    public long orElseLazy(@NotNull Lazy<? extends Long> lazy) {
        return lazy.get();
    }

    @Override
    public <E extends Throwable> long orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        throw Objects.<E>requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @Override
    public @NotNull LongStream stream() {
        return LongStream.empty();
    }

    @Override
    public @NotNull OptionalLong toOptional() {
        return OptionalLong.empty();
    }

    @Override
    public @NotNull Optional<Long> toBoxedOptional() {
        return Optional.empty();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneLong;
    }

    @Override
    public int hashCode() {
        return Objects.hash(0, super.hashCode());
    }

    @NotNull
    @Override
    public Opt<OptLong> $or(@NotNull Supplier<? extends Opt<OptLong>> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @NotNull
    @Override
    public OptLong or(@NotNull Supplier<? extends OptLong> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }
}
