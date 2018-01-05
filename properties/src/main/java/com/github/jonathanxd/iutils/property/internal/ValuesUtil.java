/*
 *      JwIUtils-Properties - Properties module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.property.internal;

import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.object.Node;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.property.value.Value;
import com.github.jonathanxd.iutils.property.value.Values;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

public final class ValuesUtil {
    private ValuesUtil() {
    }

    public static Map<String, Object> getArgs(Values values) {
        return BiStreams.mapJavaToBiStream(values.getValueList().stream(), f -> Pair.of(f.getProperty().getName(), f.getValue()))
                .collect(BiCollectors.toMap());
    }

    public static Map<String, TextComponent> getTextArgs(Values values) {
        return BiStreams.mapJavaToBiStream(values.getValueList().stream(), f -> Pair.of(f.getProperty().getName(), f.getValue()))
                .map((k, v) -> new Node<>(k, Text.of(v.toString())))
                .collect(BiCollectors.toMap());
    }

    @SuppressWarnings("unchecked")
    public static boolean reached(Value<?> currentValue, Value<?> valueToReach) {
        return ValuesUtil.reached(currentValue, valueToReach.getValue());
    }

    @SuppressWarnings("unchecked")
    public static boolean reached(Value<?> currentValue, Object valueToReach) {
        Comparator<Object> comparator = (Comparator<Object>) currentValue.getComparator();
        Object currentValueObj = currentValue.getValue();

        // currentValueObj >= valueToReachObj
        return comparator.compare(currentValueObj, valueToReach) >= 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> getValueComparator() {
        return (a, b) -> a instanceof Comparable<?> ? ((Comparable) a).compareTo(b)
                : (Objects.equals(a, b) ? 0 : -1); // If both equals, 0, otherwise -1 (a less than b)
    }

    public static Values createCurrentValues(Values values) {
        return Values.createCurrentValues(values);
    }


}
