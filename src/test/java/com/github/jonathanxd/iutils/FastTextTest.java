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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.localization.LocaleManager;
import com.github.jonathanxd.iutils.localization.LocalizationManager;
import com.github.jonathanxd.iutils.localization.MapLocaleManager;
import com.github.jonathanxd.iutils.localization.MapLocalizationManager;
import com.github.jonathanxd.iutils.map.MapUtils;
import com.github.jonathanxd.iutils.text.MapLocalizedOperators;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.dynamic.Resolve;
import com.github.jonathanxd.iutils.text.localizer.FastTextLocalizer;
import com.github.jonathanxd.iutils.text.localizer.TextLocalizer;
import com.github.jonathanxd.iutils.text.dynamic.DynamicGenerator;
import com.github.jonathanxd.iutils.text.dynamic.Section;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FastTextTest {

    @Test
    public void textTest() {
        LocaleManager localeManager = new MapLocaleManager();

        EnUsLocale enUs = new EnUsLocale();
        PtBrLocale ptBr = new PtBrLocale();

        enUs.load(Paths.get("text_test"), null, this.getClass().getClassLoader());
        ptBr.load(Paths.get("text_test"), null, this.getClass().getClassLoader());
        ptBr.loadMap(Paths.get("text_test"), "map", this.getClass().getClassLoader());

        localeManager.registerLocale(enUs);
        localeManager.registerLocale(ptBr);

        TextLocalizer localize = new FastTextLocalizer(localeManager, enUs);

        Text text = Text.of(
                Text.localizable("kill").capitalize(),
                " ",
                Text.variable("amount"),
                " ",
                Text.localizable("players")
        );

        TextComponent kill = Text.localizable("message").apply(MapUtils.mapOf(
                "killer", Text.of("ProPlayer"),
                "killed", Text.of("Noob")
        ));

        String s = localize.localize(text, MapUtils.mapOf("amount", Text.of(5)));
        String s2 = localize.localize(text, MapUtils.mapOf("amount", Text.of(5)), ptBr);
        String x1 = localize.localize(kill);
        String x2 = localize.localize(kill, ptBr);

        Assert.assertEquals("Kill 5 players", s);
        Assert.assertEquals("Mate 5 jogadores", s2);
        Assert.assertEquals("Player ProPlayer killed Noob.", x1);
        Assert.assertEquals("Jogador ProPlayer matou Noob.", x2);

        Stub generate = DynamicGenerator.generate(Stub.class);

        String stub1 = localize.localize(generate.getNotify("Moo"));
        String stub2 = localize.localize(generate.getNotify("Moo"), ptBr);

        List<String> resolve = generate.getWelcomeMessages().resolve(localize, ptBr).stream()
                .map(localize::localize)
                .collect(Collectors.toList());


        Assert.assertEquals("Notify Moo please.", stub1);
        Assert.assertEquals("Notifique o Moo por favor.", stub2);

        TextComponent helloMessage = Text.localizable("hello_message").apply(
                MapUtils.mapOf("name", Text.of("unknown"))
        );

        TextComponent helloMessages = Text.localizable("hello_messages");

        Assert.assertEquals("Hello unknown", localize.localize(helloMessage, ptBr));
        Assert.assertEquals("Hello,\n" +
                "parser\n" +
                "don't\n" +
                "care\n" +
                "about\n" +
                "commas\n" +
                "in\n" +
                "lang\n" +
                "neither\n" +
                "about\n" +
                "empty\n" +
                "lines", localize.localize(helloMessages, ptBr));

        List<String> list = Collections3.listOf("Hello,",
                "parser",
                "don't",
                "care",
                "about",
                "commas",
                "in",
                "lang",
                "neither",
                "about",
                "empty",
                "lines");

        Assert.assertEquals(list, resolve);

        Assert.assertEquals("Hello, " +
                "parser " +
                "don't " +
                "care " +
                "about " +
                "commas " +
                "in " +
                "lang " +
                "neither " +
                "about " +
                "empty " +
                "lines", localize.localize(helloMessages.mapLocalized(MapLocalizedOperators.join(Text.of(" "))), ptBr));

        TextComponent twoVarsText = Text.of("Hello ", Text.variable("name")).apply(MapUtils.mapOf(
                "name", Text.single("JwIUtils")
        )).append(Text.of(" ", Text.variable("name")));
        String twoVarsTextLocalized = localize.localize(twoVarsText);

        Assert.assertEquals("Hello JwIUtils $name", twoVarsTextLocalized);
    }

    public interface Stub {
        @Section({"message", "notify"})
        TextComponent getNotify(@Named("user") String user);

        @Section({"hello_messages"})
        Resolve.Components getWelcomeMessages();
    }

    private static class EnUsLocale implements Locale {

        private final LocalizationManager manager = new MapLocalizationManager();

        @Override
        public String getName() {
            return "en_us";
        }

        @Override
        public LocalizationManager getLocalizationManager() {
            return this.manager;
        }
    }

    private static class PtBrLocale implements Locale {

        private final LocalizationManager manager = new MapLocalizationManager();

        @Override
        public String getName() {
            return "pt_br";
        }

        @Override
        public LocalizationManager getLocalizationManager() {
            return this.manager;
        }
    }
}
