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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Lazy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface DefBaseSomeObj<T, O extends BaseOptObject<T, O>> extends BaseSome<O>, BaseOptObject<T, O> {

    @Override
    default void ifPresent(@NotNull Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        consumer.accept(this.getValue());
    }

    @Override
    default void ifPresent(@NotNull Consumer<? super T> consumer, @NotNull Runnable elseRunnable) {
        Objects.requireNonNull(consumer);
        consumer.accept(this.getValue());
    }

    @NotNull
    @Override
    default O filter(@NotNull Predicate<? super T> predicate) {
        return predicate.test(this.getValue()) ? this.cast() : this.toNone();
    }

    @Override
    default <O2 extends Opt<O2>> @NotNull O2 flatMapTo(@NotNull Function<? super T, ? extends O2> mapper, @NotNull Supplier<O2> none) {
        Objects.requireNonNull(mapper);
        return Objects.requireNonNull(mapper.apply(this.getValue()));
    }

    @Nullable
    @Override
    default T orElse(@NotNull T value) {
        return this.getValue();
    }

    @Nullable
    @Override
    default T orElseGet(@NotNull Supplier<? extends T> supplier) {
        return this.getValue();
    }

    @Nullable
    @Override
    default T orElseLazy(@NotNull Lazy<? extends T> lazy) {
        return this.getValue();
    }

    @NotNull
    @Override
    default <E extends Throwable> T orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        return this.getValue();
    }

    @NotNull
    @Override
    default Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return IteratorUtil.singleSupplied(this::getValue);
    }

    @NotNull
    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), 1L,
                Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED);
    }

    @NotNull
    @Override
    default Optional<T> toOptional() {
        return Optional.ofNullable(this.getValue());
    }

    @NotNull
    @Override
    default Opt<O> $or(@NotNull Supplier<? extends Opt<O>> supplier) {
        return this;
    }

    @NotNull
    @Override
    default O or(@NotNull Supplier<? extends O> supplier) {
        return this.cast();
    }
}
