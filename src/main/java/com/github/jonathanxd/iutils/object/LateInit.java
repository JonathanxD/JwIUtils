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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.exception.InitializationException;

/**
 * Base class of all late init instances. Lateinit are holder of values that can be initialized
 * later, trying to get the value of them before initialization results in {@link
 * InitializationException} exception.
 */
public abstract class LateInit {

    private final String name;
    private boolean isInitialized;

    LateInit(String name) {
        this.name = name;
    }

    /**
     * Creates a late init reference.
     *
     * @param name Identification name.
     * @param <E>  Type.
     * @return Late init reference.
     */
    public static <E> LateInit.Ref<E> lateRef(String name) {
        return new Ref<>(name);
    }

    /**
     * Creates a late init lazy.
     *
     * @param name Identification name.
     * @param <E>  Type.
     * @return Late init lazy.
     */
    public static <E> LateInit.LLazy<E> lateLazy(String name) {
        return new LLazy<>(name);
    }

    /**
     * Creates a late init boolean.
     *
     * @param name Identification name.
     * @return Late init reference boolean.
     */
    public static LateInit.Bool lateBool(String name) {
        return new Bool(name);
    }

    /**
     * Creates a late init character.
     *
     * @param name Identification name.
     * @return Late init reference character.
     */
    public static LateInit.Char lateChar(String name) {
        return new Char(name);
    }

    /**
     * Creates a late init byte.
     *
     * @param name Identification name.
     * @return Late init reference byte.
     */
    public static LateInit.Byte lateByte(String name) {
        return new Byte(name);
    }

    /**
     * Creates a late init short.
     *
     * @param name Identification name.
     * @return Late init reference short.
     */
    public static LateInit.Short lateShort(String name) {
        return new Short(name);
    }

    /**
     * Creates a late init int.
     *
     * @param name Identification name.
     * @return Late init reference int.
     */
    public static LateInit.Int lateInt(String name) {
        return new Int(name);
    }

    /**
     * Creates a late init long.
     *
     * @param name Identification name.
     * @return Late init reference long.
     */
    public static LateInit.Long lateLong(String name) {
        return new Long(name);
    }

    /**
     * Creates a late init float.
     *
     * @param name Identification name.
     * @return Late init reference float.
     */
    public static LateInit.Float lateFloat(String name) {
        return new Float(name);
    }

    /**
     * Creates a late init double.
     *
     * @param name Identification name.
     * @return Late init reference double.
     */
    public static LateInit.Double lateDouble(String name) {
        return new Double(name);
    }

    /**
     * Returns whether this late init instance is already initialized or not.
     *
     * @return Whether this late init instance is already initialized or not.
     */
    public final boolean isInitialized() {
        return this.isInitialized;
    }

    private void setInitialized(boolean initialized) {
        this.isInitialized = initialized;
    }

    /**
     * Gets the identification name of this late init instance.
     *
     * @return Identification name of this late init instance.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * De-initializes this late init instance. Protected by default, but may be exposed by other
     * implementations.
     */
    protected void deInit() {
        this.setNotInitState();
    }

    void initCheck() {
        if (this.isInitialized())
            throw new InitializationException("LateInit '" + this.getName() + "' is already initialized.");
    }

    void accessCheck() {
        if (!this.isInitialized())
            throw new InitializationException("LateInit '" + this.getName() + "' was not initialized.");
    }

    protected void setNotInitState() {
        this.setInitialized(false);
    }

    protected void setInitState() {
        this.setInitialized(true);
    }

    public static class Ref<E> extends LateInit {

        private E value;

        Ref(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(E value) {
            this.initCheck();
            this.value = value;
            this.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public E getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateRef[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + this.getValue().toString();
            else
                return "";
        }
    }

    public static class LLazy<E> extends LateInit {

        private Lazy<E> value;

        LLazy(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(Lazy<E> value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public E getValue() {
            this.accessCheck();
            return this.value.get();
        }

        @Override
        public String toString() {
            return "LateLazy[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                if (this.value.isEvaluated())
                    return ", value=" + this.getValue().toString();
                else
                    return ", value=[@lazy]";
            else
                return "";
        }
    }

    public static class Bool extends LateInit {
        private boolean value;

        Bool(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(boolean value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public boolean getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateBoolean[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Boolean.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Char extends LateInit {
        private char value;

        Char(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(char value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public char getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateChar[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Character.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Byte extends LateInit {
        private byte value;

        Byte(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(byte value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public byte getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateByte[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Byte.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Short extends LateInit {
        private short value;

        Short(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(short value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public short getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateShort[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Short.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Int extends LateInit {
        private int value;

        Int(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(int value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public int getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateInt[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Integer.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Long extends LateInit {
        private long value;

        Long(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(long value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public long getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateLong[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Long.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Float extends LateInit {
        private float value;

        Float(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(float value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public float getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateFloat[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Float.toString(this.getValue());
            else
                return "";
        }
    }

    public static class Double extends LateInit {
        private double value;

        Double(String name) {
            super(name);
        }

        /**
         * Initialize the value.
         *
         * @param value Value.
         * @throws InitializationException if already initialized.
         */
        public void init(double value) {
            this.initCheck();
            this.value = value;
            super.setInitState();
        }

        /**
         * Gets the value.
         *
         * @return Value.
         * @throws InitializationException if not initialized.
         */
        public double getValue() {
            this.accessCheck();
            return this.value;
        }

        @Override
        public String toString() {
            return "LateDouble[name=" + this.getName() + ", initialized=" + this.isInitialized() + this.toValue() + "]";
        }

        private String toValue() {
            if (this.isInitialized())
                return ", value=" + java.lang.Double.toString(this.getValue());
            else
                return "";
        }
    }
}
