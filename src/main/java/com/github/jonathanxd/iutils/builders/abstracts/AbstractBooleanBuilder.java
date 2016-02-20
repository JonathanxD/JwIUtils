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
package com.github.jonathanxd.iutils.builders.abstracts;

import java.util.Arrays;

import com.github.jonathanxd.iutils.interfaces.CapacityImplement;
import com.github.jonathanxd.iutils.sequence.BooleanSequence;

public abstract class AbstractBooleanBuilder implements BooleanSequence, CapacityImplement {
	/**
	 * Boolean storage
	 */
	Boolean[] value;
	
	/**
	 * The length
	 */
	int count;
	
	/**
	 * Default constructor
	 */
	public AbstractBooleanBuilder() {		
	}
	
	/**
	 * Constructor with specific capacity.
	 */
	public AbstractBooleanBuilder(int capacity) {
		this.value = new Boolean[capacity];
	}
	
	/**
	 * Returns the length
	 */
	@Override
	public int length() {
		return count;
	}
	
	/**
	 * 
	 */
	@Override
	public int capacity() {		
		return value.length;
	}
	
	@Override
    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0)
            ensureCapacityInternal(minimumCapacity);
    }

    private void ensureCapacityInternal(int minimumCapacity) {
        if (minimumCapacity - value.length > 0)
            expandCapacity(minimumCapacity);
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            if (minimumCapacity < 0)
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
    }

    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    public void setLength(int newLength) {
        if (newLength < 0)
            throw new IndexOutOfBoundsException("Boolean index out of range: " + newLength);
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            Arrays.fill(value, count, newLength, '\0');
        }

        count = newLength;
    }
    
    @Override
    public Boolean boolAt(int index) {
        if ((index < 0) || (index >= count))
        	throw new IndexOutOfBoundsException("Boolean index out of range: " + index);
        return value[index];
    }
    
    public void getBools(int srcBegin, int srcEnd, Boolean[] dst, int dstBegin)
    {
        if (srcBegin < 0)
        	throw new IndexOutOfBoundsException("Boolean index out of range: " + srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
        	throw new IndexOutOfBoundsException("Boolean index out of range: " + srcEnd);
        if (srcBegin > srcEnd)
        	throw new IndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    
    public void setBoolAt(int index, Boolean bool) {
        if ((index < 0) || (index >= count))
        	throw new IndexOutOfBoundsException("Boolean index out of range: " + index);
        value[index] = bool;
    }
    
    public AbstractBooleanBuilder append(Object obj) {
        return append(Boolean.valueOf(String.valueOf(obj)));
    }

    public AbstractBooleanBuilder append(Boolean[] bool) {
    	int len = bool.length;
    	ensureCapacityInternal(count + len);
    	System.arraycopy(bool, 0, value, count, len - 0);
    	count += len;
    	return this;
    }
    
    public AbstractBooleanBuilder append(Boolean bool) {
        ensureCapacityInternal(count + 1);
        value[count] = bool;
        count += 1;
        return this;
    }
    
    AbstractBooleanBuilder append(AbstractBooleanBuilder asb) {
        if (asb == null)
            return this;
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getBools(0, len, value, count);
        count += len;
        return this;
    }
    
    public Boolean[] subboolean(int start){
    	return subboolean(start, count);
    }

	public Boolean[] subboolean(int start, int end) {
		Boolean[] b = new Boolean[end];
		getBools(start, end, b, 0);
		return b;
	}
	
	@Override
	public BooleanSequence subSequence(int start, int end) {
		return new AbstractBooleanBuilder(end){}.append(subboolean(start, end));
	}
}
