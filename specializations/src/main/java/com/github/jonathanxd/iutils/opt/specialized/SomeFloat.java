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

import com.github.jonathanxd.iutils.function.consumer.FloatConsumer;
import com.github.jonathanxd.iutils.function.function.FloatFunction;
import com.github.jonathanxd.iutils.function.function.FloatToFloatFunction;
import com.github.jonathanxd.iutils.function.predicate.FloatPredicate;
import com.github.jonathanxd.iutils.function.supplier.FloatSupplier;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.BaseSome;
import com.github.jonathanxd.iutils.opt.Opt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

public final class SomeFloat extends OptFloat implements BaseSome<OptFloat> {

    private final float value;

    SomeFloat(float value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public float getValue() {
        return this.value;
    }

    @Override
    public void ifPresent(@NotNull FloatConsumer consumer) {
        consumer.accept(this.getValue());
    }

    @Override
    public void ifPresent(@NotNull FloatConsumer consumer, @NotNull Runnable elseRunnable) {
        consumer.accept(this.getValue());
    }

    @Override
    public @NotNull OptFloat filter(@NotNull FloatPredicate predicate) {
        return predicate.test(this.getValue()) ? this : none();
    }

    @Override
    public @NotNull OptFloat map(@NotNull FloatToFloatFunction mapper) {
        return some(mapper.apply(this.getValue()));
    }

    @Override
    public @NotNull OptFloat flatMap(@NotNull FloatFunction<? extends OptFloat> mapper) {
        return mapper.apply(this.getValue());
    }

    @Override
    public <O extends Opt<O>> @NotNull O flatMapTo(@NotNull FloatFunction<? extends O> mapper, @NotNull Supplier<O> none) {
        return mapper.apply(this.getValue());
    }

    @Override
    public float orElse(float value) {
        return this.getValue();
    }

    @Override
    public float orElseGet(@NotNull FloatSupplier supplier) {
        return this.getValue();
    }

    @Override
    public float orElseLazy(@NotNull Lazy<? extends Float> lazy) {
        return this.getValue();
    }

    @Override
    public <E extends Throwable> float orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        return this.getValue();
    }

    @Override
    public @NotNull DoubleStream stream() {
        return DoubleStream.of(this.getValue());
    }

    @Override
    public @NotNull OptionalDouble toOptional() {
        return OptionalDouble.of(this.getValue());
    }

    @Override
    public @NotNull Optional<Float> toBoxedOptional() {
        return Optional.of(this.getValue());
    }

    @Contract("null -> false")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SomeFloat && this.getValue() == ((SomeFloat) obj).getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(1, Float.hashCode(this.getValue()));
    }

    @NotNull
    @Override
    public Opt<OptFloat> $or(@NotNull Supplier<? extends Opt<OptFloat>> supplier) {
        return this;
    }

    @NotNull
    @Override
    public OptFloat or(@NotNull Supplier<? extends OptFloat> supplier) {
        return this;
    }
}
