package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Sigrid extends AgitInstance {
  public Sigrid() {
    super(262187);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 42;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33375 && door.getY() == 32874)
      door.OpenClose(open); 
    if (door.getX() == 33374 && door.getY() == 32866)
      door.OpenClose(open); 
  }
}
