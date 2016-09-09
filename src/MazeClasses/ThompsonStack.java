package MazeClasses;

public class ThompsonStack
{
  private Node headNode;
  private class Node
  {
    private Character data;
    private Node nextNode;
    public Node(Character c)
    {
      data = c;
      nextNode = null;
    }
  }
  public ThompsonStack()
  {
    headNode = null;
  }
  public boolean isEmpty()
  {
    if (headNode == null)
      return true;
    return false;
  }
  public Character peek()
  {
      return headNode.data;
  }
  public Character pop()
  {
    Node tempNode = headNode;
    headNode = headNode.nextNode;
    return tempNode.data;
  }
  public void push(Character data)
  {
    Node newNode = new Node(data);
    newNode.nextNode = headNode;
    headNode = newNode;
  }
}
