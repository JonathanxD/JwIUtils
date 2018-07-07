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
package com.github.jonathanxd.iutils.object.result;

import com.github.jonathanxd.iutils.function.combiner.Combiner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class ErrImpl<R, E> extends Result.Err<R, E> {
    private final E error;

    ErrImpl(E error) {
        this.error = error;
    }

    @Override
    public final boolean isSuccess() {
        return false;
    }

    @Override
    public final boolean isError() {
        return true;
    }

    private E getError() {
        return this.error;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public <MR> Err<MR, E> as() {
        return (Err<MR, E>) this;
    }

    @Override
    public E error() {
        return this.getError();
    }

    @Nullable
    @Override
    public R successOrNull() {
        return null;
    }

    @Override
    public R successOr(R other) {
        return other;
    }

    @Override
    public R successOrGet(Supplier<R> supplier) {
        return supplier.get();
    }

    @NotNull
    @Override
    public Result<R, E> successOrResult(@NotNull Result<R, E> other) {
        return other;
    }

    @NotNull
    @Override
    public Result<R, E> successOrResult(@NotNull Supplier<? extends Result<R, E>> otherSupplier) {
        return otherSupplier.get();
    }

    @Nullable
    @Override
    public E errorOrNull() {
        return this.error();
    }

    @Override
    public E errorOr(E other) {
        return this.error();
    }

    @Override
    public E errorOrGet(Supplier<E> supplier) {
        return this.error();
    }

    @NotNull
    @Override
    public Result<R, E> errorOrResult(@NotNull Result<R, E> other) {
        return this;
    }

    @NotNull
    @Override
    public Result<R, E> errorOrResult(@NotNull Supplier<? extends Result<R, E>> otherSupplier) {
        return this;
    }

    @NotNull
    @Override
    public void consume(@NotNull Consumer<? super R> okConsumer,
                        @NotNull Consumer<? super E> errorConsumer) {
        errorConsumer.accept(this.error());
    }

    @Override
    public void ifSuccess(@NotNull Consumer<? super R> consumer) {
    }

    @Override
    public void ifError(@NotNull Consumer<? super E> consumer) {
        consumer.accept(this.error());
    }

    @NotNull
    @Override
    public Result<R, E> recover(@NotNull Supplier<R> result) {
        return Result.ok(result.get());
    }

    @NotNull
    @Override
    public Result<R, E> recover(@NotNull Function<E, R> toResultFunc) {
        return Result.ok(toResultFunc.apply(this.error()));
    }

    @NotNull
    @Override
    public Result<R, E> fail(@NotNull Supplier<E> error) {
        return this;
    }

    @NotNull
    @Override
    public Result<R, E> fail(@NotNull Function<R, E> toErrorFunc) {
        return this;
    }

    @NotNull
    @Override
    public <MR, ME> Result<MR, ME> map(@NotNull Function<? super R, ? extends MR> okMapper,
                                       @NotNull Function<? super E, ? extends ME> errorMapper) {
        return Result.error(errorMapper.apply(this.error()));
    }

    @NotNull
    @Override
    public <MR> Result<MR, E> map(@NotNull Function<? super R, ? extends MR> okMapper) {
        return this.as();
    }

    @NotNull
    @Override
    public <ME> Result<R, ME> mapError(@NotNull Function<? super E, ? extends ME> errorMapper) {
        return Result.error(errorMapper.apply(this.error));
    }

    @NotNull
    @Override
    public <MR, ME> Result<MR, ME> flatMap(@NotNull Function<? super R, ? extends Result<MR, ME>> okMapper,
                                           @NotNull Function<? super E, ? extends Result<MR, ME>> errorMapper) {
        return errorMapper.apply(this.error());
    }

    @NotNull
    @Override
    public <MR> Result<MR, E> flatMap(@NotNull Function<? super R, ? extends Result<MR, E>> okMapper) {
        return this.as();
    }

    @NotNull
    @Override
    public <ME> Result<R, ME> flatMapError(@NotNull Function<? super E, ? extends Result<R, ME>> errorMapper) {
        return errorMapper.apply(this.error());
    }

    @NotNull
    @Override
    public Result<R, E> test(@NotNull Predicate<? super R> predicate,
                             @NotNull Function<? super R, ? extends Result<R, E>> otherwise) {
        return this;
    }

    @NotNull
    @Override
    public Result<R, E> testError(@NotNull Predicate<? super E> predicate,
                                  @NotNull Function<? super E, ? extends Result<R, E>> otherwise) {
        if (predicate.test(this.getError())) {
            return this;
        } else {
            return otherwise.apply(this.getError());
        }
    }

    @NotNull
    @Override
    public Result<R, E> mapSuccessToError(@NotNull Function<R, E> toErrorFunc) {
        return this;
    }

    @NotNull
    @Override
    public Result<R, E> mapErrorToSuccess(@NotNull Function<E, R> toOkFunc) {
        return Result.ok(toOkFunc.apply(this.error()));
    }

    @NotNull
    @Override
    public <B> B getSuccessOrError(@NotNull Function<R, B> okMapper,
                                   @NotNull Function<E, B> errorMapper) {
        return errorMapper.apply(this.error());
    }

    @NotNull
    @Override
    public Result<E, R> swap() {
        return Result.ok(this.error());
    }

    @Override
    public @NotNull <MR, ME, CR, CE> Result<CR, CE> combine(@NotNull Result<MR, ME> other,
                                                            @NotNull Combiner<R, MR, CR> successCombiner,
                                                            @NotNull Combiner<E, ME, CE> errorCombiner) {
        if (other instanceof Result.Ok<?, ?>) {
            return Result.ok(successCombiner.combineSecond(((Ok<MR, E>) other).success()));
        } else if (other instanceof Result.Err<?, ?>) {
            return Result.error(errorCombiner.combine(this.error(), ((Err<MR, ME>) other).error()));
        } else {
            throw new IllegalArgumentException("The 'other' result instance must either implement `Result.Ok<R, E>` or `Result.Err<R, E>`.");
        }
    }

    @Override
    public @NotNull <MR, CR> Result<CR, E> combineSuccess(@NotNull Result<MR, E> other,
                                                          @NotNull Combiner<R, MR, CR> combiner) {
        if (other instanceof Result.Ok<?, ?>) {
            return Result.ok(combiner.combineSecond(((Ok<MR, E>) other).success()));
        } else if (other instanceof Result.Err<?, ?>) {
            return this.as();
        } else {
            throw new IllegalArgumentException("The 'other' result instance must either implement `Result.Ok<R, E>` or `Result.Err<R, E>`.");
        }
    }

    @Override
    public @NotNull <ME, CE> Result<R, CE> combineError(@NotNull Result<R, ME> other,
                                                        @NotNull Combiner<E, ME, CE> combiner) {
        if (other instanceof Result.Ok<?, ?>) {
            return Result.error(combiner.combineFirst(this.error()));
        } else if (other instanceof Result.Err<?, ?>) {
            return Result.error(combiner.combine(this.error(), ((Err<R, ME>) other).error()));
        } else {
            throw new IllegalArgumentException("The 'other' result instance must either implement `Result.Ok<R, E>` or `Result.Err<R, E>`.");
        }
    }
}
