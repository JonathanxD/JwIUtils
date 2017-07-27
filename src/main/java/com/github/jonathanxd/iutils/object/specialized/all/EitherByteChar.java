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
import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.unary.ByteUnaryOperator;
import com.github.jonathanxd.iutils.function.unary.CharUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;

/**
 * A class which can hold either {@link byte} or {@link char} (in this documentation we call the
 * hold value as present value).
 */
@Generated
public abstract class EitherByteChar extends BaseEither {

    EitherByteChar() {
    }

    /**
     * Creates a {@link EitherByteChar} which present value is the left value.
     *
     * @param left Left value.
     * @return {@link EitherByteChar} which present value is the left value.
     */
    public static EitherByteChar left(byte left) {
        return new Left(left);
    }

    /**
     * Creates a {@link EitherByteChar} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherByteChar} which present value is the right value.
     */
    public static EitherByteChar right(char right) {
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
    public abstract byte getLeft();

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract char getRight();

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(ByteConsumer leftConsumer, CharConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(ByteConsumer consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(CharConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherByteChar} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteChar} instance with mapped values.
     */
    public abstract EitherByteChar map(ByteUnaryOperator leftMapper,
                                        CharUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherByteChar} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherByteChar} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @return {@link EitherByteChar} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteChar mapLeft(ByteUnaryOperator leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherByteChar} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherByteChar} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherByteChar} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherByteChar mapRight(CharUnaryOperator rightMapper);

    static class Left extends EitherByteChar {
        private final byte value;

        Left(byte value) {
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
        public byte getLeft() {
            return this.value;
        }

        @Override
        public char getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(ByteConsumer leftConsumer, CharConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(CharConsumer consumer) {
        }

        @Override
        public EitherByteChar map(ByteUnaryOperator leftMapper, CharUnaryOperator rightMapper) {
            return EitherByteChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteChar mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherByteChar mapRight(CharUnaryOperator rightMapper) {
            return EitherByteChar.left(this.getLeft());
        }
    }

    static class Right extends EitherByteChar {
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
        public byte getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public char getRight() {
            return this.value;
        }

        @Override
        public void ifEither(ByteConsumer leftConsumer, CharConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(ByteConsumer consumer) {
        }

        @Override
        public void ifRight(CharConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public EitherByteChar map(ByteUnaryOperator leftMapper, CharUnaryOperator rightMapper) {
            return EitherByteChar.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public EitherByteChar mapLeft(ByteUnaryOperator leftMapper) {
            return EitherByteChar.right(this.getRight());
        }

        @Override
        public EitherByteChar mapRight(CharUnaryOperator rightMapper) {
            return EitherByteChar.right(rightMapper.apply(this.getRight()));
        }
    }
}
