package Hashing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class HashtableTester {
    private HashTable testHashTable1;

    @Before
    public void setUp()
    {
        testHashTable1 = new HashTable(1);
    }

    @Test
    public void testInsert()
    {
        assertEquals("Checking insert",true,testHashTable1.insert("abc"));
        assertEquals("Checking contains after insert",true,testHashTable1.contains("abc"));
        assertEquals("Checking size after insert",new Integer(1),new Integer(testHashTable1.getSize()));
    }

    @Test
    public void testDelete()
    {
        testHashTable1.insert("abc");
        assertEquals("Checking delete",true,testHashTable1.delete("abc"));
        assertEquals("Checking contains after delete",false,testHashTable1.contains("abc"));
        assertEquals("Checking size after delete",new Integer(0),new Integer(testHashTable1.getSize()));
    }

    @Test
    public void testGetSize()
    {
        testHashTable1.insert("abc");
        testHashTable1.insert("pqr");
        testHashTable1.insert("xyz");
        testHashTable1.insert("elf");
        testHashTable1.insert("yo");
        assertEquals("Checking getSize",new Integer(5),new Integer(testHashTable1.getSize()));
    }

    @Test
    public void testContainsTrue()
    {
        testHashTable1.insert("abc");
        assertEquals("Checking contains when True",true,testHashTable1.contains("abc"));
    }

    @Test
    public void testContainsFalse()
    {
        testHashTable1.insert("abe");
        assertEquals("Checking contains when False",false,testHashTable1.contains("abc"));
    }

    @Test
    public void testContainsBeforeAfterDelete()
    {
        assertEquals("Checking contains before add is called", false, testHashTable1.contains("abc"));
        testHashTable1.insert("abc");
        assertEquals("Checking contains before delete is called",true,testHashTable1.contains("abc"));
        testHashTable1.delete("abc");
        assertEquals("Checking contains after delete is called",false,testHashTable1.contains("abc"));
    }

    @Test
    public void testContainsAfterReadd()
    {
        testHashTable1.insert("abc");
        testHashTable1.delete("abc");
        assertEquals("Checking contains after delete is called",false,testHashTable1.contains("abc"));
        testHashTable1.insert("abc");
        assertEquals("Checking contains after readding",true,testHashTable1.contains("abc"));
    }

    @Test
    public void testContainsEverything()
    {

        testHashTable1.insert("abc");
        testHashTable1.insert("pqr");
        testHashTable1.insert("xyz");
        assertEquals("Checking if contains first added",true,testHashTable1.contains("abc"));
        assertEquals("Checking if contains second added",true,testHashTable1.contains("pqr"));
        assertEquals("Checking if contains third added",true,testHashTable1.contains("xyz"));
    }

    @Test
    public void testInsertAgain()
    {
        testHashTable1.insert("abc");
        assertEquals("Checking output for duplicate inserts",false,testHashTable1.insert("abc"));
    }

    @Test
    public void testInsertException()
    {
        try {
            testHashTable1.insert(null);
            fail("Insert method should have a NullPointerException");
        }
        catch (NullPointerException e) {
            //normal
        }
    }

    @Test
    public void testContainsException()
    {
        try {
            testHashTable1.contains(null);
            fail("Contains method should have generated a NullPointerException");
        }
        catch (NullPointerException e){
            //normal
        }
    }

    @Test
    public void testDeleteAgain()
    {
        testHashTable1.insert("abc");
        testHashTable1.delete("abc");
        assertEquals("Checking output for delete again",false,testHashTable1.delete("abc"));
        assertEquals("Checking output for delete a nonexistant element",false,testHashTable1.delete("def"));
    }

    @Test
    public void testDeleteException()
    {
        try {
            testHashTable1.delete(null);
            fail("Delete method should have generated a NullPointerException");
        }
        catch (NullPointerException e){
            //normal
        }
    }
}