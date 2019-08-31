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
package com.github.jonathanxd.iutils.comp;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.collection.Comparators3;
import com.github.jonathanxd.iutils.type.TypeInfo;
import com.github.jonathanxd.iutils.type.TypeInfoSortComparator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

public class ComparatorsTest {
    private static final TypeInfoSortComparator TYPE_INFO_SORT_COMPARATOR =
            new TypeInfoSortComparator();

    @Test
    public void comparators3TestFirst() {
        List<Integer> integers = Collections3.listOf(5, 4, 9, 2, 0, 7, 6);

        Assert.assertEquals((Integer) 0, Comparators3.first(Integer::compare, integers));
    }

    @Test
    public void comparators3TestLast() {
        List<Integer> integers = Collections3.listOf(5, 4, 9, 2, 0, 7, 6);

        Assert.assertEquals((Integer) 9, Comparators3.last(Integer::compare, integers));
    }

    @Test
    public void comparators3TestLastType() {
        TypeInfo<?> listOfString = TypeInfo.builderOf(List.class).of(String.class).build();
        TypeInfo<?> collectionOfObject = TypeInfo.builderOf(Collection.class).of(Object.class).build();
        TypeInfo<?> collectionOfCharSequence = TypeInfo.builderOf(Collection.class).of(CharSequence.class).build();
        TypeInfo<?> collectionOfString = TypeInfo.builderOf(Collection.class).of(String.class).build();


        List<TypeInfo<?>> types = Collections3.listOf(
                listOfString,
                collectionOfObject,
                collectionOfCharSequence,
                collectionOfString
        );

        Assert.assertEquals(collectionOfObject, Comparators3.first(TYPE_INFO_SORT_COMPARATOR, types));
        Assert.assertEquals(listOfString, Comparators3.last(TYPE_INFO_SORT_COMPARATOR, types));
    }
}
