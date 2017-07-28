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
import com.github.jonathanxd.iutils.function.unary.IntUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

/**
 * A class which can hold either {@link L} or {@link int} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
@Generated
public abstract class EitherObjInt<L> extends BaseEither {

    EitherObjInt() {
    }

    /**
     * Creates a {@link EitherObjInt} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjInt} which present value is the left value.
     */
    public static <L> EitherObjInt<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjInt} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjInt} which present value is the right value.
     */
    public static <L> EitherObjInt<L> right(int right) {
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
    public abstract int getRight();

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, IntConsumer rightConsumer);

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
    public abstract void ifRight(IntConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherObjInt}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjInt} instance with mapped values.
     */
    public abstract <ML> EitherObjInt<ML> map(Function<? super L, ? extends ML> leftMapper,
                                              IntUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjInt} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjInt} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjInt} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjInt<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjInt} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjInt} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjInt} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjInt<L> mapRight(IntUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherObjInt}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjInt} returned by mapper function.
     */
    public abstract <ML> EitherObjInt<ML> flatMap(Function<? super L, ? extends EitherObjInt<ML>> leftMapper,
                                                  IntFunction<? extends EitherObjInt<ML>> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherObjInt} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjInt} returned by mapper function.
     */
    public abstract <ML> EitherObjInt<ML> flatMapLeft(Function<? super L, ? extends EitherObjInt<ML>> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherObjInt} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjInt} returned by mapper function.
     */
    public abstract EitherObjInt<L> flatMapRight(IntFunction<? extends EitherObjInt<L>> rightMapper);

    static class Left<L> extends EitherObjInt<L> {
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
        public int getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, IntConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(IntConsumer consumer) {
        }

        @Override
        public <ML> EitherObjInt<ML> map(Function<? super L, ? extends ML> leftMapper,
                                         IntUnaryOperator rightMapper) {
            return EitherObjInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjInt<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjInt.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjInt<L> mapRight(IntUnaryOperator rightMapper) {
            return EitherObjInt.left(this.getLeft());
        }

        @Override
        public <ML> EitherObjInt<ML> flatMap(Function<? super L, ? extends EitherObjInt<ML>> leftMapper,
                                             IntFunction<? extends EitherObjInt<ML>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> EitherObjInt<ML> flatMapLeft(Function<? super L, ? extends EitherObjInt<ML>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherObjInt<L> flatMapRight(IntFunction<? extends EitherObjInt<L>> rightMapper) {
            return EitherObjInt.left(this.getLeft());
        }

    }

    static class Right<L> extends EitherObjInt<L> {
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
        public L getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public int getRight() {
            return this.value;
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, IntConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(IntConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjInt<ML> map(Function<? super L, ? extends ML> leftMapper,
                                         IntUnaryOperator rightMapper) {
            return EitherObjInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjInt<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjInt.right(this.getRight());
        }

        @Override
        public EitherObjInt<L> mapRight(IntUnaryOperator rightMapper) {
            return EitherObjInt.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjInt<ML> flatMap(Function<? super L, ? extends EitherObjInt<ML>> leftMapper,
                                             IntFunction<? extends EitherObjInt<ML>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> EitherObjInt<ML> flatMapLeft(Function<? super L, ? extends EitherObjInt<ML>> leftMapper) {
            return EitherObjInt.right(this.getRight());
        }

        @Override
        public EitherObjInt<L> flatMapRight(IntFunction<? extends EitherObjInt<L>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }
    }
}
