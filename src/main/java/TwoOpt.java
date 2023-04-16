import java.util.ArrayList;
import java.util.Collections;

public class TwoOpt {

    void do2Opt(ArrayList<City> tspTour, int i, int j) {
        //reverse(begin(tspTour) + i + 1, begin(tspTour) + j + 1);
        Collections.reverse(tspTour.subList(i+1,j+1));
    }

    double twoOptimization(ArrayList<City> tspTour,double currentDistance){
        TravellerData td=new TravellerData();
        int n=tspTour.size();
        boolean foundImprovement = true;
        while (foundImprovement) {
            foundImprovement = false;
            for (int i = 0; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    //int lengthDelta = -tspTour[i].dist2(tspTour[(i + 1) % n]) - tspTour[j].dist2(tspTour[(j + 1) % n]) + tspTour[i].dist2(tspTour[j]) + tspTour[(i + 1) % n].dist2(tspTour[(j + 1) % n]);
                    //double a=-td.getDistance(tspTour.get(i),tspTour.get((i + 1) % n));
                    //double b=-td.getDistance(tspTour.get(j),tspTour.get((j + 1) % n));
                    //double c=-td.getDistance(tspTour.get(i),tspTour.get(j));
                    //double d=-td.getDistance(tspTour.get(((i + 1) % n)),tspTour.get((j + 1) % n));
                    double lengthDelta = -td.getDistance(tspTour.get(i),tspTour.get((i + 1) % n)) - td.getDistance(tspTour.get(j),tspTour.get((j + 1) % n)) + td.getDistance(tspTour.get(i),tspTour.get(j)) + td.getDistance(tspTour.get((i+1)%n),tspTour.get((j + 1) % n));
                    // If the length of the path is reduced, do a 2-opt swap
                    if (lengthDelta < -1) {
                        do2Opt(tspTour, i, j);
                        currentDistance += lengthDelta;
                        foundImprovement = true;
//                        System.out.println(i+" "+j);
                    }
                }
            }
        }
        return currentDistance;
    }
}
