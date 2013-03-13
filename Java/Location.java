import java.awt.*;
import java.util.*;

public class Location implements Comparable{
    public final Point point;
    public final double OBSTACLE_PROBABILITY;
    private double cost;
    private ArrayList<Location> pathTo;

    public Location(Point point, double OBSTACLE_PROBABILITY) {
        this.point = point;
        this.cost = cost;
        this.OBSTACLE_PROBABILITY = OBSTACLE_PROBABILITY;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double _cost) {
        cost = _cost;
    }

    public int compareTo(Object other) {
        return (int)(cost - ((Location)other).cost);
    }

    public ArrayList<Location> getPathTo() {
        return pathTo;
    }
    public void setPathTo(ArrayList<Location> path) {
        pathTo = path;
    }
}