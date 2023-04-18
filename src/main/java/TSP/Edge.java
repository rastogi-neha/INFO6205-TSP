package TSP;

public class Edge implements Comparable<Edge> {
    int src, dest;
    double edgeWeight;

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public double getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(double edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    // Comparator function used for sorting edges
    // based on their weight
    public int compareTo(Edge compareEdge)
    {
        return Double.compare(this.edgeWeight,compareEdge.edgeWeight);
    }
}