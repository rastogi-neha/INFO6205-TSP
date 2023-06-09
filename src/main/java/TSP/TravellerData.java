package TSP;

import org.apache.lucene.util.SloppyMath;

import java.util.*;

//This class generates a list of all cities to be traversed by the salesman
public class TravellerData{

    private static List<City> cities = new ArrayList<>();

    public static void addCity(City city){
        cities.add(city);
    }

    public static City getCity(int i){
        return cities.get(i);
    }

    public static List<City> putCitiesInList(List<String[]> data){

        int c=0;
        for (String[] row : data) {
            City city=new City(row[0],Double.parseDouble(row[1]),Double.parseDouble(row[2]),c);
            cities.add(city);
            c++;
        }
//        for(TSP.City c:cities){
//            System.out.println(c.toString());
//        }
        return cities;
   }

    public static int getNumberOfCities(){
        return cities.size();
    }

    public static double getDistance(City toCity, City fromCity){
      //Euclidean distance
//        double xDistance = Math.abs (fromCity.getX()- toCity.getX());
//        double yDistance = Math.abs (fromCity.getY()- toCity.getY());
//        double distance = Math.sqrt(Math.pow(xDistance,2)+Math.pow(yDistance,2));
        //Haversin distance
        return SloppyMath.haversinMeters(toCity.getY(),toCity.getX(),fromCity.getY(),fromCity.getX());
    }
}