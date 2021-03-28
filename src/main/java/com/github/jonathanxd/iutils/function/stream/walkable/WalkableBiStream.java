/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.function.stream.walkable;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.function.stream.BiStream;

/**
 * Abstract simple implementation of {@link BiStream} that holds a {@link Walkable}.
 *
 * @param <T> First value type.
 * @param <U> Second value type.
 * @param <W> Walkable type.
 */
public abstract class WalkableBiStream<T, U, W extends Walkable> implements BiStream<T, U> {

    private final W walkable;
    private final Runnable closeRunnable;

    protected WalkableBiStream(W walkable) {
        this.walkable = walkable;
        this.closeRunnable = null;
    }

    protected WalkableBiStream(W walkable, Runnable closeRunnable) {
        this.walkable = walkable;
        this.closeRunnable = closeRunnable;
    }

    /**
     * Always a sequential stream, if you want a parallel stream use the {@link
     * com.github.jonathanxd.iutils.function.stream.BiJavaStream} that wraps and delegates
     * operations to a {@link java.util.stream.Stream Java Stream}.
     *
     * {@inheritDoc}
     */
    @Override
    public boolean isParallel() {
        return false;
    }

    protected W getWalkable() {
        return this.walkable;
    }

    protected void updateState() {
        if (!this.getWalkable().hasNext())
            this.close();
    }

    @Override
    public void close() {

        if (this.getWalkable().hasNext())
            this.getWalkable().walkToEnd();

        if (this.closeRunnable != null)
            this.closeRunnable.run();
    }
}
