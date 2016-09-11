package MazeLogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;

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
    private int[] currentLocation;
    private HashSet<Coordinate> visitedSpecial = new HashSet<>(); // visited portals & stairs
    private HashSet<Coordinate> visitedLocations = new HashSet<>(); // visited open spaces
    private ThompsonStack<Coordinate> FredFin = new ThompsonStack<>();


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
        this.currentLocation = location;

    }
    

    public void startExploration(Slider slider, Canvas canvas){
        boolean done = false;
        Image visted = new Image(getClass().getResourceAsStream("/graphics/visited.png"));
        while (!done){
            // // TODO: 9/9/16 debug code
            try {
                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
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

    // // TODO: 9/9/16 Something is very wrong here...
    // calls of the the methods that return a character, explores the area...
    private boolean explore(){ // calls all methods of the class for a search.
        if (on().compareCharacter('*')){
            // // TODO: 9/9/16 code...
            return true;
        }
        /*
        ################################################################# checking for exit
         */
        else if ((above().compareCharacter('*'))){
            currentLocation = above().getCoords();
            FredFin.push(above());
        }
        else if (left().compareCharacter('*')){
            currentLocation = left().getCoords();
            FredFin.push(left());
        }
        else if (below().compareCharacter('*')){
            currentLocation = below().getCoords();
            FredFin.push(below());
        }
        else if (right().compareCharacter('*')){
            currentLocation = right().getCoords();
            FredFin.push(right());
        }
        /*
        ################################################################# checking for empty spaces
         */
        else if (above().compareCharacter('.') &&
                visitedLocations.add(above())){
            currentLocation = above().getCoords();
            FredFin.push(above());

        }
        else if (left().compareCharacter('.') &&
                visitedLocations.add(left())){
            currentLocation = left().getCoords();
            FredFin.push(left());
        }
        else if (below().compareCharacter('.') &&
                visitedLocations.add(below())){
            currentLocation = below().getCoords();
            FredFin.push(below());
        }
        else if (right().compareCharacter('.') &&
                visitedLocations.add(below())){
            currentLocation = right().getCoords();
            FredFin.push(right());
        }
         /*
        ################################################################# checking for unexplored portals
         */
        else if (above().compareCharacter('+') &&
                visitedSpecial.add(above())){
            currentLocation = above().getCoords();
            FredFin.push(above());
            beamMeUpScotty();
        }
        else if (left().compareCharacter('+') &&
                visitedSpecial.add(left())){
            currentLocation = left().getCoords();
            FredFin.push(left());
            beamMeUpScotty();
        }
        else if (below().compareCharacter('+') &&
                visitedSpecial.add(below())){
            currentLocation = below().getCoords();
            FredFin.push(below());
            beamMeUpScotty();

        }
        else if (right().compareCharacter('+') &&
                visitedSpecial.add(right())){
            currentLocation = right().getCoords();
            FredFin.push(right());
            beamMeUpScotty();
        }
         /*
        ################################################################# checking for unexplored stairs
         */
        else if (above().compareCharacter('=') &&
                visitedSpecial.add(above())){
            currentLocation = above().getCoords();
            FredFin.push(above());
            itsActuallyALadder();
        }
        else if (left().compareCharacter('=') &&
                visitedSpecial.add(left())){
            currentLocation = above().getCoords();
            FredFin.push(left());
            itsActuallyALadder();
        }
        else if (below().compareCharacter('=') &&
                visitedSpecial.add(below())){
            currentLocation = below().getCoords();
            FredFin.push(below());
            itsActuallyALadder();
        }
        else if (right().compareCharacter('=') &&
                visitedSpecial.add(right())){
            currentLocation = right().getCoords();
            FredFin.push(right());
            itsActuallyALadder();
        }
         /*
        ################################################################# checking for any portals
         */
        else if (above().compareCharacter('+')){
            currentLocation = above().getCoords();
            FredFin.push(above());
            beamMeUpScotty();
        }
        else if (left().compareCharacter('+')){
            currentLocation = left().getCoords();
            FredFin.push(left());
            beamMeUpScotty();
        }
        else if (below().compareCharacter('+')){
            currentLocation = below().getCoords();
            FredFin.push(below());
            beamMeUpScotty();
        }
        else if (right().compareCharacter('+')){
            currentLocation = right().getCoords();
            FredFin.push(right());
            beamMeUpScotty();
        }
         /*
        ################################################################# checking for any stairs
         */
        else if (above().compareCharacter('=')){
            currentLocation = above().getCoords();
            FredFin.push(above());
            itsActuallyALadder();
        }
        else if (left().compareCharacter('=')){
            currentLocation = above().getCoords();
            FredFin.push(left());
            itsActuallyALadder();
        }
        else if (below().compareCharacter('=')){
            currentLocation = below().getCoords();
            FredFin.push(below());
            itsActuallyALadder();
        }
        else if (right().compareCharacter('=')){
            currentLocation = right().getCoords();
            FredFin.push(right());
            itsActuallyALadder();
        }
        /*
        ################################################################# checking the spot we are on for portals
         */
        else if (on().compareCharacter('+')){
            for (int q = currentLocation[0]; q < masterMaze.length + currentLocation[0]; q ++ ){
                try {
                    if (Character.compare('+', masterMaze[q][currentLocation[1]][currentLocation[2]]) == 0 && explorable()){
                        explore();
                    }
                    else {//terminate
                        return true;
                    }
                }catch (IndexOutOfBoundsException error){
                    if (Character.compare('+', masterMaze[q - currentLocation[0]][currentLocation[1]][currentLocation[2]]) == 0 && explorable()){
                        explore();
                    }
                    else {//terminate
                        return true;
                    }
                }
            }
        }
        else if (on().compareCharacter('=')){
            try {
                if (Character.compare('+', masterMaze[currentLocation[0] + 1][currentLocation[1]][currentLocation[2]]) == 0 && explorable()){
                    explore();
                }
                else  if (Character.compare('=', masterMaze[currentLocation[0] - 1][currentLocation[1]][currentLocation[2]]) == 0 && explorable()){
                    explore();
                }
                else {//terminate
                    return true;
                }
            }catch (IndexOutOfBoundsException error){
                if (Character.compare('=', masterMaze[currentLocation[0] - 1][currentLocation[1]][currentLocation[2]]) == 0 && explorable()){
                    explore();
                }
                else {//terminate
                    return true;
                }
            }
        }
        else {
            FredFin.pop();
            currentLocation = FredFin.peek().getCoords();
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
        if (above().compareCharacter('.') && visitedLocations.add(above())){
            return true;
        }
        else if (left().compareCharacter('.') && visitedLocations.add(left())){
            return true;
        }
        else if (below().compareCharacter('.') && visitedLocations.add(below())){
            return true;
        }
        else if (right().compareCharacter('.') && visitedLocations.add(right())){
            return true;
        }
        else if (above().compareCharacter('+') && visitedSpecial.add(above())){
            return true;
        }
        else if (left().compareCharacter('+') && visitedSpecial.add(left())){
            return true;
        }
        else if (below().compareCharacter('+') && visitedSpecial.add(below())){
            return true;
        }
        else if (right().compareCharacter('+') && visitedSpecial.add(right())){
            return true;
        }
        else if (above().compareCharacter('=') && visitedSpecial.add(above())){
            return true;
        }
        else if (left().compareCharacter('=') && visitedSpecial.add(left())){
            return true;
        }
        else if (below().compareCharacter('=') && visitedSpecial.add(below())){
            return true;
        }
        else if (right().compareCharacter('=') && visitedSpecial.add(right())){
            return true;
        }
        else return false;
    }
}
