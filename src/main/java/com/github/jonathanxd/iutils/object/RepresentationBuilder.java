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
public final class RepresentationBuilder<T> {
    private Class<? extends T> aClass = null;
    private List<RepresentationBuilder<?>> related = new ArrayList<>();
    //private Object hold = null;
    private boolean isUnique = false;

    RepresentationBuilder() {
    }

    public Class<? extends T> getaClass() {
        return aClass;
    }

    public List<RepresentationBuilder<?>> getRelated() {
        return related;
    }

    public void setUnique(boolean unique) {
        this.isUnique = unique;
    }

    public boolean isUnique() {
        return isUnique;
    }

    @Required
    public RepresentationBuilder<T> a(Class<? extends T> aClass) {
        this.aClass = aClass;
        return this;
    }

    // Of
    @Optional
    public RepresentationBuilder<T> of(List<GenericRepresentation<?>> related) {

        for(GenericRepresentation<?> genericRepresentation : related) {
            this.related.add(from(genericRepresentation));
        }

        return this;
    }

    @Optional
    public <E> RepresentationBuilder<T> ofE(List<GenericRepresentation<E>> related) {

        for(GenericRepresentation<E> genericRepresentation : related) {
            this.related.add(from(genericRepresentation));
        }

        return this;
    }

    @Optional
    public final <E> RepresentationBuilder<T> of(GenericRepresentation<?>... related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @Optional
    public final <E> RepresentationBuilder<T> ofArray(GenericRepresentation<?>[] related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @Optional
    public RepresentationBuilder<T> of(RepresentationBuilder... builders) {

        for (RepresentationBuilder builder : builders) {
            this.related.add(builder);
        }
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> RepresentationBuilder<T> of(Class<? extends E>... classes) {

        List<GenericRepresentation<E>> genericRepresentations = new ArrayList<>();

        for (Class<? extends E> classz : classes) {
            genericRepresentations.add(new RepresentationBuilder<E>().a(classz).build());
        }

        ofE(genericRepresentations);

        return this;
    }

    // AND OF
    @Optional
    public <E> RepresentationBuilder<T> and(List<GenericRepresentation<E>> related) {
        andCheck();
        ofE(related);
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> RepresentationBuilder<T> and(GenericRepresentation<E>... related) {
        andCheck();
        of(related);
        return this;
    }

    @Optional
    public RepresentationBuilder<T> and(RepresentationBuilder... builders) {
        andCheck();
        of(builders);
        return this;
    }

    @Optional
    public RepresentationBuilder<T> and(Class<?>... classes) {
        andCheck();
        of(classes);
        return this;
    }

    private void andCheck() {
        if (related.size() == 0)
            throw new IllegalStateException("'and' cannot be used here! Usage ex: referenceTo().a(Object.class).of(String.class).and(Class.class)");
    }

    public static <T> RepresentationBuilder<T> from(GenericRepresentation<T> genericRepresentation) {
        RepresentationBuilder<T> representationBuilder = new RepresentationBuilder<>();
        representationBuilder.a(genericRepresentation.getAClass());

        for(GenericRepresentation<?> otherGenericRepresentation : genericRepresentation.getRelated()) {
            representationBuilder.related.add(from(otherGenericRepresentation));
        }

        return representationBuilder;
    }

    public static <T> GenericRepresentation<T> to(RepresentationBuilder<T> representationBuilder) {

        DynamicGenericRepresentation<T> dynamicReference = new DynamicGenericRepresentation<>(representationBuilder.aClass, new GenericRepresentation[]{}, representationBuilder.isUnique());

        if(representationBuilder.related.size() > 0) {

            List<GenericRepresentation<?>> genericRepresentationList = new ArrayList<>();

            for(RepresentationBuilder<?> otherRepresentationBuilder : representationBuilder.related) {
                genericRepresentationList.add(to(otherRepresentationBuilder));
            }

            for(GenericRepresentation<?> genericRepresentation : genericRepresentationList) {
                dynamicReference.addRelated(genericRepresentation);
            }
        }

        return dynamicReference.toReference();
    }

    public GenericRepresentation<T> build() {
        return to(this);
    }
}
