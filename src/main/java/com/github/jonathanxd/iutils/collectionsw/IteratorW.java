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

import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Iterator, an iterator is an object which allows traversal of elements (potentially infinite).
 *
 * @param <E> Type of elements.
 */
public interface IteratorW<E> {

    /**
     * Java iterator version of this iterator.
     *
     * Modifications are not allowed on immutable iterators.
     *
     * @return Java iterator version of this iterator.
     */
    Iterator<E> asJavaIterator();

    /**
     * Returns true if this iterator has more elements to traverse.
     *
     * @return True if this iterator has more elements to traverse.
     */
    boolean hasNext();

    /**
     * Returns next element available in the iterator.
     *
     * @return Next element available in the iterator.
     */
    E next();

    /**
     * Returns a copy of this iterator.
     *
     * @return Copy of this iterator.
     */
    IteratorW<E> copy();

    /**
     * Try to get next available element.
     *
     * @return {@link com.github.jonathanxd.iutils.opt.Some} next element if iterator has available
     * element, or {@link com.github.jonathanxd.iutils.opt.None}.
     */
    default OptObject<E> tryNext() {
        return !this.hasNext() ? OptObject.none() : OptObject.optObjectNullable(this.next());
    }

    /**
     * Call {@link Consumer#accept} on remaining elements.
     *
     * @param consumer Consumer to consume remaining elements
     */
    default void forEachRemaining(Consumer<? super E> consumer) {
        OptObject<E> opt;

        while ((opt = this.tryNext()).isPresent()) {
            consumer.accept(opt.getValue());
        }
    }

    /**
     * Returns first element (of remaining elements) that matches {@code predicate}.
     *
     * This operation stops at the first result, this means that iterator may have more elements or
     * not.
     *
     * @param predicate Predicate to test element.
     * @return First element (of remaining elements) that matches {@code predicate}.
     */
    default OptObject<E> firstOfRemaining(Predicate<? super E> predicate) {
        OptObject<E> opt;

        while ((opt = this.tryNext()).isPresent()) {
            E value = opt.getValue();

            if (predicate.test(value))
                return opt;
        }

        return OptObject.none();
    }

    /**
     * Returns true if none of remaining elements matches {@code predicate}.
     *
     * This operation may or may not stop consume all remaining elements.
     *
     * @param predicate Predicate to test element.
     * @return True if none of remaining elements matches {@code predicate}.
     */
    default boolean noneOfRemaining(Predicate<? super E> predicate) {
        OptObject<E> opt;

        while ((opt = this.tryNext()).isPresent()) {
            E value = opt.getValue();

            if (predicate.test(value))
                return false;
        }

        return true;
    }

    /**
     * Returns true if all of remaining elements matches {@code predicate}.
     *
     * This operation may or may not stop consume all remaining elements.
     *
     * @param predicate Predicate to test element.
     * @return True if all of remaining elements matches {@code predicate}.
     */
    default boolean allOfRemaining(Predicate<? super E> predicate) {
        OptObject<E> opt;

        while ((opt = this.tryNext()).isPresent()) {
            E value = opt.getValue();

            if (!predicate.test(value))
                return false;
        }

        return true;
    }

    /**
     * Returns true if all of remaining elements matches {@code predicate}.
     *
     * This operation may or may not stop consume all remaining elements.
     *
     * @param predicate Predicate to test element.
     * @return True if all of remaining elements matches {@code predicate}.
     */
    default boolean anyOfRemaining(Predicate<? super E> predicate) {
        OptObject<E> opt;

        while ((opt = this.tryNext()).isPresent()) {
            E value = opt.getValue();

            if (predicate.test(value))
                return true;
        }

        return false;
    }
}
