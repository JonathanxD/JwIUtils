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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.function.stream.MapStream;
import com.github.jonathanxd.iutils.machine.Machine;
import com.github.jonathanxd.iutils.machine.defaultspec.MSpec;
import com.github.jonathanxd.iutils.machine.os.Linux;
import com.github.jonathanxd.iutils.machine.os.Windows;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonathan on 05/03/16.
 */
public class TestBiStream {


    public static void main(String[] args) {


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
