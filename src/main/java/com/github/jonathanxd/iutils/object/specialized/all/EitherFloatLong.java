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
import com.github.jonathanxd.iutils.function.consumer.FloatConsumer;
import com.github.jonathanxd.iutils.function.function.FloatFunction;
import com.github.jonathanxd.iutils.function.supplier.FloatSupplier;
import com.github.jonathanxd.iutils.function.unary.FloatUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.LongUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;

/**
 * A class which can hold either {@link float} or {@link long} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherFloatLong extends BaseEither {

    EitherFloatLong() {
    }

    /**
     * Creates a {@link EitherFloatLong} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherFloatLong} which present value is the left value.
     */
    public static EitherFloatLong left(float left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherFloatLong} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherFloatLong} which present value is the right value.
     */
    public static EitherFloatLong right(long right) {
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
     * Returns left value or {@code value} if this is a {@link EitherFloatLong#right(long)}.
     *
     * @param value Value to return if this is a {@link EitherFloatLong#right(long)}
     * @return Left value or {@code value} if this is a {@link EitherFloatLong#right(long)}.
     */
    public abstract float leftOr(float value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatLong#right(long)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherFloatLong#right(long)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatLong#right(long)}.
     */
    public abstract float leftOrGet(FloatSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract long getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherFloatLong#left(float)}.
     *
     * @param value Value to return if this is a {@link EitherFloatLong#left(float)}
     * @return Right value or {@code value} if this is a {@link EitherFloatLong#left(float)}.
     */
    public abstract long rightOr(long value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatLong#left(float)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherFloatLong#left(float)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherFloatLong#left(float)}.
     */
    public abstract long rightOrGet(LongSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final float component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final long component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(FloatConsumer leftConsumer, LongConsumer rightConsumer);

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
    public abstract void ifRight(LongConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherFloatLong} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatLong} instance with mapped values.
     */
    public abstract EitherFloatLong map(FloatUnaryOperator leftMapper,
                                        LongUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherFloatLong} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherFloatLong} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherFloatLong} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherFloatLong mapLeft(FloatUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherFloatLong} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherFloatLong} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatLong} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherFloatLong mapRight(LongUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherFloatLong}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatLong} returned by mapper function.
     */
    public abstract EitherFloatLong flatMap(FloatFunction<? extends EitherFloatLong> leftMapper,
                                            LongFunction<? extends EitherFloatLong> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherFloatLong} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherFloatLong} returned by mapper function.
     */
    public abstract EitherFloatLong flatMapLeft(FloatFunction<? extends EitherFloatLong> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherFloatLong} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherFloatLong} returned by mapper function.
     */
    public abstract EitherFloatLong flatMapRight(LongFunction<? extends EitherFloatLong> rightMapper);

    static class Left extends EitherFloatLong {
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
        public long getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public long rightOr(long value) {
            return value;
        }

        @Override
        public long rightOrGet(LongSupplier supplier) {
            return supplier.getAsLong();
        }

        @Override
        public void ifEither(FloatConsumer leftConsumer, LongConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(FloatConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(LongConsumer consumer) {
        }

        @Override
        public EitherFloatLong map(FloatUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherFloatLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherFloatLong mapLeft(FloatUnaryOperator leftMapper) {
            return EitherFloatLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherFloatLong mapRight(LongUnaryOperator rightMapper) {
            return EitherFloatLong.left(this.getLeft());
        }

        @Override
        public EitherFloatLong flatMap(FloatFunction<? extends EitherFloatLong> leftMapper,
                                       LongFunction<? extends EitherFloatLong> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherFloatLong flatMapLeft(FloatFunction<? extends EitherFloatLong> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherFloatLong flatMapRight(LongFunction<? extends EitherFloatLong> rightMapper) {
            return EitherFloatLong.left(this.getLeft());
        }
    }

    static class Right extends EitherFloatLong {
        private final long value;

        Right(long value) {
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
        public long getRight() {
            return this.value;
        }

        @Override
        public long rightOr(long value) {
            return this.getRight();
        }

        @Override
        public long rightOrGet(LongSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(FloatConsumer leftConsumer, LongConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(FloatConsumer consumer) {
        }

        @Override
        public void ifRight(LongConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherFloatLong map(FloatUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherFloatLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherFloatLong mapLeft(FloatUnaryOperator leftMapper) {
            return EitherFloatLong.right(this.getRight());
        }

        @Override
        public EitherFloatLong mapRight(LongUnaryOperator rightMapper) {
            return EitherFloatLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherFloatLong flatMap(FloatFunction<? extends EitherFloatLong> leftMapper,
                                       LongFunction<? extends EitherFloatLong> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherFloatLong flatMapLeft(FloatFunction<? extends EitherFloatLong> leftMapper) {
            return EitherFloatLong.right(this.getRight());
        }

        @Override
        public EitherFloatLong flatMapRight(LongFunction<? extends EitherFloatLong> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
