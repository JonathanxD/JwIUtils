/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.comparator.Compared;
import com.github.jonathanxd.iutils.object.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 05/03/16.
 */
public interface Walkable<T> {

    T next();
    boolean hasNext();
    void remove();
    boolean checkIndex();
    void sort(Comparator<? super T> c);
    void walkToEnd();
    Walkable<T> newWithoutState();
    void forEach(Consumer<? super T> consumer);
    void forEach(Consumer<? super T> consumer, Predicate<? super T> until);
    T[] toArray();
    int size();
    int computeSize();
    boolean hasCurrent();
    T getCurrent();
    Walkable<T> clone();


    default List<T> currentAsList() {
        List<T> list = new ArrayList<>();
        clone().forEach(list::add);
        return list;
    }

    default List<T> pollToList() {
        List<T> list = new ArrayList<>();
        this.forEach(list::add);
        return list;
    }

    default List<T> asWithoutStateList() {
        List<T> list = new ArrayList<>();
        newWithoutState().forEach(list::add);
        return list;
    }

    default Compared<T> compare(Comparator<? super T> comparator) {
        List<T> sorted = asWithoutStateList().stream().sorted(comparator).collect(Collectors.toList());
        return new Compared<>(sorted);
    }

    static <K, V> Walkable<Node<K, V>> asList(Map<K, V> map) {
        return asList(Node.fromEntryCollection(map.entrySet()));
    }

    static <K, V> Walkable<Node<K, V>> asList(List<Node<K, V>> nodes) {
        return asList((Collection<Node<K, V>>) nodes);
    }

    static <T> Walkable<T> asList(Walkable<T> walkable) {
        List<T> list  = new ArrayList<>();
        while (walkable.hasNext())
            list.add(walkable.next());
        return asWithoutStateList(list);
    }

    static <T> Walkable<T> asList(Collection<T> collection) {
        return new WalkableList<>(new ArrayList<>(collection));
    }

    static <T> Walkable<T> asWithoutStateList(List<T> list) {
        return new WalkableList<>(list);
    }

    class WalkableList<T> implements Walkable<T>, AddSupport<T> {
        private final List<T> list;
        int index = -1;

        public WalkableList(List<T> list) {
            this.list = list;
        }


        @Override
        public T next() {
            return list.get(++index);
        }

        @Override
        public boolean hasNext() {
            return index + 1 < list.size();
        }

        @Override
        public void remove() {
            list.remove(index);
            --index;
        }

        @Override
        public boolean checkIndex() {
            return index < list.size();
        }

        @Override
        public void sort(Comparator<? super T> c) {
            this.list.sort(c);
        }

        @Override
        public void walkToEnd() {
            while (hasNext())
                next();
        }

        @Override
        public Walkable<T> newWithoutState() {
            return new WalkableList<>(new ArrayList<>(this.list));
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            forEach(consumer, t -> true);
        }

        @Override
        public void forEach(Consumer<? super T> consumer, Predicate<? super T> until) {

            Walkable<T> walkable = clone();

            while(walkable.hasNext()) {
                T next = walkable.next();
                if(until.test(next))
                    consumer.accept(next);
                else
                    break;
            }
        }

        @Override
        public boolean hasCurrent() {
            return checkIndex();
        }

        @Override
        public T getCurrent() {
            return this.list.get(index);
        }

        @SuppressWarnings("unchecked")
        @Override
        public T[] toArray() {
            T[] array;

            if(hasCurrent())
                array = (T[]) Array.newInstance(getCurrent().getClass(), computeSize());
            else
                array = (T[]) new Object[0];

            WalkableList<T> walkable = this.clone();

            if(walkable.hasCurrent()) {
                int x = -1;
                do {
                    array[++x] = walkable.getCurrent();
                }
                while(walkable.hasNext() && walkable.next() != null);
            }

            return array;
        }

        @Override
        public int size() {
            return this.list.size();
        }

        @Override
        public int computeSize() {
            return size() - index;
        }

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public WalkableList<T> clone() {
            WalkableList<T> walkable = new WalkableList<>(list);
            walkable.index = this.index;
            return walkable;
        }

        @Override
        public void add(T value) {
            this.list.add(value);
        }

    }
}
