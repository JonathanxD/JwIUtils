/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.condition.Conditions;
import com.github.jonathanxd.iutils.exception.LazyEvaluationException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * Lazy evaluation.
 *
 * @param <T> Type of value.
 */
public abstract class Lazy<T> {

    Lazy() {
    }


    /**
     * Creates a lazy from a {@link Future} instance.
     *
     * @param future Future instance.
     * @param <T>    Type of value.
     * @return {@link Lazy} with a {@link Future} wrapped in a {@link Supplier}.
     */
    @NotNull
    public static <T> Lazy<T> lazy(@NotNull Future<T> future) {
        return Lazy.lazy(() -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new LazyEvaluationException(e);
            }
        });
    }

    /**
     * Creates a {@link Lazy} from a supplier.
     *
     * @param supplier Value supplier.
     * @param <T>      Value type.
     * @return {@link Lazy} with {@code supplier} as evaluator.
     */
    @NotNull
    public static <T> Lazy<T> lazy(@NotNull Supplier<T> supplier) {
        return new Supplied<>(supplier);
    }

    /**
     * Returns a wrapper to asynchronous evaluation. Read {@link Async}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Value type.
     * @return {@link Async} wrapper.
     */
    @NotNull
    public static <T> Async<T> async(@NotNull Lazy<T> lazy) {
        if (lazy instanceof Async<?>)
            return (Async<T>) lazy;
        return new Async<>(lazy);
    }

    /**
     * Returns a wrapper of {@code lazy} that does not return a null evaluated value (throwing
     * exception if result is null).
     *
     * @param lazy Lazy instance.
     * @param <T>  Value type.
     * @return {@link NonNullLazy} wrapper.
     */
    @NotNull
    public static <T> NonNullLazy<T> nonNull(@NotNull Lazy<T> lazy) {
        if (lazy instanceof NonNullLazy<?>)
            return (NonNullLazy<T>) lazy;

        return new NonNullLazy<>(lazy);
    }

    /**
     * Creates a evaluated {@link Lazy}.
     *
     * @param value Evaluated value.
     * @param <T>   Value type.
     * @return Evaluated {@link Lazy}.
     */
    @NotNull
    public static <T> Lazy<T> evaluated(@Nullable T value) {
        return new Evaluated<>(value);
    }

    /**
     * Gets the value.
     *
     * @return Value.
     */
    @Nullable
    public abstract T get();

    /**
     * Returns true if this lazy instance is already evaluated.
     *
     * @return True if this lazy instance is already evaluated.
     */
    public abstract boolean isEvaluated();

    /**
     * Sets this lazy to evaluated state.
     *
     * @param eval  Evaluated state.
     * @param value Evaluated value.
     */
    protected abstract void setEvaluated(boolean eval, @Nullable T value);

    /**
     * Creates a copy of {@code this} {@link Lazy}.
     *
     * @param evaluated If true, creates a copy of evaluated lazy, if not, creates a non evaluated
     *                  lazy.
     * @return A copy of {@code this} {@link Lazy}.
     */
    @NotNull
    public abstract Lazy<T> copy(boolean evaluated);

    /**
     * Creates a copy of this {@link Lazy} with same state.
     *
     * @return Copy of this {@link Lazy} with same state.
     */
    @NotNull
    public abstract Lazy<T> copy();

    public static class Evaluated<T> extends Lazy<T> {

        private final T value;

        public Evaluated(T value) {
            this.value = value;
        }

        @Nullable
        @Override
        public T get() {
            return this.value;
        }

        @Override
        public boolean isEvaluated() {
            return true;
        }

        @Override
        protected void setEvaluated(boolean eval, @Nullable T value) {
        }

        @NotNull
        @Override
        public Lazy<T> copy(boolean evaluated) {
            return this;
        }

        @NotNull
        @Override
        public Lazy<T> copy() {
            return this;
        }
    }

    public static class Supplied<T> extends Lazy<T> {

        @NotNull
        private final Supplier<T> supplier;
        private boolean isEvaluated = false;
        @Nullable
        private volatile T value = null;

        Supplied(@NotNull Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public @Nullable T get() {
            if (!this.isEvaluated) {
                this.value = this.supplier.get();
                this.isEvaluated = true;
            }

            return this.value;
        }

        @Override
        public boolean isEvaluated() {
            return this.isEvaluated;
        }

        @Override
        protected void setEvaluated(boolean eval, @Nullable T value) {
            this.isEvaluated = eval;
            this.value = value;
        }

        @NotNull
        @Override
        public Lazy<T> copy(boolean evaluated) {
            Lazy<T> lazy = new Supplied<>(this.supplier);

            lazy.setEvaluated(evaluated, this.value);

            return lazy;
        }

        @NotNull
        @Override
        public Lazy<T> copy() {
            Lazy<T> lazy = new Supplied<>(this.supplier);

            lazy.setEvaluated(this.isEvaluated, this.value);

            return lazy;
        }
    }

    /**
     * Async capable {@link Lazy} evaluation. This class wraps a {@link Lazy#copy(boolean) copy} of
     * a non-async capable {@link Lazy}.
     *
     * This class is thread-safe and provide helper methods to start evaluation asynchronously.
     * {@link #get() get method} remains evaluating on call site, but if an asynchronous evaluation
     * is running, the method will wait the evaluation to finish.
     *
     * Note that if wrapped {@link Lazy} is already evaluated no async evaluation will be executed.
     *
     * @param <T> Type of value.
     */
    public static class Async<T> extends Lazy<T> {

        private static final Executor EXECUTOR = Executors.newCachedThreadPool();
        private final Lazy<T> wrapped;
        private volatile boolean isEvaluating = false;

        Async(Lazy<T> lazy) {
            this.wrapped = lazy.copy(false);
            Conditions.require(!(lazy instanceof Lazy.Async<?>), "Cannot wrap an AsyncLazy into another.");
        }

        /**
         * Starts evaluation asynchronously. (Only if the operation is not evaluated).
         *
         * Uses cached {@link Executor}.
         */
        public void evaluateAsync() {
            this.evaluateAsync(Async.EXECUTOR);
        }

        /**
         * Starts evaluation asynchronously. (Only if the operation is not evaluated).
         *
         * @param executor Executor to use to run the code asynchronously.
         */
        public void evaluateAsync(Executor executor) {
            if (this.isEvaluated())
                return;

            executor.execute(() -> {
                this.isEvaluating = true;
                this.wrapped.get();
                this.isEvaluating = false;
            });
        }

        @Override
        public T get() {

            while (this.isEvaluating) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new LazyEvaluationException("Thread interrupted when waiting async lazy evaluation to finishes.", e);
                }
            }

            return this.wrapped.get();
        }

        @Override
        public boolean isEvaluated() {
            return this.wrapped.isEvaluated();
        }

        @Override
        protected void setEvaluated(boolean eval, @Nullable T value) {
            this.wrapped.setEvaluated(eval, value);
        }

        /**
         * Returns true if is evaluating the operation asynchronously.
         *
         * @return True if is evaluating the operation asynchronously.
         */
        public synchronized boolean isEvaluating() {
            return this.isEvaluating;
        }

        @NotNull
        @Override
        public Lazy<T> copy(boolean evaluated) {
            Lazy<T> copy = this.wrapped.copy(evaluated);

            return new Async<>(copy);
        }

        @NotNull
        @Override
        public Lazy<T> copy() {
            Lazy<T> copy = this.wrapped.copy();
            return new Async<>(copy);
        }
    }
}
