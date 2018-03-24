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

class OkImpl<R, E> extends Result.Ok<R, E> {

    private final R value;

    OkImpl(R value) {
        this.value = value;
    }

    private R getValue() {
        return this.value;
    }

    @Override
    public R success() {
        return this.getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <ME> Ok<R, ME> as() {
        return (Ok<R, ME>) this;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Nullable
    @Override
    public R successOrNull() {
        return this.success();
    }

    @Override
    public R successOr(R other) {
        return this.success();
    }

    @Override
    public R successOrGet(Supplier<R> supplier) {
        return this.success();
    }

    @Override
    public Result<R, E> successOrResult(Result<R, E> other) {
        return this;
    }

    @Override
    public Result<R, E> successOrResult(Supplier<? extends Result<R, E>> otherSupplier) {
        return this;
    }

    @Override
    public @Nullable E errorOrNull() {
        return null;
    }

    @Override
    public E errorOr(E other) {
        return other;
    }

    @Override
    public E errorOrGet(Supplier<E> supplier) {
        return supplier.get();
    }

    @Override
    public Result<R, E> errorOrResult(Result<R, E> other) {
        return other;
    }

    @Override
    public Result<R, E> errorOrResult(Supplier<? extends Result<R, E>> otherSupplier) {
        return otherSupplier.get();
    }

    @Override
    public void consume(Consumer<? super R> okConsumer, Consumer<? super E> errorConsumer) {
        okConsumer.accept(this.success());
    }

    @Override
    public void ifSuccess(Consumer<? super R> consumer) {
        consumer.accept(this.success());
    }

    @Override
    public void ifError(Consumer<? super E> consumer) {

    }

    @Override
    public Result<R, E> recover(Supplier<R> result) {
        return this;
    }

    @Override
    public Result<R, E> recover(Function<E, R> toResultFunc) {
        return this;
    }

    @Override
    public Result<R, E> fail(Supplier<E> error) {
        return Result.error(error.get());
    }

    @Override
    public Result<R, E> fail(Function<R, E> toErrorFunc) {
        return Result.error(toErrorFunc.apply(this.success()));
    }

    @Override
    public <MR, ME> Result<MR, ME> map(Function<? super R, ? extends MR> okMapper,
                                       Function<? super E, ? extends ME> errorMapper) {
        return Result.ok(okMapper.apply(this.success()));
    }

    @Override
    public <MR> Result<MR, E> mapSuccess(Function<? super R, ? extends MR> okMapper) {
        return Result.ok(okMapper.apply(this.success()));
    }

    @Override
    public <ME> Result<R, ME> mapError(Function<? super E, ? extends ME> errorMapper) {
        return this.as();
    }

    @Override
    public <MR, ME> Result<MR, ME> flatMap(Function<? super R, ? extends Result<MR, ME>> okMapper,
                                           Function<? super E, ? extends Result<MR, ME>> errorMapper) {
        return okMapper.apply(this.success());
    }

    @Override
    public <MR> Result<MR, E> flatMapSuccess(Function<? super R, ? extends Result<MR, E>> okMapper) {
        return okMapper.apply(this.success());
    }

    @Override
    public <ME> Result<R, ME> flatMapError(Function<? super E, ? extends Result<R, ME>> errorMapper) {
        return this.as();
    }

    @Override
    public Result<R, E> mapSuccessToError(Function<R, E> toErrorFunc) {
        return Result.error(toErrorFunc.apply(this.success()));
    }

    @Override
    public Result<R, E> mapErrorToSuccess(Function<E, R> toOkFunc) {
        return this;
    }

    @Override
    public <B> B getSuccessOrError(Function<R, B> okMapper,
                                   Function<E, B> errorMapper) {
        return okMapper.apply(this.success());
    }

    @Override
    public Result<E, R> swap() {
        return Result.error(this.success());
    }
}
