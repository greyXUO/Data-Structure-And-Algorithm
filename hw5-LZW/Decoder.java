import java.io.*;
/**
 * This class decode and decompress a file into a 8-bit file.
 * @author Xiao Shi
 * @AndrewID xiaoshi
 * */
public class Decoder {

    private DataInputStream in;

    private DataOutputStream out;

    private HashSet decodeDict; //Dictionary to implement LZW compression.

    private int priorPointer; // To show which is the prior code word.

    private int key; // The depression key

    private byte[] buffer; // Array to transform 3 bytes into 12-bits

    private int bufferIndex; // To check if the buffer is full.

    private String[] codeword; // Two code words, one is prior code word, the other is current code word

    private static final int bitLimit = 256;// Read byte by byte, there are at most 256 different bytes. (8-bits)

    private static final int twelveBit = 4096; // 12 bit output limit.

    public Decoder(String inputFile, String outputFile) throws FileNotFoundException {
        in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(inputFile)));
        out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(outputFile)));
        decodeDict = new HashSet();
        buffer = new byte[3];
        bufferIndex = 0;
        codeword = new String[2];
        priorPointer = 0;
        key = bitLimit;
    }

    private void initDict() {
        for (int smallInt = 0; smallInt < bitLimit; smallInt++) {
            String key = String.valueOf(smallInt);
            String value = "" + (char) smallInt;
            decodeDict.insert(key, value);
        }
    }

    /**
     * Read Three bytes and generate two 12-bits number in a string form (0 - 4095)
     * buffer is used to store three bytes. The pointer will return to 0 if the array is full.
     * codeword only will be updated if buffer is full.
     * */
    private void byteToString (byte input) {
        if (bufferIndex == buffer.length - 1) { // if buffer is full.
            buffer[bufferIndex] = input;
            bufferIndex = 0;
            int firstNumber = ((buffer[0] & 0xFF) << 4) | ((buffer[1] & 0xF0)>> 4);
            int secondNumber = ((buffer[1] & 0x0F) << 8) | (buffer[2] & 0xFF);
            codeword[0] = String.valueOf(firstNumber);
            codeword[1] = String.valueOf(secondNumber);
        } else {
            buffer[bufferIndex] = input;
            bufferIndex = (bufferIndex + 1) % 3;
        }
    }

    /**
     * Reinitialize the set if it reaches the 12-bit limit.
     * */
    private void outLimit() {
        if (decodeDict.getSize() > twelveBit) {
            decodeDict = new HashSet();
            initDict();
            key = bitLimit;
        }
    }

    /**
     * Read next tree bytes from the compressed file.
     * @throws IOException
     * */
    private void readThreeBytes() throws IOException {
        for (int i = 0; i < buffer.length; i++) {
            byteToString(in.readByte());
        }
    }

    /**
     * Write the string out char by char. Transform char into bytes, then write bytes into the final file.
     * @param s output string
     * @throws IOException
     * */
    private void out(String s) throws IOException {
        for (int i = 0; i < s.length(); i++) {
            char charResult = s.charAt(i);
            byte result = (byte) (charResult & 0xFF);
            out.writeByte(result);
        }
    }

    /**
     * Handle the situation when the last two bytes in encoding is padding to 16 bits.
     * The total number of bytes is not the not a multiple of 3 only if the last two bytes is a padding 12-bits.
     * Thus, we only need to take care of this situation.
     * */
    private void lastOut() throws IOException {
        if (bufferIndex != buffer.length - 1) { // buffer is not full when it comes to the end.
            int firstNumber = ((buffer[0] & 0xFF) << 4) | ((buffer[1] & 0xF0)>> 4);
            String result = decodeDict.valueOf(String.valueOf(firstNumber));
            out(result);
        }
    }

    /**
     * Decompress the file throw LZW compression.
     * Read a file three bytes at a time. Transform the byte into two Strings.
     * For each String, they contain information of the original index, which is in [0, 4095]
     * Use the string as key to find the original value.
     * The dictionary is dynamic and this is the key of LZW compression. We can recover the dictionary during the
     * decompression process rather than store it.
     * @throws IOException when reach the final bytes, this could help to stop reading.
     * */
    public void depress() throws IOException {
        initDict();
        try {
            // Initialize the first prior codeword and first codeword
            readThreeBytes();
            String prior = codeword[0]; //prior codeword
            out(decodeDict.valueOf(codeword[priorPointer]));
            String current; // codeword

            // loop
            while (true) {
                // When the prior codeword is the second elements in the array, read a new group of bytes.
                // Because the prior codeword is already pointed by prior and we can reset the codeword array.
                if (priorPointer == 1) {
                    readThreeBytes();
                    current = codeword[0];
                } else { // if not, just update current.
                    current = codeword[1];
                }
                // Dynamically build the dictionary.
                if (!decodeDict.contain(current)) {
                    String newWord = decodeDict.valueOf(prior) + decodeDict.valueOf(prior).charAt(0);
                    decodeDict.insert(String.valueOf(key), newWord);
                    key = key + 1;
                    out(newWord);
                } else {
                    String newWord = decodeDict.valueOf(prior) + decodeDict.valueOf(current).charAt(0);
                    decodeDict.insert(String.valueOf(key), newWord);
                    key = key + 1;
                    out(decodeDict.valueOf(current));
                }
                prior = current;
                priorPointer = (priorPointer + 1) % 2; // automatically update the pointer.
                outLimit(); // Check if out of 12-bits limit
            }

        } catch(EOFException e) {
            lastOut(); // check the last two bits.
            in.close();
            out.close();
        }
    }

}
