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
package com.github.jonathanxd.iutils.object.specialized;

import com.github.jonathanxd.iutils.annotation.Generated;
import com.github.jonathanxd.iutils.function.consumer.ByteConsumer;
import com.github.jonathanxd.iutils.function.function.ByteFunction;
import com.github.jonathanxd.iutils.function.unary.ByteUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A class which can hold either {@link L} or {@link byte} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
@Generated
public abstract class EitherObjByte<L> extends BaseEither {

    EitherObjByte() {
    }

    /**
     * Creates a {@link EitherObjByte} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjByte} which present value is the left value.
     */
    public static <L> EitherObjByte<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjByte} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjByte} which present value is the right value.
     */
    public static <L> EitherObjByte<L> right(byte right) {
        return new Right<>(right);
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
    public abstract L getLeft();

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract byte getRight();

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final L component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final byte component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, ByteConsumer rightConsumer);

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifLeft(Consumer<? super L> consumer);

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public abstract void ifRight(ByteConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherObjByte}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjByte} instance with mapped values.
     */
    public abstract <ML> EitherObjByte<ML> map(Function<? super L, ? extends ML> leftMapper,
                                               ByteUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjByte} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjByte} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjByte} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjByte<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjByte} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjByte} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjByte} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjByte<L> mapRight(ByteUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherObjByte}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjByte} returned by mapper function.
     */
    public abstract <ML> EitherObjByte<ML> flatMap(Function<? super L, ? extends EitherObjByte<ML>> leftMapper,
                                                   ByteFunction<? extends EitherObjByte<ML>> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherObjByte} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjByte} returned by mapper function.
     */
    public abstract <ML> EitherObjByte<ML> flatMapLeft(Function<? super L, ? extends EitherObjByte<ML>> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherObjByte} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjByte} returned by mapper function.
     */
    public abstract EitherObjByte<L> flatMapRight(ByteFunction<? extends EitherObjByte<L>> rightMapper);

    static class Left<L> extends EitherObjByte<L> {
        private final L value;

        Left(L value) {
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
        public L getLeft() {
            return this.value;
        }

        @Override
        public byte getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, ByteConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(ByteConsumer consumer) {
        }

        @Override
        public <ML> EitherObjByte<ML> map(Function<? super L, ? extends ML> leftMapper,
                                          ByteUnaryOperator rightMapper) {
            return EitherObjByte.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjByte<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjByte.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjByte<L> mapRight(ByteUnaryOperator rightMapper) {
            return EitherObjByte.left(this.getLeft());
        }

        @Override
        public <ML> EitherObjByte<ML> flatMap(Function<? super L, ? extends EitherObjByte<ML>> leftMapper,
                                              ByteFunction<? extends EitherObjByte<ML>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> EitherObjByte<ML> flatMapLeft(Function<? super L, ? extends EitherObjByte<ML>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherObjByte<L> flatMapRight(ByteFunction<? extends EitherObjByte<L>> rightMapper) {
            return EitherObjByte.left(this.getLeft());
        }

    }

    static class Right<L> extends EitherObjByte<L> {
        private final byte value;

        Right(byte value) {
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
        public L getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public byte getRight() {
            return this.value;
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, ByteConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(ByteConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjByte<ML> map(Function<? super L, ? extends ML> leftMapper,
                                          ByteUnaryOperator rightMapper) {
            return EitherObjByte.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjByte<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjByte.right(this.getRight());
        }

        @Override
        public EitherObjByte<L> mapRight(ByteUnaryOperator rightMapper) {
            return EitherObjByte.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjByte<ML> flatMap(Function<? super L, ? extends EitherObjByte<ML>> leftMapper,
                                              ByteFunction<? extends EitherObjByte<ML>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> EitherObjByte<ML> flatMapLeft(Function<? super L, ? extends EitherObjByte<ML>> leftMapper) {
            return EitherObjByte.right(this.getRight());
        }

        @Override
        public EitherObjByte<L> flatMapRight(ByteFunction<? extends EitherObjByte<L>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
