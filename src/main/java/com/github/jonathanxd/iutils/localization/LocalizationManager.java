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
package com.github.jonathanxd.iutils.localization;

import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;

import java.util.ResourceBundle;

/**
 * Manager of localization.
 */
public interface LocalizationManager {

    /**
     * Registers a localization of {@code text} to {@code key}.
     *
     * @param key  Key to register localization.
     * @param text Text to associate to {@code key}.
     * @return True if registered with success, false otherwise.
     */
    boolean registerLocalization(String key, TextComponent text);

    /**
     * Gets the localization registered for {@code key}.
     *
     * The translation is applied by default.
     *
     * @param key Key use to register localization.
     * @return Localization associated to {@code key}, or {@code null} if no one localization was
     * registered to {@code key}.
     */
    TextComponent getLocalization(String key);

    /**
     * Gets the localization registered for {@code key}, never returns null.
     *
     * The translation is applied by default.
     *
     * @param key Key use to register localization.
     * @return Localization associated to {@code key}.
     * @throws IllegalArgumentException if no one localization was registered to {@code key}.
     */
    default TextComponent getRequiredLocalization(String key) throws IllegalArgumentException {
        TextComponent localization = this.getLocalization(key);

        if (localization == null)
            throw new IllegalArgumentException("Missing required localization: '" + key + "'");

        return localization;
    }

    /**
     * Loads localization from {@code bundle} resource.
     *
     * @param bundle Resource to load localization.
     * @return True of loaded with success.
     */
    default boolean loadFromBundle(ResourceBundle bundle) {
        for (String s : bundle.keySet()) {
            this.registerLocalization(s, Text.of(bundle.getString(s)));
        }

        return true;
    }

}
