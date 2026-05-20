package net.world.ai;

public class Node {
  public int f;
  
  public int h;
  
  public int g;
  
  public int x;
  
  public int y;
  
  public Node prev;
  
  public Node[] direct = new Node[8];
  
  public Node next;
  
  Node() {
    for (int i = 0; i < 8; i++)
      this.direct[i] = null; 
  }
}
