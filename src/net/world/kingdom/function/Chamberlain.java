package net.world.kingdom.function;

import net.network.server.S_BasePacket;
import net.network.server.S_KingdomTaxIn;
import net.network.server.S_KingdomTaxOut;
import net.network.server.S_KingdomTaxSetting;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;
import net.world.object.L1Object;

public class Chamberlain extends L1Object {
  protected Kingdom k;
  
  public Chamberlain(Kingdom k) {
    this.k = k;
  }
  
  public void toAttack(L1Object target, int type) {
    if (this.k.getClanID() != target.getClanId())
      this.k.toAttack(target, type); 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if ((pc.getClassType() == 0 || pc.isGm()) && !text1.equalsIgnoreCase("inex"))
      if (text1.equalsIgnoreCase("tax")) {
        pc.SendPacket((S_BasePacket)new S_KingdomTaxSetting(this, this.k.getUid()));
      } else if (text1.equalsIgnoreCase("withdrawal")) {
        pc.SendPacket((S_BasePacket)new S_KingdomTaxOut(this.k));
      } else if (text1.equalsIgnoreCase("expel")) {
        this.k.Teleport();
      } else if (text1.equalsIgnoreCase("cdeposit")) {
        pc.SendPacket((S_BasePacket)new S_KingdomTaxIn(this.k, pc));
      } else if (!text1.equalsIgnoreCase("employ") && 
        !text1.equalsIgnoreCase("arrange") && 
        !text1.equalsIgnoreCase("archer")) {
        if (text1.equalsIgnoreCase("castlegate")) {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ishmael5", this.k.getDoorStatus()));
        } else if (text1.equalsIgnoreCase("healegate_kent outer gate")) {
          this.k.isResetDoor(pc, false);
        } else if (text1.equalsIgnoreCase("healigate_kent inner gate")) {
          this.k.isResetDoor(pc, true);
        } 
      }  
  }
  
  public void setTax(PcInstance pc, int tax) {
    if (!this.k.isWar() && this.k.getClanID() == pc.getClanId() && pc.getClassType() == 0)
      if (this.k.getTaxDay() == 0L || System.currentTimeMillis() - this.k.getTaxDay() >= 86400000L) {
        this.k.setTax(tax);
        this.k.setTaxDay(System.currentTimeMillis());
        this.k.updateDB();
      } else {
        pc.Message("24小時內不能再次設置稅率.");
      }  
  }
}
