package TSPtest;

import TSP.City;
import TSP.Edge;
import TSP.Graph;
import TSP.TravellerData;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class GraphTest {


        @Test
        public void testEulerianCycle() {
            Graph g = new Graph(5);
            g.addEdge(1, 0);
            g.addEdge(0, 2);
            g.addEdge(2, 1);
            g.addEdge(0, 3);
            g.addEdge(3, 4);
            g.eulerianCycle();
            ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(0, 1, 1, 2, 2, 0, 0, 3, 3, 4));
            assertEquals(expected, g.eulerianCircuit);
        }

        @Test
        public void testIsValidNextEdge() {
            Graph g = new Graph(3);
            g.addEdge(0, 1);
            g.addEdge(1, 2);
            g.addEdge(2, 0);
            assertTrue(g.isValidNextEdge(0, 1));
//            assertFalse(g.isValidNextEdge(0, 2));
        }

        @Test
        public void testDfsCount() {
            Graph g = new Graph(5);
            g.addEdge(1, 0);
            g.addEdge(0, 2);
            g.addEdge(2, 1);
            g.addEdge(0, 3);
            g.addEdge(3, 4);
            boolean[] isVisited = new boolean[5];
            assertEquals(5, g.dfsCount(0, isVisited));
        }

        @Test
        public void evenOddDegreeVertices() {
            TravellerData td = new TravellerData();
            ArrayList<City> city = new ArrayList<>();
            city.add(new City("6eafbcb60dd441340fb3213a125781e865fdbf34467340aaf71d73b511e454af", -0.140222, 51.515088, 1));
            city.add(new City("6ed4419b2927cd915d5e225b97e32c9bcaf02908340d8465e7bc1b77e66f68b8", -0.006898, 51.543293, 2));
            city.add(new City("6fd853919aef3af5c63d4aa358464011fc9f3b03908c6260ab45d8881154ee9a", -0.385089, 51.524856, 3));
            city.add(new City("70316c1193e2d1936b118e14c007de361a230c378e2971401208bcbda8bc59bd", -0.364328, 51.46912, 4));
            city.add(new City("708fe876d5b9aa29b27d510bdd2e70be5de9b110292960c36ab93d4100444fb2", 0.035476, 51.608598, 5));
            city.add(new City("709744fa5a25189b26a4f30469a07238b43e6f32baae7b9b78e88d5d127bebb5", -0.344943, 51.57771, 6));
            double[][] distanceMatrix = new double[city.size()][city.size()];
            for (int i = 0; i < city.size(); i++) {
                for (int j = 0; j < city.size(); j++) {
                    if (i != j) {
                        distanceMatrix[i][j] = distanceMatrix[j][i] = td.getDistance(city.get(i), city.get(j));
                    } else
                        distanceMatrix[i][i] = Integer.MAX_VALUE;
                }
            }
            Graph g = new Graph(city.size(), 0);
            Edge[] primsResult = g.getPrimMST(distanceMatrix, city.size());
            Edge[] mst = g.findAndAddPerfectMatches(primsResult, city);

            int[] oddDegreeVerticesOnMST = new int[city.size()];

            for (int i = 1; i < mst.length; i++) {
                int src = mst[i].getSrc();
                int dest = mst[i].getDest();
                oddDegreeVerticesOnMST[src]++;
                oddDegreeVerticesOnMST[dest]++;
            }

            int c=0;
            for (int i = 0; i < oddDegreeVerticesOnMST.length; i++) {
                if(oddDegreeVerticesOnMST[i] % 2 == 1)
                    c++;
            }
            assertTrue(c%2==0);
        }


}
