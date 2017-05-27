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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

/**
 * A {@link TypedMap} which supports temporary {@link Value}.
 */
public interface TempTypedMap<K, V> extends TypedMap<K, V> {

    /**
     * Temporary associates key and a value reified type ({@code type}) to a {@code value}.
     *
     * Obs: Keys are unique, if the same key is associated to a different reified value type, old
     * key should be replaced.
     *
     * @param key   Key to associate
     * @param value Value to associate to key.
     * @param type  Reified type of value.
     * @param <B>   Type of value.
     * @return Replaced value and reified type associated to {@code key}, or {@link Pair#nullPair()}
     * if no one value was replaced.
     */
    <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTypedTemporary(K key, B value, TypeInfo<B> type);

    /**
     * Associates key and a value reified type ({@code type}) to a {@code value}.
     *
     * Obs: Keys are unique, if the same key is associated to a different reified value type, old
     * key should be replaced.
     *
     * Default implementation calls {@link #putTypedTemporary(Object, Object, TypeInfo)} is {@code
     * isTemporary} is true or call {@link #putTyped(Object, Object, TypeInfo)} if not.
     *
     * @param key         Key to associate
     * @param value       Value to associate to key.
     * @param type        Reified type of value.
     * @param isTemporary True to associates temporary, temporary values is removed when the value
     *                    is retrieved.
     * @param <B>         Type of value.
     * @return Replaced value and reified type associated to {@code key}, or {@link Pair#nullPair()}
     * if no one value was replaced.
     */
    default <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTyped(K key, B value, TypeInfo<B> type, boolean isTemporary) {
        if (isTemporary)
            return this.putTypedTemporary(key, value, type);
        else
            return this.putTyped(key, value, type);
    }

}
