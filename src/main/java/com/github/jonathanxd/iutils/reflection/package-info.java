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
/**
 * Reflection utilities.
 *
 * {@link com.github.jonathanxd.iutils.reflection.Links} and {@link com.github.jonathanxd.iutils.reflection.Invokables}
 * is an unification of {@link java.lang.invoke} and {@link java.lang.reflect} APIs.
 *
 * {@link com.github.jonathanxd.iutils.reflection.Link}s is a link to an {@link
 * com.github.jonathanxd.iutils.reflection.Invokable} element, {@link
 * com.github.jonathanxd.iutils.reflection.Invokable} may be:
 *
 * - Lambdas: Dynamic Invocation
 *
 * - {@link java.lang.invoke.MethodHandle}: New Java invocation API, more faster than Reflection.
 *
 * - {@link java.lang.reflect.Field}, {@link java.lang.reflect.Method}, {@link
 * java.lang.reflect.Constructor}, etc..: Java Reflection elements.
 *
 * {@link com.github.jonathanxd.iutils.reflection.Link}s can be used to inject dependencies (DI).
 */
package com.github.jonathanxd.iutils.reflection;