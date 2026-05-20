package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Margery extends AgitInstance {
  public Margery() {
    super(262170);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 25;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33366 && door.getY() == 32762)
      door.OpenClose(open); 
    if (door.getX() == 33365 && door.getY() == 32754)
      door.OpenClose(open); 
  }
}
