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
            this.setPrev(null);
            this.setNext(null);
        }
    }

    /**
     * This class defines MyListIterator class
     */
    protected class MyListIterator implements ListIterator<E> {
        private boolean forward = false;
        private boolean canRemove = false;
        private Node left, right; // Cursor sits between these two nodes
        private int idx = 0; // Tracks current position, what next() should return
        private boolean addremoved = false; // Tracks if an add or remove action occured

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
            if (!this.hasPrevious()) throw new NoSuchElementException();
            Node toReturn = left;
            right = left;
            left = left.getPrev();
            idx--;
            canRemove = true;
            forward = false;
            addremoved = false;
            return toReturn.getElement();
        }

        @Override
        public void add(E e) throws IllegalArgumentException {
            if (e == null) throw new IllegalArgumentException();
            else {
                if (nelems == 0) {
                    Node toAdd = new Node(e);
                    head.setNext(toAdd);
                    tail.setPrev(toAdd);
                    toAdd.setPrev(head);
                    toAdd.setNext(tail);
                    left = toAdd;
                    right = tail;
                }
                else {
                    Node toAdd = new Node(e);
                    left.setNext(toAdd);
                    right.setPrev(toAdd);
                    toAdd.setPrev(left);
                    toAdd.setNext(right);
                    left = toAdd;
                }
                idx++;
                nelems++;
                addremoved = true;
            }
        }
        @Override
        public void remove() throws IllegalStateException {
            if (addremoved == true || canRemove == false) throw new IllegalStateException();
            // If last call was to next()
            if (forward) {
                left = left.getPrev();
                right.setPrev(left);
                left.setNext(right);
            }
            // If last call was to prev()
            if (!forward) {
                right = right.getNext();
                left.setNext(right);
                right.setPrev(left);
            }
            nelems--;
            addremoved = true;
        }

        @Override
        public void set(E e) throws IllegalArgumentException, IllegalStateException {
            if (e == null) throw new IllegalArgumentException();
            if (canRemove == false || addremoved == true) throw new IllegalStateException();
            // If last call was next()
            if (forward) {
                left.setElement(e);
            }
            // If last call was prev()
            if (!forward) {
                right.setElement(e);
            }
        }
    }

    // Implementation of MyLinkedList
    public MyLinkedList() {
        head = new Node(null, null, null);
        tail = new Node(null, head, null);
        head.setNext(tail);
        nelems = 0;
    }

    @Override
    public int size() {
        return nelems;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index > this.size()-1 || index < 0) throw new IndexOutOfBoundsException();
        return this.getNth(index).getElement();
    }

    /**
     * Add an element to the list
     * @param index is where in the list to add
     * @param data is the data to add
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    @Override
    public void add(int index, E data) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (data == null) throw new IllegalArgumentException();
        else if (index > nelems || index < 0) throw new IndexOutOfBoundsException();
        else {
            Node toAdd = new Node(data);
            // Adding to end of list or empty list
            if (nelems == 0 || index == nelems) {
                add(data);
            }
            // Add to beginning of the list
            else if (index == 0) {
                Node current = head.getNext();
                head.setNext(toAdd);
                current.setPrev(toAdd);
                toAdd.setPrev(head);
                toAdd.setNext(current);
                nelems++;
            }
            // Add to index location
            else {
                Node current = this.getNth(index);
                Node tempPrev = current.getPrev();
                toAdd.setPrev(tempPrev);
                tempPrev.setNext(toAdd);
                toAdd.setNext(current);
                current.setPrev(toAdd);
                nelems++;
            }
        }
    }

    /**
     * Add an element to end of the list
     * @param data is the data to add
     * @throws IllegalArgumentException
     */
    public boolean add(E data) throws IllegalArgumentException {
        if (data == null) throw new IllegalArgumentException();
        else {
            Node toAdd = new Node(data);
            if (nelems == 0) {
                head.setNext(toAdd);
                tail.setPrev(toAdd);
                toAdd.setPrev(head);
                toAdd.setNext(tail);
            }
            else {
                Node current = this.getNth(nelems-1);
                current.setNext(toAdd);
                tail.setPrev(toAdd);
                toAdd.setPrev(current);
                toAdd.setNext(tail);
            }
            nelems++;
        }
        return true;
    }

    /**
     * Set the element at index in the list
     * @param index at where the list to set
     * @param data data to set
     * @return new data
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public E set(int index, E data) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (data == null) throw new IllegalArgumentException();
        if (index < 0 || index > nelems - 1) throw new IndexOutOfBoundsException();
        Node current = this.getNth(index);
        current.setElement(data);
        return data;
    }

    /**
     * Removes the element at index in the list
     * @param index at where the list to remove
     * @return element's data that is removed
     * @throws IndexOutOfBoundsException
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > nelems - 1) throw new IndexOutOfBoundsException();
        Node current = this.getNth(index);
        E data = current.getElement();
        current.remove();
        nelems--;
        return data;
    }

    /**
     * Clears the list
     */
    public void clear() {
        head.setElement(null);
        tail.setElement(null);
        head.setNext(tail);
        tail.setPrev(head);
        nelems = 0;
    }

    /**
     * Determines if list is empty
     * @return true or false
     */
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    // Helper method to get the Node at the Nth index
    private Node getNth(int index)
    {
        if (index > this.size() - 1) return null;
        Node currNode = this.head;
        for (int i=0; i<index+1; i++) {
            currNode = currNode.getNext();
        }
        return currNode;
    }

    public Iterator<E> QQQiterator() {
        return new MyListIterator();
    }

    public ListIterator<E> QQQlistIterator() {
        return new MyListIterator();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    public static void main (String[] args) {
        MyLinkedList<Integer> empty = new MyLinkedList<Integer>();
        System.out.println(empty.size());
        ListIterator<Integer> iterator;
        iterator = empty.listIterator();
        System.out.println(iterator.hasNext());
    }
}
