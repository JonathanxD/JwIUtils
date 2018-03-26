/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
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
import com.github.jonathanxd.iutils.function.unary.IntUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.LongUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;

/**
 * A class which can hold either {@link int} or {@link long} (in this documentation we call the hold
 * value as present value).
 */
@Generated
public abstract class EitherIntLong extends BaseEither {

    EitherIntLong() {
    }

    /**
     * Creates a {@link EitherIntLong} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherIntLong} which present value is the left value.
     */
    public static EitherIntLong left(int left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherIntLong} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherIntLong} which present value is the right value.
     */
    public static EitherIntLong right(long right) {
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
    public abstract int getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherIntLong#right(long)}.
     *
     * @param value Value to return if this is a {@link EitherIntLong#right(long)}
     * @return Left value or {@code value} if this is a {@link EitherIntLong#right(long)}.
     */
    public abstract int leftOr(int value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherIntLong#right(long)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherIntLong#right(long)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherIntLong#right(long)}.
     */
    public abstract int leftOrGet(IntSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract long getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherIntLong#left(int)}.
     *
     * @param value Value to return if this is a {@link EitherIntLong#left(int)}
     * @return Right value or {@code value} if this is a {@link EitherIntLong#left(int)}.
     */
    public abstract long rightOr(long value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherIntLong#left(int)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherIntLong#left(int)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherIntLong#left(int)}.
     */
    public abstract long rightOrGet(LongSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final int component1() {
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
    public abstract void ifEither(IntConsumer leftConsumer, LongConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(IntConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(LongConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherIntLong}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntLong} instance with mapped values.
     */
    public abstract EitherIntLong map(IntUnaryOperator leftMapper,
                                      LongUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherIntLong} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherIntLong} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherIntLong} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherIntLong mapLeft(IntUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherIntLong} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherIntLong} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntLong} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherIntLong mapRight(LongUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherIntLong}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntLong} returned by mapper function.
     */
    public abstract EitherIntLong flatMap(IntFunction<? extends EitherIntLong> leftMapper,
                                          LongFunction<? extends EitherIntLong> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherIntLong} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherIntLong} returned by mapper function.
     */
    public abstract EitherIntLong flatMapLeft(IntFunction<? extends EitherIntLong> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherIntLong} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntLong} returned by mapper function.
     */
    public abstract EitherIntLong flatMapRight(LongFunction<? extends EitherIntLong> rightMapper);

    static class Left extends EitherIntLong {
        private final int value;

        Left(int value) {
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
        public int getLeft() {
            return this.value;
        }

        @Override
        public int leftOr(int value) {
            return this.getLeft();
        }

        @Override
        public int leftOrGet(IntSupplier supplier) {
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
        public void ifEither(IntConsumer leftConsumer, LongConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(IntConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(LongConsumer consumer) {
        }

        @Override
        public EitherIntLong map(IntUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherIntLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherIntLong mapLeft(IntUnaryOperator leftMapper) {
            return EitherIntLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherIntLong mapRight(LongUnaryOperator rightMapper) {
            return EitherIntLong.left(this.getLeft());
        }

        @Override
        public EitherIntLong flatMap(IntFunction<? extends EitherIntLong> leftMapper,
                                     LongFunction<? extends EitherIntLong> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherIntLong flatMapLeft(IntFunction<? extends EitherIntLong> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherIntLong flatMapRight(LongFunction<? extends EitherIntLong> rightMapper) {
            return EitherIntLong.left(this.getLeft());
        }
    }

    static class Right extends EitherIntLong {
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
        public int getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public int leftOr(int value) {
            return value;
        }

        @Override
        public int leftOrGet(IntSupplier supplier) {
            return supplier.getAsInt();
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
        public void ifEither(IntConsumer leftConsumer, LongConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(IntConsumer consumer) {
        }

        @Override
        public void ifRight(LongConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherIntLong map(IntUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherIntLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherIntLong mapLeft(IntUnaryOperator leftMapper) {
            return EitherIntLong.right(this.getRight());
        }

        @Override
        public EitherIntLong mapRight(LongUnaryOperator rightMapper) {
            return EitherIntLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherIntLong flatMap(IntFunction<? extends EitherIntLong> leftMapper,
                                     LongFunction<? extends EitherIntLong> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherIntLong flatMapLeft(IntFunction<? extends EitherIntLong> leftMapper) {
            return EitherIntLong.right(this.getRight());
        }

        @Override
        public EitherIntLong flatMapRight(LongFunction<? extends EitherIntLong> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
