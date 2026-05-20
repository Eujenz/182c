package net.world.instance;

import net.database.SkillTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.object.Character;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMagicInstance extends ItemInstance {
  private static final Logger log = LoggerFactory.getLogger(ItemMagicInstance.class);
  
  private static final String SKILL_LOG = "??技能 IP[%s] ??[%s]  角色[%s]  等?[%s]  ??[%s] 技能[%s]    [%s]";
  
  public ItemMagicInstance(Item _item) {
    super(_item);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    magicItem((PcInstance)cha);
  }
  
  public void magicItem(PcInstance pc) {
    try {
      if (MagicLevel(pc) && (pc.isGm() || MagicZone(pc))) {
        pc.getSkill().add(SkillTable.getInstance().getTemplate(pc, getItem().getSkill_id()));
        pc.getSkill().sendList();
        pc.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)pc, getItem().get_EffectID()));
        setCount(pc, getCount() - 1L);
        logRecord(pc, "成功");
        return;
      } 
    } catch (Exception localException) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(79));
    } 
  }
  
  private boolean MagicLevel(PcInstance cha) {
    switch (cha.getClassType()) {
      case 0:
        return (getItem().get_royal() > 0 && cha.getLevel() >= getItem().get_royal());
      case 1:
        return (getItem().get_knight() > 0 && cha.getLevel() >= getItem().get_knight());
      case 2:
        return (getItem().get_elf() > 0 && cha.getLevel() >= getItem().get_elf());
    } 
    return (getItem().get_mage() > 0 && cha.getLevel() >= getItem().get_mage());
  }
  
  private boolean MagicZone(PcInstance cha) {
    try {
      int effectId = getItem().get_EffectID();
      if (effectId == 231) {
        if (ChaoticZone(cha))
          return true; 
        if (LawfulZone(cha)) {
          cha.setCurrentHp(cha.getCurrentHp() - Util.rand(10, 30));
          cha.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)cha, 3), true);
          cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, 10), true);
          setCount(cha, getCount() - 1L);
          logRecord(cha, "失?：邪?神殿");
        } 
      } 
      if (effectId == 224) {
        if (LawfulZone(cha))
          return true; 
        if (ChaoticZone(cha)) {
          cha.setCurrentHp(cha.getCurrentHp() - Util.rand(10, 30));
          cha.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)cha, 3), true);
          cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, 10), true);
          setCount(cha, getCount() - 1L);
          logRecord(cha, "失?：正?神殿");
        } 
      } 
      if (effectId == 226 && cha.getClassType() == 2 && isLearnElfMagic(cha))
        return true; 
      if (effectId == 227 && cha.getClassType() == 2 && cha.getElfAttr() == 2 && isLearnElfMagic(cha))
        return true; 
      if (effectId == 228 && cha.getClassType() == 2 && cha.getElfAttr() == 8 && isLearnElfMagic(cha))
        return true; 
      if (effectId == 229 && cha.getClassType() == 2 && cha.getElfAttr() == 4 && isLearnElfMagic(cha))
        return true; 
      if (effectId == 230 && cha.getClassType() == 2 && cha.getElfAttr() == 1 && isLearnElfMagic(cha))
        return true; 
    } catch (Exception localException) {}
    return false;
  }
  
  private boolean ChaoticZone(PcInstance pc) {
    return ((pc.getX() >= 32884 && pc.getX() <= 32891 && pc.getY() >= 32647 && pc.getY() <= 32656 && pc.getMap() == 4) || (pc.getX() > 32662 && pc.getX() < 32674 && pc.getY() > 32297 && pc.getY() < 32309 && pc.getMap() == 4));
  }
  
  private boolean LawfulZone(PcInstance pc) {
    return ((pc.getX() >= 33117 && pc.getX() <= 33128 && pc.getY() >= 32931 && pc.getY() <= 32942 && pc.getMap() == 4) || (pc.getX() >= 33136 && pc.getX() <= 33143 && pc.getY() >= 32237 && pc.getY() <= 32247 && pc.getMap() == 4) || (pc.getX() >= 32783 && pc.getX() <= 32803 && pc.getY() >= 32831 && pc.getY() <= 32851 && pc.getMap() == 77));
  }
  
  private boolean isLearnElfMagic(PcInstance pc) {
    int pc_X = pc.getX();
    int pc_Y = pc.getY();
    int pcMap_Id = pc.getMap();
    if ((pc_X >= 32786 && pc_X <= 32797 && pc_Y >= 32842 && pc_Y <= 32859 && pcMap_Id == 75) || (pc_X >= 33047 && pc_X <= 33057 && pc_Y >= 32337 && pc_Y <= 32343 && pcMap_Id == 4))
      return true; 
    return false;
  }
  
  private void logRecord(PcInstance pc, String msg) {
    log.info(String.format("??技能 IP[%s] ??[%s]  角色[%s]  等?[%s]  ??[%s] 技能[%s]    [%s]", new Object[] { pc.getClient().getIP(), pc.getClient().getID(), pc.getName(), Integer.valueOf(pc.getLevel()), pc.getClassTypeName(), pc.getSkillIdName(getItem().getSkill_id()), msg }));
  }
}
