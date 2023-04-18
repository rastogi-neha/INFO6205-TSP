package TSP;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileReading fr=new FileReading();
        List<String[]> dataPoints = fr.readingDataPoints("./src/main/java/crimeSample1.csv");

        if(dataPoints.size()==0)
            System.out.println("Error");

        else{
            TravellerData td=new TravellerData();

            List<City> cities = td.putCitiesInList(dataPoints);
            int v=td.getNumberOfCities();
            System.out.println("The number of cities to be visited are:" +v);
            int e=0;

            //distanceMatrix computed
            double[][] distanceCostMatrix=new double[v][v];
            for(int i=0;i<v;i++){
                for(int j=0;j<v;j++){
                    if(i!=j){
                        distanceCostMatrix[i][j]=distanceCostMatrix[j][i]= td.getDistance(cities.get(i),cities.get(j));
                    }
                    else
                        distanceCostMatrix[i][i]=Integer.MAX_VALUE;
                }
            }
            //graph for Prim's
            Graph g = new Graph(v,e);
            Edge [] primsResult = g.getPrimMST(distanceCostMatrix,v) ;
            //To find Odd Vertex and implement Perfect Edges
            Edge [] mst = g.findAndAddPerfectMatches(primsResult,cities);

            //Creating Multigraph for combining MST with perfect edges
            Graph mg = new Graph(v);

            for(int i=1; i<mst.length; i++) {
                mg.addEdge(mst[i].src, mst[i].dest);
            }

            //Euler Cycle from Multigraph
            mg.eulerianCycle();
            //Remove repeated cities and get Hamiltonian Cycle
            ArrayList<Integer> resultCircuit = mg.clearRepeatedCities(mg.eulerianCircuit);

            //Calculating Total distance
            double totalDistance = calculateTotalDistance(resultCircuit, distanceCostMatrix);
            System.out.println("Cost for Christofides Algorithm: "+totalDistance);

            //Post-processing to convert result set of indexes to Cities
            ArrayList<City> tspTour = postProcessing(resultCircuit,cities);
            ArrayList<City> tspTourCopy = new ArrayList<>(postProcessing(resultCircuit,cities).subList(0, tspTour.size()-1));
            ArrayList<City> tspTourCopy2 = new ArrayList<>(postProcessing(resultCircuit,cities).subList(0, tspTour.size()-1));
            ArrayList<City> tspTourCopy3 = new ArrayList<>(postProcessing(resultCircuit,cities).subList(0, tspTour.size()-1));
            ArrayList<City> tspTourCopy4 = new ArrayList<>(postProcessing(resultCircuit,cities).subList(0, tspTour.size()-1));


            //Christofides path
            showTour(tspTour);

            // Optimizations
            //2-Opt
            TwoOpt twoOpt = new TwoOpt();
            double twoOptDistance = twoOpt.twoOptimization(tspTourCopy,totalDistance);
            System.out.println("Cost after 2 Optimization: " + twoOptDistance);
            showTour(tspTourCopy);

            //3-Opt
            ThreeOpt threeOpt = new ThreeOpt();
            double threeOptDistance = threeOpt.threeOptimization(tspTourCopy2);
            System.out.println("Cost after 3 Optimization: " + threeOptDistance);
            showTour(tspTourCopy2);

            //Simulated Annealing
            SimulatedAnnealing simulatedAnnealingObj = new SimulatedAnnealing();
            ArrayList<City> optimizedTour = simulatedAnnealingObj.simulatedAnnealing(tspTourCopy3, 1000, 0.01);
            double optimizedCost = simulatedAnnealingObj.calculateTotalDistanceAnnealing(optimizedTour);
            System.out.println("Cost after Simulated Annealing: " + optimizedCost);
            showTour(tspTourCopy3);

            //Ant Colony Optimization
            TSP.AntColony aco = new TSP.AntColony(tspTourCopy4,100,0.25,10,0.4,1,0.99,150,1,distanceCostMatrix);
            aco.run();
            showTour(tspTourCopy4);
        }
    }
    public static double calculateTotalDistance(ArrayList<Integer> resultCircuit, double [][]distanceMatrix) {
        double sum = 0;
        int counter = 0;
        while(counter < resultCircuit.size()-1) {
            sum += distanceMatrix[resultCircuit.get(counter)][resultCircuit.get(counter+1)];
            counter++;
        }
        return sum;
    }

    private static ArrayList<City> postProcessing(ArrayList<Integer> resultCircuit, List<City> cities) {
        ArrayList<City> tspTour = new ArrayList<>();
        TravellerData td=new TravellerData();
        for(Integer i : resultCircuit){
            tspTour.add(td.getCity(i));
        }
        return tspTour;
    }

    private static void showTour(ArrayList<City> tspTour){
        for(City c:tspTour){
            System.out.print(c.getCityName().substring(c.getCityName().length()-5) + " -> ");
        }
        System.out.println();
    }

}