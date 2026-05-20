package net.world.function;

import net.Config;
import net.database.PolymorphTable;
import net.database.bean.Poly;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectPoly;
import net.network.server.S_ObjectRestore;
import net.network.server.S_ServerMessage;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class AskSystem {
  private static class Holder {
    static AskSystem instance = new AskSystem();
  }
  
  public static AskSystem getInstance() {
    return Holder.instance;
  }
  
  public void ask(PcInstance pc, int type, int yn, C_BasePacket bp) {
    String name;
    Poly p;
    switch (type) {
      case 97:
        ClanSystem.getInstance().JoinFinal(pc, (yn == 1));
      case 180:
        name = bp.readS();
        p = PolymorphTable.getInstance().getTemplate(name);
        if (p != null && (p.getMinlvl() <= pc.getLevel() || Config.EVENT_POLYSCROLL)) {
          ItemTimerInstance.getInstance().remove((Character)pc, "$971");
          BuffTimerInstance.getInstance().remove((L1Object)pc, 43);
          ItemTimerInstance.getInstance().remove((Character)pc, "$260");
          ItemTimerInstance.getInstance().remove((Character)pc, "$971");
          ItemTimerInstance.getInstance().add((Character)pc, pc.getpolyitem());
          PolymorphTable.getInstance().polyEquipped((L1Object)pc, p);
          pc.setGfx(p.getPolyid());
          pc.SendPacket((S_BasePacket)new S_ObjectPoly((L1Object)pc), true);
        } 
      case 217:
        ClanSystem.getInstance().WarFinal(pc, (yn == 1));
      case 221:
        ClanSystem.getInstance().WarSubmissionFinal(pc, (yn == 1));
      case 252:
        if (yn == 0) {
          TradeSystem.getInstance().tradeCancel(pc);
        } else {
          TradeSystem.getInstance().tradeStart(pc);
        } 
      case 321:
        if (pc.isStatusNatures()) {
          if (yn != 0) {
            if (pc.isOverlapping()) {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(592));
              return;
            } 
            if (pc.getInventory().getSlot(11) != null) {
              pc.setGfxMode(pc.getInventory().getSlot(11).getItem().getGfxmode());
            } else {
              pc.setGfxMode(0);
            } 
            pc.setDead(false);
            pc.SendPacket((S_BasePacket)new S_ObjectRestore((L1Object)pc, (L1Object)pc), true);
            pc.setCurrentHp(pc.getMaxHp());
            pc.setCurrentMp(0);
            pc.setStatusNatures(false);
          } 
        } else if (yn != 0) {
          pc.toRevivalFianl();
        } 
      case 325:
        if (pc.getSummon().getName().startsWith("$")) {
          pc.getSummon().NameUpdate(bp.readS());
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(326));
        } 
      case 422:
        PartySystem.getInstance().newParty(pc, (yn != 0));
      case 479:
        if (pc.LvStat(false)) {
          String stat = bp.readS();
          if ("str".equalsIgnoreCase(stat)) {
            if (pc.getStr() + pc.getLvStr() < Config.CHARACTER_STAT) {
              pc.setLvStr(pc.getLvStr() + 1);
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(481));
            } 
          } else if ("dex".equalsIgnoreCase(stat)) {
            if (pc.getDex() + pc.getLvDex() < Config.CHARACTER_STAT) {
              pc.setLvDex(pc.getLvDex() + 1);
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(481));
            } 
          } else if ("con".equalsIgnoreCase(stat)) {
            if (pc.getCon() + pc.getLvCon() < Config.CHARACTER_STAT) {
              pc.setLvCon(pc.getLvCon() + 1);
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(481));
            } 
          } else if ("cha".equalsIgnoreCase(stat)) {
            if (pc.getCha() + pc.getLvCha() < Config.CHARACTER_STAT) {
              pc.setLvCha(pc.getLvCha() + 1);
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(481));
            } 
          } else if ("wis".equalsIgnoreCase(stat)) {
            if (pc.getWis() + pc.getLvWis() < Config.CHARACTER_STAT) {
              pc.setLvWis(pc.getLvWis() + 1);
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(481));
            } 
          } else if (pc.getInt() + pc.getLvInt() < Config.CHARACTER_STAT) {
            pc.setLvInt(pc.getLvInt() + 1);
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(481));
            return;
          } 
          pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
        } 
      case 512:
        AgitSystem.getInstance().ChangName(pc, bp.readS());
      case 729:
      case 953:
      case 1256:
        return;
    } 
    System.out.println("type: " + type);
    System.out.println("yn: " + yn);
  }
}
