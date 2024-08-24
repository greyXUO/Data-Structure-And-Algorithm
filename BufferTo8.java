/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5.
 * buffer to convert int (<4096) to bytes
 */
import java.io.*;

public class BufferTo8 {
    private int[] data;
    private int count;
    public int outputComfilesize;

    /**
     * initial buffer
     */
    public BufferTo8() {
        data = new int[2];
        count = 0;
        outputComfilesize = 0;
    }

    /**
     * insert an int into the buffer.
     * if 2 ints already in the buffer, then expand the two ints into three bytes
     * and write in to the Data output Stream dos.
     * @param value an int
     * @param dos Data output Stream
     * @throws IOException error
     */
    public void insertInt(int value, DataOutputStream dos) throws IOException {
        //System.out.println(value);
        if (count == 2) {
            // merge 2 int into 3 bytes
            int byte1 = ((data[0] & 0xFFF) >> 4) & 0xFF;
            int byte2 = (((data[0] & 0xFFF) << 4) | ((data[1] & 0xFFF) >> 8)) & 0xFF;
            int byte3 = (data[1] & 0xFFF) & 0xFF;
//            System.out.println(byte1);
//            System.out.println(byte2);
//            System.out.println(byte3);

            // write into output file
            dos.writeByte(byte1);
            dos.writeByte(byte2);
            dos.writeByte(byte3);
            outputComfilesize += 3;
            count = 0;
        }
        data[count++] = value;
        if (count > 2) {
            System.out.println("over");
        }
    }

    /**
     * deal with the last a few ints.
     * If one int left in the buffer, transform it into 2 bytes.
     * If two ints, do the same thing above
     * @param dos Data output Stream
     * @throws IOException error
     */
    public void writeLast(DataOutputStream dos) throws IOException {
        // turn 1 int into 2 bytes
        if (count == 1) {
            int byte1 = ((data[0] & 0xFFF) >> 4) & 0xFF; // get first 8 bits
            int lastFourBits = ((data[0] & 0xF)); // get last 4 bits
            int byte2 = lastFourBits << 4; // add 4 0s
//            System.out.println(byte1);
//            System.out.println(byte2);
            // write into output file
            dos.writeByte(byte1);
            dos.writeByte(byte2);
            outputComfilesize += 2;
        }
        if (count == 2) {
            int byte1 = ((data[0] & 0xFFF) >> 4) & 0xFF;
            int byte2 = (((data[0] & 0xFFF) << 4) | ((data[1] & 0xFFF) >> 8)) & 0xFF;
            int byte3 = (data[1] & 0xFFF) & 0xFF;
//            System.out.println(byte1);
//            System.out.println(byte2);
//            System.out.println(byte3);

            // write into output file
            dos.writeByte(byte1);
            dos.writeByte(byte2);
            dos.writeByte(byte3);
            outputComfilesize += 3;
            count = 0;
        }
    }

    // function test
    public static void main(String[] args) {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("output.txt"));
            BufferTo8 buffer = new BufferTo8();
            buffer.insertInt(2048, dos);
            buffer.insertInt(2047, dos);
            buffer.insertInt(4095, dos);
            buffer.writeLast(dos);
            dos.close();
            System.out.println(buffer.outputComfilesize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
