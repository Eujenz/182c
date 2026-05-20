package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Leonora extends AgitInstance {
  public Leonora() {
    super(262183);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 38;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33460 && door.getY() == 32835)
      door.OpenClose(open); 
  }
}
