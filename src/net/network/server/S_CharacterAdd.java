package net.network.server;

import net.util.Util;

public class S_CharacterAdd extends S_BasePacket {
  private String log_name;
  
  private String log_clanname;
  
  private String log_type;
  
  private String log_sex;
  
  private String log_lawful;
  
  private String log_hp;
  
  private String log_mp;
  
  private String log_ac;
  
  private String log_lev;
  
  private String log_str;
  
  private String log_dex;
  
  private String log_con;
  
  private String log_wis;
  
  private String log_cha;
  
  private String log_int;
  
  public S_CharacterAdd(String name, String clanname, int type, int sex, int lawful, int hp, int mp, int ac, int lev, int str, int dex, int con, int wis, int cha, int inter) {
    this.log_name = name;
    this.log_clanname = clanname;
    this.log_type = String.valueOf(type);
    this.log_sex = String.valueOf(sex);
    this.log_lawful = String.valueOf(lawful);
    this.log_hp = String.valueOf(hp);
    this.log_mp = String.valueOf(mp);
    this.log_ac = String.valueOf(ac);
    this.log_lev = String.valueOf(lev);
    this.log_str = String.valueOf(str);
    this.log_dex = String.valueOf(dex);
    this.log_con = String.valueOf(con);
    this.log_wis = String.valueOf(wis);
    this.log_cha = String.valueOf(cha);
    this.log_int = String.valueOf(inter);
    writeC(5);
    writeS(name);
    writeS(clanname);
    writeC(type);
    writeC(sex);
    writeH(lawful);
    writeH(hp);
    writeH(mp);
    writeC(ac);
    writeC(lev);
    writeC(str);
    writeC(dex);
    writeC(con);
    writeC(wis);
    writeC(cha);
    writeC(inter);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_name);
      sb.append(" , ");
      sb.append(this.log_clanname);
      sb.append(" , ");
      sb.append(this.log_type);
      sb.append(" , ");
      sb.append(this.log_sex);
      sb.append(" , ");
      sb.append(this.log_lawful);
      sb.append(" , ");
      sb.append(this.log_hp);
      sb.append(" , ");
      sb.append(this.log_mp);
      sb.append(" , ");
      sb.append(this.log_ac);
      sb.append(" , ");
      sb.append(this.log_lev);
      sb.append(" , ");
      sb.append(this.log_str);
      sb.append(" , ");
      sb.append(this.log_dex);
      sb.append(" , ");
      sb.append(this.log_con);
      sb.append(" , ");
      sb.append(this.log_wis);
      sb.append(" , ");
      sb.append(this.log_cha);
      sb.append(" , ");
      sb.append(this.log_int);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
