import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GridPanel extends JPanel {
    private Location[][] grid;
    private int height, width;
    private ArrayList<Location> path;

    public GridPanel(int rows, int cols) {
        height = 20*rows;
        width = 20*cols;
        Random costGenerator = new Random();
        grid = new Location[rows][cols];
        for(int x=0;x<grid.length;x++) {
            for(int y=0;y<grid[x].length;y++) {
                grid[x][y] = new Location(new Point(x,y), (costGenerator.nextInt(10) + 1));
            }
        }
        grid[rows-1][cols-1] = new Location(new Point(rows-1, cols-1), 1);


        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(20*(rows+1), 20*(cols+1)));
        jframe.getContentPane().add(this);
        jframe.pack();
        jframe.setVisible(true);

        path = new ArrayList<Location>();
        aStar(grid[0][0], grid[rows-1][cols-1]);
       // DFS(grid[0][0], grid[rows-1][cols-1]);
       // BFS(grid[0][0], grid[rows-1][cols-1]);
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, width, height);

        g.setColor(new Color(0,0,0));
        for(int x=0;x<grid.length;x++) {
            for(int y=0;y<grid[x].length;y++) {
                g.fillOval(20*x, 20*y, (int)(15*grid[x][y].OBSTACLE_PROBABILITY/10.0), (int)(15*grid[x][y].OBSTACLE_PROBABILITY/10.0));
            }
        }


        g.setColor(Color.RED);
        for(int i=0;i<path.size()-1;i++) {
            int x1 = (int)(path.get(i).point.getX()),
                y1 = (int)(path.get(i).point.getY()),
                x2 = (int)(path.get(i+1).point.getX()),
                y2 = (int)(path.get(i+1).point.getY());

            // System.out.printf("Drawing line from (%d, %d) to (%d, %d)\n", x1, y1, x2, y2);
            g.drawLine(20*x1, 20*y1, 20*x2, 20*y2);
            g.fillOval(20*x1, 20*y1, (int)(15*grid[x1][y1].OBSTACLE_PROBABILITY/10.0), (int)(15*grid[x1][y1].OBSTACLE_PROBABILITY/10.0));
            g.fillOval(20*x2, 20*y2, (int)(15*grid[x2][y2].OBSTACLE_PROBABILITY/10.0), (int)(15*grid[x2][y2].OBSTACLE_PROBABILITY/10.0));

        }
    }
    public int heuristic(Location start, Location end) {
        return 1; //(int)(start.point.distance(end.point));
    }

    public ArrayList<Location> aStar(Location start, Location end) {
        // array of visited nodes
        ArrayList<Location> visited = new ArrayList<Location>();

        //pq for nodes yet to be examined
        PriorityQueue<Location> nodes = new PriorityQueue<Location>();

        //the best place to begin is at the beginning
        nodes.add(start);

        //likewise, the best path is the one that starts at the start
        path.add(start);

        //set the path on start
        start.setPathTo((ArrayList<Location>)path.clone());

        //set the cost (without a heuristic)
        start.setNonHeuristicCost(start.OBSTACLE_PROBABILITY);
        while(!nodes.isEmpty()) {
            Location current = nodes.poll();
            if(current.equals(end)) {
                repaint();
                System.out.println("Success from if statement");
                path.add(current);
                return path;
            }
            visited.add(current);
            ArrayList<Location> children = getNodeChildren(current, visited);
            for(Location child : children) {
                child.setCost(child.OBSTACLE_PROBABILITY 
                                + current.getNonHeuristicCost()
                                + heuristic(child, end)
                                );

                child.setNonHeuristicCost(child.OBSTACLE_PROBABILITY 
                                + current.getNonHeuristicCost()
                                // + heuristic(child, end)
                                );

                path = (ArrayList<Location>)current.getPathTo().clone();
                path.add(child);
                child.setPathTo((ArrayList<Location>)path.clone());
                nodes.add(child);
            }
            repaint();
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException ex) {
                System.out.println("Yo dawg quit crashing the program");
            }
            // long startTime = System.nanoTime(),
            //     now;
            // do {
            //     now = System.nanoTime();
            // }while((now - startTime) < 500000000l);
        }
        repaint();
        System.out.println("Success");
        return path;
    }


    private ArrayList<Location> getNodeChildren(Location node, ArrayList<Location> alreadyVisited) {
        ArrayList<Location> result = new ArrayList<Location>();
        int x = (int)(node.point.getX()),
            y = (int)(node.point.getY());
        result = addLocation(x-1,y-1, result, alreadyVisited);
        result = addLocation(x-1,y, result, alreadyVisited);
        result = addLocation(x-1,y+1, result, alreadyVisited);
        result = addLocation(x,y-1, result, alreadyVisited);
        result = addLocation(x,y+1, result, alreadyVisited);
        result = addLocation(x+1,y-1, result, alreadyVisited);
        result = addLocation(x+1,y, result, alreadyVisited);
        result = addLocation(x+1,y+1, result, alreadyVisited);
        return result;
    }

    private ArrayList<Location> addLocation(int x, int y, ArrayList<Location> nodes, ArrayList<Location> alreadyVisited) {
        if(inGrid(x, y)) {
            Location loc = grid[x][y];
            if(!alreadyVisited.contains(loc)) {
                nodes.add(loc);
            }
        }
        return nodes;
    }
    private boolean inGrid(int x, int y) {
        return x < grid.length && x >= 0 && y < grid[x].length && y >= 0;
    }

    public ArrayList<Location> DFS(Location start, Location end) {
        // array of visited nodes
        ArrayList<Location> visited = new ArrayList<Location>();

        //pq for nodes yet to be examined
        Stack<Location> nodes = new Stack<Location>();

        //the best place to begin is at the beginning
        nodes.add(start);

        //likewise, the best path is the one that starts at the start
        path.add(start);

        //set the path on start
        start.setPathTo((ArrayList<Location>)path.clone());

        while(!nodes.isEmpty()) {
            Location current = nodes.pop();
            if(current.equals(end)) {
                repaint();
                System.out.println("Success from if statement");
                path.add(current);
                return path;
            }
            visited.add(current);
            ArrayList<Location> children = getNodeChildren(current, visited);
            for(Location child : children) {
                path = (ArrayList<Location>)current.getPathTo().clone();
                path.add(child);
                child.setPathTo((ArrayList<Location>)path.clone());
                nodes.add(child);
            }
            repaint();
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException ex) {
                System.out.println("Yo dawg quit crashing the program");
            }
            // long startTime = System.nanoTime(),
            //     now;
            // do {
            //     now = System.nanoTime();
            // }while((now - startTime) < 500000000l);
        }
        repaint();
        System.out.println("Success");
        return path;
   
    }


    public ArrayList<Location> BFS(Location start, Location end) {
        // array of visited nodes
        ArrayList<Location> visited = new ArrayList<Location>();

        //pq for nodes yet to be examined
        LinkedList<Location> nodes = new LinkedList<Location>();

        //the best place to begin is at the beginning
        nodes.add(start);

        //likewise, the best path is the one that starts at the start
        path.add(start);

        //set the path on start
        start.setPathTo((ArrayList<Location>)path.clone());

        while(!nodes.isEmpty()) {
            Location current = nodes.poll();
            if(current.equals(end)) {
                repaint();
                System.out.println("Success from if statement");
                path.add(current);
                return path;
            }
            visited.add(current);
            ArrayList<Location> children = getNodeChildren(current, visited);
            for(Location child : children) {
                path = (ArrayList<Location>)current.getPathTo().clone();
                path.add(child);
                child.setPathTo((ArrayList<Location>)path.clone());
                nodes.add(child);
            }
            repaint();
            try {
                Thread.sleep(1);
            }
            catch(InterruptedException ex) {
                System.out.println("Yo dawg quit crashing the program");
            }
            // long startTime = System.nanoTime(),
            //     now;
            // do {
            //     now = System.nanoTime();
            // }while((now - startTime) < 500000000l);
        }
        repaint();
        System.out.println("Success");
        return path;
   
    }

    public static void main(String[] args) {
        GridPanel panel = new GridPanel(10, 10);
    }
}