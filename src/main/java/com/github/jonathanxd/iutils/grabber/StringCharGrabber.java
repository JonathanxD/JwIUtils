/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.grabber;

import java.util.Iterator;

/**
 * {@link Grabber} backed by a {@link String}.
 */
public final class StringCharGrabber extends AbstractGrabber<Character> {

    private final String string;

    public StringCharGrabber(String string) {
        super(string.length());
        this.string = string;
    }

    @Override
    protected Character get(int index) {
        return this.string.charAt(index);
    }

    @Override
    AbstractGrabber<Character> makeNew() {
        return new StringCharGrabber(this.string);
    }

    @SuppressWarnings("unchecked")
    @Override
    <U> AbstractGrabber<U> makeNew(Iterable<U> elements, int size) {

        U[] objects = (U[]) new Object[size];

        Iterator<U> iterator = elements.iterator();

        for (int i = 0; i < size; i++) {
            objects[i] = iterator.next();
        }

        return new ArrayGrabber<>(objects);

    }


}
