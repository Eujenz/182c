package net.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.LineageClient;
import net.database.CharacterTable;
import net.database.bean.L1Characte;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.world.bean.World;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class WorldInstance {
  private Map<Integer, World> list;
  
  private List<PcInstance> pc_list;
  
  private List<LineageClient> ui_client_list;
  
  private static class Holder {
    static WorldInstance instance = new WorldInstance();
  }
  
  public static WorldInstance getInstance() {
    return Holder.instance;
  }
  
  private WorldInstance() {
    this.list = new HashMap<Integer, World>();
    this.pc_list = new ArrayList<PcInstance>();
    this.ui_client_list = new ArrayList<LineageClient>();
  }
  
  public void CleanWorldItems() {
    synchronized (this.list) {
      byte b;
      int i;
      World[] arrayOfWorld;
      for (i = (arrayOfWorld = (World[])this.list.values().toArray((Object[])new World[this.list.size()])).length, b = 0; b < i; ) {
        World w = arrayOfWorld[b];
        byte b1;
        int j;
        L1Object[] arrayOfL1Object;
        for (j = (arrayOfL1Object = w.getList()).length, b1 = 0; b1 < j; ) {
          L1Object o = arrayOfL1Object[b1];
          if (o instanceof net.world.instance.ItemInstance)
            o.toDelete(); 
          b1++;
        } 
        b++;
      } 
    } 
  }
  
  public PcInstance[] getPc() {
    synchronized (this.pc_list) {
      return this.pc_list.<PcInstance>toArray(new PcInstance[this.pc_list.size()]);
    } 
  }
  
  public void addPc(PcInstance pc) {
    synchronized (this.pc_list) {
      this.pc_list.add(pc);
    } 
  }
  
  public void removePc(PcInstance pc) {
    synchronized (this.pc_list) {
      this.pc_list.remove(pc);
    } 
  }
  
  public LineageClient[] getUIClient() {
    synchronized (this.ui_client_list) {
      return this.ui_client_list.<LineageClient>toArray(new LineageClient[this.ui_client_list.size()]);
    } 
  }
  
  public void addUIClient(LineageClient lc) {
    synchronized (this.ui_client_list) {
      this.ui_client_list.add(lc);
    } 
  }
  
  public void removeUIClient(LineageClient lc) {
    synchronized (this.ui_client_list) {
      this.ui_client_list.remove(lc);
    } 
  }
  
  public PcInstance getPc(String name) {
    if (name != null && !"".equalsIgnoreCase(name)) {
      PcInstance[] pcList = getPc();
      if (pcList != null) {
        byte b;
        int i;
        PcInstance[] arrayOfPcInstance;
        for (i = (arrayOfPcInstance = pcList).length, b = 0; b < i; ) {
          PcInstance pc = arrayOfPcInstance[b];
          if (name.equalsIgnoreCase(pc.getName()))
            return pc; 
          b++;
        } 
      } 
    } 
    return null;
  }
  
  public int getPcSize() {
    synchronized (this.pc_list) {
      return this.pc_list.size();
    } 
  }
  
  public void Message(String msg) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      pc.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 20));
      b++;
    } 
  }
  
  public void SendPacket(S_BasePacket bp) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      pc.SendPacket(bp.clone());
      b++;
    } 
    bp.clear();
  }
  
  public void SendPacketGM(S_BasePacket bp) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      if (pc.isGm())
        pc.SendPacket(bp.clone()); 
      b++;
    } 
    bp.clear();
  }
  
  public void insert(L1Object o) {
    synchronized (this.list) {
      World w = this.list.get(Integer.valueOf(ChangeMapID(o.getMap())));
      if (w == null) {
        w = new World(ChangeMapID(o.getMap()));
        this.list.put(Integer.valueOf(w.getMap()), w);
      } 
      w.add(o);
    } 
  }
  
  public void delete(L1Object o) {
    synchronized (this.list) {
      World w = this.list.get(Integer.valueOf(ChangeMapID(o.getMap())));
      if (w == null) {
        w = new World(ChangeMapID(o.getMap()));
        this.list.put(Integer.valueOf(w.getMap()), w);
      } 
      w.remove(o);
    } 
  }
  
  public List<L1Object> getLocation(L1Object o, int loc) {
    synchronized (this.list) {
      World w = this.list.get(Integer.valueOf(ChangeMapID(o.getMap())));
      if (w != null) {
        o.setTempX(o.getX());
        o.setTempY(o.getY());
        o.setTempMap(o.getMap());
        List<L1Object> l = new ArrayList<L1Object>();
        byte b;
        int i;
        L1Object[] arrayOfL1Object;
        for (i = (arrayOfL1Object = w.getList()).length, b = 0; b < i; ) {
          L1Object obj = arrayOfL1Object[b];
          if (o.getObjectId() != obj.getObjectId() && o.getDistance(obj.getX(), obj.getY(), obj.getMap(), loc))
            l.add(obj); 
          b++;
        } 
        return l;
      } 
      return null;
    } 
  }
  
  private int ChangeMapID(int map) {
    if (map <= 33)
      return map; 
    switch (map) {
      case 35:
        return 34;
      case 36:
        return 35;
      case 37:
        return 36;
      case 43:
        return 37;
      case 44:
        return 38;
      case 45:
        return 39;
      case 46:
        return 40;
      case 47:
        return 41;
      case 48:
        return 42;
      case 49:
        return 43;
      case 50:
        return 44;
      case 51:
        return 45;
      case 52:
        return 46;
      case 53:
        return 47;
      case 54:
        return 48;
      case 55:
        return 49;
      case 56:
        return 50;
      case 57:
        return 51;
      case 58:
        return 52;
      case 59:
        return 53;
      case 60:
        return 54;
      case 61:
        return 55;
      case 62:
        return 56;
      case 63:
        return 58;
      case 64:
        return 59;
      case 65:
        return 60;
      case 66:
        return 61;
      case 67:
        return 62;
      case 68:
        return 63;
      case 69:
        return 64;
      case 70:
        return 65;
      case 72:
        return 66;
      case 73:
        return 67;
      case 74:
        return 68;
      case 75:
        return 69;
      case 76:
        return 70;
      case 77:
        return 71;
      case 78:
        return 72;
      case 79:
        return 73;
      case 80:
        return 74;
      case 81:
        return 75;
      case 82:
        return 76;
      case 83:
        return 77;
      case 84:
        return 78;
      case 85:
        return 79;
      case 86:
        return 80;
      case 87:
        return 81;
      case 89:
        return 82;
      case 90:
        return 83;
      case 91:
        return 84;
      case 98:
        return 85;
      case 99:
        return 86;
      case 101:
        return 87;
      case 102:
        return 88;
      case 103:
        return 89;
      case 104:
        return 90;
      case 105:
        return 91;
      case 106:
        return 92;
      case 107:
        return 93;
      case 108:
        return 94;
      case 109:
        return 95;
      case 110:
        return 96;
      case 111:
        return 97;
      case 112:
        return 98;
      case 113:
        return 99;
      case 114:
        return 100;
      case 115:
        return 101;
      case 116:
        return 102;
      case 117:
        return 103;
      case 118:
        return 104;
      case 119:
        return 105;
      case 120:
        return 106;
      case 121:
        return 107;
      case 122:
        return 108;
      case 123:
        return 109;
      case 124:
        return 110;
      case 125:
        return 111;
      case 126:
        return 112;
      case 127:
        return 113;
      case 128:
        return 114;
      case 129:
        return 115;
      case 130:
        return 116;
      case 131:
        return 117;
      case 132:
        return 118;
      case 133:
        return 119;
      case 134:
        return 120;
      case 135:
        return 121;
      case 136:
        return 122;
      case 137:
        return 123;
      case 138:
        return 124;
      case 139:
        return 125;
      case 140:
        return 126;
      case 141:
        return 127;
      case 142:
        return 128;
      case 143:
        return 129;
      case 144:
        return 130;
      case 145:
        return 131;
      case 146:
        return 132;
      case 147:
        return 133;
      case 148:
        return 134;
      case 149:
        return 135;
      case 150:
        return 136;
      case 151:
        return 137;
      case 152:
        return 138;
      case 153:
        return 139;
      case 154:
        return 140;
      case 155:
        return 141;
      case 156:
        return 142;
      case 157:
        return 143;
      case 158:
        return 144;
      case 159:
        return 145;
      case 160:
        return 146;
      case 161:
        return 147;
      case 162:
        return 148;
      case 163:
        return 149;
      case 164:
        return 150;
      case 165:
        return 151;
      case 166:
        return 152;
      case 167:
        return 153;
      case 168:
        return 154;
      case 169:
        return 155;
      case 170:
        return 156;
      case 171:
        return 157;
      case 172:
        return 158;
      case 173:
        return 159;
      case 174:
        return 160;
      case 175:
        return 161;
      case 176:
        return 162;
      case 177:
        return 163;
      case 178:
        return 164;
      case 179:
        return 165;
      case 180:
        return 166;
      case 181:
        return 167;
      case 182:
        return 168;
      case 183:
        return 169;
      case 184:
        return 170;
      case 185:
        return 171;
      case 186:
        return 172;
      case 187:
        return 173;
      case 188:
        return 174;
      case 189:
        return 175;
      case 190:
        return 176;
      case 191:
        return 177;
      case 192:
        return 178;
      case 193:
        return 179;
      case 194:
        return 180;
      case 195:
        return 181;
      case 196:
        return 182;
      case 197:
        return 183;
      case 198:
        return 184;
      case 199:
        return 185;
      case 200:
        return 186;
      case 201:
        return 187;
      case 202:
        return 188;
      case 203:
        return 189;
      case 204:
        return 190;
      case 209:
        return 191;
      case 210:
        return 192;
      case 211:
        return 193;
      case 212:
        return 194;
      case 213:
        return 195;
      case 214:
        return 196;
      case 215:
        return 197;
      case 216:
        return 198;
      case 217:
        return 199;
      case 218:
        return 200;
      case 219:
        return 201;
      case 220:
        return 202;
      case 221:
        return 203;
      case 222:
        return 204;
      case 223:
        return 205;
      case 224:
        return 206;
      case 225:
        return 207;
      case 226:
        return 208;
      case 227:
        return 209;
      case 228:
        return 210;
      case 229:
        return 211;
      case 230:
        return 212;
      case 231:
        return 213;
      case 232:
        return 214;
      case 233:
        return 215;
      case 234:
        return 216;
      case 235:
        return 217;
      case 236:
        return 218;
      case 237:
        return 219;
      case 240:
        return 220;
      case 241:
        return 221;
      case 242:
        return 222;
      case 243:
        return 223;
      case 300:
        return 224;
      case 301:
        return 225;
      case 302:
        return 226;
      case 303:
        return 227;
      case 304:
        return 228;
      case 305:
        return 229;
      case 306:
        return 230;
      case 307:
        return 231;
      case 308:
        return 232;
      case 309:
        return 233;
      case 310:
        return 234;
      case 320:
        return 235;
      case 330:
        return 236;
      case 340:
        return 237;
      case 350:
        return 238;
      case 360:
        return 239;
      case 370:
        return 240;
      case 400:
        return 241;
      case 401:
        return 242;
      case 410:
        return 243;
      case 420:
        return 244;
      case 430:
        return 245;
      case 440:
        return 246;
      case 441:
        return 247;
      case 442:
        return 248;
      case 443:
        return 249;
      case 444:
        return 250;
      case 445:
        return 251;
      case 446:
        return 252;
      case 447:
        return 253;
      case 450:
        return 254;
      case 451:
        return 255;
      case 452:
        return 256;
      case 453:
        return 257;
      case 454:
        return 258;
      case 455:
        return 259;
      case 456:
        return 260;
      case 457:
        return 261;
      case 460:
        return 262;
      case 461:
        return 263;
      case 462:
        return 264;
      case 463:
        return 265;
      case 464:
        return 266;
      case 465:
        return 267;
      case 466:
        return 268;
      case 467:
        return 269;
      case 480:
        return 270;
      case 509:
        return 271;
      case 610:
        return 272;
      case 666:
        return 273;
      case 2000:
        return 274;
      case 2001:
        return 275;
      case 2002:
        return 276;
      case 2003:
        return 277;
      case 16384:
        return 278;
      case 16896:
        return 279;
      case 17408:
        return 280;
      case 17920:
        return 281;
      case 18432:
        return 282;
      case 18944:
        return 283;
      case 19456:
        return 284;
      case 19968:
        return 285;
      case 20480:
        return 286;
      case 20992:
        return 287;
      case 21504:
        return 288;
      case 22016:
        return 289;
      case 22528:
        return 290;
      case 23040:
        return 291;
      case 23552:
        return 292;
      case 24064:
        return 293;
      case 24576:
        return 294;
      case 25088:
        return 57;
    } 
    return 295;
  }
  
  public PcInstance getOnlinePcOfAccount(String account) {
    for (L1Characte cha : CharacterTable.getInstance().getList()) {
      if (cha.getAccount().equalsIgnoreCase(account)) {
        PcInstance pc = getPc(cha.getName());
        if (pc != null)
          return pc; 
      } 
    } 
    return null;
  }
}
