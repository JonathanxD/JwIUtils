/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.function.unary.LongUnaryOperator;
import com.github.jonathanxd.iutils.object.BaseEither;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * A class which can hold either {@link L} or {@link long} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) may be null even if the value is present.
 *
 * @param <L> Left value.
 */
public abstract class EitherObjLong<L> extends BaseEither {

    EitherObjLong() {
    }

    /**
     * Creates a {@link EitherObjLong} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @return {@link EitherObjLong} which present value is the left value.
     */
    public static <L> EitherObjLong<L> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link EitherObjLong} which present value is the right value.
     *
     * @param right Right value.
     * @return {@link EitherObjLong} which present value is the right value.
     */
    public static <L> EitherObjLong<L> right(long right) {
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
     * Returns left value or {@code value} if this is a {@link EitherObjLong#right(long)}.
     *
     * @param value Value to return if this is a {@link EitherObjLong#right(long)}
     * @return Left value or {@code value} if this is a {@link EitherObjLong#right(long)}.
     */
    public abstract L leftOr(L value);

    /**
     * Returns left value or {@code null} if this is a {@link EitherObjLong#right(long)}.
     *
     * @return Left value or {@code null} if this is a {@link EitherObjLong#right(long)}.
     */
    public final L leftOrNull() {
        return this.leftOr(null);
    }

    /**
     * Returns left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjLong#right(long)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjLong#right(long)}
     * @return Left value or value supplied by {@code supplier} if this is a {@link
     * EitherObjLong#right(long)}.
     */
    public abstract L leftOrGet(Supplier<L> supplier);

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public abstract long getRight();

    /**
     * Returns right value or {@code value} if this is a {@link EitherObjLong#left(Object)}.
     *
     * @param value Value to return if this is a {@link EitherObjLong#left(Object)}
     * @return Right value or {@code value} if this is a {@link EitherObjLong#left(Object)}.
     */
    public abstract long rightOr(long value);

    /**
     * Returns right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjLong#left(Object)}.
     *
     * @param supplier Supplier of value to return if this is a {@link EitherObjLong#left(Object)}
     * @return Right value or value supplied by {@code supplier} if this is a {@link
     * EitherObjLong#left(Object)}.
     */
    public abstract long rightOrGet(LongSupplier supplier);

    /**
     * Left value. (Kotlin compatibility purpose)
     */
    public final L component1() {
        return this.getLeft();
    }

    /**
     * Right value. (Kotlin compatibility purpose)
     */
    public final long component2() {
        return this.getRight();
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, LongConsumer rightConsumer);

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
    public abstract void ifRight(LongConsumer consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link EitherObjLong}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjLong} instance with mapped values.
     */
    public abstract <ML> EitherObjLong<ML> map(Function<? super L, ? extends ML> leftMapper,
                                               LongUnaryOperator rightMapper);


    /**
     * Maps left value if present and return a new {@link EitherObjLong} with mapped value.
     *
     * If left value is not present, a new identical {@link EitherObjLong} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjLong} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> EitherObjLong<ML> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link EitherObjLong} with mapped value.
     *
     * If right value is not present, a new identical {@link EitherObjLong} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjLong} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract EitherObjLong<L> mapRight(LongUnaryOperator rightMapper);

    /**
     * Flat maps left value if present or right value if present and return {@link EitherObjLong}
     * returned by mapper function.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @return {@link EitherObjLong} returned by mapper function.
     */
    public abstract <ML> EitherObjLong<ML> flatMap(Function<? super L, ? extends EitherObjLong<ML>> leftMapper,
                                                   LongFunction<? extends EitherObjLong<ML>> rightMapper);

    /**
     * Flat maps left value if present return {@link EitherObjLong} returned by mapper function.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link EitherObjLong} returned by mapper function.
     */
    public abstract <ML> EitherObjLong<ML> flatMapLeft(Function<? super L, ? extends EitherObjLong<ML>> leftMapper);


    /**
     * Flat maps right value if present and return {@link EitherObjLong} returned by mapper
     * function.
     *
     * @param rightMapper Right value mapper.
     * @return {@link EitherObjLong} returned by mapper function.
     */
    public abstract EitherObjLong<L> flatMapRight(LongFunction<? extends EitherObjLong<L>> rightMapper);

    static class Left<L> extends EitherObjLong<L> {
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
        public long getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public long rightOr(long value) {
            return value;
        }

        @Override
        public long rightOrGet(LongSupplier supplier) {
            return supplier.getAsLong();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, LongConsumer rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(LongConsumer consumer) {
        }

        @Override
        public <ML> EitherObjLong<ML> map(Function<? super L, ? extends ML> leftMapper,
                                          LongUnaryOperator rightMapper) {
            return EitherObjLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> EitherObjLong<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjLong.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public EitherObjLong<L> mapRight(LongUnaryOperator rightMapper) {
            return EitherObjLong.left(this.getLeft());
        }

        @Override
        public <ML> EitherObjLong<ML> flatMap(Function<? super L, ? extends EitherObjLong<ML>> leftMapper,
                                              LongFunction<? extends EitherObjLong<ML>> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public <ML> EitherObjLong<ML> flatMapLeft(Function<? super L, ? extends EitherObjLong<ML>> leftMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public EitherObjLong<L> flatMapRight(LongFunction<? extends EitherObjLong<L>> rightMapper) {
            return EitherObjLong.left(this.getLeft());
        }

    }

    static class Right<L> extends EitherObjLong<L> {
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
        public long getRight() {
            return this.value;
        }

        @Override
        public long rightOr(long value) {
            return this.getRight();
        }

        @Override
        public long rightOrGet(LongSupplier supplier) {
            return this.getRight();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, LongConsumer rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(LongConsumer consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML> EitherObjLong<ML> map(Function<? super L, ? extends ML> leftMapper,
                                          LongUnaryOperator rightMapper) {
            return EitherObjLong.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> EitherObjLong<ML> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return EitherObjLong.right(this.getRight());
        }

        @Override
        public EitherObjLong<L> mapRight(LongUnaryOperator rightMapper) {
            return EitherObjLong.right(rightMapper.apply(this.getRight()));
        }


        @Override
        public <ML> EitherObjLong<ML> flatMap(Function<? super L, ? extends EitherObjLong<ML>> leftMapper,
                                              LongFunction<? extends EitherObjLong<ML>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

        @Override
        public <ML> EitherObjLong<ML> flatMapLeft(Function<? super L, ? extends EitherObjLong<ML>> leftMapper) {
            return EitherObjLong.right(this.getRight());
        }

        @Override
        public EitherObjLong<L> flatMapRight(LongFunction<? extends EitherObjLong<L>> rightMapper) {
            return rightMapper.apply(this.getRight());
        }

    }
}
