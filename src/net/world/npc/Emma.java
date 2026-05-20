package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Emma extends AgitInstance {
  public Emma() {
    super(262145);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33373 && door.getY() == 32657)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33373 && door.getY() == 32657)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 0;
  }
}
