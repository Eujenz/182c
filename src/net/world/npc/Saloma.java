package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Saloma extends AgitInstance {
  public Saloma() {
    super(262179);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 34;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33376 && door.getY() == 32829)
      door.OpenClose(open); 
    if (door.getX() == 33375 && door.getY() == 32821)
      door.OpenClose(open); 
  }
}
