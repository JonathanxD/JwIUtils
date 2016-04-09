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
package com.github.jonathanxd.iutils.thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by jonathan on 21/02/16.
 */
public final class ThreadWatcher {

    private final Map<Thread, BiConsumer<Thread, State>> threads = new HashMap<>();

    private final Thread watcher = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            if (Thread.currentThread().isInterrupted())
                break;

            if(threads.isEmpty()) {
                break;
            }

            synchronized (threads) {
                Iterator<Map.Entry<Thread, BiConsumer<Thread, State>>> threadIterator = threads.entrySet().iterator();

                while (threadIterator.hasNext()) {

                    Map.Entry<Thread, BiConsumer<Thread, State>> entry = threadIterator.next();

                    Thread thread = entry.getKey();
                    BiConsumer<Thread, State> run = entry.getValue();

                    if (!thread.isAlive() || thread.isInterrupted()) {
                        threadIterator.remove();
                        run.accept(thread, !thread.isAlive() ? State.NOT_ALIVE : State.INTERRUPT);
                    } else {
                        run.accept(thread, State.RUNNING);
                    }

                }
            }
        }
    });

    public ThreadWatcher() {
        watcher.setName("Thread-Watching #" + this.hashCode());
        watcher.start();
    }

    public void watch(Thread thread, BiConsumer<Thread, State> stateUpdateWatcher) {
        check();
        synchronized (threads) {
            threads.put(thread, stateUpdateWatcher != null ? stateUpdateWatcher : (t, d) -> {
            });
        }

        if (stateUpdateWatcher == null) {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                synchronized (threads) {
                    if (!threads.containsKey(thread)) {
                        break;
                    }
                }
            }
        }
    }

    public void destroy() {
        watcher.interrupt();
    }

    public void check() {
        if (watcher.isInterrupted() || !watcher.isAlive())
            throw new RuntimeException("Cannot watch more threads using this instance!");
    }

    public enum State {
        NOT_ALIVE,
        INTERRUPT,
        RUNNING
    }
}
