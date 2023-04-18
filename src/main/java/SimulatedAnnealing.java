

import java.util.ArrayList;

public class SimulatedAnnealing {

    public static ArrayList<City> simulatedAnnealing(ArrayList<City> tspTour, double temperature, double coolingRate) {
        ArrayList<City> currentSolution = new ArrayList<>(tspTour);
        ArrayList<City> bestSolution = new ArrayList<>(currentSolution);
        int c=0,counter=500000;
        while (temperature > 1 && c<=counter) {
            TwoOpt twoOpt=new TwoOpt();
            double d= calculateTotalDistanceAnnealing(currentSolution);
            double newCost = twoOpt.twoOptimization(currentSolution,d);
            ArrayList<City> newSolution =currentSolution;
            // Generate a new solution by swapping two cities randomly
//            int cityIndex1 = (int) (newSolution.size() * Math.random());
//            int cityIndex2 = (int) (newSolution.size() * Math.random());
//            City city1 = newSolution.get(cityIndex1);
//            City city2 = newSolution.get(cityIndex2);
//            newSolution.set(cityIndex1, city2);
//            newSolution.set(cityIndex2, city1);

            // Calculate the cost (distance) of the new solution
            double currentCost = calculateTotalDistanceAnnealing(currentSolution);
            //double newCost = calculateTotalDistanceAnnealing(newSolution);

            // Decide whether to accept the new solution based on the cost and temperature
            if (acceptanceProbability(currentCost, newCost, temperature) > Math.random()) {
                currentSolution = new ArrayList<>(newSolution);
            }

            // Update the best solution found so far
            if (calculateTotalDistanceAnnealing(currentSolution) < calculateTotalDistanceAnnealing(bestSolution)) {
                bestSolution = new ArrayList<>(currentSolution);
            }

            // Cool down the temperature
            temperature *= (1-coolingRate);
            c++;
        }

        return bestSolution;
    }

    public static double calculateTotalDistanceAnnealing(ArrayList<City> tour) {
        TravellerData td=new TravellerData();
        double totalDistance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalDistance += td.getDistance(tour.get(i), tour.get(i + 1));
        }
        totalDistance += td.getDistance(tour.get(tour.size() - 1), tour.get(0));
        return totalDistance;
    }

    public static double acceptanceProbability(double currentCost, double newCost, double temperature) {
        if (newCost < currentCost) {
            return 1.0;
        }
        return Math.exp((currentCost - newCost) / temperature);
    }

}