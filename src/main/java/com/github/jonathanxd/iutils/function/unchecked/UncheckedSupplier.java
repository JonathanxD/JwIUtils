package com.github.jonathanxd.iutils.function.unchecked;

import com.github.jonathanxd.iutils.exception.RethrowException;

import java.util.function.Supplier;

public interface UncheckedSupplier<T> extends Supplier<T> {

    @Override
    default T get() {
        try {
            return this.uncheckedGet();
        } catch (Throwable t) {
            throw new RethrowException(t);
        }
    }

    T uncheckedGet() throws Throwable;
}
