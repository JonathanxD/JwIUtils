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
package com.github.jonathanxd.iutils.sync;

import com.github.jonathanxd.iutils.exceptions.ElementLockedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

public class HashSetLock<E> extends HashSet<E> {

    /**
     *
     */
    private static final long serialVersionUID = -8630709456520761213L;
    HashSet<E> queueQueue = new HashSet<>();
    List<Runnable> removal = new ArrayList<>();

    private boolean isLocked = false;

    public void doLock() {
        lock();
    }

    void lock() {
        if (isLocked()) {
            throw new ElementLockedException(this);
        }
        isLocked = true;
    }

    public void doUnlock() {
        unlock();
        transfer();
    }

    void unlock() {
        isLocked = false;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    @Override
    public boolean add(E e) {
        return add(e, true);
    }

    public boolean add(E e, boolean addIfLocked) {
        if (isLocked()) {
            if (addIfLocked) return queueQueue.add(e);
            else return false;
        } else {
            return super.add(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        if (isLocked()) {
            if (queueQueue.contains(o)) {
                return queueQueue.remove(o);
            } else {
                removal.add(() -> super.remove(o));
            }
        }
        return super.remove(o);
    }

    @Override
    public void clear() {
        if (isLocked()) {
            throw new ElementLockedException(this);
        }
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (isLocked()) {
            return queueQueue.addAll(c);
        }
        return super.addAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        return super.iterator();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (isLocked()) {
            throw new ElementLockedException(this);
        }
        return super.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (isLocked()) {
            throw new ElementLockedException(this);
        }
        return super.retainAll(c);
    }

    @Override
    public Spliterator<E> spliterator() {
        if (isLocked()) {
            throw new ElementLockedException(this);
        }
        return super.spliterator();
    }

    /**
     * @return If any element has transfered
     */
    protected boolean transfer() {
        boolean empty = !queueQueue.isEmpty();
        super.addAll(queueQueue);
        queueQueue.clear();
        removal.forEach(Runnable::run);
        return empty;
    }
}
