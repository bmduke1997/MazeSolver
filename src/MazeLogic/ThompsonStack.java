package MazeLogic;

class ThompsonStack<T> {
      private Node headNode;

      private class Node {
            private T data;
            private Node nextNode;

            Node(T c) {
                  data = c;
                  nextNode = null;
            }
      }

      ThompsonStack(){
          headNode = null;
      }

      boolean isEmpty(){
        return  headNode == null;
      }

      T peek()
      {
          return headNode.data;
      }

      T pop(){
        Node tempNode = headNode;
        headNode = headNode.nextNode;
        return tempNode.data;
      }

      void push(T data){
        Node newNode = new Node(data);
        newNode.nextNode = headNode;
        headNode = newNode;
      }

      void peekAll(){
          Node temp = headNode;
          Node temp2 = temp;
          while (temp.nextNode != null){
              temp = temp2;
              temp2 = temp2.nextNode;
              System.out.print(temp.data);
          }
      }
}
