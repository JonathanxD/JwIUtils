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
import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.supplier.ByteSupplier;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.ByteUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;

/**
 * A class which can hold either {@link boolean} or {@link byte} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherBooleanByte extends BaseEither {

    EitherBooleanByte() {
    }

    /**
     * Creates a {@link EitherBooleanByte} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherBooleanByte} which present value is the left value.
     */
    public static EitherBooleanByte left(boolean left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherBooleanByte} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherBooleanByte} which present value is the right value.
     */
    public static EitherBooleanByte right(byte right) {
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
     * Returns left value or {@code value} if this is a {@link EitherBooleanByte#right(byte)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanByte#right(byte)}
     * @return Left value or {@code value} if this is a {@link EitherBooleanByte#right(byte)}.
     */
    public abstract boolean leftOr(boolean value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanByte#right(byte)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanByte#right(byte)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanByte#right(byte)}.
     */
    public abstract boolean leftOrGet(BooleanSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract byte getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherBooleanByte#left(boolean)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanByte#left(boolean)}
     * @return Right value or {@code value} if this is a {@link EitherBooleanByte#left(boolean)}.
     */
    public abstract byte rightOr(byte value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanByte#left(boolean)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanByte#left(boolean)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanByte#left(boolean)}.
     */
    public abstract byte rightOrGet(ByteSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final boolean component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final byte component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(BooleanConsumer leftConsumer, ByteConsumer rightConsumer);

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
    public abstract void ifRight(ByteConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherBooleanByte} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanByte} instance with mapped values.
     */
    public abstract EitherBooleanByte map(BooleanUnaryOperator leftMapper,
                                          ByteUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherBooleanByte} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherBooleanByte} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanByte} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanByte mapLeft(BooleanUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherBooleanByte} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherBooleanByte} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanByte} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanByte mapRight(ByteUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link
     * EitherBooleanByte} returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanByte} returned by mapper function.
     */
    public abstract EitherBooleanByte flatMap(BooleanFunction<? extends EitherBooleanByte> leftMapper,
                                              ByteFunction<? extends EitherBooleanByte> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherBooleanByte} returned by mapper
     * function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanByte} returned by mapper function.
     */
    public abstract EitherBooleanByte flatMapLeft(BooleanFunction<? extends EitherBooleanByte> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherBooleanByte} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanByte} returned by mapper function.
     */
    public abstract EitherBooleanByte flatMapRight(ByteFunction<? extends EitherBooleanByte> rightMapper);

    static class Left extends EitherBooleanByte {
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
        public byte getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public byte rightOr(byte value) {
            return value;
        }

        @Override
        public byte rightOrGet(ByteSupplier supplier) {
            return supplier.get();
        }

        @Override
        public void ifEither(BooleanConsumer leftConsumer, ByteConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(ByteConsumer consumer) {
        }

        @Override
        public EitherBooleanByte map(BooleanUnaryOperator leftMapper, ByteUnaryOperator rightMapper) {
            return EitherBooleanByte.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanByte mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanByte.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanByte mapRight(ByteUnaryOperator rightMapper) {
            return EitherBooleanByte.left(this.getLeft());
        }

        @Override
        public EitherBooleanByte flatMap(BooleanFunction<? extends EitherBooleanByte> leftMapper,
                                         ByteFunction<? extends EitherBooleanByte> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanByte flatMapLeft(BooleanFunction<? extends EitherBooleanByte> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanByte flatMapRight(ByteFunction<? extends EitherBooleanByte> rightMapper) {
            return EitherBooleanByte.left(this.getLeft());
        }
    }

    static class Right extends EitherBooleanByte {
        private final byte value;

        Right(byte value) {
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
        public byte getRight() {
            return this.value;
        }

        @Override
        public byte rightOr(byte value) {
            return this.getRight();
        }

        @Override
        public byte rightOrGet(ByteSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(BooleanConsumer leftConsumer, ByteConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
        }

        @Override
        public void ifRight(ByteConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherBooleanByte map(BooleanUnaryOperator leftMapper, ByteUnaryOperator rightMapper) {
            return EitherBooleanByte.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanByte mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanByte.right(this.getRight());
        }

        @Override
        public EitherBooleanByte mapRight(ByteUnaryOperator rightMapper) {
            return EitherBooleanByte.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanByte flatMap(BooleanFunction<? extends EitherBooleanByte> leftMapper,
                                         ByteFunction<? extends EitherBooleanByte> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherBooleanByte flatMapLeft(BooleanFunction<? extends EitherBooleanByte> leftMapper) {
            return EitherBooleanByte.right(this.getRight());
        }

        @Override
        public EitherBooleanByte flatMapRight(ByteFunction<? extends EitherBooleanByte> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
