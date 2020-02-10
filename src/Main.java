import LinkedList.*;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        MyLinkedList<Integer> empty = new MyLinkedList<Integer>();
        System.out.println(empty.size());
        ListIterator<Integer> iterator;
        iterator = empty.listIterator();
        System.out.println(iterator.hasNext());
    }
}
