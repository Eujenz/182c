package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.world.instance.ItemInstance;

public class SlimeRaceTicket extends ItemInstance {
  private int SlimeRaceUid;
  
  private int SlimeRacerIdx;
  
  private String SlimeRacerName;
  
  public SlimeRaceTicket(Item i) {
    super(i);
  }
  
  public SlimeRaceTicket clone() {
    SlimeRaceTicket temp = (SlimeRaceTicket)super.clone();
    temp.setSlimeRaceUid(this.SlimeRaceUid);
    temp.setSlimeRacerIdx(this.SlimeRacerIdx);
    temp.setSlimeRacerName(this.SlimeRacerName);
    return temp;
  }
  
  public int getSlimeRaceUid() {
    return this.SlimeRaceUid;
  }
  
  public void setSlimeRaceUid(int slimeRaceUid) {
    this.SlimeRaceUid = slimeRaceUid;
  }
  
  public int getSlimeRacerIdx() {
    return this.SlimeRacerIdx;
  }
  
  public void setSlimeRacerIdx(int slimeRacerIdx) {
    this.SlimeRacerIdx = slimeRacerIdx;
  }
  
  public String getSlimeRacerName() {
    return this.SlimeRacerName;
  }
  
  public void setSlimeRacerName(String slimeRacerName) {
    this.SlimeRacerName = slimeRacerName;
  }
  
  public String getName() {
    StringBuffer sb = new StringBuffer();
    sb.append(this.SlimeRaceUid);
    sb.append("-");
    sb.append(this.SlimeRacerIdx);
    sb.append(" ");
    sb.append(this.SlimeRacerName);
    return sb.toString();
  }
}
