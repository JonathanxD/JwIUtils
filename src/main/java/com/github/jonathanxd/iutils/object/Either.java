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
package com.github.jonathanxd.iutils.object;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class which can hold either {@link L} or {@link R} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) and right value ({@link R}) may be null even if it is the present value.
 *
 * @param <L> Left value.
 * @param <R> Right value.
 */
public abstract class Either<L, R> extends BaseEither {

    Either() {
    }

    /**
     * Creates a {@link Either} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @param <R>  Right type.
     * @return {@link Either} which present value is the left value.
     */
    public static <L, R> Either<L, R> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link Either} which present value is the right value.
     *
     * @param right Right value.
     * @param <L>   Left type.
     * @param <R>   Right type.
     * @return {@link Either} which present value is the right value.
     */
    public static <L, R> Either<L, R> right(R right) {
        return new Right<>(right);
    }

    /**
     * Returns true if left value is the present value.
     *
     * @return True if left value is the present value.
     */
    public abstract boolean isLeft();

    /**
     * Returns true if right value is the present value.
     *
     * @return True if right value is the present value.
     */
    public abstract boolean isRight();

    /**
     * Gets left value.
     *
     * @return Left value.
     * @throws NoSuchElementException If the left value is not present.
     */
    public abstract L getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link Either#right(Object)}.
     *
     * @param value Value to return if this is a {@link Either#right(Object)}
     * @return Left value or {@code value} if this is a {@link Either#right(Object)}.
     */
    public abstract L leftOr(L value);

    /**
     * Returns left value or {@code null} if this is a {@link Either#right(Object)}.
     *
     * @return Left value or {@code null} if this is a {@link Either#right(Object)}.
     */
    public final L leftOrNull() {
        return this.leftOr(null);
    }

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * Either#right(Object)}.
     *
     * @param supplier Supplier of value to return if this is a {@link Either#right(Object)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * Either#right(Object)}.
     */
    public abstract L leftOrGet(Supplier<L> supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract R getRight();

    /**
     * Returns right value or {@code value} if this is a {@link Either#left(Object)}.
     *
     * @param value Value to return if this is a {@link Either#left(Object)}
     * @return Right value or {@code value} if this is a {@link Either#left(Object)}.
     */
    public abstract R rightOr(R value);

    /**
     * Returns right value or {@code null} if this is a {@link Either#left(Object)}.
     *
     * @return Right value or {@code null} if this is a {@link Either#left(Object)}.
     */
    public final R rightOrNull() {
        return this.rightOr(null);
    }

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * Either#left(Object)}.
     *
     * @param supplier Supplier of value to return if this is a {@link Either#left(Object)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * Either#left(Object)}.
     */
    public abstract R rightOrGet(Supplier<R> supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final L component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final R component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(Consumer<? super L> consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(Consumer<? super R> consumer);

    /**
     * Maps left value if present or right value if present and return a new {@link Either} instance
     * with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @param <MR>        Right type.
     * @return {@link Either} instance with mapped values.
     */
    public abstract <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper,
                                                Function<? super R, ? extends MR> rightMapper);


    /**
     * Maps left value if present and return a new {@link Either} with mapped value.
     *
     * If left value is not present, a new identical {@link Either} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link Either} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link Either} with mapped value.
     *
     * If right value is not present, a new identical {@link Either} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @param <MR>        Right type.
     * @return {@link Either} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link Either} returned
     * by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @param <MR>        Right type.
     * @return {@link Either} returned by mapper function.
     */
    public abstract <ML, MR> Either<ML, MR> flatMap(Function<? super L, ? extends Either<ML, MR>> leftMapper,
                                                    Function<? super R, ? extends Either<ML, MR>> rightMapper);

    /**
     * Flat maps left value if present return {@link Either} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link Either} returned by mapper function.
     */
    public abstract <ML> Either<ML, R> flatMapLeft(Function<? super L, ? extends Either<ML, R>> leftMapper);


    /**
     * Flat maps right value if present and return {@link Either} returned by mapper function.
     *
     * @param rightMapper Right value mapper.
     * @param <MR>        Right type.
     * @return {@link Either} returned by mapper function.
     */
    public abstract <MR> Either<L, MR> flatMapRight(Function<? super R, ? extends Either<L, MR>> rightMapper);

    // Since 4.15.0:

    /**
     * If this is {@link Left}, maps the value of left side to right side, if it is the right side,
     * return same instance.
     *
     * @param toRightFunc Function that maps left value to right.
     * @return Either with left side mapped to right (if is left side, otherwise returns the same
     * instance).
     */
    public abstract Either<L, R> mapLeftToRight(Function<L, R> toRightFunc);

    /**
     * If this is {@link Right}, maps the value of right side to left side, if it is the left side,
     * return same instance.
     *
     * @param toLeftFunc Function that maps right value to left.
     * @return Either with right side mapped to left (if is right side, otherwise returns the same
     * instance).
     */
    public abstract Either<L, R> mapRightToLeft(Function<R, L> toLeftFunc);

    /**
     * Gets left or right value.
     *
     * @param leftMapper  Mapper of left side to {@link B}.
     * @param rightMapper Mapper of right side to {@link B}.
     * @param <B>         Base type of left and right side, or type of object expected to return.
     * @return Left or right side.
     */
    public abstract <B> B getLeftOrRight(Function<L, B> leftMapper, Function<R, B> rightMapper);

    /**
     * Swaps values. If this is {@link Left} returns a {@link Right} of {@link Left left value}, if
     * this is {@link Right}, returns a {@link Left} of {@link Right right value}.
     *
     * @return Swapped either.
     */
    public abstract Either<R, L> swap();

    static class Left<L, R> extends Either<L, R> {
        private final L value;

        Left(L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public L getLeft() {
            return this.value;
        }

        @Override
        public L leftOr(L value) {
            return this.getLeft();
        }

        @Override
        public L leftOrGet(Supplier<L> supplier) {
            return this.getLeft();
        }

        @Override
        public R getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public R rightOr(R value) {
            return value;
        }

        @Override
        public R rightOrGet(Supplier<R> supplier) {
            return supplier.get();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(Consumer<? super R> consumer) {
        }

        @Override
        public <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper,
                                           Function<? super R, ? extends MR> rightMapper) {
            return Either.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML, MR> Either<ML, MR> flatMap(Function<? super L, ? extends Either<ML, MR>> leftMapper,
                                               Function<? super R, ? extends Either<ML, MR>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> Either<ML, R> flatMapLeft(Function<? super L, ? extends Either<ML, R>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <MR> Either<L, MR> flatMapRight(Function<? super R, ? extends Either<L, MR>> rightMapper) {
            return Either.left(this.getLeft());
        }

        @Override
        public <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return Either.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper) {
            return Either.left(this.getLeft());
        }

        @Override
        public Either<L, R> mapLeftToRight(Function<L, R> toRightFunc) {
            return Either.right(toRightFunc.apply(this.getLeft()));
        }

        @Override
        public Either<L, R> mapRightToLeft(Function<R, L> toLeftFunc) {
            return this;
        }

        @Override
        public <B> B getLeftOrRight(Function<L, B> leftMapper, Function<R, B> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public Either<R, L> swap() {
            return Either.right(this.getLeft());
        }
    }

    static class Right<L, R> extends Either<L, R> {
        private final R value;

        Right(R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public L getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public L leftOr(L value) {
            return value;
        }

        @Override
        public L leftOrGet(Supplier<L> supplier) {
            return supplier.get();
        }

        @Override
        public R getRight() {
            return this.value;
        }

        @Override
        public R rightOrGet(Supplier<R> supplier) {
            return this.getRight();
        }

        @Override
        public R rightOr(R value) {
            return this.getRight();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(Consumer<? super R> consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper,
                                           Function<? super R, ? extends MR> rightMapper) {
            return Either.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML, MR> Either<ML, MR> flatMap(Function<? super L, ? extends Either<ML, MR>> leftMapper,
                                               Function<? super R, ? extends Either<ML, MR>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> Either<ML, R> flatMapLeft(Function<? super L, ? extends Either<ML, R>> leftMapper) {
            return Either.right(this.getRight());
        }

        @Override
        public <MR> Either<L, MR> flatMapRight(Function<? super R, ? extends Either<L, MR>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return Either.right(this.getRight());
        }

        @Override
        public <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper) {
            return Either.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public Either<L, R> mapLeftToRight(Function<L, R> toRightFunc) {
            return this;
        }

        @Override
        public Either<L, R> mapRightToLeft(Function<R, L> toLeftFunc) {
            return Either.left(toLeftFunc.apply(this.getRight()));
        }

        @Override
        public <B> B getLeftOrRight(Function<L, B> leftMapper, Function<R, B> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public Either<R, L> swap() {
            return Either.left(this.getRight());
        }
    }
}
