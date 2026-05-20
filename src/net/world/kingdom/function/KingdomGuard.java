package net.world.kingdom.function;

import java.util.Calendar;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;
import net.world.npc.Guard;
import net.world.object.L1Object;

public class KingdomGuard extends Guard {
  protected Kingdom k;
  
  protected String html;
  
  public KingdomGuard(Kingdom k, Npc npc) {
    super(npc);
    this.k = k;
    switch (k.getUid()) {
      case 1:
        this.html = "ktguard";
        break;
      case 3:
        this.html = "wdguard";
        break;
      case 4:
        this.html = "grguard";
        break;
      case 5:
        this.html = "heguard";
        break;
      case 6:
        this.html = "dcguard";
        break;
    } 
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + '\003'));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.indexOf("6") > 0) {
      if (text2 != null && text2.length() > 0) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), text2, this.k.getClanNAME(), this.k.getAgentNAME()));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), text1, getName(), this.k.getClanNAME(), this.k.getAgentNAME()));
      } 
    } else if (text1.equalsIgnoreCase("askwartime")) {
      boolean guard = true;
      switch (getNpc().get_npcId()) {
        case 517:
        case 10125:
        case 10236:
        case 10268:
        case 10333:
          guard = true;
          break;
        case 10737:
        case 11109:
        case 11124:
        case 11131:
        case 11142:
          guard = false;
          break;
      } 
      if (this.k.getWarDay() == 0L) {
        if (guard) {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "8", getName()));
        } else {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "mercenary3", getName()));
        } 
      } else {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.k.getWarDay());
        String[] html = null;
        if (guard) {
          html = new String[6];
          html[0] = getName();
          html[1] = String.valueOf(cal.get(1));
          html[2] = String.valueOf(cal.get(2) + 1);
          html[3] = String.valueOf(cal.get(5));
          html[4] = String.valueOf(cal.get(11));
          html[5] = String.valueOf(cal.get(12));
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "7", html));
        } else {
          html = new String[5];
          html[0] = String.valueOf(cal.get(1));
          html[1] = String.valueOf(cal.get(2) + 1);
          html[2] = String.valueOf(cal.get(5));
          html[3] = String.valueOf(cal.get(11));
          html[4] = String.valueOf(cal.get(12));
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "mercenary2", html));
        } 
      } 
    } 
  }
  
  public void toWalk(long time) {
    super.toWalk(time);
    if (this.k.isWar()) {
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        if (o.getClanId() > 0 && o instanceof PcInstance) {
          String clan_name = ClanSystem.getInstance().WarCheck(ClanSystem.getInstance().getClan(o.getClanId()));
          if (clan_name != null && clan_name.equalsIgnoreCase(getClanName())) {
            setFight(true);
            addAttackList(o);
          } 
        } 
        b++;
      } 
    } 
  }
  
  public void toAttack(L1Object target, int type) {
    if (target.getClanId() == 0 || this.k.getClanID() != target.getClanId())
      this.k.toAttack(target, type); 
  }
}
