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
    public <E> ReferenceBuilder<T> of(List<Reference<E>> related) {

        for(Reference<E> reference : related) {
            this.related.add(from(reference));
        }

        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> of(Reference<E>... related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @Optional
    public final <E> ReferenceBuilder<T> ofArray(Reference<E>[] related) {
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

        List<Reference<E>> references = new ArrayList<>();

        for (Class<? extends E> classz : classes) {
            references.add(new ReferenceBuilder<E>().a(classz).build());
        }

        of(references);

        return this;
    }

    // AND OF
    @Optional
    public <E> ReferenceBuilder<T> and(List<Reference<E>> related) {
        andCheck();
        of(related);
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> and(Reference<E>... related) {
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

    public static <T> ReferenceBuilder<T> from(Reference<T> reference) {
        ReferenceBuilder<T> referenceBuilder = new ReferenceBuilder<>();
        referenceBuilder.a(reference.getAClass());

        for(Reference<?> otherReference : reference.getRelated()) {
            referenceBuilder.related.add(from(otherReference));
        }

        return referenceBuilder;
    }

    public static <T> Reference<T> to(ReferenceBuilder<T> referenceBuilder) {

        DynamicReference<T> dynamicReference = new DynamicReference<>(referenceBuilder.aClass, new Reference[]{}, referenceBuilder.hold);

        if(referenceBuilder.related.size() > 0) {

            List<Reference<?>> referenceList = new ArrayList<>();

            for(ReferenceBuilder<?> otherReferenceBuilder : referenceBuilder.related) {
                referenceList.add(to(otherReferenceBuilder));
            }

            for(Reference<?> reference : referenceList) {
                dynamicReference.addRelated(reference);
            }
        }

        return dynamicReference.toReference();
    }

    public Reference<T> build() {
        List<Reference<?>> waitingReferences = new ArrayList<>();



        //return new Reference<>(aClass, related.toArray(new Reference[related.size()]), hold);
        return to(this);
    }
}
