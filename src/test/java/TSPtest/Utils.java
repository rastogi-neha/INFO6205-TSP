package TSPtest;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Utils {
    @Test
    void testFileHasData(){
        TSP.FileReading fr = new TSP.FileReading();
        String filename = "./src/main/java/crimeSample1.csv";
        assertNotEquals(0, fr.readingDataPoints(filename).size());
        //Equals(0, fr.readingDataPoints(filename).size());
    }

}