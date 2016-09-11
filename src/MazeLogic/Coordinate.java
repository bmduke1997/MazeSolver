package MazeLogic;

import java.util.Arrays;

/**
 * Coordinate data type
 * This entire class is package private.
 *
 * @author Patrick Shinn
 * @version 9/8/16
 */

// // TODO: 9/9/16 make usable with the hashSet 
class Coordinate {
    private char character;
    private int[] coords;

    Coordinate(char character, int[] coords) {
        this.character = character;
        this.coords = coords;
    }

    char getCharacter() {
        return character;
    }

    int[] getCoords() {
        return coords;
    }

    boolean compareCharacter(char character) {
        int success = Character.compare(this.character, character);
        return success == 0;
    }

    // These methods are implemented so that this class can effectively be used in a hashSet.

    @Override
    public int hashCode() {
        return Arrays.hashCode(coords);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) object;
            return compareCharacter(coordinate.getCharacter()) && Arrays.equals(coords, coordinate.getCoords());
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return coords[0] + "" + coords[1] + "" + coords[2];
    }
}

