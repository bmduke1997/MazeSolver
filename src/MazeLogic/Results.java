package MazeLogic;

/**
 * Results Class.
 * Used for returning results.
 *
 * @author Patrick Shinn
 * @version 9/11/16
 */
public class Results {
    private boolean success;
    private int movesMade;

    public Results(int movesMade, boolean success){
        this.movesMade = movesMade;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getMovesMade() {
        return movesMade;
    }
}
