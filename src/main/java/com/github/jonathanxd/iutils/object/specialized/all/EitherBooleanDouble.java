/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.object.specialized.all;

import com.github.jonathanxd.iutils.annotation.Generated;
import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

/**
 * A class which can hold either {@link boolean} or {@link double} (in this documentation we call
 * the hold value as present value).
 */
@Generated
public abstract class EitherBooleanDouble extends BaseEither {

    EitherBooleanDouble() {
    }

    /**
     * Creates a {@link EitherBooleanDouble} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherBooleanDouble} which present value is the left value.
     */
    public static EitherBooleanDouble left(boolean left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherBooleanDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherBooleanDouble} which present value is the right value.
     */
    public static EitherBooleanDouble right(double right) {
        return new Right(right);
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
    public abstract boolean getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherBooleanDouble#right(double)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanDouble#right(double)}
     * @return Left value or {@code value} if this is a {@link EitherBooleanDouble#right(double)}.
     */
    public abstract boolean leftOr(boolean value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanDouble#right(double)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanDouble#right(double)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanDouble#right(double)}.
     */
    public abstract boolean leftOrGet(BooleanSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract double getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherBooleanDouble#left(boolean)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanDouble#left(boolean)}
     * @return Right value or {@code value} if this is a {@link EitherBooleanDouble#left(boolean)}.
     */
    public abstract double rightOr(double value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanDouble#left(boolean)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanDouble#left(boolean)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanDouble#left(boolean)}.
     */
    public abstract double rightOrGet(DoubleSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final boolean component1() {
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
    public abstract void ifEither(BooleanConsumer leftConsumer, DoubleConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(BooleanConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherBooleanDouble} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanDouble} instance with mapped values.
     */
    public abstract EitherBooleanDouble map(BooleanUnaryOperator leftMapper,
                                            DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherBooleanDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherBooleanDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanDouble mapLeft(BooleanUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherBooleanDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherBooleanDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanDouble mapRight(DoubleUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link
     * EitherBooleanDouble} returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanDouble} returned by mapper function.
     */
    public abstract EitherBooleanDouble flatMap(BooleanFunction<? extends EitherBooleanDouble> leftMapper,
                                                DoubleFunction<? extends EitherBooleanDouble> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherBooleanDouble} returned by mapper
     * function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanDouble} returned by mapper function.
     */
    public abstract EitherBooleanDouble flatMapLeft(BooleanFunction<? extends EitherBooleanDouble> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherBooleanDouble} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanDouble} returned by mapper function.
     */
    public abstract EitherBooleanDouble flatMapRight(DoubleFunction<? extends EitherBooleanDouble> rightMapper);

    static class Left extends EitherBooleanDouble {
        private final boolean value;

        Left(boolean value) {
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
        public boolean getLeft() {
            return this.value;
        }

        @Override
        public boolean leftOr(boolean value) {
            return this.getLeft();
        }

        @Override
        public boolean leftOrGet(BooleanSupplier supplier) {
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
        public void ifEither(BooleanConsumer leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public EitherBooleanDouble map(BooleanUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherBooleanDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanDouble mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherBooleanDouble.left(this.getLeft());
        }

        @Override
        public EitherBooleanDouble flatMap(BooleanFunction<? extends EitherBooleanDouble> leftMapper,
                                           DoubleFunction<? extends EitherBooleanDouble> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanDouble flatMapLeft(BooleanFunction<? extends EitherBooleanDouble> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanDouble flatMapRight(DoubleFunction<? extends EitherBooleanDouble> rightMapper) {
            return EitherBooleanDouble.left(this.getLeft());
        }
    }

    static class Right extends EitherBooleanDouble {
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
        public boolean getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean leftOr(boolean value) {
            return value;
        }

        @Override
        public boolean leftOrGet(BooleanSupplier supplier) {
            return supplier.getAsBoolean();
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
        public void ifEither(BooleanConsumer leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherBooleanDouble map(BooleanUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherBooleanDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanDouble mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanDouble.right(this.getRight());
        }

        @Override
        public EitherBooleanDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherBooleanDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanDouble flatMap(BooleanFunction<? extends EitherBooleanDouble> leftMapper,
                                           DoubleFunction<? extends EitherBooleanDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherBooleanDouble flatMapLeft(BooleanFunction<? extends EitherBooleanDouble> leftMapper) {
            return EitherBooleanDouble.right(this.getRight());
        }

        @Override
        public EitherBooleanDouble flatMapRight(DoubleFunction<? extends EitherBooleanDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
