package TSPtest;

import static org.junit.Assert.*;
import java.util.ArrayList;

import TSP.City;
import TSP.ThreeOpt;
import org.junit.Test;

public class ThreeOptTest {

    @Test
    public void testDo3Opt() {
        ArrayList<City> tspTour = new ArrayList<>();
        tspTour.add(new City("A", 0,0,1));
        tspTour.add(new City("B", 0,0,2));
        tspTour.add(new City("C", 0,0,3));
        tspTour.add(new City("D", 0,0,4));
        tspTour.add(new City("E", 0,0,5));
        tspTour.add(new City("F", 0,0,6));
        double actual = ThreeOpt.reverseSegmentIfBetter(tspTour, 2, 4, 6);
        double expected = 0.0;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testDo3Opt2() {
        ArrayList<City> tspTour = new ArrayList<>();
        tspTour.add(new City("A", 0,0,1));
        tspTour.add(new City("B", 1,1,2));
        tspTour.add(new City("C", 2,2,3));
        tspTour.add(new City("D", 3,3,4));
        tspTour.add(new City("E", 4,4,5));
        tspTour.add(new City("F", 5,5,6));
        double actual = ThreeOpt.reverseSegmentIfBetter(tspTour, 2, 4, 6);
        double expected = 0.0;
        assertEquals(expected, actual, 0.001);
    }
}