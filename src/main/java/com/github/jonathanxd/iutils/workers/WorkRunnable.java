/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.workers;

import com.github.jonathanxd.iutils.arguments.Arguments;

public abstract class WorkRunnable implements FunctionalWorker{
	
	public enum State {
		WAITING,
		RUNNING,
		RUNNED
	}

	private WorkRunnable.State state = State.WAITING;
	private final Arguments localArguments;
	
	public WorkRunnable(Arguments localArguments) {
		this.localArguments = localArguments;
	}
	
	public WorkRunnable() {
		this.localArguments = new Arguments();
	}
	
	public final Arguments getLocalArguments() {
		return localArguments;
	}
	
	public final WorkRunnable.State currentState(){
		return this.state;
	}
	
	public final void setState(WorkRunnable.State state){
		this.state = state;
	}
}
