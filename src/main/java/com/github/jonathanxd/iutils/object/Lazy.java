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
public class Lazy<T> {

    private final Supplier<T> supplier;
    private boolean isEvaluated = false;
    private volatile T value = null;

    Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }


    /**
     * Creates a lazy from a {@link Future} instance.
     *
     * @param future Future instance.
     * @param <T>    Type of value.
     * @return {@link Lazy} with a {@link Future} wrapped in a {@link Supplier}.
     */
    public static <T> Lazy<T> lazy(Future<T> future) {
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
    public static <T> Lazy<T> lazy(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    /**
     * Returns a wrapper to async evaluation. Read {@link Async}.
     *
     * @param lazy Lazy instance.
     * @param <T>  Value type.
     * @return {@link Async} wrapper.
     */
    public static <T> Async<T> async(Lazy<T> lazy) {
        return new Async<>(lazy);
    }

    /**
     * Creates a evaluated {@link Lazy}.
     *
     * @param value Evaluated value.
     * @param <T>   Value type.
     * @return Evaluated {@link Lazy}.
     */
    public static <T> Lazy<T> evaluated(T value) {
        Lazy<T> lazy = new Lazy<>(() -> value);
        lazy.isEvaluated = true;
        lazy.value = value;

        return lazy;
    }

    /**
     * Gets the value.
     *
     * @return Value.
     */
    public T get() {
        if (!this.isEvaluated) {
            this.value = this.supplier.get();
            this.isEvaluated = true;
        }

        return this.value;
    }

    /**
     * Returns true if this lazy instance is already evaluated.
     *
     * @return True if this lazy instance is already evaluated.
     */
    public boolean isEvaluated() {
        return this.isEvaluated;
    }

    /**
     * Creates a copy of {@code this} {@link Lazy}.
     *
     * Note that returned {@link Lazy} instance uses the same {@link #supplier} as {@code this}
     * {@link Lazy} instance.
     *
     * @param evaluated If true, creates a copy of evaluated lazy, if not, creates a non evaluated
     *                  lazy.
     * @return A copy of {@code this} {@link Lazy}.
     */
    public Lazy<T> copy(boolean evaluated) {
        Lazy<T> lazy = new Lazy<>(this.supplier);

        lazy.isEvaluated = evaluated;
        lazy.value = this.value;

        return lazy;
    }

    /**
     * Creates a copy of this {@link Lazy} with same state.
     *
     * @return Copy of this {@link Lazy} with same state.
     */
    public Lazy<T> copy() {
        Lazy<T> lazy = new Lazy<>(this.supplier);

        lazy.isEvaluated = this.isEvaluated();
        lazy.value = this.value;

        return lazy;
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
            super(null);
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

        /**
         * Returns true if is evaluating the operation asynchronously.
         *
         * @return True if is evaluating the operation asynchronously.
         */
        public synchronized boolean isEvaluating() {
            return this.isEvaluating;
        }

        @Override
        public Lazy<T> copy(boolean evaluated) {
            Lazy<T> copy = this.wrapped.copy(evaluated);

            return new Async<>(copy);
        }
    }
}
