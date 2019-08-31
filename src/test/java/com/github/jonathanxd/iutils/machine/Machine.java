/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.machine;

import java.util.Optional;

public class Machine {

    private final OS os;
    private final Spec machineSpec;

    public Machine(OS os, Spec machineSpec) {
        this.os = os;
        this.machineSpec = machineSpec;
    }

    public Optional<OS> getOs() {
        return Optional.ofNullable(os);
    }

    public Optional<Spec> getMachineSpec() {
        return Optional.ofNullable(machineSpec);
    }

    @Override
    public String toString() {
        return "Machine[ OS[" + getOs().orElse(null) + "], Spec[?]]";
    }

    public enum MemoryVersion {
        DDR1,
        DDR2,
        DDR3,
        DDR4
    }

    public interface Spec {

        Optional<Integer> usbPorts();

        Optional<Integer> satas();

        MemorySlotSpec[] memorySlots();

        ProcessorSpec[] processors();
    }

    public interface ProcessorSpec {
        int cores();

        String[] instructions();
    }

    public interface MemorySlotSpec {
        Optional<MemoryVersion> version();
    }

    public interface OS {
        String name();

        String version();
    }

}
