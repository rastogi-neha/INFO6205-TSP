package TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AntColony {
    private final List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public double[][] getPheromoneMatrix() {
        return pheromoneMatrix;
    }

    public Random getRandom() {
        return random;
    }

    public int getNumberOfAnts() {
        return numberOfAnts;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getEvaporationRate() {
        return evaporationRate;
    }

    public double getQ() {
        return Q;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getInitialPheromone() {
        return initialPheromone;
    }

    public double getBestTourLength() {
        return bestTourLength;
    }

    public void setBestTourLength(double bestTourLength) {
        this.bestTourLength = bestTourLength;
    }

    public ArrayList<Integer> getBestTour() {
        return bestTour;
    }

    public void setBestTour(ArrayList<Integer> bestTour) {
        this.bestTour = bestTour;
    }

    private final int numberOfCities;
    private final double[][] distanceMatrix;
    private final double[][] pheromoneMatrix;
    private final Random random;

    private final int numberOfAnts;
    private final double alpha;
    private final double beta;
    private final double evaporationRate;
    private final double Q;
    private final int maxIterations;
    private final double initialPheromone;
    private double bestTourLength;
    private ArrayList<Integer> bestTour;

    public AntColony(List<City> cities, int numberOfAnts, double alpha, double beta,
                     double evaporationRate, double Q, double initialPheromone, int maxIterations, long seed, double[][] distanceCostMatrix) {
        this.cities = cities;
        this.numberOfCities = cities.size();
        this.distanceMatrix = distanceCostMatrix;
        this.pheromoneMatrix = initializePheromoneMatrix(initialPheromone, numberOfCities);
        this.numberOfAnts = numberOfAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.Q = Q;
        this.initialPheromone = initialPheromone;
        this.maxIterations = maxIterations;
        this.random = new Random(seed);
        this.bestTourLength = Double.POSITIVE_INFINITY;
        this.bestTour = null;
    }

    public void run() {
        for (int i = 0; i < maxIterations; i++) {
            ArrayList<ArrayList<Integer>> antTours = new ArrayList<>();
            ArrayList<Double> tourLengths = new ArrayList<>();
            for (int j = 0; j < numberOfAnts; j++) {
                ArrayList<Integer> tour = constructTour();
                double tourLength = calculateTourLength(tour);
                antTours.add(tour);
                tourLengths.add(tourLength);
                updatePheromone(tour, tourLength);
                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = new ArrayList<>(tour);
                }
            }
            //System.out.println("Iteration " + (i + 1) + " - Best tour length: " + bestTourLength);
            updatePheromoneEvaporation();
        }
        System.out.println("Best tour length: " + bestTourLength);
        //System.out.println("Best tour: " + bestTour);
    }

    private ArrayList<Integer> constructTour() {
        int startingCityIndex = random.nextInt(numberOfCities);
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(startingCityIndex);
        boolean[] visited = new boolean[numberOfCities];
        visited[startingCityIndex] = true;
        for (int i = 1; i < numberOfCities; i++) {
            int currentCityIndex = tour.get(i - 1);
            double[] probabilities = calculateProbabilities(currentCityIndex, visited);
            int nextCityIndex = selectNextCity(probabilities);
            tour.add(nextCityIndex);
            visited[nextCityIndex] = true;
        }
        return tour;
    }

    public double[] calculateProbabilities(int currentCityIndex, boolean[] visited) {
        double[] probabilities = new double[numberOfCities];
        double probabilitiesSum = 0;
        for (int i = 0; i < numberOfCities; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromoneMatrix[currentCityIndex][i], alpha)
                        * Math.pow(1 / distanceMatrix[currentCityIndex][i], beta);
                probabilitiesSum += probabilities[i];
            }
        }
        for (int i = 0; i < numberOfCities; i++) {
            if (!visited[i]) {
                probabilities[i] /= probabilitiesSum;
            }
        }
        return probabilities;
    }


    public int selectNextCity(double[] probabilities) {
        double randomValue = random.nextDouble();
        double probabilitiesSum = 0;
        for (int i = 0; i < numberOfCities; i++) {
            probabilitiesSum += probabilities[i];
            if (probabilitiesSum >= randomValue) {
                return i;
            }
        }
        // If the loop completes without returning a city, select the last one
        return numberOfCities - 1;
    }


    public void updatePheromoneEvaporation() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromoneMatrix[i][j] = pheromoneMatrix[i][j] * (1 - evaporationRate);
            }
        }
    }

    public double[][] initializePheromoneMatrix(double initialPheromone, int numberOfCities) {
        double[][] pheromoneMatrix = new double[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = i + 1; j < numberOfCities; j++) {
                pheromoneMatrix[i][j] = initialPheromone;
                pheromoneMatrix[j][i] = initialPheromone;
            }
        }
        return pheromoneMatrix;
    }

    public double calculateTourLength(ArrayList<Integer> tour) {
        double tourLength = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int cityIndex1 = tour.get(i);
            int cityIndex2 = tour.get(i + 1);
            tourLength += distanceMatrix[cityIndex1][cityIndex2];
        }
        int lastCityIndex = tour.get(tour.size() - 1);
        int firstCityIndex = tour.get(0);
        tourLength += distanceMatrix[lastCityIndex][firstCityIndex];
        return tourLength;
    }
    public void updatePheromone(ArrayList<Integer> tour, double tourLength) {
        double pheromoneToAdd = Q / tourLength;
        for (int i = 0; i < numberOfCities - 1; i++) {
            int city1 = tour.get(i);
            int city2 = tour.get(i + 1);
            pheromoneMatrix[city1][city2] = (1 - evaporationRate) * pheromoneMatrix[city1][city2] + evaporationRate * pheromoneToAdd;
            pheromoneMatrix[city2][city1] = (1 - evaporationRate) * pheromoneMatrix[city2][city1] + evaporationRate * pheromoneToAdd;
        }
        int lastCity = tour.get(numberOfCities - 1);
        int firstCity = tour.get(0);
        pheromoneMatrix[lastCity][firstCity] = (1 - evaporationRate) * pheromoneMatrix[lastCity][firstCity] + evaporationRate * pheromoneToAdd;
        pheromoneMatrix[firstCity][lastCity] = (1 - evaporationRate) * pheromoneMatrix[firstCity][lastCity] + evaporationRate * pheromoneToAdd;
    }


}

