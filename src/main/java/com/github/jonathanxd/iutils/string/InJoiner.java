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
package com.github.jonathanxd.iutils.string;

import com.github.jonathanxd.iutils.grabber.Grabber;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * Utilities to work with two {@link StringJoiner StringJoiners}.
 *
 * This class allows to mix two {@link StringJoiner StringJoiners} into one joining process, this
 * class provides utility methods to join the result of a second {@link StringJoiner} into first,
 * this class is used by {@link com.github.jonathanxd.iutils.grabber.AbstractGrabber} to compose the
 * string representation specified in {@link Grabber#toString()};
 */
public final class InJoiner {

    /**
     * First joiner.
     */
    private final StringJoiner first;

    /**
     * Provider of second joiner.
     */
    private final Supplier<StringJoiner> secondSupplier;

    /**
     * Count of appends to second joiner.
     */
    private int secondAppends = 0;

    /**
     * Current joiner.
     */
    private StringJoiner current = null;

    /**
     * Current second joiner.
     */
    private StringJoiner currentSecond;

    public InJoiner(StringJoiner first, Supplier<StringJoiner> secondSupplier) {

        Objects.requireNonNull(first);
        Objects.requireNonNull(secondSupplier);

        this.first = first;
        this.secondSupplier = secondSupplier;

        this.current = first;

        this.currentSecond = secondSupplier.get();
    }

    /**
     * Joins the string {@code s} in the first joiner.
     *
     * If current joiner is not the {@link #first} joiner, the {@link #joinSecondIntoFirst()} is
     * called (before joining the string {@code s}).
     *
     * @param s String to join.
     * @see #joinSecondIntoFirst()
     */
    public void joinFirst(String s) {
        if (this.current != this.first) {

            this.joinSecondIntoFirst();

            this.current = this.first;
        }

        this.first.add(s);
    }

    /**
     * Joins string {@code s} in the second joiner.
     *
     * @param s String to join.
     */
    public void joinSecond(String s) {
        this.currentSecond.add(s);
        this.current = this.currentSecond;
        this.secondAppends++;
    }

    /**
     * Selects the first joiner, if current joiner is not the first joiner, the {@link
     * #joinSecondIntoFirst()} method is called.
     *
     * @see #joinSecondIntoFirst()
     */
    public void selectFirst() {
        if (this.current != this.first) {
            this.joinSecondIntoFirst();

            this.current = this.first;
        }
    }

    /**
     * Selects the second joiner.
     */
    public void selectSecond() {
        this.current = this.currentSecond;
    }

    /**
     * Joins the string {@code s} in the current selected joiner.
     *
     * @param s String to join.
     */
    public void joinToCurrent(String s) {
        this.current.add(s);
    }

    /**
     * Finished the joining process (calls the {@link #joinSecondIntoFirst()} if the current joiner
     * is not the first joiner).
     */
    private void joinFinish() {
        if (this.current != this.currentSecond) {
            this.joinSecondIntoFirst();
            this.current = this.first;
        }
    }

    /**
     * Append the result of {@link #currentSecond#toString() second joiner} to the {@link #first
     * first joiner} and sets the {@link #currentSecond current second joiner} to a new instance
     * supplied by {@link #secondSupplier second joiner supplier}.
     *
     * This method will change the {@link #current current joiner}.
     */
    private void joinSecondIntoFirst() {
        if (this.secondAppends > 0) {
            this.first.add(this.currentSecond.toString());

            this.currentSecond = this.secondSupplier.get();
            this.secondAppends = 0;
        }
    }

    @Override
    public String toString() {
        this.joinFinish();
        return this.first.toString();
    }
}
