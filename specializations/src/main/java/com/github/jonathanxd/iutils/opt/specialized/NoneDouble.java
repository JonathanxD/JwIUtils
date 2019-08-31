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

import com.github.jonathanxd.iutils.function.function.DoubleToDoubleFunction;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseNone;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

public final class NoneDouble extends OptDouble implements BaseNone<OptDouble> {
    static final OptDouble NONE = new NoneDouble();

    private NoneDouble() {
    }

    @Contract(" -> fail")
    @Override
    public double getValue() {
        throw new NoSuchElementException("None");
    }

    @Override
    public void ifPresent(@NotNull DoubleConsumer consumer) {
    }

    @Override
    public void ifPresent(@NotNull DoubleConsumer consumer, @NotNull Runnable elseRunnable) {
        elseRunnable.run();
    }

    @Override
    public @NotNull OptDouble filter(@NotNull DoublePredicate predicate) {
        return none();
    }

    @Override
    public @NotNull OptDouble map(@NotNull DoubleToDoubleFunction mapper) {
        return none();
    }

    @Override
    public @NotNull OptDouble flatMap(@NotNull DoubleFunction<? extends OptDouble> mapper) {
        return none();
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull DoubleFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return none.get();
    }

    @Override
    public double orElse(double value) {
        return value;
    }

    @Override
    public double orElse(@NotNull DoubleSupplier supplier) {
        return supplier.getAsDouble();
    }

    @Override
    public double orElse(@NotNull Lazy<? extends Double> lazy) {
        return lazy.get();
    }

    @Override
    public <E extends Throwable> double orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        throw Objects.<E>requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @Override
    public @NotNull DoubleStream stream() {
        return DoubleStream.empty();
    }

    @Override
    public @NotNull OptionalDouble toOptional() {
        return OptionalDouble.empty();
    }

    @Override
    public @NotNull Optional<Double> toBoxedOptional() {
        return Optional.empty();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoneDouble;
    }

    @Override
    public int hashCode() {
        return Objects.hash(0, super.hashCode());
    }

    @NotNull
    @Override
    public Opt<OptDouble> $or(@NotNull Supplier<? extends Opt<OptDouble>> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }

    @NotNull
    @Override
    public OptDouble or(@NotNull Supplier<? extends OptDouble> supplier) {
        return Objects.requireNonNull(Objects.requireNonNull(supplier).get());
    }
}
