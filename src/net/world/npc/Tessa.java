package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Tessa extends AgitInstance {
  public Tessa() {
    super(262188);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 43;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33428 && door.getY() == 32872)
      door.OpenClose(open); 
    if (door.getX() == 33427 && door.getY() == 32864)
      door.OpenClose(open); 
  }
}
