import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileReading fr=new FileReading();
        List<String[]> dataPoints = fr.readingDataPoints("./src/main/java/crimeSample.csv");

        if(dataPoints.size()==0)
            System.out.println("Error");

        else{
            TravellerData td=new TravellerData();

            List<City> cities = td.putCitiesInList(dataPoints);
            int v=td.getNumberOfCities();
            int e;

            //distanceMatrix computed
            double[][] distanceCostMatrix=new double[v][v];
            for(int i=0;i<v;i++){
                for(int j=0;j<v;j++){
                    if(i!=j){
                        distanceCostMatrix[i][j]=distanceCostMatrix[j][i]= td.getDistance(cities.get(i),cities.get(j));
                    }
                    else
                        distanceCostMatrix[i][i]=Integer.MAX_VALUE;
                }
            }

            Graph g = new Graph(v);
            Edge [] primsResult = g.getPrimMST(distanceCostMatrix,v) ;


            //System.out.println("hey");

            //algos to be implemented

        }

    }
}