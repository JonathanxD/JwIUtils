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
import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.function.CharFunction;
import com.github.jonathanxd.iutils.function.supplier.CharSupplier;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.CharUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;

/**
 * A class which can hold either {@link boolean} or {@link char} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherBooleanChar extends BaseEither {

    EitherBooleanChar() {
    }

    /**
     * Creates a {@link EitherBooleanChar} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherBooleanChar} which present value is the left value.
     */
    public static EitherBooleanChar left(boolean left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherBooleanChar} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherBooleanChar} which present value is the right value.
     */
    public static EitherBooleanChar right(char right) {
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
     * Returns left value or {@code value} if this is a {@link EitherBooleanChar#right(char)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanChar#right(char)}
     * @return Left value or {@code value} if this is a {@link EitherBooleanChar#right(char)}.
     */
    public abstract boolean leftOr(boolean value);

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanChar#right(char)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanChar#right(char)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanChar#right(char)}.
     */
    public abstract boolean leftOrGet(BooleanSupplier supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract char getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherBooleanChar#left(boolean)}.
     *
     * @param value Value to return if this is a {@link EitherBooleanChar#left(boolean)}
     * @return Right value or {@code value} if this is a {@link EitherBooleanChar#left(boolean)}.
     */
    public abstract char rightOr(char value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanChar#left(boolean)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherBooleanChar#left(boolean)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherBooleanChar#left(boolean)}.
     */
    public abstract char rightOrGet(CharSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final boolean component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final char component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(BooleanConsumer leftConsumer, CharConsumer rightConsumer);

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
    public abstract void ifRight(CharConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherBooleanChar} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanChar} instance with mapped values.
     */
    public abstract EitherBooleanChar map(BooleanUnaryOperator leftMapper,
                                          CharUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherBooleanChar} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherBooleanChar} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanChar} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanChar mapLeft(BooleanUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherBooleanChar} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherBooleanChar} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanChar} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanChar mapRight(CharUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link
     * EitherBooleanChar} returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanChar} returned by mapper function.
     */
    public abstract EitherBooleanChar flatMap(BooleanFunction<? extends EitherBooleanChar> leftMapper,
                                              CharFunction<? extends EitherBooleanChar> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherBooleanChar} returned by mapper
     * function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanChar} returned by mapper function.
     */
    public abstract EitherBooleanChar flatMapLeft(BooleanFunction<? extends EitherBooleanChar> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherBooleanChar} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanChar} returned by mapper function.
     */
    public abstract EitherBooleanChar flatMapRight(CharFunction<? extends EitherBooleanChar> rightMapper);

    static class Left extends EitherBooleanChar {
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
        public char getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public char rightOr(char value) {
            return value;
        }

        @Override
        public char rightOrGet(CharSupplier supplier) {
            return supplier.get();
        }

        @Override
        public void ifEither(BooleanConsumer leftConsumer, CharConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(CharConsumer consumer) {
        }

        @Override
        public EitherBooleanChar map(BooleanUnaryOperator leftMapper, CharUnaryOperator rightMapper) {
            return EitherBooleanChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanChar mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanChar mapRight(CharUnaryOperator rightMapper) {
            return EitherBooleanChar.left(this.getLeft());
        }

        @Override
        public EitherBooleanChar flatMap(BooleanFunction<? extends EitherBooleanChar> leftMapper,
                                         CharFunction<? extends EitherBooleanChar> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanChar flatMapLeft(BooleanFunction<? extends EitherBooleanChar> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherBooleanChar flatMapRight(CharFunction<? extends EitherBooleanChar> rightMapper) {
            return EitherBooleanChar.left(this.getLeft());
        }
    }

    static class Right extends EitherBooleanChar {
        private final char value;

        Right(char value) {
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
        public char getRight() {
            return this.value;
        }

        @Override
        public char rightOr(char value) {
            return this.getRight();
        }

        @Override
        public char rightOrGet(CharSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(BooleanConsumer leftConsumer, CharConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
        }

        @Override
        public void ifRight(CharConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherBooleanChar map(BooleanUnaryOperator leftMapper, CharUnaryOperator rightMapper) {
            return EitherBooleanChar.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanChar mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanChar.right(this.getRight());
        }

        @Override
        public EitherBooleanChar mapRight(CharUnaryOperator rightMapper) {
            return EitherBooleanChar.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanChar flatMap(BooleanFunction<? extends EitherBooleanChar> leftMapper,
                                         CharFunction<? extends EitherBooleanChar> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherBooleanChar flatMapLeft(BooleanFunction<? extends EitherBooleanChar> leftMapper) {
            return EitherBooleanChar.right(this.getRight());
        }

        @Override
        public EitherBooleanChar flatMapRight(CharFunction<? extends EitherBooleanChar> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
