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

import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.function.CharFunction;
import com.github.jonathanxd.iutils.function.function.CharToCharFunction;
import com.github.jonathanxd.iutils.function.predicate.CharPredicate;
import com.github.jonathanxd.iutils.function.supplier.CharSupplier;
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

public final class SomeChar extends OptChar implements BaseSome<OptChar> {

    private final char value;

    SomeChar(char value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public char getValue() {
        return this.value;
    }

    @Override
    public void ifPresent(@NotNull CharConsumer consumer) {
        consumer.accept(this.getValue());
    }

    @Override
    public void ifPresent(@NotNull CharConsumer consumer, @NotNull Runnable elseRunnable) {
        consumer.accept(this.getValue());
    }

    @Override
    public @NotNull OptChar filter(@NotNull CharPredicate predicate) {
        return predicate.test(this.getValue()) ? this : none();
    }

    @Override
    public @NotNull OptChar map(@NotNull CharToCharFunction mapper) {
        return some(mapper.apply(this.getValue()));
    }

    @Override
    public @NotNull OptChar flatMap(@NotNull CharFunction<? extends OptChar> mapper) {
        return mapper.apply(this.getValue());
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull CharFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return mapper.apply(this.getValue());
    }

    @Override
    public char orElse(char value) {
        return this.getValue();
    }

    @Override
    public char orElseGet(@NotNull CharSupplier supplier) {
        return this.getValue();
    }

    @Override
    public char orElseLazy(@NotNull Lazy<? extends Character> lazy) {
        return this.getValue();
    }

    @Override
    public <E extends Throwable> char orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
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
    public @NotNull Optional<Character> toBoxedOptional() {
        return Optional.of(this.getValue());
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeChar && this.getValue() == ((SomeChar) obj).getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(1, Character.hashCode(this.getValue()));
    }

    @NotNull
    @Override
    public Opt<OptChar> $or(@NotNull Supplier<? extends Opt<OptChar>> supplier) {
        return this;
    }

    @NotNull
    @Override
    public OptChar or(@NotNull Supplier<? extends OptChar> supplier) {
        return this;
    }
}
