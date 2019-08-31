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

import com.github.jonathanxd.iutils.string.StringObjHelper;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.TextUtil;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocaleLoaders {

    private static final LocaleLoader LANG_LOADER = new LocaleLoader() {
        @Override
        public Map<String, List<TextComponent>> create(String string) {
            Map<String, List<TextComponent>> components = new HashMap<>();

            Map<String, String> properties = StringObjHelper.parsePropertyMap(string);

            for (Map.Entry<String, String> objectObjectEntry : properties.entrySet()) {
                components.computeIfAbsent(objectObjectEntry.getKey(), f -> new ArrayList<>())
                        .add(TextUtil.parse(objectObjectEntry.getValue()));
            }

            return components;
        }

        @Override
        public String extension() {
            return "lang";
        }
    };

    private static final LocaleLoader LANG_MAP_LOADER = new LocaleLoader() {
        @Override
        public Map<String, List<TextComponent>> create(String string) {
            return TextUtil.parseMap(string);
        }

        @Override
        public String extension() {
            return "lang";
        }
    };

    /**
     * Returns {@code .lang} locale loader. Lang format is:
     *
     * <pre>
     * {@code
     * path.to.key=Translation
     * path.to.key2=Translation
     * }
     * </pre>
     *
     * @return Simple lang locale loader
     */
    @Contract(pure = true)
    public static LocaleLoader langLoader() {
        return LANG_LOADER;
    }

    /**
     * Returns {@code .lang} map locale loader. This uses <a href="https://github.com/JwIUtils/wiki/JISDF#Map">
     * JwIUtils Simple Data Format (JISDF) map variant format</a>, but starting { and and ending } is not required.
     *
     * Example:
     *
     * <pre>
     * {@code
     * path={
     *     to={
     *         key="Translation"
     *         key2="Translation"
     *         list=["A", "B", "C"]
     *     }
     * }
     * }
     * </pre>
     *
     * @return Simple lang locale loader
     */
    @Contract(pure = true)
    public static LocaleLoader langMapLoader() {
        return LANG_MAP_LOADER;
    }
}
