package TSPtest;
import static org.junit.Assert.*;

import TSP.City;
import TSP.SimulatedAnnealing;
import org.junit.Test;
import java.util.ArrayList;

public class SimulatedAnnealingTest {

    // Test calculateTotalDistanceAnnealing() method with a known tour
    @Test
    public void testCalculateTotalDistanceAnnealing() {
        ArrayList<City> tour = new ArrayList<>();
        tour.add(new City("A", 0,0,1));
        tour.add(new City("B", 0,0,2));
        tour.add(new City("C", 0,0,3));
        tour.add(new City("D", 0,0,4));
        double expectedDistance = 4.0;
        double actualDistance = SimulatedAnnealing.calculateTotalDistanceAnnealing(tour);
        assertTrue(actualDistance<expectedDistance);
    }

    // Test acceptanceProbability() method with currentCost = 10, newCost = 20, temperature = 1
    @Test
    public void testAcceptanceProbability() {
        double currentCost = 10.0;
        double newCost = 20.0;
        double temperature = 1.0;
        double expectedProbability = Math.exp((currentCost - newCost) / temperature);
        double actualProbability = SimulatedAnnealing.acceptanceProbability(currentCost, newCost, temperature);
        assertEquals(expectedProbability, actualProbability, 0.0001);
    }

    // Test simulatedAnnealing() method with a known tour and parameters
    @Test
    public void testSimulatedAnnealing() {
        ArrayList<City> tour = new ArrayList<>();
        tour.add(new City("A", 0,0,1));
        tour.add(new City("B", 1,1,2));
        tour.add(new City("C", 2,1,3));
        tour.add(new City("D", 3,1,4));
        double temperature = 100;
        double coolingRate = 0.003;
        ArrayList<City> actualSolution = SimulatedAnnealing.simulatedAnnealing(tour, temperature, coolingRate);
        assertEquals(tour.size(), actualSolution.size());
        for (City city : tour) {
            assertTrue(actualSolution.contains(city));
        }
    }

}
