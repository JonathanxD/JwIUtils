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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface DefBaseNoneObj<T, O extends BaseOptObject<T, O>> extends BaseNone<O>, BaseOptObject<T, O> {

    @Override
    default void ifPresent(@NotNull Consumer<? super T> consumer) {
    }

    @Override
    default void ifPresent(@NotNull Consumer<? super T> consumer, @NotNull Runnable elseRunnable) {
        Objects.requireNonNull(elseRunnable);
        elseRunnable.run();
    }

    @NotNull
    @Override
    default O filter(@NotNull Predicate<? super T> predicate) {
        return this.cast();
    }

    @Override
    default <O2 extends Opt<O2>> @NotNull O2 flatMapTo(@NotNull Function<? super T, ? extends O2> mapper, @NotNull Supplier<O2> none) {
        Objects.requireNonNull(none);
        return Objects.requireNonNull(none.get());
    }

    @NotNull
    @Override
    default T orElse(@NotNull T value) {
        return value;
    }

    @NotNull
    @Override
    default T orElseGet(@NotNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return supplier.get();
    }

    @NotNull
    @Override
    default T orElseLazy(@NotNull Lazy<? extends T> lazy) {
        Objects.requireNonNull(lazy);
        return Lazy.nonNull(lazy).get();
    }

    @NotNull
    @Override
    default <E extends Throwable> T orElseFailStupidly(@NotNull Supplier<? extends E> supplier) throws E {
        Objects.requireNonNull(supplier);
        throw Objects.<E>requireNonNull(supplier.get());
    }

    @NotNull
    @Override
    default Stream<T> stream() {
        return Stream.empty();
    }

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return IteratorUtil.emptyIterator();
    }

    @NotNull
    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.emptySpliterator();
    }

    @NotNull
    @Override
    default Optional<T> toOptional() {
        return Optional.empty();
    }

    @NotNull
    @Override
    default Opt<O> $or(@NotNull Supplier<? extends Opt<O>> supplier) {
        return supplier.get();
    }

    @NotNull
    @Override
    default O or(@NotNull Supplier<? extends O> supplier) {
        return supplier.get();
    }
}
