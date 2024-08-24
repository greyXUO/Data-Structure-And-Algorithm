/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5.
 *  byte[] array list
 */
public class ByteList {
    public byte[][] elements;  // byte[] array
    public int size;

    public ByteList(int initialCapacity) {
        this.elements = new byte[initialCapacity][];
        this.size = 0;
        this.addBase();
    }

    public boolean isFull() {
        return size == elements.length;
    }

    public void addBase() { // add all possible byte combination into the array
        for (int i = 0; i < 256; i++) {
            this.add(new byte[]{(byte) i}, i);  //
        }
    }

    /**
     * check if one index already in the array list.
     * @param i an int
     * @return a boolean
     */
    public boolean contains(int i) {
        return i < size && elements[i] != null;
    }

    public int size() {
        return this.size;
    }

    /**
     * add a byte[] into the array list
     * @param bytes byte[]
     * @param index an int
     */
    public void add(byte[] bytes, int index) {
        if (index != size) {
            System.out.println("not match: " + index + " & " + size);
        } else if (index < elements.length) {
            elements[index] = bytes;
            size++;
        } else {
            throw new IndexOutOfBoundsException("Index exceeds maximum capacity.");
        }
    }

    // print
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(i + ": ");
            for (byte b : elements[i]) {
                System.out.print(String.format("%02X ", b));
            }
            System.out.println();
        }
    }

    /**
     * get the value given a key
     * @param index an int
     * @return byte[]
     */
    public byte[] get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }

    // test
    public static void main(String[] args) {
        ByteList list = new ByteList(400);

        System.out.println(list.size());

        list.add(new byte[]{97}, 256);  // ASCII for 'a'
        list.add(new byte[]{98}, 257);  // ASCII for 'b'
        list.add(new byte[]{99}, 258);  // ASCII for 'c'

        System.out.println(list.size());

        for (int i = 0; i < list.size(); i++) {
            System.out.print(i + ": ");
            for (byte b : list.get(i)) {
                System.out.print((char) b);
            }
            System.out.println();
        }
    }
}
