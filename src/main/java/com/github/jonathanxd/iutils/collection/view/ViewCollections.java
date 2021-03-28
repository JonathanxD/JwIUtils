/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.collection.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * View collections factory.
 */
public class ViewCollections {

    /**
     * Creates a simple view collection backing to {@code collection}.
     *
     * @param collection Collection to back operations.
     * @param <E>        Type of elements.
     * @return Simple view collection backing to {@code collection}.
     */
    public static <E> ViewCollection<E, E> collection(Collection<E> collection) {
        return new ViewCollection<>(collection,
                null,
                collection::add,
                collection::remove);
    }

    /**
     * Creates a simple view set backing to {@code set}.
     *
     * @param set Set to back operations.
     * @param <E> Type of elements.
     * @return Simple view set backing to {@code set}.
     */
    public static <E> ViewSet<E, E> set(Set<E> set) {
        return new ViewSet<>(set,
                null,
                set::add,
                set::remove);
    }

    /**
     * Creates a simple view list backing to {@code list}.
     *
     * @param list List to back operations.
     * @param <E>  Type of elements.
     * @return Simple view list backing to {@code list}.
     */
    public static <E> ViewList<E, E> list(List<E> list) {
        return new ViewList<>(list,
                null,
                list::add,
                list::remove);
    }


    /**
     * Creates a simple read only collection backing to {@code collection}.
     *
     * @param collection Collection to back operations.
     * @param <E>        Type of elements.
     * @return Simple read only collection backing to {@code collection}.
     */
    public static <E> ViewCollection<E, E> readOnlyCollection(Collection<E> collection) {
        return new ViewCollection<>(collection,
                null,
                o -> {
                    throw new UnsupportedOperationException("Read only!");
                },
                o -> {
                    throw new UnsupportedOperationException("Read only!");
                });
    }

    /**
     * Creates a simple read only view set backing to {@code set}.
     *
     * @param set Set to back operations.
     * @param <E> Type of elements.
     * @return Simple read only view set backing to {@code set}.
     */
    public static <E> ViewSet<E, E> readOnlySet(Set<E> set) {
        return new ViewSet<>(set,
                null,
                o -> {
                    throw new UnsupportedOperationException("Read only!");
                },
                o -> {
                    throw new UnsupportedOperationException("Read only!");
                });
    }

    /**
     * Creates a simple read only view list backing to {@code list}.
     *
     * @param list List to back operations.
     * @param <E>  Type of elements.
     * @return Simple read only view list backing to {@code list}.
     */
    public static <E> ViewList<E, E> readOnlyList(List<E> list) {
        return new ViewList<>(list,
                null,
                o -> {
                    throw new UnsupportedOperationException("Read only!");
                },
                o -> {
                    throw new UnsupportedOperationException("Read only!");
                }, false, true);
    }

    /**
     * Creates a simple reversed view list backing to {@code list}.
     *
     * @param list List to back operations.
     * @param <E>  Type of elements.
     * @return Simple reversed view list backing to {@code list}.
     */
    public static <E> ViewList<E, E> reversedList(List<E> list) {
        return new ViewList<>(list,
                null,
                list::add,
                list::remove,
                true,
                false);
    }

    /**
     * Creates a mapped collection backing to {@code collection}.
     *
     * Maps one element to multiple elements.
     *
     * @param collection Original collection.
     * @param mapper     Mapper function (should return iterator to delegate operations).
     * @param add        Element add handler.
     * @param remove     Element remove handler.
     * @param <E>        Element type.
     * @param <Y>        Mapped type.
     * @return Mapped collection backing to {@code collection}.
     */
    public static <E, Y> ViewCollection<E, Y> collectionMappedMulti(Collection<E> collection,
                                                                    BiFunction<E, Iterator<E>, Iterator<Y>> mapper,
                                                                    Predicate<Y> add,
                                                                    Predicate<Y> remove) {
        return new ViewCollection<>(collection,
                mapper,
                add,
                remove);
    }

    /**
     * Creates a mapped collection backing to {@code collection}.
     *
     * @param collection Original collection.
     * @param mapper     Mapper of elements.
     * @param add        Element add handler.
     * @param remove     Element remove handler.
     * @param <E>        Element type.
     * @param <Y>        Mapped type.
     * @return Mapped collection backing to {@code collection}.
     */
    public static <E, Y> ViewCollection<E, Y> collectionMapped(Collection<E> collection,
                                                               Function<E, Y> mapper,
                                                               Predicate<Y> add,
                                                               Predicate<Y> remove) {
        return ViewCollections.collectionMappedMulti(
                collection,
                (e, i) -> ViewUtils.mapped(e, i, mapper),
                add,
                remove
        );
    }

    /**
     * Creates a mapped list backing to {@code list}.
     *
     * Maps one element to multiple elements.
     *
     * @param list   Original list.
     * @param mapper Mapper function (should return list iterator to delegate operations).
     * @param add    Element add handler.
     * @param remove Element remove handler.
     * @param <E>    Element type.
     * @param <Y>    Mapped type.
     * @return Mapped list backing to {@code list}.
     */
    public static <E, Y> ViewList<E, Y> listMappedMulti(List<E> list,
                                                        BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper,
                                                        Predicate<Y> add,
                                                        Predicate<Y> remove) {
        return new ViewList<>(list,
                mapper,
                add,
                remove);
    }

    /**
     * Creates a mapped list backing to {@code list}.
     *
     * @param list   Original list.
     * @param mapper Mapper of elements.
     * @param add    Element add handler.
     * @param remove Element remove handler.
     * @param <E>    Element type.
     * @param <Y>    Mapped type.
     * @return Mapped list backing to {@code list}.
     */
    public static <E, Y> ViewList<E, Y> listMapped(List<E> list,
                                                   Function<E, Y> mapper,
                                                   Function<Y, E> unmapper,
                                                   Predicate<Y> add,
                                                   Predicate<Y> remove) {
        return ViewCollections.listMappedMulti(list, (e, i) -> ViewUtils.mapped(e, i, mapper, unmapper), add, remove);
    }

    /**
     * Creates a mapped set backing to {@code set}.
     *
     * Maps one element to multiple elements.
     *
     * @param set    Original collection.
     * @param mapper Mapper function (should return iterable to delegate operations).
     * @param add    Element add handler.
     * @param remove Element remove handler.
     * @param <E>    Element type.
     * @param <Y>    Mapped type.
     * @return Mapped collection backing to {@code set}.
     */
    public static <E, Y> ViewSet<E, Y> setMappedMulti(Set<E> set,
                                                      BiFunction<E, Iterator<E>, Iterator<Y>> mapper,
                                                      Predicate<Y> add,
                                                      Predicate<Y> remove) {
        return new ViewSet<>(set,
                mapper,
                add,
                remove);
    }

    /**
     * Creates a mapped set backing to {@code set}.
     *
     * @param set    Original collection.
     * @param mapper Mapper of elements.
     * @param add    Element add handler.
     * @param remove Element remove handler.
     * @param <E>    Element type.
     * @param <Y>    Mapped type.
     * @return Mapped collection backing to {@code set}.
     */
    public static <E, Y> ViewSet<E, Y> setMapped(Set<E> set,
                                                 Function<E, Y> mapper,
                                                 Predicate<Y> add,
                                                 Predicate<Y> remove) {
        return ViewCollections.setMappedMulti(set,
                (e, eIterator) -> ViewUtils.mapped(e, eIterator, mapper),
                add,
                remove);
    }

    /**
     * Utility to create flat collections.
     */
    public static class Flat {

        /**
         * Creates a flat collection.
         *
         * @param collection     Collection to create a flat version.
         * @param subCollFactory Collection factory. Used to create new collection to insert
         *                       elements.
         * @param <D>            Collection type.
         * @param <E>            Element type.
         * @param <Y>            Mapped type.
         * @return Flat collection backing to {@code collection}.
         */
        @SuppressWarnings("unchecked")
        public static <D extends Collection<E>, E extends Collection<Y>, Y> E flatCollection(D collection, Function<Y, E> subCollFactory) {
            return ViewCollections.Flat.flatCollection(Type.COLLECTION, collection, subCollFactory);
        }

        /**
         * Creates a flat set.
         *
         * @param set            Set to create a flat version.
         * @param subCollFactory Set factory. Used to create new set to insert elements.
         * @param <D>            Set type.
         * @param <E>            Element type.
         * @param <Y>            Mapped type.
         * @return Flat set backing to {@code set}.
         */
        @SuppressWarnings("unchecked")
        public static <D extends Set<E>, E extends Set<Y>, Y> E flatSet(D set, Function<Y, E> subCollFactory) {
            return ViewCollections.Flat.flatCollection(Type.SET, set, subCollFactory);
        }

        /**
         * Creates a flat list.
         *
         * @param list           List to create a flat version.
         * @param subCollFactory List factory. Used to create new list to insert elements.
         * @param <D>            List type.
         * @param <E>            Element type.
         * @param <Y>            Mapped type.
         * @return Flat list backing to {@code list}.
         */
        @SuppressWarnings("unchecked")
        public static <D extends List<E>, E extends List<Y>, Y> E flatList(D list, Function<Y, E> subCollFactory) {
            return ViewCollections.Flat.flatCollection(Type.LIST, list, subCollFactory);
        }

        /**
         * Creates a flat collection.
         *
         * @param type           Type of the collection. Supported types: {@link List}, {@link Set}
         *                       and {@link Collection}.
         * @param collection     Collection to create a flat version.
         * @param subCollFactory Collection factory. Used to create new collection to insert
         *                       elements.
         * @param <D>            Collection type.
         * @param <E>            Element type.
         * @param <Y>            Mapped type.
         * @return Flat collection backing to {@code collection}.
         */
        @SuppressWarnings("unchecked")
        private static <D extends Collection<E>, E extends Collection<Y>, Y> E flatCollection(Type type, D collection,
                                                                                              Function<Y, E> subCollFactory) {
            E value = null;

            Predicate<Y> add = y -> collection.isEmpty() ? collection.add(subCollFactory.apply(y)) : addToLast(collection, y);
            Predicate<Y> remove = o -> removeFirst(collection, o);

            if (type == Type.COLLECTION) {
                value = (E) ViewCollections.collectionMappedMulti(collection,
                        (e, eIterator) -> e.iterator(),
                        add,
                        remove);
            } else if (type == Type.SET) {
                value = (E) ViewCollections.setMappedMulti((Set<E>) collection,
                        (e, eIterator) -> e.iterator(),
                        add,
                        remove);
            } else if (type == Type.LIST) {
                value = (E) ViewCollections.listMappedMulti((List<List<Y>>) collection,
                        (e, eIterator) -> e.listIterator(),
                        add,
                        remove);
            }

            return Objects.requireNonNull(value, "Cannot create flat collection of type: '" + type + "'!");
        }

        /**
         * Handles addition to flat collection.
         *
         * @param collection Collection.
         * @param y          Element to add.
         * @param <D>        Collection type.
         * @param <E>        Flat type.
         * @param <Y>        Mapped type.
         * @return True if collection changed as result of this operation.
         */
        private static <D extends Collection<E>, E extends Collection<Y>, Y> boolean addToLast(D collection, Y y) {
            Iterator<E> iterator = collection.iterator();

            boolean result = false;

            while (iterator.hasNext()) {
                E next = iterator.next();

                if (!iterator.hasNext())
                    result |= next.add(y);
            }

            return result;
        }

        /**
         * Remove first element found in flat collection, this method also removes empty collections
         * inside original collection.
         *
         * @param collection Collection.
         * @param y          Element to remove.
         * @param <D>        Collection type.
         * @param <E>        Flat type.
         * @param <Y>        Mapped type.
         * @return True if collection changed as result of this operation.
         */
        private static <D extends Collection<E>, E extends Collection<Y>, Y> boolean removeFirst(D collection, Object y) {

            Iterator<E> iterator = collection.iterator();

            while (iterator.hasNext()) {
                E next = iterator.next();

                if (next.remove(y)) {
                    if (next.isEmpty())
                        iterator.remove();

                    return true;
                }
            }

            return false;
        }


        enum Type {
            COLLECTION,
            SET,
            LIST
        }
    }
}
