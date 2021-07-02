

//Max lat: 652685 Min lat: 573929
//Max long: 5018275 Min long: 4945029

import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

    public static int globalHeigth = 1450;
    public static int globalWidth = 800;

    public static double oldHeigth = 78756;
    public static double oldWidth = 73246;

    public static double scaledWidth = globalHeigth / oldWidth;
    public static double scaledHeigth = globalWidth / oldHeigth;

    public static int convertLongitude(int longitude) {
        return (int) ((longitude - 4945029) * scaledWidth);
    }

    public static int convertLatitude(int latitude) {
        return (int) ((latitude - 573929) * scaledHeigth);
    }

    public static GraphDraw frame = new GraphDraw("Dijkstra");

    public static Graph g;

    public static void main(String[] args) {

        int sizeOfGraph = 0;
        frame.setSize(globalWidth, globalHeigth);
        frame.setVisible(true);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("map2.xml");

            NodeList nodesList = doc.getElementsByTagName("nodes");
            Node nodes = nodesList.item(0);
            if (nodes.getNodeType() == Node.ELEMENT_NODE) {
                Element aNode = (Element) nodes;
                NodeList nList = aNode.getChildNodes();
                for (int i = 0; i < nList.getLength(); i++) {
                    Node n = nList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element currNode = (Element) n;
                        String nodeId = currNode.getAttribute("id");
                        String nodeLong = currNode.getAttribute("longitude");
                        String nodeLat = currNode.getAttribute("latitude");
                        frame.addNode(nodeId, convertLongitude(Integer.parseInt(nodeLong)),
                                convertLatitude(Integer.parseInt(nodeLat)));
                        sizeOfGraph++;
                    }
                }
            }

            g = new Graph(sizeOfGraph);

            NodeList arcsList = doc.getElementsByTagName("arcs");
            Node arcs = arcsList.item(0);
            if (arcs.getNodeType() == Node.ELEMENT_NODE) {
                Element anArc = (Element) arcs;
                NodeList aList = anArc.getChildNodes();
                for (int i = 0; i < aList.getLength(); i++) {
                    Node a = aList.item(i);
                    if (a.getNodeType() == Node.ELEMENT_NODE) {
                        Element currArc = (Element) a;
                        String from = currArc.getAttribute("from");
                        String to = currArc.getAttribute("to");
                        String length = currArc.getAttribute("length");
                        g.addEdge(Integer.parseInt(from), Integer.parseInt(to), Integer.parseInt(length));
                        frame.addEdge(Integer.parseInt(from), Integer.parseInt(to));
                    }
                }

            }
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
