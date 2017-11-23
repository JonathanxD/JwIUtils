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

import java.util.Map;
import java.util.Objects;

public final class CapitalizeComponent implements TextComponent {
    private final TextComponent textComponent;

    private CapitalizeComponent(TextComponent textComponent) {
        this.textComponent = textComponent;
    }

    public static CapitalizeComponent of(TextComponent textComponent) {
        return new CapitalizeComponent(textComponent);
    }

    public TextComponent getTextComponent() {
        return this.textComponent;
    }

    @Override
    public TextComponent apply(Map<String, TextComponent> args) {
        return CapitalizeComponent.of(this.textComponent.apply(args));
    }

    @Override
    public boolean isEmpty() {
        return this.getTextComponent().isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CapitalizeComponent)
            return Objects.equals(((CapitalizeComponent) obj).getTextComponent(), this.getTextComponent());

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.getTextComponent().hashCode();
    }

    @Override
    public String toString() {
        return "Capitalize[" + this.getTextComponent() + "]";
    }
}
