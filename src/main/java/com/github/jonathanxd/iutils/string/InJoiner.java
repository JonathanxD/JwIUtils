/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.string;

import com.github.jonathanxd.iutils.annotation.Ref;

import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * Created by jonathan on 24/06/16.
 */
public class InJoiner {

    private final StringJoiner first;
    private final Supplier<StringJoiner> secondSupplier;
    @Ref
    private StringJoiner current = null;
    @Ref
    private StringJoiner currentSecond;

    public InJoiner(StringJoiner first, Supplier<StringJoiner> secondSupplier) {

        this.first = first;
        this.secondSupplier = secondSupplier;

        this.current = first;

        this.currentSecond = secondSupplier.get();
    }

    public void joinFirst(String s) {
        if (current != first) {

            joinSecondInFirst();

            current = first;
        }

        first.add(s);
    }

    public void joinSecond(String s) {
        currentSecond.add(s);
        current = currentSecond;
    }

    public void selectFirst() {
        if (current != first) {
            joinSecondInFirst();

            current = first;
        }
    }

    public void selectSecond() {
        current = currentSecond;
    }

    public void joinToCurrent(String s) {
        current.add(s);
    }

    private void joinFinish() {
        if (current != currentSecond) {
            joinSecondInFirst();
        }
    }

    private void joinSecondInFirst() {
        if (currentSecond.length() > 2) {
            first.add(currentSecond.toString());

            currentSecond = secondSupplier.get();
        }
    }

    @Override
    public String toString() {
        this.joinFinish();
        return first.toString();
    }
}
