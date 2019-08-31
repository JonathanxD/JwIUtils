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
package com.github.jonathanxd.iutils.recursion;

import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ElementUtil {

    /**
     * Creates {@link Element elements} linked to each {@link Iterator#next() next element} of
     * {@code values}.
     *
     * Returned {@code first} is always linked to {@code last} directly or indirectly, also {@code
     * last} will be the same as {@code first} when {@code values} have only one element.
     *
     * @param values Iterable to create {@link Element elements} from, must have at least one
     *               element.
     * @param <E>    Type of element.
     * @return First and last elements created from {@code values}.
     */
    @NotNull
    public static <E> Pair<@Named("first") Element<E>, @Named("first") Element<E>> fromIterable(@NotNull Iterable<? extends E> values) {
        Iterator<? extends E> iterator = values.iterator();
        Element<E> first = new Element<>(iterator.next());
        Element<E> last = first;

        while (iterator.hasNext()) {
            last.next = new Element<>(iterator.next());
            last = last.next;
        }

        return Pairs.of(first, last);
    }

}
