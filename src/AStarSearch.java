
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class AStarSearch {

    // Init A Star Search
    static void AStarSearch(Node Start, Node Goal, Node maze[][], int horizontal_rows, int vertical_cols) {
        System.out.println();
        System.out.println("Starting a A* Search");
        System.out.println();
        int numOfSteps = 0;
        long startGreedy = System.currentTimeMillis();

        boolean goalFound = false;
        Queue queue = new LinkedList();
        Queue PathtoGoal = new LinkedList();
        Queue visted = new LinkedList();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(); //this is our priority queue, using our Node class
        GreedyMazeStartUp(Start, Goal, maze, queue, numOfSteps, PathtoGoal, goalFound, visted, horizontal_rows, vertical_cols, pq);
        long endGreedy = System.currentTimeMillis();
        System.out.println("The time of A* Search is: " + (endGreedy - startGreedy) + "ms");
    }


    // Start Star Search
    private static void GreedyMazeStartUp(Node start, Node goal, Node maze[][], Queue queue, int numOfSteps, Queue PathtoGoal, boolean goalFound, Queue visted, int horizontal, int vertical, PriorityQueue<Node> pq) {
        int x = start.x;
        int y = start.y;
        int horizontal_rows = horizontal;
        int vertical_col = vertical;
        int xG = goal.x;
        int yG = goal.y;

        double heuristic_North = 0;
        double heuristic_West = 0;
        double heuristic_South = 0;
        double heuristic_East = 0;
        double complete_Cost = 0;


        if (x - 1 >= 0) {
            //North Heuristic, store the value then Sqrt later
            heuristic_North = Math.pow(((x - 1) - xG), 2) + Math.pow((y - yG), 2);
        } else if (x - 1 < 0) {
            heuristic_North = Double.NaN;
        }


        if (y - 1 >= 0) {
            //West Heuristic, store the value then Sqrt it later
            heuristic_West = Math.pow((x - xG), 2) + Math.pow(((y - 1) - yG), 2);
        } else if (y - 1 < 0) {
            heuristic_West = Double.NaN;
        }


        if (x + 1 <= horizontal_rows) {
            //South Heuristic
            heuristic_South = Math.pow(((x + 1) - xG), 2) - Math.pow((y - yG), 2);
        } else {
            heuristic_South = Double.NaN;
        }


        if (y + 1 < vertical_col) {
            //East Heuristic
            heuristic_East = Math.pow((x - xG), 2) - Math.pow(((y + 1) - yG), 2);
        } else if (y + 1 >= vertical_col) {
            heuristic_East = Double.NaN;
        }

        start.visited = true;
        queue.add(start);
        pq.add(start);

        //Test north
        if (start.x - 1 >= 0) {
            Node test = maze[start.x - 1][y];
            complete_Cost = Math.sqrt(Math.abs(heuristic_North)) + start.northCost;
            Node.addDistance(test, complete_Cost);
            Node.addTotalCost(test, start.northCost);

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }
        }

        //This tests the west
        if (start.y - 1 >= 0) {
            Node test = maze[x][start.y - 1];
            complete_Cost = Math.sqrt(Math.abs(heuristic_West)) + start.westCost;
            Node.addDistance(test, complete_Cost);
            Node.addTotalCost(test, start.westCost);

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }
        }

        //Test south
        if (start.x + 1 < horizontal_rows) {
            Node test = maze[start.x + 1][y];
            complete_Cost = Math.sqrt(Math.abs(heuristic_South)) + start.southCost;
            Node.addDistance(test, complete_Cost); //TODO test this, seems to work
            Node.addTotalCost(test, start.southCost);

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }
        }

        //Test east
        if (start.y + 1 < vertical_col) {
            Node test = maze[x][start.y + 1];
            complete_Cost = Math.sqrt(Math.abs(heuristic_East)) + start.eastCost;
            Node.addDistance(test, complete_Cost); //TODO test this, seems to work
            Node.addTotalCost(test, start.eastCost);

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }

        }

        GreedyMazeStartSecond(start, goal, maze, queue, numOfSteps, PathtoGoal, goalFound, visted, horizontal_rows, vertical_col, pq);
    }

    private static void GreedyMazeStartSecond(Node Start, Node Goal, Node[][] maze, Queue queue, int numOfSteps, Queue PathtoGoal, boolean goalFound, Queue visted, int horizonal, int vertical, PriorityQueue<Node> pq) {
        int horizontal_rows = horizonal;
        int vertical_col = vertical;

        double heurstic_North = 0;
        double heuristic_West = 0;
        double heuristic_South = 0;
        double heuristic_East = 0;
        double complete_Cost = 0;

        Node first = (Node) pq.remove();

        numOfSteps++;

        PathtoGoal.add(first);

        //Loop through priority Queue
        while (!pq.isEmpty()) {
            if (first.x == Goal.x && first.y == Goal.y) {
                System.out.println("Goal was on top of start: Finished");
                goalFound = true;
                break;
            }

            Node Crawler = (Node) pq.remove();

            //Seperate Queue to follow our path
            PathtoGoal.add(Crawler);
            numOfSteps++;

            if (Crawler == Goal) {
                System.out.println("WE FOUND THE GOAL " + Goal);
                goalFound = true;
                visted.add(Goal);

                while (Crawler.parent != null) {
                    Crawler = Crawler.getParent();
                    visted.add(Crawler);
                }
                visted.add(Start);

                break;
            }

            Crawler.s = "V";

            //Test North
            if (Crawler.x - 1 >= 0) {
                Node QueueAdder = maze[Crawler.x - 1][Crawler.y];
                heurstic_North = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heurstic_North = Math.abs(Math.sqrt(heurstic_North));
                complete_Cost = heurstic_North + QueueAdder.northCost + Crawler.accumulated_Cost;
                Node.addTotalCost(QueueAdder, Crawler, QueueAdder.northCost);
                Node.addDistance(QueueAdder, complete_Cost);

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {
                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {
                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);
                }
            }

            //Test West
            if (Crawler.y - 1 >= 0) {
                Node QueueAdder = maze[Crawler.x][Crawler.y - 1];
                heuristic_West = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heuristic_West = Math.abs(Math.sqrt(heuristic_West));
                complete_Cost = heuristic_West + QueueAdder.westCost + Crawler.accumulated_Cost;
                Node.addTotalCost(QueueAdder, Crawler, QueueAdder.westCost);
                Node.addDistance(QueueAdder, complete_Cost);

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {
                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {
                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);
                }
            }

            //Test South
            if (Crawler.x + 1 < horizontal_rows) {
                Node QueueAdder = maze[Crawler.x + 1][Crawler.y];
                heuristic_South = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heuristic_South = Math.abs(Math.sqrt(heuristic_South));
                complete_Cost = heuristic_South + QueueAdder.southCost + Crawler.accumulated_Cost;

                Node.addTotalCost(QueueAdder, Crawler, QueueAdder.southCost);
                Node.addDistance(QueueAdder, complete_Cost);

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {
                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {
                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);

                }
            }

            //Test East
            if (Crawler.y + 1 < (vertical_col)) {
                Node QueueAdder = maze[Crawler.x][Crawler.y + 1];
                heuristic_East = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2); //This should get the QueueAdder (which already checked North)
                heuristic_East = Math.abs(Math.sqrt(heuristic_East));    //ABS just in case																						  //So now all we need \to do is sqrt it
                complete_Cost = heuristic_East + QueueAdder.eastCost + Crawler.accumulated_Cost;
                Node.addTotalCost(QueueAdder, Crawler, QueueAdder.eastCost);
                Node.addDistance(QueueAdder, complete_Cost);

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {
                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {

                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);
                }
            }
        }

        getResults(queue, PathtoGoal, visted, goalFound, numOfSteps, maze);

    }

    // Show result
    private static void getResults(Queue queue, Queue PathtoGoal, Queue visted, boolean goalFound, int numOfSteps, Node maze[][]) {

        if (queue.isEmpty() && goalFound == false) {
            System.out.println("Could not find or access the Goal");
        }

        System.out.println("Number of Steps taken " + numOfSteps);
        System.out.println();
        System.out.println("Our A* path is ");
        System.out.print(PathtoGoal + "");
        System.out.println();
        System.out.println();
        System.out.println("Our Final path is ");
        System.out.print(visted + "");
        System.out.println();
        System.out.println();

        //Prints the maze
        for (Node[] z : maze) {
            for (Node B : z) {
                System.out.print(B + "\t");
            }

            System.out.println("\n");
        }
    }
}

