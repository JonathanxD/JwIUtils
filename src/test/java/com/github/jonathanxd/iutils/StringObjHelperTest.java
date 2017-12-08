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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.map.MapUtils;
import com.github.jonathanxd.iutils.string.StringObjHelper;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class StringObjHelperTest {

    @Test
    public void listTextHelper() {
        List<Object> objects = StringObjHelper.parseStringList("[Hello, read, {p=This}, {p=may}, {p=be}, {p=useful}]");

        Assert.assertEquals("Hello", objects.get(0));
        Assert.assertEquals("read", objects.get(1));
        Assert.assertEquals(MapUtils.mapOf("p", "This"), objects.get(2));
        Assert.assertEquals(MapUtils.mapOf("p", "may"), objects.get(3));
        Assert.assertEquals(MapUtils.mapOf("p", "be"), objects.get(4));
        Assert.assertEquals(MapUtils.mapOf("p", "useful"), objects.get(5));
    }

    @Test
    public void mapTextHelper() {
        Map<Object, Object> objects = StringObjHelper.parseStringMap("{Hello=read, v=[This, may, be, useful], y={h=a}}");

        Assert.assertEquals("read", objects.get("Hello"));
        Assert.assertEquals(Collections3.listOf("This", "may", "be", "useful"), objects.get("v"));
        Assert.assertEquals(MapUtils.mapOf("h", "a"), objects.get("y"));
    }

    @Test
    public void propertyHelper() {
        Map<String, String> objects = StringObjHelper.parsePropertyMap("a.b.c= ada\na=b\n  h.e=b\na.b=b.c=d");

        Assert.assertEquals(" ada", objects.get("a.b.c"));
        Assert.assertEquals("b", objects.get("a"));
        Assert.assertEquals("b", objects.get("  h.e"));
        Assert.assertEquals("b.c=d", objects.get("a.b"));
        Assert.assertNull(objects.get("  "));
    }
}
