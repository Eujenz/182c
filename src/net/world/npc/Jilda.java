package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Jilda extends AgitInstance {
  public Jilda() {
    super(262155);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33409 && door.getY() == 32676)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33409 && door.getY() == 32676)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 10;
  }
}
