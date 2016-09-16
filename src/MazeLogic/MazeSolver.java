package MazeLogic;

import GUI.Maze.MapDrawer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashSet;

/**
 * MazeSolver Class
 * The logic behind the maze.
 *
 * @author Patrick Shinn
 * @author Brandon Duke
 * @author Claire Wallace
 * @version 9/8/16
 *
 */
public class MazeSolver{

    private char[][][] masterMaze;
    private int[] currentLocation;
    private MapDrawer drawer;
    private Slider slider;
    private Label statusLbl;
    private int movesMade = 0;
    private boolean solved = false;
    private GraphicsContext graphicsContext;
    private HashSet<Coordinate> loopVisitedSpecial = new HashSet<>(); // looped visited portals & stairs
    private HashSet<Coordinate> visitedSpecial = new HashSet<>(); // visited portals & stairs
    private HashSet<Coordinate> visitedLocations = new HashSet<>(); // visited open spaces
    private ThompsonStack<Coordinate> FredFin = new ThompsonStack<>();
    private Image visited;


    /**
     * Constructor of the class.
     *
     * @param masterMaze 3d array of the loaded maze.
     * @param slider slider to obtain speed value
     * @param canvas for drawing to
     * @param drawer MapDrawer for drawing and saving the map.
     *
     */
    public MazeSolver(char[][][] masterMaze, Slider slider, Canvas canvas, MapDrawer drawer, Label statusLbl, String mapPack){
        this.masterMaze = masterMaze;
        this.slider = slider;
        this.drawer = drawer;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.statusLbl = statusLbl;
        this.visited = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/visited.png"));


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
        this.currentLocation = location; // saves the start location.
        FredFin.push(new Coordinate(masterMaze[currentLocation[0]][ currentLocation[1]][currentLocation[2]], currentLocation)); // pushes the first coordinate onto the stack.

    }

    // Returns the current location.
    public int[] getCurrentLocation() {
        return currentLocation;
    }

    // returns the moves made.
    public int getMovesMade() {
        return movesMade;
    }

    // returns whether or not we successfully solved the maze.
    public boolean isSolved(){
        return solved;
    }

    public void startExploration(){
        boolean done = false;
        while (!done){
            try{
                markPoint(); // marks the current location.
                System.out.println(visitedLocations);
                // // TODO: 9/15/16 debug delete.
                System.out.println("Current Location: " + currentLocation[0] + " " + currentLocation[1] + " " + currentLocation[2]);
                System.out.println("startExploration method was used.");
                done = explore();
                movesMade ++;
                Thread.sleep((long)(100 - slider.getValue())*10);
            }catch (Exception e){
                saveMap();
                e.printStackTrace();
                done = true; // terminates the program.
            }

        }
        saveMap();
        if (Character.compare('*', masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]]) == 0){
            System.out.println("You found the end at: " + currentLocation[0] + " " + currentLocation[1] + " " + currentLocation[2]);
            solved = true;
        }
        else {
            System.out.println("Map unsolvable.");
        }
    }

    // calls of the the methods that return a character, explores the area...
    private boolean explore(){ // calls all methods of the class for a search.
        if (on().compareCharacter('*')){
            return true;
        }
        /*
        ################################################################# checking for exit
         */
        else if ((above().compareCharacter('*'))){
            FredFin.push(above());
            currentLocation = above().getCoords();
        }
        else if (left().compareCharacter('*')){
            FredFin.push(left());
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('*')){
            FredFin.push(below());
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('*')){
            FredFin.push(right());
            currentLocation = right().getCoords();
        }
        /*
        ################################################################# checking for empty spaces
         */
        else if (above().compareCharacter('.') &&
                visitedLocations.add(above())){
            System.out.println(above().getCharacter());
            FredFin.push(above());
            currentLocation = above().getCoords();
            loopVisitedSpecial.clear();

        }
        else if (left().compareCharacter('.') &&
                visitedLocations.add(left())){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
            loopVisitedSpecial.clear();
        }
        else if (below().compareCharacter('.') &&
                visitedLocations.add(below())){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
            loopVisitedSpecial.clear();
        }
        else if (right().compareCharacter('.') &&
                visitedLocations.add(right())){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
            loopVisitedSpecial.clear();
        }
         /*
        ################################################################# checking for unexplored portals
         */
        else if (above().compareCharacter('+') &&
                visitedSpecial.add(above())){
            System.out.println(above().getCharacter());
            FredFin.push(above());
            currentLocation = above().getCoords();
            beamMeUpScotty();
        }
        else if (left().compareCharacter('+') &&
                visitedSpecial.add(left())){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
            beamMeUpScotty();
        }
        else if (below().compareCharacter('+') &&
                visitedSpecial.add(below())){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
            beamMeUpScotty();

        }
        else if (right().compareCharacter('+') &&
                visitedSpecial.add(right())){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
            beamMeUpScotty();
        }
         /*
        ################################################################# checking for unexplored stairs
         */
        else if (above().compareCharacter('=') &&
                visitedSpecial.add(above())){
            System.out.println(above().getCharacter());
            FredFin.push(above());
            currentLocation = above().getCoords();
            itsActuallyALadder();
        }
        else if (left().compareCharacter('=') &&
                visitedSpecial.add(left())){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
            itsActuallyALadder();
        }
        else if (below().compareCharacter('=') &&
                visitedSpecial.add(below())){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
            itsActuallyALadder();
        }
        else if (right().compareCharacter('=') &&
                visitedSpecial.add(right())){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
            itsActuallyALadder();
        }
         /*
        ################################################################# checking for any portals
         */
        else if (above().compareCharacter('+')){
            System.out.println(above().getCharacter());
            FredFin.push(above());
            currentLocation = above().getCoords();
            beamMeUpScotty();
        }
        else if (left().compareCharacter('+')){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
            beamMeUpScotty();
        }
        else if (below().compareCharacter('+')){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
            beamMeUpScotty();
        }
        else if (right().compareCharacter('+')){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
            beamMeUpScotty();
        }
         /*
        ################################################################# checking for any stairs
         */
        else if (above().compareCharacter('=')){
            System.out.println(above().getCharacter());
            FredFin.push(above());
            currentLocation = above().getCoords();
            itsActuallyALadder();
        }
        else if (left().compareCharacter('=')){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
            itsActuallyALadder();
        }
        else if (below().compareCharacter('=')){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
            itsActuallyALadder();
        }
        else if (right().compareCharacter('=')){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
            itsActuallyALadder();
        }
        /*
        ################################################################# checking the spot we are on for portals
         */
        else if (on().compareCharacter('+')){
            if (loopVisitedSpecial.add(on())) beamMeUpScotty();
            else{
                breadCrumbsLoop();
            }
        }
        else if (on().compareCharacter('=')){
            if (loopVisitedSpecial.add(on())) itsActuallyALadder();
            else{
                breadCrumbsLoop();
            }
        }
        else {
            breadCrumbs();
        }
        return false;
    }

    // portal traverse method //// TODO: 9/16/16 added to stack multiple times while popping.. 
    private void beamMeUpScotty(){
        logicSleep(); // sleeps the logic thread to slow down gui update.
        markPoint(); // mark the current point.
        saveMap(); // save the floor before switching floors.
        for (int q = currentLocation[0] + 1; q < masterMaze.length + currentLocation[0]; q ++ ){

            try {
                if (Character.compare('+', masterMaze[q][currentLocation[1]][currentLocation[2]]) == 0){
                    currentLocation[0] = q;
                    drawer.displayLevel(currentLocation[0]);
                    break;
                }
            }catch (IndexOutOfBoundsException error){
                if (Character.compare('+', masterMaze[q - (currentLocation[0]  + 1)][currentLocation[1]][currentLocation[2]]) == 0){
                    currentLocation[0] = q - (currentLocation[0] + 1);
                    drawer.displayLevel(currentLocation[0]);
                    break;
                }
            }
        }
        FredFin.push(new Coordinate('+', currentLocation));
        movesMade ++;
    }

    // steps traversing junk //// TODO: 9/16/16 added to stack multiple times while popping.. 
    private void itsActuallyALadder(){
        logicSleep();
        markPoint();
        saveMap();
        try {
            if (Character.compare('=', masterMaze[currentLocation[0] + 1][currentLocation[1]][currentLocation[2]]) == 0){
                currentLocation[0] = currentLocation[0] + 1;
                drawer.displayLevel(currentLocation[0]);

            } else  if (Character.compare('=', masterMaze[currentLocation[0] - 1][currentLocation[1]][currentLocation[2]]) == 0){
                currentLocation[0] = currentLocation[0] - 1;
                drawer.displayLevel(currentLocation[0]);

            }
        }catch (IndexOutOfBoundsException error){
            if (Character.compare('=', masterMaze[currentLocation[0] - 1][currentLocation[1]][currentLocation[2]]) == 0){
                currentLocation[0] = currentLocation[0] - 1;
                drawer.displayLevel(currentLocation[0]);

            }
        }
        FredFin.push(new Coordinate('=', currentLocation));
        movesMade ++;
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

    // checks to see if there any explorable positions
    private boolean explorable(){
        return  ((above().compareCharacter('.') && !visitedLocations.contains(above())) ||
                (left().compareCharacter('.') && !visitedLocations.contains(left())) ||
                (below().compareCharacter('.') && !visitedLocations.contains(below())) ||
                right().compareCharacter('.') && !visitedLocations.contains(right()));
    }

    private void breadCrumbs(){
        saveMap();
        FredFin.pop(); // remove the coordinate from the stack.
        currentLocation = FredFin.peek().getCoords(); // set the new coordinate to the next location in the stack.
        drawer.displayLevel(currentLocation[0]); // display at the new location.
        saveMap();
    }
    
    // called for when we are constantly going back...
    private void breadCrumbsLoop(){
        while(!explorable()) {
            breadCrumbs();
            markPoint(); // this is never run because we don't use the startExploration method while this is looping.
        }
    }
    
    // this is all gui markup stuff past this point.

    // used to mark points that have been visited.
    private void markPoint(){
        graphicsContext.setGlobalAlpha(0.33); // sets opacity for visited image drawing
        graphicsContext.drawImage(visited, (double)(currentLocation[2] * 45), (double)(currentLocation[1]*45));
        graphicsContext.setGlobalAlpha(1); // resets opacity for final image drawing.
        // this next part updates the step counter on the GUI.
        try {
            boolean ran = false;
            int counter = 0;
            while (!ran){ // don't question the loop, for some reason it is necessary.
                // so we put the logic thread to sleep until the fx thread has time to do what it needs to do
                Thread.sleep((long)100);
                Platform.runLater(new Runnable() { // this is the fx thread.
                    public void run() {
                        statusLbl.setText("Map State: solving... | Current floor " + currentLocation[0] +
                                " | Moves made: " + movesMade);
                    }
                });
                if (counter == 2) ran = true;
                counter ++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // used to make the logic thread sleep for the specified slider value.
    private void logicSleep() {
        try {
            Thread.sleep((long) (100 - slider.getValue()) * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // this is used to save the map via the fx thread.
    private void saveMap(){
        try {
            boolean ran = false;
            int counter = 0;
            while (!ran){ // don't question the loop, for some reason it is necessary. Its set up for the shortest run time.
                // so we put the logic thread to sleep until the fx thread has time to do what it needs to do
                Thread.sleep((long)10);
                Platform.runLater(new Runnable() { // this is the fx thread.
                    public void run() {
                        drawer.saveMap(currentLocation[0]);
                        statusLbl.setText("Map State: solving... | Current floor " + currentLocation[0] +
                                " | Moves made: " + movesMade);

                    }
                });
                if (counter == 1) ran = true;
                counter ++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
