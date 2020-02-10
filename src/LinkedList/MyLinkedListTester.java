package LinkedList;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ListIterator;

public class MyLinkedListTester {
    private MyLinkedList<Integer> empty ;
    private MyLinkedList<Integer> one ;
    private MyLinkedList<Integer> several ;
    private MyLinkedList<String>  slist ;
    static final int DIM = 5;
    static final int FIBMAX = 30;

    /**
     * Standard Test Fixture. An empty list, a list with one entry (0) and
     * a list with several entries (0,1,2)
     */
    @Before
    public void setUp() {
        empty = new MyLinkedList<Integer>();
        one = new MyLinkedList<Integer>();
        one.add(0, 0);
        several = new MyLinkedList<Integer>() ;
        // List: 1,2,3,...,Dim
        for (int i = DIM; i > 0; i--)
            several.add(0, i);

        // List: "First","Last"
        slist = new MyLinkedList<String>();
        slist.add(0,"First");
        slist.add(1,"Last");
    }

    /** Test if heads of the lists are correct */
    @Test
    public void testGetHead() {
        assertEquals("Check 0", Integer.valueOf(0),one.get(0)) ;
        assertEquals("Check 0", Integer.valueOf(1),several.get(0)) ;
    }

    /** Test if size of lists are correct */
    @Test
    public void testListSize() {
        assertEquals("Check Empty Size",0,empty.size()) ;
        assertEquals("Check One Size",1,one.size()) ;
        assertEquals("Check Several Size",DIM,several.size()) ;
    }

    /** Test setting a specific entry */
    @Test
    public void testSet() {
        slist.set(1,"Final");
        assertEquals("Setting specific value", "Final",slist.get(1));
    }

    /** Test isEmpty */
    @Test
    public void testEmpty() {
        assertTrue("empty is empty",empty.isEmpty()) ;
        assertTrue("one is not empty",!one.isEmpty()) ;
        assertTrue("several is not empty",!several.isEmpty()) ;
    }

    /** Test out of bounds exception on get */
    @Test
    public void testGetException() {
        try
        {
            empty.get(0);
            // This is how you can test when an exception is supposed
            // to be thrown
            fail("Should have generated an exception");
        }
        catch(IndexOutOfBoundsException e)
        {
            //  normal
        }
    }

    /** Test iterator on empty list and several list */
    @Test
    public void testIterator() {
        int counter = 0 ;
        ListIterator<Integer> iter;
        for (iter = empty.listIterator() ; iter.hasNext(); )
        {
            fail("Iterating empty list and found element") ;
        }
        counter = 0 ;
        for (iter = several.listIterator() ; iter.hasNext(); iter.next())
            counter++;
        assertEquals("Iterator several count", counter, DIM);
    }

    /** test Iterator Fibonacci.
     * This is a more holistic test for the iterator.  You should add
     * several unit tests that do more targeted testing of the individual
     * iterator methods.  */
    @Test
    public void testIteratorFibonacci() {
        MyLinkedList<Integer> fib  = new MyLinkedList<Integer>();
        ListIterator<Integer> iter;
        // List: 0 1 1 2 3 5 8 13 ...
        // Build the list with integers 1 .. FIBMAX
        int t, p = 0, q = 1;
        fib.add(0,p);
        fib.add(1,q);
        for (int k = 2; k <= FIBMAX; k++)
        {
            t = p+q;
            fib.add(k,t);
            p = q; q = t;
        }
        // Now iterate through the list to near the middle, read the
        // previous two entries and verify the sum.
        iter = fib.listIterator();
        int sum = 0;
        for (int j = 1; j < FIBMAX/2; j++)
            sum = iter.next();
        iter.previous();
        assertEquals(iter.previous() + iter.previous(),sum);
        // Go forward with the list iterator
        assertEquals(iter.next() + iter.next(),sum);
    }
}
