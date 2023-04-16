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

            // Optimizations
            //2-Opt
            TwoOpt twoOpt = new TwoOpt();
            double twoOptDistance = twoOpt.twoOptimization(tspTourCopy,totalDistance);
            System.out.println("Cost after 2 Optimization: " + twoOptDistance);

            //Simulated Annealing
            SimulatedAnnealing simulatedAnnealingObj = new SimulatedAnnealing();
            ArrayList<City> optimizedTour = simulatedAnnealingObj.simulatedAnnealing(tspTourCopy2, 500000000, 0.000000003);
            double optimizedCost = simulatedAnnealingObj.calculateTotalDistanceAnnealing(optimizedTour);
            //System.out.println("Optimized Tour: " + optimizedTour);
            System.out.println("Optimized Cost: " + optimizedCost);



        }

    }

    public static double calculateTotalDistance(ArrayList<Integer> resultCircuit, double distanceMatrix[][]) {
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
            //System.out.println(td.getCity(i));
        }
        return tspTour;
    }

}