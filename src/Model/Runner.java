package Model;

public class Runner {

    private String name;
    private int number;
    private double speed;    // pixels per second (or any unit you choose)
    private double distance; // distance covered so far

    public Runner(String name, int number) {
        this.name = name;
        this.number = number;
    }

    // Basic getters
    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    // Basic setters
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // TODO: Add methods for updating distance, resetting, etc.
}
