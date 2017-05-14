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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.collection.CollectionUtils;
import com.github.jonathanxd.iutils.collection.view.ViewCollection;
import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.collection.view.ViewList;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ViewCollectionTest {

    @Test
    public void viewTest() {
        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");

        ViewCollection<String, String> collection = ViewCollections.collection(list);

        collection.forEach(System.out::println);

        System.out.println("After D");

        list.add("D");

        collection.forEach(System.out::println);
    }

    @Test
    public void mappingTest() {
        List<List<String>> list = new ArrayList<>();

        list.add(CollectionUtils.listOf("A", "B", "C"));
        list.add(CollectionUtils.listOf("D", "E", "F"));
        list.add(CollectionUtils.listOf("G", "I", "J"));

        ViewCollection<List<String>, String> collection = ViewCollections.collectionMapped(
                list,
                (e, origin) -> e.iterator(),
                (o) -> list.size() > 0 ? list.get(list.size() - 1).add(o) : list.add(CollectionUtils.listOf(o)),
                (o) -> firstRemove(list, o));

        collection.forEach(System.out::println);

        System.out.println("After K, L, M");

        collection.add("K");
        collection.add("L");
        collection.add("M");

        collection.forEach(System.out::println);
    }

    @Test
    public void listTest() {

        List<List<String>> list = new ArrayList<>();


        list.add(CollectionUtils.listOf("A", "B", "C"));
        list.add(CollectionUtils.listOf("D", "E", "F"));
        list.add(CollectionUtils.listOf("G", "I", "J"));

        ViewList<List<String>, String> view = ViewCollections.listMapped(list,
                (strings, listListIterator) -> strings.listIterator(),
                y -> list.size() > 0 ? list.get(list.size() - 1).add(y) : list.add(CollectionUtils.listOf(y)),
                y -> firstRemove(list, y)
        );

        ListIterator<String> viewIter = view.listIterator(0);
        viewIter.add("0");

        System.out.println(list);
    }

    private boolean firstRemove(List<List<String>> list, Object o) {
        for (List<String> strings : list) {
            if(strings.remove(o))
                return true;
        }

        return false;
    }

    private boolean addHelper(ViewList<List<String>, String> view, List<List<String>> list, int index, String value) {
        if(index == -1) {
            if(list.size() > 0) {
                list.get(list.size() - 1).add(value);
            } else {
                list.add(CollectionUtils.listOf(value));
            }

            return true;
        }

        view.listIterator(index).add(value);

        return true;
    }

}
