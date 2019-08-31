/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public interface TextComponent {

    /**
     * True if component is empty.
     *
     * @return True if component is empty.
     */
    boolean isEmpty();

    /**
     * True if component is not empty.
     *
     * @return True if component is not empty.
     */
    default boolean isNotEmpty() {
        return !this.isEmpty();
    }

    /**
     * Returns a text component that applies {@code operator} to localized {@link TextComponent
     * TextComponents}, or itself if the component cannot be localized to text components.
     *
     * @param operator Operator of components.
     * @return Text component that applies {@code operator} to localized {@link TextComponent
     * TextComponents}, or itself if the component cannot be localized to text components.
     */
    default TextComponent mapLocalized(UnaryOperator<List<TextComponent>> operator) {
        return this;
    }

    default TextComponent capitalize() {
        return CapitalizeComponent.of(this);
    }

    default TextComponent decapitalize() {
        return DecapitalizeComponent.of(this);
    }

    default TextComponent and(TextComponent textComponent) {
        return Text.of(this, textComponent);
    }

    default TextComponent append(TextComponent textComponent) {
        return Text.of(this, textComponent);
    }

    default TextComponent appendComponent(TextComponent textComponent) {
        return Text.of(this, textComponent);
    }

    default TextComponent append(Object... objects) {
        return Text.of(this, Text.of(objects));
    }

    default TextComponent append(Object o) {
        return Text.of(this, o);
    }

    // Prepend

    default TextComponent prepend(TextComponent textComponent) {
        return Text.of(textComponent, this);
    }

    default TextComponent prepend(Object o) {
        return Text.of(o, this);
    }

    default TextComponent prepend(Object... o) {
        return Text.of(Text.of(o), this);
    }

    /**
     * Applies component arguments.
     *
     * @param args Arguments to apply.
     * @return New text component with applied arguments, or same if this component does not have
     * arguments.
     */
    default TextComponent apply(Map<String, TextComponent> args) {
        return new ArgsAppliedText(this, args);
    }
}
