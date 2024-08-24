/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 3 maxtrix class.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/*
 * this class is for matrix generation.
 */
public class MatrixCon {
    /**
     * length and height of the matrix,
     */
    public int count;
    /**
     * matrix.
     */
    public int[][] matrix;

    public MatrixCon(int n) {
        this.count = n;
        this.matrix = new int[count][count];
    }
    /**
     * load student information and set connections between different courses.
     * @param tree the tree that stores course info.
     * @param filePath the file location.
     */
    public void loadCourse(RedBlackTree tree, String filePath) {
        try { 
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String line;
            line = in.readLine();
            while(line != null) {
                setConnect(tree, line); 
                line = in.readLine();
            }
            in.close();
        }
        catch(IOException e) {
            System.out.println("IO Exception");
        }
    }
    /**
     * set connection between courses.
     * @param tree the tree that stores course info.
     * @param line each line in the student file, representing the courses for one student.
     */
    public void setConnect(RedBlackTree tree, String line) {
        String[] parts = line.split("\\s+");
        int courseCount = Integer.parseInt(parts[1]);
        String[] courses = new String[courseCount];
        for (int i = 0; i < courseCount; i++) {
            courses[i] = parts[i + 2];
        }
        for (int i = 0; i < courseCount; i++) {
            for (int j = i + 1; j < courseCount; j++) {
                int courseIndex1 = tree.getIndex(courses[i]);
                int courseIndex2 = tree.getIndex(courses[j]);
                matrix[courseIndex1][courseIndex2] = 1;
                matrix[courseIndex2][courseIndex1] = 1;
            }
        }
    }
    /**
     * print the matrix into a writer.
     * @param writer
     */
    public void printMatrix(PrintWriter writer) {
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                writer.print(matrix[i][j]);
            }
            writer.println();
        }
    }
    /*
     * print the matrix into terminal
     */
    public void printMatrix() {
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
