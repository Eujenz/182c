package net.world.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.Config;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHitratio;
import net.network.server.S_PartyList;
import net.network.server.S_ServerMessage;
import net.network.server.S_ServerMessageYesNo;
import net.world.function.bean.Party;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class PartySystem {
  private Map<Integer, Party> list;
  
  private static class Holder {
    static PartySystem instance = new PartySystem();
  }
  
  public static PartySystem getInstance() {
    return Holder.instance;
  }
  
  private PartySystem() {
    this.list = new HashMap<Integer, Party>();
  }
  
  public void addExp(Character cha, long exp, long lawful) {
    if (cha.isDead())
      return; 
    Party p = get(cha.getPartyId());
    if (p != null) {
      List<PcInstance> inSameScreenPartyList = new ArrayList<PcInstance>();
      byte b;
      int i;
      PcInstance[] arrayOfPcInstance;
      for (i = (arrayOfPcInstance = p.getList()).length, b = 0; b < i; ) {
        PcInstance pc = arrayOfPcInstance[b];
        if (cha.getObjectId() != pc.getObjectId() && !pc.isDead() && cha.getDistance(pc.getX(), pc.getY(), pc.getMap(), 12))
          inSameScreenPartyList.add(pc); 
        b++;
      } 
      if (inSameScreenPartyList.size() > 0) {
        if (Config.DEBUG)
          System.out.print(String.valueOf(cha.getName()) + "的经验" + exp + "，将被分成" + (inSameScreenPartyList.size() + 1) + "份，一份："); 
        exp /= (inSameScreenPartyList.size() + 1);
        lawful /= inSameScreenPartyList.size();
        if (Config.DEBUG)
          System.out.println(exp); 
        long partyExp = 0L;
        if (Config.PARTY_EXP_TYPE == 2) {
          partyExp = (long)(exp * Config.RATE_PARTY_EXP * inSameScreenPartyList.size());
          if (partyExp > exp)
            partyExp = exp; 
        } else if (Config.PARTY_EXP_TYPE == 1) {
          partyExp = (long)(exp * Config.RATE_PARTY_EXP);
        } 
        exp += partyExp;
        if (Config.DEBUG)
          System.out.println("组队加成经验为" + partyExp + "，最终每人得到经验为：" + exp); 
        for (PcInstance pc : inSameScreenPartyList) {
          pc.addExp(exp);
          pc.setLawful((int)(pc.getLawful() + lawful));
        } 
      } else if (Config.DEBUG) {
        System.out.println("不存在同一屏幕的队友.");
      } 
    } 
    cha.addExp(exp);
    cha.setLawful((int)(cha.getLawful() + lawful));
  }
  
  public synchronized void updateHp(PcInstance pc) {
    if (pc.getPartyId() > 0) {
      Party p = get(pc.getPartyId());
      if (p != null)
        p.updateHpOpen(pc); 
    } 
  }
  
  public void outParty(PcInstance pc) {
    if (!pc.isDead()) {
      Party p = get(pc.getPartyId());
      if (p != null)
        if (p.getMaster().getObjectId() == pc.getObjectId()) {
          p.SendPacket((S_BasePacket)new S_ServerMessage(418));
          remove(p);
          p.clear();
        } else {
          p.SendPacket((S_BasePacket)new S_ServerMessage(420, pc.getName()));
          p.removeList(pc);
          pc.setPartyId(0);
          if (p.getCount() <= 1) {
            p.SendPacket((S_BasePacket)new S_ServerMessage(418));
            remove(p);
            p.clear();
          } 
        }  
    } 
  }
  
  public void newParty(PcInstance pc) {
    if (!pc.isDead()) {
      PcInstance use = userFind(pc);
      if (use != null)
        if (use.getPartyId() == 0) {
          Party p = get(pc.getPartyId());
          if (p == null) {
            p = new Party(pc);
            pc.setPartyId(p.getUid());
            use.setPartyId(p.getUid());
            add(p);
            use.SendPacket((S_BasePacket)new S_ServerMessageYesNo(422, pc.getName()));
          } else if (p.getMaster().getObjectId() == pc.getObjectId()) {
            use.setPartyId(p.getUid());
            use.SendPacket((S_BasePacket)new S_ServerMessageYesNo(422, pc.getName()));
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(416));
          } 
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(415));
        }  
    } 
  }
  
  public void newParty(PcInstance pc, boolean yn) {
    Party p = get(pc.getPartyId());
    if (p != null) {
      if (yn) {
        if (p.getCount() < 8) {
          p.addList(pc);
          p.SendPacket((S_BasePacket)new S_ServerMessage(424, pc.getName()));
        } else {
          pc.setPartyId(0);
          pc.Message("对方的队伍已经满了，无法再接受队员。");
          p.getMaster().SendPacket((S_BasePacket)new S_ServerMessage(417));
        } 
      } else {
        pc.setPartyId(0);
        p.getMaster().SendPacket((S_BasePacket)new S_ServerMessage(423, pc.getName()));
      } 
      if (p.getCount() <= 1) {
        remove(p);
        p.clear();
      } 
    } 
  }
  
  public void sendMembersNameList(PcInstance pc) {
    Party party = get(pc.getPartyId());
    if (party != null) {
      pc.SendPacket((S_BasePacket)new S_PartyList(pc.getObjectId(), "party", party.getMaster().getName(), getMembersNameList(party)));
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(425));
    } 
  }
  
  private String getMembersNameList(Party party) {
    StringBuilder sb = new StringBuilder();
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = party.getList()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      sb.append(pc.getName());
      sb.append("\n");
      b++;
    } 
    return sb.toString();
  }
  
  public void banParty(PcInstance pc, String name) {
    Party party = get(pc.getPartyId());
    if (party == null) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(425));
      return;
    } 
    if (party.getMaster().getObjectId() != pc.getObjectId()) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(427));
      return;
    } 
    boolean flag = false;
    PcInstance tgPc = null;
    boolean isOwn = false;
    boolean isCount = false;
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = party.getList()).length, b = 0; b < i; ) {
      PcInstance use = arrayOfPcInstance[b];
      if (use.getName().equalsIgnoreCase(name)) {
        if (party.getMaster().getObjectId() == use.getObjectId()) {
          pc.Message("不能驱逐自己。");
          isOwn = true;
          break;
        } 
        if (party.getCount() == 2) {
          breakup(party);
          isCount = true;
          break;
        } 
        party.removeList(use);
        use.setPartyId(0);
        use.SendPacket((S_BasePacket)new S_ServerMessage(419));
        party.SendPacket((S_BasePacket)new S_ServerMessage(420, name));
        flag = true;
        tgPc = use;
      } 
      b++;
    } 
    if (flag && tgPc != null)
      for (i = (arrayOfPcInstance = party.getList()).length, b = 0; b < i; ) {
        PcInstance use = arrayOfPcInstance[b];
        if (tgPc.getDistance(use.getX(), use.getY(), use.getMap(), 14)) {
          party.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)tgPc, false));
          tgPc.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)use, false));
        } 
        b++;
      }  
    if (!flag && !isOwn && !isCount)
      pc.Message("队伍中没有此人。"); 
  }
  
  private void breakup(Party party) {
    party.SendPacket((S_BasePacket)new S_ServerMessage(418));
    remove(party);
    party.clear();
  }
  
  public void add(Party p) {
    this.list.put(Integer.valueOf(p.getUid()), p);
  }
  
  public void remove(Party p) {
    this.list.remove(Integer.valueOf(p.getUid()));
  }
  
  public Party get(int partyId) {
    return this.list.get(Integer.valueOf(partyId));
  }
  
  public PcInstance userFind(PcInstance pc) {
    int locx = pc.getX();
    int locy = pc.getY();
    switch (pc.getHeading()) {
      case 0:
        locy--;
        break;
      case 1:
        locx++;
        locy--;
        break;
      case 2:
        locx++;
        break;
      case 3:
        locx++;
        locy++;
        break;
      case 4:
        locy++;
        break;
      case 5:
        locx--;
        locy++;
        break;
      case 6:
        locx--;
        break;
      case 7:
        locx--;
        locy--;
        break;
    } 
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = pc.getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof PcInstance && locx == o.getX() && locy == o.getY()) {
        switch (pc.getHeading()) {
          case 0:
            if (o.getHeading() == 4)
              return (PcInstance)o; 
            break;
          case 1:
            if (o.getHeading() == 5)
              return (PcInstance)o; 
            break;
          case 2:
            if (o.getHeading() == 6)
              return (PcInstance)o; 
            break;
          case 3:
            if (o.getHeading() == 7)
              return (PcInstance)o; 
            break;
          case 4:
            if (o.getHeading() == 0)
              return (PcInstance)o; 
            break;
          case 5:
            if (o.getHeading() == 1)
              return (PcInstance)o; 
            break;
          case 6:
            if (o.getHeading() == 2)
              return (PcInstance)o; 
            break;
          case 7:
            if (o.getHeading() == 3)
              return (PcInstance)o; 
            break;
        } 
        pc.SendPacket((S_BasePacket)new S_ServerMessage(91, o.getName()));
        return null;
      } 
      b++;
    } 
    pc.SendPacket((S_BasePacket)new S_ServerMessage(421));
    return null;
  }
}
