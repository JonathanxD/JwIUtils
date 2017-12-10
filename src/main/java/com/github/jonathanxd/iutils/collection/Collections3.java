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
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.collection.immutable.ImmutableList;
import com.github.jonathanxd.iutils.collection.immutable.ImmutableSet;
import com.github.jonathanxd.iutils.collection.wrapper.WrapperCollections;
import com.github.jonathanxd.iutils.exception.LimitExceededException;
import com.github.jonathanxd.iutils.list.PredicateList;
import com.github.jonathanxd.iutils.list.PredicateWrappedFailingList;
import com.github.jonathanxd.iutils.list.PredicateWrappedList;
import com.github.jonathanxd.iutils.list.SizedJavaList;
import com.github.jonathanxd.iutils.list.StaticList;
import com.github.jonathanxd.iutils.list.UniqueList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Collection utilities.
 */
public class Collections3 {

    /**
     * Gets first element of {@code iterable}.
     *
     * @param iterable Iterable to get first element.
     * @param <E>      Type of element.
     * @return First element of {@code iterable}, or {@code null} if {@code iterable} is empty.
     */
    public static <E> E first(Iterable<E> iterable) {
        Iterator<E> iterator = iterable.iterator();

        if (iterator.hasNext())
            return iterator.next();

        return null;
    }

    /**
     * Gets first element of {@code list}.
     *
     * @param list Iterable to get first element.
     * @param <E>  Type of element.
     * @return First element of {@code list}, or {@code null} if {@code list} is empty.
     */
    public static <E> E first(List<E> list) {
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Gets first element of {@code deque}.
     *
     * @param deque Iterable to get first element.
     * @param <E>   Type of element.
     * @return First element of {@code deque}, or {@code null} if {@code deque} is empty.
     */
    public static <E> E first(Deque<E> deque) {
        return deque.isEmpty() ? null : deque.getFirst();
    }

    /**
     * Gets first element of {@code list}.
     *
     * @param list Iterable to get first element.
     * @param <E>  Type of element.
     * @return First element of {@code list}, or {@code null} if {@code list} is empty.
     */
    public static <E> E first(LinkedList<E> list) {
        return list.isEmpty() ? null : list.getFirst();
    }

    /**
     * Gets last element of {@code iterable}.
     *
     * @param iterable Iterable to get last element.
     * @param <E>      Type of element.
     * @return Last element of {@code iterable}, or {@code null} if {@code iterable} is empty.
     */
    public static <E> E last(Iterable<E> iterable) {
        Iterator<E> iterator = iterable.iterator();

        while (iterator.hasNext()) {
            E e = iterator.next();
            if (!iterator.hasNext())
                return e;
        }

        return null;
    }

    /**
     * Gets last element of {@code list}.
     *
     * @param list Iterable to get last element.
     * @param <E>  Type of element.
     * @return Last element of {@code list}, or {@code null} if {@code list} is empty.
     */
    public static <E> E last(List<E> list) {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    /**
     * Gets last element of {@code deque}.
     *
     * @param deque Iterable to get last element.
     * @param <E>   Type of element.
     * @return Last element of {@code deque}, or {@code null} if {@code deque} is empty.
     */
    public static <E> E last(Deque<E> deque) {
        return deque.isEmpty() ? null : deque.getLast();
    }

    /**
     * Gets last element of {@code list}.
     *
     * @param list Iterable to get last element.
     * @param <E>  Type of element.
     * @return Last element of {@code list}, or {@code null} if {@code list} is empty.
     */
    public static <E> E last(LinkedList<E> list) {
        return list.isEmpty() ? null : list.getLast();
    }

    /**
     * Returns a new list with both {@code element} and {@code list} elements.
     *
     * @param element First element to add to new list.
     * @param list    List with elements to add after {@code element}.
     * @param <E>     Element type.
     * @return A new list with both {@code element} and {@code list} elements.
     */
    public static <E> List<E> prepend(E element, List<E> list) {
        return Collections3.prepend(element, list, ArrayList::new);
    }

    /**
     * Returns a new list with elements of both {@code first} and {@code second} list.
     *
     * @param first  List with element to add before {@code second}.
     * @param second List with elements to add after {@code first}.
     * @param <E>    Element type.
     * @return A new list with elements of both {@code first} and {@code second} list.
     */
    public static <E> List<E> prepend(List<E> first, List<E> second) {
        return Collections3.prepend(first, second, ArrayList::new);
    }

    /**
     * Returns a new list with both {@code element} and {@code list} elements.
     *
     * @param element First element to add to new list.
     * @param list    List with elements to add after {@code element}.
     * @param factory Factory of new list (must be mutable).
     * @param <E>     Element type.
     * @return A new list with both {@code element} and {@code list} elements.
     */
    public static <E> List<E> prepend(E element, List<E> list,
                                      Supplier<? extends List<E>> factory) {
        List<E> newList = factory.get();
        newList.add(element);
        newList.addAll(list);
        return newList;
    }

    /**
     * Returns a new list with elements of both {@code first} and {@code second} list.
     *
     * @param first   List with element to add before {@code second}.
     * @param second  List with elements to add after {@code first}.
     * @param factory Factory of new list (must be mutable).
     * @param <E>     Element type.
     * @return A new list with elements of both {@code first} and {@code second} list.
     */
    public static <E> List<E> prepend(List<E> first, List<E> second,
                                      Supplier<? extends List<E>> factory) {
        List<E> newList = factory.get();
        newList.addAll(first);
        newList.addAll(second);
        return newList;
    }

    /**
     * Returns {@code target} list with {@code element} prepended to it.
     *
     * @param element Element to prepend to {@code target} list.
     * @param <E>     Element type.
     * @return {@code target} list with {@code element} prepended to it.
     */
    public static <E> List<E> prependTo(List<E> target, E element) {
        target.add(0, element);
        return target;
    }

    /**
     * Returns {@code target} list with {@code elements} prepended to it.
     *
     * @param elements Elements to prepend to {@code target} list.
     * @param <E>      Element type.
     * @return {@code target} list with {@code elements} prepended to it.
     */
    public static <E> List<E> prependTo(List<E> target, List<E> elements) {
        target.addAll(0, elements);
        return target;
    }

    /**
     * Creates a {@link T Sequence} of {@link E}.
     *
     * @param factory  Factory method to create sequence.
     * @param adder    Sequence adding method.
     * @param elements Array Elements to add to sequence.
     * @param <E>      Element type.
     * @param <T>      Sequence type (non-java)
     * @return Created {@link T Sequence} with {@code elements}.
     */
    @SafeVarargs
    public static <E, T> T sequenceOf(Supplier<T> factory, BiConsumer<T, E> adder, E... elements) {
        T sequence = factory.get();

        for (E element : elements) {
            adder.accept(sequence, element);
        }

        return sequence;
    }

    /**
     * Creates a {@link Collection} of {@link E} and add {@code elements} to the {@link
     * Collection}.
     *
     * @param factory  Collection factory method.
     * @param elements Elements to add to collection.
     * @param <E>      Element type.
     * @param <T>      Collection type.
     * @return {@link T Collection} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E, T extends Collection<E>> T collectionOf(Supplier<T> factory, E... elements) {
        T collection = factory.get();

        Collections.addAll(collection, elements);

        return collection;
    }

    /**
     * Creates a {@link List} of {@link E} and add {@code elements} to the {@link List}.
     *
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link List} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> List<E> listOf(E... elements) {
        List<E> list = new ArrayList<>();

        Collections.addAll(list, elements);

        return list;
    }

    /**
     * Creates a {@link ImmutableList} of {@link E} and add {@code elements} to the {@link
     * ImmutableList}.
     *
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link ImmutableList} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> ImmutableList<E> immutableListOf(E... elements) {
        List<E> list = new ArrayList<>();

        Collections.addAll(list, elements);

        return WrapperCollections.immutableList(list);
    }

    /**
     * Creates a {@link ImmutableSet} of {@link E} and add {@code elements} to the {@link
     * ImmutableSet}.
     *
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link ImmutableSet} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> ImmutableSet<E> immutableSetOf(E... elements) {
        Set<E> set = new HashSet<>();

        Collections.addAll(set, elements);

        return WrapperCollections.immutableSet(set);
    }

    /**
     * Creates a {@link StaticList} of {@link E} and add {@code elements} to the {@link
     * StaticList}.
     *
     * @param type     Type of elements.
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link StaticList} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> StaticList<E> staticListOf(Class<E> type, E... elements) {
        StaticList<E> list = StaticList.createStaticListOf(type, elements.length);


        for (E element : elements) {
            list.add(element);
        }

        return list;
    }

    /**
     * Creates a {@link SizedJavaList} of {@link E} and add {@code elements} to the {@link
     * SizedJavaList}.
     *
     * @param maxSize  Max size of list.
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link SizedJavaList} of {@link E} with {@code elements}.
     * @throws LimitExceededException If the input {@code elements} has more elements than this
     *                                sized list can carry.
     */
    @SafeVarargs
    public static <E> SizedJavaList<E> sizedListOf(int maxSize, E... elements) throws LimitExceededException {
        List<E> list = new ArrayList<>(elements.length > maxSize ? elements.length : maxSize);

        Collections.addAll(list, elements);

        return new SizedJavaList<>(list, maxSize);
    }

    /**
     * Create a {@link Set} of {@link E} and add {@code elements} to the {@link Set}.
     *
     * @param elements Elements to add to set.
     * @param <E>      Element type.
     * @return {@link Set} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> Set<E> setOf(E... elements) {
        Set<E> set = new HashSet<>();

        Collections.addAll(set, elements);

        return set;
    }

    /**
     * Create a {@link LinkedHashSet} of {@link E} and add {@code elements} to the {@link
     * LinkedHashSet}.
     *
     * @param elements Elements to add to set.
     * @param <E>      Element type.
     * @return {@link LinkedHashSet} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> LinkedHashSet<E> linkedSetOf(E... elements) {
        LinkedHashSet<E> set = new LinkedHashSet<>();

        Collections.addAll(set, elements);

        return set;
    }

    // Predicate

    /**
     * Creates a {@link UniqueList} of {@link E} and add {@code elements} to the {@link List}.
     *
     * @param elements Elements to add to list.
     * @param <E>      Element type.
     * @return {@link UniqueList} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> UniqueList<E> uniqueListOf(E... elements) {
        UniqueList<E> list = new UniqueList<>();

        Collections.addAll(list, elements);

        return list;
    }

    /**
     * Creates a {@link PredicateList} of {@link E} with specified acceptor {@code predicate} and
     * add {@code elements} to the {@link List}.
     *
     * @param predicate Predicate to test elements to add.
     * @param elements  Elements to add to list.
     * @param <E>       Element type.
     * @return {@link PredicateList} of {@link E} with {@code elements}.
     */
    @SafeVarargs
    public static <E> PredicateList<E> predicateListOf(Predicate<E> predicate, E... elements) {
        PredicateList<E> list = new PredicateWrappedList<>(predicate);

        Collections.addAll(list, elements);

        return list;
    }

    /**
     * Creates a {@link PredicateList} of {@link E} with specified acceptor {@code predicate} and
     * add {@code elements} to the {@link List}. The created list throws an exception if added
     * elements does not match predicate.
     *
     * @param predicate Predicate to test elements.
     * @param elements  Elements to add to list.
     * @param <E>       Element type.
     * @return {@link PredicateList} of {@link E} with {@code elements} that throws an exception if
     * added elements does not match predicate.
     */
    @SafeVarargs
    public static <E> PredicateList<E> predicateFailingListOf(Predicate<E> predicate, E... elements) {
        PredicateList<E> list = new PredicateWrappedFailingList<>(predicate);

        Collections.addAll(list, elements);

        return list;
    }
}
