/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.arrays;

import java.util.Collection;
import java.util.Enumeration;

/**
 * Arrays IS NOT A COLLECTION, Arrays is an Array representation
 */
public class ImmutableArrays<E> extends Arrays<E> {

    public ImmutableArrays() {
        super();
    }

    public ImmutableArrays(E... values) {
        super(values);
    }

    public ImmutableArrays(Collection<? extends E> collection) {
        super(collection);
    }

    public ImmutableArrays(Iterable<? extends E> iterable) {
        super(iterable);
    }

    public ImmutableArrays(Enumeration<? extends E> enume) {
        super(enume);
    }

    public static <E> ImmutableArrays<E> empty() {
        return new ImmutableArrays<>();
    }

    @Override
    public Arrays<E> add(E value) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }

    @Override
    public Arrays<E> addAll(E[] value) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }

    @Override
    public Arrays<E> remove(E value) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }

    @Override
    public Arrays<E> set(E value, int index) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }


}
