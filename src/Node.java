public class Node implements Comparable<Node> {

    //Node locations
    public int x, y;

    //Euclidean distance
    public double distance;

    //Costs for each Node
    public double northCost = 3;
    public double westCost = 4;
    public double southCost = 1;
    public double eastCost = 2;
    public double accumulated_Cost;

    //Fill in the Maps
    public String s;
    public boolean is_forbid;
    public boolean visited;
    public boolean in_fringe;

    //Grab the parent node to print out final path
    public Node parent;

    public Node(int x, int y, String s, boolean f) {
        this.x = x;
        this.y = y;
        this.is_forbid = f;
        this.in_fringe = false;
        this.visited = false;
        this.s = s;
        northCost = 3;
        westCost = 4;
        southCost = 1;
        eastCost = 2;
    }

    //This method will take a node value that has already been created
    //and allow us to put in a distance value
    public static Node addDistance(Node changedNode, double heuristic) {
        changedNode.distance = heuristic;
        return changedNode;
    }

    public static Node addTotalCost(Node changedNode, Node oldNode, double cost) {
        changedNode.accumulated_Cost = oldNode.accumulated_Cost + cost;
        return changedNode;
    }

    //This method is for our "First search" method for A* it doesn't require an older node
    public static Node addTotalCost(Node changedNode, double cost) {
        changedNode.accumulated_Cost = cost;
        return changedNode;
    }

    //Display coordinate information
    public String toString() {
        return "(" + this.x + "," + this.y + ")" + "(" + s + ")";
    }

    public Node getParent() {
        return parent;
    }

    public int compareTo(Node n) {
        if (this.distance < n.distance) {
            return -1;
        } else if (this.distance > n.distance) {
            return 1;
        } else return 0;
    }
}
	

