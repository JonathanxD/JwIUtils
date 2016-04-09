/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
