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
package com.github.jonathanxd.iutils.jstring;

import com.github.jonathanxd.iutils.string.JString;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 28/05/16.
 */
public class JStringTest {

    @Test
    public void jstringTest() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("Maria", 32));

        personList.add(new Person("Marcos", 21));

        personList.add(new Person("Marcelo", 21));

        personList.forEach(person -> System.out.println(JString.of("Nome: ${person.name}, Idade: ${person.idade}.", "person", person)));
    }

    public static class Person {
        public final String name;
        public final int idade;

        public Person(String name, int idade) {
            this.name = name;
            this.idade = idade;
        }
    }
}
