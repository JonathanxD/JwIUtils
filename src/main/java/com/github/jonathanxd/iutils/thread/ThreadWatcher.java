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
