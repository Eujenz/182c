package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Henrietta extends AgitInstance {
  public Henrietta() {
    super(262148);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33427 && door.getY() == 32659)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33427 && door.getY() == 32659)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 3;
  }
}
