package Maze;

import java.util.*;

public class StackWorklist implements SearchWorklist {
    private LinkedList<Square> stacklist = new LinkedList<Square>();

    @Override
    public Square getNext() {
        return stacklist.pollLast();
    }

    @Override
    public void add(Square s) {
        stacklist.addLast(s);
    }

    @Override
    public boolean isEmpty() {
        return stacklist.isEmpty();
    }

    @Override
    public int size() {
        return stacklist.size();
    }
}
