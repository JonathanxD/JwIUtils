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
package com.github.jonathanxd.iutils.grabber;

import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.exception.CannotCollectElementsException;
import com.github.jonathanxd.iutils.exception.ExcludedElementIndexException;
import com.github.jonathanxd.iutils.function.consumer.ObjIntIntConsumer;
import com.github.jonathanxd.iutils.string.InJoiner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Abstract implementation of {@link Grabber}. This implementation stores blacklisted elements by
 * index in a boolean blacklist array.
 *
 * Implementations must override {@link #get(int)} method that is like a {@link List#get(int)}
 * method. This method gets elements by index.
 *
 * @param <T> Type of elements to store.
 */
public abstract class AbstractGrabber<T> implements Grabber<T> {

    /**
     * Size of the element sequence.
     */
    private final int length;

    /**
     * Blacklisted indexes.
     */
    private final boolean[] blacklistedIndexes;

    /**
     * Whitelist first index.
     */
    private int whitelistIndex = 0;

    protected AbstractGrabber(int length) {
        this.length = length;
        this.blacklistedIndexes = new boolean[length];
        this.whitelistIndex = length > 0 ? 0 : -1;
    }

    @Override
    public T grab() throws NoSuchElementException {

        int whitelistIndex = this.getWhitelistIndex();

        if (whitelistIndex == -1 || this.isBlacklisted(whitelistIndex)) {
            this.calculateWhitelistIndex();
            whitelistIndex = this.getWhitelistIndex();
        }

        if (whitelistIndex != -1) {

            this.getBlacklistedIndexes()[whitelistIndex] = true;

            T value = this.get(whitelistIndex);

            this.setWhitelistIndex(whitelistIndex + 1 < this.size() ? whitelistIndex + 1 : -1);

            return value;
        }

        throw new NoSuchElementException("No more elements!");
    }

    @Override
    public T grab(int index) throws ExcludedElementIndexException {

        if (this.getBlacklistedIndexes()[index])
            throw new ExcludedElementIndexException("Index: " + index);

        this.getBlacklistedIndexes()[index] = true;

        this.setWhitelistIndex(this.calculateNonExcludedIndex());

        return this.get(index);
    }

    @Override
    public T[] grabAll(IntFunction<T[]> arrayFactory, BiPredicate<T, Position> predicate) {
        List<T> grabbing = new ArrayList<>();

        Position position = Position.START;

        for (int x = 0; x < this.size(); ++x) {
            if (!this.getBlacklistedIndexes()[x]) {
                T atX = this.get(x);

                // If predicate accept, start grabbing
                if (predicate.test(atX, position)) {
                    // Mark to find end position.
                    position = Position.END;

                    // Add element
                    grabbing.add(this.grab(x));
                } else {
                    // If predicate reject
                    // If is marked to find a end position
                    if (position == Position.END) {
                        // Add element
                        grabbing.add(this.grab(x));
                        // Break grab.
                        break;
                    }
                }
            }
        }

        return grabbing.stream().toArray(arrayFactory);
    }

    @Override
    public boolean isBlacklisted(int index) {
        return this.getBlacklistedIndexes()[index];
    }

    @Override
    public T[] collectRemainingToArray(IntFunction<T[]> generator) {

        int size = this.calculateWhitelistedElements();

        T[] ts = generator.apply(size);

        this.foreachRemainingIndexed((t, value, count) -> {
            ts[count] = t;
        });

        return ts;
    }

    @Override
    public T[] collectRemainingToArray(int amount, IntFunction<T[]> generator) {
        int size = this.calculateWhitelistedElements();

        T[] ts = generator.apply(size);

        this.foreachRemainingIndexed(amount, (t, value, count) -> {
            ts[count] = t;
        });

        return ts;
    }

    @Override
    public List<T> collectRemainingToList(int amount) {
        List<T> list = new ArrayList<>();

        this.foreachRemaining(amount, list::add);

        return list;
    }

    @Override
    public List<T> collectRemainingToList() {

        List<T> list = new ArrayList<>();

        this.foreachRemaining(list::add);

        return list;
    }

    @Override
    public void foreachRemaining(Consumer<T> consumer) {
        for (int i = 0; i < this.getBlacklistedIndexes().length; i++) {
            if (!this.getBlacklistedIndexes()[i]) {
                this.getBlacklistedIndexes()[i] = true;

                consumer.accept(this.get(i));
            }
        }

        this.setWhitelistIndex(-1);
    }

    @Override
    public void foreachRemainingIndexed(ObjIntIntConsumer<T> consumer) {
        int includedIndex = 0;

        for (int i = 0; i < this.size(); i++) {
            if (!this.getBlacklistedIndexes()[i]) {
                this.getBlacklistedIndexes()[i] = true;

                consumer.accept(this.get(i), i, includedIndex);

                ++includedIndex;
            }
        }

        this.setWhitelistIndex(-1);
    }

    @Override
    public void foreachRemaining(int amount, Consumer<T> consumer) {
        int currentAmount = 0;

        for (int i = 0; i < this.size(); i++) {
            if (!this.getBlacklistedIndexes()[i]) {
                this.getBlacklistedIndexes()[i] = true;

                consumer.accept(this.get(i));
                ++currentAmount;
            }

            if (currentAmount == amount)
                break;
        }

        if (currentAmount != amount)
            throw new CannotCollectElementsException("Cannot collect '" + amount + "' elements. Collected only '" + currentAmount + "'!");

        this.setWhitelistIndex(this.calculateNonExcludedIndex());
    }

    @Override
    public void foreachRemainingIndexed(int amount, ObjIntIntConsumer<T> consumer) {
        int currentAmount = 0;

        for (int i = 0; i < this.size(); i++) {
            if (!this.getBlacklistedIndexes()[i]) {
                this.getBlacklistedIndexes()[i] = true;
                // In this case, the current amount variable can be used instead of 'included Element Index'
                consumer.accept(this.get(i), currentAmount, i);
                ++currentAmount;

            }

            if (currentAmount == amount)
                break;
        }

        if (currentAmount != amount)
            throw new CannotCollectElementsException("Cannot collect '" + amount + "' elements. Collected only '" + currentAmount + "'!");

        this.setWhitelistIndex(this.calculateNonExcludedIndex());
    }

    @Override
    public List<T> allElements() {

        List<T> array = new ArrayList<>();

        for (int x = 0; x < this.size(); ++x) {
            array.add(this.get(x));
        }

        return array;
    }

    @Override
    public List<T> whitelistedElements() {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            if (!this.getBlacklistedIndexes()[i])
                list.add(list.get(i));
        }

        return list;
    }

    @Override
    public List<T> blacklistedElements() {
        List<T> array = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            if (this.getBlacklistedIndexes()[i])
                array.add(array.get(i));
        }

        return array;
    }

    protected final void calculateWhitelistIndex() {
        this.setWhitelistIndex(this.calculateNonExcludedIndex());
    }

    protected int calculateNonExcludedIndex() {
        for (int i = 0; i < this.size(); i++) {
            if (!this.getBlacklistedIndexes()[i])
                return i;
        }

        return -1;
    }

    @Override
    public int calculateBlacklistedElements() {
        int num = 0;

        for (boolean excludedIndex : this.getBlacklistedIndexes()) {
            if (excludedIndex)
                ++num;
        }

        return num;
    }

    @Override
    public int calculateWhitelistedElements() {
        int num = 0;

        for (boolean excludedIndex : this.getBlacklistedIndexes()) {
            if (!excludedIndex)
                ++num;
        }

        return num;
    }

    @Override
    public int allElementsSize() {
        return this.length;
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public boolean hasWhitelistedElements() {

        if (this.getWhitelistIndex() == -1)
            this.calculateWhitelistIndex();

        return this.getWhitelistIndex() != -1;
    }

    /**
     * Gets the element at index {@code index}.
     *
     * @param index Index of element.
     * @return Element at index {@code index}.
     */
    protected abstract T get(int index);

    /**
     * Gets all blacklisted indexes.
     *
     * Implementation that override this method must take care about {@link #getWhitelistIndex()}.
     * The index can be easily recalculated calling {@link #calculateWhitelistIndex()}.
     *
     * This array must be the original blacklisted index array, not a copy.
     *
     * @return Blacklisted indexes.
     */
    protected boolean[] getBlacklistedIndexes() {
        return this.blacklistedIndexes;
    }

    /**
     * Gets the whitelist index.
     *
     * Whitelist index is the point to first available whitelisted element (to avoid calculation).
     *
     * @return Whitelist index
     */
    protected int getWhitelistIndex() {
        return this.whitelistIndex;
    }

    /**
     * Sets the whitelist index.
     *
     * Whitelist index is the point to first available whitelisted element (to avoid calculation).
     *
     * @param currentWhitelistIndex Whitelist index.
     */
    protected void setWhitelistIndex(int currentWhitelistIndex) {
        this.whitelistIndex = currentWhitelistIndex;
    }

    @Override
    public Grabber<T> createClone(boolean preserveState) {
        AbstractGrabber<T> newGrabber = makeNew();

        if (preserveState) {
            System.arraycopy(this.getBlacklistedIndexes(), 0, newGrabber.getBlacklistedIndexes(), 0, this.getBlacklistedIndexes().length);
        }

        if (preserveState) {
            // Preserve the state
            newGrabber.setWhitelistIndex(this.getWhitelistIndex());
        } else {
            newGrabber.setWhitelistIndex(newGrabber.calculateNonExcludedIndex());
        }

        return newGrabber;
    }

    /**
     * Return Excluded elements between parentheses and included elements out-of parentheses.
     *
     * @return Excluded elements between parentheses and included elements out-of parentheses.
     */
    @Override
    public String toString() {
        InJoiner inJoiner = new InJoiner(new StringJoiner(", ", "[", "]"),
                () -> new StringJoiner(", ", "-(", ")"));

        for (int x = 0; x < this.allElementsSize(); ++x) {
            if (this.getBlacklistedIndexes()[x])
                inJoiner.joinSecond(this.get(x).toString());
            else
                inJoiner.joinFirst(this.get(x).toString());
        }

        return inJoiner.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> AbstractGrabber<U> map(Function<T, U> function) {
        List<U> list = new ArrayList<>();

        while (this.hasWhitelistedElements()) {
            T grab = this.grab();

            list.add(function.apply(grab));
        }

        return this.makeNew(list, list.size());
    }

    @Override
    public <U> AbstractGrabber<U> mapAll(Function<T, U> function) {

        boolean[] clone = this.getBlacklistedIndexes().clone();

        AbstractGrabber<U> uGrabber = this.mapAllToIncluded(function);

        for (int i = 0; i < clone.length; i++) {
            uGrabber.getBlacklistedIndexes()[i] = clone[i];
        }

        return uGrabber;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> AbstractGrabber<U> mapAllToIncluded(Function<T, U> function) {

        int size = this.size();

        List<U> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(function.apply(this.get(i)));
        }

        while (this.hasWhitelistedElements())
            this.grab();


        return this.makeNew(list, list.size());
    }

    abstract AbstractGrabber<T> makeNew();

    /**
     * Creates a new grabber instance from supplied elements.
     *
     * @param elements Elements to use to create {@link AbstractGrabber}.
     * @param size     Amount of elements supplied by {@code elements iterable}.
     * @param <U>      Type of elements.
     * @return New instance of current grabber created from supplied elements.
     */
    abstract <U> AbstractGrabber<U> makeNew(Iterable<U> elements, int size);
}
