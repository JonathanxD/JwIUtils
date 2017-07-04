/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.web;

import com.github.jonathanxd.iutils.object.Lazy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WebUtils {

    /**
     * Creates a lazy instance which lazily connects to a web-page and return response body.
     *
     * @param urlString Url of web-page
     * @return lazy instance which lazily connects to a web-page and return response body.
     * @throws MalformedURLException See {@link URL#URL(String)}
     */
    public static Lazy<List<String>> getWebLazy(String urlString) throws MalformedURLException {
        return Lazy.lazy(WebUtils.getWeb(urlString));
    }

    /**
     * Creates a completable future which connects to a web-page and return response body.
     *
     * @param urlString Url of web-page
     * @return completable future which connects to a web-page and return response body.
     * @throws MalformedURLException See {@link URL#URL(String)}
     */
    public static CompletableFuture<List<String>> getWeb(String urlString) throws MalformedURLException {

        URL url = new URL(urlString);

        return CompletableFuture
                .supplyAsync(() -> {
                    try {
                        URLConnection urlConnection = url.openConnection();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        List<String> lines = new ArrayList<>();

                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            lines.add(line);
                        }

                        return lines;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).exceptionally(throwable -> Collections.emptyList());
    }

}
