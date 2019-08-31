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
import com.github.jonathanxd.iutils.function.consumer.FloatConsumer;
import com.github.jonathanxd.iutils.function.function.FloatFunction;
import com.github.jonathanxd.iutils.function.supplier.FloatSupplier;
import com.github.jonathanxd.iutils.function.unary.FloatUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.IntUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;

/**
 * A class which can hold either {@link int} or {@link float} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherIntFloat extends BaseEither {

    EitherIntFloat() {
    }

    /**
     * Creates a {@link EitherIntFloat} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherIntFloat} which present value is the left value.
     */
    public static EitherIntFloat left(int left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherIntFloat} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherIntFloat} which present value is the right value.
     */
    public static EitherIntFloat right(float right) {
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
     * Returns left value or {@code value} if this is a {@link EitherIntFloat#right(float)}.
     *
     * @param value Value to return if this is a {@link EitherIntFloat#right(float)}
     * @return Left value or {@code value} if this is a {@link EitherIntFloat#right(float)}.
     */
    public abstract int leftOr(int value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherIntFloat#right(float)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherIntFloat#right(float)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherIntFloat#right(float)}.
     */
    public abstract int leftOrGet(IntSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract float getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherIntFloat#left(int)}.
     *
     * @param value Value to return if this is a {@link EitherIntFloat#left(int)}
     * @return Right value or {@code value} if this is a {@link EitherIntFloat#left(int)}.
     */
    public abstract float rightOr(float value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherIntFloat#left(int)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherIntFloat#left(int)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherIntFloat#left(int)}.
     */
    public abstract float rightOrGet(FloatSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final int component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final float component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(IntConsumer leftConsumer, FloatConsumer rightConsumer);

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
    public abstract void ifRight(FloatConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherIntFloat}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntFloat} instance with mapped values.
     */
    public abstract EitherIntFloat map(IntUnaryOperator leftMapper,
                                       FloatUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherIntFloat} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherIntFloat} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherIntFloat} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherIntFloat mapLeft(IntUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherIntFloat} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherIntFloat} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntFloat} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherIntFloat mapRight(FloatUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherIntFloat}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntFloat} returned by mapper function.
     */
    public abstract EitherIntFloat flatMap(IntFunction<? extends EitherIntFloat> leftMapper,
                                           FloatFunction<? extends EitherIntFloat> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherIntFloat} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherIntFloat} returned by mapper function.
     */
    public abstract EitherIntFloat flatMapLeft(IntFunction<? extends EitherIntFloat> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherIntFloat} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherIntFloat} returned by mapper function.
     */
    public abstract EitherIntFloat flatMapRight(FloatFunction<? extends EitherIntFloat> rightMapper);

    static class Left extends EitherIntFloat {
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
        public float getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public float rightOr(float value) {
            return value;
        }

        @Override
        public float rightOrGet(FloatSupplier supplier) {
            return supplier.get();
        }

        @Override
        public void ifEither(IntConsumer leftConsumer, FloatConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(IntConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(FloatConsumer consumer) {
        }

        @Override
        public EitherIntFloat map(IntUnaryOperator leftMapper, FloatUnaryOperator rightMapper) {
            return EitherIntFloat.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherIntFloat mapLeft(IntUnaryOperator leftMapper) {
            return EitherIntFloat.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherIntFloat mapRight(FloatUnaryOperator rightMapper) {
            return EitherIntFloat.left(this.getLeft());
        }

        @Override
        public EitherIntFloat flatMap(IntFunction<? extends EitherIntFloat> leftMapper,
                                      FloatFunction<? extends EitherIntFloat> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherIntFloat flatMapLeft(IntFunction<? extends EitherIntFloat> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherIntFloat flatMapRight(FloatFunction<? extends EitherIntFloat> rightMapper) {
            return EitherIntFloat.left(this.getLeft());
        }
    }

    static class Right extends EitherIntFloat {
        private final float value;

        Right(float value) {
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
        public float getRight() {
            return this.value;
        }

        @Override
        public float rightOr(float value) {
            return this.getRight();
        }

        @Override
        public float rightOrGet(FloatSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(IntConsumer leftConsumer, FloatConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(IntConsumer consumer) {
        }

        @Override
        public void ifRight(FloatConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherIntFloat map(IntUnaryOperator leftMapper, FloatUnaryOperator rightMapper) {
            return EitherIntFloat.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherIntFloat mapLeft(IntUnaryOperator leftMapper) {
            return EitherIntFloat.right(this.getRight());
        }

        @Override
        public EitherIntFloat mapRight(FloatUnaryOperator rightMapper) {
            return EitherIntFloat.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherIntFloat flatMap(IntFunction<? extends EitherIntFloat> leftMapper,
                                      FloatFunction<? extends EitherIntFloat> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherIntFloat flatMapLeft(IntFunction<? extends EitherIntFloat> leftMapper) {
            return EitherIntFloat.right(this.getRight());
        }

        @Override
        public EitherIntFloat flatMapRight(FloatFunction<? extends EitherIntFloat> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
