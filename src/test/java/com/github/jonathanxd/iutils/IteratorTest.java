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
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IteratorTest {
    @Test
    public void whileIteratorTest() {
        List<String> list = Collections3.listOf("A", "B", "C", "D", "E");

        Iterator<String> d = IteratorUtil.iterateWhile(c -> !c.equals("D"), list.iterator());

        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("A", d.next());
        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("B", d.next());
        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("C", d.next());
        Assert.assertFalse(d.hasNext());
    }

    @Test
    public void whileListIteratorTest() {
        List<String> list = Collections3.listOf("A", "B", "C", "D", "E");

        ListIterator<String> d = IteratorUtil.listIterateWhile(c -> !c.equals("D"), list.listIterator());

        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("A", d.next());
        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("B", d.next());
        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("C", d.next());
        Assert.assertFalse(d.hasNext());

        Assert.assertTrue(d.hasPrevious());
        Assert.assertEquals("C", d.previous());
        Assert.assertTrue(d.hasPrevious());
        Assert.assertEquals("B", d.previous());
        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("B", d.next());
        Assert.assertTrue(d.hasNext());
        Assert.assertEquals("C", d.next());
        Assert.assertTrue(d.hasPrevious());
        Assert.assertEquals("C", d.previous());
        Assert.assertTrue(d.hasPrevious());
        Assert.assertEquals("B", d.previous());
        Assert.assertTrue(d.hasPrevious());
        Assert.assertEquals("A", d.previous());
        Assert.assertFalse(d.hasPrevious());
        Assert.assertTrue(d.hasNext());
    }
}
