/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.text;

import com.github.jonathanxd.iutils.function.UnaryOperators;
import com.github.jonathanxd.iutils.recursion.Element;
import com.github.jonathanxd.iutils.recursion.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Text class, represents the text.
 */
public final class Text implements Iterable<TextComponent>, TextComponent {

    private static final TextComponent EMPTY = Text.single("");

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

    public static Text of(Object first) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(Text.single(first));
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(Object first, Object second) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(Text.single(first));
        componentList.add(Text.single(second));
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(Object first, Object second, Object third) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(Text.single(first));
        componentList.add(Text.single(second));
        componentList.add(Text.single(third));
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(Object first, Object second, Object third, Object fourth) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(Text.single(first));
        componentList.add(Text.single(second));
        componentList.add(Text.single(third));
        componentList.add(Text.single(fourth));
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(TextComponent first) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(first);
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(TextComponent first, TextComponent second) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(first);
        componentList.add(second);
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(TextComponent first, TextComponent second, TextComponent third) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(first);
        componentList.add(second);
        componentList.add(third);
        return Text.compress(Text.ofUncompressed(componentList));
    }

    public static Text of(TextComponent first, TextComponent second, TextComponent third, TextComponent fourth) {
        List<TextComponent> componentList = new ArrayList<>();
        componentList.add(first);
        componentList.add(second);
        componentList.add(third);
        componentList.add(fourth);
        return Text.compress(Text.ofUncompressed(componentList));
    }

    /**
     * Tries to compress {@code text}, for example, a text {@code Text(Text(String("A")),
     * Text(String("B")))}, will be compressed into a single sequence of text: {@code
     * Text(String("A"), String("B"))}, allowing Garbage collector to collect
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
        return Text.compress0(text);
    }

    private static Text compress0(Text text) {
        List<TextComponent> compressed = new ArrayList<>();

        while (text.getComponents().size() == 1 && text.getComponents().get(0) instanceof Text) {
            text = (Text) text.getComponents().get(0);
        }

        Elements<TextComponent> components = new Elements<>();
        components.first = new Element<>(text);
        Map<TextComponent, UnaryOperator<TextComponent>> funcs = new HashMap<>();

        Element<TextComponent> elem;
        StringBuilder buffer = new StringBuilder();

        while ((elem = components.nextElement()) != null) {
            TextComponent component = elem.value;

            boolean cnt_ = false;
            for (TextComponent textComponent : compressed) {
                if (textComponent.equals(component) && textComponent != component) {
                    compressed.add(apply(funcs, component, textComponent));
                    cnt_ = true;
                    break;
                }
            }

            if (cnt_)
                continue;

            if (component instanceof Text) {
                List<TextComponent> componentList = ((Text) component).getComponents();
                Element<TextComponent> celem = null;
                Element<TextComponent> clast = null;
                UnaryOperator<TextComponent> func = funcs.get(component);

                for (TextComponent textComponent : componentList) {
                    if (func != null)
                        funcs.put(textComponent, func);

                    Element<TextComponent> toSet = new Element<>(textComponent);
                    if (celem == null) {
                        celem = toSet;
                    } else {
                        clast.next = toSet;
                    }

                    clast = toSet;
                }

                if (celem != null)
                    components.insert(celem, clast);
            } else if (component instanceof StringComponent) {
                buffer.append(((StringComponent) component).getText());

                while (components.first != null && components.first.value instanceof StringComponent) {
                    TextComponent cp = components.nextElement().value;

                    buffer.append(((StringComponent) cp).getText());
                }

                compressed.add(apply(funcs, component, Text.single(buffer.toString())));

                buffer.setLength(0);
            } else if (component instanceof CapitalizeComponent) {
                CapitalizeComponent capitalizeComponent = (CapitalizeComponent) component;
                TextComponent cp = capitalizeComponent.getTextComponent();
                components.insert(new Element<>(cp));
                UnaryOperator<TextComponent> func = TextComponent::capitalize;
                if (funcs.containsKey(component)) {
                    func = UnaryOperators.andThen(funcs.get(component), func);
                }
                funcs.put(cp, func);
            } else if (component instanceof DecapitalizeComponent) {
                DecapitalizeComponent decapitalizeComponent = (DecapitalizeComponent) component;
                TextComponent cp = decapitalizeComponent.getTextComponent();
                components.insert(new Element<>(cp));
                UnaryOperator<TextComponent> func = TextComponent::decapitalize;
                if (funcs.containsKey(component)) {
                    func = UnaryOperators.andThen(funcs.get(component), func);
                }
                funcs.put(cp, func);
            } else if (component instanceof ArgsAppliedText) {
                ArgsAppliedText argsAppliedText = (ArgsAppliedText) component;
                TextComponent cp = argsAppliedText.getComponent();
                components.insert(new Element<>(cp));
                UnaryOperator<TextComponent> func = f -> f.apply(argsAppliedText.getArgs());
                if (funcs.containsKey(component)) {
                    func = UnaryOperators.andThen(funcs.get(component), func);
                }
                funcs.put(cp, func);
            } else {
                compressed.add(apply(funcs, component, component));
            }

            funcs.remove(component);
        }

        if (buffer.length() > 0) {
            compressed.add(Text.single(buffer.toString()));
            buffer.setLength(0);
        }

        if (compressed.size() == 1 && compressed.get(0) instanceof Text)
            return (Text) compressed.get(0);

        Text compressedText = new Text(compressed);

        if (Objects.equals(compressedText, text))
            return text;

        return compressedText;
    }

    private static TextComponent apply(Map<TextComponent, UnaryOperator<TextComponent>> funcs,
                                       TextComponent origin,
                                       TextComponent new_) {
        if (funcs.containsKey(origin)) {
            TextComponent apply = funcs.get(origin).apply(new_);
            funcs.remove(origin);
            return apply;
        }

        return new_;
    }

    public static TextComponent single(Object o) {
        if (o instanceof TextComponent)
            return (TextComponent) o;

        if (o.toString().isEmpty())
            return Text.empty();

        return StringComponent.of(o.toString());
    }

    public static TextComponent empty() {
        return Text.EMPTY;
    }

    public static CapitalizeComponent capitalize(TextComponent textComponent) {
        return CapitalizeComponent.of(textComponent);
    }

    public static DecapitalizeComponent decapitalize(TextComponent textComponent) {
        return DecapitalizeComponent.of(textComponent);
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
    public TextComponent mapLocalized(UnaryOperator<List<TextComponent>> operator) {
        return Text.of(this.getComponents().stream()
                .map(textComponent -> textComponent.mapLocalized(operator))
                .collect(Collectors.toList()));
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
