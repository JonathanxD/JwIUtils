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
package com.github.jonathanxd.iutils.localization;

import com.github.jonathanxd.iutils.exception.LocaleLoadException;
import com.github.jonathanxd.iutils.string.StringObjHelper;

import java.nio.file.Path;

/**
 * Locale represents a language.
 *
 * Locale names must be lower case (there is no enforcement, but is the convention).
 */
public interface Locale {

    /**
     * Gets the locale name, ex: pt_br, en_us, en_uk.
     *
     * @return Locale name, ex: pt_br, en_us, en_uk.
     */
    String getName();

    /**
     * Gets the localization manager of this locale.
     *
     * @return Localization manager.
     */
    LocalizationManager getLocalizationManager();

    /**
     * Loads localizations from resource. The resource should follow the name template: {@code
     * baseName_localeName.lang}
     *
     * @param path        Path to resource directory.
     * @param baseName    Base name of resource.
     * @param classLoader Class loader.
     * @return True if loaded with success.
     */
    default void load(Path path, String baseName, ClassLoader classLoader) throws LocaleLoadException {
        LocaleLoaders.langLoader().loadFromResource(this, path, baseName, classLoader);
    }

    /**
     * Loads localizations from resource. The resource should follow the name template: {@code
     * baseName_localeName.lang} and map template specified in {@link StringObjHelper#parseStringMap(String)}.
     * Newline are converted into {@code ,}, so, it is not necessary when newline is present.
     * without initial tags ({@code {}}).
     *
     * @param path        Path to resource directory.
     * @param baseName    Base name of resource.
     * @param classLoader Class loader.
     * @return True if loaded with success.
     */
    default void loadMap(Path path, String baseName, ClassLoader classLoader) throws LocaleLoadException {
        LocaleLoaders.langMapLoader().loadFromResource(this, path, baseName, classLoader);
    }

}
