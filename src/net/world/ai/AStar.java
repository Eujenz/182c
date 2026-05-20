package net.world.ai;

import net.world.WorldMap;
import net.world.object.Character;
import net.world.object.L1Object;

public class AStar {
  Node OpenNode = null;
  
  Node ClosedNode = null;
  
  static final int LIMIT_LOOP = 200;
  
  public void ResetPath() {
    while (this.OpenNode != null) {
      Node tmp = this.OpenNode.next;
      this.OpenNode = null;
      this.OpenNode = tmp;
    } 
    while (this.ClosedNode != null) {
      Node tmp = this.ClosedNode.next;
      this.ClosedNode = null;
      this.ClosedNode = tmp;
    } 
  }
  
  public Node FindPath(Character npc, int tx, int ty) {
    Node best = null;
    int count = 0;
    int sx = npc.getX();
    int sy = npc.getY();
    int map = npc.getMap();
    Node src = new Node();
    src.g = 0;
    src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
    src.f = src.h;
    src.x = sx;
    src.y = sy;
    this.OpenNode = src;
    while (count < 200) {
      if (this.OpenNode == null)
        return null; 
      best = this.OpenNode;
      this.OpenNode = best.next;
      best.next = this.ClosedNode;
      this.ClosedNode = best;
      if (best.x == tx && best.y == ty)
        return best; 
      if (MakeChild(npc, best, tx, ty, map) == '\000' && count == 0)
        return null; 
      count++;
    } 
    return null;
  }
  
  char MakeChild(Character npc, Node node, int tx, int ty, int map) {
    //char flag = Character.MIN_VALUE;
    char flag = '\0';
    int x = node.x;
    int y = node.y;
    L1Object[] list = npc.getObjectList();
    for (int i = 0; i < 8; i++) {
      if (WorldMap.getInstance().IsThroughObject(x, y, map, i)) {
        int nx = x + npc.get_XY(i, true);
        int ny = y + npc.get_XY(i, false);
        boolean ck = true;
        if (tx != nx || ty != ny) {
          byte b;
          int j;
          L1Object[] arrayOfL1Object;
          for (j = (arrayOfL1Object = list).length, b = 0; b < j; ) {
            L1Object o = arrayOfL1Object[b];
            if (o instanceof Character && !o.isDead() && nx == o.getX() && ny == o.getY())
              ck = false; 
            b++;
          } 
        } 
        if (ck) {
          MakeChildSub(node, nx, ny, tx, ty);
          flag = '\001';
        } 
      } 
    } 
    return flag;
  }
  
  void MakeChildSub(Node node, int x, int y, int tx, int ty) {
    Node old = null;
    Node child = null;
    int g = node.g + 1;
    if ((old = IsOpen(x, y)) != null) {
      for (int i = 0; i < 8; i++) {
        if (node.direct[i] == null) {
          node.direct[i] = old;
          break;
        } 
      } 
      if (g < old.g) {
        old.prev = node;
        old.g = g;
        old.f = old.h + old.g;
      } 
    } else if ((old = IsClosed(x, y)) != null) {
      for (int i = 0; i < 8; i++) {
        if (node.direct[i] == null) {
          node.direct[i] = old;
          break;
        } 
      } 
      if (g < old.g) {
        old.prev = node;
        old.g = g;
        old.f = old.h + old.g;
      } 
    } else {
      child = new Node();
      child.prev = node;
      child.g = g;
      child.h = (x - tx) * (x - tx) + (y - ty) * (y - ty);
      child.f = child.h + child.g;
      child.x = x;
      child.y = y;
      InsertNode(child);
      for (int i = 0; i < 8; i++) {
        if (node.direct[i] == null) {
          node.direct[i] = child;
          break;
        } 
      } 
    } 
  }
  
  Node IsOpen(int x, int y) {
    Node tmp = this.OpenNode;
    while (tmp != null) {
      if (tmp.x == x && tmp.y == y)
        return tmp; 
      tmp = tmp.next;
    } 
    return null;
  }
  
  Node IsClosed(int x, int y) {
    Node tmp = this.ClosedNode;
    while (tmp != null) {
      if (tmp.x == x && tmp.y == y)
        return tmp; 
      tmp = tmp.next;
    } 
    return null;
  }
  
  void InsertNode(Node src) {
    Node old = null;
    Node tmp = null;
    if (this.OpenNode == null) {
      this.OpenNode = src;
      return;
    } 
    tmp = this.OpenNode;
    while (tmp != null && tmp.f < src.f) {
      old = tmp;
      tmp = tmp.next;
    } 
    if (old != null) {
      src.next = tmp;
      old.next = src;
    } else {
      src.next = tmp;
      this.OpenNode = src;
    } 
  }
}
