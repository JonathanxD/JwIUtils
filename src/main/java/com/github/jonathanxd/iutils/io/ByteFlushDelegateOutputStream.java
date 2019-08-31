/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * An {@link OutputStream} with a {@link #bytes byte buffer}, these bytes are flushed to {@link
 * #flushBytes(byte[])} when {@link OutputStream#flush()} is called.
 */
public abstract class ByteFlushDelegateOutputStream extends OutputStream {
    private static final int EXPAND_SIZE = 8192;
    private static final byte[] EMPTY_BYTES = new byte[0];

    private volatile byte[] bytes = new byte[EXPAND_SIZE];
    private volatile int pointer = 0;

    public ByteFlushDelegateOutputStream() {
        super();
    }

    @Override
    public synchronized void write(@NotNull byte[] b) throws IOException {
        for (byte b1 : b) {
            this.write(b1);
        }
    }

    private synchronized int getAndUpdate() {
        int p = this.pointer;
        this.pointer++;
        return p; // I don't like 'return this.pointer++;'
    }

    private synchronized int getPointer() {
        return this.pointer;
    }

    @Override
    public synchronized void write(@NotNull byte[] b, int off, int len) throws IOException {
        if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }

        for (int i = 0; i < len; i++) {
            this.write(b[off + i]);
        }
    }

    @Override
    public synchronized void flush() throws IOException {
        if (this.getPointer() == 0) {
            this.flushBytes(EMPTY_BYTES);
        } else {
            this.flushBytes(Arrays.copyOfRange(this.bytes, 0, this.getPointer()));
            this.pointer = 0;
            this.bytes = new byte[EXPAND_SIZE];
        }
    }

    @Override
    public void close() {
    }

    @Override
    public synchronized void write(int b) throws IOException {
        this.expandIfNeeded();
        this.writeUnsafe((byte) b);
    }

    private synchronized void expandIfNeeded() {
        if (this.bytes.length < (this.getPointer() + 1))
            this.expandWithExpand();
    }

    private synchronized void expand(int newElements) {
        this.bytes = Arrays.copyOf(this.bytes, this.bytes.length + newElements);
    }

    private synchronized void expandWithExpand() {
        this.expand(EXPAND_SIZE);
    }

    /**
     * Called by implementation to write byte directly to current position pointer and move
     * increment the position pointer. This may throw {@link ArrayIndexOutOfBoundsException} if
     * exceed the buffer current size.
     *
     * You should call {@link #write(int)} if you want a safe write operation.
     *
     * @param b Byte to write.
     */
    private synchronized void writeUnsafe(byte b) {
        this.bytes[this.getAndUpdate()] = b;
    }

    /**
     * Called with copy of buffered bytes when {@link OutputStream#flush()} is called.
     *
     * @param bytes Copy of buffered bytes.
     * @throws IOException If any I/O operation fail.
     */
    protected abstract void flushBytes(byte[] bytes) throws IOException;
}
