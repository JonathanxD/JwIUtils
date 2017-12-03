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
package com.github.jonathanxd.iutils.text;

import com.github.jonathanxd.iutils.string.StringObjHelper;

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

    private static final int SINGLE = -1;
    private static final int VARIABLE = 0;
    private static final int LOCALIZABLE = 1;

    public static Map<String, TextComponent> parseMap(String receiver) {
        receiver = receiver.replace("\n", ",");

        Map<String, TextComponent> componentMap = new HashMap<>();
        Map<Object, Object> map = StringObjHelper.parseStringMap(receiver, false);

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (!(key instanceof String))
                throw new IllegalArgumentException("Only strings are supported as lang keys. Key: " + key + ".");

            TextComponent current = componentMap.get(key);
            TextComponent newComponent = null;

            if (value instanceof String) {
                newComponent = TextUtil.parse((String) value);
            } else if (value instanceof List<?>) {
                List<?> list = (List<?>) value;
                Iterator<?> iterator = list.iterator();

                while (iterator.hasNext()) {
                    Object o = iterator.next();
                    if (!(o instanceof String))
                        throw new IllegalArgumentException("Only strings are supported" +
                                " as element of lang lists. Element: " + o + ".");

                    String s = o + (iterator.hasNext() ? "\n" : "");

                    if (newComponent == null)
                        newComponent = TextUtil.parse(s);
                    else
                        newComponent = newComponent.append(TextUtil.parse(s));
                }

            } else {
                throw new IllegalArgumentException("Value '" + value + "' is not supported as lang value.");
            }

            if (current != null)
                newComponent = current.append(newComponent);

            componentMap.put((String) key, newComponent);
        }

        return componentMap;
    }

    /**
     * Parses {@link TextComponent} from string. Input string should be a Text in form of string ({@link #toString(TextComponent)}).
     * Variables must start with {@code $} and variable name can only contains valid identifier characters
     * ({@link Character#isUnicodeIdentifierPart(char)}) (but not {@code $} or {@code #}, using one of them will create a new component).
     * Localizable text must start with {@code #}, and have same rules as the variable, but can have {@code .} (dot) as part of
     * localization name.
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
        StringBuilder stringBuilder = new StringBuilder();
        Consumer<String> addVar = s -> components.add(Text.variable(s));
        Consumer<String> addLocalizable = s -> components.add(Text.localizable(s));
        Consumer<String> addSingle = s -> components.add(Text.single(s));

        for (char aChar : chars) {
            if (aChar == '\\') {
                if (lastIsEscape) {
                    stringBuilder.append(aChar);
                    lastIsEscape = false;
                } else {
                    lastIsEscape = true;
                }
            } else {
                int component = !lastIsEscape
                        ? (aChar == '$' ? VARIABLE : aChar == '#' ? LOCALIZABLE : SINGLE)
                        : SINGLE;

                int currentType = isVar ? VARIABLE : isLocalizable ? LOCALIZABLE : SINGLE;

                if (component != SINGLE || (!TextUtil.isIdentifier(aChar, currentType) && !lastIsEscape)) {
                    String text = stringBuilder.toString();
                    stringBuilder.setLength(0);

                    if (isLocalizable) {
                        addLocalizable.accept(text);
                        isLocalizable = false;
                    } else if (isVar) {
                        addVar.accept(text);
                        isVar = false;
                    } else {
                        addSingle.accept(text);
                    }
                }

                switch (component) {
                    case LOCALIZABLE: {
                        isLocalizable = true;
                        break;
                    }
                    case VARIABLE: {
                        isVar = true;
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
     * Creates a plain string from {@code textComponent}. Variables starts with {@code $} and localizable component to {@code #}.
     * {@link CapitalizeComponent}, {@link DecapitalizeComponent} and {@link ArgsAppliedText} are
     * not convertible, so this method will simple unbox and convert them.
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
     * Creates a plain string from {@code textComponent}. Variables starts with {@code $} and localizable component to {@code #}.
     * {@link CapitalizeComponent}, {@link DecapitalizeComponent} and {@link ArgsAppliedText} are
     * not convertible, so this method will simple unbox and convert them.
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
        } else {
            throw new UnsupportedOperationException("Cannot convert component: '" + textComponent + "'!");
        }

    }

    private static void appendEscaped(String s, StringBuilder sb) {
        for (char c : s.toCharArray()) {
            if (c == '$' || c == '#')
                sb.append('\\');

            sb.append(c);
        }
    }

    private static void appendVariableEscaped(String s, StringBuilder sb) {
        sb.append('$');
        for (char c : s.toCharArray()) {
            if (c == '#')
                sb.append('\\');

            sb.append(c);
        }
    }

    private static void appendLocalizableEscaped(String s, StringBuilder sb) {
        sb.append('#');
        for (char c : s.toCharArray()) {
            if (c == '$')
                sb.append('\\');

            sb.append(c);
        }
    }

    private static boolean isIdentifier(char ch, int type) {
        return (Character.isUnicodeIdentifierPart(ch)
                || (ch == '.' && type == LOCALIZABLE))
                && (ch != '$' && ch != '#');
    }

}
