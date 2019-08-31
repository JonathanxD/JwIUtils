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

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.text.TextComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MapLocalizationManager implements LocalizationManager {
    private final Map<String, List<TextComponent>> componentMap = new HashMap<>();

    @Override
    public boolean registerLocalization(String key, TextComponent text) {
        boolean first = !this.componentMap.containsKey(key);

        this.componentMap.computeIfAbsent(key, k -> new ArrayList<>()).add(text);

        return first;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean registerLocalizations(String key, Iterable<? extends TextComponent> texts) {
        boolean first = !this.componentMap.containsKey(key);

        if (texts instanceof Collection<?>) {
            this.componentMap.computeIfAbsent(key, k -> new ArrayList<>())
                    .addAll((Collection<? extends TextComponent>) texts);
        } else {
            for (TextComponent text : texts) {
                this.registerLocalization(key, text);
            }
        }

        return first;
    }

    @Override
    public List<TextComponent> getLocalizations(String key) {
        List<TextComponent> components = this.componentMap.get(key);

        if (components == null) {
            return Collections.emptyList();
        }

        if (components.isEmpty()) {
            this.componentMap.remove(key);
            return Collections.emptyList();
        }

        return new ArrayList<>(components);
    }

    @Override
    public TextComponent getLocalization(String key) {
        List<TextComponent> localizations = this.getLocalizations(key);

        if (localizations.isEmpty())
            return null;

        return Collections3.last(localizations);
    }

}
