package GUI.Maze;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Arrays;

/**
 * MapDrawer Class.
 * This class is responsible for drawing the map on the canvas in the GUI,
 * as well as maintaining the map.
 *
 * @author Patrick Shinn
 * @version 9/10/16
 */
public class MapDrawer {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private char[][][] masterMaze;
    private MazeLevel[] levels = new MazeLevel[0]; // stores the levels as images to be written on.



    public MapDrawer(Canvas canvas, char[][][] masterMaze, String mapPack){
        // sets the canvas and graphicsContext
        this.masterMaze =masterMaze;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();

        // all of the images that will be needed to draw the map.
        Image wall = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/wall.png"));
        Image path = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/openPath.png"));
        Image portal = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/portal.png"));
        Image ladder = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/ladder.png"));
        Image start = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/Start.png"));
        Image finish = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/Finish.png"));

        // the counters are for keeping track of the indexes since this is not doable in the for each loops.
        int zCounter = 0;
        int yCounter = 0;
        int xCounter = 0;

        int imageX = 0;
        int imageY;
        for (char[][] z : masterMaze){
            clearMap();
            imageY = z.length * 30;
            for (char[] y : z){
                imageX = y.length * 30;
                    for (char x : y){
                        // based on what the character corresponds to, draw the item.
                        if (Character.compare(x, '#') == 0)graphicsContext.drawImage(wall, xCounter * 30,  yCounter * 30);
                        else if (Character.compare(x, '.') == 0)graphicsContext.drawImage(path, xCounter * 30, yCounter * 30);
                        else if (Character.compare(x, '+') == 0)graphicsContext.drawImage(portal, xCounter * 30, yCounter * 30);
                        else if (Character.compare(x, '=') == 0)graphicsContext.drawImage(ladder, xCounter * 30, yCounter * 30);
                        else if (Character.compare(x, '@') == 0)graphicsContext.drawImage(start, xCounter * 30, yCounter * 30);
                        else if (Character.compare(x, '*') == 0)graphicsContext.drawImage(finish, xCounter * 30, yCounter * 30);

                        xCounter ++;
                    }
                yCounter ++;
                xCounter = 0;
            }
            yCounter = 0;
            xCounter = 0;
            levels = Arrays.copyOf(levels, levels.length + 1);
            levels[zCounter] = new MazeLevel(canvas, imageX , imageY); // inserts the newly made image into the array.
            zCounter ++;
        }

    }
    public void reDraw(String mapPack){
        this.levels = new MazeLevel[0]; // we dump the array so that we can make the new theme.
        // all of the images that will be needed to draw the map.
        Image wall = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/wall.png"));
        Image path = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/openPath.png"));
        Image portal = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/portal.png"));
        Image ladder = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/ladder.png"));
        Image start = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/Start.png"));
        Image finish = new Image(getClass().getResourceAsStream("/graphics/"+mapPack+"/Finish.png"));

        // the counters are for keeping track of the indexes since this is not doable in the for each loops.
        int zCounter = 0;
        int yCounter = 0;
        int xCounter = 0;

        int imageX = 0;
        int imageY;
        for (char[][] z : masterMaze){
            clearMap();
            imageY = z.length * 30;
            for (char[] y : z){
                imageX = y.length * 30;
                for (char x : y){
                    // based on what the character corresponds to, draw the item.
                    if (Character.compare(x, '#') == 0)graphicsContext.drawImage(wall, xCounter * 30,  yCounter * 30);
                    else if (Character.compare(x, '.') == 0)graphicsContext.drawImage(path, xCounter * 30, yCounter * 30);
                    else if (Character.compare(x, '+') == 0)graphicsContext.drawImage(portal, xCounter * 30, yCounter * 30);
                    else if (Character.compare(x, '=') == 0)graphicsContext.drawImage(ladder, xCounter * 30, yCounter * 30);
                    else if (Character.compare(x, '@') == 0)graphicsContext.drawImage(start, xCounter * 30, yCounter * 30);
                    else if (Character.compare(x, '*') == 0)graphicsContext.drawImage(finish, xCounter * 30, yCounter * 30);

                    xCounter ++;
                }
                yCounter ++;
                xCounter = 0;
            }
            yCounter = 0;
            xCounter = 0;
            levels = Arrays.copyOf(levels, levels.length + 1);
            levels[zCounter] = new MazeLevel(canvas, imageX , imageY); // inserts the newly made image into the array.
            zCounter ++;
        }
    }


    public MazeLevel[] getLevels() {
        return levels;
    }

    public void displayLevel(int Level){ // displays a map level
        clearMap(); // ensures drawling to a clear map
        graphicsContext.drawImage(levels[Level].getLevelMap(), 0, 0);

    }

    public void saveMap(int Level){ // saves an edited map
        levels[Level] = new MazeLevel(canvas, levels[Level].getWidth(), levels[Level].getHeight());
    }

    public void clearMap(){
        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight()); // clears the map.
    }
}
