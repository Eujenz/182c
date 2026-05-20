package net.world.instance;

import java.util.List;
import net.database.MonsterTable;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectPet;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.function.SummonSystem;
import net.world.function.bean.Summon;
import net.world.instance.inventory.Inventory;
import net.world.instance.inventory.function.DogCollar;
import net.world.object.L1Object;

public class PetShopInstance extends L1Object {
  protected String html;
  
  public PetShopInstance(String html) {
    this.html = html;
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    List<ItemInstance> list = pc.getInventory().getItemNameId(1173);
    boolean print = true;
    if (text1.equalsIgnoreCase("withdrawnpc")) {
      if (list != null && list.size() > 0)
        for (ItemInstance item : list) {
          DogCollar dc = (DogCollar)item;
          if (dc.getPet() == null && !dc.isDeleteDb()) {
            pc.SendPacket((S_BasePacket)new S_ObjectPet(list, getObjectId()));
            print = false;
            break;
          } 
        }  
      if (print)
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "3")); 
    } else if (text1.equalsIgnoreCase("depositnpc")) {
      if (list != null && list.size() > 0) {
        for (ItemInstance item : list) {
          DogCollar dc = (DogCollar)item;
          PetInstance pet = dc.getPet();
          if (pet != null && !pet.isDead()) {
            print = false;
            pet.updateDB();
            Inventory inv = pet.getInventory();
            if (inv != null) {
              ItemInstance[] ii = inv.getAll();
              byte b;
              int i;
              ItemInstance[] arrayOfItemInstance1;
              for (i = (arrayOfItemInstance1 = ii).length, b = 0; b < i; ) {
                ItemInstance temp = arrayOfItemInstance1[b];
                inv.remove(temp);
                temp.toTeleport(pet.getX(), pet.getY(), pet.getMap());
                b++;
              } 
            } 
            SummonSystem.getInstance().getSummon(pc).remove(pet);
            dc.setPet(null);
          } 
        } 
      } else {
        print = true;
      } 
      if (print)
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "4")); 
    } 
  }
  
  public void PetGet(C_BasePacket data, PcInstance pc) {
    int count = data.readH();
    while (count-- > 0 && SummonSystem.getInstance().isSummon(pc, true)) {
      if (pc.getInventory().Aden(80L, true)) {
        int inv_id = data.readD();
        data.readD();
        ItemInstance item = pc.getInventory().getItemInvId(inv_id);
        if (item == null || !(item instanceof DogCollar))
          break; 
        DogCollar dc = (DogCollar)item;
        if (dc.getPet() != null || dc.isDeleteDb())
          break; 
        Summon s = SummonSystem.getInstance().getSummon(pc);
        PetInstance pet = new PetInstance(MonsterTable.getInstance().getMonsterNameId(dc.getPetClassId()), pc);
        pet.setReceive(true);
        pet.setObjectId(dc.getPetObjectId());
        pet.setHeading(0);
        pet.readDB();
        pet.toTeleport(pc.getX(), pc.getY(), pc.getMap());
        s.add(pet);
        dc.setPet(pet);
        pet.setReceive(false);
        continue;
      } 
      pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
      break;
    } 
  }
}
