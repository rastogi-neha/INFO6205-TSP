package TSPtest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import TSP.City;
import TSP.TravellerData;
import TSP.TwoOpt;
import org.junit.Test;

public class TwoOptTest {

    @Test
    public void testDo2Opt() {
        // Test case 1: Reverse a sub-list of size 3
        ArrayList<City> tour1 = new ArrayList<>();
        tour1.add(new City("A", 0,0,1));
        tour1.add(new City("B", 0,0,2));
        tour1.add(new City("C", 0,0,3));
        tour1.add(new City("D", 0,0,4));
        TwoOpt twoOpt = new TwoOpt();
        twoOpt.do2Opt(tour1, 1, 2);
        ArrayList<City> expected1 = new ArrayList<>();
        expected1.add(new City("A", 0,0,1));
        expected1.add(new City("B", 0,0,2));
        expected1.add(new City("C", 0,0,3));
        expected1.add(new City("D", 0,0,4));
        assertEquals(expected1.size(), tour1.size());

        // Test case 2: Reverse a sub-list of size 2
        ArrayList<City> tour2 = new ArrayList<>();
        tour2.add(new City("A", 0,0,1));
        tour2.add(new City("B", 0,0,2));
        tour2.add(new City("C", 0,0,3));
        twoOpt.do2Opt(tour2, 0, 1);
        ArrayList<City> expected2 = new ArrayList<>();
        expected2.add(new City("A", 0,0,1));
        expected2.add(new City("B", 0,0,2));
        expected2.add(new City("C", 0,0,3));
        assertEquals(expected2.size(), tour2.size());

        // Test case 3: Reverse the entire tour
        ArrayList<City> tour3 = new ArrayList<>();
        tour3.add(new City("A", 0,0,1));
        tour3.add(new City("B", 0,0,2));
        tour3.add(new City("C", 0,0,3));
        tour3.add(new City("D", 0,0,4));
        twoOpt.do2Opt(tour3, 0, 3);
        ArrayList<City> expected3 = new ArrayList<>();
        expected3.add(new City("A", 0,0,1));
        expected3.add(new City("B", 0,0,2));
        expected3.add(new City("C", 0,0,3));
        expected3.add(new City("D", 0,0,4));
        assertEquals(expected3.size(), tour3.size());
    }

    @Test
    public void testTwoOptimization() {
        // Test case 1: Tour of size 4, optimal tour
        ArrayList<City> tour1 = new ArrayList<>();
        tour1.add(new City("A", 0,0,1));
        tour1.add(new City("B", 0,0,2));
        tour1.add(new City("C", 0,0,3));
        tour1.add(new City("D", 0,0,4));
        TravellerData td = new TravellerData();
        double currentDistance1 = td.getDistance(tour1.get(0), tour1.get(1)) + td.getDistance(tour1.get(1), tour1.get(2)) + td.getDistance(tour1.get(2), tour1.get(3)) + td.getDistance(tour1.get(3), tour1.get(0));
        TwoOpt twoOpt = new TwoOpt();
        double optimizedDistance1 = twoOpt.twoOptimization(tour1, currentDistance1);
        assertEquals(currentDistance1, optimizedDistance1, 0.001);
    }
}
