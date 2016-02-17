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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by jonathan on 14/02/16.
 */
public class FileUtils {

    public static File setName(File file, String name) {

        String ext = file.getName().substring(file.getName().lastIndexOf('.'));
        File abPath = file.getAbsoluteFile();

        String nName;
        if (abPath.getParentFile() == null) {
            nName = name + ext;
        } else {
            nName = abPath.getParentFile().getAbsolutePath() + File.separator + name + ext;
        }

        return new File(nName);
    }

    public static void moveTo(File file, File newGo) throws IOException {

        if (newGo.exists()) {
            if (newGo.getParentFile() != null && !newGo.getParentFile().exists())
                newGo.getParentFile().mkdirs();
        }

        Files.move(file.toPath(), newGo.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }


    public static File addDir(File file, String dir, PathNamePosition position) throws IOException {
        File addDir = null;
        if (position == PathNamePosition.AHEAD) {
            addDir = addDirA(file, dir);
        } else if (position == PathNamePosition.BEHIND) {
            addDir = addDirB(file, dir);
        }

        if (addDir.getParentFile() != null && !addDir.getParentFile().exists()) {
            addDir.getParentFile().mkdirs();
        }

        return addDir;
    }

    public static File addDirA(File file, String dir) throws IOException {

        String name = file.getName();
        if (file.getParentFile() == null) {
            return new File(dir, name);
        } else {

            File parent = new File(file.getParentFile().getAbsolutePath() + File.separator + dir);

            if (!parent.exists()) {
                parent.mkdirs();
            }

            return new File(parent, name);
        }

    }

    public static File addDirB(File file, String bdir) throws IOException {
        File current = new File(".").getCanonicalFile();

        String relative = current.toPath().relativize(file.getCanonicalFile().toPath()).toString();

        return new File(bdir + File.separator + relative);
    }

    @Deprecated
    public static ExistingFile getExistingFile(File file) {
        return new ExistingFile(file.toURI());
    }

    @Deprecated
    public static ExistingFile getExistingFile(String file) {
        return new ExistingFile(file);
    }

}
