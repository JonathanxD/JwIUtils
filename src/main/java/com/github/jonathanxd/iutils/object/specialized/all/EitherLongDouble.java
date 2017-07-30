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
import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.LongUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;

/**
 * A class which can hold either {@link long} or {@link double} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherLongDouble extends BaseEither {

    EitherLongDouble() {
    }

    /**
     * Creates a {@link EitherLongDouble} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherLongDouble} which present value is the left value.
     */
    public static EitherLongDouble left(long left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherLongDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherLongDouble} which present value is the right value.
     */
    public static EitherLongDouble right(double right) {
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
    public abstract long getLeft();

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract double getRight();

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final long component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final double component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(LongConsumer leftConsumer, DoubleConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(LongConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherLongDouble} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherLongDouble} instance with mapped values.
     */
    public abstract EitherLongDouble map(LongUnaryOperator leftMapper,
                                         DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherLongDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherLongDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherLongDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherLongDouble mapLeft(LongUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherLongDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherLongDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherLongDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherLongDouble mapRight(DoubleUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherLongDouble}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherLongDouble} returned by mapper function.
     */
    public abstract EitherLongDouble flatMap(LongFunction<? extends EitherLongDouble> leftMapper,
                                             DoubleFunction<? extends EitherLongDouble> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherLongDouble} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherLongDouble} returned by mapper function.
     */
    public abstract EitherLongDouble flatMapLeft(LongFunction<? extends EitherLongDouble> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherLongDouble} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherLongDouble} returned by mapper function.
     */
    public abstract EitherLongDouble flatMapRight(DoubleFunction<? extends EitherLongDouble> rightMapper);

    static class Left extends EitherLongDouble {
        private final long value;

        Left(long value) {
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
        public long getLeft() {
            return this.value;
        }

        @Override
        public double getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(LongConsumer leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(LongConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public EitherLongDouble map(LongUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherLongDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherLongDouble mapLeft(LongUnaryOperator leftMapper) {
            return EitherLongDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherLongDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherLongDouble.left(this.getLeft());
        }

        @Override
        public EitherLongDouble flatMap(LongFunction<? extends EitherLongDouble> leftMapper,
                                        DoubleFunction<? extends EitherLongDouble> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherLongDouble flatMapLeft(LongFunction<? extends EitherLongDouble> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherLongDouble flatMapRight(DoubleFunction<? extends EitherLongDouble> rightMapper) {
            return EitherLongDouble.left(this.getLeft());
        }
    }

    static class Right extends EitherLongDouble {
        private final double value;

        Right(double value) {
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
        public long getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public double getRight() {
            return this.value;
        }

        @Override
        public void ifEither(LongConsumer leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(LongConsumer consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherLongDouble map(LongUnaryOperator leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherLongDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherLongDouble mapLeft(LongUnaryOperator leftMapper) {
            return EitherLongDouble.right(this.getRight());
        }

        @Override
        public EitherLongDouble mapRight(DoubleUnaryOperator rightMapper) {
            return EitherLongDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherLongDouble flatMap(LongFunction<? extends EitherLongDouble> leftMapper,
                                        DoubleFunction<? extends EitherLongDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherLongDouble flatMapLeft(LongFunction<? extends EitherLongDouble> leftMapper) {
            return EitherLongDouble.right(this.getRight());
        }

        @Override
        public EitherLongDouble flatMapRight(DoubleFunction<? extends EitherLongDouble> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
