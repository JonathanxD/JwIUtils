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
import com.github.jonathanxd.iutils.function.consumer.FloatConsumer;
import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.FloatFunction;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.supplier.FloatSupplier;
import com.github.jonathanxd.iutils.function.supplier.ShortSupplier;
import com.github.jonathanxd.iutils.function.unary.FloatUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.ShortUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;

/**
 * A class which can hold either {@link short} or {@link float} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherShortFloat extends BaseEither {

    EitherShortFloat() {
    }

    /**
     * Creates a {@link EitherShortFloat} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherShortFloat} which present value is the left value.
     */
    public static EitherShortFloat left(short left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherShortFloat} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherShortFloat} which present value is the right value.
     */
    public static EitherShortFloat right(float right) {
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
    public abstract short getLeft();

    /**
     * Returns left value or {@code value} if this is a {@link EitherShortFloat#right(float)}.
     *
     * @param value Value to return if this is a {@link EitherShortFloat#right(float)}
     * @return Left value or {@code value} if this is a {@link EitherShortFloat#right(float)}.
     */
    public abstract short leftOr(short value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherShortFloat#right(float)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherShortFloat#right(float)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherShortFloat#right(float)}.
     */
    public abstract short leftOrGet(ShortSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract float getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherShortFloat#left(short)}.
     *
     * @param value Value to return if this is a {@link EitherShortFloat#left(short)}
     * @return Right value or {@code value} if this is a {@link EitherShortFloat#left(short)}.
     */
    public abstract float rightOr(float value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherShortFloat#left(short)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherShortFloat#left(short)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherShortFloat#left(short)}.
     */
    public abstract float rightOrGet(FloatSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final short component1() {
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
    public abstract void ifEither(ShortConsumer leftConsumer, FloatConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(ShortConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(FloatConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherShortFloat} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortFloat} instance with mapped values.
     */
    public abstract EitherShortFloat map(ShortUnaryOperator leftMapper,
                                         FloatUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherShortFloat} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherShortFloat} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherShortFloat} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherShortFloat mapLeft(ShortUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherShortFloat} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherShortFloat} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortFloat} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherShortFloat mapRight(FloatUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherShortFloat}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortFloat} returned by mapper function.
     */
    public abstract EitherShortFloat flatMap(ShortFunction<? extends EitherShortFloat> leftMapper,
                                             FloatFunction<? extends EitherShortFloat> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherShortFloat} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherShortFloat} returned by mapper function.
     */
    public abstract EitherShortFloat flatMapLeft(ShortFunction<? extends EitherShortFloat> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherShortFloat} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortFloat} returned by mapper function.
     */
    public abstract EitherShortFloat flatMapRight(FloatFunction<? extends EitherShortFloat> rightMapper);

    static class Left extends EitherShortFloat {
        private final short value;

        Left(short value) {
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
        public short getLeft() {
            return this.value;
        }

        @Override
        public short leftOr(short value) {
            return this.getLeft();
        }

        @Override
        public short leftOrGet(ShortSupplier supplier) {
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
        public void ifEither(ShortConsumer leftConsumer, FloatConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ShortConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(FloatConsumer consumer) {
        }

        @Override
        public EitherShortFloat map(ShortUnaryOperator leftMapper, FloatUnaryOperator rightMapper) {
            return EitherShortFloat.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherShortFloat mapLeft(ShortUnaryOperator leftMapper) {
            return EitherShortFloat.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherShortFloat mapRight(FloatUnaryOperator rightMapper) {
            return EitherShortFloat.left(this.getLeft());
        }

        @Override
        public EitherShortFloat flatMap(ShortFunction<? extends EitherShortFloat> leftMapper,
                                        FloatFunction<? extends EitherShortFloat> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherShortFloat flatMapLeft(ShortFunction<? extends EitherShortFloat> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherShortFloat flatMapRight(FloatFunction<? extends EitherShortFloat> rightMapper) {
            return EitherShortFloat.left(this.getLeft());
        }
    }

    static class Right extends EitherShortFloat {
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
        public short getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public short leftOr(short value) {
            return value;
        }

        @Override
        public short leftOrGet(ShortSupplier supplier) {
            return supplier.get();
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
        public void ifEither(ShortConsumer leftConsumer, FloatConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ShortConsumer consumer) {
        }

        @Override
        public void ifRight(FloatConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherShortFloat map(ShortUnaryOperator leftMapper, FloatUnaryOperator rightMapper) {
            return EitherShortFloat.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherShortFloat mapLeft(ShortUnaryOperator leftMapper) {
            return EitherShortFloat.right(this.getRight());
        }

        @Override
        public EitherShortFloat mapRight(FloatUnaryOperator rightMapper) {
            return EitherShortFloat.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherShortFloat flatMap(ShortFunction<? extends EitherShortFloat> leftMapper,
                                        FloatFunction<? extends EitherShortFloat> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherShortFloat flatMapLeft(ShortFunction<? extends EitherShortFloat> leftMapper) {
            return EitherShortFloat.right(this.getRight());
        }

        @Override
        public EitherShortFloat flatMapRight(FloatFunction<? extends EitherShortFloat> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
