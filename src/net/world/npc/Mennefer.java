package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Mennefer extends AgitInstance {
  public Mennefer() {
    super(262166);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 21;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33404 && door.getY() == 32740)
      door.OpenClose(open); 
    if (door.getX() == 33403 && door.getY() == 32732)
      door.OpenClose(open); 
  }
}
