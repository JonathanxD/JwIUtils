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
public final class Either<L, R> {

    private final int r;
    private final L left;
    private final R right;

    Either(int r, L left, R right) {
        this.r = r;
        this.left = left;
        this.right = right;
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
        return new Either<>(0, left, null);
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
        return new Either<>(1, null, right);
    }

    /**
     * Returns true if left value is the present value.
     *
     * @return True if left value is the present value.
     */
    public boolean isLeft() {
        return this.r == 0;
    }

    /**
     * Returns true if right value is the present value.
     *
     * @return True if right value is the present value.
     */
    public boolean isRight() {
        return this.r == 1;
    }

    /**
     * Gets left value.
     *
     * @return Left value.
     * @throws NoSuchElementException If the left value is not present.
     */
    public L getLeft() {
        if (!this.isLeft())
            throw new NoSuchElementException();

        return this.left;
    }

    /**
     * Gets right value.
     *
     * @return Right value.
     * @throws NoSuchElementException If the right value is not present.
     */
    public R getRight() {
        if (!this.isRight())
            throw new NoSuchElementException();

        return this.right;
    }

    /**
     * Consumes the left value with {@code leftConsumer} if the value is present, or consumes the
     * right value with {@code rightConsumer} (which must be present if left value is not).
     *
     * @param leftConsumer  Left value consumer.
     * @param rightConsumer Right value consumer.
     */
    public void ifEither(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
        if (this.isLeft())
            leftConsumer.accept(this.getLeft());
        else
            rightConsumer.accept(this.getRight());
    }

    /**
     * Consume left value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public void ifLeft(Consumer<? super L> consumer) {
        if (this.isLeft())
            consumer.accept(this.getLeft());
    }

    /**
     * Consume right value if the value is present.
     *
     * @param consumer Consumer to consume value.
     */
    public void ifRight(Consumer<? super R> consumer) {
        if (this.isRight())
            consumer.accept(this.getRight());
    }

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
    public <ML, MR> Either<ML, MR> map(Function<? super L, ? extends ML> leftMapper, Function<? super R, ? extends MR> rightMapper) {
        if (this.isLeft())
            return Either.left(leftMapper.apply(this.getLeft()));

        return Either.right(rightMapper.apply(this.getRight()));
    }


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
    public <ML> Either<ML, R> mapLeft(Function<? super L, ? extends ML> leftMapper) {
        if (this.isLeft())
            return Either.left(leftMapper.apply(this.getLeft()));

        return Either.right(this.getRight());
    }

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
    public <MR> Either<L, MR> mapRight(Function<? super R, ? extends MR> rightMapper) {
        if (this.isRight())
            return Either.right(rightMapper.apply(this.getRight()));

        return Either.left(this.getLeft());
    }

}
