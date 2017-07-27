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
import com.github.jonathanxd.iutils.function.unary.DoubleUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;

/**
 * A class which can hold either {@link L} or {@link double} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
@Generated
public abstract class EitherObjDouble<L> extends BaseEither {

    EitherObjDouble() {
    }

    /**
     * Creates a {@link EitherObjDouble} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjDouble} which present value is the left value.
     */
    public static <L> EitherObjDouble<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjDouble} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjDouble} which present value is the right value.
     */
    public static <L> EitherObjDouble<L> right(double right) {
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
    public abstract double getRight();

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, DoubleConsumer rightConsumer);

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
    public abstract void ifRight(DoubleConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherObjDouble}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjDouble} instance with mapped values.
     */
    public abstract <ML> EitherObjDouble<ML> map(Function<? super L, ? extends ML> leftMapper,
                                                          DoubleUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjDouble} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjDouble} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjDouble} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjDouble<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjDouble} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjDouble} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjDouble} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjDouble<L> mapRight(DoubleUnaryOperator rightMapper);

    static class Left<L> extends EitherObjDouble<L> {
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
        public double getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, DoubleConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
        }

        @Override
        public <ML> EitherObjDouble<ML> map(Function<? super L, ? extends ML> leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjDouble<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjDouble.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjDouble<L> mapRight(DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.left(this.getLeft());
        }
    }

    static class Right<L> extends EitherObjDouble<L> {
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
        public L getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public double getRight() {
            return this.value;
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, DoubleConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(DoubleConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjDouble<ML> map(Function<? super L, ? extends ML> leftMapper, DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjDouble<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjDouble.right(this.getRight());
        }

        @Override
        public EitherObjDouble<L> mapRight(DoubleUnaryOperator rightMapper) {
            return EitherObjDouble.right(rightMapper.apply(this.getRight()));
        }
    }
}
