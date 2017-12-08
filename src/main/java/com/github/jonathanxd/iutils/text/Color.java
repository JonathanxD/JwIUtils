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

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Color, this may or may not be supported by the receiver of the text, unsupported colors are
 * commonly translated to default color, which is determined by the receiver.
 *
 * Note that equality of two colors depends on {@code rgba} and the {@code name} does not matter,
 * name is only used for easy identification (like in UIs).
 */
public final class Color implements TextComponent {

    private static final Map<String, Color> CACHED_COLORS = new ConcurrentHashMap<>();

    private final String name;
    private final int r;
    private final int g;
    private final int b;
    private final float a;

    /**
     * Creates a RGBA color.
     *
     * @param name Name of the color.
     * @param r    Red color. (0-255)
     * @param g    Green color. (0-255)
     * @param b    Blue color. (0-255)
     * @param a    Alpha. (0.0-1.0)
     */
    private Color(String name, int r, int g, int b, float a) {
        Objects.requireNonNull(name, "Name cannot be null");
        checkRange("Red color", r);
        checkRange("Green color", g);
        checkRange("Blue color", b);
        checkAlphaRange(a);
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    // Public API

    /**
     * Creates a color with {@code name} or returns a cached one. Color is cached by color name, if
     * the color have the same {@code name} as the cached one but does not have same {@code rgba}
     * values, then a new one is returned, but not cached.
     *
     * @param name Name of color.
     * @param r    Red color.
     * @param g    Green color.
     * @param b    Blue color.
     * @return New color or a cached one.
     */
    public static Color createColor(String name, int r, int g, int b) {
        return Color.createColor(name, r, g, b, 1.0F);
    }

    /**
     * Creates a color with {@code name} or returns a cached one. Color is cached by color name, if
     * the color have the same {@code name} as the cached one but does not have same {@code rgba}
     * values, then a new one is returned, but not cached.
     *
     * @param name Name of color.
     * @param r    Red color.
     * @param g    Green color.
     * @param b    Blue color.
     * @param a    Alpha.
     * @return New color or a cached one.
     */
    public static Color createColor(String name, int r, int g, int b, float a) {
        Color cached = CACHED_COLORS.get(name);

        if (cached != null && cached.getR() == r && cached.getG() == g && cached.getB() == b && cached.getA() == a) {
            return cached;
        }

        Color color = new Color(name, r, g, b, a);

        if (cached == null) {
            CACHED_COLORS.put(name, color);
        }

        return color;
    }

    private static void checkRange(String name, int value) {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException(name + " value must be between 0-255");
    }

    private static void checkAlphaRange(float value) {
        if (value < 0.0F || value > 1.0F)
            throw new IllegalArgumentException("Alpha value must be between 0.0-1.0");
    }

    /**
     * Gets the color name.
     *
     * @return Color name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the red color, range between 0-255.
     *
     * @return Red color.
     */
    public int getR() {
        return this.r;
    }

    /**
     * Gets the green color, range between 0-255.
     *
     * @return Green color.
     */
    public int getG() {
        return this.g;
    }

    /**
     * Gets the blue color, range between 0-255.
     *
     * @return Blue color.
     */
    public int getB() {
        return this.b;
    }

    /**
     * Gets the alpha, range between 0.0-1.0.
     *
     * @return Alpha.
     */
    public float getA() {
        return this.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getR(), this.getG(), this.getB(), this.getA());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Color))
            return super.equals(obj);

        return this.getR() == ((Color) obj).getR()
                && this.getG() == ((Color) obj).getG()
                && this.getB() == ((Color) obj).getB()
                && this.getA() == ((Color) obj).getA();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
