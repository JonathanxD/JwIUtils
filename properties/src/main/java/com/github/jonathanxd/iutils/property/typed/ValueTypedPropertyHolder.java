/*
 *      JwIUtils-Properties - Properties module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.property.typed;

import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.PropertyHolder;
import com.github.jonathanxd.iutils.property.internal.PropCollUtil;
import com.github.jonathanxd.iutils.property.value.ValueType;
import com.github.jonathanxd.iutils.property.value.ValueTypeHolder;

import java.util.List;

public interface ValueTypedPropertyHolder extends PropertyHolder, ValueTypeHolder {

    List<ValueTypedProperty<?>> getPropertiesWithValueTypes();

    @Override
    default List<Property<?>> getProperties() {
        return PropCollUtil.getProperties(this);
    }

    @Override
    default List<ValueType> getPropertyValueTypes() {
        return PropCollUtil.getValueTypes(this);
    }
}
