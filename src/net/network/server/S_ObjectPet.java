package net.network.server;

import java.util.List;
import net.world.instance.ItemInstance;
import net.world.instance.PetInstance;
import net.world.instance.SummonInstance;
import net.world.instance.inventory.function.DogCollar;

public class S_ObjectPet extends S_BasePacket {
  public S_ObjectPet(List<ItemInstance> list, int npcId) {
    writeC(49);
    writeD(npcId);
    int count = 0;
    for (ItemInstance item : list) {
      DogCollar dc = (DogCollar)item;
      if (dc.getPet() == null)
        count++; 
    } 
    writeH(count);
    writeC(12);
    for (ItemInstance item : list) {
      DogCollar dc = (DogCollar)item;
      if (dc.getPet() == null) {
        writeD(dc.getInvID());
        writeC(dc.getItem().getType1());
        writeH(dc.getItem().get_gfxid());
        writeC(dc.getBless());
        writeD(1);
        writeC(dc.isDefinite() ? 1 : 0);
        StringBuilder sb = new StringBuilder();
        sb.append(dc.getName());
        sb.append(" [Lv.");
        sb.append(dc.getPetLevel());
        sb.append(" ");
        sb.append(dc.getPetName());
        sb.append("]");
        writeS(sb.toString());
      } 
    } 
    writeD(80);
  }
  
  public S_ObjectPet(PetInstance pet) {
    writeC(42);
    writeD(pet.getObjectId());
    writeS("anicom");
    writeC(0);
    writeH(10);
    switch (pet.get_Status()) {
      case 1:
        writeS("  $469");
        break;
      case 2:
        writeS("  $470");
        break;
      case 3:
        writeS("  $476");
        break;
      case 4:
        writeS("  $472");
        break;
      case 5:
        writeS("  $613");
        break;
      default:
        writeS("  $471");
        break;
    } 
    writeS(String.valueOf(pet.getCurrentHp()));
    writeS(String.valueOf(pet.getTotalHp()));
    writeS(String.valueOf(pet.getCurrentMp()));
    writeS(String.valueOf(pet.getTotalMp()));
    writeS(String.valueOf(pet.getLevel()));
    writeS(pet.getName());
    writeS(pet.getFoodStatus());
    writeS(pet.getExpPercentage());
    writeS(String.valueOf(pet.getLawful() - 65536));
  }
  
  public S_ObjectPet(SummonInstance sum) {
    writeC(42);
    writeD(sum.getObjectId());
    writeS("moncom");
    writeH(9);
    switch (sum.get_Status()) {
      case 1:
        writeS("  $469");
        break;
      case 2:
        writeS("  $470");
        break;
      case 3:
        writeS("  $476");
        break;
      case 4:
        writeS("  $472");
        break;
      case 5:
        writeS("  $613");
        break;
      default:
        writeS("  $471");
        break;
    } 
    writeS(String.valueOf(sum.getCurrentHp()));
    writeS(String.valueOf(sum.getTotalHp()));
    writeS(String.valueOf(sum.getCurrentMp()));
    writeS(String.valueOf(sum.getTotalMp()));
    writeS(String.valueOf(sum.getLevel()));
    writeS(sum.getName());
    writeS("0");
    writeS("792");
  }
  
  public S_ObjectPet(SummonInstance pet, int type) {
    switch (type) {
      case 0:
        writeC(87);
        writeD(pet.getObjectId());
        writeC(0);
        break;
      case 1:
        writeC(88);
        writeD(pet.getObjectId());
        writeS(pet.getName());
        break;
    } 
  }
}
