/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.reflection;

/**
 * Represents a link to a invokable element.
 *
 * @param <T> Result type of invocation of the element.
 */
public interface Link<T> {

    /**
     * Binds a instance to invocation.
     *
     * This method will not check if the target element requires a instance.
     *
     * @param instance Instance to bind.
     * @param <U>      Type of instance.
     * @return A link with the instance bind-ed.
     */
    <U> BindLink<U, T> bind(U instance);

    /**
     * Invokes the linked invokable element.
     *
     * If this is not a {@link BindLink} and the linked invokable element is a instance element, the
     * first argument should be the instance (or you can bind the instance using {@link
     * #bind(Object)}).
     *
     * @param args Arguments to pass to the invokable element.
     * @return Result of invocation.
     */
    T invoke(Object... args);

}
