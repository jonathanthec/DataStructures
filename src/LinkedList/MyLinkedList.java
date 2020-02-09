package LinkedList;

/**
 * @author Jonathan Chen
 *
 * Created in February, 2020
 * Created to mimic the function of a linkedlist datastructure
 *
 * A linked list is a list of nodes, each consisting a data, and two nodes pointing toward the nodes
 * before and after the node
 *
 * An iterator tells the linked list implementor current location to perform actions to linked list
 **/

import java.util.*;

public class MyLinkedList<E> extends AbstractList<E> {

    private int nelems;
    private Node head;
    private Node tail;

    protected class Node {
        E data;
        Node next;
        Node prev;

        /**
         * Constructor for a singleton node with only data
         * @param element
         */
        public Node(E element) {
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Constructor for a node with data, previous and next node
         * @param element
         * @param prevNode
         * @param nextNode
         */
        public Node(E element, Node prevNode, Node nextNode) {
            this.data = element;
            this.prev = prevNode;
            this.next = nextNode;
        }

        /**
         * Setter for data
         * @param e is new data to be set into node
         */
        public void setElement(E e) {
            this.data = e;
        }

        /**
         * Setter for previous node in the list
         * @param p is previous node
         */
        public void setPrev(Node p) {
            this.prev = p;
        }

        /**
         * Setter for the next node in the list
         * @param n is next node
         */
        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * Getter for data
         * @return data of the node
         */
        public E getElement() {
            return this.data;
        }

        /**
         * Getter for prev node
         * @return previous node of the current node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Getter for next node
         * @return next node of the current node
         */
        public Node getNext() {
            return this.next;
        }

        public void remove() {
            Node tempPrev = this.getPrev();
            Node tempNext = this.getNext();
            tempPrev.setNext(tempNext);
            tempNext.setPrev(tempPrev);
        }
    }

    private boolean addremoved = false;

    protected class MyIterator implements ListIterator<E> {

    }
}
