import java.awt.*;
import java.util.*;

/**
 * Represents a location on the map
 * Contains obstacle and cost data
 * Also lets you set the path to it
 * 
 * @author Thomas Shields
 * @version 2.0 3/19/13
 */

public class Location implements Comparable{
    public double obstacleProbability;
    private double cost;
    private double nonHeuristicCost;
    private ArrayList<Location> pathTo;
    private int x, y;

    /**
     * Constructor for a Location
     * @param x         the x-coordinate of the location
     * @param y         the y-coordinate of the location
     * @param obstacleProbability   the probability of an obstacle at location
     */
    public Location(int x, int y, double obstacleProbability) {
        this.x = x;
        this.y = y;
        this.obstacleProbability = obstacleProbability;
    }

    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the probability of an obstacle at this location
     * @return that probability. Obviously. Why would it be anythign diff?
     */
    public double getObstacleProbability() {
        return obstacleProbability;
    }

    /**
     * Gets the cost
     * @return the cost with the heuristic factored in
     */
    public double getCost() {
        return cost;
    }

    /**
     * Set the cost
     * @param _cost     the cost to set it to
     */
    public void setCost(double _cost) {
        cost = _cost;
    }

    /**
     * Gets the cost without heuristic
     * @return the raw cost, without the heuristic
     */
    public double getNonHeuristicCost() {
        return nonHeuristicCost;
    }

    /**
     * Set the non heuristic cost
     * @param _cost     the cost to set it to
     */    public void setNonHeuristicCost(double _cost) {
        nonHeuristicCost = _cost;
    }

    /**
     * gets the path to this location
     * @return the path to this location
     */
    public ArrayList<Location> getPathTo() {
        return (ArrayList<Location>)pathTo.clone();
    }

    /**
     * Sets the path to this location
     * @param path      The path
     */
    public void setPathTo(ArrayList<Location> path) {
        pathTo = path;
    }

    /**
     * Checks equality yo
     * @param obj       the object to check with
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Location ? 
                    x == ((Location)obj).x && y == ((Location)obj).y
                    : false;
    }

    /**
     * Compares two objects yo
     * @param other     the other thing obviously
     * @return an integer comparison
     */
    @Override
    public int compareTo(Object other) {
        return other instanceof Location ? 
                    (int)((cost - ((Location)other).cost)*10)
                    : Integer.MAX_VALUE;
    }
}