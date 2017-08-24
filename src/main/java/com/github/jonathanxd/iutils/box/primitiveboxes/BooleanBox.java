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
import com.github.jonathanxd.iutils.function.unary.BooleanUnaryOperator;

/**
 * Improved to {@link boolean} primitive type.
 */
public class BooleanBox implements UnknownBox<Boolean> {

    /**
     * Value
     */
    private boolean b;

    /**
     * Creates a boolean box with {@code false} as initial value.
     */
    public BooleanBox() {
        this.b = false;
    }

    /**
     * Creates a boolean box with {@code b} as initial value.
     *
     * @param b Initial value.
     */
    public BooleanBox(boolean b) {
        this.b = b;
    }

    /**
     * Creates a boolean box with {@code b} as initial value.
     *
     * @param b Initial value.
     * @return Boolean box with {@code b} as initial value.
     */
    public static BooleanBox of(boolean b) {
        return new BooleanBox(b);
    }

    /**
     * Toggle the boolean value. (If is {@code true}, change to {@code false} and vice-versa).
     */
    public void toogle() {
        this.set(!this.get());
    }

    /**
     * Sets the boolean value to {@code b}.
     *
     * @param b New boolean value.
     */
    public void set(boolean b) {
        this.b = b;
    }

    /**
     * Gets the boolean value.
     *
     * @return Current boolean value.
     */
    public boolean get() {
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

}
