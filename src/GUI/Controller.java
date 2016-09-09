package GUI;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    // data structures / logic stuff
    private char[][][] masterMaze; // the map as a 3d array for coordinate purposes.
    private boolean run = false;


    //GUI elements, can be called by using @FXML then defining an object with the same name as the FXid.
    //--------------------------------------------------------------------------------
    private Stage primaryStage; // the primary stage of the GUI, obtained through setStage method.

    // Buttons
    @FXML private Button loadBtn;
    @FXML private Button startBtn;

    // Labels
    @FXML private Label statusLbl;

    // Slider
    @FXML private Slider slider;

    // Map GUI Stuff
    @FXML private Canvas mapCanvas;


    // used to extract the primary stage from the Main Class.
    public void setStage(Stage stage){
        this.primaryStage = stage;
    }
    private GraphicsContext graphicsContext;

    // Load method, loads the maps into a 3d array.
    // // TODO: 9/2/16 make this load the map into the GUI 
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

        try {
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
        
        //// TODO: 9/2/16 more debug stuff to delete 
        for ( char[][] z : masterMazeHolder){
            for (char[] y : z ){
                for (char x : y){
                    System.out.print(x);
                }
                System.out.println();
            }
            System.out.println("---------------------------------");
        }
        statusLbl.setText("Loaded " + map.getName() + ".");
        run = true;
    }

    // Starts the search algorithm.
    //// TODO: 9/7/16 make method.
    public void start(){
        int[] location = {-1,-1,-1};
        boolean startFound = false;
        // checks to see if a maze is actually loaded...
        if (!run){
            WarningWindow warningWindow = new WarningWindow(primaryStage, "NOTHING LOADED", "You have not loaded anything...");
            warningWindow.display();
        }
        else{
            //Find the start position
            for ( char[][] z : masterMaze){
                if (startFound) break;
                location[0] = location[0] + 1;
                location[1] = -1; //new maze reset y val
                for (char[] y : z ){
                    if (startFound) break;
                    location[1] = location[1] + 1;
                    location[2] = -1; // new row in maze reset x val
                    for (char x : y){
                        location[2] = location[2] + 1;
                        if (Character.compare(x,'@') == 0){
                            startFound=true;
                            break;
                        }
                    }
                }
            }
            System.out.println("star is: " + location[0] + " " + location[1] + " " + location[2]);

            //Find exit Algorithm

        }

        //// TODO: 9/8/16 REMOVE THIS TEST CODE
        this.graphicsContext = mapCanvas.getGraphicsContext2D();
        graphicsContext.strokeArc(50.00, 50.00, 50.00, 50.00, 360.00, 360.00, ArcType.ROUND);
    }

    //clears the map from the gui. Has a keyboard shortcut of ctrl + shift + c
    //// TODO: 9/2/16 make this method
    public void clearScreen(){
        // code to clear GUI
        statusLbl.setText("Status: Nothing Loaded.");
        masterMaze = new char[0][0][0];

    }

    //// TODO: 9/8/16 DELTE ME
    public void test(){
        System.out.println(slider.getValue());
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