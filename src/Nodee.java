

import java.util.*;

public class Nodee implements Comparable<Nodee> {
    public int name;
    public ArrayList<Edge> neighbours;
    public LinkedList<Nodee> path;
    public int minDistance = Integer.MAX_VALUE;
    public Nodee previous;

    public int compareTo(Nodee other) {
        return Integer.compare(minDistance, other.minDistance);
    }

    public Nodee(int name) {
        this.name = name;
        neighbours = new ArrayList<Edge>();
        path = new LinkedList<Nodee>();
    }

    public int getName() {
        return name;
    }
}
