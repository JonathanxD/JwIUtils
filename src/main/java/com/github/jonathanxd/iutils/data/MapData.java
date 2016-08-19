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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.object.TypeInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jonathan on 03/06/16.
 */
public class MapData {

    private final Map<TypeInfo<?>, List<Object>> map = new HashMap<>();

    public <T> int registerData(TypeInfo<T> typeInfo, T object) {

        if (!map.containsKey(typeInfo)) {
            map.put(typeInfo, new ArrayList<>());
        }

        map.get(typeInfo).add(object);

        return map.get(typeInfo).size() - 1;
    }

    public <T> void unregisterData(TypeInfo<T> typeInfo, T object) {

        if (!map.containsKey(typeInfo))
            return;

        map.get(typeInfo).remove(object);

        if (map.get(typeInfo).isEmpty())
            map.remove(typeInfo);

    }

    public <T> void unregisterAllData(TypeInfo<T> typeInfo) {
        map.remove(typeInfo);
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> getAll(TypeInfo<T> typeInfo) {
        Collection<T> ts = (Collection<T>) map.get(typeInfo);

        if (ts == null)
            return Collections.emptyList();

        return ts;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAllAsList(TypeInfo<T> typeInfo) {
        List<T> ts = (List<T>) map.get(typeInfo);

        if (ts == null)
            return Collections.emptyList();

        return ts;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptional(TypeInfo<T> typeInfo) {
        List<Object> objectList = map.get(typeInfo);

        if (objectList == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((T) objectList.get(objectList.size() - 1));
    }

    @SuppressWarnings("unchecked")
    public <T, U extends T> Optional<U> getOptionalCasted(TypeInfo<T> typeInfo) {

        List<Object> objectList = map.get(typeInfo);

        if (objectList == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((U) objectList.get(objectList.size() - 1));
    }

    public <T> T getRequired(TypeInfo<T> typeInfo) {
        return this.getOptional(typeInfo).get();
    }

    public <T> T getRequired(TypeInfo<T> typeInfo, String message) {
        return this.getOptional(typeInfo).orElseThrow(() -> new IllegalStateException(message));
    }

    public <T, U extends T> U getRequiredCasted(TypeInfo<T> typeInfo) {
        return this.<T, U>getOptionalCasted(typeInfo).get();
    }

    public <T, U extends T> U getRequiredCasted(TypeInfo<T> typeInfo, String message) {
        return this.<T, U>getOptionalCasted(typeInfo).orElseThrow(() -> new IllegalStateException(message));
    }

    /**
     * Mix two {@link MapData}
     *
     * @param other Other data to mix with this
     * @return A new {@link MapData} instance containing elements of this {@link MapData} and all
     * elements of {@code other} {@link MapData}.
     */
    public MapData with(MapData other) {
        MapData new_ = new MapData();

        new_.map.putAll(this.map);
        new_.map.putAll(other.map);

        return new_;
    }
}
