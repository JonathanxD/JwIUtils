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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.treeexplore.TreeExplore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by jonathan on 30/04/16.
 */
public class ReferenceTreeExplorer implements TreeExplore<ReferenceBuilder<?>> {
    @Override
    public TreeExplorer<ReferenceBuilder<?>> startExplore(ReferenceBuilder<?> element) {
        return new Explorer(element);
    }

    private static class Explorer implements TreeExplorer<ReferenceBuilder<?>> {

        private Node current = null;

        public Explorer(ReferenceBuilder<?> referenceBuilder) {
            current = new Node(null, referenceBuilder);
        }

        @Override
        public void join(ReferenceBuilder<?> element) {
            Node node = new Node(current, element);
            current.child().add(node);
            current = node;
        }

        @Override
        public ReferenceBuilder<?> current() {
            return Objects.requireNonNull(current, "No elements!").value();
        }

        @Override
        public void exit() {
            current = Objects.requireNonNull(current, "No elements!").parent();
        }
    }

    private static class Node implements TreeNode<ReferenceBuilder<?>, Node> {

        private final Node parent;
        private final ReferenceBuilder<?> value;
        private final List<Node> childs = new ArrayList<>();

        private Node(Node parent, ReferenceBuilder<?> value) {
            this.parent = parent;
            this.value = value;
        }

        @Override
        public Node parent() {
            return parent;
        }

        @Override
        public ReferenceBuilder<?> value() {
            return value;
        }

        @Override
        public List<Node> child() {
            return childs;
        }
    }
}
