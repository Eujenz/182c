package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Eulalia extends AgitInstance {
  public Eulalia() {
    super(262162);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 17;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33341 && door.getY() == 32711)
      door.OpenClose(open); 
    if (door.getX() == 33340 && door.getY() == 32703)
      door.OpenClose(open); 
  }
}
