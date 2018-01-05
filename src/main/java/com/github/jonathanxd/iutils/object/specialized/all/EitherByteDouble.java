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
import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.supplier.ByteSupplier;
import com.github.jonathanxd.iutils.function.unary.ByteUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

/**
 * A class which can hold either {@link byte} or {@link double} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherByteDouble extends BaseEither {

    EitherByteDouble() {
    }

    /**
     * Creates a {@link EitherByteDouble} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherByteDouble} which present value is the left value.
     */
    public static EitherByteDouble left(byte left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherByteDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherByteDouble} which present value is the right value.
     */
    public static EitherByteDouble right(double right) {
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
    public abstract byte getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherByteDouble#right(double)}.
     *
     * @param value Value to return if this is a {@link EitherByteDouble#right(double)}
     * @return Left value or {@code value} if this is a {@link EitherByteDouble#right(double)}.
     */
    public abstract byte leftOr(byte value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherByteDouble#right(double)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherByteDouble#right(double)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherByteDouble#right(double)}.
     */
    public abstract byte leftOrGet(ByteSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract double getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherByteDouble#left(byte)}.
     *
     * @param value Value to return if this is a {@link EitherByteDouble#left(byte)}
     * @return Right value or {@code value} if this is a {@link EitherByteDouble#left(byte)}.
     */
    public abstract double rightOr(double value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherByteDouble#left(byte)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherByteDouble#left(byte)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherByteDouble#left(byte)}.
     */
    public abstract double rightOrGet(DoubleSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final byte component1() {
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
    public abstract void ifEither(ByteConsumer leftConsumer, DoubleConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(ByteConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherByteDouble} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteDouble} instance with mapped values.
     */
    public abstract EitherByteDouble map(ByteUnaryOperator leftMapper,
                                         DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherByteDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherByteDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteDouble mapLeft(ByteUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherByteDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherByteDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteDouble mapRight(DoubleUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherByteDouble}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteDouble} returned by mapper function.
     */
    public abstract EitherByteDouble flatMap(ByteFunction<? extends EitherByteDouble> leftMapper,
                                             DoubleFunction<? extends EitherByteDouble> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherByteDouble} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteDouble} returned by mapper function.
     */
    public abstract EitherByteDouble flatMapLeft(ByteFunction<? extends EitherByteDouble> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherByteDouble} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteDouble} returned by mapper function.
     */
    public abstract EitherByteDouble flatMapRight(DoubleFunction<? extends EitherByteDouble> rightMapper);

    static class Left extends EitherByteDouble {
        private final byte value;

        Left(byte value) {
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
        public byte getLeft() {
            return this.value;
        }

        @Override
        public byte leftOr(byte value) {
            return this.getLeft();
        }

        @Override
        public byte leftOrGet(ByteSupplier supplier) {
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
        public void ifEither(ByteConsumer leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public EitherByteDouble map(ByteUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherByteDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteDouble mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherByteDouble.left(this.getLeft());
        }

        @Override
        public EitherByteDouble flatMap(ByteFunction<? extends EitherByteDouble> leftMapper,
                                        DoubleFunction<? extends EitherByteDouble> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherByteDouble flatMapLeft(ByteFunction<? extends EitherByteDouble> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherByteDouble flatMapRight(DoubleFunction<? extends EitherByteDouble> rightMapper) {
            return EitherByteDouble.left(this.getLeft());
        }
    }

    static class Right extends EitherByteDouble {
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
        public byte getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public byte leftOr(byte value) {
            return value;
        }

        @Override
        public byte leftOrGet(ByteSupplier supplier) {
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
        public void ifEither(ByteConsumer leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherByteDouble map(ByteUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherByteDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteDouble mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteDouble.right(this.getRight());
        }

        @Override
        public EitherByteDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherByteDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteDouble flatMap(ByteFunction<? extends EitherByteDouble> leftMapper,
                                        DoubleFunction<? extends EitherByteDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherByteDouble flatMapLeft(ByteFunction<? extends EitherByteDouble> leftMapper) {
            return EitherByteDouble.right(this.getRight());
        }

        @Override
        public EitherByteDouble flatMapRight(DoubleFunction<? extends EitherByteDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
