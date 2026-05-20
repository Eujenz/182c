package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Tekla extends AgitInstance {
  public Tekla() {
    super(262157);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 12;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33375 && door.getY() == 32699) {
      door.OpenClose(open);
    } else if (door.getX() == 33374 && door.getY() == 32691) {
      door.OpenClose(open);
    } 
  }
}
