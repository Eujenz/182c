package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Eve extends AgitInstance {
  public Eve() {
    super(262150);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33456 && door.getY() == 32647) {
      door.OpenClose(true);
    } else if (door.getX() == 33457 && door.getY() == 32655) {
      door.OpenClose(true);
    } 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33456 && door.getY() == 32647) {
      door.OpenClose(false);
    } else if (door.getX() == 33457 && door.getY() == 32655) {
      door.OpenClose(false);
    } 
  }
  
  public int getAgitLocationIdx() {
    return 5;
  }
}
