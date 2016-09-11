package GUI.Maze;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * MazeLevel Class.
 * Data type for use with the MapDrawer Class.
 *
 * @author Patrick Shinn
 * @version 9/11/16
 */
class MazeLevel {

    private WritableImage snapShot;
    private int width;
    private int height;

    MazeLevel(Canvas canvas, int width, int height){
        WritableImage writableImage = new WritableImage(width, height);
        this.width = width;
        this.height = height;
        SnapshotParameters parameters = new SnapshotParameters();
        this.snapShot = canvas.snapshot(parameters, writableImage);

    }
    Image getLevelMap(){
        return snapShot;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }
}