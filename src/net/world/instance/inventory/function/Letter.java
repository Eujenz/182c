package net.world.instance.inventory.function;

import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_LatterRead;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldInstance;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class Letter extends ItemInstance {
  private String sender_name;
  
  private String subject;
  
  public Letter(Item i) {
    super(i);
  }
  
  public ItemInstance clone() {
    Letter temp = (Letter)super.clone();
    temp.setSenderName(getSenderName());
    temp.setSubject(getSubject());
    return temp;
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (getItem().get_gfxid() == 464) {
      setCount(cha, getCount() - 1L);
      bp.readH();
      String to = bp.readS();
      String subject = bp.readSS();
      String content = bp.readSS();
      if (!subject.isEmpty())
        subject = subject.trim(); 
      sendLetter(cha, to, subject, content);
    } else {
      if (getItem().get_gfxid() == 465)
        setItem(ItemsTable.getInstance().getTemplate(342)); 
      cha.SendPacket((S_BasePacket)new S_LatterRead(this));
    } 
  }
  
  public void updateInventory(int uid) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE characters_letter SET paperInventory='1' WHERE uid='");
    sb.append(uid);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public String getSenderName() {
    return this.sender_name;
  }
  
  public void setSenderName(String sender_name) {
    this.sender_name = sender_name;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  protected void sendLetter(Character cha, String to, String subject, String content) {
    if (to != null && subject != null && content != null && to.length() > 0 && subject.length() > 0 && content.length() > 0) {
      int uid = getUID();
      addDB(cha.getName(), to, subject, content, uid);
      PcInstance pc = WorldInstance.getInstance().getPc(to);
      if (pc != null) {
        Letter letter = (Letter)ItemsTable.getInstance().newItem(341, false, true);
        letter.setLetterUid(uid);
        letter.setSenderName(cha.getName());
        letter.setSubject(subject);
        pc.getInventory().add(letter);
        pc.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)pc, 1091), true);
        pc.SendPacket((S_BasePacket)new S_ServerMessage(428));
        updateInventory(uid);
      } 
    } 
  }
  
  protected void addDB(String from, String to, String subject, String content, int uid) {
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO characters_letter SET type='Paper', paperFrom='");
    sb.append(from);
    sb.append("', paperTo='");
    sb.append(to);
    sb.append("', paperSubject='");
    sb.append(subject);
    sb.append("', paperMemo='");
    sb.append(content);
    sb.append("', paperYear='");
    sb.append(Util.Year());
    sb.append("', paperMonth='");
    sb.append(Util.Month());
    sb.append("', paperDate='");
    sb.append(Util.Date());
    sb.append("', uid='");
    sb.append(uid);
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
  }
  
  protected int getUID() {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT COUNT(*) FROM characters_letter");
    return DatabaseConnection.getInstance().query_select_count(sb.toString()) + 1;
  }
}
