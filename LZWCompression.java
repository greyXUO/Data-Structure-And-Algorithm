/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5.
 * main class
 */
import java.io.*;

public class LZWCompression {
    public static void main(String[] args) throws IOException {
        if (args.length < 4 || !(args[0].equals("-c") || args[0].equals("-d")) || !args[1].equals("-v")) {
            System.out.println("Usage: java LZWCompression <-c|-d> -v <inputFile> <outputFile>");
            return;
        }

        String inputFileName = args[2];
        String outputFileName = args[3];

        if (args[0].equals("-c")) {
            // compress
            Compressor c = new Compressor(inputFileName, outputFileName);
            c.zip();
        } else if (args[0].equals("-d")) {
            // decompress
            Decompressor d = new Decompressor(inputFileName, outputFileName);
            d.unzip();
        }
    }
}
