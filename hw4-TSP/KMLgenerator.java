import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class KMLgenerator {
    
    public static void toKML(LinkedList<Crime> dict, LinkedList<Integer> tspPath, LinkedList<Integer> optimalPath, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            writer.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
            writer.write("<Document>\n");
            writer.write("<name>Pittsburgh TSP</name><description>TSP on Crime</description>\n");
            writer.write("<Style id=\"style6\">\n");
            writer.write("<LineStyle>\n");
            writer.write("<color>73FF0000</color>\n");
            writer.write("<width>5</width>\n");
            writer.write("</LineStyle>\n");
            writer.write("</Style>\n");
            writer.write("<Style id=\"style5\">\n");
            writer.write("<LineStyle>\n");
            writer.write("<color>507800F0</color>\n");
            writer.write("<width>5</width>\n");
            writer.write("</LineStyle>\n");
            writer.write("</Style>\n");
            
            // Write TSP Path
            writer.write("<Placemark>\n");
            writer.write("<name>TSP Path</name>\n");
            writer.write("<description>TSP Path</description>\n");
            writer.write("<styleUrl>#style6</styleUrl>\n");
            writer.write("<LineString>\n");
            writer.write("<tessellate>1</tessellate>\n");
            writer.write("<coordinates>\n");
            for (int i : tspPath) {
                String record = dict.get(i).data;
                // Split the record by comma
                String[] parts = record.split(",");

                // Get the last two parts
                String secondLastPart = parts[parts.length - 2];
                String lastPart = parts[parts.length - 1];
                writer.write(lastPart + "," + secondLastPart + ",0.000000\n");
            }
            writer.write("</coordinates>\n");
            writer.write("</LineString>\n");
            writer.write("</Placemark>\n");
            
            // Write Optimal Path
            writer.write("<Placemark>\n");
            writer.write("<name>Optimal Path</name>\n");
            writer.write("<description>Optimal Path</description>\n");
            writer.write("<styleUrl>#style5</styleUrl>\n");
            writer.write("<LineString>\n");
            writer.write("<tessellate>1</tessellate>\n");
            writer.write("<coordinates>\n");
            for (int i : optimalPath) {
                String record = dict.get(i).data;
                // Split the record by comma
                String[] parts = record.split(",");

                // Get the last two parts
                double secondLastPart = Double.parseDouble(parts[parts.length - 2]) + 0.0001;
                double lastPart = Double.parseDouble(parts[parts.length - 1]) + 0.0001;
                writer.write(lastPart + "," + secondLastPart + ",0.000000\n");
            }
            writer.write("</coordinates>\n");
            writer.write("</LineString>\n");
            writer.write("</Placemark>\n");
            
            writer.write("</Document>\n");
            writer.write("</kml>\n");
            writer.close();
            System.out.println("KML file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}