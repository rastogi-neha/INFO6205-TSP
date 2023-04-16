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
            ArrayList<City> tspTourCopy = new ArrayList<>(tspTour.subList(0, tspTour.size()-1));

            // Optimizations
            //2-Opt
            TwoOpt twoOpt = new TwoOpt();
            double twoOptDistance = twoOpt.twoOptimization(tspTourCopy,totalDistance);
            System.out.println("Cost after 2 Optimization: " + twoOptDistance);

            //Simulated Annealing
            ArrayList<City> optimizedTour = simulatedAnnealing(tspTour, 10000, 0.999, td);
            double optimizedCost = calculateTotalDistanceAnnealing(optimizedTour, td);
            System.out.println("Optimized Tour: " + optimizedTour);
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

// Simulated Annealing code
    private static ArrayList<City> simulatedAnnealing(ArrayList<City> tspTour, double temperature, double coolingRate, TravellerData td) {
        ArrayList<City> currentSolution = new ArrayList<>(tspTour);
        ArrayList<City> bestSolution = new ArrayList<>(currentSolution);

        while (temperature > 1) {
            ArrayList<City> newSolution = new ArrayList<>(currentSolution);

            // Generate a new solution by swapping two cities randomly
            int cityIndex1 = (int) (newSolution.size() * Math.random());
            int cityIndex2 = (int) (newSolution.size() * Math.random());
            City city1 = newSolution.get(cityIndex1);
            City city2 = newSolution.get(cityIndex2);
            newSolution.set(cityIndex1, city2);
            newSolution.set(cityIndex2, city1);

            // Calculate the cost (distance) of the new solution
            double currentCost = calculateTotalDistanceAnnealing(currentSolution, td);
            double newCost = calculateTotalDistanceAnnealing(newSolution, td);

            // Decide whether to accept the new solution based on the cost and temperature
            if (acceptanceProbability(currentCost, newCost, temperature) > Math.random()) {
                currentSolution = new ArrayList<>(newSolution);
            }

            // Update the best solution found so far
            if (calculateTotalDistanceAnnealing(currentSolution, td) < calculateTotalDistanceAnnealing(bestSolution, td)) {
                bestSolution = new ArrayList<>(currentSolution);
            }

            // Cool down the temperature
            temperature *= coolingRate;
        }

        return bestSolution;
    }

    private static double calculateTotalDistanceAnnealing(ArrayList<City> tour, TravellerData td) {
        double totalDistance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalDistance += td.getDistance(tour.get(i), tour.get(i + 1));
        }
        totalDistance += td.getDistance(tour.get(tour.size() - 1), tour.get(0));
        return totalDistance;
    }

    private static double acceptanceProbability(double currentCost, double newCost, double temperature) {
        if (newCost < currentCost) {
            return 1.0;
        }
        return Math.exp((currentCost - newCost) / temperature);
    }

}