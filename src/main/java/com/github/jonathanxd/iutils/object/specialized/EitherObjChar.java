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

import com.github.jonathanxd.iutils.function.consumer.CharConsumer;
import com.github.jonathanxd.iutils.function.function.CharFunction;
import com.github.jonathanxd.iutils.function.supplier.CharSupplier;
import com.github.jonathanxd.iutils.function.unary.CharUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class which can hold either {@link L} or {@link char} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
public abstract class EitherObjChar<L> extends BaseEither {

    EitherObjChar() {
    }

    /**
     * Creates a {@link EitherObjChar} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjChar} which present value is the left value.
     */
    public static <L> EitherObjChar<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjChar} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjChar} which present value is the right value.
     */
    public static <L> EitherObjChar<L> right(char right) {
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
     * Returns left value or {@code value} if this is a {@link EitherObjChar#right(char)}.
     *
     * @param value Value to return if this is a {@link EitherObjChar#right(char)}
     * @return Left value or {@code value} if this is a {@link EitherObjChar#right(char)}.
     */
    public abstract L leftOr(L value);

    /**
     * Returns left value or {@code null} if this is a {@link EitherObjChar#right(char)}.
     *
     * @return Left value or {@code null} if this is a {@link EitherObjChar#right(char)}.
     */
    public final L leftOrNull() {
        return this.leftOr(null);
    }

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjChar#right(char)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjChar#right(char)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjChar#right(char)}.
     */
    public abstract L leftOrGet(Supplier<L> supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract char getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherObjChar#left(Object)}.
     *
     * @param value Value to return if this is a {@link EitherObjChar#left(Object)}
     * @return Right value or {@code value} if this is a {@link EitherObjChar#left(Object)}.
     */
    public abstract char rightOr(char value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjChar#left(Object)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjChar#left(Object)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjChar#left(Object)}.
     */
    public abstract char rightOrGet(CharSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final L component1() {
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
    public abstract void ifEither(Consumer<? super L> leftConsumer, CharConsumer rightConsumer);

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
    public abstract void ifRight(CharConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherObjChar}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjChar} instance with mapped values.
     */
    public abstract <ML> EitherObjChar<ML> map(Function<? super L, ? extends ML> leftMapper,
                                               CharUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjChar} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjChar} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjChar} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjChar<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjChar} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjChar} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjChar} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjChar<L> mapRight(CharUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherObjChar}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjChar} returned by mapper function.
     */
    public abstract <ML> EitherObjChar<ML> flatMap(Function<? super L, ? extends EitherObjChar<ML>> leftMapper,
                                                   CharFunction<? extends EitherObjChar<ML>> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherObjChar} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjChar} returned by mapper function.
     */
    public abstract <ML> EitherObjChar<ML> flatMapLeft(Function<? super L, ? extends EitherObjChar<ML>> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherObjChar} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjChar} returned by mapper function.
     */
    public abstract EitherObjChar<L> flatMapRight(CharFunction<? extends EitherObjChar<L>> rightMapper);

    static class Left<L> extends EitherObjChar<L> {
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
        public L leftOr(L value) {
            return this.getLeft();
        }

        @Override
        public L leftOrGet(Supplier<L> supplier) {
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
        public void ifEither(Consumer<? super L> leftConsumer, CharConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(CharConsumer consumer) {
        }

        @Override
        public <ML> EitherObjChar<ML> map(Function<? super L, ? extends ML> leftMapper,
                                          CharUnaryOperator rightMapper) {
            return EitherObjChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjChar<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjChar.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjChar<L> mapRight(CharUnaryOperator rightMapper) {
            return EitherObjChar.left(this.getLeft());
        }

        @Override
        public <ML> EitherObjChar<ML> flatMap(Function<? super L, ? extends EitherObjChar<ML>> leftMapper,
                                              CharFunction<? extends EitherObjChar<ML>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> EitherObjChar<ML> flatMapLeft(Function<? super L, ? extends EitherObjChar<ML>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherObjChar<L> flatMapRight(CharFunction<? extends EitherObjChar<L>> rightMapper) {
            return EitherObjChar.left(this.getLeft());
        }

    }

    static class Right<L> extends EitherObjChar<L> {
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
        public L getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public L leftOr(L value) {
            return value;
        }

        @Override
        public L leftOrGet(Supplier<L> supplier) {
            return supplier.get();
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
        public void ifEither(Consumer<? super L> leftConsumer, CharConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(CharConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjChar<ML> map(Function<? super L, ? extends ML> leftMapper,
                                          CharUnaryOperator rightMapper) {
            return EitherObjChar.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjChar<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjChar.right(this.getRight());
        }

        @Override
        public EitherObjChar<L> mapRight(CharUnaryOperator rightMapper) {
            return EitherObjChar.right(rightMapper.apply(this.getRight()));
        }


        @Override
        public <ML> EitherObjChar<ML> flatMap(Function<? super L, ? extends EitherObjChar<ML>> leftMapper,
                                              CharFunction<? extends EitherObjChar<ML>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> EitherObjChar<ML> flatMapLeft(Function<? super L, ? extends EitherObjChar<ML>> leftMapper) {
            return EitherObjChar.right(this.getRight());
        }

        @Override
        public EitherObjChar<L> flatMapRight(CharFunction<? extends EitherObjChar<L>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

    }
}
