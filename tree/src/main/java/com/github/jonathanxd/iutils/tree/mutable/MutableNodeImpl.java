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

import com.github.jonathanxd.iutils.collection.wrapper.WrapperCollections;
import com.github.jonathanxd.iutils.tree.Node;
import com.github.jonathanxd.iutils.tree.immutable.ImmutableNode;
import com.github.jonathanxd.iutils.tree.immutable.ImmutableNodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MutableNodeImpl<T> implements MutableNode<T> {

    private MutableNode<T> parent;
    private T value;
    private final List<MutableNode<T>> children = new ArrayList<>();

    public MutableNodeImpl(T value, MutableNode<T> parent) {
        this.parent = parent == null ? this : parent;
        this.value = value;
    }

    public MutableNodeImpl(T value, MutableNode<T> parent, List<MutableNode<T>> children) {
        this.parent = parent == null ? this : parent;
        this.value = value;
        this.children.addAll(children);
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public MutableNode<T> setParent(Node<T> parent) {
        this.parent = parent.toMutable();
        return this;
    }

    @Override
    public MutableNode<T> addChildren(T value) {
        MutableNode<T> node = new MutableNodeImpl<>(value, this);
        this.children.add(node);
        return node;
    }

    @Override
    public MutableNode<T> addChildren(Node<T> node) {
        MutableNode<T> newNode = new MutableNodeImpl<>(node.value(), this);

        for (Node<T> child : node.children()) {
            newNode.addChildren(child);
        }

        return newNode;
    }

    @Override
    public List<? extends MutableNode<T>> addChildrenValues(List<? extends T> value) {
        return value.stream().map(this::addChildren).collect(Collectors.toList());
    }

    @Override
    public List<? extends MutableNode<T>> addChildrenNodes(List<? extends Node<T>> nodes) {
        return nodes.stream().map(this::addChildren).collect(Collectors.toList());
    }

    @Override
    public List<MutableNode<T>> mutableChildren() {
        return this.children;
    }

    @Override
    public MutableNode<T> mutableParent() {
        return this.parent;
    }

    @Override
    public T value() {
        return this.value;
    }

    @Override
    public Node<T> parent() {
        return this.parent;
    }

    @Override
    public List<? extends Node<T>> children() {
        return this.children;
    }

    @Override
    public MutableNode<T> toMutable() {
        return this;
    }

    @Override
    public ImmutableNode<T> toImmutable() {
        ImmutableNode<T> parent = this.parent == this ? null : this.parent.toImmutable();
        List<ImmutableNode<T>> collect = this.children.stream().map(Node::toImmutable).collect(Collectors.toList());
        return new ImmutableNodeImpl<>(this.value, parent, WrapperCollections.immutableList(collect));
    }
}
