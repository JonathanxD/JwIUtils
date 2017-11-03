/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.comp;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.type.TypeInfo;
import com.github.jonathanxd.iutils.type.TypeInfoSortComparator;
import com.github.jonathanxd.iutils.type.TypeParameterProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TypeInfoComparatorTest {

    private static final TypeInfoSortComparator TYPE_INFO_SORT_COMPARATOR =
            new TypeInfoSortComparator();

    @Test
    public void typeInfoComparatorTest() {


        TypeInfo<List<String>> typeInfo1 = new TypeParameterProvider<List<String>>() {
        }.createTypeInfo();
        TypeInfo<List<CharSequence>> typeInfo2 = new TypeParameterProvider<List<CharSequence>>() {
        }.createTypeInfo();

        List<TypeInfo<?>> t = Collections3.listOf(typeInfo1, typeInfo2);

        t.sort(TYPE_INFO_SORT_COMPARATOR);

        Assert.assertEquals(t.get(0), typeInfo2);
        Assert.assertEquals(t.get(1), typeInfo1);

        TypeInfo<Collection<String>> typeInfo3 = new TypeParameterProvider<Collection<String>>() {
        }.createTypeInfo();

        TypeInfo<Collection<CharSequence>> typeInfo4 = new TypeParameterProvider<Collection<CharSequence>>() {
        }.createTypeInfo();

        t = Collections3.listOf(typeInfo1, typeInfo3, typeInfo4);

        t.sort(TYPE_INFO_SORT_COMPARATOR);

        Assert.assertEquals(t.get(0), typeInfo4);
        Assert.assertEquals(t.get(1), typeInfo3);
        Assert.assertEquals(t.get(2), typeInfo1);

        Assert.assertEquals("[Collection<CharSequence>, Collection<String>, List<String>]", t.toString());
    }

    @Test
    public void typeInfoComparatorTestMultiRel() {
        TypeInfo<Map<Entity, String>> typeInfo1 = new TypeParameterProvider<Map<Entity, String>>() {
        }.createTypeInfo();

        TypeInfo<Map<Person, String>> typeInfo2 = new TypeParameterProvider<Map<Person, String>>() {
        }.createTypeInfo();

        // First is less than second
        Assert.assertEquals(-1, TYPE_INFO_SORT_COMPARATOR.compare(typeInfo1, typeInfo2));

    }

    @Test
    public void typeInfoComparatorTestMultiRel2() {
        TypeInfo<Map<Entity, CharSequence>> typeInfo1 = new TypeParameterProvider<Map<Entity, CharSequence>>() {
        }.createTypeInfo();

        TypeInfo<Map<Person, String>> typeInfo2 = new TypeParameterProvider<Map<Person, String>>() {
        }.createTypeInfo();

        // First is less than second
        Assert.assertEquals(-1, TYPE_INFO_SORT_COMPARATOR.compare(typeInfo1, typeInfo2));

    }

    @Test
    public void typeInfoComparatorTestMultiRel3() {
        TypeInfo<Map<Entity, CharSequence>> typeInfo1 = new TypeParameterProvider<Map<Entity, CharSequence>>() {
        }.createTypeInfo();

        TypeInfo<Map<Person, CharSequence>> typeInfo2 = new TypeParameterProvider<Map<Person, CharSequence>>() {
        }.createTypeInfo();

        // First is less than second
        Assert.assertEquals(-1, TYPE_INFO_SORT_COMPARATOR.compare(typeInfo1, typeInfo2));
    }

    @Test
    public void typeInfoComparatorTestMultiRel4() {
        TypeInfo<Map<Entity, String>> typeInfo1 = new TypeParameterProvider<Map<Entity, String>>() {
        }.createTypeInfo();

        TypeInfo<Map<Person, CharSequence>> typeInfo2 = new TypeParameterProvider<Map<Person, CharSequence>>() {
        }.createTypeInfo();

        // Both are equals
        Assert.assertEquals(0, TYPE_INFO_SORT_COMPARATOR.compare(typeInfo1, typeInfo2));
    }

    public interface Entity {

    }

    public interface Person extends Entity {

    }
}
