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
import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.supplier.ByteSupplier;
import com.github.jonathanxd.iutils.function.supplier.ShortSupplier;
import com.github.jonathanxd.iutils.function.unary.ByteUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.ShortUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;

/**
 * A class which can hold either {@link byte} or {@link short} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherByteShort extends BaseEither {

    EitherByteShort() {
    }

    /**
     * Creates a {@link EitherByteShort} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherByteShort} which present value is the left value.
     */
    public static EitherByteShort left(byte left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherByteShort} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherByteShort} which present value is the right value.
     */
    public static EitherByteShort right(short right) {
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
     * Returns left value or {@code value} if this is a {@link EitherByteShort#right(short)}.
     *
     * @param value Value to return if this is a {@link EitherByteShort#right(short)}
     * @return Left value or {@code value} if this is a {@link EitherByteShort#right(short)}.
     */
    public abstract byte leftOr(byte value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherByteShort#right(short)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherByteShort#right(short)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherByteShort#right(short)}.
     */
    public abstract byte leftOrGet(ByteSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract short getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherByteShort#left(byte)}.
     *
     * @param value Value to return if this is a {@link EitherByteShort#left(byte)}
     * @return Right value or {@code value} if this is a {@link EitherByteShort#left(byte)}.
     */
    public abstract short rightOr(short value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherByteShort#left(byte)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherByteShort#left(byte)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherByteShort#left(byte)}.
     */
    public abstract short rightOrGet(ShortSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final byte component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final short component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(ByteConsumer leftConsumer, ShortConsumer rightConsumer);

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
    public abstract void ifRight(ShortConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherByteShort} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteShort} instance with mapped values.
     */
    public abstract EitherByteShort map(ByteUnaryOperator leftMapper,
                                        ShortUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherByteShort} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherByteShort} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteShort} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteShort mapLeft(ByteUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherByteShort} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherByteShort} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteShort} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteShort mapRight(ShortUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherByteShort}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteShort} returned by mapper function.
     */
    public abstract EitherByteShort flatMap(ByteFunction<? extends EitherByteShort> leftMapper,
                                            ShortFunction<? extends EitherByteShort> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherByteShort} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteShort} returned by mapper function.
     */
    public abstract EitherByteShort flatMapLeft(ByteFunction<? extends EitherByteShort> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherByteShort} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteShort} returned by mapper function.
     */
    public abstract EitherByteShort flatMapRight(ShortFunction<? extends EitherByteShort> rightMapper);

    static class Left extends EitherByteShort {
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
        public short getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public short rightOr(short value) {
            return value;
        }

        @Override
        public short rightOrGet(ShortSupplier supplier) {
            return supplier.get();
        }

        @Override
        public void ifEither(ByteConsumer leftConsumer, ShortConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(ShortConsumer consumer) {
        }

        @Override
        public EitherByteShort map(ByteUnaryOperator leftMapper, ShortUnaryOperator rightMapper) {
            return EitherByteShort.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteShort mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteShort.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteShort mapRight(ShortUnaryOperator rightMapper) {
            return EitherByteShort.left(this.getLeft());
        }

        @Override
        public EitherByteShort flatMap(ByteFunction<? extends EitherByteShort> leftMapper,
                                       ShortFunction<? extends EitherByteShort> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherByteShort flatMapLeft(ByteFunction<? extends EitherByteShort> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherByteShort flatMapRight(ShortFunction<? extends EitherByteShort> rightMapper) {
            return EitherByteShort.left(this.getLeft());
        }
    }

    static class Right extends EitherByteShort {
        private final short value;

        Right(short value) {
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
        public short getRight() {
            return this.value;
        }

        @Override
        public short rightOr(short value) {
            return this.getRight();
        }

        @Override
        public short rightOrGet(ShortSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(ByteConsumer leftConsumer, ShortConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
        }

        @Override
        public void ifRight(ShortConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherByteShort map(ByteUnaryOperator leftMapper, ShortUnaryOperator rightMapper) {
            return EitherByteShort.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteShort mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteShort.right(this.getRight());
        }

        @Override
        public EitherByteShort mapRight(ShortUnaryOperator rightMapper) {
            return EitherByteShort.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteShort flatMap(ByteFunction<? extends EitherByteShort> leftMapper,
                                       ShortFunction<? extends EitherByteShort> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherByteShort flatMapLeft(ByteFunction<? extends EitherByteShort> leftMapper) {
            return EitherByteShort.right(this.getRight());
        }

        @Override
        public EitherByteShort flatMapRight(ShortFunction<? extends EitherByteShort> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
