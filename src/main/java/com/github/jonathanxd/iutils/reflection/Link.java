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
package com.github.jonathanxd.iutils.reflection;

/**
 * Created by jonathan on 20/08/16.
 */

/**
 * Represents a Link to an element that return a value of type {@link T}.
 *
 * A {@link Link} holds an element, this element must be {@link Invokable}.
 *
 * @param <T> Type of value that this element returns.
 */
public interface Link<T> {

    /**
     * Bind a instance to invocation, the bind-ed instance will be used to {@link
     * #invoke(Object...)} element without providing the instance explicit.
     *
     * @param instance Instance to bind
     * @param <U>      Type of instance
     * @return Instance Bind-ed link.
     */
    <U> BindLink<U, T> bind(U instance);

    /**
     * Invoke the linked element.
     *
     * @param args Arguments, if this element is not static and has no instance bind-ed to link,
     *             first argument MUST be the instance to be used to invoke the element.
     * @return Element invocation result.
     */
    T invoke(Object... args);

}
