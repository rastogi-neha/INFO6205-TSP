INFO 6205 - Program Structure and Algorithms
Spring 2023 Project : Traveling Salesman using Christofides Algorithm



Team 34: 
Neha Rastogi (NUid : 002709191) 
Harshini Venkata Chalam (NUid : 002934047) 
GitHub Repository Link - https://github.com/rastogi-neha/INFO6205-TSP 
 
 
Algorithm 
 
We broke down the problem into smaller subproblems and started dealing with them one by one.

Step 1 : Take input from the CSV files using OpenCSV API and check for proper filepath and reading it in according to the datatypes necessary.

Step 2 : Created a class called City which stores the latitude, longitude, crimeID and adds an additional column called index which helps in using the cities as nodes for the Graph.

Step 3 : Constructed a 2D matrix to store the distances between cities which will serve as the cost for choosing Edges between Nodes.

Step 4 : Now that we have our prerequisites, we initialise a graph and a corresponding adjacency matrix to store chosen edges.


Step 5 : A Minimum Spanning Tree is required to determine the lower bound of an optimal TSP which is constructed in our case using Prim’s algorithm since it is more suited for a denser graph with multiple edges.

Step 6 : The MST may have multiple edges from the graph since its purpose is to get a connected graph therefore we need to determine and isolate vertices having odd degrees since they will be used for constructing a TSP tour.

Step 7 : We find the Perfect matching edges based on the Greedy approach of Nearest Neighbours thus obtaining a secondary graph.

Step 8 : Combine the MST and the secondary graph to create a Multigraph which is utilised for further processing.

Step 9 : The Multigraph is used to construct a Eulerian tour, a path which visits each edge but might have repeating nodes in it.

Step 10 : Next we remove the repeating nodes and convert the Eulerian tour to a Hamiltonian circuit. The current tour starts and ends at the same node and visits each city once in the minimum possible cost.

Step 11 : The tour obtained needs to undergo optimizations to better the current cost that we are obtaining. We implemented Tactical Optimizations namely 2 Opt which swaps links between 2 nodes at a time and if a lower cost is obtained then chooses that.

Step 12 :  3 Opt follows a similar operation but instead of considering 2 nodes, it considers 3 nodes and tries to find a better set of edges which result in a lower cost.

Step 13 : For Strategic Optimization, Simulated Annealing randomly swaps links between 2 cities to find an optimum path however, this was performing poorly so we switched out the swapping algorithm to 2 Opt instead.

Step 14 : Finally, we applied our last Optimization, the Ant Colony Optimization which uses the concept of ants, their pheromones and their ability to determine best paths based on shared knowledge.


This is a Maven project and you need to download the entire file and run the main function.



