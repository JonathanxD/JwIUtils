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
package com.github.jonathanxd.iutils.localization;

import com.github.jonathanxd.iutils.exception.LocaleLoadException;
import com.github.jonathanxd.iutils.text.TextComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface LocaleLoader {
    /**
     * Loads {@code locale} localizations from resource of {@code classLoader}.
     *
     * This function searches for a resource with following name format name:
     *
     * {@link Locale#getName() locale name} {@code .} {@link #extension() locale loader extension}.
     *
     * @param locale      Locale to load from resource.
     * @param classLoader Classloader with resource.
     */
    default void loadFromResource(@NotNull Locale locale,
                                  @NotNull ClassLoader classLoader) throws LocaleLoadException {
        this.loadFromResource(locale, null, classLoader);
    }


    /**
     * Loads {@code locale} localizations from resource of {@code classLoader} in {@code path}.
     *
     * This function searches for a resource with following name format name:
     *
     * {@code path} {@link Locale#getName() locale name} {@code .} {@link #extension() locale loader
     * extension}.
     *
     * @param locale      Locale to load from resource.
     * @param path        Path to resource (omitted if null).
     * @param classLoader Classloader with resource.
     */
    default void loadFromResource(@NotNull Locale locale,
                                  @Nullable Path path,
                                  @NotNull ClassLoader classLoader) throws LocaleLoadException {
        this.loadFromResource(locale, path, null, classLoader);
    }

    /**
     * Loads {@code locale} localizations from resource of {@code classLoader} in {@code path}.
     *
     * This function searches for a resource with following name format name:
     *
     * {@code path} {@code baseName _} {@link Locale#getName() locale name} {@code .} {@link
     * #extension() locale loader extension}.
     *
     * @param locale      Locale to load from resource.
     * @param path        Path to resource (omitted if null).
     * @param baseName    Base resource name (omitted if null).
     * @param classLoader Classloader with resource.
     */
    default void loadFromResource(@NotNull Locale locale,
                                  @Nullable Path path,
                                  @Nullable String baseName,
                                  @NotNull ClassLoader classLoader) throws LocaleLoadException {

        String name = (baseName == null ? "" : baseName + "_") + locale.getName() + "." + this.extension();

        Path rPath = (path == null ? Paths.get(name) : path.resolve(name));
        URL resource = classLoader.getResource(rPath.toString());

        if (resource == null)
            throw new LocaleLoadException("Resource cannot be found: '" + rPath.toString() + "'");

        try (BufferedReader stream = new BufferedReader(new InputStreamReader(resource.openStream(), "UTF-8"))) {
            this.create(stream).forEach((key, comps) -> locale.getLocalizationManager().registerLocalizations(key, comps));
        } catch (Exception e) {
            throw new LocaleLoadException(e);
        }
    }

    /**
     * Creates localization map from {@code reader}.
     *
     * @param reader Reader of localizations.
     * @return Localization map.
     */
    default Map<String, List<TextComponent>> create(BufferedReader reader) throws Exception {
        return this.create(reader.lines());
    }

    /**
     * Creates localization map from {@code stream}.
     *
     * @param stream Stream with localizations.
     * @return Localization map.
     */
    default Map<String, List<TextComponent>> create(Stream<String> stream) throws Exception {
        return this.create(stream.filter(c -> !c.isEmpty()).collect(Collectors.joining("\n")));
    }

    /**
     * Creates localization map from {@code string}.
     *
     * @param string String with localizations.
     * @return Localization map.
     */
    Map<String, List<TextComponent>> create(String string) throws Exception;

    /**
     * Extension that this loader supports.
     *
     * @return Extension that this loader supports.
     */
    String extension();
}
