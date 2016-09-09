package MazeLogic;

import java.util.HashSet;

/**
 * MazeSolver Class
 * Creates an object with coordinates and makes life easier...
 * For use on the current maze coordinate.
 *
 * @author Patrick Shinn
 * @author Brandon Duke
 * @version 9/8/16
 *
 */
public class MazeSolver {

    private char[][][] masterMaze;
    private int[] startLocation, currentLocation;
    private HashSet<Coordinate> visitedSpecial = new HashSet<>(); // visited portals & stairs
    private HashSet<Coordinate> visitedLocations = new HashSet<>(); // visited open spaces


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

    public int[] getCurrentLocation() {
        return currentLocation;
    }

    // calls of the the methods that return a character, explores the area...
    public void explore(){ // calls all methods of the class for a search.
        /*
        ################################################################# checking for exit
         */
        if ((above().compareCharacter('*'))){
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('*')){
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('*')){
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('*')){
            currentLocation = right().getCoords();
        }
        /*
        ################################################################# checking for empty spaces
         */
        else if (above().compareCharacter('.') &&
                visitedLocations.add(above())){
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('.') &&
                visitedLocations.add(left())){
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('.') &&
                visitedLocations.add(below())){
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('.') &&
                visitedLocations.add(below())){
            currentLocation = right().getCoords();
        }
         /*
        ################################################################# checking for unexplored portals
         */
        else if (above().compareCharacter('+') &&
                visitedSpecial.add(above())){
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('+') &&
                visitedSpecial.add(left())){
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('+') &&
                visitedSpecial.add(below())){
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('+') &&
                visitedSpecial.add(right())){
            currentLocation = right().getCoords();
        }
         /*
        ################################################################# checking for unexplored stairs
         */
        else if (above().compareCharacter('=') &&
                visitedSpecial.add(above())){
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('=') &&
                visitedSpecial.add(left())){
            currentLocation = above().getCoords();
        }
        else if (below().compareCharacter('=') &&
                visitedSpecial.add(below())){
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('=') &&
                visitedSpecial.add(right())){
            currentLocation = right().getCoords();
        }
         /*
        ################################################################# checking for any portals
         */
        else if (above().compareCharacter('+')){
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('+')){
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('+')){
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('+')){
            currentLocation = right().getCoords();
        }
         /*
        ################################################################# checking for any stairs
         */
        else if (above().compareCharacter('=')){
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('=')){
            currentLocation = above().getCoords();
        }
        else if (below().compareCharacter('=')){
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('=')){
            currentLocation = right().getCoords();
        }
        /*
        ################################################################# checking the spot we are on for portals
         */
        else if (on().compareCharacter('+')){
            //// TODO: 9/9/16 CODE ME
        }
        else if (on().compareCharacter('=')){
            //// TODO: 9/9/16 CODE ME
        }
        else {
            // // TODO: 9/9/16 POP ME BABY
        }


    }

    // All of these return the characters in the respective location to the current position.
    public Coordinate on(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]],
                currentLocation );
    }

    private Coordinate above(){
         return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]-1][currentLocation[2]],
                 new int[] {currentLocation[0],currentLocation[1]-1,currentLocation[2]});
    }

    private Coordinate right(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]+1],
                new int[] {currentLocation[0],currentLocation[1],currentLocation[2]+1});
    }

    private Coordinate left(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]-1],
                new int[] {currentLocation[0],currentLocation[1],currentLocation[2]-1});
    }

    private Coordinate below(){
        return new Coordinate(masterMaze[currentLocation[0]][currentLocation[1]+1][currentLocation[2]],
                new int[] {currentLocation[0],currentLocation[1]+1,currentLocation[2]});
    }

}
