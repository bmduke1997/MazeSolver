package GUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * MapDrawer Class.
 * This class is responsible for drawing the map on the canvas in the GUI.
 *
 * @author Patrick Shinn
 * @version 9/10/16
 */
class MapDrawer {
    private char[][][] masterMaze;
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private Image[] levels = new Image[0]; // stores the levels as images to be written on.

    // all of the images that will be needed to draw the map.
    private Image wall = new Image(getClass().getResourceAsStream("/graphics/wall.png"));
    private Image path = new Image(getClass().getResourceAsStream("/graphics/openPath.png"));
    private Image portal = new Image(getClass().getResourceAsStream("/graphics/portal.png"));
    private Image ladder = new Image(getClass().getResourceAsStream("/graphics/ladder.png"));
    private Image start = new Image(getClass().getResourceAsStream("/graphics/Start.png"));
    private Image finish = new Image(getClass().getResourceAsStream("/graphics/Finish.png"));

    MapDrawer(Canvas canvas, char[][][] masterMaze){
        this.masterMaze = masterMaze;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
    }

    void displayLevel(int Level){

        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight()); // ensures we are drawing on a clear canvas.
        int zCounter = -1;
        int yCounter = 0;
        int xCounter = 0;
        for (char[][] z : masterMaze){
            // this is used for cycling through the levels. Once the desired level is reached, the loop breaks.
            if (zCounter == Level)break;
            zCounter ++;
            for (char[] y : z){
                if (zCounter != Level+1) // unless we are on the level we want, draw nothing.
                for (char x : y){
                    // based on what the character corresponds to, draw the item.
                    if (Character.compare(x, '#') == 0)graphicsContext.drawImage(wall, xCounter * 45,  yCounter * 45);
                    else if (Character.compare(x, '.') == 0)graphicsContext.drawImage(path, xCounter * 45, yCounter * 45);
                    else if (Character.compare(x, '+') == 0)graphicsContext.drawImage(portal, xCounter * 45, yCounter * 45);
                    else if (Character.compare(x, '=') == 0)graphicsContext.drawImage(ladder, xCounter * 45, yCounter * 45);
                    else if (Character.compare(x, '@') == 0)graphicsContext.drawImage(start, xCounter * 45, yCounter * 45);
                    else if (Character.compare(x, '*') == 0)graphicsContext.drawImage(finish, xCounter * 45, yCounter * 45);

                    xCounter ++;
                }
                yCounter ++;
                xCounter = 0;
            }
            yCounter = 0;
            xCounter = 0;
        }

    }

    void clearMap(){
        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight()); // clears the map.
    }
}
