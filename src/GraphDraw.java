

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class GraphDraw extends JFrame {

    public double getDistance(Node node1, Node node2) {
        return Math.sqrt((node2.x - node1.x) * (node2.x - node1.x) + (node2.y - node1.y) * (node2.y - node1.y));
    }

    public Node getClosescNode(Node node) {
        int radius = 100;
        double distance = Double.MAX_VALUE;
        Node foundNode = null;
        for (Node nodeToCheck : nodes) {
            if ((node.x + radius) <= nodeToCheck.x) {
                if (getDistance(nodeToCheck, node) < distance) {
                    distance = getDistance(nodeToCheck, node);
                    foundNode = new Node(nodeToCheck.idName, nodeToCheck.x, nodeToCheck.y);
                }
            }
            if ((node.x - radius) <= nodeToCheck.x) {
                if (getDistance(nodeToCheck, node) < distance) {
                    distance = getDistance(nodeToCheck, node);
                    foundNode = new Node(nodeToCheck.idName, nodeToCheck.x, nodeToCheck.y);
                }
            }
            if ((node.y + radius) <= nodeToCheck.y) {
                if (getDistance(nodeToCheck, node) < distance) {
                    distance = getDistance(nodeToCheck, node);
                    foundNode = new Node(nodeToCheck.idName, nodeToCheck.x, nodeToCheck.y);
                }
            }
            if ((node.y - radius) <= nodeToCheck.y) {
                if (getDistance(nodeToCheck, node) < distance) {
                    distance = getDistance(nodeToCheck, node);
                    foundNode = new Node(nodeToCheck.idName, nodeToCheck.x, nodeToCheck.y);
                }
            }
        }
        return foundNode;
    }

    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    Point selectedNode = null;
    ArrayList<Node> nodesForDijsktra = new ArrayList<Node>();

    public GraphDraw(String name) {
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                selectedNode = event.getPoint();
            }

            public void mouseReleased(MouseEvent event) {
                nodesForDijsktra.add(new Node("dummyName", event.getX(), event.getY()));
                selectedNode = null;

                Node node1 = null;
                Node node2 = null;

                long startTime = System.currentTimeMillis();

                if (nodesForDijsktra.size() == 2) {
                    node1 = getClosescNode(nodesForDijsktra.get(0));
                    node2 = getClosescNode(nodesForDijsktra.get(1));

                    Main.g.calcDijkstra(Main.g.getNode(Integer.parseInt(node1.idName)),
                            Main.g.getNode(Integer.parseInt(node2.idName)));

                    Nodee n = Main.g.getNode(Integer.parseInt(node2.idName));
                    long endTime = System.currentTimeMillis();

//					System.out.println("Start node: " + node1.idName + ", destination: " + n.getName() + ", path:");
//					for (Nodee path : n.path) {
//						System.out.println(path.getName());
//					}
//					System.out.println("Distance: " + n.minDistance + ".");

                    long drawStartTime = System.currentTimeMillis();
                    for (int i = 0; i < n.path.size() - 1; i++) {
                        for (Edge e : edges) {
                            if (n.path.get(i).getName() == e.from && n.path.get(i + 1).getName() == e.to) {
                                e.setColor(Color.red);
                                e.setStroke(3);
                                break;
                            }
                        }
                    }

                    long endDrawTime = System.currentTimeMillis();

                    JLabel startNodeLabel = new JLabel("Start node: " + node1.idName);
                    JLabel destinationNodeLabel = new JLabel("Destination node: " + n.getName());
                    JLabel distanceLabel = new JLabel("Distance: " + String.valueOf(n.minDistance));
                    startNodeLabel.setFont(new Font("Verdana", 1, 20));
                    startNodeLabel.setBounds(1000, 600, 300, 20);
                    destinationNodeLabel.setFont(new Font("Verdana", 1, 20));
                    destinationNodeLabel.setBounds(1000, 625, 300, 20);
                    distanceLabel.setFont(new Font("Verdana", 1, 20));
                    distanceLabel.setBounds(1000, 650, 300, 20);

                    Main.frame.add(startNodeLabel);
                    Main.frame.add(destinationNodeLabel);
                    Main.frame.add(distanceLabel);
                    repaint();

                    JLabel timeLabel = new JLabel(
                            "Dijsktra computation time: " + String.valueOf(endTime - startTime) + " ms");
                    timeLabel.setFont(new Font("Verdana", 1, 20));
                    timeLabel.setBounds(1000, 675, 500, 20);
                    Main.frame.add(timeLabel);

                    JLabel drawTimeLabel = new JLabel(
                            "Draw time: " + String.valueOf(endDrawTime - drawStartTime) + " ms");
                    drawTimeLabel.setFont(new Font("Verdana", 1, 20));
                    drawTimeLabel.setBounds(1000, 700, 300, 20);
                    Main.frame.add(drawTimeLabel);

                    // nodesForDijsktra.clear();
                    return;
                }
            }
        });
    }

    class Node {
        int x, y;
        String idName;

        public Node(String name, int x, int y) {
            this.x = x;
            this.y = y;
            this.idName = name;
        }
    }

    class Edge {
        int from, to;
        double stroke;
        Color color;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
            this.stroke = 1;
            color = Color.darkGray;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setStroke(float stroke) {
            this.stroke = stroke;
        }
    }

    public void addNode(String id, int x, int y) {
        nodes.add(new Node(id, x, y));
    }

    public void addEdge(int from, int to) {
        edges.add(new Edge(from, to));
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paint(g2);
        getContentPane().setBackground(Color.lightGray);
//		g2.translate(this.getWidth() / 2, this.getHeight());
//		g2.rotate(Math.PI);
        for (Edge e : edges) {
            g2.setColor(e.color);
            g2.setStroke(new BasicStroke((float) e.stroke));
            g2.drawLine(nodes.get(e.from).x, nodes.get(e.from).y, nodes.get(e.to).x, nodes.get(e.to).y);
        }
    }
}
