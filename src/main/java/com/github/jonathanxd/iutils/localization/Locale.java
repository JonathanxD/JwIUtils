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
package com.github.jonathanxd.iutils.localization;

import com.github.jonathanxd.iutils.string.TextHelper;
import com.github.jonathanxd.iutils.string.TextParser;
import com.github.jonathanxd.iutils.text.TextComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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
    default boolean load(Path path, String baseName, ClassLoader classLoader) {
        String name = (baseName == null ? "" : baseName + "_") + this.getName() + ".lang";

        Path rPath = (path == null ? Paths.get(name) : path.resolve(name));
        URL resource = classLoader.getResource(rPath.toString());

        if (resource == null)
            return false;

        try (InputStream resourceAsStream = resource.openStream()) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);

            for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
                this.getLocalizationManager().registerLocalization(
                        (String) objectObjectEntry.getKey(),
                        TextParser.parse((String) objectObjectEntry.getValue())
                );
            }

            return true;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * Loads localizations from resource. The resource should follow the name template: {@code
     * baseName_localeName.lang} and map template specified in {@link TextHelper#parseStringMap(String)}.
     * Newline are converted into {@code ,}, so, it is not necessary when newline is present.
     * without initial tags ({@code {}}).
     *
     * @param path        Path to resource directory.
     * @param baseName    Base name of resource.
     * @param classLoader Class loader.
     * @return True if loaded with success.
     */
    default boolean loadMap(Path path, String baseName, ClassLoader classLoader) {
        String name = (baseName == null ? "" : baseName + "_") + this.getName() + ".lang";

        Path rPath = (path == null ? Paths.get(name) : path.resolve(name));
        URL resource = classLoader.getResource(rPath.toString());

        if (resource == null)
            return false;

        try (BufferedReader stream = new BufferedReader(new InputStreamReader(resource.openStream()))) {

            String s = stream.lines()
                    .filter(c -> !c.isEmpty())
                    .collect(Collectors.joining("\n"));

            Map<String, TextComponent> map = TextParser.parseMap(s);

            for (Map.Entry<String, TextComponent> entry : map.entrySet()) {
                this.getLocalizationManager().registerLocalization(entry.getKey(), entry.getValue());
            }

            return true;
        } catch (IOException e) {
            return false;
        }

    }

}
