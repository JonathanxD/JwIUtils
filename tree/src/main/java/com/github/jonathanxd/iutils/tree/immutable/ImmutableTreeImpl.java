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
package com.github.jonathanxd.iutils.tree.immutable;

import com.github.jonathanxd.iutils.tree.Node;
import com.github.jonathanxd.iutils.tree.mutable.MutableNodeImpl;
import com.github.jonathanxd.iutils.tree.mutable.MutableTree;
import com.github.jonathanxd.iutils.tree.mutable.MutableTreeImpl;

public class ImmutableTreeImpl<T> implements ImmutableTree<T> {

    private final ImmutableNode<T> root;
    private final ImmutableNode<T> left;
    private final ImmutableNode<T> right;

    public ImmutableTreeImpl(ImmutableNode<T> root,
                             ImmutableNode<T> left,
                             ImmutableNode<T> right) {
        this.root = root;
        this.left = left;
        this.right = right;
    }

    @Override
    public ImmutableTree<T> withRoot(T value) {
        return new ImmutableTreeImpl<>(
                new ImmutableNodeImpl<>(value, this.root.immutableParent(), this.root.immutableChildren()),
                this.left,
                this.right
        );
    }

    @Override
    public ImmutableTree<T> withRoot(Node<T> value) {
        return new ImmutableTreeImpl<>(
                value.toImmutable(),
                this.left,
                this.right
        );
    }

    @Override
    public ImmutableTree<T> toImmutable() {
        return this;
    }

    @Override
    public Node<T> root() {
        return this.root;
    }

    @Override
    public MutableTree<T> toMutable() {
        return new MutableTreeImpl<>(

        );
    }
}
