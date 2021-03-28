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
package com.github.jonathanxd.iutils.text;

import com.github.jonathanxd.iutils.exception.LocalizationMapParseException;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.recursion.Element;
import com.github.jonathanxd.iutils.recursion.ElementUtil;
import com.github.jonathanxd.iutils.recursion.Elements;
import com.github.jonathanxd.iutils.string.StringObjHelper;
import com.github.jonathanxd.iutils.type.Primitive;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Parsers localization value into {@link TextComponent}.
 */
public class TextUtil {

    private static final int TAG_CLOSE = -2;
    private static final int SINGLE = -1;
    private static final int VARIABLE = 0;
    private static final int LOCALIZABLE = 1;
    private static final int COLOR_OR_STYLE = 2;

    @SuppressWarnings("unchecked")
    public static Map<String, List<TextComponent>> parseMap(String receiver) {
        receiver = receiver.replace("\n", ",");
        Map<Object, Object> map = StringObjHelper.parseStringMap(receiver, false);
        return TextUtil.parseMap(map);
    }

    /**
     * Parses {@code Localization map} from {@code map}. The input map must have only {@link String}
     * keys and values of following types: {@code primitive}, {@link String}, {@link List} (of any
     * of listed types) and {@link Map} (with values of any listed types, keys must be of {@link
     * String} type).
     *
     * @param map Map with localizations.
     * @return Localization map.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, List<TextComponent>> parseMap(Map<Object, Object> map) throws LocalizationMapParseException {

        Map<String, List<TextComponent>> componentMap = new HashMap<>();

        if (!map.isEmpty()) {
            Elements<Map.Entry<Object, Object>> entries = new Elements<>();
            List<Map.Entry<Object, Object>> mapEntries = new ArrayList<>(map.entrySet());

            entries.insertFromPair(ElementUtil.fromIterable(mapEntries));

            Element<Map.Entry<Object, Object>> entry;

            while ((entry = entries.nextElement()) != null) {
                Object key = entry.value.getKey();
                Object value = entry.value.getValue();

                if (!(key instanceof String))
                    throw new LocalizationMapParseException("Only string is supported as key type. Key: " + key + ".");

                if (value instanceof List<?> && !((List<?>) value).isEmpty()) {
                    List<Object> array = (List<Object>) value;
                    Iterator<Object> arraIterator = array.iterator();

                    Iterator<Map.Entry<Object, Object>> iterator =
                            IteratorUtil.mappedIterator(arraIterator, v -> new AbstractMap.SimpleEntry<>(key, v));

                    entries.insertFromPair(ElementUtil.fromIterable(() -> iterator));
                } else if (value instanceof Map<?, ?> && !((Map<?, ?>) value).isEmpty()) {
                    Map<Object, Object> obj = (Map<Object, Object>) value;

                    Iterator<Map.Entry<Object, Object>> iterator =
                            IteratorUtil.mappedIterator(obj.entrySet().iterator(),
                                    v -> new AbstractMap.SimpleEntry<>(key + "." + v.getKey(), v.getValue()));

                    entries.insertFromPair(ElementUtil.fromIterable(() -> iterator));
                } else if (Primitive.instanceOfAnyPrimitiveBox(value) || value instanceof String) {
                    componentMap.computeIfAbsent((String) key, f -> new ArrayList<>()).add(TextUtil.parse(String.valueOf(value)));
                } else {
                    throw new IllegalArgumentException("Invalid input '" + value + "' in map object '" + map + "'.");
                }
            }
        }

        return componentMap;
    }

    /**
     * Parses {@link TextComponent} from string. Input string should be a Text in form of string
     * ({@link #toString(TextComponent)}).
     *
     * Variables must start with {@code $} and variable name can only contains valid identifier
     * characters ({@link Character#isUnicodeIdentifierPart(char)}) (but not {@code $}, {@code #} or
     * {@code &}, using one of them will create a new component).
     *
     * Localizable text must start with {@code #}, and have same rules as the variable, but can have
     * {@code .} (dot) as part of localization name.
     *
     * Color or style must start with {@code &}, and have same rules as the variable, see {@link
     * ColorAndStyleTable} for color and style representation.
     *
     * Example: {@code Welcome $user_name, #messages.welcome}.
     *
     * Localizable texts can be used as global variables, but are not recommended.
     *
     * You can also escape the text using {@code \}.
     *
     * @param receiver String to parse.
     * @return Component.
     */
    public static TextComponent parse(String receiver) {
        List<TextComponent> components = new ArrayList<>();
        char[] chars = receiver.toCharArray();
        boolean lastIsEscape = false;
        boolean isVar = false;
        boolean isLocalizable = false;
        boolean isColorOrStyle = false;
        boolean isOpenTag = false;
        StringBuilder stringBuilder = new StringBuilder();
        Consumer<String> addVar = s -> components.add(Text.variable(s));
        Consumer<String> addLocalizable = s -> components.add(Text.localizable(s));
        Consumer<String> addSingle = s -> components.add(Text.single(s));
        Consumer<String> addColor = s -> components.add(ColorAndStyleTable.TABLE2.get(s.charAt(0)));

        for (char aChar : chars) {
            if (aChar == '\\') {
                if (lastIsEscape) {
                    stringBuilder.append(aChar);
                    lastIsEscape = false;
                } else {
                    lastIsEscape = true;
                }
            } else {
                int component = !lastIsEscape ? (
                        aChar == '$' ? VARIABLE : aChar == '#' ? LOCALIZABLE : aChar == '&' ? COLOR_OR_STYLE
                                : (aChar == '}' && isOpenTag) ? TAG_CLOSE : SINGLE
                ) : SINGLE;

                int currentType = isVar ? VARIABLE : isLocalizable ? LOCALIZABLE : isColorOrStyle ? COLOR_OR_STYLE : SINGLE;

                // Process tag open
                if (!lastIsEscape && currentType != SINGLE) {
                    if (aChar == '{' && !isOpenTag) {
                        isOpenTag = true;
                        continue;
                    }
                }

                // If component is tag close, set openTag boolean to salfe
                if (component == TAG_CLOSE) {
                    isOpenTag = false;
                }

                // Process last char component
                if (component != SINGLE
                        || ((!TextUtil.isIdentifier(aChar, currentType) && !lastIsEscape)
                        || (currentType == COLOR_OR_STYLE && stringBuilder.length() > 0))
                        || component == TAG_CLOSE) {
                    String text = stringBuilder.toString();
                    stringBuilder.setLength(0);

                    if (isLocalizable) {
                        addLocalizable.accept(text);
                        isLocalizable = false;
                    } else if (isVar) {
                        addVar.accept(text);
                        isVar = false;
                    } else if (isColorOrStyle) {
                        addColor.accept(text);
                        isColorOrStyle = false;
                    } else {
                        addSingle.accept(text);
                    }
                }
                // Set current char component
                switch (component) {
                    case LOCALIZABLE: {
                        isLocalizable = true;
                        break;
                    }
                    case VARIABLE: {
                        isVar = true;
                        break;
                    }
                    case COLOR_OR_STYLE: {
                        isColorOrStyle = true;
                        break;
                    }
                    case TAG_CLOSE: {
                        break;
                    }
                    default: {
                        stringBuilder.append(aChar);
                        break;
                    }
                }
            }
        }

        if (stringBuilder.length() != 0) {
            String text = stringBuilder.toString();
            stringBuilder.setLength(0);
            if (isVar) {
                addVar.accept(text);
            } else if (isLocalizable) {
                addLocalizable.accept(text);
            } else {
                addSingle.accept(text);
            }
        }

        return Text.of(components);
    }

    /**
     * Creates a plain string from {@code textComponent}. Variables starts with {@code $} and
     * localizable component to {@code #}. {@link CapitalizeComponent}, {@link
     * DecapitalizeComponent} and {@link ArgsAppliedText} are not convertible, so this method will
     * simple unbox and convert them.
     *
     * @param textComponent Component to convert to plain string.
     * @return Plain serialized string of component.
     */
    public static String toString(TextComponent textComponent) {
        StringBuilder sb = new StringBuilder();
        TextUtil.toString(textComponent, sb);
        return sb.toString();
    }

    /**
     * Creates a plain string from {@code textComponent}. Variables starts with {@code $} and
     * localizable component to {@code #}. {@link CapitalizeComponent}, {@link
     * DecapitalizeComponent} and {@link ArgsAppliedText} are not convertible, so this method will
     * simple unbox and convert them.
     *
     * @param textComponent Component to convert to plain string.
     * @param sb            String buffer to put result characters.
     * @return Plain serialized string of component.
     */
    public static void toString(TextComponent textComponent, StringBuilder sb) {

        if (textComponent instanceof Text) {
            Text text = (Text) textComponent;
            for (TextComponent component : text.getComponents()) {
                TextUtil.toString(component, sb);
            }
        } else if (textComponent instanceof CapitalizeComponent) {
            TextUtil.toString(((CapitalizeComponent) textComponent).getTextComponent(), sb);
        } else if (textComponent instanceof DecapitalizeComponent) {
            TextUtil.toString(((DecapitalizeComponent) textComponent).getTextComponent(), sb);
        } else if (textComponent instanceof ArgsAppliedText) {
            TextUtil.toString(((ArgsAppliedText) textComponent).getComponent(), sb);
        } else if (textComponent instanceof StringComponent) {
            appendEscaped(((StringComponent) textComponent).getText(), sb);
        } else if (textComponent instanceof VariableComponent) {
            appendVariableEscaped(((VariableComponent) textComponent).getVariable(), sb);
        } else if (textComponent instanceof LocalizableComponent) {
            appendLocalizableEscaped(((LocalizableComponent) textComponent).getLocalization(), sb);
        } else if (textComponent instanceof Color || textComponent instanceof Style) {
            sb.append('&').append(ColorAndStyleTable.TABLE.get(textComponent));
        } else {
            throw new UnsupportedOperationException("Cannot convert component: '" + textComponent + "'!");
        }

    }

    private static void appendEscaped(String s, StringBuilder sb) {
        for (char c : s.toCharArray()) {
            if (c == '$' || c == '#' || c == '&')
                sb.append('\\');

            sb.append(c);
        }
    }

    private static void appendVariableEscaped(String s, StringBuilder sb) {
        sb.append('$');
        for (char c : s.toCharArray()) {
            if (c == '#' || c == '&')
                sb.append('\\');

            sb.append(c);
        }
    }

    private static void appendLocalizableEscaped(String s, StringBuilder sb) {
        sb.append('#');
        for (char c : s.toCharArray()) {
            if (c == '$' || c == '&')
                sb.append('\\');

            sb.append(c);
        }
    }

    private static boolean isIdentifier(char ch, int type) {
        return (Character.isUnicodeIdentifierPart(ch)
                || (ch == '.' && type == LOCALIZABLE))
                && (ch != '$' && ch != '#' && ch != '&');
    }

    /**
     * Defines the correspondence between {@code &} and color and style. This is based on Minecraft
     * Color Codes and only used in String serialization of text.
     */
    public static class ColorAndStyleTable {
        public static Map<TextComponent, Character> TABLE = new HashMap<>();
        public static Map<Character, TextComponent> TABLE2 = new HashMap<>();

        static {
            put(Colors.BLACK, '0');
            put(Colors.DARK_BLUE, '1');
            put(Colors.DARK_GREEN, '2');
            put(Colors.DARK_AQUA, '3');
            put(Colors.DARK_RED, '4');
            put(Colors.DARK_PURPLE, '5');
            put(Colors.GOLD, '6');
            put(Colors.GRAY, '7');
            put(Colors.DARK_GRAY, '8');
            put(Colors.BLUE, '9');
            put(Colors.GREEN, 'a');
            put(Colors.AQUA, 'b');
            put(Colors.RED, 'c');
            put(Colors.LIGHT_PURPLE, 'd');
            put(Colors.YELLOW, 'e');
            put(Colors.WHITE, 'f');
            put(Styles.OBFUSCATED, 'k');
            put(Styles.BOLD, 'l');
            put(Styles.STRIKE_THROUGH, 'm');
            put(Styles.UNDERLINE, 'n');
            put(Styles.ITALIC, 'o');

            put(Colors.RESET, 'r');
            put(Styles.RESET, 'r');
        }

        public static void put(TextComponent o, Character c) {
            TABLE.put(o, c);
            TABLE2.put(c, o);
        }
    }

}
