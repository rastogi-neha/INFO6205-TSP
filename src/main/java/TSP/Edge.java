package TSP;

public class Edge implements Comparable<Edge> {
    int src, dest;
    double edgeWeight;

    // Comparator function used for sorting edges
    // based on their weight
    public int compareTo(Edge compareEdge)
    {
        return Double.compare(this.edgeWeight,compareEdge.edgeWeight);
    }
}