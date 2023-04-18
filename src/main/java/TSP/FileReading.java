package TSP;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.List;

//This class is used to read data from a file supplied
public class FileReading {

    public static List<String[]> readingDataPoints(String file){

        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            return allData;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
