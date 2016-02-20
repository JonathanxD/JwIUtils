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
package com.github.jonathanxd.iutils.service;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.map.SimpleNodeOff;
import com.github.jonathanxd.iutils.map.FastMap;

public class ServiceManager {
	
	private static FastMap<Class<? extends Service>, Service> services = new FastMap<>();
	private static final Arrays<Class<? extends Service>> loadWait = new Arrays<>();
	/**
	 * 
	 * @param service Service
	 * @return True if not registered, false if already registered
	 */
	public synchronized static boolean registerService(Service service){
		if(!services.containsKey(service.getClass())){
			if(service.getRelationsClass() != null &&
					service.getRelationsClass().length > 0){
				synchronized (loadWait) {
					loadWait.add(service.getClass());
				}
				int x = 0;
				while(x < service.getRelationsClass().length){
					Class<? extends Service> current = service.getRelationsClass()[x];
					if(current != null){
						System.out.println("--> Checking service: "+current.getName());
						if(getService(current) == null){
							if(!isWaitingForLoad(current)){
								Service s = service.makeRelation(current);
								if(s != null){
									System.out.println("---> Service not found, loading...");
									System.out.println("----> Registering needed service: "+s.getName()+" for service: "+service.getName());							
									registerService(s);
									System.out.println("----< Registered needed service: "+s.getName()+" for service: "+service.getName());																	
								}else{
									throw new RuntimeException(new Exception("Cannot load service from class: "+current.getName()));
								}
							}else{
								System.out.println("---< Load task for: "+current.getName()+" already running.");
							}
						}else{
							System.out.println("--< Service found, continue!");
						}
					}
					++x;
				}
				synchronized (loadWait) {
					loadWait.remove(service.getClass());
				}				
			}
			System.out.println();
			
			services.put(service.getClass(), service);
			return true;
		}
		
		return false;
	}
	
	public static boolean isWaitingForLoad(Class<? extends Service> wait){
		return loadWait.contains(wait);
	}
	
	public static <T extends Service> Service getService(Class<T> serviceClass){
		if(services.containsGenKey(serviceClass)){
			return services.get(serviceClass);					
		}
		
		return null;
	}
	
	public static <T extends Service> Service waitService(Class<T> serviceClass, int max){
		int x = 0;
		while(getService(serviceClass) == null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
			if(x >= max)
				break;
			++x;			
		}
		
		return getService(serviceClass);
	}
	

	public static <T extends Service> Service waitService(Class<T> serviceClass){
		return waitService(serviceClass, 60);
	}
	
	public static Arrays<Service> listServices(){
		Arrays<Service> xsServices = new Arrays<Service>();
		
		for(SimpleNodeOff<Class<? extends Service>, Service> s : services.getNodesOff()){
			xsServices.add(s.getValue());
		}
		
		return xsServices;
	}
	
}
