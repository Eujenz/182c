package net.world.instance.inventory;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.SlimeRaceTicket;
import net.world.object.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Inventory {
  final Logger log = LoggerFactory.getLogger(Inventory.class);
  
  protected List<ItemInstance> list;
  
  public Inventory() {
    this.list = new ArrayList<ItemInstance>();
  }
  
  public ItemInstance[] getAll() {
    return this.list.<ItemInstance>toArray(new ItemInstance[this.list.size()]);
  }
  
  public int getCount() {
    return this.list.size();
  }
  
  public boolean insert(ItemInstance item, long count) {
    if (item.getCount() >= count && count > 0L) {
      ItemInstance temp = isItem(item);
      if (getCount() >= 180 && temp == null) {
        SendPacket((S_BasePacket)new S_ServerMessage(263));
        return false;
      } 
      if (!isWeight((int)(item.getItem().getWeight() * count))) {
        SendPacket((S_BasePacket)new S_ServerMessage(82));
        return false;
      } 
      if (temp == null) {
        if (item.getItem().isPiles()) {
          temp = item.clone();
          temp.setCount(count);
          add(temp);
        } else {
          int c = (int)count;
          while (c-- > 0) {
            temp = item.clone();
            temp.setCount(1L);
            add(temp);
          } 
        } 
      } else {
        temp.setCount(temp.getCount() + count);
        SendPacket((S_BasePacket)new S_InventoryStatus(temp));
      } 
      item.setCount(item.getCount() - count);
      return true;
    } 
    return false;
  }
  
  public void add(ItemInstance item) {
    this.list.add(item);
    item.setCha(getCha());
  }
  
  public void remove(ItemInstance item) {
    this.list.remove(item);
    item.setCha(null);
  }
  
  public void delete() {
    this.list.clear();
    this.list = null;
  }
  
  public void clear() {}
  
  public void sendList() {}
  
  public void save() {}
  
  public void read() {}
  
  public ItemInstance getItemInvId(int invId) {
    for (ItemInstance item : getAll()) {
      if (item.getInvID() == invId)
        return item; 
    } 
    return null;
  }
  
  public boolean isItem(String nameID) {
    boolean flag = false;
    for (ItemInstance item : getAll()) {
      if (item.getName() == nameID) {
        flag = true;
        break;
      } 
    } 
    return flag;
  }
  
  public boolean isItem(int db_id) {
    boolean flag = false;
    for (ItemInstance item : getAll()) {
      if (item.getItem().getItemId() == db_id) {
        flag = true;
        break;
      } 
    } 
    return flag;
  }
  
  public List<ItemInstance> getItemDbId(int db_id) {
    List<ItemInstance> list = new ArrayList<ItemInstance>();
    try {
      ItemInstance[] itemList = getAll();
      if (itemList != null) {
        for (ItemInstance item : itemList) {
          Item temp = item.getItem();
          if (temp.getItemId() == db_id)
            list.add(item); 
        } 
        if (list.size() > 0)
          return list; 
      } 
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
    return null;
  }
  
  public List<ItemInstance> getItemNameId(int name_id) {
    List<ItemInstance> list = new ArrayList<ItemInstance>();
    for (ItemInstance item : getAll()) {
      if (item.getItem().get_nameidN() == name_id)
        list.add(item); 
    } 
    if (list.size() > 0)
      return list; 
    return null;
  }
  
  public ItemInstance getItemNameId(String nameid) {
    for (ItemInstance item : getAll()) {
      if (item != null && item.getName().equalsIgnoreCase(nameid))
        return item; 
    } 
    return null;
  }
  
  public List<ItemInstance> getName(int id) {
    List<ItemInstance> list = new ArrayList<ItemInstance>();
    for (ItemInstance item : getAll()) {
      if (item.getItem().getItemId() == id)
        list.add(item); 
    } 
    if (list.size() > 0)
      return list; 
    return null;
  }
  
  public ItemInstance isItem(int db_id, int bless) {
    for (ItemInstance item : getAll()) {
      if (item.getItem().isPiles() && item.getItem().getItemId() == db_id && item.getBless() == bless)
        return item; 
    } 
    return null;
  }
  
  public ItemInstance isItem(ItemInstance temp) {
    for (ItemInstance item : getAll()) {
      if (item.getItem().isPiles() && item.getItem().getItemId() == temp.getItem().getItemId() && item.getBless() == temp.getBless())
        if (item instanceof SlimeRaceTicket) {
          SlimeRaceTicket t = (SlimeRaceTicket)item;
          SlimeRaceTicket tt = (SlimeRaceTicket)temp;
          if (t.getSlimeRacerIdx() == tt.getSlimeRacerIdx() && t.getSlimeRaceUid() == tt.getSlimeRaceUid())
            return item; 
        } else {
          return item;
        }  
    } 
    return null;
  }
  
  public ItemInstance getSlot(int slot) {
    return null;
  }
  
  public void setSlot(int slot, ItemInstance temp) {}
  
  public void JoinWorld() {}
  
  public boolean Arrow(boolean Gamso) {
    return false;
  }
  
  public ItemInstance getArrow() {
    for (ItemInstance temp : getAll()) {
      if (temp.getItem().getType() == 1)
        return temp; 
    } 
    return null;
  }
  
  public ItemInstance getAden() {
    List<?> list = getItemNameId(4);
    if (list != null)
      return (ItemInstance)list.get(0); 
    return null;
  }
  
  public ItemInstance getMoney() {
    List<?> list = getName(449);
    if (list != null)
      return (ItemInstance)list.get(0); 
    return null;
  }
  
  public boolean Money(long CheckCount, boolean GamSo) {
    return false;
  }
  
  public boolean Aden(long CheckCount, boolean GamSo) {
    return false;
  }
  
  public int getWeight() {
    int weight = 0;
    try {
      for (ItemInstance item : getAll())
        weight = (int)(weight + item.getItem().getWeight() * item.getCount()); 
    } catch (Exception localException) {}
    return weight;
  }
  
  public boolean isWeight(int weight) {
    return true;
  }
  
  public boolean RingOfPolymorphControl() {
    return false;
  }
  
  public boolean RingOfTeleportControl() {
    return false;
  }
  
  public Character getCha() {
    return null;
  }
  
  public void clickItem(C_BasePacket bp) {}
  
  public void SendPacket(S_BasePacket bp) {
    bp.clear();
  }
  
  public void SetItemSetting(ItemInstance item) {}
  
  public boolean isSkillCheckHelmMagic(PcInstance pc) {
    ItemInstance helm = getSlot(0);
    if (helm != null && helm.isEquipped()) {
      int itemId = helm.getItem().getItemId();
      if (itemId == 249 || itemId == 250 || itemId == 251) {
        pc.Message("??魔法前?先?掉魔法?盔。");
        return false;
      } 
    } 
    return true;
  }
  
  public boolean isWeapon() {
    ItemInstance weapon = getSlot(11);
    if (weapon != null && weapon.isEquipped())
      return true; 
    return false;
  }
  
  public ItemInstance getWeapon() {
    ItemInstance weapon = getSlot(11);
    if (weapon != null && weapon.isEquipped())
      return weapon; 
    return null;
  }
  
  public ItemInstance getArmor() {
    ItemInstance armor = getSlot(4);
    if (armor != null && armor.isEquipped())
      return armor; 
    return null;
  }
}
