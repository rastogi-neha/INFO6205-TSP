import java.util.*;


public class Graph {
    //V->count of vertices
    //E->count of edges
    private final int V;
    private final int E;

    private LinkedList<Integer>[] adjList;
    ArrayList<Integer> eulerianCircuit = new ArrayList<Integer>();

    Graph(int v, int e) {
        this.V = v;
        this.E = e;
    }

    //***Eulerian Cycle******

    void eulerianCycle() {

        Integer u = 0;
        for (int i = 0; i < V; i++)
        {
            if (adjList[i].size() % 2 == 1)
            {
                u = i;
                break;
            }
        }

        eulerUtil(u);
    }

    void eulerUtil(Integer u) {

        for (int i = 0; i < adjList[u].size(); i++)
        {
            Integer v = adjList[u].get(i);

            if (isValidNextEdge(u, v))
            {
                System.out.print(u + "-" + v + " ");
                eulerianCircuit.add(u);
                eulerianCircuit.add(v);

                removeEdge(u, v);
                eulerUtil(v);
            }
        }
    }


    boolean isValidNextEdge(Integer u, Integer v) {

        if (adjList[u].size() == 1) {
            return true;
        }

        boolean[] isVisited = new boolean[this.V];
        int count1 = dfsCount(u, isVisited);

        removeEdge(u, v);
        isVisited = new boolean[this.V];
        int count2 = dfsCount(u, isVisited);

        addEdge(u, v);
        return (count1 > count2) ? false : true;
    }

    int dfsCount(Integer s, boolean[] isVisited) {
        int count=0;
        Stack<Integer> stack = new Stack<>();


        stack.push(s);

        while(stack.empty() == false)
        {
            s = stack.peek();
            stack.pop();

            if(isVisited[s] == false)
            {
                //System.out.print(s + " ");
                isVisited[s]= true;
                count++;
            }

            Iterator<Integer> itr = adjList[s].iterator();

            while (itr.hasNext())
            {
                int v = itr.next();
                if(!isVisited[v]) {
                    stack.push(v);
                }
            }
        }
        return count;
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

    Edge[] findAndAddPerfectMatches(Edge[] mst,List<City> cities){
        int[] neighbouringVerticesOnMST = new int[V];

        for(int i = 1 ; i < mst.length ; i++) {
            int src = mst[i].src;
            int dest = mst[i].dest;
            neighbouringVerticesOnMST[src]++;
            neighbouringVerticesOnMST[dest]++;
        }

        ArrayList<Edge> newEdgesForOddVertexs = new ArrayList<Edge>();
        List<City> oddDegreeVertex = new ArrayList<>();

        for(int i = 0 ; i < neighbouringVerticesOnMST.length ; i++) {
            if(neighbouringVerticesOnMST[i] % 2 == 1) {
                oddDegreeVertex.add(cities.get(i));
            }
        }
        findMatchesWithNearestNeighbour(oddDegreeVertex,newEdgesForOddVertexs);

        //merging new edges into mst so all nodes have even number edge now
        Edge[] newEdges = newEdgesForOddVertexs.toArray(new Edge[0]);
        int fal = mst.length-1;        //determines length of firstArray
        int sal = newEdges.length;   //determines length of secondArray
        Edge[] result = new Edge[fal + sal];  //resultant array of size first array and second array
        System.arraycopy(mst, 0, result, 0, fal);
        System.arraycopy(newEdges, 0, result, fal, sal);

        int[] neighbourCounterOnMST2 = new int[V];

        for (int i = 1; i < fal+sal; ++i) {
            int src = result[i].src;
            int dest = result[i].dest;
            neighbourCounterOnMST2[src]++;
            neighbourCounterOnMST2[dest]++;

        }
        return result;
    }

    void findMatchesWithNearestNeighbour(List<City> oddDegreeVertex, ArrayList<Edge> newEdgesForOddVertexs) {

        int nextCityIndex=0,indexForRemove=0;
        double distance,min=Double.MAX_VALUE;
        TravellerData td=new TravellerData();
        Edge oddDegreeEdge;

        City temp,temp2;
        for(int i=0 ;  i < oddDegreeVertex.size() ;i=nextCityIndex) {

            temp=oddDegreeVertex.get(i);

            oddDegreeVertex.remove(i);

            for (int k = 0; k < oddDegreeVertex.size(); k++) {
                temp2 = oddDegreeVertex.get(k);
                distance =td.getDistance(temp,temp2);

                if(distance<min ) {
                    min=distance;
                    nextCityIndex=0;
                    indexForRemove=k;
                }
            }

            temp2=oddDegreeVertex.get(indexForRemove);
            oddDegreeEdge = new Edge();
            oddDegreeEdge.src = temp.getIndex();
            oddDegreeEdge.dest = temp2.getIndex();
            oddDegreeEdge.edgeWeight = min;
            newEdgesForOddVertexs.add(oddDegreeEdge);

            min=Integer.MAX_VALUE;
            oddDegreeVertex.remove(indexForRemove);

            if(oddDegreeVertex.size()==2){
                oddDegreeEdge = new Edge();
                oddDegreeEdge.src = oddDegreeVertex.get(0).getIndex();
                oddDegreeEdge.dest = oddDegreeVertex.get(1).getIndex();
                oddDegreeEdge.edgeWeight = td.getDistance(oddDegreeVertex.get(0),oddDegreeVertex.get(1));
                newEdgesForOddVertexs.add(oddDegreeEdge);
                break;
            }

        }

    }




    int minKey(double [] cost, boolean visited[]) {

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
        int [] path = new int[V];

        //Cost of picking vertex
        double [] cost = new double[V];

        //Set of visited vertices
        boolean [] visited = new boolean[V];

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
