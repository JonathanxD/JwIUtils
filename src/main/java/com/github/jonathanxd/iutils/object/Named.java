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
package com.github.jonathanxd.iutils.object;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by jonathan on 13/08/16.
 */
public class Named<T> {
    private final String name;
    private final T value;

    public Named(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Named(T value) {
        this(null, value);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    /**
     * Find a {@link Named} element in a {@code collection}
     *
     * @param collection Collection
     * @return Named element in list
     */
    public Optional<Named<?>> get(Collection<? extends Named<?>> collection) {
        for (Named<?> named : collection) {
            if (this.equals(named)) {
                return Optional.of(named);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Named<?>) {
            Named named = (Named) obj;

            return this.getValue().equals(named.getValue())
                    && (this.getName() == null || named.getName() != null && this.getName().equals(named.getName()));
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (this.getName() == null) {
            return Objects.hash(this.value);
        }

        return Objects.hash(this.getName(), this.getValue());

    }
}
