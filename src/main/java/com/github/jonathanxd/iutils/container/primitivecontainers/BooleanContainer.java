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
 * Improved to {@link boolean} primitive type
 */
public class BooleanContainer implements UnknownContainer<Boolean> {

    private boolean b;

    public BooleanContainer() {
        this.b = false;
    }

    public BooleanContainer(boolean b) {
        this.b = b;
    }

    public static BooleanContainer of(boolean b) {
        return new BooleanContainer(b);
    }

    public void toogle() {
        this.set(!this.get());
    }

    public void toFalse() {
        this.set(Boolean.FALSE);
    }

    public void toTrue() {
        this.set(Boolean.TRUE);
    }

    public void set(boolean b) {
        this.b = b;
    }

    public boolean get() {
        return b;
    }

    @Override
    public BaseContainer<Boolean> box() {
        return MutableContainer.of(b);
    }

    @Override
    public Class<?> type() {
        return Boolean.TYPE;
    }

}
