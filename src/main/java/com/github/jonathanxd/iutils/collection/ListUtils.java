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

import com.github.jonathanxd.iutils.iterator.BackableIterator;
import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * Created by jonathan on 08/02/16.
 */
public class ListUtils {

    private static final String DELIMITER = ", ";
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";

    public static <E> SafeBackableIterator<E> toSafeBackableIterator(List<E> list) {
        return new ListSafeBackableIterator<>(list);
    }

    public static <E> BackableIterator<E> toBackableIterator(List<E> list) {
        return toSafeBackableIterator(list);
    }

    public static List<Character> from(char[] primitiveArray) {
        List<Character> list = new ArrayList<>();
        for (char primitiveElement : primitiveArray) {
            list.add(primitiveElement);
        }
        return list;
    }

    public static <T> String[] markListPosition(List<T> list, int position, Function<T, String> elementStringFactory) {

        StringJoiner sj = new StringJoiner(DELIMITER, PREFIX, SUFFIX);

        int textPos = PREFIX.length() + SUFFIX.length();
        int x = 0;
        int mark = 0;

        for (T element : list) {

            if (x == position) {
                mark = textPos;
            }

            String text = elementStringFactory.apply(element);

            textPos += DELIMITER.length() + text.length();

            sj.add(text);
            ++x;
        }

        String[] result = new String[2];

        result[0] = sj.toString();
        result[1] = String.format(
                /**
                 %% = %
                 %d = MARK NUMBER
                 s = s
                 Consider mark = 10
                 %10s
                 */
                String.format("%%%ds", mark),
                "^"
        );
        return result;
    }


}
