package net.world.instance.inventory.function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryIdentify;
import net.network.server.S_InventoryStatus;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ScrollLabeledKERNODWEL extends ItemInstance {
  public ScrollLabeledKERNODWEL(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    ItemInstance item = cha.getInventory().getItemInvId(bp.readD());
    if (item != null) {
      if (!item.isDefinite()) {
        item.setDefinite(true);
        cha.SendPacket((S_BasePacket)new S_InventoryStatus(item));
      } 
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT * FROM definite_scroll WHERE nameid=?");
        st.setString(1, item.getName());
        rs = st.executeQuery();
        if (rs.next())
          cha.SendPacket((S_BasePacket)new S_InventoryIdentify(item, rs.getString(4), rs.getInt(3))); 
      } catch (Exception exception) {
      
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
    } 
  }
}
