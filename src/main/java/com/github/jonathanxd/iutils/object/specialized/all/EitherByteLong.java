/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.supplier.ByteSupplier;
import com.github.jonathanxd.iutils.function.unary.ByteUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.LongUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;

/**
 * A class which can hold either {@link byte} or {@link long} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherByteLong extends BaseEither {

    EitherByteLong() {
    }

    /**
     * Creates a {@link EitherByteLong} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherByteLong} which present value is the left value.
     */
    public static EitherByteLong left(byte left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherByteLong} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherByteLong} which present value is the right value.
     */
    public static EitherByteLong right(long right) {
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
     * Returns left value or {@code value} if this is a {@link EitherByteLong#right(long)}.
     *
     * @param value Value to return if this is a {@link EitherByteLong#right(long)}
     * @return Left value or {@code value} if this is a {@link EitherByteLong#right(long)}.
     */
    public abstract byte leftOr(byte value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherByteLong#right(long)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherByteLong#right(long)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherByteLong#right(long)}.
     */
    public abstract byte leftOrGet(ByteSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract long getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherByteLong#left(byte)}.
     *
     * @param value Value to return if this is a {@link EitherByteLong#left(byte)}
     * @return Right value or {@code value} if this is a {@link EitherByteLong#left(byte)}.
     */
    public abstract long rightOr(long value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherByteLong#left(byte)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherByteLong#left(byte)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherByteLong#left(byte)}.
     */
    public abstract long rightOrGet(LongSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final byte component1() {
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
    public abstract void ifEither(ByteConsumer leftConsumer, LongConsumer rightConsumer);

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
    public abstract void ifRight(LongConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherByteLong}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteLong} instance with mapped values.
     */
    public abstract EitherByteLong map(ByteUnaryOperator leftMapper,
                                       LongUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherByteLong} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherByteLong} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteLong} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteLong mapLeft(ByteUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherByteLong} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherByteLong} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteLong} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteLong mapRight(LongUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherByteLong}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteLong} returned by mapper function.
     */
    public abstract EitherByteLong flatMap(ByteFunction<? extends EitherByteLong> leftMapper,
                                           LongFunction<? extends EitherByteLong> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherByteLong} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteLong} returned by mapper function.
     */
    public abstract EitherByteLong flatMapLeft(ByteFunction<? extends EitherByteLong> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherByteLong} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteLong} returned by mapper function.
     */
    public abstract EitherByteLong flatMapRight(LongFunction<? extends EitherByteLong> rightMapper);

    static class Left extends EitherByteLong {
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
        public void ifEither(ByteConsumer leftConsumer, LongConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(LongConsumer consumer) {
        }

        @Override
        public EitherByteLong map(ByteUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherByteLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteLong mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteLong mapRight(LongUnaryOperator rightMapper) {
            return EitherByteLong.left(this.getLeft());
        }

        @Override
        public EitherByteLong flatMap(ByteFunction<? extends EitherByteLong> leftMapper,
                                      LongFunction<? extends EitherByteLong> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherByteLong flatMapLeft(ByteFunction<? extends EitherByteLong> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherByteLong flatMapRight(LongFunction<? extends EitherByteLong> rightMapper) {
            return EitherByteLong.left(this.getLeft());
        }
    }

    static class Right extends EitherByteLong {
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
        public void ifEither(ByteConsumer leftConsumer, LongConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
        }

        @Override
        public void ifRight(LongConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherByteLong map(ByteUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherByteLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteLong mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteLong.right(this.getRight());
        }

        @Override
        public EitherByteLong mapRight(LongUnaryOperator rightMapper) {
            return EitherByteLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteLong flatMap(ByteFunction<? extends EitherByteLong> leftMapper,
                                      LongFunction<? extends EitherByteLong> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherByteLong flatMapLeft(ByteFunction<? extends EitherByteLong> leftMapper) {
            return EitherByteLong.right(this.getRight());
        }

        @Override
        public EitherByteLong flatMapRight(LongFunction<? extends EitherByteLong> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
