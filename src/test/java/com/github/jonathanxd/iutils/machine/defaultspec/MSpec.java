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
