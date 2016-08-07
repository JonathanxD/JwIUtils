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
package com.github.jonathanxd.iutils.option;

import com.github.jonathanxd.iutils.function.stream.BiStream;
import com.github.jonathanxd.iutils.function.stream.MapStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jonathan on 07/08/16.
 */
public class Options {

    private final Map<Option<?>, Object> value = new HashMap<>();

    public <T> Options set(Option<T> option, T value) {
        this.value.put(option, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Option<T> option) {
        if (!value.containsKey(option))
            return Optional.ofNullable(option.getDefaultValue());

        return Optional.ofNullable((T) value.get(option));
    }

    public <T> T getOrNull(Option<T> option) {
        return this.getOrElse(option, (T) null);
    }

    public <T> T getOrElse(Option<T> option, T value) {
        return this.get(option).orElse(value);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public <T> Optional<T> getOrElseOptional(Option<T> option, Optional<T> value) {
        Optional<T> t = this.get(option);

        if (!t.isPresent())
            return value;

        return t;
    }

    public <T> boolean contains(Option<T> option) {
        return this.value.containsKey(option);
    }

    public boolean containsValue(Object value) {
        return this.value.containsValue(value);
    }

    public BiStream<Option<?>, Object> stream() {
        return MapStream.of(this.value);
    }

    public Map<Option<?>, Object> getValueMap() {
        return this.value;
    }
}
