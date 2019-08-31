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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PrependAndMergeTest {

    @Test
    public void prependSingleTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prepend(1, numbers);

        Assert.assertEquals(Collections3.listOf(2, 3, 4), numbers);
        Assert.assertEquals(Collections3.listOf(1, 2, 3, 4), prepend);
    }

    @Test
    public void prependListTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prepend(Collections3.listOf(0, 1), numbers);

        Assert.assertEquals(Collections3.listOf(2, 3, 4), numbers);
        Assert.assertEquals(Collections3.listOf(0, 1, 2, 3, 4), prepend);
    }

    @Test
    public void prependSameSingleTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prependTo(numbers, 1);

        Assert.assertTrue(prepend == numbers);
        Assert.assertEquals(Collections3.listOf(1, 2, 3, 4), numbers);
    }

    @Test
    public void prependSameListTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prependTo(numbers, Collections3.listOf(0, 1));

        Assert.assertTrue(prepend == numbers);
        Assert.assertEquals(Collections3.listOf(0, 1, 2, 3, 4), numbers);
    }

    @Test
    public void iteratorMergeTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prepend(Collections3.listOf(0, 1), numbers);

        Iterator<Number> numberIterator = IteratorUtil.mergeIterator(numbers.iterator(), prepend.iterator());

        Assert.assertEquals(2, numberIterator.next());
        Assert.assertEquals(3, numberIterator.next());
        Assert.assertEquals(4, numberIterator.next());
        Assert.assertEquals(0, numberIterator.next());
        Assert.assertEquals(1, numberIterator.next());
        Assert.assertEquals(2, numberIterator.next());
        Assert.assertEquals(3, numberIterator.next());
        Assert.assertEquals(4, numberIterator.next());
        Assert.assertFalse(numberIterator.hasNext());
    }

    @Test
    public void iteratorMergeRemoveTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prepend(Collections3.listOf(0, 1), numbers);

        Iterator<Number> numberIterator = IteratorUtil.mergeIterator(numbers.iterator(), prepend.iterator());

        Assert.assertEquals(2, numberIterator.next());
        Assert.assertEquals(3, numberIterator.next());
        Assert.assertEquals(4, numberIterator.next());
        Assert.assertEquals(0, numberIterator.next());
        numberIterator.remove();
        Assert.assertEquals(1, numberIterator.next());
        Assert.assertEquals(2, numberIterator.next());
        Assert.assertEquals(3, numberIterator.next());
        Assert.assertEquals(4, numberIterator.next());
        Assert.assertFalse(numberIterator.hasNext());

        Assert.assertEquals(Collections3.listOf(1, 2, 3, 4), prepend);
    }


    @Test
    public void listIteratorMergeTest() {
        List<Number> numbers = Collections3.listOf(2, 3, 4);
        List<Number> prepend = Collections3.prepend(Collections3.listOf(0, 1), numbers);

        ListIterator<Number> numbersIter = numbers.listIterator();

        ListIterator<Number> numberIterator = IteratorUtil.mergeListIterator(numbersIter, prepend.listIterator());

        Assert.assertEquals(2, numberIterator.next());
        Assert.assertEquals(3, numberIterator.next());
        Assert.assertEquals(4, numberIterator.next());
        Assert.assertEquals(0, numberIterator.next());
        Assert.assertEquals(0, numberIterator.previous());
        Assert.assertEquals(0, numberIterator.next());
        Assert.assertEquals(1, numberIterator.next());
        numbersIter.add(9);
        numbersIter.previous();
        Assert.assertEquals(9, numberIterator.next()); // Documented behavior
        Assert.assertEquals(1, numberIterator.previous()); // Documented behavior

        Assert.assertEquals(Collections3.listOf(2, 3, 4, 9), numbers);

        Assert.assertEquals(1, numberIterator.next());
        Assert.assertEquals(2, numberIterator.next());
        numberIterator.set(6);
        Assert.assertEquals(6, numberIterator.previous());
        Assert.assertEquals(6, numberIterator.next());
        Assert.assertEquals(3, numberIterator.next());
        Assert.assertEquals(4, numberIterator.next());
        Assert.assertFalse(numberIterator.hasNext());

        Assert.assertEquals(Collections3.listOf(0, 1, 6, 3, 4), prepend);
    }
}
