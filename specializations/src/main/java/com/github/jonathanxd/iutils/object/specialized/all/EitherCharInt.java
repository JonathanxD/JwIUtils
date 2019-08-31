/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import com.github.jonathanxd.iutils.function.unary.IntUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;

/**
 * A class which can hold either {@link char} or {@link int} (in this documentation we call the hold
 * value as present value).
 */
@Generated
public abstract class EitherCharInt extends BaseEither {

    EitherCharInt() {
    }

    /**
     * Creates a {@link EitherCharInt} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherCharInt} which present value is the left value.
     */
    public static EitherCharInt left(char left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherCharInt} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherCharInt} which present value is the right value.
     */
    public static EitherCharInt right(int right) {
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
     * Returns left value or {@code value} if this is a {@link EitherCharInt#right(int)}.
     *
     * @param value Value to return if this is a {@link EitherCharInt#right(int)}
     * @return Left value or {@code value} if this is a {@link EitherCharInt#right(int)}.
     */
    public abstract char leftOr(char value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherCharInt#right(int)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherCharInt#right(int)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherCharInt#right(int)}.
     */
    public abstract char leftOrGet(CharSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract int getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherCharInt#left(char)}.
     *
     * @param value Value to return if this is a {@link EitherCharInt#left(char)}
     * @return Right value or {@code value} if this is a {@link EitherCharInt#left(char)}.
     */
    public abstract int rightOr(int value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherCharInt#left(char)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherCharInt#left(char)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherCharInt#left(char)}.
     */
    public abstract int rightOrGet(IntSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final char component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final int component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(CharConsumer leftConsumer, IntConsumer rightConsumer);

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
    public abstract void ifRight(IntConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherCharInt}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharInt} instance with mapped values.
     */
    public abstract EitherCharInt map(CharUnaryOperator leftMapper,
                                      IntUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherCharInt} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherCharInt} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherCharInt} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherCharInt mapLeft(CharUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherCharInt} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherCharInt} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharInt} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherCharInt mapRight(IntUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherCharInt}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharInt} returned by mapper function.
     */
    public abstract EitherCharInt flatMap(CharFunction<? extends EitherCharInt> leftMapper,
                                          IntFunction<? extends EitherCharInt> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherCharInt} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherCharInt} returned by mapper function.
     */
    public abstract EitherCharInt flatMapLeft(CharFunction<? extends EitherCharInt> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherCharInt} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherCharInt} returned by mapper function.
     */
    public abstract EitherCharInt flatMapRight(IntFunction<? extends EitherCharInt> rightMapper);

    static class Left extends EitherCharInt {
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
        public int getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public int rightOr(int value) {
            return value;
        }

        @Override
        public int rightOrGet(IntSupplier supplier) {
            return supplier.getAsInt();
        }

        @Override
        public void ifEither(CharConsumer leftConsumer, IntConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(CharConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(IntConsumer consumer) {
        }

        @Override
        public EitherCharInt map(CharUnaryOperator leftMapper, IntUnaryOperator rightMapper) {
            return EitherCharInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherCharInt mapLeft(CharUnaryOperator leftMapper) {
            return EitherCharInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherCharInt mapRight(IntUnaryOperator rightMapper) {
            return EitherCharInt.left(this.getLeft());
        }

        @Override
        public EitherCharInt flatMap(CharFunction<? extends EitherCharInt> leftMapper,
                                     IntFunction<? extends EitherCharInt> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherCharInt flatMapLeft(CharFunction<? extends EitherCharInt> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherCharInt flatMapRight(IntFunction<? extends EitherCharInt> rightMapper) {
            return EitherCharInt.left(this.getLeft());
        }
    }

    static class Right extends EitherCharInt {
        private final int value;

        Right(int value) {
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
        public int getRight() {
            return this.value;
        }

        @Override
        public int rightOr(int value) {
            return this.getRight();
        }

        @Override
        public int rightOrGet(IntSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(CharConsumer leftConsumer, IntConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(CharConsumer consumer) {
        }

        @Override
        public void ifRight(IntConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherCharInt map(CharUnaryOperator leftMapper, IntUnaryOperator rightMapper) {
            return EitherCharInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherCharInt mapLeft(CharUnaryOperator leftMapper) {
            return EitherCharInt.right(this.getRight());
        }

        @Override
        public EitherCharInt mapRight(IntUnaryOperator rightMapper) {
            return EitherCharInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherCharInt flatMap(CharFunction<? extends EitherCharInt> leftMapper,
                                     IntFunction<? extends EitherCharInt> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherCharInt flatMapLeft(CharFunction<? extends EitherCharInt> leftMapper) {
            return EitherCharInt.right(this.getRight());
        }

        @Override
        public EitherCharInt flatMapRight(IntFunction<? extends EitherCharInt> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
