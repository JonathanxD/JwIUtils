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
package com.github.jonathanxd.iutils.arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 30/04/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerformanceTest {

    private static final int ITERATIONS = 10005;

    PrintWriter voidPrinter = new PrintWriter(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            //Void
        }
    });


    private static List<String> stringList = new ArrayList<>();
    private static Arrays<String> stringArrays = new Arrays<>();

    @Test
    public void a_stringListAdd() {
        for(int x = 0; x < ITERATIONS; ++x) {
            stringList.add(String.valueOf(x).concat("x"));
        }
    }

    @Test
    public void a_zstringListAlertGc() {
        System.gc();
    }

    @Test
    public void b_stringListIterate() {
        for(String s : stringList) {
            clock(s);
        }
    }

    private void clock(String s) {
        voidPrinter.println(s);
    }

    @Test
    public void c_stringListRemove9() {
        stringList.remove("9x");
        stringList.remove("99x");
        stringList.remove("999x");
        stringList.remove("9999x");
    }

    @Test
    public void d_stringListRemove() {
        for(int x = 0; x < stringList.size(); ++x) {
            stringList.remove(x);
        }
    }


    // Arrays

    @Test
    public void a_stringArraysAdd() {
        for(int x = 0; x < ITERATIONS; ++x) {
            stringArrays.add(String.valueOf(x).concat("x"));
        }
    }

    @Test
    public void a_zstringArraysAlertGc() {
        System.gc();
    }

    @Test
    public void b_stringArraysIterate() {
        for(String s : stringArrays) {
            clock(s);
        }
    }

    @Test
    public void c_stringArraysRemove9() {
        stringArrays.remove("9x");
        stringArrays.remove("99x");
        stringArrays.remove("999x");
        stringArrays.remove("9999x");
    }

    @Test
    public void d_stringArraysRemove() {
        for(int x = 0; x < stringArrays.length(); ++x) {
            stringArrays.remove(x);
        }
    }

}
