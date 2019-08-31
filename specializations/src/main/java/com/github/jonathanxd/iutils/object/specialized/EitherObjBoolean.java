/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.function.consumer.BooleanConsumer;
import com.github.jonathanxd.iutils.function.function.BooleanFunction;
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class which can hold either {@link L} or {@link boolean} (in this documentation we call the
 * hold value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
public abstract class EitherObjBoolean<L> extends BaseEither {

    EitherObjBoolean() {
    }

    /**
     * Creates a {@link EitherObjBoolean} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjBoolean} which present value is the left value.
     */
    public static <L> EitherObjBoolean<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjBoolean} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjBoolean} which present value is the right value.
     */
    public static <L> EitherObjBoolean<L> right(boolean right) {
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
     * Returns left value or {@code value} if this is a {@link EitherObjBoolean#right(boolean)}.
     *
     * @param value Value to return if this is a {@link EitherObjBoolean#right(boolean)}
     * @return Left value or {@code value} if this is a {@link EitherObjBoolean#right(boolean)}.
     */
    public abstract L leftOr(L value);

    /**
     * Returns left value or {@code null} if this is a {@link EitherObjBoolean#right(boolean)}.
     *
     * @return Left value or {@code null} if this is a {@link EitherObjBoolean#right(boolean)}.
     */
    public final L leftOrNull() {
        return this.leftOr(null);
    }

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjBoolean#right(boolean)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjBoolean#right(boolean)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjBoolean#right(boolean)}.
     */
    public abstract L leftOrGet(Supplier<L> supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract boolean getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherObjBoolean#left(Object)}.
     *
     * @param value Value to return if this is a {@link EitherObjBoolean#left(Object)}
     * @return Right value or {@code value} if this is a {@link EitherObjBoolean#left(Object)}.
     */
    public abstract boolean rightOr(boolean value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjBoolean#left(Object)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjBoolean#left(Object)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjBoolean#left(Object)}.
     */
    public abstract boolean rightOrGet(BooleanSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final L component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final boolean component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, BooleanConsumer rightConsumer);

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
    public abstract void ifRight(BooleanConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link
     * EitherObjBoolean} instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjBoolean} instance with mapped values.
     */
    public abstract <ML> EitherObjBoolean<ML> map(Function<? super L, ? extends ML> leftMapper,
                                                  BooleanUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjBoolean} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjBoolean} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjBoolean} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjBoolean<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjBoolean} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjBoolean} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjBoolean} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjBoolean<L> mapRight(BooleanUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherObjBoolean}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjBoolean} returned by mapper function.
     */
    public abstract <ML> EitherObjBoolean<ML> flatMap(Function<? super L, ? extends EitherObjBoolean<ML>> leftMapper,
                                                      BooleanFunction<? extends EitherObjBoolean<ML>> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherObjBoolean} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjBoolean} returned by mapper function.
     */
    public abstract <ML> EitherObjBoolean<ML> flatMapLeft(Function<? super L, ? extends EitherObjBoolean<ML>> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherObjBoolean} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjBoolean} returned by mapper function.
     */
    public abstract EitherObjBoolean<L> flatMapRight(BooleanFunction<? extends EitherObjBoolean<L>> rightMapper);

    static class Left<L> extends EitherObjBoolean<L> {
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
        public boolean getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean rightOr(boolean value) {
            return value;
        }

        @Override
        public boolean rightOrGet(BooleanSupplier supplier) {
            return supplier.getAsBoolean();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, BooleanConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(BooleanConsumer consumer) {
        }

        @Override
        public <ML> EitherObjBoolean<ML> map(Function<? super L, ? extends ML> leftMapper,
                                             BooleanUnaryOperator rightMapper) {
            return EitherObjBoolean.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjBoolean<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjBoolean.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjBoolean<L> mapRight(BooleanUnaryOperator rightMapper) {
            return EitherObjBoolean.left(this.getLeft());
        }

        @Override
        public <ML> EitherObjBoolean<ML> flatMap(Function<? super L, ? extends EitherObjBoolean<ML>> leftMapper,
                                                 BooleanFunction<? extends EitherObjBoolean<ML>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> EitherObjBoolean<ML> flatMapLeft(Function<? super L, ? extends EitherObjBoolean<ML>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherObjBoolean<L> flatMapRight(BooleanFunction<? extends EitherObjBoolean<L>> rightMapper) {
            return EitherObjBoolean.left(this.getLeft());
        }

    }

    static class Right<L> extends EitherObjBoolean<L> {
        private final boolean value;

        Right(boolean value) {
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
        public boolean getRight() {
            return this.value;
        }

        @Override
        public boolean rightOr(boolean value) {
            return this.getRight();
        }

        @Override
        public boolean rightOrGet(BooleanSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, BooleanConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(BooleanConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjBoolean<ML> map(Function<? super L, ? extends ML> leftMapper,
                                             BooleanUnaryOperator rightMapper) {
            return EitherObjBoolean.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjBoolean<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjBoolean.right(this.getRight());
        }

        @Override
        public EitherObjBoolean<L> mapRight(BooleanUnaryOperator rightMapper) {
            return EitherObjBoolean.right(rightMapper.apply(this.getRight()));
        }


        @Override
        public <ML> EitherObjBoolean<ML> flatMap(Function<? super L, ? extends EitherObjBoolean<ML>> leftMapper,
                                                 BooleanFunction<? extends EitherObjBoolean<ML>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> EitherObjBoolean<ML> flatMapLeft(Function<? super L, ? extends EitherObjBoolean<ML>> leftMapper) {
            return EitherObjBoolean.right(this.getRight());
        }

        @Override
        public EitherObjBoolean<L> flatMapRight(BooleanFunction<? extends EitherObjBoolean<L>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

    }
}
