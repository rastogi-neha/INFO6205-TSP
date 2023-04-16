import java.util.*;
public class ThreeOpt {

    public static double reverseSegmentIfBetter(ArrayList<City> tspTour, int i, int j, int k) {
        // Given tour [...A-B...C-D...E-F...]
        TravellerData td=new TravellerData();
        City A = tspTour.get(i-1), B = tspTour.get(i), C = tspTour.get(j-1), D = tspTour.get(j), E = tspTour.get(k-1), F = tspTour.get(k % tspTour.size());
        double d0 = td.getDistance(A, B) + td.getDistance(C, D) + td.getDistance(E, F);
        double d1 = td.getDistance(A, C) + td.getDistance(B, D) + td.getDistance(E, F);
        double d2 = td.getDistance(A, B) + td.getDistance(C, E) + td.getDistance(D, F);
        double d3 = td.getDistance(A, D) + td.getDistance(E, B) + td.getDistance(C, F);
        double d4 = td.getDistance(F, B) + td.getDistance(C, D) + td.getDistance(E, A);

        if (d0 > d1) {
            Collections.reverse(tspTour.subList(i, j));
            return -d0 + d1;
        } else if (d0 > d2) {
            Collections.reverse(tspTour.subList(j, k));
            return -d0 + d2;
        } else if (d0 > d4) {
            Collections.reverse(tspTour.subList(i, k));
            return -d0 + d4;
        } else if (d0 > d3) {
            List<City> tmp = new ArrayList<>(tspTour.subList(j, k));
            tmp.addAll(tspTour.subList(i, j));
            tspTour.subList(i, k).clear();
            tspTour.addAll(i, tmp);
            return -d0 + d3;
        }
        return 0;
    }

    public static ArrayList<City> threeOpt(ArrayList<City> tspTour) {
        // Iterative improvement based on 3 exchange.
        while (true) {
            double delta = 0;
            for (int[] segment : allSegments(tspTour.size())) {
                delta += reverseSegmentIfBetter(tspTour, segment[0], segment[1], segment[2]);
            }
            if (delta >= 0) {
                break;
            }
        }
        return tspTour;
    }

    public static ArrayList<int[]> allSegments(int n) {
        // Generate all segment combinations.
        ArrayList<int[]> segments = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            for (int j = i + 2; j < n-2; j++) {
                for (int k = j + 2; k < n-4; k++) {
                    segments.add(new int[]{i, j, k});
                }
            }
        }
        return segments;
    }

}
