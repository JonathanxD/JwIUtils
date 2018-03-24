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

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
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

    @Override
    public Result<R, E> successOrResult(Result<R, E> other) {
        return other;
    }

    @Override
    public Result<R, E> successOrResult(Supplier<? extends Result<R, E>> otherSupplier) {
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

    @Override
    public Result<R, E> errorOrResult(Result<R, E> other) {
        return this;
    }

    @Override
    public Result<R, E> errorOrResult(Supplier<? extends Result<R, E>> otherSupplier) {
        return this;
    }

    @Override
    public void consume(Consumer<? super R> okConsumer, Consumer<? super E> errorConsumer) {
        errorConsumer.accept(this.error());
    }

    @Override
    public void ifSuccess(Consumer<? super R> consumer) {

    }

    @Override
    public void ifError(Consumer<? super E> consumer) {
        consumer.accept(this.error());
    }

    @Override
    public Result<R, E> recover(Supplier<R> result) {
        return Result.ok(result.get());
    }

    @Override
    public Result<R, E> recover(Function<E, R> toResultFunc) {
        return Result.ok(toResultFunc.apply(this.error()));
    }

    @Override
    public Result<R, E> fail(Supplier<E> error) {
        return this;
    }

    @Override
    public Result<R, E> fail(Function<R, E> toErrorFunc) {
        return this;
    }

    @Override
    public <MR, ME> Result<MR, ME> map(Function<? super R, ? extends MR> okMapper,
                                       Function<? super E, ? extends ME> errorMapper) {
        return Result.error(errorMapper.apply(this.error()));
    }

    @Override
    public <MR> Result<MR, E> mapSuccess(Function<? super R, ? extends MR> okMapper) {
        return this.as();
    }

    @Override
    public <ME> Result<R, ME> mapError(Function<? super E, ? extends ME> errorMapper) {
        return Result.error(errorMapper.apply(this.error));
    }

    @Override
    public <MR, ME> Result<MR, ME> flatMap(Function<? super R, ? extends Result<MR, ME>> okMapper,
                                           Function<? super E, ? extends Result<MR, ME>> errorMapper) {
        return errorMapper.apply(this.error());
    }

    @Override
    public <MR> Result<MR, E> flatMapSuccess(Function<? super R, ? extends Result<MR, E>> okMapper) {
        return this.as();
    }

    @Override
    public <ME> Result<R, ME> flatMapError(Function<? super E, ? extends Result<R, ME>> errorMapper) {
        return errorMapper.apply(this.error());
    }

    @Override
    public Result<R, E> mapSuccessToError(Function<R, E> toErrorFunc) {
        return this;
    }

    @Override
    public Result<R, E> mapErrorToSuccess(Function<E, R> toOkFunc) {
        return Result.ok(toOkFunc.apply(this.error()));
    }

    @Override
    public <B> B getSuccessOrError(Function<R, B> okMapper, Function<E, B> errorMapper) {
        return errorMapper.apply(this.error());
    }

    @Override
    public Result<E, R> swap() {
        return Result.ok(this.error());
    }
}
