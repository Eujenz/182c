package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Teodora extends AgitInstance {
  public Teodora() {
    super(262169);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 24;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33404 && door.getY() == 32757)
      door.OpenClose(open); 
  }
}
