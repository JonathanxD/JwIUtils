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
package com.github.jonathanxd.iutils.construct;

import com.github.jonathanxd.iutils.construct.annotation.PropertyContainer;
import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.stream.BiJavaStream;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.optional.Require;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jonathan on 02/05/16.
 */
public final class Properties extends HashSet<Prop> {

    public Properties(int initialCapacity) {
        super(initialCapacity);
    }

    public Properties() {
        super();
    }

    public Properties(Collection<? extends Prop> c) {
        super(c);
    }

    public Properties(Prop... properties) {
        super();
        for (Prop prop : properties) {
            this.add(prop);
        }
    }

    public Object get(PropertyContainer container) {
        if (container.isRequired()) {
            return getRequired(container.getPropertyId(), container.getType());
        } else {
            return get(container.getPropertyId(), container.getType());
        }
    }

    public Object get(String id, Class<?> type) {
        Optional<Prop> any = stream().filter(prop -> prop.getId().equals(id)).findAny();

        if (!any.isPresent()) {
            return null;
        }

        Optional<Object> value = any.get().getValue();

        if (value.isPresent()) {
            Object o = value.get();

            if (!type.isAssignableFrom(o.getClass())) {
                throw new CannotFindPropertyException(id, type);
            }
        }

        return null;
    }

    public Object getRequired(String id, Class<?> type) throws CannotFindPropertyException {
        Optional<Prop> any = stream().filter(prop -> prop.getId().equals(id)).findAny();

        if (!any.isPresent()) {
            throw new CannotFindPropertyException(id, type);
        }

        Optional<Object> value = any.get().getValue();

        if (!value.isPresent()) {
            throw new CannotFindPropertyException(id, type);
        }

        return value.get();
    }

    public Map<String, Object> toMap() {
        return BiJavaStream
                .fromJavaStream(this.stream().filter(prop -> prop.getValue().isPresent()), prop -> Pair.of(prop.getId(), Require.require(prop.getValue())))
                .collect(BiCollectors.toHashMap());
    }
}
