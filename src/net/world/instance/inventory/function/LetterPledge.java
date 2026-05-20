package net.world.instance.inventory.function;

import java.util.StringTokenizer;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.function.ClanSystem;
import net.world.function.bean.Clan;
import net.world.object.Character;

public class LetterPledge extends Letter {
  public LetterPledge(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (getItem().get_gfxid() == 464) {
      setCount(cha, getCount() - 1L);
      bp.readH();
      String to = bp.readS();
      String subject = bp.readSS();
      String content = bp.readSS();
      subject = subject.trim();
      Clan c = ClanSystem.getInstance().getClanName(to);
      if (c != null) {
        StringTokenizer st = new StringTokenizer(c.get_list(), " ");
        int size = st.countTokens();
        while (size-- > 0) {
          String name = st.nextToken();
          sendLetter(cha, name, subject, content);
        } 
      } 
    } else {
      super.clickItem(cha, bp);
    } 
  }
}
