package MazeLogic;

import GUI.Maze.MapDrawer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
 * @version 9/8/16
 *
 */
public class MazeSolver{

    private char[][][] masterMaze;
    private int[] currentLocation;
    private MapDrawer drawer;
    private Slider slider;
    private GraphicsContext graphicsContext;
    private HashSet<Coordinate> visitedSpecial = new HashSet<>(); // visited portals & stairs
    private HashSet<Coordinate> visitedLocations = new HashSet<>(); // visited open spaces
    private ThompsonStack<Coordinate> FredFin = new ThompsonStack<>();


    /**
     * Constructor of the class.
     *
     * @param masterMaze 3d array of the loaded maze.
     * @param slider slider to obtain speed value
     * @param canvas for drawing to
     * @param drawer MapDrawer for drawing and saving the map.
     *
     */
    public MazeSolver(char[][][] masterMaze, Slider slider, Canvas canvas, MapDrawer drawer){
        this.masterMaze = masterMaze;
        this.slider = slider;
        this.drawer = drawer;
        this.graphicsContext = canvas.getGraphicsContext2D();

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

    // Returns the current location.
    public int[] getCurrentLocation() {
        return currentLocation;
    }

    // explores!!
    public void startExploration(){
        boolean done = false;
        Image visited = new Image(getClass().getResourceAsStream("/graphics/visited.png"));
        while (!done){
            // // // // TODO: 9/11/16 MAKE THIS RUN IN A SEPERATE THREAD SOMEHOW GOD DAMNIT
            try{
                graphicsContext.setGlobalAlpha(0.33); // sets opacity for visited image drawing
                graphicsContext.drawImage(visited, (double)(currentLocation[2] * 45), (double)(currentLocation[1]*45));
                graphicsContext.setGlobalAlpha(1); // resets opacity for final image drawing.
                System.out.println(visitedLocations);
                System.out.println("Current Location: " + currentLocation[0] + " " + currentLocation[1] + " " + currentLocation[2]);
                done = explore();
                Thread.sleep((long) (100 - slider.getValue()) * 10);
            }catch (Exception e){
                drawer.saveMap(currentLocation[0]); // saves the map where it breaks
                System.out.println("Something went wrong...");
                e.printStackTrace();
            }



        }
        graphicsContext.setGlobalAlpha(1); // resets opacity for final image drawing.
        drawer.saveMap(currentLocation[0]);
        if (Character.compare('*', masterMaze[currentLocation[0]][currentLocation[1]][currentLocation[2]]) == 0){
            System.out.println("You found the end at: " + currentLocation[0] + " " + currentLocation[1] + " " + currentLocation[2]);
        }
        else {
            System.out.println("Map unsolvable.");
        }
    }

    // // TODO: 9/9/16 Something is very wrong here...
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

        }
        else if (left().compareCharacter('.') &&
                visitedLocations.add(left())){
            System.out.println(left().getCharacter());
            FredFin.push(left());
            currentLocation = left().getCoords();
        }
        else if (below().compareCharacter('.') &&
                visitedLocations.add(below())){
            System.out.println(below().getCharacter());
            FredFin.push(below());
            currentLocation = below().getCoords();
        }
        else if (right().compareCharacter('.') &&
                visitedLocations.add(right())){
            System.out.println(right().getCharacter());
            FredFin.push(right());
            currentLocation = right().getCoords();
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
            currentLocation = above().getCoords();
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
            currentLocation = above().getCoords();
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
            Coordinate tempCoor = FredFin.pop();
            System.out.println("Poped: " + tempCoor.getCharacter() + " " + Arrays.toString(tempCoor.getCoords()));
            currentLocation = FredFin.peek().getCoords();
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
        graphicsContext.setGlobalAlpha(1); // sets opacity back to full for image save.
        drawer.saveMap(currentLocation[0]);
        for (int q = currentLocation[0] + 1; q < masterMaze.length + currentLocation[0]; q ++ ){

            try {
                if (Character.compare('+', masterMaze[q][currentLocation[1]][currentLocation[2]]) == 0){
                    currentLocation[0] = q;
                    drawer.displayLevel(currentLocation[0]);
                    break;
                }
            }catch (IndexOutOfBoundsException error){
                if (Character.compare('+', masterMaze[q - (currentLocation[0]  + 1)][currentLocation[1]][currentLocation[2]]) == 0){
                    currentLocation[0] = q - currentLocation[0] + 1;
                    drawer.displayLevel(currentLocation[0]);
                    break;
                }
            }
        }
        FredFin.push(new Coordinate('+', currentLocation));
    }

    private void itsActuallyALadder(){
        graphicsContext.setGlobalAlpha(1);
        drawer.saveMap(currentLocation[0]);
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
    }

    // checks to see if there any explorable positions
    // // TODO: 9/11/16 I think we can turn this whole thing into one giant or check.
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
