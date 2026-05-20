package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Sarah extends AgitInstance {
  public Sarah() {
    super(262156);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33419 && door.getY() == 32709)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33419 && door.getY() == 32709)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 11;
  }
}
