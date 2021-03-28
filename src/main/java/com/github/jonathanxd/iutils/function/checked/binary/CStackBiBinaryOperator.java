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
package com.github.jonathanxd.iutils.function.checked.binary;

import com.github.jonathanxd.iutils.exception.RethrowException;
import com.github.jonathanxd.iutils.function.binary.StackBiBinaryOperator;

/**
 * {@link StackBiBinaryOperator}
 *
 * @see com.github.jonathanxd.iutils.function.checked
 */
@FunctionalInterface
public interface CStackBiBinaryOperator<T, PLAIN_T, U> extends StackBiBinaryOperator<T, PLAIN_T, U> {

    @Override
    default U apply(T value, U value2, PLAIN_T value3, U value4) {
        try {
            return this.applyChecked(value, value2, value3, value4);
        } catch (Throwable th) {
            throw RethrowException.rethrow(th);
        }
    }

    /**
     * {@link StackBiBinaryOperator#apply} equivalent which declares a {@code throws} clauses,
     * allowing exceptions to be caught outside of lambda context.
     *
     * Like other interfaces of this package, this interface implements a java corresponding
     * interface. All exceptions which occurs inside the lambda is rethrown in the implemented
     * method using {@link RethrowException#rethrow(Throwable)}.
     *
     * @return See {@link StackBiBinaryOperator#apply}.
     * @throws Throwable Exception occurred inside of function.
     */
    U applyChecked(T value, U value2, PLAIN_T value3, U value4) throws Throwable;

}
