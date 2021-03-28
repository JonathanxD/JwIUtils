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

import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.function.BoolToBoolFunction;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.predicate.BooleanPredicate;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Tristate;
import com.github.jonathanxd.iutils.opt.BaseNone;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class NoneBoolean extends OptBoolean implements BaseNone<OptBoolean> {
    static final OptBoolean NONE = new NoneBoolean();

    private NoneBoolean() {
    }

    @Contract(" -> fail")
    @Override
    public boolean getValue() {
        throw new NoSuchElementException("None");
    }

    @Override
    public void ifPresent(@NotNull BooleanConsumer consumer) {
    }

    @Override
    public void ifPresent(@NotNull BooleanConsumer consumer, @NotNull Runnable elseRunnable) {
        elseRunnable.run();
    }

    @Override
    public @NotNull OptBoolean filter(@NotNull BooleanPredicate predicate) {
        return OptBoolean.falseOpt();
    }

    @Override
    public @NotNull OptBoolean map(@NotNull BoolToBoolFunction mapper) {
        return OptBoolean.falseOpt();
    }

    @Override
    public @NotNull OptBoolean flatMap(@NotNull BooleanFunction<? extends OptBoolean> mapper) {
        return OptBoolean.falseOpt();
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull BooleanFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return none.get();
    }

    @Override
    public boolean orElse(boolean value) {
        return value;
    }

    @Override
    public boolean orElseGet(@NotNull BooleanSupplier supplier) {
        return supplier.getAsBoolean();
    }

    @Override
    public boolean orElseLazy(@NotNull Lazy<? extends Boolean> lazy) {
        return lazy.get();
    }

    @Override
    public <E extends Throwable> boolean orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
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
    public @NotNull Optional<Boolean> toBoxedOptional() {
        return Optional.empty();
    }

    @Override
    public @NotNull Tristate toTristate() {
        return Tristate.UNKNOWN;
    }

    @NotNull
    @Override
    public Opt<OptBoolean> $or(@NotNull Supplier<? extends Opt<OptBoolean>> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @NotNull
    @Override
    public OptBoolean or(@NotNull Supplier<? extends OptBoolean> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneBoolean;
    }

    @Override
    public int hashCode() {
        return Objects.hash(0, super.hashCode());
    }

}
