/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.recursion.RecursionUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RecursiveTest {

    @Test
    public void rec() {
        List<Node> nodeList = new ArrayList<>();

        nodeList.add(new Node(7, new Node(8, new Node(17))));

        nodeList.add(new Node("X"));

        nodeList.add(new Node("B", new Node("Z", new Node("="))));

        nodeList.add(new Node(new Node(89), new Node(74, new Node(35))));


        RecursionUtil.recursiveForEach(nodeList, node -> {

            System.out.println("Visit node: "+node.toString());

            List<Node> nodes = new ArrayList<>();

            if(node.getValue() instanceof Node) {
                nodes.add((Node) node.getValue());
            }

            if(node.getNext() != null) {
                nodes.add(node.getNext());
            }

            System.out.println("Exit node: "+node.toString());

            return nodes.isEmpty() ? null : nodes;
        });
    }

    class Node {
        final Object value;
        Node next;

        Node(Object value) {
            this(value, null);
        }

        Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Object getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node["+getValue()+"]";
        }
    }

}
