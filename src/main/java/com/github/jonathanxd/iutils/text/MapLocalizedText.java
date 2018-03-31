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

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Mapper to apply to list of resolved components of a {@link LocalizableComponent}.
 */
public final class MapLocalizedText implements TextComponent {
    private final LocalizableComponent localizableComponent;
    private final UnaryOperator<List<TextComponent>> operator;

    MapLocalizedText(LocalizableComponent localizableComponent,
                     UnaryOperator<List<TextComponent>> operator) {
        this.localizableComponent = localizableComponent;
        this.operator = operator;
    }

    public LocalizableComponent getLocalizableComponent() {
        return this.localizableComponent;
    }

    public UnaryOperator<List<TextComponent>> getOperator() {
        return this.operator;
    }

    @Override
    public TextComponent mapLocalized(UnaryOperator<List<TextComponent>> operator) {
        return new MapLocalizedText(this.getLocalizableComponent(),
                it -> operator.apply(this.getOperator().apply(it))
        );
    }

    @Override
    public boolean isEmpty() {
        return this.getLocalizableComponent().isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapLocalizedText)
            return Objects.equals(((MapLocalizedText) obj).getLocalizableComponent(), this.getLocalizableComponent())
                    && Objects.equals(((MapLocalizedText) obj).getOperator(), this.getOperator());

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLocalizableComponent(), this.getOperator());
    }

    @Override
    public String toString() {
        return "MapLocalized[" + this.getLocalizableComponent() + ", operator=" + this.getOperator() + "]";
    }
}
