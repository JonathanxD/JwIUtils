/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
