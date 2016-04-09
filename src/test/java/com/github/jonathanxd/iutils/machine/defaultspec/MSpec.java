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
package com.github.jonathanxd.iutils.machine.defaultspec;

import com.github.jonathanxd.iutils.machine.Machine;

import java.util.Optional;

/**
 * Created by jonathan on 05/03/16.
 */
public class MSpec implements Machine.Spec {
    @Override
    public Optional<Integer> usbPorts() {
        return Optional.of(6);
    }

    @Override
    public Optional<Integer> satas() {
        return Optional.of(4);
    }

    @Override
    public Machine.MemorySlotSpec[] memorySlots() {
        return new Machine.MemorySlotSpec[]{new MSpecMemory(), new MSpecMemory(), new MSpecMemory(), new MSpecMemory()};
    }

    @Override
    public Machine.ProcessorSpec[] processors() {
        return new Machine.ProcessorSpec[] {new MSpecProcessor()};
    }

    private static class MSpecMemory implements Machine.MemorySlotSpec {

        @Override
        public Optional<Machine.MemoryVersion> version() {
            return Optional.of(Machine.MemoryVersion.DDR4);
        }
    }

    private static class MSpecProcessor implements Machine.ProcessorSpec {

        @Override
        public int cores() {
            return 8;
        }

        @Override
        public String[] instructions() {
            return new String[]{"E8", "0F", "CF"};
        }
    }
}
