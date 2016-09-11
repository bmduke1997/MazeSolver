package MazeLogic;
//// TODO: 9/8/16 DELETE ME EVENTUALLY
import java.util.*;
/**
 * L10-1 Tests the ThompsonStack class
 * 
 * @author J. Thompson 
 * @version 11-Mar-16
 */
public class ThompsonStackTest
{
  public static void main(String[] args)
  {
    Scanner myScanner = new Scanner(System.in);
    ThompsonStack<Character> myStack;
    myStack = new ThompsonStack<>();
    System.out.println("1 - Peek");
    System.out.println("2 - Pop");
    System.out.println("3 - Push");
    System.out.println("4 - Empty?");
    System.out.println("5 - exit");
    System.out.println("6 - fuck off");
    boolean done = false;
    while (!done)
    {
      System.out.print("> ");
      int choice = Integer.parseInt(myScanner.nextLine());
      if (choice == 1)
        System.out.println(myStack.peek());
      else if (choice == 2)
        System.out.println(myStack.pop());
      else if (choice == 3)
      {
        System.out.print("Enter character: ");
        char myChar = myScanner.nextLine().charAt(0);
        myStack.push(myChar);
      }
      else if (choice == 4)
        System.out.println(myStack.isEmpty());
      else if (choice == 5)
        done = true;
      else if (choice == 6){
          myStack.peekAll();
      }
      else 
        System.out.println("Invalid choice");
    }
      System.out.println("Goodbye!");
  }
}
