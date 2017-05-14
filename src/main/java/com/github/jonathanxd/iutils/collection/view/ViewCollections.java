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
package com.github.jonathanxd.iutils.collection.view;

import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class ViewCollections {

    public static <E> ViewCollection<E, E> collection(Collection<E> collection) {
        return new ViewCollection<>(collection,
                e -> IteratorUtil.wrapIterator(IteratorUtil.single(e)),
                (e, eBackingIterator) -> collection.add(e),
                (o, eBackingIterator) -> collection.remove(o));
    }

    public static <E, Y> ViewCollection<E, Y> collectionMapped(Collection<E> collection,
                                                   Function<E, Iterable<Y>> mapper,
                                                   BiPredicate<Y, ViewUtils.BackingIterator<E, Y>> add,
                                                   BiPredicate<Object, ViewUtils.BackingIterator<E, Y>> remove) {
        return new ViewCollection<>(collection,
                mapper,
                add,
                remove);
    }


}
