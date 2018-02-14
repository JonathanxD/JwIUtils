/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public class DelegatePrintStream extends PrintStream {
    public DelegatePrintStream(Consumer<String> printlnConsumer) {
        super(new MyOS(null, printlnConsumer), true);
    }

    public DelegatePrintStream(@NotNull String encoding, Consumer<String> printlnConsumer) throws UnsupportedEncodingException {
        super(new MyOS(encoding, printlnConsumer), false, encoding);
    }

    static class MyOS extends ByteFlushDelegateOutputStream {
        private final String encoding;
        private final Consumer<String> printlnConsumer;

        MyOS(String encoding, Consumer<String> printlnConsumer) {
            this.encoding = encoding;
            this.printlnConsumer = printlnConsumer;
        }

        @Override
        public synchronized void write(int b) throws IOException {
            if (b == '\n')
                this.flush();
            else
                super.write(b);
        }

        @Override
        protected void flushBytes(byte[] bytes) throws IOException {
            String s = encoding == null ? new String(bytes) : new String(bytes, Charset.forName(encoding));
            printlnConsumer.accept(s);
        }
    }
}
