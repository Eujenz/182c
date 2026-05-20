package net.world.instance.inventory;

import net.world.instance.NpcInstance;

public class NpcInventory extends Inventory {
  private NpcInstance npc;
  
  public NpcInventory(NpcInstance npc) {
    this.npc = npc;
  }
  
  public void clear() {
    this.list.clear();
  }
}
