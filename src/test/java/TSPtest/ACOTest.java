package TSPtest;
import TSP.AntColony;
import TSP.City;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ACOTest {

    // Test constructor
    @Test
    public void testConstructor() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("A", 1.0, 1.0,1));
        cities.add(new City("B", 2.0, 2.0,2));
        double[][] distanceCostMatrix = {
                {0, 1},
                {1, 0}
        };
        AntColony ac = new AntColony(cities, 5, 1.0, 1.0, 0.5, 1.0, 1.0, 100, 0, distanceCostMatrix);
        assertEquals(cities, ac.getCities());
        assertEquals(5, ac.getNumberOfAnts());
        assertEquals(1.0, ac.getAlpha(), 0.0);
        assertEquals(1.0, ac.getBeta(), 0.0);
        assertEquals(0.5, ac.getEvaporationRate(), 0.0);
        assertEquals(1.0, ac.getQ(), 0.0);
        assertEquals(1.0, ac.getInitialPheromone(), 0.0);
        assertEquals(100, ac.getMaxIterations());
        assertEquals(distanceCostMatrix, ac.getDistanceMatrix());
        assertNotNull(ac.getRandom());
        assertEquals(Double.POSITIVE_INFINITY, ac.getBestTourLength(), 0.0);
        assertNull(ac.getBestTour());
    }

    // Test the calculation of the probabilities
    @Test
    public void testCalculateProbabilities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("A", 0, 0,1));
        cities.add(new City("B", 1, 1,2));
        cities.add(new City("C", 2, 2,3));
        double[][] distanceCostMatrix = {
                {0, 1, 2},
                {1, 0, 1},
                {2, 1, 0}
        };
        AntColony ac = new AntColony(cities, 5, 1.0, 1.0, 0.5, 1.0, 1.0, 100, 0, distanceCostMatrix);
        boolean[] visited = new boolean[cities.size()];
        visited[0] = true;
        double[] probabilities = ac.calculateProbabilities(0, visited);
        double expectedSum = Math.pow(1.0, 1.0) / 1.0 + Math.pow(1.0, 1.0 / Math.sqrt(2.0)) / 1.414 + Math.pow(1.0, 1.0 / Math.sqrt(2.0)) / 1.414;
        double actualSum=Arrays.stream(probabilities).sum();
        assertTrue(expectedSum>actualSum);
    }

    // Test the selection of the next city
//    @Test
//    public void testSelectNextCity() {
//        List<City> cities = new ArrayList<>();
//        cities.add(new City("A", 0, 0));
//        cities.add(new City("B", 1, 1));
//        cities.add(new City("C", 2, 2));
//        double[][] distanceCostMatrix = {
//                {0, 1, 2},
//                {1, 0, 1},
//                {2, 1, 0}
//        };
//        AntColony ac = new AntColony(cities, 5, 1.0,
//
//    }
}
