package net.util;

import java.util.ArrayList;
import java.util.List;
import net.util.bean.Stat;

public class CharacterStatDice {
  private List<Stat> _list;
  
  private static class Holder {
    static CharacterStatDice instance = new CharacterStatDice();
  }
  
  public static CharacterStatDice getInstance() {
    return Holder.instance;
  }
  
  private CharacterStatDice() {
    this._list = new ArrayList<Stat>();
    Stat s = new Stat();
    s.setStr(13);
    s.setCon(10);
    s.setDex(10);
    s.setWis(11);
    s.setCha(13);
    s.setInt(10);
    s.setHp(14);
    s.setMp(2);
    s.setType(0);
    s.setMale(0);
    s.setFemale(1);
    s.setX(32700);
    s.setY(32868);
    s.setMap(69);
    this._list.add(s);
    s = new Stat();
    s.setStr(16);
    s.setCon(14);
    s.setDex(12);
    s.setWis(9);
    s.setCha(12);
    s.setInt(8);
    s.setHp(16);
    s.setMp(1);
    s.setType(1);
    s.setMale(61);
    s.setFemale(48);
    s.setX(32700);
    s.setY(32868);
    s.setMap(69);
    this._list.add(s);
    s = new Stat();
    s.setStr(11);
    s.setCon(12);
    s.setDex(12);
    s.setWis(12);
    s.setCha(9);
    s.setInt(12);
    s.setHp(15);
    s.setMp(4);
    s.setType(2);
    s.setMale(138);
    s.setFemale(37);
    s.setX(32700);
    s.setY(32868);
    s.setMap(69);
    this._list.add(s);
    s = new Stat();
    s.setStr(8);
    s.setCon(12);
    s.setDex(7);
    s.setWis(12);
    s.setCha(8);
    s.setInt(12);
    s.setHp(12);
    s.setMp(8);
    s.setType(3);
    s.setMale(734);
    s.setFemale(1186);
    s.setX(32700);
    s.setY(32868);
    s.setMap(69);
    this._list.add(s);
  }
  
  public Stat getStat(int type) {
    if (type < 4)
      return getNewStat(this._list.get(type)); 
    return null;
  }
  
  private Stat getNewStat(Stat s) {
    Stat ss = new Stat();
    ss.setStr(s.getStr());
    ss.setCon(s.getCon());
    ss.setDex(s.getDex());
    ss.setWis(s.getWis());
    ss.setCha(s.getCha());
    ss.setInt(s.getInt());
    ss.setHp(s.getHp());
    ss.setMp(s.getMp());
    ss.setType(s.getType());
    ss.setMale(s.getMale());
    ss.setFemale(s.getFemale());
    ss.setX(s.getX());
    ss.setY(s.getY());
    ss.setMap(s.getMap());
    return ss;
  }
}
