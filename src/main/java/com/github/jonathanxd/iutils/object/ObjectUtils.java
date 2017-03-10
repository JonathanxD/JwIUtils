/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

/**
 * Object utilities.
 */
public final class ObjectUtils {

    private ObjectUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Serialize {@code object} to a byte array.
     *
     * @param object Object to serialize.
     * @return Object array of serialized object.
     * @throws IOException See {@link ObjectOutputStream#writeObject(Object)}.
     */
    public static byte[] getObjectBytes(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(object);
        oos.flush();
        oos.close();

        return baos.toByteArray();
    }

    /**
     * Serialize {@code object} to a byte array.
     *
     * @param object Object to serialize.
     * @return {@link Optional} of object array, or a empty {@link Optional} if serialization
     * failed.
     */
    public static Optional<byte[]> getObjectBytesSafe(Object object) {
        try {
            return Optional.of(ObjectUtils.getObjectBytes(object));
        } catch (Exception ignored) {
        }

        return Optional.empty();
    }

    /**
     * Deserialized object from the array.
     *
     * @param bytes Array with serialized object.
     * @param <T>   Type of object.
     * @return Instance of object.
     * @throws IOException            See {@link ObjectInputStream#readObject()}
     * @throws ClassNotFoundException See {@link ObjectInputStream#readObject()}
     */
    public static <T> T getObjectFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);

        @SuppressWarnings("unchecked")
        T object = (T) ois.readObject();
        ois.close();

        return object;
    }

    /**
     * Deserialized object from the array.
     *
     * @param bytes Array with serialized object.
     * @return {@link Optional} of instance of deserilized object, or a empty {@link Optional} if
     * deserialization failed.
     */
    public static Optional<Object> getObjectFromBytesSafe(byte[] bytes) {
        try {
            return Optional.of(ObjectUtils.getObjectFromBytes(bytes));
        } catch (Exception ignored) {
        }

        return Optional.empty();
    }

    /**
     * Returns true if {@code source} is a instance of any {@code instanceOfClasses}.
     *
     * @param source            Instance to test.
     * @param instanceOfClasses Classes to check if {@code source} is instance.
     * @return True if {@code source} is a instance of any {@code instanceOfClasses}.
     */
    public static boolean isInstanceOfAny(Object source, Class<?>... instanceOfClasses) {
        for (Class<?> instanceOf : instanceOfClasses) {
            if (instanceOf.isInstance(source)) {
                return true;
            }
        }

        return false;
    }

}
