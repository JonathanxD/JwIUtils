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
package com.github.jonathanxd.iutils.opt.specialized;

import com.github.jonathanxd.iutils.opt.Some;

/**
 * {@link Some} specialized for Java {@code float}.
 */
public final class SomeFloat implements Some {

    /**
     * Value.
     */
    private final float value;

    /**
     * Constructs a {@link Some} instance.
     *
     * @param value Value.
     */
    public SomeFloat(float value) {
        this.value = value;
    }

    /**
     * Gets the value of {@link Some} instance.
     *
     * @return Value of {@link Some} instance.
     */
    public float getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Some(" + this.getValue() + ")";
    }

    @Override
    public int hashCode() {
        return Float.hashCode(this.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SomeFloat))
            return super.equals(obj);

        return ((SomeFloat) obj).getValue() == this.getValue();
    }
}
