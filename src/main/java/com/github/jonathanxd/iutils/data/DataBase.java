/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Optional;

/**
 * Base class of all data holders.
 *
 *
 * Temporary data: Temporary stored objects will be removed when the value is retrieved by a getter.
 */
public interface DataBase<D extends DataBase<D>> {

    /**
     * Gets parent data holder.
     *
     * @return Parent data holder, or {@code null} if not present.
     */
    D getParent();

    /**
     * Gets main data holder.
     *
     * This method recursively call {@link #getParent()} to find main data holder.
     *
     * @return Main data holder, or {@code this} if {@link #getParent()} returns {@code null}.
     */
    @SuppressWarnings("unchecked")
    default D getMainData() {
        D parent = (D) this;

        while (true) {
            D tmp = parent.getParent();

            if (tmp == null)
                return parent;

            parent = tmp;
        }
    }

    /**
     * Associates {@code key} and {@code type} to {@code value}.
     *
     * @param key   Key to associate.
     * @param value Value to associate.
     * @param type  Type of value to associate.
     * @param <T>   Type of value.
     * @return {@link Pair} of replaced value and value type associated to {@code key} or a {@link
     * Pair#nullPair()}if no one value was replaced.
     */
    default <T> Pair<?, TypeInfo<?>> set(Object key, T value, TypeInfo<T> type) {
        return this.set(key, value, type, false);
    }

    /**
     * Associates {@code key} and {@code type} to {@code value}.
     *
     * @param key         Key to associate.
     * @param value       Value to associate.
     * @param type        Type of value to associate.
     * @param isTemporary True to store temporary data, read the {@link DataBase} documentation for
     *                    more info.
     * @param <T>         Type of value.
     * @return {@link Pair} of replaced value and value type associated to {@code key} or a {@link
     * Pair#nullPair()}if no one value was replaced.
     */
    <T> Pair<?, TypeInfo<?>> set(Object key, T value, TypeInfo<T> type, boolean isTemporary);

    /**
     * Removes the value associated to {@code key} regardless the type.
     *
     * @param key Key to remove associated value.
     * @return {@link Pair} of value and type associated to {@code key}, or {@link Pair#nullPair()}
     * if no one was removed.
     */
    Pair<?, TypeInfo<?>> remove(Object key);

    /**
     * Removes the value associated to {@code key} and {@code type}.
     *
     * @param key  Key to remove associated value.
     * @param type Type of value.
     * @return Removed value associated to {@code key} and {@code type}, or null if no one value was
     * removed.
     */
    <T> T remove(Object key, TypeInfo<T> type);

    /**
     * Removes the {@code value} associated to {@code key} and {@code type}.
     *
     * @param key   Key to remove associated value.
     * @param type  Type of value.
     * @param value Value to remove.
     * @return True if element was removed.
     */
    <T> boolean remove(Object key, T value, TypeInfo<T> type);

    /**
     * Gets the value associated to {@code key} and {@code type}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @return Value associated to {@code key} and {@code type} or null if not found.
     */
    <T> T getOrNull(Object key, TypeInfo<T> type);

    /**
     * Gets the value associated to {@code key} and {@code type}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @return {@link Optional} of value associated to {@code key} and {@code type} or {@link
     * Optional#empty()} if not found.
     */
    <T> Optional<T> getOptional(Object key, TypeInfo<T> type);

    /**
     * Gets the value associated to {@code key} and {@code type} as a value of type {@link A}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @param <A>  Type to cast.
     * @return Value associated to {@code key} and {@code type} as a value of type {@link A} or null
     * if not found.
     */
    <T, A> A getOrNullAs(Object key, TypeInfo<T> type);

    /**
     * Gets the value associated to {@code key} and {@code type} as a value of type {@link A}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @param <A>  Type to cast.
     * @return {@link Optional} of value associated to {@code key} and {@code type} as a value of
     * type {@link A} or {@link Optional#empty()} if not found.
     */
    <T, A> Optional<A> getOptionalAs(Object key, TypeInfo<T> type);

    /**
     * Returns true if this data has any value associated to {@code key}.
     *
     * @param key Key to check if any value is associated.
     * @return True if this data has any value associated to {@code key}.
     */
    boolean contains(Object key);

    /**
     * Returns true if this data has any value associated to both {@code key} and {@code type}.
     *
     * @param key  Key to check if any value is associated.
     * @param type Type to check if any value is associated.
     * @return True if this data has any value associated to both {@code key} and {@code type}.
     */
    boolean contains(Object key, TypeInfo<?> type);

    /**
     * Gets value associated to {@code key} and {@code type} if present, otherwise sets to {@code
     * value} and returns {@code value}.
     *
     * @param key   Key.
     * @param value Value to set if none value is set to {@code key}.
     * @param type  Type of value.
     * @param <T>   Type of value.
     * @return Value associated to {@code key} and {@code type} or {@code value} if no one is
     * defined to {@code key} and {@code type}.
     */
    <T> T getOrSet(Object key, T value, TypeInfo<T> type);

    /**
     * Gets value associated to {@code key} nad {@code type} if present, otherwise sets to {@code
     * value} and returns {@code value}.
     *
     * @param key   Key.
     * @param value Lazy value to set if none value is associated to {@code key} and {@code type}.
     * @param type  Type of value.
     * @param <T>   Type of value.
     * @return Value associated to {@code key} and {@code type} or {@code value} if no one is
     * associated to {@code key} and {@code type}.
     */
    <T> T getOrSetLazily(Object key, Lazy<T> value, TypeInfo<T> type);

    /**
     * Returns true if this data holder is empty.
     *
     * @return True if this data holder is empty.
     */
    boolean isEmpty();

    /**
     * Creates a copy of {@code this} data.
     *
     * @return Copy of {@code this} data.
     */
    D copy();

    /**
     * Creates a copy of {@code this} data with parent {@code parent}.
     *
     * @param parent Parent data holder.
     * @return Copy of {@code this} data with parent {@code parent}.
     */
    D copy(D parent);

    /**
     * Gets a typed map of values in this data.
     *
     * @return Data typed map.
     */
    TypedMap<Object, Object> getTypedDataMap();

}
