package TSPtest;


import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Utils {

        @Test
        public void testFileReading() throws IOException {
            TSP.FileReading fr = new TSP.FileReading();
            String filename = "./src/main/java/crimeSample.csv";
            assertNotEquals(0, fr.readingDataPoints(filename).size());
        }

    @Test
    void testFileHasData(){
        TSP.FileReading fr = new TSP.FileReading();
        String filename = "./src/main/java/crimeSample1.csv";
        assertEquals(585, fr.readingDataPoints(filename).size());
    }
    @Test
    public void testHeaderCSVFile() throws IOException, CsvValidationException {
        String filePath = "./src/main/java/crimeSample1.csv";
        String[] expectedHeader = {"crimeID", "longitude", "latitude"};

        CSVReader csvReader = new CSVReader(new FileReader(filePath));
        String[] header = csvReader.readNext();

        assertEquals(expectedHeader[0], header[0]);
        assertEquals(expectedHeader[1], header[1]);
        assertEquals(expectedHeader[2], header[2]);
    }
    @Test
    public void testFileDataFormat() throws IOException, CsvValidationException {
        String filePath = "./src/main/java/crimeSample1.csv";
        CSVReader csvReader = new CSVReader(new FileReader(filePath));
        String []parts;

        while (csvReader.readNext() != null) {
            parts = csvReader.readNext();
            assertTrue(parts.length == 3);
        }
    }



    @Test
    public void checkNumeric() throws IOException, CsvValidationException {
        String filePath = "./src/main/java/crimeSample1.csv";
        TSP.FileReading fr = new TSP.FileReading();
        List<String[]> dataPoints = fr.readingDataPoints(filePath);


        int fs = dataPoints.size(), c = 1;
        while (c < fs) {
            assertTrue(dataPoints.get(c)[1].matches("-?\\d+(\\.\\d+)?"));
            assertTrue(dataPoints.get(c)[2].matches("-?\\d+(\\.\\d+)?"));
            c++;
        }
    }



}
