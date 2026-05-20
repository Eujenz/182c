package net.network.server;

import java.sql.ResultSet;
import net.util.Util;

public class S_CharacterInfo extends S_BasePacket {
  private String log_name;
  
  private String log_clanname;
  
  private String log_class;
  
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
  
  public S_CharacterInfo(ResultSet rs) throws Exception {
    this.log_name = rs.getString("name");
    this.log_clanname = rs.getString("clanNAME");
    this.log_class = String.valueOf(rs.getInt("class"));
    this.log_sex = String.valueOf(rs.getInt("sex"));
    this.log_lawful = String.valueOf(rs.getInt("lawful"));
    this.log_hp = String.valueOf(rs.getInt("maxHP"));
    this.log_mp = String.valueOf(rs.getInt("maxMP"));
    this.log_ac = String.valueOf(266 - rs.getInt("ac"));
    this.log_lev = String.valueOf(rs.getInt("level"));
    this.log_str = String.valueOf(rs.getInt("str"));
    this.log_dex = String.valueOf(rs.getInt("dex"));
    this.log_con = String.valueOf(rs.getInt("con"));
    this.log_wis = String.valueOf(rs.getInt("wis"));
    this.log_cha = String.valueOf(rs.getInt("cha"));
    this.log_int = String.valueOf(rs.getInt("inter"));
    writeC(4);
    writeS(rs.getString("name"));
    writeS(rs.getString("clanNAME"));
    writeC(rs.getInt("class"));
    writeC(rs.getInt("sex"));
    writeH(rs.getInt("lawful"));
    writeH(rs.getInt("maxHP"));
    writeH(rs.getInt("maxMP"));
    writeC(266 - rs.getInt("ac"));
    writeC(rs.getInt("level"));
    writeC(rs.getInt("str"));
    writeC(rs.getInt("dex"));
    writeC(rs.getInt("con"));
    writeC(rs.getInt("wis"));
    writeC(rs.getInt("cha"));
    writeC(rs.getInt("inter"));
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
      sb.append(this.log_class);
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
