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

import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.function.ShortToShortFunction;
import com.github.jonathanxd.iutils.function.predicate.ShortPredicate;
import com.github.jonathanxd.iutils.function.supplier.ShortSupplier;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseSome;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class SomeShort extends OptShort implements BaseSome<OptShort> {

    private final short value;

    SomeShort(short value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public short getValue() {
        return this.value;
    }

    @Override
    public void ifPresent(@NotNull ShortConsumer consumer) {
        consumer.accept(this.getValue());
    }

    @Override
    public void ifPresent(@NotNull ShortConsumer consumer, @NotNull Runnable elseRunnable) {
        consumer.accept(this.getValue());
    }

    @Override
    public @NotNull OptShort filter(@NotNull ShortPredicate predicate) {
        return predicate.test(this.getValue()) ? this : OptShort.none();
    }

    @Override
    public @NotNull OptShort map(@NotNull ShortToShortFunction mapper) {
        return OptShort.some(mapper.apply(this.getValue()));
    }

    @Override
    public @NotNull OptShort flatMap(@NotNull ShortFunction<? extends OptShort> mapper) {
        return mapper.apply(this.getValue());
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull ShortFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return mapper.apply(this.getValue());
    }

    @Override
    public short orElse(short value) {
        return this.getValue();
    }

    @Override
    public short orElse(@NotNull ShortSupplier supplier) {
        return this.getValue();
    }

    @Override
    public short orElseLazy(@NotNull Lazy<? extends Short> lazy) {
        return this.getValue();
    }

    @Override
    public <E extends Throwable> short orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
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
    public @NotNull Optional<Short> toBoxedOptional() {
        return Optional.of(this.getValue());
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeShort && this.getValue() == ((SomeShort) obj).getValue();
    }

    @Override
    public int hashCode() {
        return Short.hashCode(this.getValue());
    }

    @NotNull
    @Override
    public Opt<OptShort> $or(@NotNull Supplier<? extends Opt<OptShort>> supplier) {
        return this;
    }

    @NotNull
    @Override
    public OptShort or(@NotNull Supplier<? extends OptShort> supplier) {
        return this;
    }
}
