package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Gleda extends AgitInstance {
  public Gleda() {
    super(262171);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 26;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33351 && door.getY() == 32776)
      door.OpenClose(open); 
  }
}
