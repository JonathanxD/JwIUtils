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
package com.github.jonathanxd.iutils.object;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A class which can hold either {@link L} or {@link R} (in this documentation we call the hold
 * value as present value).
 *
 * Left value ({@link L}) and right value ({@link R}) may be null even if it is the present value.
 *
 * @param <L> Left value.
 * @param <R> Right value.
 */
public abstract class Either<L, R> {

    Either() {
    }

    /**
     * Creates a {@link Either} which present value is the left value.
     *
     * @param left Left value.
     * @param <L>  Left type.
     * @param <R>  Right type.
     * @return {@link Either} which present value is the left value.
     */
    public static <L, R> Either<L, R> left(L left) {
        return new Left<>(left);
    }

    /**
     * Creates a {@link Either} which present value is the right value.
     *
     * @param right Right value.
     * @param <L>   Left type.
     * @param <R>   Right type.
     * @return {@link Either} which present value is the right value.
     */
    public static <L, R> Either<L, R> right(R right) {
        return new Right<>(right);
    }

    /**
     * Returns true if left value is the present value.
     *
     * @return True if left value is the present value.
     */
    public abstract boolean isLeft();

    /**
     * Returns true if right value is the present value.
     *
     * @return True if right value is the present value.
     */
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
    public abstract R getRight();

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public abstract void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer);

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
    public abstract void ifRight(Consumer<? super R> consumer);

    /**
     * Maps left value if present and right value if present and return a new {@link Either}
     * instance with mapped values.
     *
     * @param leftMapper  Left value mapper.
     * @param rightMapper Right value mapper.
     * @param <ML>        Left type.
     * @param <MR>        Right type.
     * @return {@link Either} instance with mapped values.
     */
    public abstract <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper,
                                                Function<? super R, ? extends MR> rightMapper);


    /**
     * Maps left value if present and return a new {@link Either} with mapped value.
     *
     * If left value is not present, a new identical {@link Either} will be returned.
     *
     * @param leftMapper Left value mapper.
     * @param <ML>       Left type.
     * @return {@link Either} instance with mapped left value.
     */
    @SuppressWarnings("unchecked")
    public abstract <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper);

    /**
     * Maps right value if present and return a new {@link Either} with mapped value.
     *
     * If right value is not present, a new identical {@link Either} will be returned.
     *
     * @param rightMapper Right value mapper.
     * @param <MR>        Right type.
     * @return {@link Either} instance with mapped right value.
     */
    @SuppressWarnings("unchecked")
    public abstract <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper);

    static class Left<L, R> extends Either<L, R> {
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
        public R getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
            leftConsumer.accept(this.getLeft());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.getLeft());
        }

        @Override
        public void ifRight(Consumer<? super R> consumer) {
        }

        @Override
        public <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper, Function<? super R, ? extends MR> rightMapper) {
            return Either.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return Either.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper) {
            return Either.left(this.getLeft());
        }
    }

    static class Right<L, R> extends Either<L, R> {
        private final R value;

        Right(R value) {
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
        public R getRight() {
            return this.value;
        }

        @Override
        public void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
            rightConsumer.accept(this.getRight());
        }

        @Override
        public void ifLeft(Consumer<? super L> consumer) {
        }

        @Override
        public void ifRight(Consumer<? super R> consumer) {
            consumer.accept(this.getRight());
        }

        @Override
        public <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper, Function<? super R, ? extends MR> rightMapper) {
            return Either.right(rightMapper.apply(this.getRight()));
        }

        @Override
        public <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper) {
            return Either.right(this.getRight());
        }

        @Override
        public <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper) {
            return Either.right(rightMapper.apply(this.getRight()));
        }
    }
}
