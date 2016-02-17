/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by jonathan on 27/01/16.
 */
public class MethodSpecification {

    private final Optional<String> name;
    private final Optional<Class<?>[]> parameterTypes;
    private final Optional<Class<?>> returnType;

    public MethodSpecification(String name, Class<?>[] parameterTypes, Class<?> returnType) {
        this.name = Optional.ofNullable(name);
        this.parameterTypes = Optional.ofNullable(parameterTypes);
        this.returnType = Optional.ofNullable(returnType);
    }

    public boolean match(Method m) {
        m.setAccessible(true);
        if (this.name.isPresent()) {
            if (!m.getName().equals(this.name.get())) {
                return false;
            }
        }
        if (this.parameterTypes.isPresent()) {
            if (!Arrays.deepEquals(m.getParameterTypes(), parameterTypes.get())) {
                return false;
            }
        }
        if(this.returnType.isPresent()) {
            if(!m.getReturnType().equals(this.returnType.get())) {
                return false;
            }
        }
        return true;
    }
}
