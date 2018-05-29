import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GreedySearch {

    // Init Greedy Search
    static void GreedySearch(Node Start, Node Goal, Node maze[][], int horizontal_rows, int vertical_cols) {
        long startGreedy = System.currentTimeMillis();
        System.out.println();
        System.out.println("Starting a Greedy Search");
        System.out.println();
        int numOfSteps = 0;
        boolean goalFound = false;
        Queue queue = new LinkedList();
        Queue PathtoGoal = new LinkedList();
        Queue visted = new LinkedList();
        PriorityQueue<Node> pq = new PriorityQueue<Node>();

        GreedyMazeStartUp(Start, Goal, maze, queue, numOfSteps, PathtoGoal, goalFound, visted, horizontal_rows, vertical_cols, pq);
        long endGreedy = System.currentTimeMillis();
        System.out.println("The time of Greedy Search is: " + (endGreedy - startGreedy) + "ms");
    }

    // Start Greedy Search
    private static void GreedyMazeStartUp(Node start, Node goal, Node maze[][], Queue queue, int numOfSteps, Queue PathtoGoal, boolean goalFound, Queue visted, int horizontal, int vertical, PriorityQueue<Node> pq) {
        int x = start.x;
        int y = start.y;
        int horizontal_rows = horizontal;
        int vertical_col = vertical;
        int xG = goal.x;
        int xY = goal.y;
        double heuristic_North = 0;
        double heuristic_West = 0;
        double heuristic_South = 0;
        double heuristic_East = 0;

        if (x - 1 >= 0) {
            //North Heuristic, store the value then Sqrt later
            heuristic_North = Math.pow(((x - 1) - xG), 2) + Math.pow((y - xY), 2);
        } else if (x - 1 < 0) {
            heuristic_North = Double.NaN;
        }

        if (y - 1 >= 0) {
            //West Heuristic, store the value then Sqrt it later
            heuristic_West = Math.pow((x - xG), 2) + Math.pow(((y - 1) - xY), 2);
        } else if (y - 1 < 0) {
            heuristic_West = Double.NaN;
        }

        if (x + 1 <= horizontal_rows) {
            //South Heuristic
            heuristic_South = Math.pow(((x + 1) - xG), 2) - Math.pow((y - xY), 2);
        } else {
            heuristic_South = Double.NaN;
        }

        if (y + 1 < vertical_col) {
            //East Heuristic
            heuristic_East = Math.pow((x - xG), 2) - Math.pow(((y + 1) - xY), 2);
        } else if (y + 1 >= vertical_col) {
            heuristic_East = Double.NaN;
        }

        start.visited = true;
        queue.add(start);
        pq.add(start);

        //test north
        if (start.x - 1 >= 0) {
            Node test = maze[start.x - 1][y];
            Node.addDistance(test, Math.sqrt(Math.abs(heuristic_North))); //TODO test this, seems to work

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }
        }

        //This tests the west
        if (start.y - 1 >= 0) {
            Node test = maze[x][start.y - 1];
            Node.addDistance(test, Math.sqrt(Math.abs(heuristic_West))); //TODO test this, seems to work

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }
        }

        //Test south
        if (start.x + 1 < horizontal_rows) {
            Node test = maze[start.x + 1][y];
            Node.addDistance(test, Math.sqrt(Math.abs(heuristic_South))); //TODO test this, seems to work

            if (test.is_forbid == false) {
                test.visited = true;
                test.parent = null;
                pq.add(test);
            }
        }

        //Test east
        if (start.y + 1 < vertical_col) {
            Node test = maze[x][start.y + 1];
            Node.addDistance(test, Math.sqrt(Math.abs(heuristic_East))); //TODO test this, seems to work
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
        double heurstic_North = 1;
        double heuristic_West = 0;
        double heuristic_South = 0;
        double heuristic_East = 0;

        //Gets rid of start
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
                System.out.println();
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

            //Test North, this will not proceed if it reaches of map
            if (Crawler.x - 1 >= 0) {
                Node QueueAdder = maze[Crawler.x - 1][Crawler.y];
                heurstic_North = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heurstic_North = Math.abs(Math.sqrt(heurstic_North));
                Node.addDistance(QueueAdder, heurstic_North); //TODO This is where I left off for lunch

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {

                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {

                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);
                }
            }

            //Test west, this will not proceed if it reaches end of map
            if (Crawler.y - 1 >= 0) {
                Node QueueAdder = maze[Crawler.x][Crawler.y - 1];
                heuristic_West = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heuristic_West = Math.abs(Math.sqrt(heuristic_West));
                Node.addDistance(QueueAdder, heuristic_West);

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {

                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {

                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);
                }
            }

            //Test south, this test should skip south
            if (Crawler.x + 1 < horizontal_rows) {
                Node QueueAdder = maze[Crawler.x + 1][Crawler.y];
                heuristic_South = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heuristic_South = Math.abs(Math.sqrt(heuristic_South));
                Node.addDistance(QueueAdder, heuristic_South);

                if (QueueAdder.visited == true || QueueAdder.is_forbid == true) {

                } else if (QueueAdder.is_forbid == false || QueueAdder.visited == false) {

                    QueueAdder.visited = true;
                    QueueAdder.in_fringe = true;
                    QueueAdder.parent = Crawler;
                    pq.add(QueueAdder);
                }
            }

            //Test east, confirm it will hit end of wall
            if (Crawler.y + 1 < (vertical_col)) {
                Node QueueAdder = maze[Crawler.x][Crawler.y + 1];
                heuristic_East = Math.pow((QueueAdder.x - Goal.x), 2) + Math.pow((QueueAdder.y - Goal.y), 2);
                heuristic_East = Math.abs(Math.sqrt(heuristic_East));
                Node.addDistance(QueueAdder, heuristic_East);

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
        System.out.println("Our Greedy Search path is ");
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

