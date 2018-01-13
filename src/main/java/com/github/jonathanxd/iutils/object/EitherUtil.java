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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.function.Predicates;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EitherUtil {

    /**
     * A function that returns supplied {@code left} value if {@code right} value does not match
     * {@code predicate}.
     *
     * @param predicate Predicate to test {@code right} value.
     * @param ifNot     Supplier of value to return if {@code right} value does not match
     *                  predicate.
     * @param <L>       Left value type.
     * @param <R>       Right value type.
     * @return Either of supplied {@code left} value if {@code right} value does not match {@code
     * predicate}, {@code right} value otherwise.
     */
    public static <L, R> Function<R, Either<L, R>> ifRightMatches(Predicate<R> predicate, Supplier<L> ifNot) {
        return v -> predicate.test(v) ? Either.right(v) : Either.left(ifNot.get());
    }

    /**
     * A function that returns supplied {@code right} value if {@code left} value does not match
     * {@code predicate}.
     *
     * @param predicate Predicate to test {@code left} value.
     * @param ifNot     Supplier of value to return if {@code left} value does not match predicate.
     * @param <L>       Left value type.
     * @param <R>       Right value type.
     * @return Either of supplied {@code right} value if {@code left} value does not match {@code
     * predicate}, {@code left} value otherwise.
     */
    public static <L, R> Function<L, Either<L, R>> ifLeftMatches(Predicate<L> predicate, Supplier<R> ifNot) {
        return v -> predicate.test(v) ? Either.left(v) : Either.right(ifNot.get());
    }

    /**
     * A function that returns supplied left value if right value is null, or the right value
     * otherwise.
     *
     * @param ifNull Left value to return if right value is null.
     * @param <L>    Left value type.
     * @param <R>    Right value type.
     * @return Either of supplied left value if right value is null, right value otherwise.
     */
    public static <L, R> Function<R, Either<L, R>> nonNullRight(Supplier<L> ifNull) {
        return EitherUtil.ifRightMatches(Predicates.isNull(), ifNull);
    }

    /**
     * A function that returns supplied right value if left value is null, or left value otherwise.
     *
     * @param ifNull Right value to return if left value is null.
     * @param <L>    Left value type.
     * @param <R>    Right value type.
     * @return Either of supplied right value if left value is null, left value otherwise.
     */
    public static <L, R> Function<L, Either<L, R>> nonNull(Supplier<R> ifNull) {
        return EitherUtil.ifLeftMatches(Predicates.isNull(), ifNull);
    }

    /**
     * Consumes left or right side of {@code either} with {@code consumer} if both sides have same
     * base type or same type.
     *
     * @param either   Either.
     * @param consumer Consumer of value.
     * @param <B>      Base type which left and right extends from, or the type of both left and
     *                 right.
     * @param <L>      Left type.
     * @param <R>      Right type.
     */
    public static <B, L extends B, R extends B> void consumeLeftOrRight(Either<L, R> either, Consumer<B> consumer) {
        if (either.isLeft()) {
            consumer.accept(either.getLeft());
        } else {
            consumer.accept(either.getRight());
        }
    }

    /**
     * Consumes mapped left or right side of {@code either} with {@code consumer}.
     *
     * @param either   Either.
     * @param consumer Consumer of value.
     * @param leftToB  Left to {@link B B type} mapper.
     * @param rightToB Right to {@link B B type} mapper.
     * @param <B>      Base type to map left or right side.
     * @param <L>      Left type.
     * @param <R>      Right type.
     */
    public static <B, L, R> void consumeLeftOrRightMap(Either<L, R> either,
                                                       Function<L, B> leftToB,
                                                       Function<R, B> rightToB,
                                                       Consumer<B> consumer) {
        if (either.isLeft()) {
            consumer.accept(leftToB.apply(either.getLeft()));
        } else {
            consumer.accept(rightToB.apply(either.getRight()));
        }
    }

    /**
     * Returns left or right side of {@code either} if both sides have same base type or same type.
     *
     * @param either Either.
     * @param <B>    Base type which left and right extends from, or the type of both left and
     *               right.
     * @param <L>    Left type.
     * @param <R>    Right type.
     * @return Left or right side of {@code either} if both sides have same base type or same type.
     */
    public static <B, L extends B, R extends B> B getLeftOrRight(Either<L, R> either) {
        if (either.isLeft()) {
            return either.getLeft();
        } else {
            return either.getRight();
        }
    }

    /**
     * Returns left or right side of {@code either} mapped to same type.
     *
     * @param either   Either.
     * @param leftToB  Left to {@link B B type} mapper.
     * @param rightToB Right to {@link B B type} mapper.
     * @param <B>      Base type to map left or right side.
     * @param <L>      Left type.
     * @param <R>      Right type.
     * @return Left or right side of {@code either} if both sides have same base type or same type.
     */
    public static <B, L, R> B getLeftOrRightMap(Either<L, R> either, Function<L, B> leftToB, Function<R, B> rightToB) {
        if (either.isLeft()) {
            return leftToB.apply(either.getLeft());
        } else {
            return rightToB.apply(either.getRight());
        }
    }

    /**
     * Returns supplier of left or right side of {@code either} mapped to same type.
     *
     * @param either   Either.
     * @param leftToB  Left to {@link B B type} mapper.
     * @param rightToB Right to {@link B B type} mapper.
     * @param <B>      Base type to map left or right side.
     * @param <L>      Left type.
     * @param <R>      Right type.
     * @return Supplier of left or right side of {@code either} if both sides have same base type or
     * same type.
     */
    public static <B, L, R> Supplier<B> getLeftOrRightMapSupplied(Either<L, R> either,
                                                                  Function<L, B> leftToB,
                                                                  Function<R, B> rightToB) {
        if (either.isLeft()) {
            return () -> leftToB.apply(either.getLeft());
        } else {
            return () -> rightToB.apply(either.getRight());
        }
    }

}
