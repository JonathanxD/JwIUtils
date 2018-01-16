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

import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.function.BoolToBoolFunction;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.predicate.BooleanPredicate;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Tristate;
import com.github.jonathanxd.iutils.opt.BaseSome;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class SomeBoolean extends OptBoolean implements BaseSome<OptBoolean> {
    public static final OptBoolean TRUE = new SomeBoolean() {
        @Override
        public boolean getValue() {
            return true;
        }
    };

    public static final OptBoolean FALSE = new SomeBoolean() {
        @Override
        public boolean getValue() {
            return false;
        }
    };

    private SomeBoolean() {
    }

    @Override
    public void ifPresent(@NotNull BooleanConsumer consumer) {
        consumer.accept(this.getValue());
    }

    @Override
    public void ifPresent(@NotNull BooleanConsumer consumer, @NotNull Runnable elseRunnable) {
        consumer.accept(this.getValue());
    }

    @Override
    public @NotNull OptBoolean filter(@NotNull BooleanPredicate predicate) {
        return predicate.test(this.getValue()) ? this : OptBoolean.none();
    }

    @Override
    public @NotNull OptBoolean map(@NotNull BoolToBoolFunction mapper) {
        return OptBoolean.some(mapper.apply(this.getValue()));
    }

    @Override
    public @NotNull OptBoolean flatMap(@NotNull BooleanFunction<? extends OptBoolean> mapper) {
        return mapper.apply(this.getValue());
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull BooleanFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return mapper.apply(this.getValue());
    }

    @Override
    public boolean orElse(boolean value) {
        return this.getValue();
    }

    @Override
    public boolean orElseGet(@NotNull BooleanSupplier supplier) {
        return this.getValue();
    }

    @Override
    public boolean orElseLazy(@NotNull Lazy<? extends Boolean> lazy) {
        return this.getValue();
    }

    @Override
    public <E extends Throwable> boolean orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        return this.getValue();
    }

    @Override
    public @NotNull IntStream stream() {
        return IntStream.of(this.getValue() ? 1 : 0);
    }

    @Override
    public @NotNull OptionalInt toOptional() {
        return OptionalInt.of(this.getValue() ? 1 : 0);
    }

    @Override
    public @NotNull Optional<Boolean> toBoxedOptional() {
        return Optional.of(this.getValue());
    }

    @Override
    public @NotNull Tristate toTristate() {
        return this.getValue() ? Tristate.TRUE : Tristate.FALSE;
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeBoolean && this.getValue() == ((SomeBoolean) obj).getValue();
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.getValue());
    }

    @NotNull
    @Override
    public Opt<OptBoolean> $or(@NotNull Supplier<? extends Opt<OptBoolean>> supplier) {
        return this;
    }

    @NotNull
    @Override
    public OptBoolean or(@NotNull Supplier<? extends OptBoolean> supplier) {
        return this;
    }
}
