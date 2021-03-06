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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.localization.LocaleManager;
import com.github.jonathanxd.iutils.localization.LocalizationManager;
import com.github.jonathanxd.iutils.localization.MapLocaleManager;
import com.github.jonathanxd.iutils.localization.MapLocalizationManager;
import com.github.jonathanxd.iutils.map.MapUtils;
import com.github.jonathanxd.iutils.text.CapitalizeComponent;
import com.github.jonathanxd.iutils.text.Colors;
import com.github.jonathanxd.iutils.text.Styles;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.TextUtil;
import com.github.jonathanxd.iutils.text.localizer.DefaultTextLocalizer;
import com.github.jonathanxd.iutils.text.localizer.TextLocalizer;
import com.github.jonathanxd.iutils.text.dynamic.DynamicGenerator;
import com.github.jonathanxd.iutils.text.dynamic.Section;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class TextTest {

    @Test
    public void textAsString() {
        Text text = Text.of("Hello ", Text.variable("player"), ", ",
                Text.localizable("message.welcome"), Text.variable("a"),
                Text.localizable("b"));

        String gen = TextUtil.toString(text);

        String txt = "Hello $player, #message.welcome$a#b";

        TextComponent parse = TextUtil.parse(txt);

        Assert.assertEquals(txt, gen);
        Assert.assertEquals(text, parse);
    }

    @Test
    public void textAsString2() {
        Text text = Text.of("Hello ", Text.variable("player"), ", ",
                Colors.RED, Styles.UNDERLINE,
                Text.localizable("message.welcome"), Text.variable("a"),
                Text.localizable("b"));

        String gen = TextUtil.toString(text);

        String txt = "Hello $player, &c&n#message.welcome$a#b";

        TextComponent parse = TextUtil.parse(txt);

        Assert.assertEquals(txt, gen);
        Assert.assertEquals(text, parse);
    }

    @Test
    public void parseText() {
        String txt = "Hello $player, #message.welcome$a#b";

        TextComponent parse = TextUtil.parse(txt);

        Assert.assertEquals(
                Text.of("Hello ",
                        Text.variable("player"), ", ",
                        Text.localizable("message.welcome"),
                        Text.variable("a"),
                        Text.localizable("b")),
                parse
        );
    }
    @Test
    public void parseTextTag() {
        String txt = "Hello ${player}ish, #{message.welcome}ish";

        TextComponent parse = TextUtil.parse(txt);

        Assert.assertEquals(
                Text.of("Hello ",
                        Text.variable("player"), "ish, ",
                        Text.localizable("message.welcome"),
                        Text.single("ish")),
                parse
        );
    }

    @Test
    public void parseText2() {
        String txt = "Hello $player, &c&n#message.welcome$a#b";

        TextComponent parse = TextUtil.parse(txt);

        Assert.assertEquals(
                Text.of("Hello ",
                        Text.variable("player"), ", ",
                        Colors.RED, Styles.UNDERLINE,
                        Text.localizable("message.welcome"),
                        Text.variable("a"),
                        Text.localizable("b")),
                parse
        );
    }

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

        Text text = Text.of(
                Text.localizable("kill").capitalize(),
                " ",
                Text.variable("amount"),
                " ",
                Text.localizable("players")
        );

        TextLocalizer localize = new DefaultTextLocalizer(localeManager, enUs);

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
    }

    @Test
    public void testCompress() {
        Text firstUncompressed = Text.ofUncompressed("A", "B");
        Text secondUncompressed = Text.ofUncompressed(firstUncompressed);

        Assert.assertFalse(firstUncompressed == secondUncompressed);

        Text firstCompressed = Text.of("A", "B");
        Text secondCompressed = Text.of(firstCompressed);

        Assert.assertTrue(firstCompressed == secondCompressed);

        Assert.assertNotEquals(Text.ofUncompressed(Text.ofUncompressed("A", "B")), firstUncompressed);
        Assert.assertEquals(Text.of(Text.of("A", "B")), firstCompressed);
    }

    @Test
    public void testCompress2() {
        TextComponent variable = Text.variable("a");

        Text of = Text.of(variable, Text.of(Text.variable("a").capitalize()));

        Assert.assertTrue(variable == of.getComponents().get(0));
        Assert.assertTrue(of.getComponents().get(1) instanceof CapitalizeComponent);
        Assert.assertTrue(variable == ((CapitalizeComponent) of.getComponents().get(1)).getTextComponent());
    }

    @Test
    public void testCompress3() {
        TextComponent variable = Text.variable("a");

        Text of = Text.of("A", "B", variable, "C");

        Assert.assertEquals(Text.of("AB", variable, "C"), of);
    }

    public interface Stub {
        @Section({"message", "notify"})
        TextComponent getNotify(@Named("user") String user);
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
