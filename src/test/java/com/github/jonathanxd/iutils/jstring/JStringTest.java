/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
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
package com.github.jonathanxd.iutils.jstring;

import com.github.jonathanxd.iutils.map.MapUtils;
import com.github.jonathanxd.iutils.string.JString;
import com.github.jonathanxd.iutils.string.SimpleStringExpression;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JStringTest {

    @Test
    public void jstringTest1() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("JonathanxD", Integer.MAX_VALUE));

        Assert.assertEquals("First: JonathanxD", JString.of("First: ${personList.get(0).getName()}", "personList", personList).toString());

    }

    @Test
    public void jstringTest() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("Maria", 32));

        personList.add(new Person("Marcos", 21));

        personList.add(new Person("Marcelo", 21));

        personList.forEach(person -> System.out.println(JString.of("Name: ${person.name}, Age: ${person.age}.", "person", person)));

        Assert.assertEquals("First: Maria", JString.of("First: ${personList.get(0).getName()}", "personList", personList).toString());

        SimpleStringExpression.evaluateExpression("System.out.println(\"Hello World\")", MapUtils.mapOf("System", System.class));
    }

    @Test
    public void propertyTag() {
        Assert.assertEquals("A->B = hi. b:c = k. x.y = l",
                JString.of("A->B = ${a->b}. b:c = ${b:c}. x.y = ${x.y}", "a->b", "hi",
                        "b:c", "k",
                        "x.y", "l")
                        .toString()
        );
    }

    public static class Person {
        public final String name;
        public final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public boolean equals(Object obj) {

            if (obj instanceof Person) {
                return ((Person) obj).name.equals(this.name) && ((Person) obj).age == this.age;
            }

            return super.equals(obj);
        }

        @Override
        public String toString() {
            return name + " -> " + age;
        }
    }
}
