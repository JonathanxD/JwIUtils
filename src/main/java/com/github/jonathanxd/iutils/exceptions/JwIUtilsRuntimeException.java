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
package com.github.jonathanxd.iutils.exceptions;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.reflection.Reflection;

public class JwIUtilsRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3820066586895196852L;

	public JwIUtilsRuntimeException(Class<?> involved, String exceptionMessage) {
		this(involved, exceptionMessage, 0);
	}
	
	public JwIUtilsRuntimeException(Class<?> involved, String exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
		Arrays<StackTraceElement> arr = Arrays.ofG(super.getStackTrace());
		arr.add(new StackTraceElement(involved.getName(), "<? unknown method>", involved.getSimpleName(), 0));
		super.setStackTrace(arr.toGenericArray());
		
	}
	
	public JwIUtilsRuntimeException(Class<?> involved, String exceptionMessage, int offset) {
		super(exceptionMessage);
		
		StackTraceElement ste = Reflection.getCallInformations(involved);
		Arrays<StackTraceElement> arr = Arrays.ofG(super.getStackTrace());
		arr.add(new StackTraceElement(ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber()+offset));
		super.setStackTrace(arr.toGenericArray());
	}
	
}
