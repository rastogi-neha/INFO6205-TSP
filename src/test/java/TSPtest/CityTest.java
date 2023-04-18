package TSPtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import TSP.City;
public class CityTest {

    @Test
    public void testCityName() {
        City city = new City("6a49ccac8c508acb24e193b73ee7351e70eb8d4ccd0fa467e84c78899126ed74", -0.167888,51.497988, 100);
        Assertions.assertEquals("6a49ccac8c508acb24e193b73ee7351e70eb8d4ccd0fa467e84c78899126ed74", city.getCityName());
    }
    @Test
    public void testXCoordinate() {
        City city = new City("2071d68f7af15e3c61a50716fb7e9975a6ede5aca151972495fb5124aeeb9aa6", 0.012614,51.433033, 20);
        Assertions.assertEquals(0.012614, city.getX(), 0.0001);
    }
    @Test
    public void testYCoordinate() {
        City city = new City("431c35c09f060084de8b562ad8ca5240808b4364641d8a997c2f5069e10e549d", -0.259035,51.566954, 31);
        Assertions.assertEquals(51.566954, city.getY(), 0.0001);
    }
    @Test
    public void testIndex() {
        City city = new City("868f7dab4cf763f84c4fd108ded5d8d88f88bcc9e31964a21b43d9a4e8475cfc", 0.072534,51.558445, 68);
        Assertions.assertEquals(68, city.getIndex());
    }
    @Test
    public void testToString() {
        City city = new City("dbffa8d35a80dc6e57be4a06d815498980c5ed3f3306824ffbfe7dfa222f937f", 0.015471,51.399233, 42);
        String expected = "TSP.City{cityName='dbffa8d35a80dc6e57be4a06d815498980c5ed3f3306824ffbfe7dfa222f937f', x=0.015471, y=51.399233, index=42}";
        Assertions.assertEquals(expected, city.toString());
    }
}
