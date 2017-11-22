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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Locales {
    private static final Map<java.util.Locale, Locale> map = new ConcurrentHashMap<>();
    private static final Map<String, Locale> cache = new ConcurrentHashMap<>();

    /**
     * Creates a new locale with {@code name}.
     *
     * The value is cached.
     *
     * @param name Lower case locale name, example {@code en_us}, {@code pt_br}.
     * @return New locale.
     */
    public static Locale create(String name) {
        synchronized (cache) {
            Locale g = cache.get(name);

            if (g == null) {
                g = new Locale() {
                    private final LocalizationManager manager = new MapLocalizationManager();

                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public LocalizationManager getLocalizationManager() {
                        return manager;
                    }
                };
            }

            cache.put(name, g);

            return g;
        }
    }

    /**
     * Creates a locale from java locale.
     *
     * The value is cached.
     *
     * @param javaLocale Java locale.
     * @return Locale from Java locale.
     */
    public static Locale from(java.util.Locale javaLocale) {
        synchronized (map) {
            Locale l = map.get(javaLocale);

            if (l == null) {
                l = new Locale() {
                    private final LocalizationManager manager = new MapLocalizationManager();

                    @Override

                    public String getName() {
                        return javaLocale.getLanguage().toLowerCase() + "_" + javaLocale.getCountry().toLowerCase();
                    }

                    @Override
                    public LocalizationManager getLocalizationManager() {
                        return this.manager;
                    }
                };

                map.put(javaLocale, l);
            }

            return l;
        }
    }


}
