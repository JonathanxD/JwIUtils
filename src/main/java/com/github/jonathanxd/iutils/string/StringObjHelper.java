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
package com.github.jonathanxd.iutils.string;

import com.github.jonathanxd.iutils.array.ArrayUtils;
import com.github.jonathanxd.iutils.array.PrimitiveArrayConverter;
import com.github.jonathanxd.iutils.box.IMutableBox;
import com.github.jonathanxd.iutils.box.MutableBox;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class StringObjHelper {

    private static final Character MAP_OPEN = '{';
    private static final Character MAP_CLOSE = '}';
    private static final Character MAP_DEFINE = '=';
    private static final Character LIST_OPEN = '[';
    private static final Character LIST_CLOSE = ']';
    private static final Character[] SEPARATORS = new Character[]{',', ' '};
    private static final Character[] OPEN_CLOSE_CHAR = new Character[]{'"', '\''};
    private static final Character[] PROP_SEPARATORS = new Character[]{'\n'};
    private static final char ESCAPE = '\\';

    /**
     * Parses a string list in the following format:
     *
     * {@code [A, B, C, B]}.
     *
     * List values can be:
     *
     * {@code List} in format specified in this method documentation.
     *
     * {@code Map} in format specified in {@link #parseStringMap(String, boolean)}.
     *
     * {@code String} element.
     *
     * @param stringList String with list in the specified format.
     * @return List with parsed values.
     */
    public static List<Object> parseStringList(String stringList) {
        return StringObjHelper.parseStringList(stringList, true);
    }

    /**
     * Parses a string map in the following format:
     *
     * {@code {A=B, C=D}}.
     *
     * Map values can be:
     *
     * {@code List} in format specified in {@link #parseStringList(String, boolean)}.
     *
     * {@code Map} in format specified in this method documentation.
     *
     * {@code String} element.
     *
     * @param stringMap String with map in the specified format.
     * @return Map with parsed values.
     */
    public static Map<Object, Object> parseStringMap(String stringMap) {
        return StringObjHelper.parseStringMap(stringMap, true);
    }

    /**
     * Parses a string list in the following format:
     *
     * {@code [A, B, C, B]}.
     *
     * List values can be:
     *
     * {@code List} in format specified in this method documentation.
     *
     * {@code Map} in format specified in {@link #parseStringMap(String, boolean)}.
     *
     * {@code String} element.
     *
     * Tags are always required for inner Lists and Maps.
     *
     * @param stringList String with list in the specified format.
     * @param reqTag     Whether {@code []} is required in main {@code stringList}.
     * @return List with parsed values.
     */
    public static List<Object> parseStringList(String stringList, boolean reqTag) {
        Iterator<Character> charIter = IteratorUtil.ofArray(PrimitiveArrayConverter.fromPrimitive(stringList.toCharArray()));

        if (reqTag) {
            if (!charIter.hasNext())
                throw new ListParseException("Empty string list");

            Character c;
            if ((c = charIter.next()) != LIST_OPEN) {
                throw new ListParseException("Expected '" + LIST_OPEN + "' but found '" + c + "'.");
            }
        }

        return parseStringList(charIter, reqTag);
    }

    /**
     * Parses a string map in the following format:
     *
     * {@code {A=B, C=D}}.
     *
     * Map values can be:
     *
     * {@code List} in format specified in {@link #parseStringList(String, boolean)}.
     *
     * {@code Map} in format specified in this method documentation.
     *
     * {@code String} element.
     *
     * Tags are always required for inner Lists and Maps.
     *
     * @param stringMap String with map in the specified format.
     * @param reqTag    Whether {@code {}} is required in main {@code stringMap}.
     * @return Map with parsed values.
     */
    public static Map<Object, Object> parseStringMap(String stringMap, boolean reqTag) {
        Iterator<Character> charIter = IteratorUtil.ofArray(PrimitiveArrayConverter.fromPrimitive(stringMap.toCharArray()));

        if (reqTag) {
            if (!charIter.hasNext())
                throw new MapParseException("Empty string map");

            Character c;
            if ((c = charIter.next()) != MAP_OPEN) {
                throw new MapParseException("Expected '" + MAP_OPEN + "' but found '" + c + "'.");
            }
        }

        return parseStringMap(charIter, reqTag);
    }

    private static List<Object> parseStringList(Iterator<Character> charIter, boolean req) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean lastIsEscape = false;
        boolean[] openCount = new boolean[OPEN_CLOSE_CHAR.length];

        List<Object> list = new ArrayList<>();
        final IMutableBox<Object> obj = new MutableBox<>();

        BiConsumer<Object, Token> set = (input, token) -> {
            if (token != Token.SEPARATOR && token != Token.CLOSE)
                throw new ListParseException("Expected list element but found token: $token.");

            list.add(input);
        };

        Consumer<Object> setObj = (input) -> {
            if (obj.get() != null)
                throw new ListParseException("Expected either list or map, but found both.");

            obj.set(input);
        };

        Consumer<Character> append = stringBuilder::append;
        Consumer<Token> build = token -> {
            if (obj.get() != null && stringBuilder.length() != 0)
                throw new ListParseException("Invalid entry, list object with list entry string." +
                        " Obj: " + obj + ", entry string: " + stringBuilder);
            if (obj.get() != null) {
                set.accept(obj.get(), token);
                obj.set(null);
            } else {
                if (stringBuilder.length() != 0) {
                    set.accept(stringBuilder.toString(), token);
                    stringBuilder.setLength(0);
                }
            }
        };

        while (charIter.hasNext()) {
            Character c = charIter.next();

            int indexOfOpenClose0 = -1;

            for (int i = 0; i < OPEN_CLOSE_CHAR.length; i++) {
                if (OPEN_CLOSE_CHAR[i] == c) {
                    indexOfOpenClose0 = i;
                    break;
                }
            }

            final int indexOfOpenClose = indexOfOpenClose0;

            if (lastIsEscape) {
                lastIsEscape = false;
                append.accept(c);
            } else if (c == ESCAPE) {
                lastIsEscape = true;
            } else if (IntStream.rangeClosed(0, OPEN_CLOSE_CHAR.length - 1)
                    .anyMatch(it -> it != indexOfOpenClose && openCount[it])) {
                append.accept(c);
            } else if (indexOfOpenClose != -1) {
                boolean bValue = openCount[indexOfOpenClose];
                openCount[indexOfOpenClose] = !bValue;
            } else if (ArrayUtils.any(SEPARATORS, t -> t == c)) {
                build.accept(Token.SEPARATOR);
            } else if (c == MAP_OPEN) {
                setObj.accept(parseStringMap(charIter, true));
            } else if (c == MAP_CLOSE) {
                build.accept(Token.CLOSE);
            } else if (c == LIST_OPEN) {
                setObj.accept(parseStringList(charIter, true));
            } else if (c == LIST_CLOSE) {
                build.accept(Token.CLOSE);
                return list;
            } else {
                append.accept(c);
            }
        }

        for (int i = 0; i < openCount.length; i++) {
            if (openCount[i]) {
                // Remaining chars
                append.accept(OPEN_CLOSE_CHAR[i]);
            }
        }

        if (stringBuilder.length() != 0 || obj.isPresent()) {
            build.accept(Token.CLOSE);
        }

        if (req)
            throw new ListParseException("Expected list close tag '" + LIST_CLOSE + "' at end of string");

        return list;
    }

    private static Map<Object, Object> parseStringMap(Iterator<Character> charIter, boolean req) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean lastIsEscape = false;
        boolean[] openCount = new boolean[OPEN_CLOSE_CHAR.length];

        final Map<Object, Object> map = new HashMap<>();
        final IMutableBox<OptObject<Object>> key = new MutableBox<>(Opt.none());
        final IMutableBox<OptObject<Object>> value = new MutableBox<>(Opt.none());
        final IMutableBox<Object> obj = new MutableBox<>();

        Consumer<Object> setObj = (input) -> {
            if (obj.get() != null)
                throw new MapParseException("Expected either list or map, but found both.");

            obj.set(input);
        };

        BiConsumer<Object, Token> set = (input, token) -> {
            if (token != Token.DEFINE && !key.isPresent())
                throw new MapParseException("Expected map define character, but found token: " + token + ".");

            if (token != Token.SEPARATOR && token != Token.CLOSE && key.get().isPresent() && !value.get().isPresent())
                throw new MapParseException("Expected key ('" + key + "') value but found token: " + token + ".");

            if (!key.get().isPresent())
                key.set(Opt.some(input));
            else if (!value.get().isPresent())
                value.set(Opt.some(input));

            if (key.get().isPresent() && value.get().isPresent()) {
                map.put(key.get().getValue(), value.get().getValue());
                key.set(Opt.none());
                value.set(Opt.none());
            }
        };

        Consumer<Character> append = stringBuilder::append;
        Consumer<Token> build = token -> {
            if (obj.get() != null && stringBuilder.length() != 0)
                throw new MapParseException("Invalid entry, map object with map entry string." +
                        " Obj: " + obj + ", entry string: " + stringBuilder);
            if (obj.get() != null) {
                set.accept(obj.get(), token);
                obj.set(null);
            } else {
                if (stringBuilder.length() != 0) {
                    set.accept(stringBuilder.toString(), token);
                    stringBuilder.setLength(0);
                }
            }
        };

        while (charIter.hasNext()) {
            Character c = charIter.next();

            int indexOfOpenClose0 = -1;

            for (int i = 0; i < OPEN_CLOSE_CHAR.length; i++) {
                if (OPEN_CLOSE_CHAR[i] == c) {
                    indexOfOpenClose0 = i;
                    break;
                }
            }

            final int indexOfOpenClose = indexOfOpenClose0;

            if (lastIsEscape) {
                lastIsEscape = false;
                append.accept(c);
            } else if (c == ESCAPE) {
                lastIsEscape = true;
            } else if (IntStream.rangeClosed(0, OPEN_CLOSE_CHAR.length - 1)
                    .anyMatch(it -> it != indexOfOpenClose && openCount[it])) {
                append.accept(c);
            } else if (indexOfOpenClose != -1) {
                boolean bValue = openCount[indexOfOpenClose];
                openCount[indexOfOpenClose] = !bValue;
            } else if (ArrayUtils.any(SEPARATORS, t -> t == c)) {
                build.accept(Token.SEPARATOR);
            } else if (c == MAP_DEFINE) {
                build.accept(Token.DEFINE);
            } else if (c == LIST_OPEN) {
                setObj.accept(parseStringList(charIter, true));
            } else if (c == LIST_CLOSE) {
                build.accept(Token.CLOSE);
            } else if (c == MAP_OPEN) {
                setObj.accept(parseStringMap(charIter, true));
            } else if (c == MAP_CLOSE) {
                build.accept(Token.CLOSE);
                return map;
            } else {
                append.accept(c);
            }

        }

        for (int i = 0; i < openCount.length; i++) {
            if (openCount[i]) {
                // Remaining chars
                append.accept(OPEN_CLOSE_CHAR[i]);
            }
        }

        if (stringBuilder.length() != 0 || obj.isPresent()) {
            build.accept(Token.CLOSE);
        }

        if (req)
            throw new MapParseException("Expected map close '" + MAP_CLOSE + "' at end of string.");

        return map;
    }

    /**
     * Parse a simple properties map (like Java properties).
     *
     * @param propertyString String with properties.
     * @return Property map
     */
    public static Map<String, String> parsePropertyMap(String propertyString) {
        Iterator<Character> charIter = IteratorUtil.ofArray(PrimitiveArrayConverter.fromPrimitive(propertyString.toCharArray()));

        if (!charIter.hasNext())
            return new HashMap<>();

        return StringObjHelper.parsePropertyMap(charIter);
    }

    /**
     * Parse a simple properties map (like Java properties).
     *
     * @param charIter Iterator
     * @return Property map
     */
    public static Map<String, String> parsePropertyMap(Iterator<Character> charIter) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean lastIsEscape = false;
        boolean[] openCount = new boolean[OPEN_CLOSE_CHAR.length];

        final Map<String, String> map = new HashMap<>();
        final IMutableBox<OptObject<String>> key = new MutableBox<>(Opt.none());
        final IMutableBox<OptObject<String>> value = new MutableBox<>(Opt.none());

        BiConsumer<String, Token> set = (input, token) -> {
            if (token != Token.DEFINE && !key.isPresent())
                throw new MapParseException("Expected map define character, but found token: " + token + ".");

            if (token != Token.SEPARATOR && token != Token.CLOSE && key.get().isPresent() && !value.get().isPresent())
                throw new MapParseException("Expected key ('" + key + "') value but found token: " + token + ".");

            if (!key.get().isPresent())
                key.set(Opt.some(input));
            else if (!value.get().isPresent())
                value.set(Opt.some(input));

            if (key.get().isPresent() && value.get().isPresent()) {
                map.put(key.get().getValue(), value.get().getValue());
                key.set(Opt.none());
                value.set(Opt.none());
            }
        };

        Consumer<Character> append = stringBuilder::append;
        Consumer<Token> build = token -> {
            if (stringBuilder.length() != 0) {
                set.accept(stringBuilder.toString(), token);
                stringBuilder.setLength(0);
            }
        };

        while (charIter.hasNext()) {
            Character c = charIter.next();

            int indexOfOpenClose0 = -1;

            for (int i = 0; i < OPEN_CLOSE_CHAR.length; i++) {
                if (OPEN_CLOSE_CHAR[i] == c) {
                    indexOfOpenClose0 = i;
                    break;
                }
            }

            final int indexOfOpenClose = indexOfOpenClose0;

            if (lastIsEscape) {
                lastIsEscape = false;
                append.accept(c);
            } else if (c == ESCAPE) {
                lastIsEscape = true;
            } else if (IntStream.rangeClosed(0, OPEN_CLOSE_CHAR.length - 1)
                    .anyMatch(it -> it != indexOfOpenClose && openCount[it])) {
                append.accept(c);
            } else if (indexOfOpenClose != -1) {
                boolean bValue = openCount[indexOfOpenClose];
                openCount[indexOfOpenClose] = !bValue;
            } else if (ArrayUtils.any(PROP_SEPARATORS, t -> t == c)) {
                build.accept(Token.SEPARATOR);
            } else if (c == MAP_DEFINE && (!key.isPresent() || !key.get().isPresent())) {
                build.accept(Token.DEFINE);
            } else {
                append.accept(c);
            }

        }

        if (stringBuilder.length() != 0) {
            build.accept(Token.CLOSE);
        }

        return map;
    }

    enum Token {
        SEPARATOR,
        DEFINE,
        CLOSE
    }

    public static final class ListParseException extends RuntimeException {
        public ListParseException(String message) {
            super(message);
        }
    }

    public static final class MapParseException extends RuntimeException {
        public MapParseException(String message) {
            super(message);
        }
    }
}
