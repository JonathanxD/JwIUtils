/*
 *      JwIUtils-Json-lang-loader - Loader of json lang files <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.localization.json;

import com.github.jonathanxd.iutils.localization.LocaleLoader;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.TextUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;
import java.util.Map;

public final class JsonLocaleLoader implements LocaleLoader {

    public static final JsonLocaleLoader JSON_LOCALE_LOADER = new JsonLocaleLoader();

    private JsonLocaleLoader() {
    }

    @Override
    public Map<String, List<TextComponent>> create(String json) throws Exception {
        return this.create((JSONObject) new JSONParser().parse(json));
    }

    @Override
    public String extension() {
        return "json";
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<TextComponent>> create(JSONObject jsonObject) {
        Map<Object, Object> map = (Map<Object, Object>) jsonObject;

        return TextUtil.parseMap(map);
    }
}
