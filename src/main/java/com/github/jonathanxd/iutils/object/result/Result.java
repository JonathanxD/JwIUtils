/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.object.result;

import com.github.jonathanxd.iutils.function.checked.supplier.CSupplier;
import com.github.jonathanxd.iutils.function.combiner.Combiner;
import com.github.jonathanxd.iutils.function.combiner.Combiners;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
    @NotNull
    public static <R> Result<R, Throwable> Try(@NotNull CSupplier<R> supplier) {
        try {
            return Result.ok(supplier.getChecked());
        } catch (Throwable t) {
            return Result.error(t);
        }
    }

    /**
     * Tests {@code value} with {@code predicate}. If {@code predicate} returns {@code true}, then
     * return {@link #Ok(Object) success result}, otherwise call {@code otherwise} with {@code
     * value} and return it.
     *
     * @param value     Value to test.
     * @param predicate Predicate to test value.
     * @param otherwise Function to map {@code value} to a {@link Result} if {@code predicate}
     *                  returns false.
     * @param <R>       Result type.
     * @param <E>       Error type.
     * @return Result of operation.
     */
    public static <R, E> Result<R, E> test(R value,
                                           Predicate<? super R> predicate,
                                           Function<? super R, ? extends Result<R, E>> otherwise) {
        return predicate.test(value) ? Ok(value) : otherwise.apply(value);
    }

    /**
     * Tests {@code error} with {@code predicate}. If {@code predicate} returns {@code true}, then
     * return {@link #Err(Object) error result}, otherwise call {@code otherwise} with {@code value}
     * and return it.
     *
     * @param error     Error value to test.
     * @param predicate Predicate to test value.
     * @param otherwise Function to map {@code value} to a {@link Result} if {@code predicate}
     *                  returns false.
     * @param <R>       Result type.
     * @param <E>       Error type.
     * @return Result of operation.
     */
    public static <R, E> Result<R, E> testError(E error,
                                                Predicate<? super E> predicate,
                                                Function<? super E, ? extends Result<R, E>> otherwise) {
        return predicate.test(error) ? Err(error) : otherwise.apply(error);
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
    @NotNull
    public abstract Result<R, E> successOrResult(@NotNull Result<R, E> other);

    /**
     * Returns this result or result supplied by {@code otherSupplier} if this is not a {@link
     * #isSuccess() success result}.
     *
     * @return This result or result supplied by {@code otherSupplier} if this is not a {@link
     * #isSuccess() success result}.
     */
    @NotNull
    public abstract Result<R, E> successOrResult(@NotNull Supplier<? extends Result<R, E>> otherSupplier);

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
    @NotNull
    public abstract Result<R, E> errorOrResult(@NotNull Result<R, E> other);

    /**
     * Returns this result or result supplied by {@code otherSupplier} if this is not an {@link
     * #isError() error result}.
     *
     * @return This result or result supplied by {@code otherSupplier} if this is not an {@link
     * #isError() error result}.
     */
    @NotNull
    public abstract Result<R, E> errorOrResult(@NotNull Supplier<? extends Result<R, E>> otherSupplier);

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
    public abstract void consume(@NotNull Consumer<? super R> okConsumer,
                                 @NotNull Consumer<? super E> errorConsumer);

    /**
     * Consumes {@code success} value with {@code okConsumer} if this is a {@link #isSuccess()
     * success result}.
     *
     * @param consumer Consumer of success result.
     */
    public abstract void ifSuccess(@NotNull Consumer<? super R> consumer);

    /**
     * Consumes {@code error} value with {@code errorConsumer} if this is an {@link #isError() error
     * result}.
     *
     * @param consumer Consumer of error value.
     */
    public abstract void ifError(@NotNull Consumer<? super E> consumer);

    /**
     * Returns a {@link Result} recovered from error to a value supplied by {@code success}, or
     * returns the same {@link Result} if this is a {@link #isSuccess() success result}.
     *
     * @param result Supplier of recovered value.
     * @return {@link Result} recovered from error to a value supplied by {@code success}, or
     * returns the same {@link Result} if this is a {@link #isSuccess() success result}.
     */
    @NotNull
    public abstract Result<R, E> recover(@NotNull Supplier<R> result);

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
    @NotNull
    public abstract Result<R, E> recover(@NotNull Function<E, R> toResultFunc);

    /**
     * Returns a {@link Result} failed with a value supplied by {@code error}, or returns the same
     * {@link Result} if this is an {@link #isError() error result}.
     *
     * @param error Error value.
     * @return {@link Result} failed with a value supplied by {@code error}, or returns the same
     * {@link Result} if this is an {@link #isError() error result}.
     */
    @NotNull
    public abstract Result<R, E> fail(@NotNull Supplier<E> error);

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
    @NotNull
    public abstract Result<R, E> fail(@NotNull Function<R, E> toErrorFunc);

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
    @NotNull
    public abstract <MR, ME> Result<MR, ME> map(@NotNull Function<? super R, ? extends MR> okMapper,
                                                @NotNull Function<? super E, ? extends ME> errorMapper);


    /**
     * Maps {@code success} with {@code okMapper} if this is a {@link #isSuccess() success result},
     * or returns same instance if this is an {@link #isError() error result}.
     *
     * @param okMapper Mapper of {@code success} value.
     * @param <MR>     New success value type.
     * @return New result with mapped {@code success} or same result if this is an {@link #isError()
     * error result},
     */
    @NotNull
    public abstract <MR> Result<MR, E> map(@NotNull Function<? super R, ? extends MR> okMapper);

    /**
     * Maps {@code error} with {@code errorMapper} if this is an {@link #isError() error result}, or
     * returns same instance if this is a {@link #isSuccess() success result}.
     *
     * @param errorMapper Mapper of {@code error} value.
     * @param <ME>        New error value type.
     * @return New result with mapped {@code error} or same result if this is a {@link #isSuccess()
     * success result},
     */
    @NotNull
    public abstract <ME> Result<R, ME> mapError(@NotNull Function<? super E, ? extends ME> errorMapper);

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
    @NotNull
    public abstract <MR, ME> Result<MR, ME> flatMap(@NotNull Function<? super R, ? extends Result<MR, ME>> okMapper,
                                                    @NotNull Function<? super E, ? extends Result<MR, ME>> errorMapper);

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
    @NotNull
    public abstract <MR> Result<MR, E> flatMap(@NotNull Function<? super R, ? extends Result<MR, E>> okMapper);

    /**
     * Tests if {@link #isSuccess() success result} matches {@code predicate}, returning a {@link
     * Result} with same {@link #isSuccess() success result} if {@code predicate} returns {@code
     * true}, or returning {@link Result} provided by {@code otherwise} if {@code predicate} returns
     * {@code false}.
     *
     * @param predicate Predicate to test {@link #isSuccess() success result}.
     * @param otherwise Function that returns the {@link Result} if {@code predicate} does not match
     *                  current value.
     * @return Either current result or result returned by {@code otherwise}.
     */
    @NotNull
    public abstract Result<R, E> test(@NotNull Predicate<? super R> predicate,
                                      @NotNull Function<? super R, ? extends Result<R, E>> otherwise);

    /**
     * Tests if {@link #isError() error result} matches {@code predicate}, returning a {@link
     * Result} with same {@link #isError() error result} if {@code predicate} returns {@code true},
     * or returning {@link Result} provided by {@code otherwise} if {@code predicate} returns {@code
     * false}.
     *
     * @param predicate Predicate to test {@link #isError() error result}.
     * @param otherwise Function that returns the {@link Result} if {@code predicate} does not match
     *                  current value.
     * @return Either current result or result returned by {@code otherwise}.
     */
    @NotNull
    public abstract Result<R, E> testError(@NotNull Predicate<? super E> predicate,
                                           @NotNull Function<? super E, ? extends Result<R, E>> otherwise);

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
    @NotNull
    public abstract <ME> Result<R, ME> flatMapError(@NotNull Function<? super E, ? extends Result<R, ME>> errorMapper);

    /**
     * Maps this {@link #isSuccess() success result} to an {@link #isError() error result} or
     * returns the same result if this is an {@link #isError() error result}.
     *
     * @param toErrorFunc Function that maps {@code success} value to an {@code error} value.
     * @return A new {@link #isError() error result} with {@code success} value mapped to an {@code
     * error} value or the same result if this is an {@link #isError() error result}.
     */
    @NotNull
    public abstract Result<R, E> mapSuccessToError(@NotNull Function<R, E> toErrorFunc);

    /**
     * Maps this {@link #isError() error result} to a {@link #isSuccess() success result} or returns
     * the same result if this is an {@link #isSuccess() success result}.
     *
     * @param toOkFunc Function that maps {@code error} value to a {@code success} value.
     * @return A new {@link #isSuccess() success result} with {@code error} value mapped to a {@code
     * result} value or the same result if this is a {@link #isSuccess() success result}.
     */
    @NotNull
    public abstract Result<R, E> mapErrorToSuccess(@NotNull Function<E, R> toOkFunc);

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
    @NotNull
    public abstract <B> B getSuccessOrError(@NotNull Function<R, B> okMapper,
                                            @NotNull Function<E, B> errorMapper);

    /**
     * Swaps {@code error} and {@code success} value.
     *
     * @return Either a {@link #isSuccess() success result} with {@code error} value or an {@link
     * #isError() error result} with {@code success} value.
     */
    @NotNull
    public abstract Result<E, R> swap();

    /**
     * Combines {@code this} {@link Result result} with {@code other} {@link Result result}. Both
     * {@code this} and {@code other} must be the same type of {@link Result result} ({@link
     * Result.Ok success} or {@link Result.Err error}), if they are not, {@link Result.Ok success result} will be returned.
     *
     * @param other           Other result to combine.
     * @param successCombiner Function which maps both success results into a combined one, either
     *                        first or second value may be {@code null} in case of absence.
     * @param errorCombiner   Function which maps both errors result into a combined one, either
     *                        first or second value may be {@code null} in case of absence.
     * @param <MR>            Other success result type.
     * @param <ME>            Other error result type.
     * @param <CR>            Combined success result type.
     * @param <CE>            Combined error result type.
     * @return Result with combined success and error.
     */
    @NotNull
    public abstract <MR, ME, CR, CE> Result<CR, CE> combine(@NotNull Result<MR, ME> other,
                                                            @NotNull Combiner<R, MR, CR> successCombiner,
                                                            @NotNull Combiner<E, ME, CE> errorCombiner);

    /**
     * Combines {@code this} {@link Result result} with {@code other} {@link Result result}. Both
     * {@code this} and {@code other} must be the same type of {@link Result result} ({@link
     * Result.Ok success} or {@link Result.Err error}), if they are not, {@link Result.Ok success result} will be returned.
     *
     * @param other           Other result to combine.
     * @param successCombiner Function which maps both success results into a combined one, either
     *                        first or second value may be {@code null} in case of absence.
     * @param errorCombiner   Function which maps both errors result into a combined one, either
     *                        first or second value may be {@code null} in case of absence.
     * @param <MR>            Other success result type.
     * @param <ME>            Other error result type.
     * @param <CR>            Combined success result type.
     * @param <CE>            Combined error result type.
     * @return Result with combined success and error.
     */
    @NotNull
    public final <MR, ME, CR, CE> Result<CR, CE> combine(@NotNull Result<MR, ME> other,
                                                         @NotNull BiFunction<@Nullable R, @Nullable MR, CR> successCombiner,
                                                         @NotNull BiFunction<@Nullable E, @Nullable ME, CE> errorCombiner) {
        return this.combine(other, Combiners.biFunctionAsCombiner(successCombiner), Combiners.biFunctionAsCombiner(errorCombiner));
    }

    /**
     * Combines {@code this} {@link Result result} with {@code other} {@link Result result}. Both
     * {@code this} and {@code other} must be the same type of {@link Result result} ({@link
     * Result.Ok success} or {@link Result.Err error}), if they are not, then {@code
     * singleThisSuccessCombiner}, {@code singleThisErrorCombiner}, {@code
     * singleOtherSuccessCombiner} or {@code singleOtherErrorCombiner} will be used to create a
     * {@link Result} with combined results and a {@link Result.Ok success result} will be returned.
     *
     * @param other                      Other result to combine.
     * @param successCombiner            Function which maps both success results into a combined
     *                                   one.
     * @param errorCombiner              Function which maps both errors result into a combined
     *                                   one.
     * @param singleThisSuccessCombiner  Function which maps a single success result of {@code this}
     *                                   into a combined one.
     * @param singleThisErrorCombiner    Function which maps a single error result of {@code this}
     *                                   into a combined one.
     * @param singleOtherSuccessCombiner Function which maps a single success result of {@code
     *                                   other} into a combined one.
     * @param singleOtherErrorCombiner   Function which maps a single error result of {@code other}
     *                                   into a combined one.
     * @param <MR>                       Other success result type.
     * @param <ME>                       Other error result type.
     * @param <CR>                       Combined success result type.
     * @param <CE>                       Combined error result type.
     * @return Result with combined success and error.
     */
    @NotNull
    public final <MR, ME, CR, CE> Result<CR, CE> combine(@NotNull Result<MR, ME> other,
                                                         @NotNull BiFunction<R, MR, CR> successCombiner,
                                                         @NotNull BiFunction<E, ME, CE> errorCombiner,
                                                         @NotNull Function<R, CR> singleThisSuccessCombiner,
                                                         @NotNull Function<E, CE> singleThisErrorCombiner,
                                                         @NotNull Function<MR, CR> singleOtherSuccessCombiner,
                                                         @NotNull Function<ME, CE> singleOtherErrorCombiner) {
        return this.combine(other,
                (f, s) -> (f != null && s == null)
                        ? singleThisSuccessCombiner.apply(f)
                        : (f == null && s != null)
                        ? singleOtherSuccessCombiner.apply(s)
                        : successCombiner.apply(f, s),

                (f, s) -> (f != null && s == null)
                        ? singleThisErrorCombiner.apply(f)
                        : (f == null && s != null)
                        ? singleOtherErrorCombiner.apply(s)
                        : errorCombiner.apply(f, s)
        );
    }

    /**
     * Combines {@code this} {@link Result success result} with {@code other} {@link Result success
     * result}.
     *
     * If {@code this} {@link Result result} is an {@link Result.Err error result}, {@code this}
     * instance is returned, if {@code other} {@link Result result} is an {@link Result.Err error
     * result}, the {@code other} instance is returned.
     *
     * @param other    Other result to combine.
     * @param combiner Function which maps both success results into a combined one.
     * @param <MR>     Other success result type.
     * @param <CR>     Combined success result type.
     * @return Result with combined success and error. If both {@code this} and {@code other} is
     * {@link Result.Err error result}, then a {@link Result.Err error result} will be returned,
     * otherwise, {@link Result.Ok success result} is always returned.
     */
    @NotNull
    public abstract <MR, CR> Result<CR, E> combineSuccess(@NotNull Result<MR, E> other,
                                                          @NotNull Combiner<R, MR, CR> combiner);

    /**
     * Combines {@code this} {@link Result success result} with {@code other} {@link Result success
     * result}.
     *
     * If {@code this} {@link Result result} is an {@link Result.Err error result}, {@code this}
     * instance is returned, if {@code other} {@link Result result} is an {@link Result.Err error
     * result}, the {@code other} instance is returned.
     *
     * @param other    Other result to combine.
     * @param combiner Function which maps both success results into a combined one.
     * @param <MR>     Other success result type.
     * @param <CR>     Combined success result type.
     * @return Result with combined success and error. If both {@code this} and {@code other} is
     * {@link Result.Err error result}, then a {@link Result.Err error result} will be returned,
     * otherwise, {@link Result.Ok success result} is always returned.
     */
    @NotNull
    public final <MR, CR> Result<CR, E> combineSuccess(@NotNull Result<MR, E> other,
                                                       @NotNull BiFunction<@Nullable R, @Nullable MR, CR> combiner) {
        return this.combineSuccess(other, Combiners.biFunctionAsCombiner(combiner));
    }

    /**
     * Combines {@code this} {@link Result success result} with {@code other} {@link Result success
     * result}.
     *
     * If {@code this} {@link Result result} is an {@link Result.Err error result}, {@code this}
     * instance is returned, if {@code other} {@link Result result} is an {@link Result.Err error
     * result}, the {@code other} instance is returned.
     *
     * @param other    Other result to combine.
     * @param combiner Function which maps both success results into a combined one.
     * @param <MR>     Other success result type.
     * @param <CR>     Combined success result type.
     * @return {@link Result.Ok success result} with combined success and error.
     */
    @NotNull
    public final <MR, CR> Result<CR, E> combineSuccess(@NotNull Result<MR, E> other,
                                                       @NotNull BiFunction<R, MR, CR> combiner,
                                                       @NotNull Function<R, CR> singleThisSuccessCombiner,
                                                       @NotNull Function<MR, CR> singleOtherSuccessCombiner) {
        return this.combineSuccess(other,
                (f, s) -> (f != null && s == null)
                        ? singleThisSuccessCombiner.apply(f)
                        : (f == null && s != null)
                        ? singleOtherSuccessCombiner.apply(s)
                        : combiner.apply(f, s)
        );
    }

    /**
     * Combines {@code this} {@link Result error result} with {@code other} {@link Result error
     * result}.
     *
     * If {@code this} {@link Result result} is an {@link Result.Ok success result}, {@code this}
     * instance is returned, if {@code other} {@link Result result} is an {@link Result.Ok success
     * result}, the {@code other} instance is returned.
     *
     * @param other    Other result to combine.
     * @param combiner Function which maps both error results into a combined one.
     * @param <ME>     Other error result type.
     * @param <CE>     Combined error result type.
     * @return Result with combined success and error. If both {@code this} and {@code other} is
     * {@link Result.Ok success result}, then a {@link Result.Ok success result} will be returned,
     * otherwise, {@link Result.Err error result} is always returned.
     */
    @NotNull
    public abstract <ME, CE> Result<R, CE> combineError(@NotNull Result<R, ME> other,
                                                        @NotNull Combiner<E, ME, CE> combiner);

    /**
     * Combines {@code this} {@link Result error result} with {@code other} {@link Result error
     * result}.
     *
     * If {@code this} {@link Result result} is an {@link Result.Ok success result}, {@code this}
     * instance is returned, if {@code other} {@link Result result} is an {@link Result.Ok success
     * result}, the {@code other} instance is returned.
     *
     * @param other    Other result to combine.
     * @param combiner Function which maps both error results into a combined one.
     * @param <ME>     Other error result type.
     * @param <CE>     Combined error result type.
     * @return Result with combined success and error. If both {@code this} and {@code other} is
     * {@link Result.Ok success result}, then a {@link Result.Ok success result} will be returned,
     * otherwise, {@link Result.Err error result} is always returned.
     */
    @NotNull
    public final <ME, CE> Result<R, CE> combineError(@NotNull Result<R, ME> other,
                                                     @NotNull BiFunction<@Nullable E, @Nullable ME, CE> combiner) {
        return this.combineError(other, Combiners.biFunctionAsCombiner(combiner));
    }

    /**
     * Combines {@code this} {@link Result error result} with {@code other} {@link Result error
     * result}.
     *
     * If {@code this} {@link Result result} is an {@link Result.Ok success result}, {@code this}
     * instance is returned, if {@code other} {@link Result result} is an {@link Result.Ok success
     * result}, the {@code other} instance is returned.
     *
     * @param other    Other result to combine.
     * @param combiner Function which maps both error results into a combined one.
     * @param <ME>     Other error result type.
     * @param <CE>     Combined error result type.
     * @return {@link Result.Err error result} with combined success and error.
     */
    @NotNull
    public final <ME, CE> Result<R, CE> combineError(@NotNull Result<R, ME> other,
                                                     @NotNull BiFunction<E, ME, CE> combiner,
                                                     @NotNull Function<E, CE> singleThisErrorCombiner,
                                                     @NotNull Function<ME, CE> singleOtherErrorCombiner) {
        return this.combineError(other,
                (f, s) -> (f != null && s == null)
                        ? singleThisErrorCombiner.apply(f)
                        : (f == null && s != null)
                        ? singleOtherErrorCombiner.apply(s)
                        : combiner.apply(f, s)
        );
    }

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
        @NotNull
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
        @NotNull
        public abstract <MR> Err<MR, E> as();

    }


}
