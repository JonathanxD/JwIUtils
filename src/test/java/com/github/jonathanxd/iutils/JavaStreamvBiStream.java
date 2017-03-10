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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.function.stream.MapStream;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JavaStreamvBiStream {

    static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(11, "B");
        map.put(12, "C");
        map.put(10, "A");
    }

    private static void print(Integer integer, String s) {
        System.out.printf("Integer = %d, String = %s%n", integer, s);
    }

    private static void printStack(StackTraceElement[] stackTraceElements) {
        for (StackTraceElement callInformation : stackTraceElements) {
            System.out.println(callInformation);
        }
    }

    @Test
    public void javaStream() {
        System.out.println("Java Stream");

        map.entrySet().stream().filter(integerStringEntry -> {
            printStack(new RuntimeException().getStackTrace());

            return integerStringEntry.getKey() < 0;
        }).forEach(System.out::println);
    }

    @Test
    public void jwBiStream() {
        System.out.println("Jw BiStream");

        BiStreams.mapStream(map).filter((integer, s) -> {
            printStack(new RuntimeException().getStackTrace());

            return integer < 0;
        }).forEach(JavaStreamvBiStream::print);
    }

    @Test
    public void jwJavaBiStream() {
        System.out.println("Jw BiStream");

        BiStreams.fromJavaStream(map.entrySet().stream()).filter((integer, s) -> {
            printStack(new RuntimeException().getStackTrace());

            return integer < 0;
        }).forEach(JavaStreamvBiStream::print);
    }

}
