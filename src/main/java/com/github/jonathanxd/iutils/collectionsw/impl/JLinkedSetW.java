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
package com.github.jonathanxd.iutils.collectionsw.impl;

import com.github.jonathanxd.iutils.collectionsw.SetW;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A {@link SetW} backed by a {@link java.util.LinkedHashMap}.
 *
 * @param <E> Element type.
 */
public class JLinkedSetW<E> extends JHashSetW<E> {

    public JLinkedSetW() {
        super(new LinkedHashMap<>());
    }

    public JLinkedSetW(E element) {
        super(JLinkedSetW.create(element));
    }

    public JLinkedSetW(SetW<E> elements) {
        super(JLinkedSetW.create(elements));
    }

    public JLinkedSetW(E[] elements) {
        super(JLinkedSetW.create(elements));
    }

    private JLinkedSetW(Map<E, Object> map) {
        super(map);
    }

    @Override
    protected <V> Map<V, Object> newMap() {
        return new LinkedHashMap<>();
    }

    @Override
    protected <V> Map<V, Object> newMap(Map<V, Object> defaults) {
        return new LinkedHashMap<>(defaults);
    }

    private static <E> LinkedHashMap<E, Object> create(E element) {
        LinkedHashMap<E, Object> map = new LinkedHashMap<>();

        map.put(element, JHashSetW.DEF);

        return map;
    }

    private static <E> LinkedHashMap<E, Object> create(SetW<E> element) {
        LinkedHashMap<E, Object> map = new LinkedHashMap<>();

        element.forEach(e -> map.put(e, JHashSetW.DEF));

        return map;
    }

    private static <E> LinkedHashMap<E, Object> create(E[] elements) {
        LinkedHashMap<E, Object> map = new LinkedHashMap<>();

        for (E element : elements) {
            map.put(element, JHashSetW.DEF);
        }

        return map;
    }

    @Override
    public SetW<E> copy() {
        return new JLinkedSetW<>(super.map);
    }
}
