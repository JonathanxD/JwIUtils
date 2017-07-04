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
package com.github.jonathanxd.iutils.collectionsw;

import com.github.jonathanxd.iutils.collection.CollectionUtils;
import com.github.jonathanxd.iutils.collectionsw.impl.ArrayListW;
import com.github.jonathanxd.iutils.collectionsw.impl.BiSwitchingIteratorW;
import com.github.jonathanxd.iutils.collectionsw.impl.LinkedListW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaBackedIteratorW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaWrappedCollectionW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutListW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutSetW;
import com.github.jonathanxd.iutils.collectionsw.mutable.BiDiIndexedMutIteratorW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableIteratorW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableListW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSetW;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class CollectionsW {


    @Test
    public void test() {
        CollectionW<String> stringTest = new JavaWrappedCollectionW<>(new ArrayList<>(), ArrayList::new);

        System.out.println(stringTest);
        System.out.println(stringTest.add("Hey"));
        System.out.println(stringTest.add("f").add("x"));
        System.out.println(stringTest);
        System.out.println(stringTest.head());
        System.out.println(stringTest.tail().add("Hey"));

    }

    @Test
    public void test2() {
        ListW<String> w = LinkedListW.fromCollection(new ArrayListW<String>().add("Hey").add("Hey2"));

        ListW<String> hey = w.head().prepend("Hey");

        System.out.println(hey);
        System.out.println(w);
        System.out.println(w.head());

        BiDiIndexedIteratorW<String> iterator = w.iterator();

        boolean flag = false;
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println("N: " + next);

            if (next.equals("Hey2") && !flag) {
                flag = true;
                System.out.println("P: " + iterator.previous());
            }

        }

        while (iterator.hasPrevious()) {
            String previous = iterator.previous();
            System.out.println("Prev: " + previous);
        }
    }

    @Test
    public void test3() {
        ListW<String> nome = LinkedListW.empty();

        ListW<String> prepend = nome.prepend("Viana").prepend("Pedro").prepend("Joao");

        System.out.println(nome);
        System.out.println(prepend);
    }

    @Test
    public void test4() {
        ListW<String> nome = LinkedListW.empty();

        ListW<String> prepend = nome.append("Joao").append("Pedro").append("Viana");

        System.out.println(nome);
        System.out.println(prepend);
    }

    @Test
    public void test5() {
        ListW<Integer> nome = LinkedListW.empty();

        for (int i = 0; i < 1000; ++i) {
            nome = nome.prepend(i).prepend(i * 2).prepend(i + 1);
        }

        System.out.println(nome);
    }

    @Test
    public void test6() {
        ListW<Integer> nome = LinkedListW.empty();

        for (int i = 0; i < 1000; ++i) {
            nome = nome.append(i).append(i * 2).append(i + 1);
        }

        System.out.println(nome);
    }

    @Test
    public void test7() {
        ListW<String> lst = LinkedListW.single("Potato")
                .add("Tomato")
                .add("Orange");

        System.out.println(lst.remove("Tomato"));
    }

    @Test
    public void test8() {
        IteratorW<String> ite = new JavaBackedIteratorW<>(CollectionUtils.listOf(
                "A",
                "B",
                "E",
                "F"
        ).iterator());

        IteratorW<String> ite2 = new JavaBackedIteratorW<>(CollectionUtils.listOf(
                "C",
                "D"
        ).iterator());

        IteratorW<String> bi = new BiSwitchingIteratorW<>(ite, ite2, 1);

        bi.forEachRemaining(System.out::println);
    }


    @Test
    public void test9() {
        ListW<String> l = LinkedListW.single("A");

        Assert.assertEquals(1, l.size());

        Assert.assertEquals(2, l.add("X").size());
        Assert.assertEquals("[A, X]", l.add("X").toString());
        Assert.assertEquals(2, l.prepend("X").size());
        Assert.assertEquals("[X, A]", l.prepend("X").toString());

        Assert.assertEquals(3, l.append(new ArrayListW<String>().add("N").add("T")).size());

        ListW<String> prepend = l.prepend(new ArrayListW<String>().add("N").add("T"));

        Assert.assertEquals(3, prepend.size());
        Assert.assertEquals(2, prepend.remove("A").size());
        Assert.assertEquals(1, prepend.removeAll(new ArrayListW<String>().add("N").add("T")).size());

        Assert.assertEquals(3, l.addAll(1, new ArrayListW<String>().add("N").add("T")).size());

        Assert.assertEquals(3, l.addAll(0, new ArrayListW<String>().add("N").add("T")).size());

        ArrayList<String> s = new ArrayList<>();

        s.add("A");
        s.addAll(1, CollectionUtils.listOf("N", "T"));
        s.addAll(2, CollectionUtils.listOf("V", "D"));

        ListW<String> all = l.addAll(1, new ArrayListW<String>().add("N").add("T"))
                .addAll(2, new ArrayListW<String>().add("V").add("D"));

        Assert.assertEquals("[A, N, V, D, T]", all.toString());

        Assert.assertEquals(s.toString(), all.toString());

        Assert.assertEquals("[N, T, A]", l.addAll(0, new ArrayListW<String>().add("N").add("T")).toString());

        List<String> strings = s.subList(1, 4);

        Assert.assertEquals(strings.toString(), all.subList(1, 4).toString());

    }

    @Test
    public void test10() {
        ListW<String> l = LinkedListW.single("A").append("X").add("Y");

        ListW<String> l2 = LinkedListW.single("Z").append("V").add("V");

        l.append(l2);

        Assert.assertEquals("[A, X, Y, Z, V, V]", l.append(l2).toString());
    }

    @Test
    public void mutableListTest() {
        MutableListW<String> listW = new JavaBackedMutListW<>(new LinkedList<>());

        listW.add("B");
        listW.add("D");
        listW.add("E");
        listW.add("F");
        listW.add("G");
        listW.add("HHH");
        listW.add("I");
        listW.add("J");

        listW.add(0, "A");
        listW.add(2, "C");

        Assert.assertEquals("[A, B, C, D, E, F, G, HHH, I, J]", listW.toString());

        BiDiIndexedMutIteratorW<String> iterator = listW.iterator();

        while(iterator.hasNext()) {
            String next = iterator.next();

            if(next.equals("E"))
                iterator.remove();

        }

        Assert.assertEquals("[A, B, C, D, F, G, HHH, I, J]", listW.toString());

        listW.remove("D");

        Assert.assertEquals("[A, B, C, F, G, HHH, I, J]", listW.toString());

        listW.filter(s -> s.length() > 2);

        Assert.assertEquals("[HHH]", listW.toString());

        listW.addAll(LinkedListW.fromArray(new String[]{"K", "L", "M"}));

        Assert.assertEquals("[HHH, K, L, M]", listW.toString());

        listW.getEntry(1).add("X");

        Assert.assertEquals("[HHH, X, K, L, M]", listW.toString());

        listW.prepend("A");

        Assert.assertEquals("[A, HHH, X, K, L, M]", listW.toString());
    }

    @Test
    public void mutableSetTest() {
        MutableSetW<String> setW = new JavaBackedMutSetW<>(new LinkedHashSet<>());

        setW.add("A");
        setW.add("B");
        setW.add("C");
        setW.add("D");
        setW.add("E");
        setW.add("F");
        setW.add("G");
        setW.add("HHH");
        setW.add("I");
        setW.add("J");


        Assert.assertEquals("[A, B, C, D, E, F, G, HHH, I, J]", setW.toString());

        MutableIteratorW<String> iterator = setW.iterator();

        while(iterator.hasNext()) {
            String next = iterator.next();

            if(next.equals("E"))
                iterator.remove();

        }

        Assert.assertEquals("[A, B, C, D, F, G, HHH, I, J]", setW.toString());

        setW.remove("D");

        Assert.assertEquals("[A, B, C, F, G, HHH, I, J]", setW.toString());

        setW.filter(s -> s.length() > 2);

        Assert.assertEquals("[HHH]", setW.toString());

        setW.addAll(LinkedListW.fromArray(new String[]{"K", "L", "M"}));

        Assert.assertEquals("[HHH, K, L, M]", setW.toString());

        setW.prepend("A");

        Assert.assertEquals("[A, HHH, K, L, M]", setW.toString());
    }

}
