package TSPtest;

import static org.junit.Assert.*;

import TSP.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    private double[][] distanceCostMatrix;
    private List<City> cities;
    private ArrayList<Integer> resultCircuit;

    @Before
    public void setUp() throws Exception {
        FileReading fr=new FileReading();
        List<String[]> dataPoints = fr.readingDataPoints("./src/main/java/crimeSample1.csv");

        TravellerData td=new TravellerData();

        cities = td.putCitiesInList(dataPoints);
        int v=td.getNumberOfCities();

        //distanceMatrix computed
        distanceCostMatrix=new double[v][v];
        for(int i=0;i<v;i++){
            for(int j=0;j<v;j++){
                if(i!=j){
                    distanceCostMatrix[i][j]=distanceCostMatrix[j][i]= td.getDistance(cities.get(i),cities.get(j));
                }
                else
                    distanceCostMatrix[i][i]=Integer.MAX_VALUE;
            }
        }

        //Creating Multigraph for combining MST with perfect edges
        Graph mg = new Graph(v);

        mg.addEdge(0, 1);
        mg.addEdge(1, 2);
        mg.addEdge(2, 0);
        mg.addEdge(2, 3);
        mg.addEdge(3, 4);
        mg.addEdge(4, 2);

        //Euler Cycle from Multigraph
        mg.eulerianCycle();
        //Remove repeated cities and get Hamiltonian Cycle
        resultCircuit = mg.clearRepeatedCities(mg.eulerianCircuit);
    }

    @Test
    public void testPostProcessing() {
        ArrayList<City> expected = new ArrayList<>();
        expected.add(new City( "CHI01", 41.89593064, -87.63750018,0));
        expected.add(new City("CHI02", 41.78794306, -87.62249877,1));
        expected.add(new City("CHI03", 41.89192223, -87.61146198,2));
        expected.add(new City( "CHI04", 41.74785333, -87.74011977,3));
        expected.add(new City( "CHI05", 41.81192945, -87.68732082,4));
        ArrayList<City> actual = Main.postProcessing(resultCircuit, cities);
        assertEquals(expected.size(), actual.size()-1);//since PostProcessing returns a tour so starting point is included twice

    }

}
