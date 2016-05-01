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
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

/**
 * Created by jonathan on 16/02/16.
 */

/**
 * GenericRepresentation to a Existent File
 * If the file is deleted, all methods will throw {@link NonexistentFileException}
 */
public class ExistingFile extends File {

    public ExistingFile(String pathname) {
        super(pathname);
        if(!this.exists())
            throw new NonexistentFileException(this);
    }

    public ExistingFile(String parent, String child) {
        super(parent, child);
        if(!this.exists())
            throw new NonexistentFileException(this);
    }

    public ExistingFile(File parent, String child) {
        super(parent, child);
        if(!this.exists())
            throw new NonexistentFileException(this);
    }

    public ExistingFile(URI uri) {
        super(uri);
        if(!this.exists())
            throw new NonexistentFileException(this);
    }

    @Override
    public File getAbsoluteFile() {
        return super.getAbsoluteFile();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getParent() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getParent();
    }

    @Override
    public File getParentFile() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getParentFile();
    }

    @Override
    public String getPath() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getPath();
    }

    @Override
    public boolean isAbsolute() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.isAbsolute();
    }

    @Override
    public String getAbsolutePath() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getAbsolutePath();
    }

    @Override
    public String getCanonicalPath() throws IOException {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getCanonicalPath();
    }

    @Override
    public File getCanonicalFile() throws IOException {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getCanonicalFile();
    }

    @Override
    public URL toURL() throws MalformedURLException {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.toURL();
    }

    @Override
    public URI toURI() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.toURI();
    }

    @Override
    public boolean canRead() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.canRead();
    }

    @Override
    public boolean canWrite() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.canWrite();
    }

    @Override
    public boolean exists() {
        return super.exists();
    }

    @Override
    public boolean isDirectory() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.isDirectory();
    }

    @Override
    public boolean isFile() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.isFile();
    }

    @Override
    public boolean isHidden() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.isHidden();
    }

    @Override
    public long lastModified() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.lastModified();
    }

    @Override
    public long length() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.length();
    }

    @Override
    public boolean createNewFile() throws IOException {
        return super.createNewFile();
    }

    @Override
    public boolean delete() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.delete();
    }

    @Override
    public void deleteOnExit() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        super.deleteOnExit();
    }

    @Override
    public String[] list() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.list();
    }

    @Override
    public String[] list(FilenameFilter filter) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.list(filter);
    }

    @Override
    public File[] listFiles() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.listFiles();
    }

    @Override
    public File[] listFiles(FilenameFilter filter) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.listFiles(filter);
    }

    @Override
    public File[] listFiles(FileFilter filter) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.listFiles(filter);
    }

    @Override
    public boolean mkdir() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.mkdir();
    }

    @Override
    public boolean mkdirs() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.mkdirs();
    }

    @Override
    public boolean renameTo(File dest) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.renameTo(dest);
    }

    @Override
    public boolean setLastModified(long time) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setLastModified(time);
    }

    @Override
    public boolean setReadOnly() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setReadOnly();
    }

    @Override
    public boolean setWritable(boolean writable, boolean ownerOnly) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setWritable(writable, ownerOnly);
    }

    @Override
    public boolean setWritable(boolean writable) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setWritable(writable);
    }

    @Override
    public boolean setReadable(boolean readable, boolean ownerOnly) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setReadable(readable, ownerOnly);
    }

    @Override
    public boolean setReadable(boolean readable) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setReadable(readable);
    }

    @Override
    public boolean setExecutable(boolean executable, boolean ownerOnly) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setExecutable(executable, ownerOnly);
    }

    @Override
    public boolean setExecutable(boolean executable) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.setExecutable(executable);
    }

    @Override
    public boolean canExecute() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.canExecute();
    }

    @Override
    public long getTotalSpace() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getTotalSpace();
    }

    @Override
    public long getFreeSpace() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getFreeSpace();
    }

    @Override
    public long getUsableSpace() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.getUsableSpace();
    }

    @Override
    public int compareTo(File pathname) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.compareTo(pathname);
    }

    @Override
    public boolean equals(Object obj) {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.hashCode();
    }

    @Override
    public String toString() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.toString();
    }

    @Override
    public Path toPath() {
        if(!this.exists())
            throw new NonexistentFileException(this);
        return super.toPath();
    }
}
