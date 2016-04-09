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
package com.github.jonathanxd.iutils.extra;

import com.github.jonathanxd.iutils.exceptions.ContainerMakeException;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.github.jonathanxd.iutils.reflection.Reflection;

public class ImmutableContainer<T> implements BaseContainer<T>{
	
	private static ImmutableContainer<?> empty = new ImmutableContainer<>();
	
	private T value;
	private BiFunction<BaseContainer<T>, T, T> applier = null;
	
	public ImmutableContainer() {
		this.value = null;
	}

	public ImmutableContainer(T value) {
		this.value = value;
	}
	
	@Override
	public void setApplier(BiFunction<BaseContainer<T>, T, T> applier) {
		this.applier = applier;
	}
	
	@Override
	public void apply(T value){		
		this.value = this.applier.apply(this, value);
	}
	
	public static <T> ImmutableContainer<T> make(Class<? super T> clazz) throws ContainerMakeException {
		T value;
		try{
			value = Reflection.constructEmpty(clazz);
		}catch(Exception e){
			throw new ContainerMakeException("Non public empty constructors found for class: "+clazz+"!");
		}
		
		return new ImmutableContainer<>(value);
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public boolean isPresent() {
		return getValue() != null;
	}
	
	@Override
	public T getOrElse(T another){
		Objects.requireNonNull(another);
		return (isPresent() ? this.getValue() : another);
	}

	@Override
	public T getOr(BaseContainer<T> another){
		return (isPresent() ? this.getValue() : another.getValue());
	}
	
	@Override
	public BaseContainer<T> getOrContainer(BaseContainer<T> another){
		return (isPresent() ? this : another);
	}
	
	@Override
	public void ifPresent(Consumer<? super T> consumer){
		Objects.requireNonNull(consumer);
		if(isPresent()){
			consumer.accept(getValue());
		}
	}
	
	@Override
	public ImmutableContainer<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()){
            return this;
        }else{
            return predicate.test(value) ? this : null;
        }
    }

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ImmutableContainer)) {
            return false;
        }

        ImmutableContainer<?> other = (ImmutableContainer<?>) obj;
        return Objects.equals(value, other.getValue());
    }
    
    // This container type (MutableContainer) has a different hash codes for same value
    @Override
    public int hashCode() {
    	return super.hashCode();
    }
    
    @Override
    public String toString() {
   		return String.format("Container[%s]", (isPresent() ? this.value.toString() : "null"));
    }
    
    public static <T> ImmutableContainer<T> of(T value){
    	Objects.requireNonNull(value);
    	return new ImmutableContainer<>(value);
    }

    public static <T> ImmutableContainer<T> of(Class<? super T> clazz){
    	Objects.requireNonNull(clazz);
    	return ImmutableContainer.make(clazz);
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T empty(){
    	return (T) ImmutableContainer.empty;
    }
}
