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
package com.github.jonathanxd.iutils.arguments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.github.jonathanxd.iutils.object.Reference;

/**
 * Created by jonathan on 13/02/16.
 */
public class ArrayArgument<E> {

    private final E[] eArray;
    private final Map<Reference<?>, Function<E, ?>> transformerMap = new HashMap<>();

    public ArrayArgument(E[] eArray) {
        this.eArray = eArray;
    }

    public <R> void add(Reference<R> reference, Function<E, Optional<R>> transformer) {

        transformerMap.put(reference, transformer);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Reference<T> reference, int pos) {
        E value = eArray[pos];

        for (Map.Entry<Reference<?>, Function<E, ?>> entry : transformerMap.entrySet()) {

            if (entry.getKey().compareTo(reference) == 0) {
                Object ret = entry.getValue().apply(value);

                if (ret == null)
                    continue;

                Optional<?> retOpt;

                if (ret instanceof Optional) {
                    retOpt = (Optional<?>) ret;
                    if (!retOpt.isPresent())
                        continue;
                    ret = retOpt.get();
                }

                return Optional.of((T) ret);
            }
        }

        return Optional.empty();
    }

    public <T> Collection<T> allOf(Reference<T> reference) {
        Collection<T> collection = new ArrayList<>();

        Optional<T> current;

        int offset = 0;

        while ((current = offSetFirst(reference, offset)).isPresent()) {
            collection.add(current.get());
        }

        return collection;
    }

    public <T> boolean find(Reference<T> reference) {
        return transformerMap.entrySet().stream().filter(e -> e.getKey().compareTo(reference) == 0).findFirst().isPresent();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> first(Reference<T> reference) {
        for (int x = 0; x < eArray.length; ++x) {
            E value = eArray[x];

            Optional<T> retOpt = get(reference, x);

            if (retOpt.isPresent()) {
                return retOpt;
            }
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> offSetFirst(Reference<T> reference, int offset) {

        int off = 0;

        for (int x = 0; x < eArray.length; ++x) {
            E value = eArray[x];

            Optional<T> retOpt = get(reference, x);

            if (retOpt.isPresent()) {
                if (off < offset) {
                    ++off;
                } else {
                    return retOpt;
                }
            }
        }

        return Optional.empty();
    }

    public E[] getArray() {
        return eArray;
    }
}
