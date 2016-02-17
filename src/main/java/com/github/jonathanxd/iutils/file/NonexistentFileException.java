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
package com.github.jonathanxd.iutils.file;

import com.github.jonathanxd.iutils.object.ObjectUtils;

import java.io.File;

import static com.github.jonathanxd.iutils.object.ObjectUtils.deepFields;

/**
 * Created by jonathan on 16/02/16.
 */
public class NonexistentFileException extends RuntimeException {

    public NonexistentFileException(File file) {
        super(to(file));
    }

    public NonexistentFileException(File file, String message) {
        super(to(file, message));
    }

    public NonexistentFileException(File file, String message, Throwable cause) {
        super(to(file, message), cause);
    }

    public NonexistentFileException(File file, Throwable cause) {
        super(to(file), cause);
    }

    public NonexistentFileException(File file, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(to(file, message), cause, enableSuppression, writableStackTrace);
    }

    private static String to(File file) {
        return ObjectUtils.deepFields(file).toString();
    }

    private static String to(File file, String message) {
        return message + "(" + ObjectUtils.deepFields(file).toString() + ")";
    }
}
