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
package com.github.jonathanxd.iutils.arguments;

import com.github.jonathanxd.iutils.arguments.exception.ParseArgumentException;
import com.github.jonathanxd.iutils.arguments.exception.UnsatisfiedArgumentError;
import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.iterator.BackableIterator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EasyArguments {
	
	private final HashMap<String, Arrays<String>> arguments = new HashMap<>();
	
	private synchronized EasyArguments registerArgument(String id, Arrays<String> argument){
		arguments.put(id, argument);
		return this;
	}
	
	@SuppressWarnings("unused")
	private synchronized EasyArguments removeArgument(String id){
		arguments.remove(id);
		return this;
	}

	public synchronized boolean argumentExists(String id){
		return arguments.containsKey(id);
	}

	
	public synchronized Arrays<String> getLastestArgument(String id){
		return arguments.get(id);
	}

	public synchronized Map<String, Arrays<String>> getArgMap(){
		return Collections.unmodifiableMap(arguments);
	}
	
	public synchronized int length(){
		return arguments.size();
	}
	
	public static EasyArguments parseArgArray(String[] args, String[] singleArgNames) throws ParseArgumentException {
		
		EasyArguments xArgs = new EasyArguments();
		Arrays<String> arrays = Arrays.ofG(args);
		Arrays<String> single = Arrays.ofG(singleArgNames);
		
		
		BackableIterator<String> iter = arrays.iterator();
		String opt;
		while(iter.hasNext()){
			opt = iter.next();
			if(opt.startsWith("-")){
				if(!single.isEmpty() && single.contains(opt)){
					xArgs.registerArgument(opt.substring(1), single);
					
				}else{
					if(iter.hasNext()){
						
						String key = opt;
						
						Arrays<String> argSend = new Arrays<>();
						
						while(iter.hasNext() && !(opt = iter.next()).startsWith("-")){							
							argSend.add(opt);
						}
						
						if(opt.startsWith("-")){
							iter.back();
						}
						
						xArgs.registerArgument(key.substring(1), argSend);
					}else{
						throw new ParseArgumentException("No provided parameters for argument: ");
					}
				}				
			}
		}
		
		return xArgs;
	}
	
	public synchronized void checkArguments(String[] ids) throws UnsatisfiedArgumentError{
		Arrays<String> unsatisfiedArgList = new Arrays<>();
		for(String id : ids){
			if(!arguments.containsKey(id)){
				unsatisfiedArgList.add(id);
			}
		}
		if(!unsatisfiedArgList.isEmpty()){
			throw new UnsatisfiedArgumentError("Unsatisfied Argument(s): "+unsatisfiedArgList);
		}
	}
	
}
