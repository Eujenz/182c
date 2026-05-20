package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Beth extends AgitInstance {
  public Beth() {
    super(262147);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33395 && door.getY() == 32657) {
      door.OpenClose(true);
    } else if (door.getX() == 33394 && door.getY() == 32649) {
      door.OpenClose(true);
    } 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33395 && door.getY() == 32657) {
      door.OpenClose(false);
    } else if (door.getX() == 33394 && door.getY() == 32649) {
      door.OpenClose(false);
    } 
  }
  
  public int getAgitLocationIdx() {
    return 2;
  }
}
