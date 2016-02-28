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
package com.github.jonathanxd.iutils.extra;

import com.github.jonathanxd.iutils.exceptions.ContainerMakeException;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Container<T> extends MutableContainer<T> implements Mutability {

	private static Container<?> empty = new Container<>();

	private T value;
	private BiFunction<BaseContainer<T>, T, T> applier = null;

	Container() {
		this.value = null;
	}

	public Container(T value) {
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

	public static <T> Container<T> make(Class<? super T> clazz) throws ContainerMakeException {
		T value;
		try{
			value = Reflection.constructEmpty(clazz);
		}catch(Exception e){
			throw new ContainerMakeException("Non public empty constructors found for class: "+clazz+"!");
		}

		return new Container<>(value);
	}

	@Override
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
	public Container<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()){
            return this;
        }else{
            return predicate.test(value) ? this : null;
        }
    }

	@Override
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

        if (!(obj instanceof Container)) {
            return false;
        }

        Container<?> other = (Container<?>) obj;
        return Objects.equals(value, other.getValue());
    }

    // This container type (Container) has a same hash code for same value
    @Override
    public int hashCode() {
    	return Objects.hashCode(this.getValue());
    }

    @Override
    public String toString() {
   		return String.format("Container[%s]", (isPresent() ? this.value.toString() : "null"));
    }

    public static <T> Container<T> of(T value){
    	Objects.requireNonNull(value);
    	return new Container<>(value);
    }

    public static <T> Container<T> of(Class<? super T> clazz){
    	Objects.requireNonNull(clazz);
    	return Container.make(clazz);
    }

    public static <T> Container<T> fromOptional(Optional<T> optional) {
		if (!optional.isPresent()) {
			return empty();
		}

		return new Container<T>(optional.get());
	}

    @SuppressWarnings("unchecked")
	public static <T> T empty(){
    	return (T) Container.empty;
    }

	@Override
	public boolean isMutable() {
		return true;
	}
}
