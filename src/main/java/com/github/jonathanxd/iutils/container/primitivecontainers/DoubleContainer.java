/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.container.primitivecontainers;

import com.github.jonathanxd.iutils.container.BaseContainer;
import com.github.jonathanxd.iutils.container.MutableContainer;
import com.github.jonathanxd.iutils.container.UnknownContainer;

/**
 * Improved to {@link double} primitive type
 */
public class DoubleContainer implements UnknownContainer<Double> {

    private double d;

    public DoubleContainer() {
        this(0D);
    }

    public DoubleContainer(double d) {
        this.d = d;
    }

    public static DoubleContainer of(double d) {
        return new DoubleContainer(d);
    }

    public void add(double d) {
        this.set(this.get() + d);
    }

    public void add() {
        this.add(1);
    }

    public void remove(double d) {
        this.add(-d);
    }

    public void remove() {
        this.remove(1);
    }

    public void multiply(double d) {
        this.set(this.get() * d);
    }

    public void divide(double d) {
        this.set(this.get() / d);
    }

    public void set(double d) {
        this.d = d;
    }

    public double get() {
        return d;
    }

    @Override
    public BaseContainer<Double> box() {
        return MutableContainer.of(d);
    }

    @Override
    public Class<?> type() {
        return Double.TYPE;
    }
}
