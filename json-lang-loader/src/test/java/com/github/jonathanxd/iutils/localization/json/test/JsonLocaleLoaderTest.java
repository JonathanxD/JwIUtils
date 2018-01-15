/*
 *      JwIUtils-Json-lang-loader - Loader of json lang files <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.localization.json.test;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.localization.Locales;
import com.github.jonathanxd.iutils.localization.LocalizationManager;
import com.github.jonathanxd.iutils.localization.json.JsonLocaleLoader;
import com.github.jonathanxd.iutils.text.Text;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

public class JsonLocaleLoaderTest {

    Locale locale = Locales.create("en_us");

    @Before
    public void loadLocale() {
        JsonLocaleLoader.JSON_LOCALE_LOADER
                .loadFromResource(locale, Paths.get("."), "mylang", JsonLocaleLoader.class.getClassLoader());
    }

    @Test
    public void jsonLocaleLoader() {
        LocalizationManager localizationManager = locale.getLocalizationManager();

        Assert.assertEquals(null, localizationManager.getLocalization("messages"));
        Assert.assertEquals(Text.of("Example2."), localizationManager.getLocalization("messages.greeting"));
        Assert.assertEquals(
                Collections3.listOf(Text.of("Hello ", Text.variable("name"), "."), Text.of("Example2.")),
                localizationManager.getLocalizations("messages.greeting"));

        Assert.assertEquals(Text.of("Invalid credentials."), localizationManager.getLocalization("messages.auth.invalid_cred"));

        Assert.assertEquals(Text.of("Session expired."), localizationManager.getLocalization("messages.auth.expired"));
    }

}
