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
package com.github.jonathanxd.iutils.box;

import com.github.jonathanxd.iutils.exception.EmptyBoxException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * A variant of {@link UnknownBox}, however, this type of box is {@code "emptiable"}, this means that the value
 * inside this box could be make invisible, so this box signals that there is no value to read in the box.
 *
 * This is useful for position pointers when working with text parser for fast-failure implementation, for example:
 *
 * <pre>
 *     {@code
 *     public String readVariableName(String text, EmptiableIntBox position) {
 *         // Try to read the variable name from text starting from 'position'
 *         if (text.startsWith("var")) {
 *             // ... do its job
 *             // Updates the position in pointer, so now the next read method
 *             // starts from the end of this
 *             position.set(end);
 *         } else {
 *             // Empties this box, so the next time you check the box for the int
 *             // it will report that there is no int in the box
 *             // It does in fact do not empties the box, it only sets an 'empty' flag
 *             // to 'true', so emptying the box does not involve any boxing or unboxing operation
 *             // for values backed by 'Object', it depends on the implementation if the value inside
 *             // is changed to 'null' or not.
 *             position.empty();
 *         }
 *     }
 *
 *     }
 * </pre>
 *
 * Also, Boxes are not atomic, there are used for single-thread works which does not want the overhead of Atomic operations.
 */
public interface UnknownEmptiableBox<T> extends UnknownBox<T> {

    /**
     * Empties this box, for primitive boxes it does in fact do not empties the box, it only sets an {@code empty} flag to
     * {@code true}, being free from any boxing or unboxing overhead.
     *
     * When emptied, trying to get values out of the box will throw {@link EmptyBoxException},
     * notifying the caller that it was trying to look into an empty box.
     */
    void empty();

    /**
     * Returns whether this box is empty, in other words, this means that no values could be retrieve from this box.
     *
     * Also there is no way to recover an emptied box, however, setting a new value to an empty box will toggle the box to
     * not-empty state.
     * @return Whether this box is empty or not.
     */
    boolean isEmpty();

    default void checkEmpty() {
        if (this.isEmpty())
            throw new EmptyBoxException("Trying to get value out of an emptied box.");
    }
}
