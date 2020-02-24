package Maze;

import java.util.*;

public class QueueWorklist implements SearchWorklist {
    private LinkedList<Square> queuelist = new LinkedList<Square>();

    // Retrieves and removes the first element of the queue list
    @Override
    public Square getNext() {
        return queuelist.pollFirst();
    }

    // Add a square to the end of the queue list
    @Override
    public void add(Square s) {
        queuelist.addLast(s);
    }

    // Check whether the queue list is empty
    @Override
    public boolean isEmpty() {
        return queuelist.isEmpty();
    }

    // Returns the size of the queue list
    @Override
    public int size() {
        return queuelist.size();
    }

}