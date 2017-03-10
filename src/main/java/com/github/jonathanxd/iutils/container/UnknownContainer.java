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
package com.github.jonathanxd.iutils.container;

/**
 * Created by jonathan on 24/06/16.
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Container that has only getter and setter link methods.
 *
 * Methods to implement (required):
 *
 * {@link #type()} get()
 *
 * {@code void} set({@link #type()} value)
 */
public interface UnknownContainer<T> {

    MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();

    /**
     * Creates a boxed version of this container (if this container is primitive, if not, returns
     * {@code this}).
     *
     * @return Boxed version of this container (if this container is primitive, if not, returns
     * {@code this}).
     */
    BaseContainer<T> box();

    /**
     * Getter method handle.
     *
     * @return Getter method handle.
     */
    default MethodHandle getterHandle() {
        try {
            return LOOKUP.findVirtual(this.getClass(), "get", MethodType.methodType(this.type()));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Container '" + this.getClass().getCanonicalName() + "' don't implements 'get()' method (or implements with incorrect signature).");
        }
    }

    /**
     * Setter method handle.
     *
     * @return Setter method handle.
     */
    default MethodHandle setterHandle() {
        try {
            return LOOKUP.findVirtual(this.getClass(), "set", MethodType.methodType(Void.TYPE, this.type()));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Container '" + this.getClass().getCanonicalName() + "' don't implements 'set(value)' method (or implements with incorrect signature).");
        }
    }

    /**
     * Return the container value type (will return value type only if the container is a primitive
     * container, if is a generic container will return {@link Object})
     *
     * @return Container Value Type
     */
    Class<?> type();
}
