package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Duana extends AgitInstance {
  public Duana() {
    super(262186);
  }
  
  public void OpenDoor(DoorInstance door) {
    OpenCloseDoor(door, true);
  }
  
  public void CloseDoor(DoorInstance door) {
    OpenCloseDoor(door, false);
  }
  
  public int getAgitLocationIdx() {
    return 41;
  }
  
  private void OpenCloseDoor(DoorInstance door, boolean open) {
    if (door.getX() == 33414 && door.getY() == 32853)
      door.OpenClose(open); 
  }
}
