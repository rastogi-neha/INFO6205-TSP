import java.util.*;
public class Graph {
    //V->count of vertices
    //E->count of edges
    private final int V;
    private final int E;

    private LinkedList<Integer>[] adjList;

    Graph(int v, int e) {
        this.V = v;
        this.E = e;
    }

    Graph(int numOfVertices) {
        // initialise vertex count
        this.V = numOfVertices;
        // initialise adjacency list
        adjList = new LinkedList[V];
        for (int i = 0; i < V; i++)
        {
            adjList[i] = new LinkedList<Integer>();
        }
        E = 0;
    }

//    private void initAdjList() {
//        adjList = new LinkedList[V];
//        for (int i = 0; i < V; i++)
//        {
//            adjList[i] = new LinkedList<Integer>();
//        }
//    }

    void addEdge(Integer u, Integer v) {
        adjList[u].add(v);
        adjList[v].add(u);
    }
    void removeEdge(Integer u, Integer v) {
        adjList[u].remove(v);
        adjList[v].remove(u);
    }

    int minKey(double cost[], boolean visited[]) {

        double min = Double.MAX_VALUE;
        int min_index = -1;

        for ( int v = 0; v < V; v++) {
            if (visited[v] == false && cost[v] < min) {
                min = cost[v];
                min_index = v;
            }
        }
        return min_index;
    }
    Edge[] getPrimMST(double distanceCostMatrix[][],int V){
        //Tour chosen for MST
        int path[] = new int[V];

        //Cost of picking vertex
        double cost[] = new double[V];

        //Set of visited vertices
        boolean visited[] = new boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            cost[i] = Double.MAX_VALUE;
            visited[i] = false;
        }

        // Always include first 1st vertex in MST.
        cost[0] = 0.0; // Make key 0 so that this vertex is
        // picked as first vertex
        path[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {

            //To choose the next vertex
            int u=minKey(cost,visited);
            visited[u]=true;
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (distanceCostMatrix[u][v] != 0 && visited[v] == false && distanceCostMatrix[u][v] < cost[v]) {
                    path[v] = u;
                    cost[v] = distanceCostMatrix[u][v];
                }
        }

        // print the constructed MST
        Edge[] mst = new Edge[V];
        for (int i = 1; i < V; i++) {
            mst[i]=new Edge();
            mst[i].src = path[i];
            mst[i].dest = i;
            mst[i].edgeWeight = distanceCostMatrix[i][path[i]];
            //System.out.println(mst[i].src+"->"+mst[i].dest+"{"+mst[i].edgeWeight +"}");
        }
        return mst;
    }

}
