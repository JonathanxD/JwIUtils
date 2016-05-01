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
package com.github.jonathanxd.iutils.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jonathanxd.iutils.optional.Optional;
import com.github.jonathanxd.iutils.optional.Required;

/**
 * Created by jonathan on 13/02/16.
 */
public final class ReferenceBuilder<T> {
    private Class<? extends T> aClass = null;
    private List<ReferenceBuilder<?>> related = new ArrayList<>();
    private Object hold = null;

    ReferenceBuilder() {
    }

    public Class<? extends T> getaClass() {
        return aClass;
    }

    public List<ReferenceBuilder<?>> getRelated() {
        return related;
    }

    public Object getHold() {
        return hold;
    }

    @Required
    public ReferenceBuilder<T> a(Class<? extends T> aClass) {
        this.aClass = aClass;
        return this;
    }

    @Optional
    public ReferenceBuilder<T> hold(Object object) {
        this.hold = object;
        return this;
    }

    // Of
    @Optional
    public ReferenceBuilder<T> of(List<GenericRepresentation<?>> related) {

        for(GenericRepresentation<?> genericRepresentation : related) {
            this.related.add(from(genericRepresentation));
        }

        return this;
    }

    @Optional
    public <E> ReferenceBuilder<T> ofE(List<GenericRepresentation<E>> related) {

        for(GenericRepresentation<E> genericRepresentation : related) {
            this.related.add(from(genericRepresentation));
        }

        return this;
    }

    @Optional
    public final <E> ReferenceBuilder<T> of(GenericRepresentation<?>... related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @Optional
    public final <E> ReferenceBuilder<T> ofArray(GenericRepresentation<?>[] related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @Optional
    public ReferenceBuilder<T> of(ReferenceBuilder... builders) {

        for (ReferenceBuilder builder : builders) {
            this.related.add(builder);
        }
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> of(Class<? extends E>... classes) {

        List<GenericRepresentation<E>> genericRepresentations = new ArrayList<>();

        for (Class<? extends E> classz : classes) {
            genericRepresentations.add(new ReferenceBuilder<E>().a(classz).build());
        }

        ofE(genericRepresentations);

        return this;
    }

    // AND OF
    @Optional
    public <E> ReferenceBuilder<T> and(List<GenericRepresentation<E>> related) {
        andCheck();
        ofE(related);
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> and(GenericRepresentation<E>... related) {
        andCheck();
        of(related);
        return this;
    }

    @Optional
    public ReferenceBuilder<T> and(ReferenceBuilder... builders) {
        andCheck();
        of(builders);
        return this;
    }

    @Optional
    public ReferenceBuilder<T> and(Class<?>... classes) {
        andCheck();
        of(classes);
        return this;
    }

    private void andCheck() {
        if (related.size() == 0)
            throw new IllegalStateException("'and' cannot be used here! Usage ex: referenceTo().a(Object.class).of(String.class).and(Class.class)");
    }

    public static <T> ReferenceBuilder<T> from(GenericRepresentation<T> genericRepresentation) {
        ReferenceBuilder<T> referenceBuilder = new ReferenceBuilder<>();
        referenceBuilder.a(genericRepresentation.getAClass());

        for(GenericRepresentation<?> otherGenericRepresentation : genericRepresentation.getRelated()) {
            referenceBuilder.related.add(from(otherGenericRepresentation));
        }

        return referenceBuilder;
    }

    public static <T> GenericRepresentation<T> to(ReferenceBuilder<T> referenceBuilder) {

        DynamicGenericRepresentation<T> dynamicReference = new DynamicGenericRepresentation<>(referenceBuilder.aClass, new GenericRepresentation[]{}, referenceBuilder.hold);

        if(referenceBuilder.related.size() > 0) {

            List<GenericRepresentation<?>> genericRepresentationList = new ArrayList<>();

            for(ReferenceBuilder<?> otherReferenceBuilder : referenceBuilder.related) {
                genericRepresentationList.add(to(otherReferenceBuilder));
            }

            for(GenericRepresentation<?> genericRepresentation : genericRepresentationList) {
                dynamicReference.addRelated(genericRepresentation);
            }
        }

        return dynamicReference.toReference();
    }

    public GenericRepresentation<T> build() {
        List<GenericRepresentation<?>> waitingGenericRepresentations = new ArrayList<>();



        //return new GenericRepresentation<>(aClass, related.toArray(new GenericRepresentation[related.size()]), hold);
        return to(this);
    }
}
