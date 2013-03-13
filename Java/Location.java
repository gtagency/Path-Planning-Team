import java.awt.*;
import java.util.*;

public class Location implements Comparable{
    public final Point point;
    public final double OBSTACLE_PROBABILITY;
    private double cost;
    private double nonHeuristicCost;
    private ArrayList<Location> pathTo;

    public Location(Point point, double OBSTACLE_PROBABILITY) {
        this.point = point;
        this.OBSTACLE_PROBABILITY = OBSTACLE_PROBABILITY;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double _cost) {
        cost = _cost;
    }

    public double getNonHeuristicCost() {
        return nonHeuristicCost;
    }

    public void setNonHeuristicCost(double _cost) {
        nonHeuristicCost = _cost;
    }

    public int compareTo(Object other) {
        return (int)((cost - ((Location)other).cost)*10);
    }

    public ArrayList<Location> getPathTo() {
        return (ArrayList<Location>)pathTo.clone();
    }
    public void setPathTo(ArrayList<Location> path) {
        pathTo = path;
    }

    public boolean equals(Object obj) {
        return ((Location)obj).point.equals(point);
    }
}