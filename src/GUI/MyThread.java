package GUI;

import MazeLogic.MazeSolver;

/**
 * MyThread Class.
 *
 * @author Patrick Shinn
 * @version 9/12/16
 */
public class MyThread implements Runnable{

    private MazeSolver mazeSolver;

    public MyThread(MazeSolver mazeSolver){
        this.mazeSolver = mazeSolver;
    }

    @Override
    public void run() {
        mazeSolver.startExploration();
    }

    public MazeSolver getMazeSolver() {
        return mazeSolver;
    }
}
