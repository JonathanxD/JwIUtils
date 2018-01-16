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

import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.function.ByteToByteFunction;
import com.github.jonathanxd.iutils.function.predicate.BytePredicate;
import com.github.jonathanxd.iutils.function.supplier.ByteSupplier;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseSome;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class SomeByte extends OptByte implements BaseSome<OptByte> {

    private final byte value;

    SomeByte(byte value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public byte getValue() {
        return this.value;
    }

    @Override
    public void ifPresent(@NotNull ByteConsumer consumer) {
        consumer.accept(this.getValue());
    }

    @Override
    public void ifPresent(@NotNull ByteConsumer consumer, @NotNull Runnable elseRunnable) {
        consumer.accept(this.getValue());
    }

    @Override
    public @NotNull OptByte filter(@NotNull BytePredicate predicate) {
        return predicate.test(this.getValue()) ? this : OptByte.none();
    }

    @Override
    public @NotNull OptByte map(@NotNull ByteToByteFunction mapper) {
        return OptByte.some(mapper.apply(this.getValue()));
    }

    @Override
    public @NotNull OptByte flatMap(@NotNull ByteFunction<? extends OptByte> mapper) {
        return mapper.apply(this.getValue());
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull ByteFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return mapper.apply(this.getValue());
    }

    @Override
    public byte orElse(byte value) {
        return this.getValue();
    }

    @Override
    public byte orElseGet(@NotNull ByteSupplier supplier) {
        return this.getValue();
    }

    @Override
    public byte orElseLazy(@NotNull Lazy<? extends Byte> lazy) {
        return this.getValue();
    }

    @Override
    public <E extends Throwable> byte orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
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
    public @NotNull Optional<Byte> toBoxedOptional() {
        return Optional.of(this.getValue());
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeByte && this.getValue() == ((SomeByte) obj).getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(1, Byte.hashCode(this.getValue()));
    }

    @NotNull
    @Override
    public Opt<OptByte> $or(@NotNull Supplier<? extends Opt<OptByte>> supplier) {
        return this;
    }

    @NotNull
    @Override
    public OptByte or(@NotNull Supplier<? extends OptByte> supplier) {
        return this;
    }
}
