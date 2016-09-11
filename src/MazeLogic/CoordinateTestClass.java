package MazeLogic;

import java.util.HashSet;

/**
 * CoordinateTestClass
 *
 * @author Patrick Shinn
 * @version 9/9/16
 */
public class CoordinateTestClass {
    
    // // TODO: 9/11/16 DELETE ME 

    public static void main(String[] args){
        HashSet<Coordinate> coordinateHashSet = new HashSet<>();
        Coordinate coordinate1 = new Coordinate('@', new int[] {1,5,0});
        Coordinate coordinate2 = new Coordinate('#', new int[] {1,5,0});
        Coordinate coordinate3 = new Coordinate('@', new int[] {1,5,0});
        Coordinate coordinate4 = new Coordinate('#', new int[] {1,5,0});
        Coordinate coordinate5 = new Coordinate('*', new int[] {1,7,0});

        System.out.println(coordinateHashSet);
        coordinateHashSet.add(coordinate1);
        System.out.println(coordinateHashSet);
        coordinateHashSet.add(coordinate2);
        System.out.println(coordinateHashSet);
        coordinateHashSet.add(coordinate3);
        System.out.println(coordinateHashSet);
        coordinateHashSet.add(coordinate4);
        System.out.println(coordinateHashSet);
        coordinateHashSet.add(coordinate5);
        System.out.println(coordinateHashSet);


    }
}
