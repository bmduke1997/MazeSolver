package MazeLogic;

/**
 * Coordinate data type
 * This entire class is package private.
 *
 * @author Patrick Shinn
 * @version 9/8/16
 */
class Coordinate {
    private char character;
    private int[] coords;

    Coordinate(char character, int[] coords){
        this.character = character;
        this.coords = coords;
    }

    char getCharacter(){
        return character;
    }

    int[] getCoords() {
        return coords;
    }

    boolean compareCharacter(char character){
        int success = Character.compare(this.character, character);
        return success == 0;
    }
}
