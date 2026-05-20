package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Valeska extends AgitInstance {
  public Valeska() {
    super(262151);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33476 && door.getY() == 32668)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33476 && door.getY() == 32668)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 6;
  }
}
