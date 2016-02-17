/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by jonathan on 16/02/16.
 */
public class ComparatorMonitor<T> implements Comparator<T> {

    private final Comparator<T> comparator;
    private final List<Function<Integer, Integer>> functionList = new ArrayList<>();

    public ComparatorMonitor(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void add(Function<Integer, Integer> func) {
        functionList.add(func);
    }

    @Override
    public int compare(T o1, T o2) {
        int result = comparator.compare(o1, o2);
        for(Function<Integer, Integer> function : functionList) {
            result = function.apply(result);
        }
        return result;
    }

    @Override
    public Comparator<T> reversed() {
        return comparator.reversed();
    }

    @Override
    public Comparator<T> thenComparing(Comparator<? super T> other) {
        return comparator.thenComparing(other);
    }

    @Override
    public <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return comparator.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor) {
        return comparator.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<T> thenComparingInt(ToIntFunction<? super T> keyExtractor) {
        return comparator.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<T> thenComparingLong(ToLongFunction<? super T> keyExtractor) {
        return comparator.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> keyExtractor) {
        return comparator.thenComparingDouble(keyExtractor);
    }
}
