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

import com.github.jonathanxd.iutils.function.stream.MapStream;
import com.github.jonathanxd.iutils.machine.Machine;
import com.github.jonathanxd.iutils.machine.defaultspec.MSpec;
import com.github.jonathanxd.iutils.machine.os.Linux;
import com.github.jonathanxd.iutils.machine.os.Windows;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestBiStream {


    @Test
    public void test() {


        Map<String, Map<String, Machine>> allMachines = new HashMap<>();

        //////////////////////////////////////////////////////////////////////////////
        Map<String, Machine> machineMap = new HashMap<>();

        machineMap.put("MACHINE_1", new Machine(new Linux(), new MSpec()));

        allMachines.put("MACHINE_MAP_1", machineMap);
        //////////////////////////////////////////////////////////////////////////////

        Map<String, Machine> machineMap2 = new HashMap<>();

        machineMap2.put("MACHINE_1", new Machine(new Windows(), new MSpec()));
        machineMap2.put("MACHINE_2", new Machine(new Linux(), new MSpec()));

        allMachines.put("MACHINE_MAP_2", machineMap2);
        //////////////////////////////////////////////////////////////////////////////
        MapStream.of(allMachines)
                .flatMap((key, value) -> MapStream.of(value))
                .forEach((s, machine1) -> System.out.println("Machine name: " + s + ". Machine: " + machine1));


    }

}
