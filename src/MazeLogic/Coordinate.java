package MazeLogic;

/**
 * Coordinate data type
 *
 * @author Patrick Shinn
 * @version 9/8/16
 */
public class Coordinate {
    char character;
    int[] coords;

    public Coordinate(char character, int[] coords){
        this.character = character;
        this.coords = coords;
    }

    public char getCharacter(){
        return character;
    }

    public int[] getCoords() {
        return coords;
    }
}
