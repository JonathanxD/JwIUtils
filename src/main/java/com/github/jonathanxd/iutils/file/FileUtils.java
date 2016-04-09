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
