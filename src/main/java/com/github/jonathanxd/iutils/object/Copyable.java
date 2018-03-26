/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.object;

/**
 * Copyable objects are those that can be safely copied to a new instance that looks exactly like
 * the old one and modifications in the original instance must no reflect in the other (does not
 * apply to inner data, but the recommendation is to copy inner data too, unless it is immutable).
 *
 * Immutable data structures must return itself if them implement {@link Copyable copyable}.
 */
public interface Copyable {
    /**
     * Creates a copy of {@code value} by calling {@link Copyable#createCopy()}.
     *
     * @param value Value to create copy.
     * @param <T>   Type of object.
     * @return Copy of {@code value}.
     */
    @SuppressWarnings("unchecked")
    static <T extends Copyable> T copy(T value) {
        return (T) value.createCopy();
    }

    /**
     * Creates an exact copy of this object.
     *
     * The returned object must be of the same type as the current object.
     *
     * We heavily recommend you to copy objects using {@link Copyable#copy(Copyable)} method.
     *
     * @return Exact copy of this object.
     */
    Copyable createCopy();
}
