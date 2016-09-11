package MazeLogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashSet;

/**
 * MazeSolver Class Debugger
 * Creates an object with coordinates and makes life easier...
 * For use on the current maze coordinate.
 *
 * @author Patrick Shinn
 * @author Brandon Duke
 * @version 9/8/16
 *
 */
public class UseMe {

    private char[][][] masterMaze;
    private int[] currentLocation;
    private HashSet<String> visitedSpecial = new HashSet<>(); // visited portals & stairs
    private HashSet<String> visitedLocations = new HashSet<>(); // visited open spaces
    private ThompsonStack<Coordinate> FredFin = new ThompsonStack<>();

    Image visted = new Image(getClass().getResourceAsStream("/graphics/visited.png"));
    Image popped = new Image(getClass().getResourceAsStream("/graphics/popped.png"));

    Canvas canvas;
    GraphicsContext graphicsContext;

// // TODO: 9/11/16 DELETE ME 

    /**
     * Constructor of the class.
     *
     * @param masterMaze 3d array of the loaded maze.
     *
     */
    public UseMe(char[][][] masterMaze){
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
        this.currentLocation = location;

    }
    

    public void startExploration(Slider slider, Canvas canvas){
        boolean done = false;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();

        while (!done){
            try {

                graphicsContext.drawImage(visted, (double)(currentLocation[2] * 45), (double)(currentLocation[1]*45));
                System.out.println(visitedLocations);
                System.out.println("Current Location: " + currentLocation[0] + " " + currentLocation[1] + " " + currentLocation[2]);
                done = explore();

                    Thread.sleep((long) slider.getValue()*10);
            }catch (Exception e){
                System.out.println(e);
                break;
            }
        }
        if (Character.compare('*', masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]]) == 0){
            System.out.println("You found the end at: " + currentLocation[0] + " " + currentLocation[1] + " " + currentLocation[2]);
        }
        else {
            System.out.println("you didn't");
        }
    }

    // calls of the the methods that return a character, explores the area...
    private boolean explore(){ // calls all methods of the class for a search.
        if (on().compareCharacter('*')){
            return true;
        }
        else if (above().compareCharacter('.') &&
                visitedLocations.add(above().toString())){
            System.out.println(above().getCharacter());
            FredFin.push(above());
            currentLocation = above().getCoords();

        }
        else if (left().compareCharacter('.') &&
                visitedLocations.add(left().toString())){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('.') &&
                visitedLocations.add(below().toString())){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('.') &&
                visitedLocations.add(right().toString())){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
        }

        else {
            Coordinate tempCoor = FredFin.pop();
            System.out.println("Popped: " + tempCoor.getCharacter() + " " + Arrays.toString(tempCoor.getCoords()));
            currentLocation = FredFin.peek().getCoords();
            graphicsContext.drawImage(popped, currentLocation[2] * 45, currentLocation[1] * 45);
            System.out.println(Arrays.toString(FredFin.peek().getCoords()));
        }
        return false;
    }

    // All of these return the characters in the respective location to the current position.
    private Coordinate on(){
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

    // portal and stair traverse methods
    private void beamMeUpScotty(){
        for (int q = currentLocation[0]; q < masterMaze.length + currentLocation[0]; q ++ ){
            try {
                if (Character.compare('+', masterMaze[q][currentLocation[1]][currentLocation[2]]) == 0){
                    currentLocation[0] = q;
                    break;
                }
            }catch (IndexOutOfBoundsException error){
                if (Character.compare('+', masterMaze[q - currentLocation[0]][currentLocation[1]][currentLocation[2]]) == 0){
                    currentLocation[0] = q - currentLocation[0];
                    break;
                }
            }
        }
        FredFin.push(new Coordinate('+', currentLocation));
    }

    private void itsActuallyALadder(){
        try {
            if (Character.compare('=', masterMaze[currentLocation[0] + 1][currentLocation[1]][currentLocation[2]]) == 0){
                currentLocation[0] = currentLocation[0] + 1;
            } else  if (Character.compare('=', masterMaze[currentLocation[0] - 1][currentLocation[1]][currentLocation[2]]) == 0){
                currentLocation[0] = currentLocation[0] - 1;
            }
        }catch (IndexOutOfBoundsException error){
            if (Character.compare('=', masterMaze[currentLocation[0] - 1][currentLocation[1]][currentLocation[2]]) == 0){
                currentLocation[0] = currentLocation[0] - 1;
            }
        }
        FredFin.push(new Coordinate('=', currentLocation));
    }

    // checks to see if there any explorable positions
    private boolean explorable(){
        if (above().compareCharacter('.') && visitedLocations.add(above().toString())){
            return true;
        }
        else if (left().compareCharacter('.') && visitedLocations.add(left().toString())){
            return true;
        }
        else if (below().compareCharacter('.') && visitedLocations.add(below().toString())){
            return true;
        }
        else if (right().compareCharacter('.') && visitedLocations.add(right().toString())){
            return true;
        }
        else if (above().compareCharacter('+') && visitedSpecial.add(above().toString())){
            return true;
        }
        else if (left().compareCharacter('+') && visitedSpecial.add(left().toString())){
            return true;
        }
        else if (below().compareCharacter('+') && visitedSpecial.add(below().toString())){
            return true;
        }
        else if (right().compareCharacter('+') && visitedSpecial.add(right().toString())){
            return true;
        }
        else if (above().compareCharacter('=') && visitedSpecial.add(above().toString())){
            return true;
        }
        else if (left().compareCharacter('=') && visitedSpecial.add(left().toString())){
            return true;
        }
        else if (below().compareCharacter('=') && visitedSpecial.add(below().toString())){
            return true;
        }
        else if (right().compareCharacter('=') && visitedSpecial.add(right().toString())){
            return true;
        }
        else return false;
    }
}
