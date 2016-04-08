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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.reflection.RClass;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.ParameterizedType;

/**
 * Created by jonathan on 08/04/16.
 */
public abstract class AbstractReference<T> extends Reference<T> {


    @SuppressWarnings("unchecked")
    public AbstractReference() {
        super();

        Reference<T> reference = (Reference<T>) TypeUtil.toReference(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        try {
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "aClass", reference.getAClass());
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "related", reference.getRelated());
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "hold", reference.get());
        } catch (Exception e) {
            throw new Error(e);
        }

    }

}
