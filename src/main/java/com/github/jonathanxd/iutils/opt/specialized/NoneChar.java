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

import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.function.CharFunction;
import com.github.jonathanxd.iutils.function.function.CharToCharFunction;
import com.github.jonathanxd.iutils.function.predicate.CharPredicate;
import com.github.jonathanxd.iutils.function.supplier.CharSupplier;
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

public final class NoneChar extends OptChar implements BaseNone<OptChar> {
    static final OptChar NONE = new NoneChar();

    private NoneChar() {
    }

    @Contract(" -> fail")
    @Override
    public char getValue() {
        throw new NoSuchElementException("None");
    }

    @Override
    public void ifPresent(@NotNull CharConsumer consumer) {
    }

    @Override
    public void ifPresent(@NotNull CharConsumer consumer, @NotNull Runnable elseRunnable) {
        elseRunnable.run();
    }

    @Override
    public @NotNull OptChar filter(@NotNull CharPredicate predicate) {
        return OptChar.none();
    }

    @Override
    public @NotNull OptChar map(@NotNull CharToCharFunction mapper) {
        return OptChar.none();
    }

    @Override
    public @NotNull OptChar flatMap(@NotNull CharFunction<? extends OptChar> mapper) {
        return OptChar.none();
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull CharFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return none.get();
    }

    @Override
    public char orElse(char value) {
        return value;
    }

    @Override
    public char orElseGet(@NotNull CharSupplier supplier) {
        return supplier.get();
    }

    @Override
    public char orElseLazy(@NotNull Lazy<? extends Character> lazy) {
        return lazy.get();
    }

    @Override
    public <E extends Throwable> char orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
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
    public @NotNull Optional<Character> toBoxedOptional() {
        return Optional.empty();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneChar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(0, super.hashCode());
    }

    @NotNull
    @Override
    public Opt<OptChar> $or(@NotNull Supplier<? extends Opt<OptChar>> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @NotNull
    @Override
    public OptChar or(@NotNull Supplier<? extends OptChar> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }
}
