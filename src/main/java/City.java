
public class City{

    private String cityName;
    private double x;
    private double y;
    private int index;

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public City(String cityName, double x, double y,int index) {
        this.cityName = cityName;
        this.x = x;
        this.y = y;
        this.index = index;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", index=" + index +
                '}';
    }
}