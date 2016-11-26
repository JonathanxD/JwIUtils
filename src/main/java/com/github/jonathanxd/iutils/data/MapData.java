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

import com.github.jonathanxd.iutils.optional.Require;
import com.github.jonathanxd.iutils.type.TypeInfo;
import com.github.jonathanxd.iutils.type.TypeUtil;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A map data, {@link MapData} can hold parent elements, {@link MapData} that hold parent element is
 * called 'Child MapData'.
 *
 * The purpose of this class is to hold information to be retrieved later by another context.
 *
 * {@link MapData} hold a Map with {@link MapData} as key and a LIFO list as value.
 */
public class MapData extends BaseData<TypeInfo<?>> {

    private final Map<TypeInfo<?>, List<Object>> map = new HashMap<>();
    private final MapData parent;

    public MapData() {
        this(null);
    }

    protected MapData(MapData parent) {
        this.parent = parent;
    }

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
        Collection<T> ts = (Collection<T>) this.map.get(typeInfo);

        if (ts == null)
            return Collections.emptyList();

        return ts;
    }

    /**
     * Gets all data including parent data.
     *
     * @param typeInfo Type info.
     * @param <T>      Type of data.
     * @return All data including parent data.
     */
    public <T> Collection<T> getAllAndParent(TypeInfo<T> typeInfo) {
        Collection<T> collection = new ArrayList<>();

        MapData parent = this.getParent();

        if (parent != null) {
            collection.addAll(parent.getAllAndParent(typeInfo));
        }

        collection.addAll(this.getAll(typeInfo));

        return collection;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAllAsList(TypeInfo<T> typeInfo) {
        List<T> ts = (List<T>) this.map.get(typeInfo);

        if (ts == null)
            return Collections.emptyList();

        return ts;
    }

    /**
     * Gets all data including parent data.
     *
     * @param typeInfo Type info.
     * @param <T>      Type of data.
     * @return All data including parent data.
     */
    public <T> List<T> getAllAsListAndParent(TypeInfo<T> typeInfo) {
        List<T> list = new ArrayList<>();

        MapData parent = this.getParent();

        if (parent != null) {
            list.addAll(parent.getAllAndParent(typeInfo));
        }

        list.addAll(this.getAll(typeInfo));

        return list;
    }

    /**
     * Find all elements of type {@code typeInfo} that matches {@code predicate}.
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return Stream with a sequence of all found elements.
     */
    public <T> Stream<T> findAllToStream(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return this.getAllAsList(typeInfo).stream().filter(predicate);
    }

    /**
     * Find all elements of type {@code typeInfo} that matches {@code predicate} (including {@link
     * #parent}).
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return Stream with a sequence of all found elements.
     */
    public <T> Stream<T> findAllToStreamAndParent(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return this.getAllAsListAndParent(typeInfo).stream().filter(predicate);
    }


    /**
     * Find all elements of type {@code typeInfo} that matches {@code predicate}.
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return List of all found elements.
     */
    public <T> List<T> findAll(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return this.getAllAsList(typeInfo).stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Find all elements of type {@code typeInfo} that matches {@code predicate} (including {@link
     * #parent}).
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return Stream with a sequence of all found elements.
     */
    public <T> List<T> findAllAndParent(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return this.getAllAsListAndParent(typeInfo).stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Find first element of type {@code typeInfo} that matches {@code predicate}.
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return {@link Optional} of first found element, or a empty {@link Optional} if no one
     * element was found.
     */
    public <T> Optional<T> findFirst(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return this.getAllAsList(typeInfo).stream().filter(predicate).findFirst();
    }

    /**
     * Find first element of type {@code typeInfo} that matches {@code predicate} (including {@link
     * #parent}).
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return {@link Optional} of first found element, or a empty {@link Optional} if no one
     * element was found.
     */
    public <T> Optional<T> findFirstAndParent(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return this.getAllAsListAndParent(typeInfo).stream().filter(predicate).findFirst();
    }

    /**
     * Find first element of type {@code typeInfo} that matches {@code predicate}.
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return The first found element.
     * @throws IllegalStateException if no one element was found.
     */
    public <T> T findFirstRequired(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return Require.require(this.getAllAsList(typeInfo).stream().filter(predicate).findFirst());
    }

    /**
     * Find first element of type {@code typeInfo} that matches {@code predicate}  (including {@link
     * #parent}).
     *
     * @param typeInfo  Type Representation.
     * @param predicate Predicate
     * @param <T>       Type.
     * @return The first found element.
     * @throws IllegalStateException if no one element was found.
     */
    public <T> T findFirstRequiredAndParent(TypeInfo<T> typeInfo, Predicate<T> predicate) {
        return Require.require(this.getAllAsListAndParent(typeInfo).stream().filter(predicate).findFirst());
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptional(TypeInfo<T> typeInfo) {
        List<Object> objectList = this.map.get(typeInfo);

        if (objectList == null) {
            return Optional.empty();
        }

        if (objectList.isEmpty()) {

            this.map.remove(typeInfo);

            return Optional.empty();
        }

        return Optional.ofNullable((T) objectList.get(objectList.size() - 1));
    }

    /**
     * Gets the data in current map data, if not found, try to get in parent data.
     *
     * @param typeInfo Type info key.
     * @param <T>      Type
     * @return Data in current map data, if not found, try to get in parent data.
     */
    public <T> Optional<T> getOptionalOrParent(TypeInfo<T> typeInfo) {
        Optional<T> optional = this.getOptional(typeInfo);

        if (optional.isPresent())
            return optional;


        MapData parent = this.getParent();

        return parent == null ? optional : parent.getOptionalOrParent(typeInfo);
    }

    @SuppressWarnings("unchecked")
    public <T, U extends T> Optional<U> getOptionalCasted(TypeInfo<T> typeInfo) {
        return (Optional<U>) this.getOptional(typeInfo);
    }

    @SuppressWarnings("unchecked")
    public <T, U extends T> Optional<U> getOptionalOrParentCasted(TypeInfo<T> typeInfo) {
        return (Optional<U>) this.getOptionalOrParent(typeInfo);
    }

    public <T> T getRequired(TypeInfo<T> typeInfo) {
        return Require.require(this.getOptional(typeInfo));
    }

    public <T> T getRequiredOrParent(TypeInfo<T> typeInfo) {
        return Require.require(this.getOptionalOrParent(typeInfo));
    }

    public <T> T getRequired(TypeInfo<T> typeInfo, String message) {
        return this.getOptional(typeInfo).orElseThrow(() -> new IllegalStateException(message));
    }

    public <T> T getRequiredOrParent(TypeInfo<T> typeInfo, String message) {
        return this.getOptionalOrParent(typeInfo).orElseThrow(() -> new IllegalStateException(message));
    }

    public <T, U extends T> U getRequiredCasted(TypeInfo<T> typeInfo) {
        return Require.require(this.<T, U>getOptionalCasted(typeInfo));
    }

    public <T, U extends T> U getRequiredOrParentCasted(TypeInfo<T> typeInfo) {
        return Require.require(this.<T, U>getOptionalOrParentCasted(typeInfo));
    }

    public <T, U extends T> U getRequiredCasted(TypeInfo<T> typeInfo, String message) {
        return this.<T, U>getOptionalCasted(typeInfo).orElseThrow(() -> new IllegalStateException(message));
    }

    public <T, U extends T> U getRequiredOrParentCasted(TypeInfo<T> typeInfo, String message) {
        return this.<T, U>getOptionalOrParentCasted(typeInfo).orElseThrow(() -> new IllegalStateException(message));
    }

    /**
     * Mix two {@link MapData}
     *
     * @param other Other data to mix with this
     * @return A new {@link MapData} instance containing elements of this {@link MapData} and all
     * elements of {@code other} {@link MapData}.
     */
    public MapData with(MapData other) {
        MapData new_ = new MapData(this.parent);

        new_.map.putAll(this.map);
        new_.map.putAll(other.map);

        return new_;
    }

    @SuppressWarnings("unchecked")
    private <T> void addGenericData(TypeInfo<?> data, Object o) {
        this.registerData((TypeInfo<T>) data, (T) o);
    }

    @Override
    public void addData(TypeInfo<?> data, Object o) {
        this.addGenericData(data, o);
    }

    @Override
    public void removeData(TypeInfo<?> data) {
        this.unregisterAllData(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(TypeInfo<?> data) {
        return this.getOptional((TypeInfo<X>) data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Class<? extends X> dataClass) {
        return this.getOptional((TypeInfo<X>) TypeInfo.aEnd(dataClass));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Parameter parameter) {
        return this.getOptional((TypeInfo<X>) TypeUtil.toReference(parameter.getParameterizedType()));
    }

    @Override
    public <X> Optional<X> getDataAssignable(Class<? extends X> dataClass) {
        return this.getData(dataClass);
    }

    /**
     * Create a new child map data, returned map data will hold current map data as parent map data.
     *
     * @return Map data holding current as child.
     */
    public MapData newChild() {
        return new MapData(this);
    }

    /**
     * Gets the parent map data.
     *
     * @return Parent map data.
     */
    public MapData getParent() {
        return this.parent;
    }

    @Override
    public BaseData clone() {
        MapData mapData = new MapData(this.parent);
        mapData.map.putAll(this.map);
        return mapData;
    }
}
