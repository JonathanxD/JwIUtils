/*
 *      JwIUtils-Tree - Tree data structure JwIUtils module <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.tree.mutable;

import com.github.jonathanxd.iutils.collection.immutable.ImmutableList;
import com.github.jonathanxd.iutils.collection.wrapper.impl.ImmutableWrapperList;
import com.github.jonathanxd.iutils.tree.Node;
import com.github.jonathanxd.iutils.tree.immutable.ImmutableNode;
import com.github.jonathanxd.iutils.tree.immutable.ImmutableNodeImpl;
import com.github.jonathanxd.iutils.tree.immutable.ImmutableTree;
import com.github.jonathanxd.iutils.tree.immutable.ImmutableTreeImpl;

import java.util.ArrayList;

public class MutableTreeImpl<T> implements MutableTree<T> {

    private MutableNode<T> root = new MutableNodeImpl<>(null, null);
    private MutableNode<T> left = new MutableNodeImpl<>(null, null);
    private MutableNode<T> right = new MutableNodeImpl<>(null, null);

    @Override
    public MutableNode<T> setRoot(T value) {
        MutableNode<T> newNode = new MutableNodeImpl<T>(value, this.root.mutableParent(), this.root.mutableChildren());
        this.root = newNode;
        return newNode;
    }

    @Override
    public MutableNode<T> setRoot(Node<T> node) {
        MutableNode<T> oldRoot = this.root;
        this.root = this.root.toMutable();
        return oldRoot;
    }

    @Override
    public Node<T> root() {
        return this.root;
    }

    @Override
    public MutableTree<T> toMutable() {
        return this;
    }

    @Override
    public ImmutableTree<T> toImmutable() {
        if (this.root == null) {
            return null;
        } else {
            return new ImmutableTreeImpl<>(this.root.toImmutable(), this.left.toImmutable(), this.right.toImmutable());
        }
    }
}
