package GUI;

import GUI.Maze.MapDrawer;
import GUI.Windows.AboutWindow;
import GUI.Windows.WarningWindow;
import MazeLogic.MazeSolver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Patrick Shinn
 * @author Brandon Duke
 * @author Claire Wallace
 * @version Alpha 0.5
 *
 * This is the controller class for the GUI.fxml. This code does all of the heavy lifting for the GUI.
 */
public class Controller{
    // data structures / logic stuff
    private char[][][] masterMaze; // the map as a 3d array for coordinate purposes.
    private boolean run = false;
    private int currentLevel = 0; // used for changing the level that is currently displayed.


    //GUI elements, can be called by using @FXML then defining an object with the same name as the FXid.
    //--------------------------------------------------------------------------------
    private Stage primaryStage; // the primary stage of the GUI, obtained through setStage method.

    //Buttons
    @FXML private Button lvlUp;
    @FXML private Button lvlDown;
    @FXML private Button start;
    @FXML private Button load;

    // Labels
    @FXML private Label statusLbl;

    // Slider
    @FXML private Slider slider;

    // Map GUI Stuff
    @FXML private Canvas canvas;
    private MapDrawer drawer;

    // used to extract the primary stage from the Main Class.
    void setStage(Stage stage){
        this.primaryStage = stage;
    }

    // Load method, loads the maps into a 3d array.
    public void load() {
        FileChooser chooser = new FileChooser();
        Scanner scanner;
        char[][] maze = new char[0][0];
        char[][][] masterMazeHolder = {maze};
        int mazeCounter = 0;
        int masterCounter = 0;

        chooser.setTitle("Open a Map");
        // sets the extension filter types
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Map Files", "*.map") // custom extension, just for fun.
        );

        File map = chooser.showOpenDialog(primaryStage);// opens file chooser box to main stage.
        statusLbl.setText("Status: Loading " + map.getName() + "...");

        masterMaze = new char[0][0][0]; // ensures that the previous maze has been dumped.

        try { // builds the array from the file
            scanner = new Scanner(map);
            

            while (scanner.hasNextLine()){ // so while we still have lines to read
                String line = scanner.nextLine();
                if (line.contains("--")){ // if we have reached the end of a maze
                    masterMazeHolder = Arrays.copyOf(masterMazeHolder, masterCounter + 1); // expand the masterMazeHolder array
                    masterMazeHolder[masterCounter] = maze; // insert maze
                    maze = Arrays.copyOf(maze, 0); // dump maze
                    masterCounter ++; // increase masterCounter
                    mazeCounter = 0; //reset mazeCounter for next run.


                }else{ // so if we are in a level of the maze
                    char[] row = line.toCharArray(); // split each line of the maze into an array.
                    maze = Arrays.copyOf(maze, mazeCounter + 1); // expand the maze array
                    maze[mazeCounter] = row; // insert into the maze array
                    mazeCounter ++; // increase the counter.

                }
            }
            masterMazeHolder = Arrays.copyOf(masterMazeHolder, masterCounter + 1); // expand the masterMazeHolder array one last time
            masterMazeHolder[masterCounter] = maze; // get the last maze.
        }catch (FileNotFoundException e){
            //congrats, this doesnt matter... simply appeasing the compiler.
        }

        this.masterMaze = masterMazeHolder; //makes the newly made 3D array available to other methods.

        // draws the newly loaded map.
        drawer = new MapDrawer(canvas, masterMazeHolder);
        drawer.displayLevel(0);

        // updates the gui buttons
        currentLevel = 0;
        lvlDown.setDisable(true);
        lvlUp.setDisable(false);

        // Status updates
        statusLbl.setText("Loaded " + map.getName() + ".");
        run = true; // we loaded a file, so now we can run through the maze.
    }

    // Starts the search algorithm.
    public void start(){
        // checks to see if a maze is actually loaded...
        if (!run){
            WarningWindow warningWindow = new WarningWindow(primaryStage, "NOTHING LOADED", "You have not loaded anything...");
            warningWindow.display();
        }
        else{
            //solve the maze.
            statusLbl.setText("Running maze...");
            MazeSolver mazeSolver = new MazeSolver(masterMaze, slider, canvas, drawer, statusLbl);

            // disables buttons so the user cant screw stuff up.
            start.setDisable(true);
            load.setDisable(true);
            lvlDown.setDisable(true);
            lvlUp.setDisable(true);

            Thread one = new Thread() {
                public void run() { // logic thread!
                    currentLevel = mazeSolver.getCurrentLocation()[0];
                    drawer.displayLevel(mazeSolver.getCurrentLocation()[0]); // displays the location of start on the map.
                    // modifying the buttons accordingly
                    mazeSolver.startExploration();
                    currentLevel = mazeSolver.getCurrentLocation()[0];
                    Platform.runLater(new Runnable() { // updates the buttons for new usage.
                        @Override
                        public void run() { // re enable all the buttons!
                            if (currentLevel == 0){
                                lvlDown.setDisable(true);
                                lvlUp.setDisable(false);
                            }
                            else if (currentLevel == masterMaze.length - 1){
                                lvlUp.setDisable(true);
                                lvlDown.setDisable(false);
                            }else{
                                lvlUp.setDisable(false);
                                lvlDown.setDisable(false);
                            }
                            start.setDisable(false);
                            load.setDisable(false);
                        }
                    });

                }
            };
            one.start();
        }

    }

    //clears the map from the gui. Has a keyboard shortcut of ctrl + shift + c
    public void clearScreen(){
        // code to clear GUI
        if (!run){
            WarningWindow warning = new WarningWindow(primaryStage, "Unable to Clear",
                    "There is nothing loaded, so there is nothing to clear.");
            warning.display();
        }else{
            drawer.clearMap();
            statusLbl.setText("Status: Nothing Loaded.");
            masterMaze = new char[0][0][0];
            run = false;
            lvlDown.setDisable(true);
            lvlUp.setDisable(true);
        }
    }

    // displays the next level down
    public void displayLevelDown(){
        currentLevel --;
        drawer.displayLevel(currentLevel);
        lvlUp.setDisable(false);
        if (currentLevel == 0)lvlDown.setDisable(true); // if we are at the bottom, make it impossible to go lower.
    }

    // displays the next level up
    public void displayLevelUp(){
        currentLevel ++;
        drawer.displayLevel(currentLevel);
        lvlDown.setDisable(false);
        if(currentLevel == masterMaze.length -1)lvlUp.setDisable(true); // if we are at the top level, make it impossible to go up again.

    }

    //Shows about info screen from help menu
    public void about(){
        AboutWindow aboutWindow = new AboutWindow("About", "Maze Solver", "1.0",
                "This is the most amazing maze crawler that world will ever know!\n" +
                        "Let it be known that this is a fact and has been scientifically proven.",
                "Awesome Team", "https://github.com/shinn16/MazeSolver");
        aboutWindow.display();
    }
}