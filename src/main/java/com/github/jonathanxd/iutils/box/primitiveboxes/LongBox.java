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
package com.github.jonathanxd.iutils.box.primitiveboxes;

import com.github.jonathanxd.iutils.box.BaseBox;
import com.github.jonathanxd.iutils.box.MutableBox;
import com.github.jonathanxd.iutils.box.UnknownBox;

/**
 * Improved to {@link long} primitive type
 */
public class LongBox implements UnknownBox<Long> {

    /**
     * Value
     */
    private long d;

    /**
     * Creates a long box with {@code 0L} as initial value.
     */
    public LongBox() {
        this(0L);
    }

    /**
     * Creates a long box with {@code d} as initial value.
     *
     * @param d Initial value.
     */
    public LongBox(long d) {
        this.d = d;
    }

    /**
     * Creates a long box with {@code d} as initial value.
     *
     * @param d Initial value.
     * @return Long box with {@code d} as initial value.
     */
    public static LongBox of(long d) {
        return new LongBox(d);
    }

    /**
     * Applies arithmetic operation addition ({@code +}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation addition ({@code +}).
     */
    public void add(long d) {
        this.set(this.get() + d);
    }

    /**
     * Applies arithmetic operation addition ({@code +}) to the current value with {@code 1} as
     * right operand.
     */
    public void add() {
        this.add(1);
    }

    /**
     * Applies arithmetic operation subtraction ({@code -}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation subtraction ({@code -}).
     */
    public void remove(long d) {
        this.set(this.get() - d);
    }

    /**
     * Applies arithmetic operation subtraction ({@code -}) to the current value with {@code 1} as
     * right operand.
     */
    public void remove() {
        this.remove(1);
    }

    /**
     * Applies arithmetic operation multiplication ({@code *}) to the current value with {@code d}
     * as right operand.
     *
     * @param d Right operand of arithmetic operation multiplication ({@code *}).
     */
    public void multiply(long d) {
        this.set(this.get() * d);
    }

    /**
     * Applies arithmetic operation division ({@code /}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation division ({@code /}).
     */
    public void divide(long d) {
        this.set(this.get() / d);
    }

    /**
     * Applies arithmetic operation power ({@code ^}) to the current value with {@code d} as right
     * operand.
     *
     * @param d Right operand of arithmetic operation power ({@code ^}).
     */
    public void pow(long d) {
        this.set((long) Math.pow(this.get(), d));
    }

    /**
     * Applies arithmetic operation square root ({@code √}) to the current value.
     */
    public void sqrt() {
        this.set((long) Math.sqrt(this.get()));
    }

    /**
     * Applies arithmetic operation cube root ({@code ∛}) to the current value.
     */
    public void cbrt() {
        this.set((long) Math.cbrt(this.get()));
    }

    /**
     * Sets the long value to {@code d}.
     *
     * @param d New long value.
     */
    public void set(long d) {
        this.d = d;
    }

    /**
     * Gets the long value.
     *
     * @return Current long value.
     */
    public long get() {
        return this.d;
    }

    @Override
    public BaseBox<Long> box() {
        return MutableBox.of(d);
    }

    @Override
    public Class<?> type() {
        return Long.TYPE;
    }
}
