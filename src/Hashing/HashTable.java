package Hashing;

import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HashTable implements IHashTable {

    private LinkedList<String>[] hashTable;

    private int nelems;                     //Number of element stored in the hash table
    private int expand;                     //Number of times that the table has been expanded
    private int collision;                  //Number of collisions since last expansion
    private String statsFileName;           //FilePath for the file to write statistics upon every rehash
    private boolean printStats = false;     //Boolean to decide whether to write statistics to file or not after rehashing

    /**
     * Constructor for HashTable with one parameter
     * @param size describes the length of HashTable...for now
     */
    public HashTable(int size) {
        hashTable = new LinkedList[size];
    }

    /**
     * Another constructor for HashTable with a little twist
     * @param size describes the length of the HashTable...for now
     * @param fileName describes the filepath to write statistics
     */
    public HashTable(int size, String fileName){
        hashTable = new LinkedList[size];
        statsFileName = fileName;
        printStats = true;
    }

    /**
     * The method which inserts an element into the HashTable
     * @param value the value to insert
     * @return whether the element is inserted successfully
     * @throws NullPointerException
     */
    @Override
    public boolean insert(String value) throws NullPointerException {
        if (value == null) throw new NullPointerException();
        if (!contains(value)) {
            int bucket = hashFunction(value, hashTable.length);
            addNode(value, bucket, hashTable);
            hashTable = rehash();
            nelems++;
            return true;
        }
        return false;
    }

    /**
     * Helper method to insert element into HashTable
     */
    private void addNode(String value, int bucket, LinkedList<String>[] table) {
        if (table[bucket]==null) {
            table[bucket] = new LinkedList<String>();
        }
        else {
            collision++;
        }
        table[bucket].add(value);
    }

    /**
     * This method deletes an element from the HashTable, if it is there
     * @param value value to delete
     * @return boolean whether it was deleted
     * @throws NullPointerException
     */
    @Override
    public boolean delete(String value) throws NullPointerException {
        if (value == null) throw new NullPointerException();
        if (contains(value)) {
            int bucket = hashFunction(value, hashTable.length);
            hashTable[bucket].remove(value);
            nelems--;
            return true;
        }
        return false;
    }

    /**
     * Check whether the HashTable contains a specific element
     * @param value value to look up
     * @return
     * @throws NullPointerException
     */
    @Override
    public boolean contains(String value) throws NullPointerException {
        if (value == null) throw new NullPointerException();
        int bucket = hashFunction(value, hashTable.length);
        if (hashTable[bucket] != null) {
            for (int i=0; i<hashTable[bucket].size(); i++) {
                if (hashTable[bucket].get(i).equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void printTable() {
        String toReturn = "";
        for (int i=0; i<hashTable.length; i++) {
            toReturn += i + ": ";
            if (hashTable[i] != null) {
                for (int j = 0; j < hashTable[i].size(); j++) {
                    if (hashTable[i].get(j) == null) break;
                    if (hashTable[i].get(j) != null) {
                        toReturn += j;
                    }
                    if (hashTable[i].get(j + 1) != null) {
                        toReturn += ", ";
                    }
                }
            }
            System.out.println(toReturn);
        }
    }

    /**
     * A method that returns the number of elements in a HashTable
     * @return the number of elements in a HashTable
     */
    @Override
    public int getSize() {
        return nelems;
    }

    private LinkedList<String>[] rehash() {
        if (nelems*3 > hashTable.length*2) {
            expand++;
            printStatistics();
            int newSize = 2*hashTable.length;
            LinkedList<String>[] TempTable = new LinkedList[newSize];
            for (int i=0; i<hashTable.length; i++) {
                if (hashTable[i] == null) continue;
                for (int j=0; j<hashTable[i].size(); j++) {
                    String value = hashTable[i].get(j);
                    int bucket = hashFunction(value, newSize);
                    addNode(value, bucket, TempTable);
                }
            }
            hashTable = TempTable;
            collision = 0;
            return hashTable;
        }
        return hashTable;
    }

    private void printStatistics() {
        if (printStats == false) return;
        int longest = findLongestChain();
        double loadFactor = (nelems/hashTable.length);
        try {
            FileWriter writeStats = new FileWriter(statsFileName, true);
            PrintWriter appendStats = new PrintWriter(writeStats);
            appendStats.printf("%d resizes, load factor %.2f, %d collisions, "
                    + "%d longest chain%n", expand, loadFactor, collision, longest);
            appendStats.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private int findLongestChain() {
        int longest = 0;
        for (int i=0; i<hashTable.length; i++) {
            if (hashTable[i] != null) {
                if (longest < hashTable[i].size()) {
                    longest = hashTable[i].size();
                }
            }
        }
        return longest;
    }

    // Hash Function method that returns the index
    private int hashFunction(String value, int tableSize) {
        if (value == null) return -1;
        int hashVal = value.charAt(0);
        for (int i=0; i<value.length(); i++) {
            int letter = value.charAt(i);
            hashVal = (hashVal*27+letter)%tableSize;
        }
        return hashVal;
    }
}
