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
package com.github.jonathanxd.iutils.object;

/**
 * Created by jonathan on 24/06/16.
 */

/**
 * A generic representation that hold an object.
 *
 * @param <T> Generic representation (are not related with object type, if you need an object with
 *            same type of representation use: {@link THolderGenericRepresentation})
 */
public class HolderGenericRepresentation<T> extends GenericRepresentation<T> {

    private final Object value;

    private HolderGenericRepresentation(Class<? extends T> aClass, GenericRepresentation[] related, boolean isUnique, Object value) {
        super(aClass, related, isUnique);
        this.value = value;
    }

    public static <T> HolderGenericRepresentation<T> makeHold(GenericRepresentation<T> representation, Object value) {
        return new HolderGenericRepresentation<>(representation.getAClass(), representation.getRelated(), representation.isUnique(), value);
    }

    public static <T> HolderGenericRepresentation<T> makeHold(GenericRepresentation<T> representation, boolean isUnique, Object value) {
        return new HolderGenericRepresentation<>(representation.getAClass(), representation.getRelated(), isUnique, value);
    }

    /**
     * Get value
     *
     * @return Value
     */
    public Object getValue() {
        return value;
    }
}
