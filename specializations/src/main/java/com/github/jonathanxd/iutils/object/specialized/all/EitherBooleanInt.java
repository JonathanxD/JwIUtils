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
import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.IntUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;

/**
 * A class which can hold either {@link boolean} or {@link int} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherBooleanInt extends BaseEither {

    EitherBooleanInt() {
    }

    /**
     * Creates a {@link EitherBooleanInt} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherBooleanInt} which present value is the left value.
     */
    public static EitherBooleanInt left(boolean left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherBooleanInt} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherBooleanInt} which present value is the right value.
     */
    public static EitherBooleanInt right(int right) {
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
     * Returns left value or {@code value} if this is a {@link EitherBooleanInt#right(int)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanInt#right(int)}
     * @return Left value or {@code value} if this is a {@link EitherBooleanInt#right(int)}.
     */
    public abstract boolean leftOr(boolean value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanInt#right(int)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanInt#right(int)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanInt#right(int)}.
     */
    public abstract boolean leftOrGet(BooleanSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract int getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherBooleanInt#left(boolean)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanInt#left(boolean)}
     * @return Right value or {@code value} if this is a {@link EitherBooleanInt#left(boolean)}.
     */
    public abstract int rightOr(int value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanInt#left(boolean)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanInt#left(boolean)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanInt#left(boolean)}.
     */
    public abstract int rightOrGet(IntSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final boolean component1() {
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
    public abstract void ifEither(BooleanConsumer leftConsumer, IntConsumer rightConsumer);

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
    public abstract void ifRight(IntConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherBooleanInt} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanInt} instance with mapped values.
     */
    public abstract EitherBooleanInt map(BooleanUnaryOperator leftMapper,
                                         IntUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherBooleanInt} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherBooleanInt} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanInt} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanInt mapLeft(BooleanUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherBooleanInt} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherBooleanInt} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanInt} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanInt mapRight(IntUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherBooleanInt}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanInt} returned by mapper function.
     */
    public abstract EitherBooleanInt flatMap(BooleanFunction<? extends EitherBooleanInt> leftMapper,
                                             IntFunction<? extends EitherBooleanInt> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherBooleanInt} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanInt} returned by mapper function.
     */
    public abstract EitherBooleanInt flatMapLeft(BooleanFunction<? extends EitherBooleanInt> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherBooleanInt} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanInt} returned by mapper function.
     */
    public abstract EitherBooleanInt flatMapRight(IntFunction<? extends EitherBooleanInt> rightMapper);

    static class Left extends EitherBooleanInt {
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
        public void ifEither(BooleanConsumer leftConsumer, IntConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(IntConsumer consumer) {
        }

        @Override
        public EitherBooleanInt map(BooleanUnaryOperator leftMapper, IntUnaryOperator rightMapper) {
            return EitherBooleanInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanInt mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanInt mapRight(IntUnaryOperator rightMapper) {
            return EitherBooleanInt.left(this.getLeft());
        }

        @Override
        public EitherBooleanInt flatMap(BooleanFunction<? extends EitherBooleanInt> leftMapper,
                                        IntFunction<? extends EitherBooleanInt> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanInt flatMapLeft(BooleanFunction<? extends EitherBooleanInt> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanInt flatMapRight(IntFunction<? extends EitherBooleanInt> rightMapper) {
            return EitherBooleanInt.left(this.getLeft());
        }
    }

    static class Right extends EitherBooleanInt {
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
        public void ifEither(BooleanConsumer leftConsumer, IntConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
        }

        @Override
        public void ifRight(IntConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherBooleanInt map(BooleanUnaryOperator leftMapper, IntUnaryOperator rightMapper) {
            return EitherBooleanInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanInt mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanInt.right(this.getRight());
        }

        @Override
        public EitherBooleanInt mapRight(IntUnaryOperator rightMapper) {
            return EitherBooleanInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanInt flatMap(BooleanFunction<? extends EitherBooleanInt> leftMapper,
                                        IntFunction<? extends EitherBooleanInt> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherBooleanInt flatMapLeft(BooleanFunction<? extends EitherBooleanInt> leftMapper) {
            return EitherBooleanInt.right(this.getRight());
        }

        @Override
        public EitherBooleanInt flatMapRight(IntFunction<? extends EitherBooleanInt> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
