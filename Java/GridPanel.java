import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GridPanel extends JPanel {
    private Location[][] grid;
    private int height, width;
    private ArrayList<Location> path;
    private ArrayList<Location> currentPath;
    private final int MULTIPLIER = 20;


    /**
     * Sets up a grid panel and all, ya know?
     * @param rows      The number of rows for this grid panel thingy
     * @param cols      The number of columns. I hate javadocs.
     */
    public GridPanel(int rows, int cols) {

        //multiply by MULTIPLIER for better visualization
        height = MULTIPLIER*rows;
        width = MULTIPLIER*cols;
        Random costGenerator = new Random();
        grid = new Location[rows][cols];
        for(int x=0;x<grid.length;x++) {
            for(int y=0;y<grid[x].length;y++) {
                grid[x][y] = new Location(x,y, (costGenerator.nextInt(8) + 3));
            }
        }
        grid[rows-1][cols-1] = new Location(rows-1, cols-1, 1);


        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(MULTIPLIER*(rows+1),
                                        MULTIPLIER*(cols+1)));
        jframe.getContentPane().add(this);
        jframe.pack();
        jframe.setVisible(true);

        path = new ArrayList<Location>();
        aStar(grid[0][0], grid[rows-1][cols-1]);
       // DFS(grid[0][0], grid[rows-1][cols-1]);
       // BFS(grid[0][0], grid[rows-1][cols-1]);
    }

    /**
     * PAINT ALL THE THINGS
     */
    public void paint(Graphics g) {

        // paint the map
        g.clearRect(0, 0, width, height);

        g.setColor(new Color(0,0,0));
        for(int x=0;x<grid.length;x++) {
            for(int y=0;y<grid[x].length;y++) {
                g.fillOval(MULTIPLIER*x, MULTIPLIER*y, 
                    (int)(15*grid[x][y].getObstacleProbability()/10.0), 
                    (int)(15*grid[x][y].getObstacleProbability()/10.0));
            }
        }

        // uncomment to paint all paths tried
        // g.setColor(Color.BLUE);
        // for(int i=0;i<path.size()-1;i++) {
        //     int x1 = path.get(i).getX(),
        //         y1 = path.get(i).getY(),
        //         x2 = path.get(i+1).getX(),
        //         y2 = path.get(i+1).getY();

        //     g.drawLine(MULTIPLIER*x1, MULTIPLIER*y1, MULTIPLIER*x2, MULTIPLIER*y2);
        //     g.fillOval(MULTIPLIER*x1, MULTIPLIER*y1, 
        //         (int)(15*grid[x1][y1].getObstacleProbability()/10.0), 
        //         (int)(15*grid[x1][y1].getObstacleProbability()/10.0));
        //     g.fillOval(MULTIPLIER*x2, MULTIPLIER*y2, 
        //         (int)(15*grid[x2][y2].getObstacleProbability()/10.0), 
        //         (int)(15*grid[x2][y2].getObstacleProbability()/10.0));

        // }


        // paint last path in red
        g.setColor(Color.RED);
        for(int i=0;i<currentPath.size()-1;i++) {
            int x1 = currentPath.get(i).getX(),
                y1 = currentPath.get(i).getY(),
                x2 = currentPath.get(i+1).getX(),
                y2 = currentPath.get(i+1).getY();

            g.drawLine(MULTIPLIER*x1, MULTIPLIER*y1, 
                        MULTIPLIER*x2, MULTIPLIER*y2);
            g.fillOval(MULTIPLIER*x1, MULTIPLIER*y1, 
                (int)(15*grid[x1][y1].getObstacleProbability()/10.0), 
                (int)(15*grid[x1][y1].getObstacleProbability()/10.0));
            g.fillOval(MULTIPLIER*x2, MULTIPLIER*y2, 
                (int)(15*grid[x2][y2].getObstacleProbability()/10.0), 
                (int)(15*grid[x2][y2].getObstacleProbability()/10.0));

        }
    }

    /** 
     * Heuristic between two things
     */
    public int heuristic(Location start, Location end) {
        return (int)(Math.sqrt(Math.pow(start.getX() - end.getX(),2) 
                    + Math.pow(start.getY() - end.getY(),2)));
    }

    /**
     * a* all the things!
     */
    public ArrayList<Location> aStar(Location start, Location end) {
        // array of visited nodes
        ArrayList<Location> visited = new ArrayList<Location>();

        //pq for nodes yet to be examined
        PriorityQueue<Location> nodes = new PriorityQueue<Location>();

        //the best place to begin is at the beginning
        nodes.add(start);

        //likewise, the best path is the one that starts at the start
        path.clear();
        path.add(start);
        //set the path on start
        start.setPathTo((ArrayList<Location>)path.clone());
        currentPath = (ArrayList<Location>)start.getPathTo().clone();

        //set the cost (without a heuristic)
        start.setNonHeuristicCost(start.getObstacleProbability());
        while(!nodes.isEmpty()) {
            Location current = nodes.poll();
            visited.add(current);
            path.add(current);
            currentPath = (ArrayList<Location>)current.getPathTo().clone();
            if(current.equals(end)) {
                System.out.println("Success from if statement");
                // path.add(current);
                repaint();
                return path;
            }
            ArrayList<Location> children = getNodeChildren(current, visited, 
                                            new ArrayList(nodes));
            for(Location child : children) {
                child.setCost(child.getObstacleProbability() 
                               + current.getNonHeuristicCost()
                                + heuristic(child, end)
                                );

                child.setNonHeuristicCost(child.getObstacleProbability() 
                                + current.getNonHeuristicCost()
                                // + heuristic(child, end)
                                );

                ArrayList<Location> childPath = 
                            ((ArrayList<Location>)current.getPathTo().clone());
                childPath.add(child);
                child.setPathTo((ArrayList<Location>)childPath.clone());
                nodes.add(child);
            }
            repaint();
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException ex) {
                System.out.println("Yo dawg quit crashing the program");
            }
        }
        repaint();
        System.out.println("Success");
        return path;
    }


    private ArrayList<Location> getNodeChildren(Location node, 
            ArrayList<Location> alreadyVisited, ArrayList<Location> stack) {
        ArrayList<Location> result = new ArrayList<Location>();
        int x = node.getX(),
            y = node.getY();
        result = addLocation(x-1,y-1, result, alreadyVisited, stack);
        result = addLocation(x-1,y, result, alreadyVisited, stack);
        result = addLocation(x-1,y+1, result, alreadyVisited, stack);
        result = addLocation(x,y-1, result, alreadyVisited, stack);
        result = addLocation(x,y+1, result, alreadyVisited, stack);
        result = addLocation(x+1,y-1, result, alreadyVisited, stack);
        result = addLocation(x+1,y, result, alreadyVisited, stack);
        result = addLocation(x+1,y+1, result, alreadyVisited, stack);
        return result;
    }

    private ArrayList<Location> addLocation(int x, int y, 
        ArrayList<Location> nodes, 
        ArrayList<Location> alreadyVisited, 
        ArrayList<Location> stack) {
        if(inGrid(x, y)) {
            Location loc = grid[x][y];
            if(!alreadyVisited.contains(loc) && !stack.contains(loc)) {
                nodes.add(loc);
            }
        }
        return nodes;
    }

    /**
     * Checks to see if a point is in the grid
     * @param x     The x coordinate of the point to check
     * @param y     The y coordinate ...
     * @return true if the point is valid
     */
    private boolean inGrid(int x, int y) {
        return x < grid.length && x >= 0 && y < grid[x].length && y >= 0;
    }


   public static void main(String[] args) {
        GridPanel panel = new GridPanel(30, 30);
    }
}