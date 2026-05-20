package net.world.kingdom.function;

import net.network.server.S_Attribute;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectRestore;
import net.world.kingdom.Kingdom;
import net.world.object.Character;
import net.world.object.L1Object;

public class DoorKingdom extends Character {
  private boolean SideType;
  
  private int npc_id;
  
  private Kingdom k;
  
  public DoorKingdom(Kingdom k, int npc_id) {
    this.k = k;
    this.npc_id = npc_id;
  }
  
  public int getHp() {
    return (int)((getCurrentHp() / getTotalHp()) * 100.0D);
  }
  
  public void setCurrentHp(int currentHp) {
    if (this.npc_id == 592)
      return; 
    if (!isDead()) {
      super.setCurrentHp(currentHp);
      int hp = getHp();
      int mode = 0;
      if (hp > 80) {
        mode = 29;
      } else if (hp > 60) {
        mode = 33;
      } else if (hp > 40) {
        mode = 34;
      } else if (hp > 20) {
        mode = 35;
      } else {
        mode = 36;
      } 
      if (getGfxMode() != mode) {
        setGfxMode(mode);
        SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, getGfxMode()), true);
      } 
      if (isDead()) {
        setGfxMode(37);
        SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, getGfxMode()), true);
        send((L1Object)null);
      } 
    } 
  }
  
  public void toAttack(L1Object target, int type) {
    this.k.toAttack(target, type);
  }
  
  public void toRevival(L1Object own) {
    if (own == null) {
      setGfxMode(getClassGfxMode());
      setDelete(false);
      setDead(false);
      setPoison(false);
      setCurrentHp(getTotalHp());
      setCurrentMp(getTotalMp());
      SendPacket((S_BasePacket)new S_ObjectRestore(own, (L1Object)this), true);
    } 
  }
  
  public void OpenClose(boolean open) {
    if (isDead() || getGfxMode() > 29)
      return; 
    if (open) {
      open();
    } else {
      close();
    } 
    send((L1Object)null);
  }
  
  public void Attribute(L1Object o) {
    send(o);
  }
  
  private void open() {
    setGfxMode(28);
    SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, getGfxMode()), true);
  }
  
  private void close() {
    int hp = getHp();
    if (hp > 80) {
      setGfxMode(29);
    } else if (hp > 60) {
      setGfxMode(33);
    } else if (hp > 40) {
      setGfxMode(34);
    } else if (hp > 20) {
      setGfxMode(35);
    } else {
      setGfxMode(36);
    } 
    SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, getGfxMode()), true);
  }
  
  private void send(L1Object o) {
    int i;
    switch (this.k.getUid()) {
      case 1:
        if (this.SideType) {
          send(o, 33170, 32760, isMove());
          send(o, 33171, 32760, isMove());
          break;
        } 
        if (getHeading() == 4) {
          for (int j = 0; j < 4; j++)
            send(o, 33170 + j, 32760, isMove()); 
          break;
        } 
        for (i = 0; i < 4; i++)
          send(o, 33111, 32769 + i, isMove()); 
        break;
      case 3:
        if (this.SideType) {
          send(o, 32677, 33393, isMove());
          send(o, 32678, 33393, isMove());
          break;
        } 
        for (i = 0; i < 4; i++)
          send(o, 32589, 33407 + i, isMove()); 
        break;
      case 4:
        if (this.SideType) {
          send(o, 33631, 32661, isMove());
          send(o, 33632, 32661, isMove());
          break;
        } 
        switch (this.npc_id) {
          case 11118:
            for (i = 0; i < 4; i++)
              send(o, 33630 + i, 32735, isMove()); 
            break;
          case 11119:
            for (i = 0; i < 4; i++)
              send(o, 33631 + i, 32703, isMove()); 
            break;
          case 11120:
            for (i = 0; i < 4; i++)
              send(o, 33579, 32676 + i, isMove()); 
            break;
          case 11121:
            send(o, 33608, 32677, isMove());
            send(o, 33608, 32678, isMove());
            break;
          case 11122:
            send(o, 33653, 32677, isMove());
            send(o, 33653, 32678, isMove());
            break;
        } 
        break;
      case 5:
        if (this.SideType) {
          send(o, 33522, 33386, isMove());
          send(o, 33523, 33386, isMove());
          break;
        } 
        if (getHeading() == 6) {
          send(o, 33524, 33345, isMove());
          send(o, 33525, 33345, isMove());
          break;
        } 
        send(o, 33523, 33472, isMove());
        send(o, 33524, 33472, isMove());
        send(o, 33525, 33472, isMove());
        send(o, 33523, 33469, isMove());
        send(o, 33524, 33469, isMove());
        send(o, 33525, 33469, isMove());
        break;
      case 6:
        if (this.SideType) {
          send(o, 32844, 32812, isMove());
          send(o, 32844, 32813, isMove());
          break;
        } 
        if (getHeading() == 6) {
          for (i = 0; i < 4; i++)
            send(o, 32779, 32857 + i, isMove()); 
          break;
        } 
        for (i = 0; i < 4; i++)
          send(o, 32810 + i, 32888, isMove()); 
        break;
    } 
  }
  
  public void send(L1Object o, int x, int y, boolean move) {
    if (o == null) {
      SendPacket((S_BasePacket)new S_Attribute(x, y, getHeading(), move), true);
    } else {
      o.SendPacket((S_BasePacket)new S_Attribute(x, y, getHeading(), move));
    } 
  }
  
  public boolean isMove() {
    return !(!isDead() && getGfxMode() != 28);
  }
  
  public boolean isSideType() {
    return this.SideType;
  }
  
  public void setSideType(boolean sideType) {
    this.SideType = sideType;
  }
}
