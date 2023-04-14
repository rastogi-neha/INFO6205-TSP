
public class City{

    private String cityName;
    private double x;
    private double y;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public City(String cityName, double x, double y) {
        this.cityName = cityName;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }



}