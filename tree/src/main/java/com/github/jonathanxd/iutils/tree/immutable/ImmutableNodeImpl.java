/*
 *      JwIUtils-Tree - Tree data structure JwIUtils module <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.tree.immutable;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.collection.immutable.ImmutableList;
import com.github.jonathanxd.iutils.collection.wrapper.WrapperCollections;
import com.github.jonathanxd.iutils.tree.Node;
import com.github.jonathanxd.iutils.tree.mutable.MutableNode;
import com.github.jonathanxd.iutils.tree.mutable.MutableNodeImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ImmutableNodeImpl<T> implements ImmutableNode<T> {
    private final T value;
    private final ImmutableNode<T> parent;
    private final ImmutableList<ImmutableNode<T>> childs;

    public ImmutableNodeImpl(T value, ImmutableNode<T> parent, ImmutableList<ImmutableNode<T>> childs) {
        this.value = value;
        this.parent = parent == null ? this : parent;
        this.childs = childs;
    }

    @Override
    public ImmutableNode<T> withValue(T value) {
        return new ImmutableNodeImpl<>(value, this.parent, this.childs);
    }

    @Override
    public ImmutableNode<T> withParent(Node<T> parent) {
        return new ImmutableNodeImpl<>(this.value, parent.toImmutable(), this.childs);
    }

    @Override
    public ImmutableNode<T> withChildren(T value) {
        ImmutableNode<T> immutableNode = ImmutableNodeImpl.valueToNode(value, this);

        List<ImmutableNode<T>> newChilds = Collections3.append(immutableNode, this.childs);

        return new ImmutableNodeImpl<>(this.value, this.parent, WrapperCollections.immutableList(newChilds));
    }

    @Override
    public ImmutableNode<T> withChildren(Node<T> node) {
        List<ImmutableNode<T>> newChilds = Collections3.append(node.toImmutable(), this.childs);

        return new ImmutableNodeImpl<>(this.value, this.parent, WrapperCollections.immutableList(newChilds));
    }

    @Override
    public ImmutableNode<T> withChildrenValues(List<? extends T> value) {
        return this.withChildrenNodes(value.stream().map(o -> ImmutableNodeImpl.valueToNode(o, this)).collect(Collectors.toList()));
    }

    @Override
    public ImmutableNode<T> withChildrenNodes(List<? extends Node<T>> nodes) {
        List<ImmutableNode<T>> immutableNodes = nodes.stream().map(Node::toImmutable).collect(Collectors.toList());
        List<ImmutableNode<T>> newChilds = Collections3.append(immutableNodes, this.childs);

        return new ImmutableNodeImpl<>(this.value, this.parent, WrapperCollections.immutableList(newChilds));
    }

    @Override
    public ImmutableList<ImmutableNode<T>> immutableChildren() {
        return this.childs;
    }

    @Override
    public ImmutableNode<T> immutableParent() {
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
        return this.childs;
    }

    @Override
    public MutableNode<T> toMutable() {
        MutableNode<T> parent = this.parent == this ? null : this.parent.toMutable();
        MutableNodeImpl<T> mutableNode = new MutableNodeImpl<>(this.value, parent);
        mutableNode.addChildrenNodes(this.childs);
        return mutableNode;
    }

    @Override
    public ImmutableNode<T> toImmutable() {
        return this;
    }

    public static <T> ImmutableNode<T> valueToNode(T value, ImmutableNode<T> parent) {
        return new ImmutableNodeImpl<>(value, parent, WrapperCollections.emptyImmutableList());
    }
}
