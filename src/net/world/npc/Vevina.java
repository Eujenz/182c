package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Vevina extends AgitInstance {
  public Vevina() {
    super(262153);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33458 && door.getY() == 32700)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33458 && door.getY() == 32700)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 8;
  }
}
