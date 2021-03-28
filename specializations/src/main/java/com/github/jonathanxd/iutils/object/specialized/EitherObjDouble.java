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
package com.github.jonathanxd.iutils.object.specialized;

import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class which can hold either {@link L} or {@link double} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
public abstract class EitherObjDouble<L> extends BaseEither {

    EitherObjDouble() {
    }

    /**
     * Creates a {@link EitherObjDouble} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjDouble} which present value is the left value.
     */
    public static <L> EitherObjDouble<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjDouble} which present value is the right value.
     */
    public static <L> EitherObjDouble<L> right(double right) {
        return new Right<>(right);
    }

    /**
     * Returns true if left value is the present value.
     *
     * @return True if left value is the present value.
     */
    @Override
    public abstract boolean isLeft();

    /**
     * Returns true if right value is the present value.
     *
     * @return True if right value is the present value.
     */
    @Override
    public abstract boolean isRight();

    /**
     * Gets left value.
     *
     * @return Left value.
     * @throws NoSuchElementException If the left value is not present.
     */
    public abstract L getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherObjDouble#right(double)}.
     *
     * @param value Value to return if this is a {@link EitherObjDouble#right(double)}
     * @return Left value or {@code value} if this is a {@link EitherObjDouble#right(double)}.
     */
    public abstract L leftOr(L value);

    /**
     * Returns left value or {@code null} if this is a {@link EitherObjDouble#right(double)}.
     *
     * @return Left value or {@code null} if this is a {@link EitherObjDouble#right(double)}.
     */
    public final L leftOrNull() {
        return this.leftOr(null);
    }

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjDouble#right(double)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjDouble#right(double)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjDouble#right(double)}.
     */
    public abstract L leftOrGet(Supplier<L> supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract double getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherObjDouble#left(Object)}.
     *
     * @param value Value to return if this is a {@link EitherObjDouble#left(Object)}
     * @return Right value or {@code value} if this is a {@link EitherObjDouble#left(Object)}.
     */
    public abstract double rightOr(double value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjDouble#left(Object)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjDouble#left(Object)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjDouble#left(Object)}.
     */
    public abstract double rightOrGet(DoubleSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final L component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final double component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, DoubleConsumer rightConsumer);

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
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherObjDouble} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjDouble} instance with mapped values.
     */
    public abstract <ML> EitherObjDouble<ML> map(Function<? super L, ? extends ML> leftMapper,
                                                 DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjDouble<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjDouble<L> mapRight(DoubleUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherObjDouble}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjDouble} returned by mapper function.
     */
    public abstract <ML> EitherObjDouble<ML> flatMap(Function<? super L, ? extends EitherObjDouble<ML>> leftMapper,
                                                     DoubleFunction<? extends EitherObjDouble<ML>> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherObjDouble} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjDouble} returned by mapper function.
     */
    public abstract <ML> EitherObjDouble<ML> flatMapLeft(Function<? super L, ? extends EitherObjDouble<ML>> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherObjDouble} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjDouble} returned by mapper function.
     */
    public abstract EitherObjDouble<L> flatMapRight(DoubleFunction<? extends EitherObjDouble<L>> rightMapper);

    static class Left<L> extends EitherObjDouble<L> {
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
        public double getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public double rightOr(double value) {
            return value;
        }

        @Override
        public double rightOrGet(DoubleSupplier supplier) {
            return supplier.getAsDouble();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public <ML> EitherObjDouble<ML> map(Function<? super L, ? extends ML> leftMapper,
                                            DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjDouble<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjDouble<L> mapRight(DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.left(this.getLeft());
        }

        @Override
        public <ML> EitherObjDouble<ML> flatMap(Function<? super L, ? extends EitherObjDouble<ML>> leftMapper,
                                                DoubleFunction<? extends EitherObjDouble<ML>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> EitherObjDouble<ML> flatMapLeft(Function<? super L, ? extends EitherObjDouble<ML>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherObjDouble<L> flatMapRight(DoubleFunction<? extends EitherObjDouble<L>> rightMapper) {
            return EitherObjDouble.left(this.getLeft());
        }

    }

    static class Right<L> extends EitherObjDouble<L> {
        private final double value;

        Right(double value) {
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
        public double getRight() {
            return this.value;
        }

        @Override
        public double rightOr(double value) {
            return this.getRight();
        }

        @Override
        public double rightOrGet(DoubleSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjDouble<ML> map(Function<? super L, ? extends ML> leftMapper,
                                            DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjDouble<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjDouble.right(this.getRight());
        }

        @Override
        public EitherObjDouble<L> mapRight(DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.right(rightMapper.apply(this.getRight()));
        }


        @Override
        public <ML> EitherObjDouble<ML> flatMap(Function<? super L, ? extends EitherObjDouble<ML>> leftMapper,
                                                DoubleFunction<? extends EitherObjDouble<ML>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> EitherObjDouble<ML> flatMapLeft(Function<? super L, ? extends EitherObjDouble<ML>> leftMapper) {
            return EitherObjDouble.right(this.getRight());
        }

        @Override
        public EitherObjDouble<L> flatMapRight(DoubleFunction<? extends EitherObjDouble<L>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

    }
}
