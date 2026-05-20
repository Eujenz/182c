package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectLock;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class ScrollLabeledVERRYEDHORAE extends ItemInstance {
  public static final int[] Rmap = new int[] { 70, 200, 303, 666 };
  
  public ScrollLabeledVERRYEDHORAE(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    byte b;
    int i, arrayOfInt[];
    for (i = (arrayOfInt = Rmap).length, b = 0; b < i; ) {
      int m = arrayOfInt[b];
      if (cha.getMap() == m) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(647));
        cha.SendPacket((S_BasePacket)new S_ObjectLock());
        return;
      } 
      b++;
    } 
    Location((L1Object)cha);
    cha.toTeleport(cha.getTempX(), cha.getTempY(), cha.getTempMap());
  }
  
  public static synchronized void Location(L1Object o) {
    switch (o.getMap()) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 5:
      case 14:
        TalkingIsland(o);
        return;
      case 15:
        Kent(o);
        return;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
        Gludio(o);
        return;
      case 4:
        if (o.getX() >= 32512 && o.getX() <= 32960 && o.getY() >= 32537 && o.getY() <= 33023) {
          Gludio(o);
        } else if ((o.getX() >= 32960 && o.getX() <= 33280 && o.getY() >= 32511 && o.getY() <= 33023) || (
          o.getX() >= 33088 && o.getX() <= 33280 && o.getY() >= 33023 && o.getY() <= 33087)) {
          Kent(o);
        } else if (o.getX() >= 32511 && o.getX() <= 32960 && o.getY() >= 32191 && o.getY() <= 32537) {
          OrcishForest(o);
        } else if (o.getX() >= 32960 && o.getX() <= 33216 && o.getY() >= 32191 && o.getY() <= 32511) {
          if (o.getClassType() == 2) {
            ElvenForest(o);
          } else {
            OrcishForest(o);
          } 
        } else if (o.getX() >= 33216 && o.getX() <= 33472 && o.getY() >= 32191 && o.getY() <= 32511) {
          Giran(o);
        } else if ((o.getX() >= 33472 && o.getX() <= 33856 && o.getY() >= 32191 && o.getY() <= 32511) || (
          o.getX() >= 33536 && o.getX() <= 33856 && o.getY() >= 32511 && o.getY() <= 32575)) {
          Welldone(o);
        } else if (o.getX() >= 32512 && o.getX() <= 32960 && o.getY() >= 33023 && o.getY() <= 33535) {
          Windawood(o);
        } else if (o.getX() >= 33280 && o.getX() <= 33792 && o.getY() >= 33023 && o.getY() <= 33535) {
          Heine(o);
        } else if ((o.getX() >= 32960 && o.getX() <= 33088 && o.getY() >= 33023 && o.getY() <= 33087) || (
          o.getX() >= 32959 && o.getX() <= 33280 && o.getY() >= 33087 && o.getY() <= 33535)) {
          SilverknightTown(o);
        } else if ((o.getX() >= 33280 && o.getX() <= 33536 && o.getY() >= 32511 && o.getY() <= 33023) || (
          o.getX() >= 33536 && o.getX() <= 33920 && o.getY() >= 32575 && o.getY() <= 33023)) {
          Giran(o);
        } else if ((o.getX() >= 33856 && o.getX() <= 33920 && o.getY() >= 32192 && o.getY() <= 32738) || (
          o.getX() >= 33920 && o.getX() <= 34303 && o.getY() >= 32128 && o.getY() <= 32738)) {
          Oren(o);
        } 
        return;
      case 16:
      case 17:
      case 18:
        ElvenForest(o);
        return;
      case 19:
      case 20:
      case 21:
        OrcishForest(o);
        return;
      case 22:
      case 23:
      case 24:
      case 29:
        Windawood(o);
        return;
      case 25:
      case 26:
      case 27:
      case 28:
        SilverknightTown(o);
        return;
      case 30:
      case 31:
      case 32:
      case 33:
      case 35:
      case 36:
      case 37:
        Giran(o);
        return;
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
        Windawood(o);
        return;
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
        Giran(o);
        return;
      case 58:
        Chobo(o);
        return;
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
        Heine(o);
        return;
      case 66:
      case 67:
        Welldone(o);
        return;
      case 69:
        Hidden(o);
        return;
      case 70:
        o.setTempX(32828);
        o.setTempY(32848);
        return;
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
        Oren(o);
        return;
    } 
    TalkingIsland(o);
  }
  
  private static void TalkingIsland(L1Object o) {
    o.setTempMap(0);
    switch (Util.rand(0, 4)) {
      case 0:
        o.setTempX(32596);
        o.setTempY(32916);
        break;
      case 1:
        o.setTempX(32583);
        o.setTempY(32931);
        break;
      case 2:
        o.setTempX(32587);
        o.setTempY(32947);
        break;
      case 3:
        o.setTempX(32566);
        o.setTempY(32952);
        break;
      case 4:
        o.setTempX(32561);
        o.setTempY(32973);
        break;
    } 
  }
  
  private static void Kent(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 4)) {
      case 0:
        o.setTempX(33060);
        o.setTempY(32745);
        break;
      case 1:
        o.setTempX(33045);
        o.setTempY(32757);
        break;
      case 2:
        o.setTempX(33060);
        o.setTempY(32770);
        break;
      case 3:
        o.setTempX(33049);
        o.setTempY(32789);
        break;
      case 4:
        o.setTempX(33058);
        o.setTempY(32806);
        break;
    } 
  }
  
  private static void Gludio(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 4)) {
      case 0:
        o.setTempX(32615);
        o.setTempY(32772);
        break;
      case 1:
        o.setTempX(32610);
        o.setTempY(32788);
        break;
      case 2:
        o.setTempX(32625);
        o.setTempY(32802);
        break;
      case 3:
        o.setTempX(32599);
        o.setTempY(32756);
        break;
      case 4:
        o.setTempX(32613);
        o.setTempY(32728);
        break;
    } 
  }
  
  private static void OrcishForest(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 3)) {
      case 0:
        o.setTempX(32741);
        o.setTempY(32436);
        break;
      case 1:
        o.setTempX(32749);
        o.setTempY(32446);
        break;
      case 2:
        o.setTempX(32738);
        o.setTempY(32452);
        break;
      case 3:
        o.setTempX(32750);
        o.setTempY(32435);
        break;
    } 
  }
  
  private static void ElvenForest(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 4)) {
      case 0:
        o.setTempX(33068);
        o.setTempY(32336);
        break;
      case 1:
        o.setTempX(33076);
        o.setTempY(32324);
        break;
      case 2:
        o.setTempX(33052);
        o.setTempY(32313);
        break;
      case 3:
        o.setTempX(33071);
        o.setTempY(32314);
        break;
      case 4:
        o.setTempX(33030);
        o.setTempY(32370);
        break;
    } 
  }
  
  private static void Giran(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 4)) {
      case 0:
        o.setTempX(33428);
        o.setTempY(32823);
        break;
      case 1:
        o.setTempX(33418);
        o.setTempY(32818);
        break;
      case 2:
        o.setTempX(33439);
        o.setTempY(32817);
        break;
      case 3:
        o.setTempX(33435);
        o.setTempY(32803);
        break;
      case 4:
        o.setTempX(33432);
        o.setTempY(32824);
        break;
    } 
  }
  
  private static void Welldone(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 5)) {
      case 0:
        o.setTempX(33723);
        o.setTempY(32512);
        break;
      case 1:
        o.setTempX(33693);
        o.setTempY(32513);
        break;
      case 2:
        o.setTempX(33696);
        o.setTempY(32498);
        break;
      case 3:
        o.setTempX(33702);
        o.setTempY(32492);
        break;
      case 4:
        o.setTempX(33746);
        o.setTempY(32499);
        break;
      case 5:
        o.setTempX(33710);
        o.setTempY(32521);
        break;
    } 
  }
  
  private static void Windawood(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 2)) {
      case 0:
        o.setTempX(32608);
        o.setTempY(33178);
        break;
      case 1:
        o.setTempX(32638);
        o.setTempY(33203);
        break;
      case 2:
        o.setTempX(32630);
        o.setTempY(33179);
        break;
    } 
  }
  
  private static void Heine(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 3)) {
      case 0:
        o.setTempX(33599);
        o.setTempY(33252);
        break;
      case 1:
        o.setTempX(33610);
        o.setTempY(33241);
        break;
      case 2:
        o.setTempX(33604);
        o.setTempY(33236);
        break;
      case 3:
        o.setTempX(33593);
        o.setTempY(33242);
        break;
    } 
  }
  
  private static void SilverknightTown(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 4)) {
      case 0:
        o.setTempX(33110);
        o.setTempY(33365);
        break;
      case 1:
        o.setTempX(33071);
        o.setTempY(33402);
        break;
      case 2:
        o.setTempX(33085);
        o.setTempY(33402);
        break;
      case 3:
        o.setTempX(33091);
        o.setTempY(33396);
        break;
      case 4:
        o.setTempX(33097);
        o.setTempY(33366);
        break;
    } 
  }
  
  private static void Oren(L1Object o) {
    o.setTempMap(4);
    switch (Util.rand(0, 2)) {
      case 0:
        o.setTempX(34053);
        o.setTempY(32284);
        break;
      case 1:
        o.setTempX(34046);
        o.setTempY(32268);
        break;
      case 2:
        o.setTempX(34059);
        o.setTempY(32314);
        break;
    } 
  }
  
  private static void Chobo(L1Object o) {
    o.setTempMap(58);
    o.setTempX(32680);
    o.setTempY(32867);
  }
  
  private static void Hidden(L1Object o) {
    o.setTempMap(69);
    o.setTempX(32700);
    o.setTempY(32868);
  }
}
