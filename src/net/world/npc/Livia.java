package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Livia extends AgitInstance {
  public Livia() {
    super(262154);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33423 && door.getY() == 32684) {
      door.OpenClose(true);
    } else if (door.getX() == 33424 && door.getY() == 32692) {
      door.OpenClose(true);
    } 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33423 && door.getY() == 32684) {
      door.OpenClose(false);
    } else if (door.getX() == 33424 && door.getY() == 32692) {
      door.OpenClose(false);
    } 
  }
  
  public int getAgitLocationIdx() {
    return 9;
  }
}
