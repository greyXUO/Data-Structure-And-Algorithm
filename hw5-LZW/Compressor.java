/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5.
 * Compressor class
 */
import java.io.*;

class Compressor {
    private int TABLE_SIZE = 127;
    private int MAX_INDEX = 4096; // 2^12
    private int MAX_BYTE = 256;
    private HashTable encode;
    private int inputComfilesize = 0; // Count the input file byte size
    private BufferTo8 buffer;
    private DataInputStream in;
    private DataOutputStream out;


    /**
     * initial the compressor
     * @param inputFileName string
     * @param outputFileName string
     */
    public Compressor(String inputFileName, String outputFileName) {
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

            // initialize hash table
            encode = new HashTable();

            // initialize buffer
            buffer = new BufferTo8();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * compress method
     * @throws IOException error
     */
    public void zip() throws IOException {
        String s = "";
        char c;
        byte byteIn;

        try {
            byteIn = in.readByte();
            inputComfilesize++;
            s = "" + (char)(byteIn & 0xFF);

            while (true) {
                byteIn = in.readByte();
                inputComfilesize++;
                c = (char)(byteIn & 0xFF);
                // if s + c is in the dictionary
                if (encode.containKey(s + c)) {
                    s = s + c;
                } else {
                    //write value of s int;
                    buffer.insertInt(encode.getValue(s), out);

                    encode.insert(s + c);
                    s = "" + c;
                }

                if (encode.isFull()) {
                    encode = new HashTable();
                }
            }
        } catch (EOFException e) {
            in.close();
            // insert the last value
            buffer.insertInt(encode.getValue(s), out);
            // write into output file
            buffer.writeLast(out);
            out.close();
            System.out.println("bytes read = " + inputComfilesize + ", bytes written = " + buffer.outputComfilesize);
        }
    }
}