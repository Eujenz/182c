package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAttackMagic;
import net.network.server.S_ObjectHeading;
import net.util.Util;
import net.world.WorldMap;
import net.world.function.SummonSystem;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class EbonyWand extends ItemInstance {
  private static final int EFFECT_ID = 10;
  
  public EbonyWand(Item i) {
    super(i);
    setHaveCount(Util.rand(5, 15));
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    L1Object o = cha.getObject(bp.readD());
    int x = bp.readH();
    int y = bp.readH();
    if (getHaveCount() > 0 && !cha.isInvis()) {
      cha.setFight(true);
      setHaveCount(cha, getHaveCount() - 1);
      if (cha.getDistance(x, y, cha.getMap(), 12)) {
        cha.setHeading(cha.calcheading(x, y));
        cha.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)cha), true);
        if (o != null && o instanceof Character) {
          if (cha.getDistance(o.getX(), o.getY(), o.getMap(), 12)) {
            int dmg = getDmage(cha, o);
            if (dmg > 0) {
              o.toAttack((L1Object)cha, 3);
              o.setCurrentHp(o.getCurrentHp() - dmg);
              SummonSystem.getInstance().toAttack(cha, o);
            } 
            cha.SendPacket((S_BasePacket)new S_ObjectAttackMagic((L1Object)cha, o.getObjectId(), o.getX(), o.getY(), 17, dmg, 10), true);
          } 
        } else {
          cha.SendPacket((S_BasePacket)new S_ObjectAttackMagic((L1Object)cha, 0, x, y, 17, 0, 10), true);
        } 
      } 
    } 
  }
  
  private int getDmage(Character cha, L1Object temp) {
    if (!temp.isLock() && cha.isPkLevel(temp) && WorldMap.getInstance().AttackZone((L1Object)cha, temp))
      return Util.rand(5, 20); 
    return 0;
  }
}
