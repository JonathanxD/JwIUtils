/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.iutils.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;

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

}
