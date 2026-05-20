package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Diane extends AgitInstance {
  public Diane() {
    super(262146);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33381 && door.getY() == 32657) {
      door.OpenClose(true);
    } else if (door.getX() == 33384 && door.getY() == 32656) {
      door.OpenClose(true);
    } 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33381 && door.getY() == 32657) {
      door.OpenClose(false);
    } else if (door.getX() == 33384 && door.getY() == 32656) {
      door.OpenClose(false);
    } 
  }
  
  public int getAgitLocationIdx() {
    return 1;
  }
}
