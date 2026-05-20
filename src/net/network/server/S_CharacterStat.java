package net.network.server;

import net.Config;
import net.util.Util;
import net.world.object.Character;

public class S_CharacterStat extends S_BasePacket {
  private String log_object;
  
  private String log_lev;
  
  private String log_exp;
  
  private String log_str;
  
  private String log_int;
  
  private String log_wis;
  
  private String log_dex;
  
  private String log_con;
  
  private String log_cha;
  
  private String log_hp;
  
  private String log_maxhp;
  
  private String log_mp;
  
  private String log_maxmp;
  
  private String log_ac;
  
  private String log_worldtime;
  
  private String log_food;
  
  private String log_weight;
  
  private String log_lawfult;
  
  private String log_fireress;
  
  private String log_waterress;
  
  private String log_windress;
  
  private String log_earthress;
  
  public S_CharacterStat(Character pc) {
    this.log_object = pc.toString();
    this.log_lev = String.valueOf(pc.getLevel());
    this.log_exp = String.valueOf(pc.getExp());
    this.log_str = String.valueOf(pc.getTotalStr());
    this.log_int = String.valueOf(pc.getTotalInt());
    this.log_wis = String.valueOf(pc.getTotalWis());
    this.log_dex = String.valueOf(pc.getTotalDex());
    this.log_con = String.valueOf(pc.getTotalCon());
    this.log_cha = String.valueOf(pc.getTotalCha());
    this.log_hp = String.valueOf(pc.getCurrentHp());
    this.log_maxhp = String.valueOf(pc.getTotalHp());
    this.log_mp = String.valueOf(pc.getCurrentMp());
    this.log_maxmp = String.valueOf(pc.getTotalMp());
    this.log_ac = String.valueOf(266 - pc.getTotalAc());
    this.log_worldtime = String.valueOf(Config.WORLDTIME);
    this.log_food = String.valueOf(pc.getFood());
    this.log_weight = String.valueOf((pc.getInventory() == null) ? 0 : pc.getInventory().getWeight());
    this.log_lawfult = String.valueOf(pc.getLawful());
    this.log_fireress = String.valueOf(pc.getTotalFireress());
    this.log_waterress = String.valueOf(pc.getTotalWaterress());
    this.log_windress = String.valueOf(pc.getTotalWindress());
    this.log_earthress = String.valueOf(pc.getTotalEarthress());
    writeC(12);
    writeD(pc.getObjectId());
    writeC(pc.getLevel());
    writeD((int)pc.getExp());
    writeC(pc.getTotalStr());
    writeC(pc.getTotalInt());
    writeC(pc.getTotalWis());
    writeC(pc.getTotalDex());
    writeC(pc.getTotalCon());
    writeC(pc.getTotalCha());
    writeH(pc.getCurrentHp());
    writeH(pc.getTotalHp());
    writeH(pc.getCurrentMp());
    writeH(pc.getTotalMp());
    writeC(266 - pc.getTotalAc());
    writeD(Config.WORLDTIME);
    writeC(pc.getFood());
    writeC((pc.getInventory() == null) ? 0 : pc.getInventory().getWeight());
    writeH(pc.getLawful());
    writeC(pc.getTotalFireress());
    writeC(pc.getTotalWaterress());
    writeC(pc.getTotalWindress());
    writeC(pc.getTotalEarthress());
  }
  
  public S_CharacterStat(Character pc, int weight) {
    this.log_object = pc.toString();
    this.log_lev = String.valueOf(pc.getLevel());
    this.log_exp = String.valueOf(pc.getExp());
    this.log_str = String.valueOf(pc.getTotalStr());
    this.log_int = String.valueOf(pc.getTotalInt());
    this.log_wis = String.valueOf(pc.getTotalWis());
    this.log_dex = String.valueOf(pc.getTotalDex());
    this.log_con = String.valueOf(pc.getTotalCon());
    this.log_cha = String.valueOf(pc.getTotalCha());
    this.log_hp = String.valueOf(pc.getCurrentHp());
    this.log_maxhp = String.valueOf(pc.getTotalHp());
    this.log_mp = String.valueOf(pc.getCurrentMp());
    this.log_maxmp = String.valueOf(pc.getTotalMp());
    this.log_ac = String.valueOf(266 - pc.getTotalAc());
    this.log_worldtime = String.valueOf(Config.WORLDTIME);
    this.log_food = String.valueOf(pc.getFood());
    this.log_weight = String.valueOf(weight);
    this.log_lawfult = String.valueOf(pc.getLawful());
    this.log_fireress = String.valueOf(pc.getTotalFireress());
    this.log_waterress = String.valueOf(pc.getTotalWaterress());
    this.log_windress = String.valueOf(pc.getTotalWindress());
    this.log_earthress = String.valueOf(pc.getTotalEarthress());
    writeC(12);
    writeD(pc.getObjectId());
    writeC(pc.getLevel());
    writeD((int)pc.getExp());
    writeC(pc.getTotalStr());
    writeC(pc.getTotalInt());
    writeC(pc.getTotalWis());
    writeC(pc.getTotalDex());
    writeC(pc.getTotalCon());
    writeC(pc.getTotalCha());
    writeH(pc.getCurrentHp());
    writeH(pc.getTotalHp());
    writeH(pc.getCurrentMp());
    writeH(pc.getTotalMp());
    writeC(266 - pc.getTotalAc());
    writeD(Config.WORLDTIME);
    writeC(pc.getFood());
    writeC(weight);
    writeH(pc.getLawful());
    writeC(pc.getTotalFireress());
    writeC(pc.getTotalWaterress());
    writeC(pc.getTotalWindress());
    writeC(pc.getTotalEarthress());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_object);
      sb.append(" , ");
      sb.append(this.log_lev);
      sb.append(" , ");
      sb.append(this.log_exp);
      sb.append(" , ");
      sb.append(this.log_str);
      sb.append(" , ");
      sb.append(this.log_int);
      sb.append(" , ");
      sb.append(this.log_wis);
      sb.append(" , ");
      sb.append(this.log_dex);
      sb.append(" , ");
      sb.append(this.log_con);
      sb.append(" , ");
      sb.append(this.log_cha);
      sb.append(" , ");
      sb.append(this.log_hp);
      sb.append(" , ");
      sb.append(this.log_maxhp);
      sb.append(" , ");
      sb.append(this.log_mp);
      sb.append(" , ");
      sb.append(this.log_maxmp);
      sb.append(" , ");
      sb.append(this.log_ac);
      sb.append(" , ");
      sb.append(this.log_worldtime);
      sb.append(" , ");
      sb.append(this.log_food);
      sb.append(" , ");
      sb.append(this.log_weight);
      sb.append(" , ");
      sb.append(this.log_lawfult);
      sb.append(" , ");
      sb.append(this.log_fireress);
      sb.append(" , ");
      sb.append(this.log_waterress);
      sb.append(" , ");
      sb.append(this.log_windress);
      sb.append(" , ");
      sb.append(this.log_earthress);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
