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
 * Improved to {@link float} primitive type
 */

public class FloatBox implements UnknownBox<Float> {

    private float f;

    public FloatBox() {
        this.f = 0F;
    }

    public FloatBox(float f) {
        this.f = f;
    }

    public static FloatBox of(float f) {
        return new FloatBox(f);
    }

    public void add(float f) {
        this.set(this.get() + f);
    }

    public void add() {
        this.add(1);
    }

    public void remove(float f) {
        this.add(-f);
    }

    public void remove() {
        this.remove(1);
    }

    public void multiply(float f) {
        this.set(this.get() * f);
    }

    public void divide(float f) {
        this.set(this.get() / f);
    }

    public void set(float f) {
        this.f = f;
    }

    public float get() {
        return f;
    }

    @Override
    public BaseBox<Float> box() {
        return MutableBox.of(f);
    }

    @Override
    public Class<?> type() {
        return Float.TYPE;
    }
}
