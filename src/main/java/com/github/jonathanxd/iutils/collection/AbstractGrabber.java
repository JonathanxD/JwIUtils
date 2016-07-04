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

import com.github.jonathanxd.iutils.annotations.Named;
import com.github.jonathanxd.iutils.arrays.JwArray;
import com.github.jonathanxd.iutils.exceptions.CannotCollectElementsException;
import com.github.jonathanxd.iutils.exceptions.ExcludedElementIndexException;
import com.github.jonathanxd.iutils.function.consumer.ObjIntIntConsumer;
import com.github.jonathanxd.iutils.string.InJoiner;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ObjIntConsumer;

/**
 * Created by jonathan on 20/06/16.
 */
public abstract class AbstractGrabber<T> implements Grabber<T> {

    private final int length;
    private final boolean[] excludedIndexes;
    private int currentNonExcludedIndex = 0;

    protected AbstractGrabber(int length) {
        this.length = length;
        excludedIndexes = new boolean[length];
        currentNonExcludedIndex = length > 0 ? 0 : -1;
    }

    @Override
    public T grab() throws IndexOutOfBoundsException {
        if (currentNonExcludedIndex != -1) {
            excludedIndexes[currentNonExcludedIndex] = true;

            T value = this.get(currentNonExcludedIndex);

            currentNonExcludedIndex = currentNonExcludedIndex + 1 < length ? currentNonExcludedIndex + 1 : -1;

            return value;
        }

        throw new IndexOutOfBoundsException("No more elements!");
    }

    @Override
    public T grab(int index) throws ExcludedElementIndexException {

        if (excludedIndexes[index])
            throw new ExcludedElementIndexException("Index: " + index);

        excludedIndexes[index] = true;

        currentNonExcludedIndex = calculateNonExcludedIndex();

        return this.get(index);
    }

    @Override
    public boolean isExcluded(int index) {
        return excludedIndexes[index];
    }

    @Override
    public T[] collectRemainingToArray(IntFunction<T[]> generator) {

        int size = calculateIncludedElements();

        T[] ts = generator.apply(size);

        foreachRemaining((t, value, indexInArray) -> {
            ts[value] = t;
        });

        return ts;
    }

    @Override
    public T[] collectRemainingToArray(int amount, IntFunction<T[]> generator) {
        int size = calculateIncludedElements();

        T[] ts = generator.apply(size);

        foreachRemaining(amount, (t, value, indexInArray) -> {
            ts[value] = t;
        });

        return ts;
    }

    @Override
    public List<T> collectRemainingToList(int amount) {
        List<T> list = new ArrayList<>();

        foreachRemaining(amount, (Consumer<T>) list::add);

        return list;
    }

    @Override
    public List<T> collectRemainingToList() {

        List<T> list = new ArrayList<>();

        foreachRemaining((Consumer<T>) list::add);

        return list;
    }

    @Override
    public void foreachRemaining(Consumer<T> consumer) {
        for (int i = 0; i < excludedIndexes.length; i++) {
            if (!excludedIndexes[i]) {
                excludedIndexes[i] = true;

                consumer.accept(this.get(i));
            }
        }

        currentNonExcludedIndex = -1;
    }

    @Override
    public void foreachRemaining(ObjIntIntConsumer<T> consumer) {
        int includedIndex = 0;

        for (int i = 0; i < excludedIndexes.length; i++) {
            if (!excludedIndexes[i]) {
                excludedIndexes[i] = true;

                consumer.accept(this.get(i), includedIndex, i);

                ++includedIndex;
            }
        }

        currentNonExcludedIndex = -1;
    }

    @Override
    public void foreachRemaining(int amount, Consumer<T> consumer) {
        int currentAmount = 0;

        for (int i = 0; i < excludedIndexes.length; i++) {
            if (!excludedIndexes[i]) {
                excludedIndexes[i] = true;

                consumer.accept(this.get(i));
                ++currentAmount;
            }

            if (currentAmount == amount)
                break;
        }

        if (currentAmount != amount)
            throw new CannotCollectElementsException("Cannot collect '" + amount + "' elements. Collected only '" + currentAmount + "'!");

        currentNonExcludedIndex = calculateNonExcludedIndex();
    }

    @Override
    public void foreachRemaining(int amount, ObjIntIntConsumer<T> consumer) {
        int currentAmount = 0;

        for (int i = 0; i < excludedIndexes.length; i++) {
            if (!excludedIndexes[i]) {
                excludedIndexes[i] = true;
                 // In this case, the current amount variable can be used instead of 'included Element Index'
                consumer.accept(this.get(i), currentAmount, i);
                ++currentAmount;

            }

            if (currentAmount == amount)
                break;
        }

        if (currentAmount != amount)
            throw new CannotCollectElementsException("Cannot collect '" + amount + "' elements. Collected only '" + currentAmount + "'!");

        currentNonExcludedIndex = calculateNonExcludedIndex();
    }

    @Override
    public JwArray<T> allElements() {

        JwArray<T> array = new JwArray<>();

        for (int x = 0; x < length; ++x) {
            array.add(this.get(x));
        }

        return array;
    }

    @Override
    public JwArray<T> includedElements() {
        JwArray<T> array = new JwArray<>();

        for (int i = 0; i < excludedIndexes.length; i++) {
            if (!excludedIndexes[i])
                array.add(array.get(i));
        }

        return array;
    }

    @Override
    public JwArray<T> excludedElements() {
        JwArray<T> array = new JwArray<>();

        for (int i = 0; i < excludedIndexes.length; i++) {
            if (excludedIndexes[i])
                array.add(array.get(i));
        }

        return array;
    }

    protected int calculateNonExcludedIndex() {
        for (int i = 0; i < excludedIndexes.length; i++) {
            if (!excludedIndexes[i])
                return i;
        }

        return -1;
    }

    @Override
    public int calculateExcludedElements() {
        int num = 0;

        for (boolean excludedIndex : excludedIndexes) {
            if (excludedIndex)
                ++num;
        }

        return num;
    }

    @Override
    public int calculateIncludedElements() {
        int num = 0;

        for (boolean excludedIndex : excludedIndexes) {
            if (!excludedIndex)
                ++num;
        }

        return num;
    }

    @Override
    public int allElementsSize() {
        return length;
    }

    @Override
    public boolean hasIncludedElements() {
        return currentNonExcludedIndex != -1;
    }

    protected abstract T get(int index);

    protected boolean[] getExcludedIndexes() {
        return excludedIndexes.clone();
    }

    @Override
    public Grabber<T> makeClone() {
        AbstractGrabber<T> newGrabber = makeNew();

        for (int i = 0; i < excludedIndexes.length; i++) {
            newGrabber.excludedIndexes[i] = excludedIndexes[i];
        }

        // More Secure. Can be replaced with: newGrabber.currentNonExcludedIndex = this.currentNonExcludedIndex
        newGrabber.currentNonExcludedIndex = newGrabber.calculateNonExcludedIndex();

        return newGrabber;
    }

    abstract AbstractGrabber<T> makeNew();

    /**
     * Return Excluded elements between parentheses and included elements out-of parentheses.
     *
     * @return Excluded elements between parentheses and included elements out-of parentheses.
     */
    @Override
    public String toString() {
        //[(0, 1, 2, 3) 4, 5, 6, 7]

        InJoiner inJoiner = new InJoiner(new @Named("Included") StringJoiner(", ", "[", "]"),
                () -> new @Named("Excluded") StringJoiner(", ", "(", ")"));

        for (int x = 0; x < allElementsSize(); ++x) {
            if (excludedIndexes[x])
                inJoiner.joinSecond(get(x).toString());
            else
                inJoiner.joinFirst(get(x).toString());
        }

        return inJoiner.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> AbstractGrabber<U> map(Function<T, U> function) {
        U[] genericArray = (U[]) new Object[calculateIncludedElements()];

        for(int x = 0; hasIncludedElements(); ++x) {
            T grab = grab(); // IMPORTANT: Consume

            genericArray[x] = function.apply(grab);
        }

        return makeNewFromArray(genericArray);
    }

    @Override
    public <U> AbstractGrabber<U> mapAll(Function<T, U> function) {

        boolean[] clone = excludedIndexes.clone();

        AbstractGrabber<U> uGrabber = mapAllToIncluded(function);

        for (int i = 0; i < clone.length; i++) {
            uGrabber.excludedIndexes[i] = clone[i];
        }

        return uGrabber;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> AbstractGrabber<U> mapAllToIncluded(Function<T, U> function) {

        U[] genericArray = (U[]) new Object[length];

        for (int i = 0; i < length; i++) {
            genericArray[i] = function.apply(get(i));
        }

        while(hasIncludedElements())
            grab();

        return makeNewFromArray(genericArray);
    }

    abstract <U> AbstractGrabber<U> makeNewFromArray(U[] array);
}
