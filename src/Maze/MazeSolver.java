package Maze;

import java.util.ArrayList;
import java.util.ListIterator;

public class MazeSolver {

    // The worklist to hold the search as it proceeds.
    private SearchWorklist worklist;

    // The Maze to solve.
    private Maze maze = new Maze();

    // The path to the exit, if any
    private String path="Found the Escape!";

    // Begin: Used in MazeApp
    private boolean gameOver = false; // game over, exit has(n't) been found
    private boolean foundExit = false; // exit has been found

    public void setGameOver() {
        gameOver = true;
    }

    public void setFoundExit() {
        foundExit = true;
    }

    public boolean gameOver() {
        return gameOver || foundExit;
    }

    public boolean isFoundExit() {
        return foundExit;
    }

    public void makeEmpty() {
        // remove Squares until empty
        while (!worklist.isEmpty()) {
            worklist.getNext();
        }
    }
    // End: Used in MazeApp

    public SearchWorklist getWorkList() {
        return worklist;
    }

    /** isEmpty
     * @return true if the worklist is empty, false otherwise
     */
    public boolean isEmpty() {
        return worklist.isEmpty();
    }

    /** size of the worklist
     * @return The number of elements in the worklist
     */
    public int size() {
        return worklist.size();
    }

    /** Make a new Solver with a given Maze and Worklist
     * @param theMaze The Maze to solve
     * @theWorklist The worklist to use
     */
    MazeSolver(Maze theMaze, SearchWorklist theWorklist){
        this.maze = theMaze;
        this.worklist = theWorklist;
    }

    /**
     * Get the Maze object
     * @return the maze
     */
    public Maze getMaze() {
        return this.maze;
    }

    /**
     * Solve the maze, if possible.
     * If a solution is found, set the path variable and the
     * foundExit variable appropriately.
     */
    public void solve() {
        Square start = maze.getStart();
        Square next;
        worklist.add(start);
        while (!worklist.isEmpty() && !foundExit) {
            next = this.step();
            if (next.isEnd()) {
                foundExit = true;
            }
        }
        gameOver = true;
        if (foundExit) {
            this.setPath(maze.getFinish());
        }

    }

    /** Take the next step toward the goal
     * PRECONDITION: The worklist is not empty
     * @return The next Square that has just been visited.
     */
    public Square step() {
        Square curr = worklist.getNext();
        curr.setVisited();
        ArrayList<Square> neighbors = maze.getNeighbors(curr);
        for (int i=0; i<neighbors.size(); i++) {
            Square temp = neighbors.get(i);
            if (temp.getPrevious()==null && !temp.isVisited()) {
                worklist.add(temp);
                temp.setPrevious(curr);
            }
        }
        return curr;
    }

    private String printCoor(Square s) {
        String toReturn = "[" + s.getRow() + "," + s.getCol() + "] ";
        return toReturn;
    }

    // Set the squares in the path appropriately and set the path
    // from start to finish.
    public void setPath(Square finish) {
        path += "\nPath from start to finish: ";
        String pathCoor = "";
        while(!finish.isStart()) {
            pathCoor = printCoor(finish) + pathCoor;
            finish.setFinalPath();
            finish = finish.getPrevious();
        }
        path = path + printCoor(finish) + pathCoor;
    }

    /**
     * Get the number of elements that are left on the worklist
     * @return The size of the worklist
     */
    public int getWorklistSize() {
        return worklist.size();
    }

    /**
     * Get the path from start to exit, if any.
     * @return Path from S to E as a list of coordinates [row,col]
     * If not solvable, the path is a message
     */
    public String getPath() {
        if (foundExit) {
            return path;
        } else {
            path = "Uh Oh!! There's no escape!!";
            return path;
        }
    }

    /** A program to solve a maze using either BFS or DFS */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java hw3.MazeSolver mazefile " +
                    "[dfs|bfs]");
            System.exit(0);
        }
        if (!(args[1].equals("dfs") || args[1].equals("bfs"))) {
            System.out.println("Second command line argument must be " +
                    "either dfs (depth first search) or bfs "+
                    "(breadth first search)");
            System.exit(0);
        }

        Maze myMaze = new Maze();
        boolean load = myMaze.loadMaze(args[0]);
        String output = "";
        if (!load) {
            System.out.println("Oops!! Could not load the Maze");
            System.exit(0);
        }

        MazeSolver solver = null;
        if (args[1].equals("bfs")) {
            solver = new MazeSolver(myMaze, new QueueWorklist());
        } else {
            solver = new MazeSolver(myMaze, new StackWorklist());
        }
        solver.solve();
        System.out.println(solver.getPath() +"\n");
        System.out.println(solver.getMaze().toString());
        System.out.println("Number of squares remaining in the "+
                "worklist = "+ solver.getWorklistSize());
    }
}
