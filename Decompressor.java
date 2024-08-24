/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5.
 * decompress class
 */
import java.io.*;

class Decompressor {
    private int MAX_INDEX = 4096;
    private int MAX_BYTE = 256;
    private ByteList decode; // decode arraylist
    private int inputDeComfilesize = 0; // Count the input file byte size
    private int outputDeComfilesize = 0; // Count the output file byte size
    private DataInputStream in;
    private DataOutputStream out;
    private byte[] buffer;
    private int[] decodeInt;
    private int bufferCount;
    private int index;

    /**
     * initialize a compresssor with input and output path
     * @param inputFileName a string
     * @param outputFileName a string
     */
    public Decompressor(String inputFileName, String outputFileName) {
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

            // initialize table
            decode = new ByteList(MAX_INDEX);

            // initialize buffer
            buffer = new byte[3];
            // initialize buffer output
            decodeInt = new int[2];
            // initialize buffer count
            bufferCount = 0;
            // initialize current index (2^8)
            index = MAX_BYTE;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * unzip a compressed file with LZW algorithm.
     * @throws IOException error
     */
    public void unzip() throws IOException {
        int cur;
        byte[] curWord = null;
        boolean isDone = false;
        try {
            // get bytes 3 by 3
            for (int i = 0; i < buffer.length; i++) {
                insertByte(in.readByte());
                inputDeComfilesize++;
            }
            // read the first codeword and output
            int prior = decodeInt[0];
            writeBytes(decode.get(prior));

            while (true) {
                if (isDone) {
                    // if 2 ints are read, redo the loop to generate 2 new ints
                    // get bytes 3 by 3
                    for (int i = 0; i < buffer.length; i++) {
                        insertByte(in.readByte());
                        inputDeComfilesize++;
                    }
                    cur = decodeInt[0];
                } else {
                    // else, continue get the second int generated last loop
                    cur = decodeInt[1];
                }
                if (!decode.contains(cur)) { // if cur not in the list
                    byte[] priorWord = decode.get(prior);
                    byte[] newEntry = extendArray(priorWord, priorWord[0]);
                    // add new string into the list
                    decode.add(newEntry, index++);
                    writeBytes(newEntry);
                } else {
                    curWord = decode.get(cur);
                    byte[] newEntry = extendArray(decode.get(prior), curWord[0]);
                    // add new string into the list
                    decode.add(newEntry, index++);
                    writeBytes(curWord);
                }
                // replace p with c
                prior = cur;
                // mark int read flag
                isDone = !isDone;

                // reinitialize list if full
                if (decode.isFull()) {
                    decode = new ByteList(MAX_INDEX);
                    index = MAX_BYTE;
                }
            }
        } catch (EOFException e) {
            // if 2 more bytes leave in the buffer, write the last byte[]
            if (bufferCount == 1) {
                writeLast();
            }
            in.close();
            out.close();
            System.out.println("bytes read = " + inputDeComfilesize + ", bytes written = " + outputDeComfilesize);
        }
    }

    /**
     * static byte merge method.
     * @param original byte[] 1
     * @param newByte byte[] 2
     * @return merged byte[]
     */
    public static byte[] extendArray(byte[] original, byte newByte) {
        byte[] newArray = new byte[original.length + 1];
        System.arraycopy(original, 0, newArray, 0, original.length);
        newArray[original.length] = newByte;
        return newArray;
    }

    /**
     * insert bytes into the buffer and transform then reach a size of 3.
     * @param b a byte
     */
    public void insertByte(Byte b) {
        if (bufferCount == 2) {
            // convert 3 bytes into 2 int
            buffer[bufferCount++] = b;

            decodeInt[0] = ((buffer[0] & 0xFF) << 4) | ((buffer[1] & 0xF0) >> 4);
            int lowerNibbleShifted = (buffer[1] & 0x0F) << 8;
            decodeInt[1] = lowerNibbleShifted | (buffer[2] & 0xFF);

//            System.out.println(decodeInt[0]);
//            System.out.println(decodeInt[1]);

            //reset buffer
            bufferCount = 0;
            buffer = new byte[3];
            return;
        }
        if (bufferCount == 0) {
            decodeInt = new int[2];
            buffer[bufferCount++] = b;
            return;
        }
        buffer[bufferCount++] = b;
    }

    /**
     * write the last one string with the index (int1) into output file
     * @throws IOException
     */
    public void writeLast() throws IOException {
        if (bufferCount == 2) {
            // convert 2 bytes into 1 int
            int int1 = (buffer[0] & 0xFF) << 4 | ((buffer[1] & 0xFF) >> 4);
            outputDeComfilesize += 2;
            writeBytes(decode.get(int1));
            return;
        }
        if (bufferCount == 1) {
            System.out.println("single byte!");
        }
    }

    /**
     * write bytes into the output file stream
     * @param data byte[]
     * @throws IOException error
     */
    private void writeBytes(byte[] data) throws IOException {
        out.write(data);
        outputDeComfilesize += data.length;
        out.flush();
    }

    // test case
    public static void main(String[] args) throws IOException {
        Decompressor d = new Decompressor("src/zip.txt","src/decompress.txt");
        // Test with three bytes
        d.unzip();
    }
}