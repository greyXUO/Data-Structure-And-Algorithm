import java.io.*;
/**
 * This class encode and compress a file into a 8-bit file.
 * @author Xiao Shi
 * @AndrewID xiaoshi
 * */
public class Encoder {
    private DataInputStream in;

    private DataOutputStream out;

    public int total = 0;

    private HashSet encodeDict; //Dictionary to implement LZW compression.

    private int outCode; // The output code.

    private int[] buffer; // Array to transform String into 12bits.

    private int bufferIndex; // To check if the buffer is full.

    private static final int bitLimit = 256; // Read byte by byte, there are at most 256 different bytes. (8-bits)

    private static final int twelveBit = 4096; // 12 bit output limit.

    public Encoder(String inputFile, String outputFile) throws FileNotFoundException {
        in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(inputFile)));
        out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(outputFile)));
        encodeDict = new HashSet();
        outCode = bitLimit;
        bufferIndex = 0;
        buffer = new int[2];
    }

    /**
     * Initialize the set by inserting 0-255 in.
     * */
    private void initDict() {
        for (int smallInt = 0; smallInt < bitLimit; smallInt++) {
            String key = "" + (char) smallInt;
            String value = String.valueOf(smallInt);
            encodeDict.insert(key, value);
        }
    }

    /**
     * Transform a byte into char.
     * When transform a byte to a char, java will pad sign extension. We shall remove extra bits.
     * @param code the byte just read in
     * @return char without sign
     * */
    private char byteToChar(byte code) {
        char c =(char)code;
        c = (char)(c & 0xFF); //remove extra bits
        return c;
    }

    /**
     * Reinitialize the set if it reaches the 12-bit limit.
     * */
    private void outLimit() {
        if (encodeDict.getSize() > twelveBit) {
            encodeDict = new HashSet();
            initDict();
            outCode = bitLimit;
        }
    }

    /**
     * Put a string into buffer array. Generates 3 bytes every two string.
     * String should be int form string whose value is less than 12-bit (4096).
     * @param s input string
     * @throws IOException
     * */
    private void out(String s) throws IOException, InterruptedException {
        int codeword = Integer.parseInt(s);
        if (bufferIndex == buffer.length - 1) {
            buffer[bufferIndex] = codeword;
            bufferIndex = 0;
            int combined = (buffer[0] << 12) | buffer[1]; // Combine as a 24 bit int
            byte byte1 = (byte) ((combined >> 16) & 0xFF);// The highest 8 bits
            byte byte2 = (byte) ((combined >> 8) & 0xFF);// the middle 8 bits
            byte byte3 = (byte) (combined & 0xFF); // The lowest 8 bits
            out.writeByte(byte1);
            out.writeByte(byte2);
            out.writeByte(byte3);

        } else {
            buffer[bufferIndex] = codeword;
            bufferIndex = bufferIndex + 1;
        }
    }

    /**
     * The final status of buffer will be either empty or contain one element.
     * No need to print anything if the array is empty (if bufferIndex = 0)
     * */
    private void lastOut() throws IOException {
        if (bufferIndex == 0) {
            return;
        } else {
            int combined = buffer[0] << 4;
            int byte1 = ((combined >> 8) & 0xFF);
            int byte2 =  (combined & 0xFF);
            out.writeByte(byte1);
            out.writeByte(byte2);
            total++;
        }
    }

    /**
     * Compress the file throw LZW compression.
     * Read a file byte by byte. Transform the byte into a (key, value) form, where key is a string of int
     * and value is a string of int which is less than 4096(12-bit limit).
     * Write the value (outCode) to binary files.
     * @throws IOException when reach the final bytes, this could help to stop reading.
     * */
    public void compress() throws IOException, InterruptedException {
        initDict();
        String s = "";
        try {
            while (true) {
                byte byteIn = in.readByte();
                char c = byteToChar(byteIn);
                if (encodeDict.contain(s + c)) {
                    s = s + c;
                } else {
                    out(encodeDict.valueOf(s));
                    encodeDict.insert(s + c, String.valueOf(outCode));
                    outCode = outCode + 1;
                    s = "" + c;
                }
                outLimit(); // Reinitialize the set if it reaches the 12-bit limit.
            }

        } catch(EOFException e) {
            lastOut();
            in.close();
            out.close();
            System.out.println("Total bytes read: " + total);
        }
    }
}
