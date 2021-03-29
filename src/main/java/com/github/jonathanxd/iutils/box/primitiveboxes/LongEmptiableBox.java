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
package com.github.jonathanxd.iutils.box.primitiveboxes;

import com.github.jonathanxd.iutils.box.BaseBox;
import com.github.jonathanxd.iutils.box.MutableBox;
import com.github.jonathanxd.iutils.box.UnknownEmptiableBox;

/**
 * Improved to {@link long} primitive type
 */
public class LongEmptiableBox implements UnknownEmptiableBox<Long> {

    /**
     * Value
     */
    private long d;

    private boolean empty;

    /**
     * Creates an empty long box.
     */
    public LongEmptiableBox() {
        this(0);
        this.empty = true;
    }

    /**
     * Creates a long box with {@code d} as initial value.
     *
     * @param d Initial value.
     */
    public LongEmptiableBox(long d) {
        this.d = d;
        this.empty = false;
    }

    /**
     * Creates a long box with {@code d} as initial value.
     *
     * @param d Initial value.
     * @return long box with {@code d} as initial value.
     */
    public static LongEmptiableBox of(long d) {
        return new LongEmptiableBox(d);
    }

    /**
     * Applies arithmetic operation addition ({@code +}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation addition ({@code +}).
     */
    public void add(long d) {
        this.checkEmpty();
        this.set(this.get() + d);
    }

    /**
     * Applies arithmetic operation addition ({@code +}) to the current value with {@code 1} as
     * right operand.
     */
    public void add() {
        this.checkEmpty();
        this.add(1);
    }

    /**
     * Applies arithmetic operation subtraction ({@code -}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation subtraction ({@code -}).
     */
    public void remove(long d) {
        this.checkEmpty();
        this.set(this.get() - d);
    }

    /**
     * Applies arithmetic operation subtraction ({@code -}) to the current value with {@code 1} as
     * right operand.
     */
    public void remove() {
        this.checkEmpty();
        this.remove(1);
    }

    /**
     * Applies arithmetic operation multiplication ({@code *}) to the current value with {@code d}
     * as right operand.
     *
     * @param d Right operand of arithmetic operation multiplication ({@code *}).
     */
    public void multiply(long d) {
        this.checkEmpty();
        this.set(this.get() * d);
    }

    /**
     * Applies arithmetic operation division ({@code /}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation division ({@code /}).
     */
    public void divide(long d) {
        this.checkEmpty();
        this.set((long) (this.get() / d));
    }

    /**
     * Applies arithmetic operation power ({@code ^}) to the current value with {@code d} as right
     * operand.
     *
     * @param d Right operand of arithmetic operation power ({@code ^}).
     */
    public void pow(long d) {
        this.checkEmpty();
        this.set((long) Math.pow(this.get(), d));
    }

    /**
     * Applies arithmetic operation square root ({@code √}) to the current value.
     */
    public void sqrt() {
        this.checkEmpty();
        this.set((long) Math.sqrt(this.get()));
    }

    /**
     * Applies arithmetic operation cube root ({@code ∛}) to the current value.
     */
    public void cbrt() {
        this.checkEmpty();
        this.set((long) Math.cbrt(this.get()));
    }

    /**
     * Sets the long value to {@code d}.
     *
     * @param d New long value.
     */
    public void set(long d) {
        this.checkEmpty();
        this.d = d;
    }

    /**
     * Gets the long value.
     *
     * @return Current long value.
     */
    public long get() {
        this.checkEmpty();
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

    @Override
    public void empty() {
        this.empty = true;
    }

    @Override
    public boolean isEmpty() {
        return this.empty;
    }
}
