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
    /* ------------------------ screenshot of a rectangle search ---------------------------------------
     * Crime file loaded into 2d tree with 27218 records.
        What would you like to do?
        1: inorder
        2: preorder
        3: levelorder
        4: postorder
        5: reverseLevelOrder
        6: search for points within rectangle
        7: search for nearest neighbor
        8: quit
        6
        Enter a rectangle bottom left (X1,Y1) and top right (X2, Y2) as four doubles each separated by a space.
        1357605.688 411838.5393 1358805.688 413038.5393
        Searching for points within (1357605.688, 411838.5393) and (1358805.688, 413038.5393)
        Examined 25 nodes during search.
        Found 9 crimes.
        1358646.638,412330.7924,35042,5044 FORBES AV,ROBBERY,12/9/95,140100,40.44444479,-79.94136529
        1358349.449,412795.1595,33641,1060 MOREWOOD AV,RAPE,2/7/92,140100,40.44569883,-79.94247415
        1358205.688,412438.5393,34590,5000 FORBES AV,ROBBERY,9/13/94,140100,40.44471042,-79.94295871
        1358205.688,412438.5393,34074,5000 FORBES AV,ROBBERY,4/15/93,140100,40.44471042,-79.94295871
        1358205.688,412438.5393,33570,5000 FORBES AV,AGGRAVATED ASSAULT,11/28/91,140100,40.44471042,-79.94295871
        1358275.087,412559.1355,33347,1085 MOREWOOD AV,RAPE,4/19/91,140100,40.44504608,-79.9427202
        1358446.935,412903.5158,33181,1045 MOREWOOD AV,RAPE,11/4/90,140100,40.44600282,-79.94213366
        1358701.856,412316.9622,32969,5050 FORBES AV,ROBBERY,4/6/90,140100,40.44441059,-79.94116573
        1358205.688,412438.5393,32898,5000 FORBES AV,ROBBERY,1/25/90,140100,40.44471042,-79.94295871
        The crime data has been written to PGHCrimes.KML. It is viewable in Google Earth Pro.

        ---------------------------------------KML FILE----------------------------------------------------
        <?xml version="1.0" encoding="UTF-8" ?>
        <kml xmlns="http://www.opengis.net/kml/2.2">
        <Document>
        <Style id="style1">
        <IconStyle>
        <Icon>
        <href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue-dot.png</href>
        </Icon>
        </IconStyle>
        </Style>
        <Placemark>
        <name>ROBBERY</name>
        <description>5044 FORBES AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44444479,-79.94136529</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>RAPE</name>
        <description>1060 MOREWOOD AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44569883,-79.94247415</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>ROBBERY</name>
        <description>5000 FORBES AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44471042,-79.94295871</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>ROBBERY</name>
        <description>5000 FORBES AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44471042,-79.94295871</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>AGGRAVATED ASSAULT</name>
        <description>5000 FORBES AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44471042,-79.94295871</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>RAPE</name>
        <description>1085 MOREWOOD AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44504608,-79.9427202</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>RAPE</name>
        <description>1045 MOREWOOD AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44600282,-79.94213366</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>ROBBERY</name>
        <description>5050 FORBES AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44441059,-79.94116573</coordinates>
        </Point>
        </Placemark>
        <Placemark>
        <name>ROBBERY</name>
        <description>5000 FORBES AV</description>
        <styleUrl>#style1</styleUrl>
        <Point>
        <coordinates>40.44471042,-79.94295871</coordinates>
        </Point>
        </Placemark>
        </Document>
        </kml>
        ----------------------------------END OF HOMEWORK---------------------------------------------------
     */
}
