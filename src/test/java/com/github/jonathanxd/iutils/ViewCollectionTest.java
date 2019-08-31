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
import com.github.jonathanxd.iutils.collection.view.ViewCollection;
import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.collection.view.ViewList;
import com.github.jonathanxd.iutils.collection.view.ViewSet;
import com.github.jonathanxd.iutils.collection.view.ViewUtils;
import com.github.jonathanxd.iutils.function.consumer.IntObjConsumer;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.iterator.DelegatedListIterator;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class ViewCollectionTest {

    @Test
    public void viewTest() {
        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");

        ViewCollection<String, String> collection = ViewCollections.collection(list);

        list.add("D");

        Assert.assertEquals(Collections3.listOf("A", "B", "C", "D").toString(), collection.toString());
    }

    @Test
    public void viewSetTest() {
        Set<String> set = new HashSet<>();

        ViewSet<String, String> view = ViewCollections.setMappedMulti(set,
                (s, stringIterator) -> IteratorUtil.single(s),
                s -> s.length() > 2 && set.add(s),
                set::remove);

        view.add("ViewSet");
        view.add("V");

        Assert.assertTrue(set.size() == 1);

    }

    @Test
    public void mappedViewSetTest() {
        Set<String> set = new HashSet<>();

        ViewSet<String, Integer> view = ViewCollections.setMappedMulti(set,
                (s, stringIterator) -> ViewUtils.mapped(s, stringIterator, Integer::parseInt),
                i -> i % 2 == 0 && set.add(i.toString()),
                i -> set.remove(i.toString()));

        view.add(5);
        view.add(10);
        view.add(90);

        Assert.assertTrue(set.size() == 2);
    }

    @Test
    public void mappedListTest() {
        List<String> list = new ArrayList<>();

        Predicate<Integer> addPred = i -> list.add(i.toString());
        Predicate<Integer> removePred = i -> list.remove(i.toString());
        IntObjConsumer<Integer> set = (i, element) -> list.set(i, element.toString());

        ViewList<String, Integer> view = ViewCollections.listMappedMulti(list,
                (s, stringIterator) -> new DelegatedListIterator<Integer>() {
                    ListIterator<Integer> delegate = ViewUtils.mapped(s, stringIterator, Integer::parseInt, Object::toString);

                    @Override
                    public void add(Integer integer) {
                        addPred.test(integer);
                    }

                    @Override
                    public void set(Integer integer) {
                        int index = this.getDelegate().nextIndex() - 1;
                        set.accept(index, integer);
                    }

                    @Override
                    protected ListIterator<Integer> getDelegate() {
                        return delegate;
                    }
                },
                addPred,
                removePred);

        for (int i = 0; i < 20; ++i)
            view.add(i);

        ListIterator<Integer> iterator = view.listIterator();

        while (iterator.hasNext()) {
            int next = iterator.next();

            if(next % 2 != 0)
                iterator.set(next - 1);
        }

        Assert.assertEquals("[0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 14, 14, 16, 16, 18, 18]", list.toString());
    }

    @Test
    public void mappingTest() {
        List<List<String>> list = new ArrayList<>();

        list.add(Collections3.listOf("A", "B", "C"));
        list.add(Collections3.listOf("D", "E", "F"));
        list.add(Collections3.listOf("G", "I", "J"));

        ViewCollection<List<String>, String> collection = ViewCollections.collectionMappedMulti(
                list,
                (e, origin) -> e.iterator(),
                (o) -> list.size() > 0 ? list.get(list.size() - 1).add(o) : list.add(Collections3.listOf(o)),
                (o) -> firstRemove(list, o));

        collection.add("K");
        collection.add("L");
        collection.add("M");

        Assert.assertEquals(
                Collections3.listOf("A", "B", "C", "D", "E", "F", "G", "I", "J", "K", "L", "M").toString(),
                collection.toString());
    }

    @Test
    public void listTest() {

        List<List<String>> list = new ArrayList<>();

        list.add(Collections3.listOf("A", "B", "C"));
        list.add(Collections3.listOf("D", "E", "F"));
        list.add(Collections3.listOf("G", "I", "J"));

        ViewList<List<String>, String> view = ViewCollections.listMappedMulti(list,
                (strings, listListIterator) -> strings.listIterator(),
                y -> list.size() > 0 ? list.get(list.size() - 1).add(y) : list.add(Collections3.listOf(y)),
                y -> firstRemove(list, y)
        );

        ListIterator<String> viewIter = view.listIterator(0);

        viewIter.add("0");
        // Where is 'H'?
        Assert.assertEquals(Collections3.listOf("0", "A", "B", "C", "D", "E", "F", "G", "I", "J").toString(), view.toString());
    }

    @Test
    public void listAddTest() {

        List<List<String>> list = new ArrayList<>();


        list.add(Collections3.listOf("A", "B", "C"));
        list.add(Collections3.listOf("D", "E", "F"));
        list.add(Collections3.listOf("G", "I", "J"));

        ViewList<List<String>, String> view = ViewCollections.listMappedMulti(list,
                (strings, listListIterator) -> strings.listIterator(),
                y -> list.size() > 0 ? list.get(list.size() - 1).add(y) : list.add(Collections3.listOf(y)),
                y -> firstRemove(list, y)
        );

        view.add(4, "N");

        List<String> lst = Collections3.listOf("A", "B", "C", "D", "E", "F", "G", "I", "J");

        lst.add(4, "N");

        Assert.assertEquals(lst.toString(), view.toString());
    }

    @Test
    public void reverseList() {
        List<String> list = new ArrayList<>();

        list.addAll(Collections3.listOf("1", "2", "3"));
        list.addAll(Collections3.listOf("4", "5", "6"));

        ViewList<String, String> reversed = ViewCollections.reversedList(list);

        Assert.assertEquals("[6, 5, 4, 3, 2, 1]", reversed.toString());

        Assert.assertEquals("4", reversed.remove(2));

        reversed.add(2, "4");

        Assert.assertEquals("[6, 5, 4, 3, 2, 1]", reversed.toString());

        ViewList<String, String> readOnlyList = ViewCollections.readOnlyList(reversed);

        Assert.assertEquals("[6, 5, 4, 3, 2, 1]", readOnlyList.toString());

        try {
            readOnlyList.add(0, "7");
            throw new AssertionError();
        }catch (UnsupportedOperationException ignored) {
        }
    }

    @Test
    public void listMapTest() {

        List<String> list = new ArrayList<>();


        list.addAll(Collections3.listOf("1", "2", "3"));
        list.addAll(Collections3.listOf("4", "5", "6"));

        ViewList<String, Integer> view = ViewCollections.listMappedMulti(list,
                (strings, listListIterator) ->
                        ViewUtils.mapped(strings, listListIterator, Integer::parseInt, Object::toString),
                y -> list.add(y.toString()),
                y -> list.remove(y.toString())
        );

        view.add(9);

        Iterator<Integer> iterator = view.iterator();

        while (iterator.hasNext()) {
            Integer next = iterator.next();

            if (next == 3)
                iterator.remove();
        }

        int index = 0;

        ListIterator<Integer> listIterator = view.listIterator();

        while(listIterator.hasNext()) {
            int i = listIterator.nextIndex();
            int p = listIterator.previousIndex();

            listIterator.next();

            Assert.assertEquals(listIterator.nextIndex(), i + 1);
            Assert.assertEquals(listIterator.previousIndex(), p + 1);

            Assert.assertEquals(index, i);
            Assert.assertEquals(index - 1, p);

            ++index;
        }

        List<String> lst = Collections3.listOf("1", "2", "4", "5", "6", "9");

        Assert.assertEquals(lst.toString(), view.toString());
    }

    private boolean firstRemove(List<List<String>> list, Object o) {
        for (List<String> strings : list) {
            if (strings.remove(o))
                return true;
        }

        return false;
    }

    private boolean addHelper(ViewList<List<String>, String> view, List<List<String>> list, int index, String value) {
        if (index == -1) {
            if (list.size() > 0) {
                list.get(list.size() - 1).add(value);
            } else {
                list.add(Collections3.listOf(value));
            }

            return true;
        }

        view.listIterator(index).add(value);

        return true;
    }

}
