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

import com.github.jonathanxd.iutils.function.checked.supplier.CSupplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Result represents either the {@link Ok success} of an operation or the {@link Err failure} of
 * the operation because of an {@link E error}.
 *
 * A simple example of usage of result would be to parse a Number from a String:
 *
 * <pre>
 *     {@code
 *     Result<Integer, NumberFormatException> number
 *     }
 * </pre>
 *
 * @param <R> Success value type.
 * @param <E> Error value type.
 */
public abstract class Result<R, E> {

    /**
     * Tries to get value supplied by {@code supplier} and returns a {@link Result.Ok success
     * result} with the value if value was supplied without exceptions or {@link Result.Err error
     * result} if the supplier failed with an exception.
     *
     * @param supplier Supplier of value.
     * @param <R>      Result value type.
     * @return Result of operation.
     */
    public static <R> Result<R, Throwable> Try(CSupplier<R> supplier) {
        try {
            return Result.ok(supplier.getChecked());
        } catch (Throwable t) {
            return Result.error(t);
        }
    }

    /**
     * Creates an {@link Ok} instance with {@code result} as {@link Ok#success() success value}.
     *
     * @param result Result value.
     * @param <R>    Result type.
     * @param <E>    Error type.
     * @return {@link Ok} instance of {@code result}.
     */
    @NotNull
    public static <R, E> Ok<R, E> ok(R result) {
        return new OkImpl<>(result);
    }

    /**
     * Creates an {@link Ok} instance with {@code result} as {@link Ok#success() success value}.
     *
     * @param result Result value.
     * @param <R>    Result type.
     * @param <E>    Error type.
     * @return {@link Ok} instance of {@code result}.
     */
    @NotNull
    public static <R, E> Ok<R, E> Ok(R result) {
        return new OkImpl<>(result);
    }

    /**
     * Creates an {@link Err} instance with {@code error} as {@link Err#error() error value}.
     *
     * @param error Error value.
     * @param <R>   Result type.
     * @param <E>   Error type.
     * @return {@link Err} instance of {@code error}.
     */
    @NotNull
    public static <R, E> Err<R, E> error(E error) {
        return new ErrImpl<>(error);
    }

    /**
     * Creates an {@link Err} instance with {@code error} as {@link Err#error() error value}.
     *
     * @param error Error value.
     * @param <R>   Result type.
     * @param <E>   Error type.
     * @return {@link Err} instance of {@code error}.
     */
    @NotNull
    public static <R, E> Err<R, E> Err(E error) {
        return new ErrImpl<>(error);
    }

    /**
     * Returns whether this is a success result or not.
     *
     * @return Whether this is a success result or not.
     * @see Ok
     */
    public abstract boolean isSuccess();

    /**
     * Returns whether this is a success result or not.
     *
     * @return Whether this is a success result or not.
     * @see Ok
     */
    public final boolean isOk() {
        return this.isSuccess();
    }

    /**
     * Returns whether this is an error result or not.
     *
     * @return Whether this is an error result or not.
     * @see Err
     */
    public abstract boolean isError();

    /**
     * Returns the success value or {@code null} if this is not a {@link #isSuccess() success
     * result}.
     *
     * @return The success value or {@code null} if this is not a {@link #isSuccess() success
     * result}.
     */
    @Nullable
    public abstract R successOrNull();

    /**
     * Returns the success value or {@code other} if this is not a {@link #isSuccess() success
     * result}.
     *
     * @return The success value or {@code other} if this is not a {@link #isSuccess() success
     * result}.
     */
    public abstract R successOr(R other);

    /**
     * Returns the {@code success} value or value supplied by {@code supplier} if this is not a
     * {@link #isSuccess() success result}.
     *
     * @return The {@code success} value or value supplied by {@code supplier} if this is not a
     * {@link #isSuccess() success result}.
     */
    public abstract R successOrGet(Supplier<R> supplier);

    /**
     * Returns this result or {@code other} if this is not a {@link #isSuccess() success result}.
     *
     * @return This result or {@code other} if this is not a {@link #isSuccess() success result}.
     */
    public abstract Result<R, E> successOrResult(Result<R, E> other);

    /**
     * Returns this result or result supplied by {@code otherSupplier} if this is not a {@link
     * #isSuccess() success result}.
     *
     * @return This result or result supplied by {@code otherSupplier} if this is not a {@link
     * #isSuccess() success result}.
     */
    public abstract Result<R, E> successOrResult(Supplier<? extends Result<R, E>> otherSupplier);

    /**
     * Returns the error value or {@code null} if this is not an {@link #isError() error result}.
     *
     * @return The error value or {@code null} if this is not an {@link #isError() error result}.
     */
    @Nullable
    public abstract E errorOrNull();

    /**
     * Returns the error value or {@code other} if this is not an {@link #isError() error result}.
     *
     * @return The error value or {@code other} if this is not an {@link #isError() error result}.
     */
    public abstract E errorOr(E other);

    /**
     * Returns the error value or error supplied by {@code supplier} if this is not an {@link
     * #isError() error result}.
     *
     * @return The error value or error supplied by {@code supplier} if this is not an {@link
     * #isError() error result}.
     */
    public abstract E errorOrGet(Supplier<E> supplier);

    /**
     * Returns this result or {@code success} if this is not an {@link #isError() error result}.
     *
     * @return This result or {@code success} if this is not an {@link #isError() error result}.
     */
    public abstract Result<R, E> errorOrResult(Result<R, E> other);

    /**
     * Returns this result or result supplied by {@code otherSupplier} if this is not an {@link
     * #isError() error result}.
     *
     * @return This result or result supplied by {@code otherSupplier} if this is not an {@link
     * #isError() error result}.
     */
    public abstract Result<R, E> errorOrResult(Supplier<? extends Result<R, E>> otherSupplier);

    /**
     * Success value or null. (Kotlin compatibility purpose)
     */
    @Nullable
    public final R component1() {
        return this.successOrNull();
    }

    /**
     * Error value or null. (Kotlin compatibility purpose)
     */
    @Nullable
    public final E component2() {
        return this.errorOrNull();
    }

    /**
     * Consumes {@code success} value with {@code okConsumer} if this is a {@link #isSuccess()
     * success result}, or the {@code error} value with {@code errorConsumer} if this is an {@link
     * #isError() error result}.
     *
     * @param okConsumer    Consumer of {@code success} value.
     * @param errorConsumer Consumer of {@code error} value.
     */
    public abstract void consume(Consumer<? super R> okConsumer, Consumer<? super E> errorConsumer);

    /**
     * Consumes {@code success} value with {@code okConsumer} if this is a {@link #isSuccess()
     * success result}.
     *
     * @param consumer Consumer of success result.
     */
    public abstract void ifSuccess(Consumer<? super R> consumer);

    /**
     * Consumes {@code error} value with {@code errorConsumer} if this is an {@link #isError() error
     * result}.
     *
     * @param consumer Consumer of error value.
     */
    public abstract void ifError(Consumer<? super E> consumer);

    /**
     * Returns a {@link Result} recovered from error to a value supplied by {@code success}, or
     * returns the same {@link Result} if this is a {@link #isSuccess() success result}.
     *
     * @param result Supplier of recovered value.
     * @return {@link Result} recovered from error to a value supplied by {@code success}, or
     * returns the same {@link Result} if this is a {@link #isSuccess() success result}.
     */
    public abstract Result<R, E> recover(Supplier<R> result);

    /**
     * Returns a {@link Result} recovered from error to a value transformed by {@code toResultFunc}
     * from {@code error} value, or returns the same {@link Result} if this is a {@link #isSuccess()
     * success result}.
     *
     * @param toResultFunc Transformer of error to recovered value.
     * @return {@link Result} recovered from error to a value transformed by {@code toResultFunc}
     * from {@code error} value, or returns the same {@link Result} if this is a {@link #isSuccess()
     * success result}.
     */
    public abstract Result<R, E> recover(Function<E, R> toResultFunc);

    /**
     * Returns a {@link Result} failed with a value supplied by {@code error}, or returns the same
     * {@link Result} if this is an {@link #isError() error result}.
     *
     * @param error Error value.
     * @return {@link Result} failed with a value supplied by {@code error}, or returns the same
     * {@link Result} if this is an {@link #isError() error result}.
     */
    public abstract Result<R, E> fail(Supplier<E> error);

    /**
     * Returns a {@link Result} failed with a value transformed by {@code toErrorFunc} from {@code
     * result} value, or returns the same {@link Result} if this is an {@link #isError() error
     * result}.
     *
     * @param toErrorFunc Transformer of success value to error.
     * @return {@link Result} failed with a value transformed by {@code toErrorFunc} from {@code
     * result} value, or returns the same {@link Result} if this is an {@link #isError() error
     * result}.
     */
    public abstract Result<R, E> fail(Function<R, E> toErrorFunc);

    /**
     * Maps {@code success} with {@code okMapper} if this is a {@link #isSuccess() success result}
     * or maps {@code error} with {@code errorMapper} if this is an {@link #isError() error
     * result}.
     *
     * @param okMapper    Mapper of {@code success} value.
     * @param errorMapper Mapper of {@code error} value.
     * @param <MR>        New success value type.
     * @param <ME>        New error value type.
     * @return New result mapped with either a success result mapped with {@code okMapper} or an
     * error result mapped with {@code errorMapper}.
     */
    public abstract <MR, ME> Result<MR, ME> map(Function<? super R, ? extends MR> okMapper,
                                                Function<? super E, ? extends ME> errorMapper);


    /**
     * Maps {@code success} with {@code okMapper} if this is a {@link #isSuccess() success result},
     * or returns same instance if this is an {@link #isError() error result}.
     *
     * @param okMapper Mapper of {@code success} value.
     * @param <MR>     New success value type.
     * @return New result with mapped {@code success} or same result if this is an {@link #isError()
     * error result},
     */
    public abstract <MR> Result<MR, E> mapSuccess(Function<? super R, ? extends MR> okMapper);

    /**
     * Maps {@code error} with {@code errorMapper} if this is an {@link #isError() error result}, or
     * returns same instance if this is a {@link #isSuccess() success result}.
     *
     * @param errorMapper Mapper of {@code error} value.
     * @param <ME>        New error value type.
     * @return New result with mapped {@code error} or same result if this is a {@link #isSuccess()
     * success result},
     */
    public abstract <ME> Result<R, ME> mapError(Function<? super E, ? extends ME> errorMapper);

    /**
     * Maps this result to a new {@link #isSuccess() success result} with {@code okMapper} if this
     * is a {@link #isSuccess() success result} or maps this result to a new {@link #isError() error
     * result} with {@code errorMapper} if this is an {@link #isError() error result}.
     *
     * @param okMapper    Mapper of {@code success} value to a new {@link #isSuccess() success
     *                    result}.
     * @param errorMapper Mapper of {@code error} value to a new {@link #isError() error result}.
     * @param <MR>        New success value type.
     * @param <ME>        New error value type.
     * @return New result mapped to either a new {@link #isSuccess() success result} or a new {@link
     * #isError() error result}.
     */
    public abstract <MR, ME> Result<MR, ME> flatMap(Function<? super R, ? extends Result<MR, ME>> okMapper,
                                                    Function<? super E, ? extends Result<MR, ME>> errorMapper);

    /**
     * Maps this result to a new {@link #isSuccess() success result} with {@code okMapper} if this
     * is a {@link #isSuccess() success result} or returns same result if this is an {@link
     * #isError() error result}.
     *
     * @param okMapper Mapper of {@code success} value to a new {@link #isSuccess() success
     *                 result}.
     * @param <MR>     New success value type.
     * @return New result mapped to a new success result or the same result if this is an {@link
     * #isError() error result}.
     */
    public abstract <MR> Result<MR, E> flatMapSuccess(Function<? super R, ? extends Result<MR, E>> okMapper);

    /**
     * Maps this result to a new {@link #isError() error result} with {@code errorMapper} if this is
     * an {@link #isError() error result} or returns same result if this is a {@link #isSuccess()
     * success result}.
     *
     * @param errorMapper Mapper of {@code error} value to a new {@link #isError() error result}.
     * @param <ME>        New error value type.
     * @return New result mapped to a new {@link #isError() error result} or the same result if this
     * is an {@link #isSuccess() success result}.
     */
    public abstract <ME> Result<R, ME> flatMapError(Function<? super E, ? extends Result<R, ME>> errorMapper);

    /**
     * Maps this {@link #isSuccess() success result} to an {@link #isError() error result} or
     * returns the same result if this is an {@link #isError() error result}.
     *
     * @param toErrorFunc Function that maps {@code success} value to an {@code error} value.
     * @return A new {@link #isError() error result} with {@code success} value mapped to an {@code
     * error} value or the same result if this is an {@link #isError() error result}.
     */
    public abstract Result<R, E> mapSuccessToError(Function<R, E> toErrorFunc);

    /**
     * Maps this {@link #isError() error result} to a {@link #isSuccess() success result} or returns
     * the same result if this is an {@link #isSuccess() success result}.
     *
     * @param toOkFunc Function that maps {@code error} value to a {@code success} value.
     * @return A new {@link #isSuccess() success result} with {@code error} value mapped to a {@code
     * result} value or the same result if this is a {@link #isSuccess() success result}.
     */
    public abstract Result<R, E> mapErrorToSuccess(Function<E, R> toOkFunc);

    /**
     * Returns either {@code success} or {@code error} mapped with {@code okMapper} or {@code
     * errorMapper} respectively.
     *
     * @param okMapper    Mapper of {@code success} value.
     * @param errorMapper Mapper of {@code error} value.
     * @param <B>         Type of value to return.
     * @return Either {@code success} or {@code error} mapped with {@code okMapper} or {@code
     * errorMapper} respectively.
     */
    public abstract <B> B getSuccessOrError(Function<R, B> okMapper, Function<E, B> errorMapper);

    /**
     * Swaps {@code error} and {@code success} value.
     *
     * @return Either a {@link #isSuccess() success result} with {@code error} value or an {@link
     * #isError() error result} with {@code success} value.
     */
    public abstract Result<E, R> swap();

    /**
     * Represents the success result.
     *
     * @param <R> Result type.
     * @param <E> Error type.
     */
    public static abstract class Ok<R, E> extends Result<R, E> {
        public abstract R success();

        /**
         * Casts this {@link Ok} instance of {@link R} and {@link E} to a {@code Ok} instance of
         * {@link R} and {@link ME}, this cast is possible because it is safe to assume that an
         * {@code Ok} instance of {@link R} and {@link E} can have any {@link E error type} because
         * it does not hold any error instance.
         *
         * @param <ME> New error type.
         * @return {@link Ok} instance of {@link R} and {@link ME}.
         */
        public abstract <ME> Ok<R, ME> as();

    }

    /**
     * Represents the error result.
     *
     * @param <R> Result type.
     * @param <E> Error type.
     */
    public static abstract class Err<R, E> extends Result<R, E> {
        public abstract E error();

        /**
         * Casts this {@link Err} instance of {@link R} and {@link E} to an {@link Err} instance of
         * {@link MR} and {@link E}, this cast is possible because it is safe to assume that an
         * {@code Err} instance of {@link R} and {@link E} can have any {@link R result type}
         * because it does not hold any result instance.
         *
         * @param <MR> New result type.
         * @return {@link Err} instance of {@link MR} and {@link E}.
         */
        public abstract <MR> Err<MR, E> as();

    }


}
