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
package com.github.jonathanxd.iutils.function.checked.binary;

import com.github.jonathanxd.iutils.function.binary.NodeBiBinaryOperator;
import com.github.jonathanxd.iutils.object.Node;

/**
 * {@link NodeBiBinaryOperator}
 *
 * @see com.github.jonathanxd.iutils.function.checked
 */
@FunctionalInterface
public interface ENodeBiBinaryOperator<T, U> extends NodeBiBinaryOperator<T, U> {


    @Override
    default Node<T, U> apply(T t, U u, T t2, U u2) {
        try {
            return this.applyChecked(t, u, t2, u2);
        } catch (Exception th) {
            throw new RuntimeException(th);
        }
    }

    /**
     * {@link NodeBiBinaryOperator#apply} equivalent which declares a {@code throws} clauses,
     * allowing exceptions to be caught outside of lambda context.
     *
     * Like other interfaces of this package, this interface implements a java corresponding
     * interface. All exceptions which occurs inside the lambda is rethrown in the implemented
     * method using {@link RuntimeException}.
     *
     * @return See {@link NodeBiBinaryOperator#apply}.
     * @throws Exception Exception occurred inside of function.
     */
    Node<T, U> applyChecked(T t, U u, T t2, U u2) throws Exception;

}
