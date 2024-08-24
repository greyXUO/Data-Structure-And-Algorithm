import java.util.Scanner;
/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 driver class.
 */
public class TwoDTreeDriver {
    public static void main(String[] args) {
        // load file and add into tree.
        TwoDTree tree = new TwoDTree();
        tree.constructTree("CrimeLatLonXY.csv");
        System.out.println("Crime file loaded into 2d tree with " + tree.counts + " records.");

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("8")) {
            System.out.println("What would you like to do?");
            System.out.println("1: inorder");
            System.out.println("2: preorder");
            System.out.println("3: levelorder");
            System.out.println("4: postorder");
            System.out.println("5: reverseLevelOrder");
            System.out.println("6: search for points within rectangle");
            System.out.println("7: search for nearest neighbor");
            System.out.println("8: quit");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    tree.inOrderPrint();
                    break;
                case "2":
                    tree.preOrderPrint();
                    break;
                case "3":
                    tree.levelOrderPrint();
                    break;
                case "4":
                    tree.postOrderPrint();
                    break;
                case "5":
                    tree.reverseLevelOrderPrint();
                    break;
                case "6":
                    System.out.println("Enter a rectangle bottom left (X1,Y1) and top right (X2, Y2) as four doubles each separated by a space.");
                    double x1 = scanner.nextDouble();
                    double y1 = scanner.nextDouble();
                    double x2 = scanner.nextDouble();
                    double y2 = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Searching for points within (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ")");
                    ListOfCrimes crimes = tree.findPointsInRange(x1, y1, x2, y2);
                    System.out.println("Examined " + tree.searchCount + " nodes during search.");
                    System.out.println("Found " + crimes.getCount() + " crimes.");
                    crimes.printList();
                    crimes.toKML();
                    System.out.println("The crime data has been written to PGHCrimes.KML. It is viewable in Google Earth Pro.");
                    break;
                case "7":
                    System.out.println("Enter a point to find nearest crime. Separate with a space.");
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    scanner.nextLine();
                    Neighbor result = tree.nearestNeighbor(x, y);
                    System.out.println("Looked at " + tree.searchCount + " nodes in tree. Found the nearest crime at:");
                    System.out.println(result.toString());
                    break;
                case "8":
                    System.out.println("Thank you for exploring Pittsburgh crimes in the 1990’s.");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 8.");
                    break;
            }
        }
        scanner.close();
    }
}
