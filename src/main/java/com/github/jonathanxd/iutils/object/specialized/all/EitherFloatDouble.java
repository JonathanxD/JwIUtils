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
package com.github.jonathanxd.iutils.object.specialized.all;

import com.github.jonathanxd.iutils.annotation.Generated;
import com.github.jonathanxd.iutils.function.consumer.FloatConsumer;
import com.github.jonathanxd.iutils.function.function.FloatFunction;
import com.github.jonathanxd.iutils.function.supplier.FloatSupplier;
import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.FloatUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

/**
 * A class which can hold either {@link float} or {@link double} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherFloatDouble extends BaseEither {

    EitherFloatDouble() {
    }

    /**
     * Creates a {@link EitherFloatDouble} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherFloatDouble} which present value is the left value.
     */
    public static EitherFloatDouble left(float left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherFloatDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherFloatDouble} which present value is the right value.
     */
    public static EitherFloatDouble right(double right) {
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
    public abstract float getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherFloatDouble#right(double)}.
     *
     * @param value Value to return if this is a {@link EitherFloatDouble#right(double)}
     * @return Left value or {@code value} if this is a {@link EitherFloatDouble#right(double)}.
     */
    public abstract float leftOr(float value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatDouble#right(double)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherFloatDouble#right(double)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatDouble#right(double)}.
     */
    public abstract float leftOrGet(FloatSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract double getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherFloatDouble#left(float)}.
     *
     * @param value Value to return if this is a {@link EitherFloatDouble#left(float)}
     * @return Right value or {@code value} if this is a {@link EitherFloatDouble#left(float)}.
     */
    public abstract double rightOr(double value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatDouble#left(float)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherFloatDouble#left(float)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatDouble#left(float)}.
     */
    public abstract double rightOrGet(DoubleSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final float component1() {
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
    public abstract void ifEither(FloatConsumer leftConsumer, DoubleConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(FloatConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherFloatDouble} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatDouble} instance with mapped values.
     */
    public abstract EitherFloatDouble map(FloatUnaryOperator leftMapper,
                                          DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherFloatDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherFloatDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherFloatDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherFloatDouble mapLeft(FloatUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherFloatDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherFloatDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherFloatDouble mapRight(DoubleUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link
     * EitherFloatDouble} returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatDouble} returned by mapper function.
     */
    public abstract EitherFloatDouble flatMap(FloatFunction<? extends EitherFloatDouble> leftMapper,
                                              DoubleFunction<? extends EitherFloatDouble> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherFloatDouble} returned by mapper
     * function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherFloatDouble} returned by mapper function.
     */
    public abstract EitherFloatDouble flatMapLeft(FloatFunction<? extends EitherFloatDouble> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherFloatDouble} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatDouble} returned by mapper function.
     */
    public abstract EitherFloatDouble flatMapRight(DoubleFunction<? extends EitherFloatDouble> rightMapper);

    static class Left extends EitherFloatDouble {
        private final float value;

        Left(float value) {
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
        public float getLeft() {
            return this.value;
        }

        @Override
        public float leftOr(float value) {
            return this.getLeft();
        }

        @Override
        public float leftOrGet(FloatSupplier supplier) {
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
        public void ifEither(FloatConsumer leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(FloatConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public EitherFloatDouble map(FloatUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherFloatDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherFloatDouble mapLeft(FloatUnaryOperator leftMapper) {
            return EitherFloatDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherFloatDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherFloatDouble.left(this.getLeft());
        }

        @Override
        public EitherFloatDouble flatMap(FloatFunction<? extends EitherFloatDouble> leftMapper,
                                         DoubleFunction<? extends EitherFloatDouble> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherFloatDouble flatMapLeft(FloatFunction<? extends EitherFloatDouble> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherFloatDouble flatMapRight(DoubleFunction<? extends EitherFloatDouble> rightMapper) {
            return EitherFloatDouble.left(this.getLeft());
        }
    }

    static class Right extends EitherFloatDouble {
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
        public float getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public float leftOr(float value) {
            return value;
        }

        @Override
        public float leftOrGet(FloatSupplier supplier) {
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
        public void ifEither(FloatConsumer leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(FloatConsumer consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherFloatDouble map(FloatUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherFloatDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherFloatDouble mapLeft(FloatUnaryOperator leftMapper) {
            return EitherFloatDouble.right(this.getRight());
        }

        @Override
        public EitherFloatDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherFloatDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherFloatDouble flatMap(FloatFunction<? extends EitherFloatDouble> leftMapper,
                                         DoubleFunction<? extends EitherFloatDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherFloatDouble flatMapLeft(FloatFunction<? extends EitherFloatDouble> leftMapper) {
            return EitherFloatDouble.right(this.getRight());
        }

        @Override
        public EitherFloatDouble flatMapRight(DoubleFunction<? extends EitherFloatDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
