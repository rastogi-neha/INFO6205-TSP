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
            int e=0;

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

            Graph g = new Graph(v,e);
            Edge [] primsResult = g.getPrimMST(distanceCostMatrix,v) ;

            Edge [] mst = g.findAndAddPerfectMatches(primsResult,cities);

            //creating new graph for our cyclic mst
            Graph mg = new Graph(v);

            for(int i=1; i<mst.length; i++) {
                mg.addEdge(mst[i].src, mst[i].dest);
            }

            //creating euler cycle from cyclic mst
            mg.eulerianCycle();

            ArrayList<Integer> resultCircuit = mg.clearRepeatedCities(mg.eulerianCircuit);

            //calculating path distance
            int totalDistance = calculateTotalDistance(resultCircuit, distanceCostMatrix);
            System.out.println(totalDistance);

            //algos to be implemented
        }

    }

    public static int calculateTotalDistance(ArrayList<Integer> resultCircuit, double distanceMatrix[][]) {
        int sum = 0;
        int counter = 0;

        while(counter < resultCircuit.size()-1) {
            sum += distanceMatrix[resultCircuit.get(counter)][resultCircuit.get(counter+1)];
            counter++;
        }
        return sum;
    }
}