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
package com.github.jonathanxd.iutils.text.localizer;

import com.github.jonathanxd.iutils.text.TextComponent;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Base interface of localizers.
 */
public interface Localizer {

    /**
     * Returns a list with all localizations that {@code textComponent} resolves to.
     *
     * @param textComponent Component to localize.
     * @return List with all localizations that {@code textComponent} resolves to.
     */
    List<TextComponent> getLocalizations(@NotNull TextComponent textComponent);

    /**
     * Returns a list with all localizations that {@code textComponent} resolves to.
     *
     * @param textComponent Component to localize.
     * @param args          Arguments.
     * @return List with all localizations that {@code textComponent} resolves to.
     */
    List<TextComponent> getLocalizations(@NotNull TextComponent textComponent,
                                         @NotNull Map<String, TextComponent> args);

    /**
     * Localizes {@code textComponent}.
     *
     * @param textComponent Component to localize.
     * @param args          Arguments.
     * @return Localized text.
     */
    @NotNull
    String localize(@NotNull TextComponent textComponent, @NotNull Map<String, TextComponent> args);

    /**
     * Localizes {@code textComponent}.
     *
     * @param textComponent Component to localize.
     * @return Localized text.
     */
    @NotNull
    String localize(@NotNull TextComponent textComponent);
}
