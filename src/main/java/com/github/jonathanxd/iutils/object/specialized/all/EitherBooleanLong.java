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
import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.LongUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.LongConsumer;

/**
 * A class which can hold either {@link boolean} or {@link long} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherBooleanLong extends BaseEither {

    EitherBooleanLong() {
    }

    /**
     * Creates a {@link EitherBooleanLong} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherBooleanLong} which present value is the left value.
     */
    public static EitherBooleanLong left(boolean left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherBooleanLong} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherBooleanLong} which present value is the right value.
     */
    public static EitherBooleanLong right(long right) {
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
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract long getRight();

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(BooleanConsumer leftConsumer, LongConsumer rightConsumer);

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
    public abstract void ifRight(LongConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherBooleanLong} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanLong} instance with mapped values.
     */
    public abstract EitherBooleanLong map(BooleanUnaryOperator leftMapper,
                                        LongUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherBooleanLong} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherBooleanLong} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherBooleanLong} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanLong mapLeft(BooleanUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherBooleanLong} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherBooleanLong} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherBooleanLong} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherBooleanLong mapRight(LongUnaryOperator rightMapper);

    static class Left extends EitherBooleanLong {
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
        public long getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(BooleanConsumer leftConsumer, LongConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(LongConsumer consumer) {
        }

        @Override
        public EitherBooleanLong map(BooleanUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherBooleanLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanLong mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherBooleanLong mapRight(LongUnaryOperator rightMapper) {
            return EitherBooleanLong.left(this.getLeft());
        }
    }

    static class Right extends EitherBooleanLong {
        private final long value;

        Right(long value) {
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
        public long getRight() {
            return this.value;
        }

        @Override
        public void ifEither(BooleanConsumer leftConsumer, LongConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(BooleanConsumer consumer) {
        }

        @Override
        public void ifRight(LongConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherBooleanLong map(BooleanUnaryOperator leftMapper, LongUnaryOperator rightMapper) {
            return EitherBooleanLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherBooleanLong mapLeft(BooleanUnaryOperator leftMapper) {
            return EitherBooleanLong.right(this.getRight());
        }

        @Override
        public EitherBooleanLong mapRight(LongUnaryOperator rightMapper) {
            return EitherBooleanLong.right(rightMapper.apply(this.getRight()));
        }
    }
}
