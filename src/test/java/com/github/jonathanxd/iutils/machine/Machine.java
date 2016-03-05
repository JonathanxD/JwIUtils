/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.machine;

import java.util.Optional;

/**
 * Created by jonathan on 05/03/16.
 */
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

    public enum MemoryVersion {
        DDR1,
        DDR2,
        DDR3,
        DDR4
    }

    public interface OS {
        String name();
        String version();
    }

    @Override
    public String toString() {
        return "Machine[ OS["+getOs().orElse(null)+"], Spec[?]]";
    }
}
