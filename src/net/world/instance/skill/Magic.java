package net.world.instance.skill;

import java.util.List;
import java.util.Random;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldMap;
import net.world.instance.ItemInstance;
import net.world.instance.NpcInstance;
import net.world.instance.SummonInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.ItemTimerInstance;

public class Magic {
  protected static final Random _random = new Random();
  
  private int time;
  
  protected Character operator;
  
  protected Skill skill;
  
  public static int MagicAction = 18;
  
  public static int MagicAction2 = 19;
  
  public static int MagicAction3 = 17;
  
  private static int[][] intMP = new int[][] { new int[10], { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 1, 2, 2, 2, 2, 2, 2, 2, 2 }, { 0, 1, 2, 3, 3, 3, 3, 3, 3, 3 }, { 0, 1, 2, 3, 4, 4, 4, 4, 4, 4 }, { 0, 1, 2, 3, 4, 5, 5, 5, 5, 5 }, { 0, 1, 2, 3, 4, 5, 6, 6, 6, 6 }, { 0, 1, 2, 3, 4, 5, 6, 7, 7, 7 } };
  
  protected boolean gm_buff = false;
  
  public Magic(Character cha, Skill skill) {
    this.operator = cha;
    this.skill = skill;
    setTime(getSkill().getBuffDuration());
  }
  
  public void setGMBuff(boolean b) {
    this.gm_buff = b;
  }
  
  public Skill getSkill() {
    return this.skill;
  }
  
  public Character getCha() {
    return this.operator;
  }
  
  public void setCha(Character cha) {
    this.operator = cha;
  }
  
  public void toMagic(int id) {}
  
  public void toMagic(int x, int y) {}
  
  public void toMagic(int x, int y, int id) {}
  
  protected boolean HpMpCheck() {
    if (this.operator.isGm() || this.gm_buff)
      return true; 
    if (this.operator instanceof net.world.instance.MonsterInstance) {
      this.operator.setCurrentMp(this.operator.getCurrentMp() - getSkill().getMpConsume());
      return true;
    } 
    int hp = getSkill().getHpConsume();
    int mp = getSkill().getMpConsume();
    int lawful = getSkill().get_lawfulconsume();
    int intmpNum = this.operator.getTotalInt() - 12;
    int skilllev = getSkill().getSkill_level() - 1;
    if (intmpNum > 7) {
      intmpNum = 7;
    } else if (intmpNum <= 0) {
      intmpNum = 0;
    } 
    if (skilllev > 9)
      skilllev = 0; 
    mp -= intMP[intmpNum][skilllev];
    if (this.operator.getClassType() == 1) {
      mp /= 2;
      hp /= 2;
    } 
    if (this.operator.getCurrentHp() <= hp || this.operator.getCurrentMp() < mp) {
      if (this.operator.getCurrentHp() <= hp) {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(279));
      } else {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(278));
      } 
      return false;
    } 
    if (hp > 0)
      this.operator.setCurrentHp(this.operator.getCurrentHp() - hp); 
    if (mp > 0)
      this.operator.setCurrentMp(this.operator.getCurrentMp() - mp); 
    if (lawful > 0)
      this.operator.setLawful(this.operator.getLawful() - lawful); 
    return true;
  }
  
  protected boolean ConsumeCount() {
    if (this.operator.isGm() || this.operator instanceof net.world.instance.MonsterInstance || this.gm_buff)
      return true; 
    if (getSkill().getItemConsume() > 0) {
      List<ItemInstance> list = this.operator.getInventory().getItemNameId(getSkill().getItemConsume());
      if (list == null) {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(299));
        return false;
      } 
      if (((ItemInstance)list.get(0)).getCount() < getSkill().getItemConsumeCount()) {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(299));
        return false;
      } 
      ((ItemInstance)list.get(0)).setCount(this.operator, ((ItemInstance)list.get(0)).getCount() - getSkill().getItemConsumeCount());
    } 
    return true;
  }
  
  protected boolean Figure(L1Object temp) {
    if (isChaoticMagic() && !WorldMap.getInstance().AttackZone((L1Object)this.operator, temp))
      return false; 
    if (temp instanceof net.world.kingdom.function.CastleTop || temp instanceof net.world.kingdom.function.Crown)
      return false; 
    if (temp.isDead())
      return false; 
    if (temp instanceof Character) {
      Character c = (Character)temp;
      return (Util.rand(0, this.operator.getLevel() + this.operator.getDynamicInt() * 2) > Util.rand(0, c.getLevel() + c.getMr()));
    } 
    if (temp instanceof NpcInstance) {
      NpcInstance npc = (NpcInstance)temp;
      if (getSkill().getSkill_id() == 12)
        return (Util.rand(0, this.operator.getLevel()) > Util.rand(0, npc.getLevel()) && Util.rand(0, this.operator.getDynamicInt()) > Util.rand(0, npc.getMr())); 
      return (Util.rand(0, this.operator.getLevel() + this.operator.getDynamicInt()) > Util.rand(0, 100 + npc.getLevel()));
    } 
    return (Util.rand(0, 100) < 50);
  }
  
  protected boolean FigureLv(L1Object temp) {
    int a = this.operator.getLevel() - temp.getLevel();
    return (Util.rand(0, (this.operator.getLevel() + a) * 2) > Util.rand(0, temp.getLevel() * 2));
  }
  
  public int Damage(L1Object o, boolean longCheck) {
    if (o.isLock())
      return 0; 
    if (o.isDead())
      return 0; 
    if (this.operator instanceof net.world.instance.MonsterInstance && o instanceof net.world.instance.MonsterInstance && !(o instanceof SummonInstance) && !(o instanceof net.world.instance.PetInstance))
      return 0; 
    if (!(o instanceof SummonInstance) && o.isRecess())
      return 0; 
    if (o instanceof net.world.kingdom.function.CastleTop || o instanceof net.world.kingdom.function.Crown || o instanceof net.world.kingdom.function.DoorKingdom)
      return 0; 
    if (this.operator instanceof net.world.instance.PcInstance && isAttackMagic() && !this.operator.isPkLevel(o))
      return 0; 
    double dmg = 0.0D;
    if (this.operator.getClassType() == 1)
      dmg += 4.0D; 
    dmg += (Util.rand(getSkill().getMindmg(), this.operator.getSp(false)) * getSkill().getMaxdmg());
    dmg += IntDamage();
    if (this.operator.getClassType() == 3 && ItemTimerInstance.getInstance().contains(this.operator, "$944"))
      dmg *= 1.5D; 
    if (longCheck && !this.operator.LongAttackCK(o, 12))
      return 0; 
    if ((isChaoticMagic() || getSkill().getType().equalsIgnoreCase("attack")) && o instanceof Character) {
      Character c = (Character)o;
      if (!WorldMap.getInstance().AttackZone((L1Object)this.operator, o))
        return 0; 
      dmg -= dmg * Util.rand(0, c.getMr()) * 0.01D;
    } 
    double result = dmg / 2.0D;
    if (isHealMagic())
      result = getHealAmount(); 
    if (result < 0.0D)
      result = 0.0D; 
    return (int)result;
  }
  
  private boolean isAttackMagic() {
    return !isHealMagic();
  }
  
  private boolean isHealMagic() {
    return (this instanceof net.world.instance.skill.function.LesserHeal || this instanceof net.world.instance.skill.function.Heal || this instanceof net.world.instance.skill.function.GreaterHeal || this instanceof net.world.instance.skill.function.FullHeal || this instanceof net.world.instance.skill.function.HealPledge);
  }
  
  protected double getHealAmount() {
    return Util.rand(getSkill().getMindmg(), getSkill().getMaxdmg());
  }
  
  public int lawfulDamage(L1Object o, int dmg) {
    int init = 65536;
    int lawful = this.operator.getLawful();
    if (lawful < 65536) {
      dmg = (int)(dmg / 1.5D);
    } else if (lawful >= 65536 && lawful <= 75536) {
      dmg = (int)(dmg / 1.4D);
    } else if (lawful >= 75537 && lawful <= 85536) {
      dmg = (int)(dmg / 1.3D);
    } else if (lawful >= 85537 && lawful <= 95536) {
      dmg = (int)(dmg / 1.2D);
    } else if (lawful >= 95537 && lawful <= 98302) {
      dmg = (int)(dmg / 1.1D);
    } 
    return dmg;
  }
  
  protected boolean isClan(L1Object o) {
    if (o == null)
      return false; 
    if (this.operator.getObjectId() != o.getObjectId()) {
      if (o.getClanId() == 0)
        return false; 
      if (this.operator.getClanId() != o.getClanId())
        return false; 
    } 
    return true;
  }
  
  protected boolean isSummon(L1Object o) {
    if (o == null)
      return false; 
    if (o instanceof SummonInstance) {
      SummonInstance sum = (SummonInstance)o;
      if (sum.getOwn().getObjectId() != this.operator.getObjectId())
        return false; 
    } else {
      return false;
    } 
    return true;
  }
  
  private int IntDamage() {
    int dmg = 0;
    int adddmg = this.operator.getTotalInt() - 11;
    if (this.operator.getTotalInt() < 12) {
      dmg = 0;
    } else {
      dmg = adddmg;
    } 
    return dmg;
  }
  
  protected boolean isChaoticMagic() {
    switch (getSkill().getSkill_id()) {
      case 8:
      case 14:
      case 18:
      case 20:
      case 21:
      case 29:
      case 35:
      case 38:
      case 42:
      case 43:
      case 48:
      case 49:
        return true;
    } 
    return false;
  }
  
  public int getTime() {
    return this.time;
  }
  
  public void setTime(int time) {
    this.time = time;
  }
  
  public void isTimerRun(L1Object o) {}
  
  public void isTimerStop(L1Object o) {}
  
  public void isTimer(L1Object o) {}
  
  public void isTimerEnd(L1Object o) {}
  
  public void toMagic(L1Object o, int id) {}
}
