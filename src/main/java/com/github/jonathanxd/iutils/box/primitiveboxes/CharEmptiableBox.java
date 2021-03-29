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
 * Improved to {@link char} primitive type
 */
public class CharEmptiableBox implements UnknownEmptiableBox<Character> {

    /**
     * Value
     */
    private char d;

    private boolean empty;

    /**
     * Creates an empty char box.
     */
    public CharEmptiableBox() {
        this((char) 0);
        this.empty = true;
    }

    /**
     * Creates a char box with {@code d} as initial value.
     *
     * @param d Initial value.
     */
    public CharEmptiableBox(char d) {
        this.d = d;
        this.empty = false;
    }

    /**
     * Creates a char box with {@code d} as initial value.
     *
     * @param d Initial value.
     * @return Char box with {@code d} as initial value.
     */
    public static CharEmptiableBox of(char d) {
        return new CharEmptiableBox(d);
    }

    /**
     * Applies arithmetic operation addition ({@code +}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation addition ({@code +}).
     */
    public void add(char d) {
        this.checkEmpty();
        this.set((char) (this.get() + d));
    }

    /**
     * Applies arithmetic operation addition ({@code +}) to the current value with {@code 1} as
     * right operand.
     */
    public void add() {
        this.checkEmpty();
        this.add((char) 1);
    }

    /**
     * Applies arithmetic operation subtraction ({@code -}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation subtraction ({@code -}).
     */
    public void remove(char d) {
        this.checkEmpty();
        this.set((char) (this.get() - d));
    }

    /**
     * Applies arithmetic operation subtraction ({@code -}) to the current value with {@code 1} as
     * right operand.
     */
    public void remove() {
        this.checkEmpty();
        this.remove((char) 1);
    }

    /**
     * Applies arithmetic operation multiplication ({@code *}) to the current value with {@code d}
     * as right operand.
     *
     * @param d Right operand of arithmetic operation multiplication ({@code *}).
     */
    public void multiply(char d) {
        this.checkEmpty();
        this.set((char) (this.get() * d));
    }

    /**
     * Applies arithmetic operation division ({@code /}) to the current value with {@code d} as
     * right operand.
     *
     * @param d Right operand of arithmetic operation division ({@code /}).
     */
    public void divide(char d) {
        this.checkEmpty();
        this.set((char) (this.get() / d));
    }

    /**
     * Applies arithmetic operation power ({@code ^}) to the current value with {@code d} as right
     * operand.
     *
     * @param d Right operand of arithmetic operation power ({@code ^}).
     */
    public void pow(char d) {
        this.checkEmpty();
        this.set((char) Math.pow(this.get(), d));
    }

    /**
     * Applies arithmetic operation square root ({@code √}) to the current value.
     */
    public void sqrt() {
        this.checkEmpty();
        this.set((char) Math.sqrt(this.get()));
    }

    /**
     * Applies arithmetic operation cube root ({@code ∛}) to the current value.
     */
    public void cbrt() {
        this.checkEmpty();
        this.set((char) Math.cbrt(this.get()));
    }

    /**
     * Sets the char value to {@code d}.
     *
     * @param d New char value.
     */
    public void set(char d) {
        this.checkEmpty();
        this.d = d;
    }

    /**
     * Gets the char value.
     *
     * @return Current char value.
     */
    public char get() {
        this.checkEmpty();
        return this.d;
    }

    @Override
    public BaseBox<Character> box() {
        return MutableBox.of(d);
    }

    @Override
    public Class<?> type() {
        return Character.TYPE;
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
