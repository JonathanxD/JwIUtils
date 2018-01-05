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
package com.github.jonathanxd.iutils.keyboard.key;

/**
 * Keyboard keys mapping.
 */
public enum KeyboardKey {
    //Manipulation
    BACKSPACE(8, KeyType.MANIPULATION, "BackSpace"),
    TAB(9, KeyType.MANIPULATION, "Tab"),
    ENTER(13, KeyType.MANIPULATION, "Enter"),

    //Control
    SHIFT(16, KeyType.CONTROL, "Shift"),
    CTRL(17, KeyType.CONTROL, "Control"),
    ALT(18, KeyType.CONTROL, "Alternate"),
    PAUSE_BREAK(19, KeyType.CONTROL, "Pause Break"),

    //Key board states
    CAPS_LOCK(20, KeyType.KEY_BOARD_STATES, "Caps Lock"),

    //ESC/Control
    ESCAPE(27, KeyType.CONTROL, "ESC"),

    //Position
    PAGE_UP(33, KeyType.POSITION, "Page Up"),
    PAGE_DOWN(34, KeyType.POSITION, "Page Down"),
    END(35, KeyType.POSITION, "End"),
    HOME(36, KeyType.POSITION, "HOME"),
    LEFT_ARROW(37, KeyType.POSITION, "<-"),
    UP_ARROW(38, KeyType.POSITION, "Up Arrow"),
    RIGHT_ARROW(39, KeyType.POSITION, "->"),
    DOWN_ARROW(40, KeyType.POSITION, "Down Arrow"),

    //Manipulation
    INSERT(45, KeyType.MANIPULATION, "Insert"),
    DELETE(46, KeyType.MANIPULATION, "Delete"),

    //Numbers
    ZERO(48, KeyType.NUMBERS, "0"),
    ONE(49, KeyType.NUMBERS, "1"),
    TWO(50, KeyType.NUMBERS, "2"),
    THREE(51, KeyType.NUMBERS, "3"),
    FOUR(52, KeyType.NUMBERS, "4"),
    FIVE(53, KeyType.NUMBERS, "5"),
    SIX(54, KeyType.NUMBERS, "6"),
    SEVEN(55, KeyType.NUMBERS, "7"),
    EIGHT(56, KeyType.NUMBERS, "8"),
    NINE(57, KeyType.NUMBERS, "9"),

    //Alphabet
    A(65, KeyType.ALPHABET),
    B(66, KeyType.ALPHABET),
    C(67, KeyType.ALPHABET),
    D(68, KeyType.ALPHABET),
    E(69, KeyType.ALPHABET),
    F(70, KeyType.ALPHABET),
    G(71, KeyType.ALPHABET),
    H(72, KeyType.ALPHABET),
    I(73, KeyType.ALPHABET),
    J(74, KeyType.ALPHABET),
    K(75, KeyType.ALPHABET),
    L(76, KeyType.ALPHABET),
    M(77, KeyType.ALPHABET),
    N(78, KeyType.ALPHABET),
    O(79, KeyType.ALPHABET),
    P(80, KeyType.ALPHABET),
    Q(81, KeyType.ALPHABET),
    R(82, KeyType.ALPHABET),
    S(83, KeyType.ALPHABET),
    T(84, KeyType.ALPHABET),
    U(85, KeyType.ALPHABET),
    V(86, KeyType.ALPHABET),
    W(87, KeyType.ALPHABET),
    X(88, KeyType.ALPHABET),
    Y(89, KeyType.ALPHABET),
    Z(90, KeyType.ALPHABET),

    //Window Keys
    LEFT_SUPER(91, KeyType.SUPER_KEYS, "Left Super"),
    RIGHT_SUPER(92, KeyType.SUPER_KEYS, "Right Super"),

    //Tool
    SELECT(93, KeyType.TOOL, "Tool"),

    //Numpad numbers
    NUMPAD_0(96, KeyType.NUMPAD_NUMBERS, "0"),
    NUMPAD_1(97, KeyType.NUMPAD_NUMBERS, "1"),
    NUMPAD_2(98, KeyType.NUMPAD_NUMBERS, "2"),
    NUMPAD_3(99, KeyType.NUMPAD_NUMBERS, "3"),
    NUMPAD_4(100, KeyType.NUMPAD_NUMBERS, "4"),
    NUMPAD_5(101, KeyType.NUMPAD_NUMBERS, "5"),
    NUMPAD_6(102, KeyType.NUMPAD_NUMBERS, "6"),
    NUMPAD_7(103, KeyType.NUMPAD_NUMBERS, "7"),
    NUMPAD_8(104, KeyType.NUMPAD_NUMBERS, "8"),
    NUMPAD_9(105, KeyType.NUMPAD_NUMBERS, "9"),

    //Math manipulation
    MULTIPLY(106, KeyType.MATH_MANIPULATION, "*"),
    ADD(107, KeyType.MATH_MANIPULATION, "+"),
    SUBTRACT(108, KeyType.MATH_MANIPULATION, "-"),
    DECIMAL_POINT(110, KeyType.MATH_MANIPULATION, "."),
    DIVIDE(111, KeyType.MATH_MANIPULATION, "/"),

    //Function
    F1(112, KeyType.FUNCTION),
    F2(113, KeyType.FUNCTION),
    F3(114, KeyType.FUNCTION),
    F4(115, KeyType.FUNCTION),
    F5(116, KeyType.FUNCTION),
    F6(117, KeyType.FUNCTION),
    F7(118, KeyType.FUNCTION),
    F8(119, KeyType.FUNCTION),
    F9(120, KeyType.FUNCTION),
    F10(121, KeyType.FUNCTION),
    F11(122, KeyType.FUNCTION),
    F12(123, KeyType.FUNCTION),

    //Key board states
    NUM_LOCK(144, KeyType.KEY_BOARD_STATES),
    SCROLL_LOCK(145, KeyType.KEY_BOARD_STATES),

    //Others
    SEMI_COLON(186, KeyType.OTHERS, ";"),
    EQUAL_SIGN(187, KeyType.OTHERS, "="),
    COMMA(188, KeyType.OTHERS, ","),
    DASH(189, KeyType.OTHERS, "-"),
    PERIOD(190, KeyType.OTHERS, "."),
    FORWARD_SLASH(191, KeyType.OTHERS, "/"),
    GRAVE_ACCENT(192, KeyType.OTHERS, "`"),
    OPEN_BRACKET(219, KeyType.OTHERS, "["),
    BACK_SLASH(220, KeyType.OTHERS, "\\"),
    CLOSE_BRAKET(221, KeyType.OTHERS, "]"),
    SINGLE_QUOTE(222, KeyType.OTHERS, "'");

    private final int i;
    private final KeyType keyType;
    private final String name;

    KeyboardKey(int i, KeyType keyType) {
        this.i = i;
        this.keyType = keyType;
        this.name = null;
    }

    KeyboardKey(int i, KeyType keyType, String name) {
        this.i = i;
        this.keyType = keyType;
        this.name = name;
    }

    /**
     * Gets the keyboard key from a int {@code code}.
     *
     * @param code Key code.
     * @return Keyboard key for {@code code}, or null if no one mapped {@link KeyboardKey} has the
     * specified {@code code}.
     */
    public static KeyboardKey fromCode(int code) {
        for (KeyboardKey modifier : KeyboardKey.values()) {

            if (modifier.getCode() == code) {
                return modifier;
            }
        }

        return null;
    }

    /**
     * Gets the code of this key.
     *
     * @return Code of this key.
     */
    public int getCode() {
        return this.i;
    }

    @Override
    public String toString() {
        if (this.name == null) {
            return super.name();
        } else {
            return this.name();
        }
    }

    /**
     * Gets key type.
     *
     * @return Key type.
     */
    public KeyType getKeyType() {
        return this.keyType;
    }

}
