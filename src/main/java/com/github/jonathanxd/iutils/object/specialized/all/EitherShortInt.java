/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
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
import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.unary.IntUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.ShortUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

/**
 * A class which can hold either {@link short} or {@link int} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherShortInt extends BaseEither {

    EitherShortInt() {
    }

    /**
     * Creates a {@link EitherShortInt} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherShortInt} which present value is the left value.
     */
    public static EitherShortInt left(short left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherShortInt} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherShortInt} which present value is the right value.
     */
    public static EitherShortInt right(int right) {
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
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract int getRight();

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(ShortConsumer leftConsumer, IntConsumer rightConsumer);

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
    public abstract void ifRight(IntConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherShortInt}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortInt} instance with mapped values.
     */
    public abstract EitherShortInt map(ShortUnaryOperator leftMapper,
                                       IntUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherShortInt} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherShortInt} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherShortInt} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherShortInt mapLeft(ShortUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherShortInt} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherShortInt} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortInt} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherShortInt mapRight(IntUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherShortInt}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortInt} returned by mapper function.
     */
    public abstract EitherShortInt flatMap(ShortFunction<? extends EitherShortInt> leftMapper,
                                           IntFunction<? extends EitherShortInt> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherShortInt} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherShortInt} returned by mapper function.
     */
    public abstract EitherShortInt flatMapLeft(ShortFunction<? extends EitherShortInt> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherShortInt} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortInt} returned by mapper function.
     */
    public abstract EitherShortInt flatMapRight(IntFunction<? extends EitherShortInt> rightMapper);

    static class Left extends EitherShortInt {
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
        public int getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(ShortConsumer leftConsumer, IntConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ShortConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(IntConsumer consumer) {
        }

        @Override
        public EitherShortInt map(ShortUnaryOperator leftMapper, IntUnaryOperator rightMapper) {
            return EitherShortInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherShortInt mapLeft(ShortUnaryOperator leftMapper) {
            return EitherShortInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherShortInt mapRight(IntUnaryOperator rightMapper) {
            return EitherShortInt.left(this.getLeft());
        }

        @Override
        public EitherShortInt flatMap(ShortFunction<? extends EitherShortInt> leftMapper,
                                      IntFunction<? extends EitherShortInt> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherShortInt flatMapLeft(ShortFunction<? extends EitherShortInt> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherShortInt flatMapRight(IntFunction<? extends EitherShortInt> rightMapper) {
            return EitherShortInt.left(this.getLeft());
        }
    }

    static class Right extends EitherShortInt {
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
        public short getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public int getRight() {
            return this.value;
        }

        @Override
        public void ifEither(ShortConsumer leftConsumer, IntConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ShortConsumer consumer) {
        }

        @Override
        public void ifRight(IntConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherShortInt map(ShortUnaryOperator leftMapper, IntUnaryOperator rightMapper) {
            return EitherShortInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherShortInt mapLeft(ShortUnaryOperator leftMapper) {
            return EitherShortInt.right(this.getRight());
        }

        @Override
        public EitherShortInt mapRight(IntUnaryOperator rightMapper) {
            return EitherShortInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherShortInt flatMap(ShortFunction<? extends EitherShortInt> leftMapper,
                                      IntFunction<? extends EitherShortInt> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherShortInt flatMapLeft(ShortFunction<? extends EitherShortInt> leftMapper) {
            return EitherShortInt.right(this.getRight());
        }

        @Override
        public EitherShortInt flatMapRight(IntFunction<? extends EitherShortInt> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
