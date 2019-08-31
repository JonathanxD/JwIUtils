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
package com.github.jonathanxd.iutils.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Abstract class to provide type information (workaround for erasure). Unlike {@link
 * AbstractTypeInfo}, this approach does not prevents the garbage collector to collect this instance
 * if you wish only to use {@link TypeInfo}. This approach is not expensive as {@link
 * AbstractTypeInfo}, only {@link Type} is referenced, and does not require to allocate all objects
 * of {@link TypeInfo} only to delegate invocations to a {@code wrapper} instance (that is allocated
 * too).
 *
 * The {@link TypeInfo} provided by this class is not cached, and it is intentional..
 *
 * @param <T> Type.
 */
public abstract class TypeParameterProvider<T> {

    /**
     * Reference to type {@link T}.
     */
    private final Type type;

    /**
     * Constructor
     */
    protected TypeParameterProvider() {
        this.type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Gets the reference to type {@link T}.
     *
     * @return Reference to type {@link T}.
     */
    public final Type getType() {
        return this.type;
    }

    /**
     * Creates a {@link TypeInfo} of type {@link T} using the {@link #type reference}.
     *
     * @return Information of type {@link T}.
     */
    @SuppressWarnings("unchecked")
    public final TypeInfo<T> createTypeInfo() {
        return (TypeInfo<T>) TypeUtil.toTypeInfo(this.getType());
    }
}
