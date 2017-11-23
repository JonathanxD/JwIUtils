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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Text class, represents the text.
 */
public final class Text implements Iterable<TextComponent>, TextComponent {

    private final List<TextComponent> components;

    private Text(List<TextComponent> components) {
        this.components = Collections.unmodifiableList(components);
    }

    public static Text ofUncompressed(List<TextComponent> components) {
        return new Text(components);
    }

    public static Text ofUncompressed(Object... objects) {
        return new Text(Arrays.stream(objects)
                .map(Text::single)
                .collect(Collectors.toList()));
    }

    public static Text of(List<TextComponent> components) {
        return Text.compress(Text.ofUncompressed(components));
    }

    public static Text of(Object... objects) {
        return Text.compress(Text.ofUncompressed(objects));
    }

    /**
     * Tries to compress {@code text}, for example, a text {@code Text(Text(String("A")),
     * Text(String("B")))}, will be compressed into a single sequence of text: {@code
     * Text(String("A"), String("B"), String("C")}, allowing Garbage collector to collect
     * unnecessary instances, this is automatically applied to inputs comping from {@link
     * #of(Object...)} and {@link #of(List)}, also other components may be compressed, because
     * {@link TextComponent} is (and must be) immutable, instances can be reused instead of using a
     * new one, complex texts are not compressed (example: {@code Text(Variable("A"),
     * Capitalize(Variable("A")))}.
     *
     * @param text Text to compress.
     * @return Compressed text, or {@code text} if cannot be compressed.
     */
    public static Text compress(Text text) {
        return Text.compress(text, new ArrayList<>());
    }

    private static Text compress(Text text, List<TextComponent> compressed) {
        List<TextComponent> toCompress = text.getComponents();

        while (toCompress.size() == 1 && toCompress.get(0) instanceof Text) {
            text = (Text) toCompress.get(0);
            toCompress = text.getComponents();
        }

        compressed = Text.compress(toCompress, compressed);

        if (Objects.equals(toCompress, compressed))
            return text;

        if (compressed.size() == 1 && compressed.get(0) instanceof Text)
            return (Text) compressed.get(0);

        return new Text(compressed);
    }

    private static TextComponent compressComponent(TextComponent toCompress, List<TextComponent> compressedList) {
        for (TextComponent compressedComponent : compressedList) {
            if (compressedComponent.equals(toCompress) && toCompress != compressedComponent) {
                return compressedComponent;
            }
        }

        if (toCompress instanceof Text) {
            return Text.compress((Text) toCompress, compressedList);
        }

        return toCompress;
    }

    private static List<TextComponent> compress(List<TextComponent> toCompressList, List<TextComponent> compressedList) {
        ListIterator<TextComponent> iterator = toCompressList.listIterator();

        while (iterator.hasNext()) {
            TextComponent compress = iterator.next();

            if (compress instanceof Text) {
                Text.compress(((Text) compress).getComponents(), compressedList);
            } else {
                TextComponent add;

                if (compress instanceof CapitalizeComponent) {
                    add = Text.compressComponent(((CapitalizeComponent) compress).getTextComponent(), compressedList).capitalize();
                } else if (compress instanceof DecapitalizeComponent) {
                    add = Text.compressComponent(((DecapitalizeComponent) compress).getTextComponent(), compressedList).decapitalize();
                } else if (compress instanceof ArgsAppliedText) {
                    add = Text.compressComponent(((ArgsAppliedText) compress).getComponent(), compressedList)
                            .apply(((ArgsAppliedText) compress).getArgs());
                } else if (compress instanceof StringComponent && iterator.hasNext()) {
                    StringBuilder sb = new StringBuilder();

                    sb.append(((StringComponent) compress).getText());

                    while (iterator.hasNext()) {
                        TextComponent next = iterator.next();

                        if (next instanceof StringComponent) {
                            sb.append(((StringComponent) next).getText());
                        } else {
                            iterator.previous();
                            break;
                        }
                    }

                    add = Text.compressComponent(Text.single(sb.toString()), compressedList);
                } else {
                    add = Text.compressComponent(compress, compressedList);
                }

                compressedList.add(add);
            }
        }

        return compressedList;
    }

    public static TextComponent single(Object o) {
        if (o instanceof TextComponent)
            return (TextComponent) o;
        if (o instanceof String || o instanceof Number)
            return StringComponent.of(o.toString());

        throw new IllegalArgumentException("Illegal input component '" + o + "'!");
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

    public Stream<TextComponent> stream() {
        return this.components.stream();
    }

    public Stream<TextComponent> parallelStream() {
        return this.components.parallelStream();
    }

    @Override
    public boolean isEmpty() {
        return this.components.isEmpty()
                || this.stream().allMatch(TextComponent::isEmpty);
    }

    public List<TextComponent> getComponents() {
        return this.components;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Text)
            return Objects.equals(((Text) obj).getComponents(), this.getComponents());

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getComponents());
    }

    @Override
    public String toString() {
        return "Text[" + this.getComponents().stream().map(Objects::toString).collect(Collectors.joining(",")) + "]";
    }
}
