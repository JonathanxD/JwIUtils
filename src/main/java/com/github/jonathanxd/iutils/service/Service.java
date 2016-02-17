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
package com.github.jonathanxd.iutils.service;

import java.util.UUID;

public interface Service {
	
	public default String getName(){return null;};
	public default UUID getUID(){return null;};
	public default Service makeRelation(Class<? extends Service> relationClass){return null;};
	public default Class<? extends Service>[] getRelationsClass(){return null;};
	
	@SuppressWarnings("unchecked")
	public static <T extends Service> T cast(Service service){
		return (T) service;
	}

}
