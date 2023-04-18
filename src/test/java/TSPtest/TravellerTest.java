package TSPtest;

import TSP.City;
import TSP.TravellerData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TravellerTest {

    @Test
    public void testGetDistance() {
        City city1 = new City("dbffa8d35a80dc6e57be4a06d815498980c5ed3f3306824ffbfe7dfa222f937f", 0.015471,51.399233, 42);
        City city2 = new City("70316c1193e2d1936b118e14c007de361a230c378e2971401208bcbda8bc59bd",-0.364328,51.46912,67);

        double expectedDistance = 27450.75047603352; // Haversine distance between New York and Los Angeles

        double actualDistance = TravellerData.getDistance(city1, city2);

        Assertions.assertEquals(expectedDistance, actualDistance, 0.1); // 0.1 tolerance to account for possible rounding errors
    }
    @Test
    public void testAddCity() {
        City city = new City("6a49ccac8c508acb24e193b73ee7351e70eb8d4ccd0fa467e84c78899126ed74", -0.167888, 51.497988, 100);
        TravellerData.addCity(city);
        Assertions.assertEquals(city, TravellerData.getCity(0));
    }

    @Test
    public void testGetNumberOfCities() {
        Assertions.assertEquals(1, TravellerData.getNumberOfCities());
    }
}

