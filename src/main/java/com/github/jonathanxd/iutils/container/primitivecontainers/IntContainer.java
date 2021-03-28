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
package com.github.jonathanxd.iutils.container.primitivecontainers;

import com.github.jonathanxd.iutils.container.BaseContainer;
import com.github.jonathanxd.iutils.container.MutableContainer;
import com.github.jonathanxd.iutils.container.UnknownContainer;

/**
 * Improved to {@link int} primitive type
 */
public class IntContainer implements UnknownContainer<Integer> {

    private int i;

    public IntContainer() {
        this.i = 0;
    }

    public IntContainer(int i) {
        this.i = i;
    }

    public static IntContainer of(int i) {
        return new IntContainer(i);
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
    public BaseContainer<Integer> box() {
        return MutableContainer.of(i);
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
