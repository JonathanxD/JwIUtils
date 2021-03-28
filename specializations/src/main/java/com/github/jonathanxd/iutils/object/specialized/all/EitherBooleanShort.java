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
import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.supplier.ShortSupplier;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.ShortUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;

/**
 * A class which can hold either {@link boolean} or {@link short} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherBooleanShort extends BaseEither {

    EitherBooleanShort() {
    }

    /**
     * Creates a {@link EitherBooleanShort} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherBooleanShort} which present value is the left value.
     */
    public static EitherBooleanShort left(boolean left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherBooleanShort} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherBooleanShort} which present value is the right value.
     */
    public static EitherBooleanShort right(short right) {
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
     * Returns left value or {@code value} if this is a {@link EitherBooleanShort#right(short)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanShort#right(short)}
     * @return Left value or {@code value} if this is a {@link EitherBooleanShort#right(short)}.
     */
    public abstract boolean leftOr(boolean value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanShort#right(short)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanShort#right(short)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanShort#right(short)}.
     */
    public abstract boolean leftOrGet(BooleanSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract short getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherBooleanShort#left(boolean)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanShort#left(boolean)}
     * @return Right value or {@code value} if this is a {@link EitherBooleanShort#left(boolean)}.
     */
    public abstract short rightOr(short value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanShort#left(boolean)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanShort#left(boolean)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanShort#left(boolean)}.
     */
    public abstract short rightOrGet(ShortSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final boolean component1() {
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
    public abstract void ifEither(BooleanConsumer leftConsumer, ShortConsumer rightConsumer);

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
    public abstract void ifRight(ShortConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherBooleanShort} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanShort} instance with mapped values.
     */
    public abstract EitherBooleanShort map(BooleanUnaryOperator leftMapper,
                                           ShortUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherBooleanShort} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherBooleanShort} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanShort} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanShort mapLeft(BooleanUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherBooleanShort} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherBooleanShort} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanShort} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanShort mapRight(ShortUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link
     * EitherBooleanShort} returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanShort} returned by mapper function.
     */
    public abstract EitherBooleanShort flatMap(BooleanFunction<? extends EitherBooleanShort> leftMapper,
                                               ShortFunction<? extends EitherBooleanShort> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherBooleanShort} returned by mapper
     * function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanShort} returned by mapper function.
     */
    public abstract EitherBooleanShort flatMapLeft(BooleanFunction<? extends EitherBooleanShort> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherBooleanShort} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanShort} returned by mapper function.
     */
    public abstract EitherBooleanShort flatMapRight(ShortFunction<? extends EitherBooleanShort> rightMapper);

    static class Left extends EitherBooleanShort {
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
        public void ifEither(BooleanConsumer leftConsumer, ShortConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(ShortConsumer consumer) {
        }

        @Override
        public EitherBooleanShort map(BooleanUnaryOperator leftMapper, ShortUnaryOperator rightMapper) {
            return EitherBooleanShort.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanShort mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanShort.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanShort mapRight(ShortUnaryOperator rightMapper) {
            return EitherBooleanShort.left(this.getLeft());
        }

        @Override
        public EitherBooleanShort flatMap(BooleanFunction<? extends EitherBooleanShort> leftMapper,
                                          ShortFunction<? extends EitherBooleanShort> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanShort flatMapLeft(BooleanFunction<? extends EitherBooleanShort> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanShort flatMapRight(ShortFunction<? extends EitherBooleanShort> rightMapper) {
            return EitherBooleanShort.left(this.getLeft());
        }
    }

    static class Right extends EitherBooleanShort {
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
        public void ifEither(BooleanConsumer leftConsumer, ShortConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
        }

        @Override
        public void ifRight(ShortConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherBooleanShort map(BooleanUnaryOperator leftMapper, ShortUnaryOperator rightMapper) {
            return EitherBooleanShort.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanShort mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanShort.right(this.getRight());
        }

        @Override
        public EitherBooleanShort mapRight(ShortUnaryOperator rightMapper) {
            return EitherBooleanShort.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanShort flatMap(BooleanFunction<? extends EitherBooleanShort> leftMapper,
                                          ShortFunction<? extends EitherBooleanShort> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherBooleanShort flatMapLeft(BooleanFunction<? extends EitherBooleanShort> leftMapper) {
            return EitherBooleanShort.right(this.getRight());
        }

        @Override
        public EitherBooleanShort flatMapRight(ShortFunction<? extends EitherBooleanShort> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
