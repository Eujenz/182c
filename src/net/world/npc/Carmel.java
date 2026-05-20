package net.world.npc;

import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;

public class Carmel extends AgitInstance {
  public Carmel() {
    super(262152);
  }
  
  public void OpenDoor(DoorInstance door) {
    if (door.getX() == 33471 && door.getY() == 32680)
      door.OpenClose(true); 
  }
  
  public void CloseDoor(DoorInstance door) {
    if (door.getX() == 33471 && door.getY() == 32680)
      door.OpenClose(false); 
  }
  
  public int getAgitLocationIdx() {
    return 7;
  }
}
