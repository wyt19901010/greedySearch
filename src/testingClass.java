import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class testingClass {

    private static int start_row;
    private static int start_col;
    private static int[][] grid;
    private static int vertical_col = 0;          //Class variable
    private static int horizontal_rows = 0;       //Class variable
    private static int end_row;                   //Sets our goal row
    private static int end_col;                   //Sets our goal column
    private static Scanner in = new Scanner(System.in);


    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Running from TestingClass.java");
        System.out.println();
        setup_maze();
        printMaze();

    }

    // Init maze
    private static void setup_maze() throws FileNotFoundException {

        Scanner sc = new Scanner(new File("src/SearchTest.txt"));
        horizontal_rows = sc.nextInt();
        vertical_col = sc.nextInt();     //Affects the class variable
        start_row = sc.nextInt();        //Affects the class variable
        start_col = sc.nextInt();        //Affects the class variable
        end_row = sc.nextInt();          //Affects the class variable
        end_col = sc.nextInt();          //Affects the class variable
        grid = new int[horizontal_rows][vertical_col];

        System.out.println("Height= " + horizontal_rows + " Width= " + vertical_col);
        System.out.println();

        for (int x = 0; x < horizontal_rows; x++) {
            for (int y = 0; y < vertical_col; y++) {
                grid[x][y] = sc.nextInt();
            }
        }
    }

    // Setting maze and
    private static void printMaze() {

        Node[][] maze = new Node[horizontal_rows][vertical_col];
        Node Start = new Node(start_row, start_col, "S", false);
        Node Goal = new Node(end_row, end_col, "G", false);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                Node Empty = new Node(i, j, "0", false);
                maze[i][j] = Empty;
                for (int x = 0; x < horizontal_rows; x++) {
                    for (int y = 0; y < vertical_col; y++) {
                        if (grid[i][j] == 1) {
                            Node trap = new Node(i, j, "X", true);
                            maze[i][j] = trap;
                        }
                    }
                }
            }
        }

        System.out.println("Maze: ");
        System.out.println();
        maze[start_row][start_col] = Start;
        maze[end_row][end_col] = Goal;

        // Prints the maze and execute algorithm
        for (Node[] z : maze) {
            for (Node x : z) {
                System.out.print(x + "\t");
            }
            System.out.println("\n");
        }

        System.out.println("Enter 1 for Greedy Search, or 2 for A* Search");

        while (!in.hasNext("[1-2]+")) {
            System.out.println("Press Press 1 or 2");
            in.next();
        }

        //Select the algorithm which we want to execute
        String searchType = in.next();
        //Execute algorithm
        String searchMethod = searchType.substring(0, 1);

        // Passes in start, goal, and initialized maze
        if (searchMethod.equals("1")) {
            GreedySearch.GreedySearch(Start, Goal, maze, horizontal_rows, vertical_col);
        } else if (searchMethod.equals("2")) {
            AStarSearch.AStarSearch(Start, Goal, maze, horizontal_rows, vertical_col);
        }
    }

}
