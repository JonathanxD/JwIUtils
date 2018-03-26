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
package com.github.jonathanxd.iutils.property.test;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.opt.OptObject;
import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.PropertyFactory;
import com.github.jonathanxd.iutils.property.ValueHelper;
import com.github.jonathanxd.iutils.property.value.Value;
import com.github.jonathanxd.iutils.property.value.ValueFactory;
import com.github.jonathanxd.iutils.property.value.Values;
import com.github.jonathanxd.iutils.type.TypeInfo;

import org.junit.Assert;
import org.junit.Test;

public class PropTest {
    private final Property<System> SYSTEM_PROPERTY =
            PropertyFactory.createProperty(TypeInfo.of(System.class), "system", OptObject.none());
    private final Property<Integer> USERS_PROPERTY =
            PropertyFactory.createProperty(TypeInfo.of(Integer.class), "users", OptObject.some(0));

    @Test
    public void test() {

        Value<System> systemValue = ValueFactory.createConstantValue(SYSTEM_PROPERTY, System.A);
        Value<Integer> usersValue = ValueFactory.createReachValue(USERS_PROPERTY, 100);

        Values reachValues = Values.createValues(Collections3.listOf(systemValue, usersValue));
        Values currentValues = Values.createCurrentValues(reachValues);

        Assert.assertEquals(System.A, reachValues.getConstantValue(SYSTEM_PROPERTY).get().getValue());
        Assert.assertFalse(reachValues.getConstantValue(USERS_PROPERTY).isPresent()); // Not a constant, it is a ReachValue
        Assert.assertEquals(100, (int) reachValues.getReachValue(USERS_PROPERTY).get().getValue());
        Assert.assertEquals(0, (int) currentValues.getCurrentValue(USERS_PROPERTY).get().getValue());

        currentValues.values()
                .matchConstant(SYSTEM_PROPERTY, System.A)
                .getCurrentValue(USERS_PROPERTY)
                .ifPresent(ValueHelper.apply(ValueHelper.incrementIntOp()));

        Assert.assertEquals(1, (int) currentValues.getCurrentValue(USERS_PROPERTY).get().getValue());

        currentValues.values()
                .matchConstant(SYSTEM_PROPERTY, System.A)
                .getCurrentValue(USERS_PROPERTY)
                .ifPresent(ValueHelper.apply(ValueHelper.incrementIntOp(3)));

        Assert.assertEquals(4, (int) currentValues.getCurrentValue(USERS_PROPERTY).get().getValue());

        Assert.assertFalse(currentValues.isComplete(reachValues));

        currentValues.values()
                .matchConstant(SYSTEM_PROPERTY, System.A)
                .getCurrentValue(USERS_PROPERTY)
                .ifPresent(ValueHelper.apply(ValueHelper.incrementIntOp(99)));

        Assert.assertTrue(currentValues.isComplete(reachValues));
    }

    enum System {
        A,
        B
    }
}
