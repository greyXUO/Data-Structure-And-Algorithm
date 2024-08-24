import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 list of crimes class.
 */
public class ListOfCrimes {
    /**
     * head of the singly linked list.
     */
    private Crime head;
    /**
     * node define.
     */
    private int counts;
    /**
     * nested class for crime.
     */
    public static class Crime {
        /**
         * a string store crime info.
         */
        private String record;
        /**
         * a reference to the next crime node.
         */
        private Crime next;
        private String crimeType;
        private String address;
        private String num1;
        private String num2;
        //private String num3;
        /**
         * initialize crime node.
         * @param record a string.
         * @param next a node.
         */
        public Crime(String record, Crime next) {
            this.record = record;
            this.next = next;
            String[] info = record.split(",");
            this.num1 = info[7];
            this.num2 = info[8];
            //this.num3 = info[8];
            this.address = info[3];
            this.crimeType = info[4];
        }
        /**
         * set current node next to the given reference.
         * @param nextOne a node.
         */
        public void setNext(Crime nextOne) {
            this.next = nextOne;
        }
        /**
         * check if current node has next node.
         * @return a boolean.
         */
        public boolean hasNext() {
            return this.next!= null;
        }
        @Override
        public String toString() {
            return record;
        }
    }
    /**
     * initialize list of crimes.
     */
    public ListOfCrimes() {
        head = null;
    }
    /**
     * add crime record into the list.
     * @param record a string.
     */
    public void add(String record) {
        counts++;
        Crime newCrime = new Crime(record, null);
        if (head == null) {
            head = newCrime;
            return;
        }
        newCrime.setNext(head);
        head = newCrime;
    }
    /**
     * return all records in a string.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        Crime current = head; 
        while (current != null) {
            result.append(current.toString());
            result.append("\n");
            current = current.next; 
        }
        return result.toString();
    }
    /**
     * print list in the terminal.
     */
    public void printList() {
        Crime current = head; 
        while (current != null) {
            System.out.println(current); 
            current = current.next; 
        }
    }  
    /**
     * get the length of the list.
     * @return an int.
     */
    public int getCount() {
        return counts;
    }
    public void toKML() {
        try (FileWriter writer = new FileWriter("PGHCrimes.KML")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
            writer.write("<Document>\n");
            writer.write(" <Style id=\"style1\">\n");
            writer.write(" <IconStyle>\n");
            writer.write(" <Icon>\n");
            writer.write(" <href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue-dot.png</href>\n");
            writer.write(" </Icon>\n");
            writer.write(" </IconStyle>\n");
            writer.write(" </Style>\n");
            
            // Iterate through the linked list and create Placemark for each Crime
            Crime current = head;
            while (current != null) {
                writer.write(" <Placemark>\n");
                writer.write(" <name>" + current.crimeType + "</name>\n");
                writer.write(" <description>" + current.address+ "</description>\n");
                writer.write(" <styleUrl>#style1</styleUrl>\n");
                writer.write(" <Point>\n");
                writer.write(" <coordinates>" + current.num2 + "," + current.num1 + "</coordinates>\n");
                writer.write(" </Point>\n");
                writer.write(" </Placemark>\n");
                
                current = current.next;
            }
            
            writer.write("</Document>\n");
            writer.write("</kml>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
