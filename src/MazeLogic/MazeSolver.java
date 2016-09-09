package MazeLogic;

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


    /**
     * Constructor of the class.
     *
     * @param masterMaze 3d array of the loaded maze.
     *
     */
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

    // calls of the the methods that return a character, explores the area...
    public void explore(){ // calls all methods of the class for a search.
        if (Character.compare(above().getCharacter(), '*') == 0 ){
            currentLocation = above().getCoords();
        }
        else if (Character.compare(right().getCharacter(), '*') == 0){
            currentLocation = right().getCoords();
        }
        else if (Character.compare(left().getCharacter(), '*') == 0 ){
            currentLocation = left().getCoords();
        }
        else if (Character.compare(below().getCharacter(), '*') == 0){
            currentLocation = below().getCoords();
        }

    }


    // All of these return the characters in the respective location to the current position.
    public Coordinate on(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]],currentLocation );
    }

    public Coordinate above(){
         return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]-1][currentLocation[2]],
                                new int[] {currentLocation[0],currentLocation[1]-1,currentLocation[2]});
    }

    public Coordinate right(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]+1],
                new int[] {currentLocation[0],currentLocation[1],currentLocation[2]+1});
    }

    public Coordinate left(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]-1],
                new int[] {currentLocation[0],currentLocation[1],currentLocation[2]-1});
    }

    public Coordinate below(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]+1][currentLocation[2]],
                new int[] {currentLocation[0],currentLocation[1]+1,currentLocation[2]});
    }

}
