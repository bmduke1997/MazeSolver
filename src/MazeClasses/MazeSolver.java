package MazeClasses;

import java.util.HashSet;

/**
 * MazeSolver Class
 * Creates an object with coordinates and makes life easier...
 * For use on the current maze coordinate.
 *
 * @author Patrick Shinn
 * @version 9/8/16
 *
 */
public class MazeSolver {

    private char[][][] masterMaze;
    private int[] startLocation, currentLocation;
    private HashSet<int[]> visitedLocations = new HashSet<>();

    public MazeSolver(char[][][] masterMaze){
        this.masterMaze = masterMaze;

        // This will find the start position and save it for later use...
        boolean startFound = false;
        int[] location = {-1, -1, -1};
        //Find the start position
        for ( char[][] z : masterMaze){ // For every maze in the file
            if (startFound) break;
            location[0] = location[0] + 1;
            location[1] = -1; //new maze reset y val
            for (char[] y : z ){  // for every row in the maze
                if (startFound) break;
                location[1] = location[1] + 1;
                location[2] = -1; // new row in maze reset x val
                for (char x : y){ // for every column in the row
                    location[2] = location[2] + 1;
                    if (Character.compare(x,'@') == 0){
                        startFound=true;
                        break;
                    }
                }
            }
        }

        this.startLocation = location;
        this.currentLocation = location;

    }

    public void callAll(){ // calls all methods of the class for a search.

    }

    public char on(){
        return masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]];
    }



    public char above(){
        return masterMaze[currentLocation[0]][currentLocation[1]-1][currentLocation[2]];
    }

    public char right(){
        return masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]+1];
    }

}
