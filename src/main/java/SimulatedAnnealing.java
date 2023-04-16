

import java.util.ArrayList;

public class SimulatedAnnealing {

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
            double currentCost = calculateTotalDistance(currentSolution, td);
            double newCost = calculateTotalDistance(newSolution, td);

            // Decide whether to accept the new solution based on the cost and temperature
            if (acceptanceProbability(currentCost, newCost, temperature) > Math.random()) {
                currentSolution = new ArrayList<>(newSolution);
            }

            // Update the best solution found so far
            if (calculateTotalDistance(currentSolution, td) < calculateTotalDistance(bestSolution, td)) {
                bestSolution = new ArrayList<>(currentSolution);
            }

            // Cool down the temperature
            temperature *= coolingRate;
        }

        return bestSolution;
    }

    private static double calculateTotalDistance(ArrayList<City> tour, TravellerData td) {
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