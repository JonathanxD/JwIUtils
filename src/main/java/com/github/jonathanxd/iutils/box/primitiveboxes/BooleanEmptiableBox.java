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
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;

/**
 * Improved to {@code boolean} primitive type.
 */
public class BooleanEmptiableBox implements UnknownEmptiableBox<Boolean> {

    /**
     * Value
     */
    private boolean b;

    /**
     * Has value.
     */
    private boolean empty;

    /**
     * Creates a boolean box without any value.
     */
    public BooleanEmptiableBox() {
        this.b = false;
        this.empty = true;
    }

    /**
     * Creates a boolean box with {@code b} as initial value.
     *
     * @param b Initial value.
     */
    public BooleanEmptiableBox(boolean b) {
        this.b = b;
        this.empty = false;
    }

    /**
     * Creates a boolean box with {@code b} as initial value.
     *
     * @param b Initial value.
     * @return Boolean box with {@code b} as initial value.
     */
    public static BooleanEmptiableBox of(boolean b) {
        return new BooleanEmptiableBox(b);
    }

    /**
     * Toggle the boolean value. (If is {@code true}, change to {@code false}, vice-versa).
     */
    public void toggle() {
        this.checkEmpty();
        this.set(!this.get());
    }

    /**
     * Sets the boolean value to {@code b}.
     *
     * @param b New boolean value.
     */
    public void set(boolean b) {
        this.empty = false;
        this.b = b;
    }

    /**
     * Gets the boolean value.
     *
     * @return Current boolean value.
     */
    public boolean get() {
        this.checkEmpty();
        return this.b;
    }

    /**
     * Applies operator {@code f} to value and sets to new value (returned by operator).
     *
     * @param f Operator to apply to current value.
     */
    public void apply(BooleanUnaryOperator f) {
        this.set(f.apply(this.get()));
    }

    @Override
    public BaseBox<Boolean> box() {
        return MutableBox.of(b);
    }

    @Override
    public Class<?> type() {
        return Boolean.TYPE;
    }


    @Override
    public boolean isEmpty() {
        return this.empty;
    }

    @Override
    public void empty() {
        this.empty = true;
    }
}
