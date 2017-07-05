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
package com.github.jonathanxd.iutils.grabber;

import com.github.jonathanxd.iutils.exception.CannotCollectElementsException;
import com.github.jonathanxd.iutils.exception.ExcludedElementIndexException;
import com.github.jonathanxd.iutils.function.consumer.ObjIntIntConsumer;

import java.lang.reflect.Array;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;


/**
 * Element grab utility.
 *
 * This class grabs elements and put them into a {@code grab blacklist}.
 *
 * Elements in the blacklist cannot be {@code grabbed} again, some methods will not returns elements
 * in the blacklist.
 *
 * Grab methods can only grab {@code whitelisted elements} ({@code Whitelisted elements} are all
 * elements that are not in the blacklist).
 *
 * Methods marked as {@code element consumer} are methods that adds consumed elements to the
 * blacklist.
 *
 * @param <T> Elements type
 */
public interface Grabber<T> {

    /**
     * Grab first available whitelisted element and put the element in the blacklist.
     *
     * <b>Element consumer.</b>
     *
     * @return First available whitelisted element.
     * @throws NoSuchElementException If there is no element to grab.
     */
    T grab() throws NoSuchElementException;

    /**
     * Grab element at a {@code index} and put it in blacklist.
     *
     * <b>Element consumer.</b>
     *
     * @param index Element index.
     * @return Element at a {@code index}.
     * @throws ExcludedElementIndexException if the element is in the blacklist.
     */
    T grab(int index) throws ExcludedElementIndexException;

    /**
     * Grabs all elements that matches predicate.
     *
     * @param predicate Predicate to test elements.
     * @return Grabbed elements.
     */
    List<T> grab(Predicate<T> predicate);

    /**
     * Grab multiple elements.
     *
     * <b>Element consumer.</b>
     *
     * @param predicate Predicate to test start position and end position.
     * @return Array with all elements grabbed.
     */
    T[] grabAll(IntFunction<T[]> arrayFactory, BiPredicate<T, Position> predicate);

    /**
     * Return true if specified {@code index} is in the blacklist.
     *
     * @param index Index
     * @return True if specified {@code index} is in the blacklist.
     */
    boolean isBlacklisted(int index);

    /**
     * Map remaining to a new Grabber (blacklisted elements are not included).
     *
     * <b>Element consumer.</b>
     *
     * @param <U> New Grabber Type.
     * @return Mapped grabber.
     */
    <U> Grabber<U> map(Function<T, U> function);

    /**
     * Map all elements to new Grabber (blacklisted elements will be included, but remains excluded
     * in the new grabber).
     *
     * <b>Element consumer.</b>
     *
     * @param <U> New Grabber Type.
     * @return Mapped grabber.
     */
    <U> Grabber<U> mapAll(Function<T, U> function);

    /**
     * Map all elements to new Grabber (blacklisted elements will be included and will not be added
     * to blacklist of the resulting {@link Grabber}).
     *
     * <b>Element consumer.</b>
     *
     * @param <U> New Type.
     * @return Mapped grabber.
     */
    <U> Grabber<U> mapAllToIncluded(Function<T, U> function);

    /**
     * Collects all remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param generator Array generator.
     * @return All remaining whitelisted elements.
     */
    T[] collectRemainingToArray(IntFunction<T[]> generator);

    /**
     * Collects all remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param arrayClass Array class type.
     * @return All remaining whitelisted elements.
     */
    @SuppressWarnings("unchecked")
    default T[] collectRemainingToArray(Class<T[]> arrayClass) {
        return collectRemainingToArray((i) -> (T[]) Array.newInstance(arrayClass.getComponentType(), i));
    }

    /**
     * Collects all remaining whitelisted elements and add to a {@link List}.
     *
     * <b>Element consumer.</b>
     *
     * @return List with all remaining whitelisted elements.
     */
    List<T> collectRemainingToList();

    /**
     * Collects {@code amount} of remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param amount    Amount of elements to collect.
     * @param generator Array generator.
     * @return Array of collected elements.
     */
    T[] collectRemainingToArray(int amount, IntFunction<T[]> generator);

    /**
     * Collects {@code amount} of remaining whitelisted elements.
     *
     * @param amount     Amount of elements to collect.
     * @param arrayClass Type of the array.
     * @return Array of collected elements.
     */
    @SuppressWarnings("unchecked")
    default T[] collectRemainingToArray(int amount, Class<T[]> arrayClass) {
        return collectRemainingToArray(amount, (i) -> (T[]) Array.newInstance(arrayClass.getComponentType(), i));
    }

    /**
     * Collects {@code amount} of remaining whitelisted elements and add to a {@link List}.
     *
     * @param amount Amount of elements to collect.
     * @return List with collected elements.
     */
    List<T> collectRemainingToList(int amount);

    /**
     * Foreach all remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param consumer Consumer.
     */
    void foreachRemaining(Consumer<T> consumer);

    /**
     * Foreach all remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param consumer Consumer of elements, first argument is the element, the second element is
     *                 the index of element and third argument is the number of consumed elements.
     */
    void foreachRemainingIndexed(ObjIntIntConsumer<T> consumer);

    /**
     * Foreach a {@code amount} of remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param amount Amount of elements to consume.
     * @throws CannotCollectElementsException if cannot collect the specified amount of elements.
     */
    void foreachRemaining(int amount, Consumer<T> consumer) throws CannotCollectElementsException;

    /**
     * Foreach a {@code amount} of remaining whitelisted elements.
     *
     * <b>Element consumer.</b>
     *
     * @param amount   Amount of elements to consume.
     * @param consumer Consumer of elements, first argument is the element, the second element is
     *                 the index of element and third argument is the number of consumed elements.
     * @throws CannotCollectElementsException if cannot collect the specified amount of elements.
     */
    void foreachRemainingIndexed(int amount, ObjIntIntConsumer<T> consumer) throws CannotCollectElementsException;

    /**
     * Calculate number of {@code whitelisted elements}
     *
     * @return Number of {@code whitelisted elements}
     */
    int calculateWhitelistedElements();

    /**
     * Calculate number of {@code blacklisted elements}
     *
     * @return Number of {@code blacklisted elements}.
     */
    int calculateBlacklistedElements();

    /**
     * Gets the amount of elements (whitelisted and blacklisted).
     *
     * @return Amount of elements (whitelisted and blacklisted).
     */
    int allElementsSize();

    /**
     * Same as {@link #allElementsSize()}.
     *
     * @return Same as {@link #allElementsSize()}.
     * @see #allElementsSize()
     */
    int size();

    /**
     * Returns true if has whitelisted elements.
     *
     * @return True if has whitelisted elements.
     */
    boolean hasWhitelistedElements();

    /**
     * All elements (whitelisted and blacklisted).
     *
     * This list is immutable and does not reflect in original element list.
     *
     * @return All Elements (whitelisted and blacklisted).
     */
    List<T> allElements();

    /**
     * Whitelisted elements.
     *
     * This list is immutable and does not reflect in original element list.
     *
     * @return Whitelisted elements
     */
    List<T> whitelistedElements();

    /**
     * Blacklisted elements.
     *
     * This list is immutable and does not reflect in original element list.
     *
     * @return Blacklisted elements.
     */
    List<T> blacklistedElements();

    /**
     * Creates a clone of this grabber (preserve state, blacklisted elements remains blacklisted).
     *
     * <b>This operation does not consume elements</b>
     *
     * @return A clone of this grabber (preserve state, blacklisted elements remains blacklisted).
     */
    default Grabber<T> createClone() {
        return this.createClone(true);
    }

    /**
     * Creates a clone of this grabber.
     *
     * <b>This operation does not consume elements</b>
     *
     * @param preserveState If true, blacklisted elements remains blacklisted, if false, blacklisted
     *                      elements will be not added to blacklist of the cloned {@link Grabber}
     *                      instance.
     * @return A clone of this grabber.
     */
    Grabber<T> createClone(boolean preserveState);

    /**
     * Returns a string representation of this grabber.
     *
     * Recommended representation: Included elements between {@code [} and {@code ]} and excluded
     * elements between {@code -(} and {@code )}.
     *
     * Obs: String representation may or may not follow recommended format.
     *
     * @return String representation of this grabber.
     */
    @Override
    String toString();
}