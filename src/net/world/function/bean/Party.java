package net.world.function.bean;

import java.util.ArrayList;
import java.util.List;
import net.Config;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHitratio;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class Party {
  private int uid;
  
  private PcInstance master;
  
  private List<PcInstance> list;
  
  public Party(PcInstance pc) {
    this.master = pc;
    this.uid = Config.getObjectID_ETC();
    this.list = new ArrayList<PcInstance>();
    this.list.add(this.master);
  }
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public PcInstance getMaster() {
    return this.master;
  }
  
  public void setMaster(PcInstance master) {
    this.master = master;
  }
  
  public PcInstance[] getList() {
    return this.list.<PcInstance>toArray(new PcInstance[this.list.size()]);
  }
  
  public void addList(PcInstance pc) {
    if (!this.list.contains(pc)) {
      this.list.add(pc);
      updateHpOpen(pc);
    } 
  }
  
  public void removeList(PcInstance pc) {
    if (this.list.contains(pc)) {
      updateHpClose(pc);
      this.list.remove(pc);
    } 
  }
  
  public int getCount() {
    return this.list.size();
  }
  
  public void SendPacket(S_BasePacket bp) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = getList()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      pc.SendPacket(bp.clone());
      b++;
    } 
    bp.clear();
  }
  
  public void updateHpOpen(PcInstance pc) {
    updateHp(pc, true);
  }
  
  public void updateHpClose(PcInstance pc) {
    updateHp(pc, false);
  }
  
  public void updateHp(PcInstance pc, boolean flag) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = getList()).length, b = 0; b < i; ) {
      PcInstance use = arrayOfPcInstance[b];
      if (pc.getObjectId() != use.getObjectId() && pc.getDistance(use.getX(), use.getY(), use.getMap(), 14)) {
        pc.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)use, flag));
        use.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)pc, flag));
      } 
      b++;
    } 
  }
  
  public void clear() {
    PcInstance[] p = getList();
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance1;
    for (i = (arrayOfPcInstance1 = p).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance1[b];
      pc.setPartyId(0);
      byte b1;
      int j;
      PcInstance[] arrayOfPcInstance;
      for (j = (arrayOfPcInstance = p).length, b1 = 0; b1 < j; ) {
        PcInstance use = arrayOfPcInstance[b1];
        if (pc.getObjectId() != use.getObjectId() && pc.getDistance(use.getX(), use.getY(), use.getMap(), 12)) {
          pc.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)use, false));
          use.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)pc, false));
        } 
        b1++;
      } 
      b++;
    } 
    this.list.clear();
    this.master = null;
    this.uid = 0;
  }
}
