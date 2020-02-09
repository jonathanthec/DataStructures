package LinkedList;

/**
 * @author Jonathan Chen
 *
 * Created in February, 2020
 * Created to mimic the functionality of a linkedlist datastructure
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

    /**
     * This class defines the Node class
     */
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

    // Is last move an add or remove operation?
    private boolean addremoved = false;

    /**
     * This class defines MyListIterator class
     */
    protected class MyListIterator implements ListIterator<E> {
        private boolean forward = false;
        private boolean canRemove = false;
        private Node left, right; // Cursor sits between these two nodes
        private int idx = 0; // Tracks current position, what next() should return

        public MyListIterator() {
            left = head;
            right = head.getNext();
        }

        /** Checks if there are elements in the forward direction */
        @Override
        public boolean hasNext() {
            // An empty list has no next
            if (nelems == 0) return false;
            // Checks in case right is null and is not at end of list
            else if (right.getElement() == null && idx < nelems) return true;
            else if (right.getElement() != null) return true;
            else return false;
        }

        @Override
        public int nextIndex() {
            if (idx == nelems) {
                return nelems;
            }
            return idx++;
        }

        /** Returns the next element in the list and advances cursor */
        @Override
        public E next() throws NoSuchElementException {
            if (!this.hasNext()) throw new NoSuchElementException();
            Node toReturn = right;
            left = right;
            right = right.getNext();
            idx++;
            canRemove = true;
            forward = true;
            addremoved = false;
            return toReturn.getElement();
        }

        @Override
        public boolean hasPrevious() {
            if (nelems == 0) return false;
            else if (left.getElement() == null && idx > 0) return true;
            else if (left.getElement() != null) return true;
            else return false;
        }

        @Override
        public int previousIndex() {
            if (idx == 0) {
                return -1;
            }
            return idx--;
        }

        @Override
        public E previous() throws NoSuchElementException {

        }

    }
}
