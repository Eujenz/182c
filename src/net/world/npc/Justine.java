package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Justine extends AgitInstance {
  public Justine() {
    super(262149);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33439 && door.getY() == 32667)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33439 && door.getY() == 32667)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 4;
  }
}
