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
 * Improved to {@link int} primitive type
 */
public class IntBox implements UnknownBox<Integer> {

    private int i;

    public IntBox() {
        this.i = 0;
    }

    public IntBox(int i) {
        this.i = i;
    }

    public static IntBox of(int i) {
        return new IntBox(i);
    }

    public void add(int i) {
        this.set(this.get() + i);
    }

    public void add() {
        this.add(1);
    }

    public void remove(int i) {
        this.add(-i);
    }

    public void remove() {
        this.remove(1);
    }

    public void multiply(int i) {
        this.set(this.get() * i);
    }

    public void divide(int i) {
        this.set(this.get() / i);
    }

    @Override
    public BaseBox<Integer> box() {
        return MutableBox.of(i);
    }

    @Override
    public Class<?> type() {
        return Integer.TYPE;
    }

    public void set(int i) {
        this.i = i;
    }

    public int get() {
        return this.i;
    }
}
