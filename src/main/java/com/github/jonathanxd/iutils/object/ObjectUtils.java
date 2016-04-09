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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.arrays.Arrays.PrimitiveArray;
import com.github.jonathanxd.iutils.reflection.Reflection;

public class ObjectUtils {

    public static Arrays<Byte> getObjectBytes(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(object);
        oos.flush();
        oos.close();

        return Arrays.ofG(PrimitiveArray.fromPrimitive(baos.toByteArray()));
    }

    public static Arrays<Byte> getObjectBytesSecure(Object object) {
        Arrays<Byte> bytes = new Arrays<>();

        try {
            bytes = getObjectBytes(object);
        } catch (Exception e) {
        }

        return bytes;
    }

    public static byte[] getObjectBytesArray(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(object);
        oos.flush();
        oos.close();

        return baos.toByteArray();
    }

    public static <T> T objectFromBytes(Arrays<Byte> bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(PrimitiveArray.toPrimitive(bytes.toGenericArray()));
        ObjectInputStream ois = new ObjectInputStream(bais);

        @SuppressWarnings("unchecked")
        T object = (T) ois.readObject();
        ois.close();

        return object;
    }

    public static Object objectFromBytesSecure(Arrays<Byte> bytes) {
        Object object = null;

        try {
            object = objectFromBytes(bytes);
        } catch (Exception e) {
        }

        return object;
    }

    public static StringHelper toHelper(Object object) {
        return new StringHelper(object);
    }

    public static StringHelper deepFields(Object object) {
        return deepFields(object, false);
    }

    public static StringHelper deepFields(Object object, boolean semiRecursive) {

        StringHelper helper = toHelper(object);

        Collection<Field> fields = Reflection.fieldCollection(object, semiRecursive, false, false);
        for (Field field : fields) {
            if (!field.isAccessible())
                field.setAccessible(true);

            try {
                helper.set(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
            }
        }

        return helper;
    }

    public static boolean isInstanceOfAny(Object source, Class<?>... instanceOfObjects) {
        for(Class<?> instanceOf : instanceOfObjects) {
            if(instanceOf.isAssignableFrom(source.getClass())) {
                return true;
            }
        }

        return false;
    }

    public static <E> String strip(E[] elements, Function<E, String> elementStringFactory) {

        StringJoiner sj = new StringJoiner("\n");

        sj.add("");

        for(E element : elements) {
            sj.add(elementStringFactory.apply(element));
        }

        return sj.toString();
    }

    public static byte[] compress(byte[] bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            OutputStream out = new DeflaterOutputStream(baos);
            out.write(bytes);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

    public static byte[] decompress(byte[] bytes) {
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while((len = in.read(buffer))>0)
                baos.write(buffer, 0, len);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
