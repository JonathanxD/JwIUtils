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
package com.github.jonathanxd.iutils.object.specialized.all;

import com.github.jonathanxd.iutils.annotation.Generated;
import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.function.CharFunction;
import com.github.jonathanxd.iutils.function.supplier.CharSupplier;
import com.github.jonathanxd.iutils.function.unary.CharUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

/**
 * A class which can hold either {@link char} or {@link double} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherCharDouble extends BaseEither {

    EitherCharDouble() {
    }

    /**
     * Creates a {@link EitherCharDouble} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherCharDouble} which present value is the left value.
     */
    public static EitherCharDouble left(char left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherCharDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherCharDouble} which present value is the right value.
     */
    public static EitherCharDouble right(double right) {
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
    public abstract char getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherCharDouble#right(double)}.
     *
     * @param value Value to return if this is a {@link EitherCharDouble#right(double)}
     * @return Left value or {@code value} if this is a {@link EitherCharDouble#right(double)}.
     */
    public abstract char leftOr(char value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherCharDouble#right(double)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherCharDouble#right(double)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherCharDouble#right(double)}.
     */
    public abstract char leftOrGet(CharSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract double getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherCharDouble#left(char)}.
     *
     * @param value Value to return if this is a {@link EitherCharDouble#left(char)}
     * @return Right value or {@code value} if this is a {@link EitherCharDouble#left(char)}.
     */
    public abstract double rightOr(double value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherCharDouble#left(char)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherCharDouble#left(char)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherCharDouble#left(char)}.
     */
    public abstract double rightOrGet(DoubleSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final char component1() {
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
    public abstract void ifEither(CharConsumer leftConsumer, DoubleConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(CharConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherCharDouble} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharDouble} instance with mapped values.
     */
    public abstract EitherCharDouble map(CharUnaryOperator leftMapper,
                                         DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherCharDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherCharDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherCharDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherCharDouble mapLeft(CharUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherCharDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherCharDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherCharDouble mapRight(DoubleUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherCharDouble}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharDouble} returned by mapper function.
     */
    public abstract EitherCharDouble flatMap(CharFunction<? extends EitherCharDouble> leftMapper,
                                             DoubleFunction<? extends EitherCharDouble> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherCharDouble} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherCharDouble} returned by mapper function.
     */
    public abstract EitherCharDouble flatMapLeft(CharFunction<? extends EitherCharDouble> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherCharDouble} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharDouble} returned by mapper function.
     */
    public abstract EitherCharDouble flatMapRight(DoubleFunction<? extends EitherCharDouble> rightMapper);

    static class Left extends EitherCharDouble {
        private final char value;

        Left(char value) {
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
        public char getLeft() {
            return this.value;
        }

        @Override
        public char leftOr(char value) {
            return this.getLeft();
        }

        @Override
        public char leftOrGet(CharSupplier supplier) {
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
        public void ifEither(CharConsumer leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(CharConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public EitherCharDouble map(CharUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherCharDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherCharDouble mapLeft(CharUnaryOperator leftMapper) {
            return EitherCharDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherCharDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherCharDouble.left(this.getLeft());
        }

        @Override
        public EitherCharDouble flatMap(CharFunction<? extends EitherCharDouble> leftMapper,
                                        DoubleFunction<? extends EitherCharDouble> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherCharDouble flatMapLeft(CharFunction<? extends EitherCharDouble> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherCharDouble flatMapRight(DoubleFunction<? extends EitherCharDouble> rightMapper) {
            return EitherCharDouble.left(this.getLeft());
        }
    }

    static class Right extends EitherCharDouble {
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
        public char getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public char leftOr(char value) {
            return value;
        }

        @Override
        public char leftOrGet(CharSupplier supplier) {
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
        public void ifEither(CharConsumer leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(CharConsumer consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherCharDouble map(CharUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherCharDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherCharDouble mapLeft(CharUnaryOperator leftMapper) {
            return EitherCharDouble.right(this.getRight());
        }

        @Override
        public EitherCharDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherCharDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherCharDouble flatMap(CharFunction<? extends EitherCharDouble> leftMapper,
                                        DoubleFunction<? extends EitherCharDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherCharDouble flatMapLeft(CharFunction<? extends EitherCharDouble> leftMapper) {
            return EitherCharDouble.right(this.getRight());
        }

        @Override
        public EitherCharDouble flatMapRight(DoubleFunction<? extends EitherCharDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
