

import java.util.*;

public class Graph {
    private ArrayList<Nodee> nodes;

    public Graph(int numberVertices) {
        nodes = new ArrayList<Nodee>(numberVertices);
        for (int i = 0; i < numberVertices; i++) {
            nodes.add(new Nodee(i));
        }
    }

    public void addEdge(int src, int dest, int weight) {
        Nodee s = nodes.get(src);
        Edge new_edge = new Edge(nodes.get(dest), weight);
        s.neighbours.add(new_edge);
    }

    public ArrayList<Nodee> getNodes() {
        return nodes;
    }

    public Nodee getNode(int node) {
        return nodes.get(node);
    }

    public void calcDijkstra(Nodee source, Nodee destination) {
        source.minDistance = 0;
        PriorityQueue<Nodee> queue = new PriorityQueue<Nodee>();
        queue.add(source);

        while (!queue.isEmpty()) {

            Nodee u = queue.poll();
            if(u.name == destination.name)
                return;

            for (Edge neighbour : u.neighbours) {
                int newDist = (int) (u.minDistance + neighbour.length);

                if (neighbour.target.minDistance > newDist) {
                    queue.remove(neighbour.target);
                    neighbour.target.minDistance = newDist;

                    neighbour.target.path = new LinkedList<Nodee>(u.path);
                    neighbour.target.path.add(u);

                    queue.add(neighbour.target);
                }
            }
        }
    }
}
