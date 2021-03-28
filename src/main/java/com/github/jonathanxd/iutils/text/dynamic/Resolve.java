/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.text.dynamic;

import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.localizer.TextLocalizer;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Represents unresolved {@link com.github.jonathanxd.iutils.text.TextComponent} that resolves to
 * {@link T}.
 *
 * @param <T> Target type that this component resolves to (commonly {@link String} or {@link
 *            java.util.List} of {@link com.github.jonathanxd.iutils.text.TextComponent}).
 */
public abstract class Resolve<T> {

    Resolve() {
    }

    /**
     * Resolves the element to {@link T} with {@code localizer}.
     *
     * @param localizer Localizer to used in resolution.
     * @return Resolved object.
     */
    @NotNull
    public abstract T resolve(@NotNull TextLocalizer localizer);

    public static abstract class Localizing<T> extends Resolve<T> {
        Localizing() {
        }

        /**
         * Resolves with {@code localizer} to {@code locale}.
         *
         * @param localizer Localizer to resolve.
         * @param locale    Locale to resolve.
         * @return Resolved object.
         */
        @NotNull
        public abstract T resolve(@NotNull TextLocalizer localizer,
                                  @NotNull Locale locale);

        /**
         * Resolves with {@code localizer} to {@code locale} with {@code args} applied.
         *
         * @param localizer Localizer to resolve.
         * @param locale    Locale to resolve.
         * @param args      Args to apply.
         * @return Resolved object.
         */
        @NotNull
        public abstract T resolve(@NotNull TextLocalizer localizer,
                                  @NotNull Map<String, TextComponent> args,
                                  @NotNull Locale locale);

        /**
         * Resolves with {@code localizer} with {@code args} applied.
         *
         * @param localizer Localizer to resolve.
         * @param args      Args to apply.
         * @return Resolved object.
         */
        @NotNull
        public abstract T resolve(@NotNull TextLocalizer localizer,
                                  @NotNull Map<String, TextComponent> args);
    }

    /**
     * Resolves to single {@link TextComponent}.
     */
    public static abstract class Component extends Resolve<TextComponent> {
        Component() {
        }
    }

    /**
     * Resolves to string
     */
    public static abstract class Str extends Localizing<String> {
        Str() {
        }
    }

    /**
     * Resolves to multiple {@link com.github.jonathanxd.iutils.text.TextComponent TextComponents}.
     */
    public static abstract class Components extends Localizing<List<TextComponent>> {
        Components() {
        }
    }
}
