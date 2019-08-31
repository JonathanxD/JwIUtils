/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.opt.specialized;

import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.function.ByteToByteFunction;
import com.github.jonathanxd.iutils.function.predicate.BytePredicate;
import com.github.jonathanxd.iutils.function.supplier.ByteSupplier;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseNone;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class NoneByte extends OptByte implements BaseNone<OptByte> {
    static final OptByte NONE = new NoneByte();

    private NoneByte() {
    }

    @Contract(" -> fail")
    @Override
    public byte getValue() {
        throw new NoSuchElementException("None");
    }

    @Override
    public void ifPresent(@NotNull ByteConsumer consumer) {
    }

    @Override
    public void ifPresent(@NotNull ByteConsumer consumer, @NotNull Runnable elseRunnable) {
        elseRunnable.run();
    }

    @Override
    public @NotNull OptByte filter(@NotNull BytePredicate predicate) {
        return OptByte.none();
    }

    @Override
    public @NotNull OptByte map(@NotNull ByteToByteFunction mapper) {
        return OptByte.none();
    }

    @Override
    public @NotNull OptByte flatMap(@NotNull ByteFunction<? extends OptByte> mapper) {
        return OptByte.none();
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull ByteFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return none.get();
    }

    @Override
    public byte orElse(byte value) {
        return value;
    }

    @Override
    public byte orElseGet(@NotNull ByteSupplier supplier) {
        return supplier.get();
    }

    @Override
    public byte orElseLazy(@NotNull Lazy<? extends Byte> lazy) {
        return lazy.get();
    }

    @Override
    public <E extends Throwable> byte orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
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
    public @NotNull Optional<Byte> toBoxedOptional() {
        return Optional.empty();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneByte;
    }

    @Override
    public int hashCode() {
        return Objects.hash(0, super.hashCode());
    }

    @NotNull
    @Override
    public Opt<OptByte> $or(@NotNull Supplier<? extends Opt<OptByte>> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @NotNull
    @Override
    public OptByte or(@NotNull Supplier<? extends OptByte> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }
}
