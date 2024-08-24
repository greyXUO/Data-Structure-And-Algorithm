/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5.
 * Hash table
 */
public class HashTable {
    public final int DICTIONARY_SIZE = 127;
    public SinglyLinkedList[] dictionary;
    public int size;
    public final int MAX_SIZE = 4096;

    public HashTable() {
        dictionary = new SinglyLinkedList[DICTIONARY_SIZE];
        for (int i = 0; i < DICTIONARY_SIZE; i++) {
            dictionary[i] = new SinglyLinkedList();
        }
        // put all ASCII into the dictionary
        for(int i = 0; i < 256; i++) {
            String temp = "" + (char)i;
            //System.out.print(temp);
            //System.out.println(i);
            int index = Math.abs((temp.hashCode())) % DICTIONARY_SIZE;
            dictionary[index].addNodeEnd(i, temp);
            size++;
        }
    }

    /**
     * insert a string into the dictionary
     * the string is checked first that does not exist in the table
     * @param s a string
     */
    public void insert(String s) {
        int index = Math.abs((s.hashCode())) % DICTIONARY_SIZE;
        dictionary[index].addNodeEnd(size, s);
        //System.out.println(s);
        size++;
    }

    /**
     * check if the table is full
     * @return a boolean
     */
    public boolean isFull() {
        return size == MAX_SIZE;
    }

    /**
     * get the index of a string
     * @param k a string
     * @return an int (<4096)
     */
    public int getValue(String k) {
        int index = Math.abs((k.hashCode())) % DICTIONARY_SIZE;
        return dictionary[index].getValue(k);
    }

    /**
     * check if a string exist in the dict
     * @param k a string
     * @return a boolean
     */
    public boolean containKey(String k) {
        int index = Math.abs((k.hashCode())) % DICTIONARY_SIZE;
        return dictionary[index].containsKey(k);
    }

    public void printDict() {
        System.out.println("==============");
        for (int i = 0; i < DICTIONARY_SIZE; i++) {
            dictionary[i].printList();
        }
        System.out.println("==============");
    }

    //test
    public static void main(String[] args) {
        HashTable hs = new HashTable();
        hs.insert("idk");
        String s = "" + (char)1;
        System.out.println(hs.getValue(s));
        System.out.println(hs.size);
        hs.printDict();
    }
}

