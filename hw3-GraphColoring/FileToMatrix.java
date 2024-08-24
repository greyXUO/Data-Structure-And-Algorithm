/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 3 file input driver.
 */
import java.util.Scanner;
/**
 * this class is for data file transform.
 * After running this class, you need to type in the file name in the command line.
 * The output of the program will be :
 * (a) a trwo dimensional matrix that representthe graph.
 * (b) a final exam schedule.
 */
public class FileToMatrix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        scanner.close();

        // create red black tree
        RedBlackTree tree = new RedBlackTree();
        
        tree.constructTree(fileName);

        // construct matrix
        MatrixCon matrix = new MatrixCon(tree.count);
        matrix.loadCourse(tree, fileName);
        matrix.printMatrix();
        System.out.println("");

        // get coloring result
        GraphColoring gc1 = new GraphColoring(matrix.matrix);
        gc1.greedyColoring();

        for (int i = 0; i < tree.count; i++) {
            String re = gc1.printResult(tree.courses, i);
            if (re.trim().isEmpty()) {
                continue;
            }
            System.out.println("Final Exam Period " + (i + 1));
            System.out.println(re);
        }
    }
}
