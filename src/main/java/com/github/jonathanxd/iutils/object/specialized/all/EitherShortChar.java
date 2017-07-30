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
import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.consumer.ShortConsumer;
import com.github.jonathanxd.iutils.function.function.CharFunction;
import com.github.jonathanxd.iutils.function.function.ShortFunction;
import com.github.jonathanxd.iutils.function.unary.CharUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.ShortUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;

/**
 * A class which can hold either {@link short} or {@link char} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherShortChar extends BaseEither {

    EitherShortChar() {
    }

    /**
     * Creates a {@link EitherShortChar} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherShortChar} which present value is the left value.
     */
    public static EitherShortChar left(short left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherShortChar} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherShortChar} which present value is the right value.
     */
    public static EitherShortChar right(char right) {
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
    public abstract char getRight();

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final short component1() {
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
    public abstract void ifEither(ShortConsumer leftConsumer, CharConsumer rightConsumer);

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
    public abstract void ifRight(CharConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherShortChar} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortChar} instance with mapped values.
     */
    public abstract EitherShortChar map(ShortUnaryOperator leftMapper,
                                        CharUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherShortChar} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherShortChar} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherShortChar} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherShortChar mapLeft(ShortUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherShortChar} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherShortChar} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortChar} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherShortChar mapRight(CharUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherShortChar}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortChar} returned by mapper function.
     */
    public abstract EitherShortChar flatMap(ShortFunction<? extends EitherShortChar> leftMapper,
                                            CharFunction<? extends EitherShortChar> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherShortChar} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherShortChar} returned by mapper function.
     */
    public abstract EitherShortChar flatMapLeft(ShortFunction<? extends EitherShortChar> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherShortChar} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherShortChar} returned by mapper function.
     */
    public abstract EitherShortChar flatMapRight(CharFunction<? extends EitherShortChar> rightMapper);

    static class Left extends EitherShortChar {
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
        public char getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(ShortConsumer leftConsumer, CharConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ShortConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(CharConsumer consumer) {
        }

        @Override
        public EitherShortChar map(ShortUnaryOperator leftMapper, CharUnaryOperator rightMapper) {
            return EitherShortChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherShortChar mapLeft(ShortUnaryOperator leftMapper) {
            return EitherShortChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherShortChar mapRight(CharUnaryOperator rightMapper) {
            return EitherShortChar.left(this.getLeft());
        }

        @Override
        public EitherShortChar flatMap(ShortFunction<? extends EitherShortChar> leftMapper,
                                       CharFunction<? extends EitherShortChar> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherShortChar flatMapLeft(ShortFunction<? extends EitherShortChar> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherShortChar flatMapRight(CharFunction<? extends EitherShortChar> rightMapper) {
            return EitherShortChar.left(this.getLeft());
        }
    }

    static class Right extends EitherShortChar {
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
        public short getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public char getRight() {
            return this.value;
        }

        @Override
        public void ifEither(ShortConsumer leftConsumer, CharConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ShortConsumer consumer) {
        }

        @Override
        public void ifRight(CharConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherShortChar map(ShortUnaryOperator leftMapper, CharUnaryOperator rightMapper) {
            return EitherShortChar.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherShortChar mapLeft(ShortUnaryOperator leftMapper) {
            return EitherShortChar.right(this.getRight());
        }

        @Override
        public EitherShortChar mapRight(CharUnaryOperator rightMapper) {
            return EitherShortChar.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherShortChar flatMap(ShortFunction<? extends EitherShortChar> leftMapper,
                                       CharFunction<? extends EitherShortChar> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public EitherShortChar flatMapLeft(ShortFunction<? extends EitherShortChar> leftMapper) {
            return EitherShortChar.right(this.getRight());
        }

        @Override
        public EitherShortChar flatMapRight(CharFunction<? extends EitherShortChar> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
