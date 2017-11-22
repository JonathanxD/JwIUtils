/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.text;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Text class, represents the text.
 */
public final class Text implements Iterable<TextComponent>, TextComponent {

    private final List<TextComponent> components;

    private Text(List<TextComponent> components) {
        this.components = Collections.unmodifiableList(components);
    }

    public static Text of(List<TextComponent> components) {
        return new Text(components);
    }

    public static Text of(Object... objects) {
        return new Text(Arrays.stream(objects).map(o -> {
            if (o instanceof String
                    || o instanceof Number)
                return StringComponent.of(o.toString());
            if (o instanceof TextComponent)
                return (TextComponent) o;

            throw new IllegalArgumentException("Illegal input component '" + o + "'!");
        }).collect(Collectors.toList()));
    }

    public static CapitalizeComponent capitalize(TextComponent textComponent) {
        return CapitalizeComponent.of(textComponent);
    }

    public static LocalizableComponent localizable(String localization) {
        return LocalizableComponent.of(localization);
    }

    public static LocalizableComponent localizable(String locale, String localization) {
        return LocalizableComponent.of(locale, localization);
    }

    public static VariableComponent variable(String variable) {
        return VariableComponent.of(variable);
    }

    @Override
    public Iterator<TextComponent> iterator() {
        return this.components.iterator();
    }

    @Override
    public void forEach(Consumer<? super TextComponent> action) {
        this.components.forEach(action);
    }

    @Override
    public Spliterator<TextComponent> spliterator() {
        return this.components.spliterator();
    }

}
