package net.world.kingdom.function;

import net.world.kingdom.Kingdom;
import net.world.object.Character;
import net.world.object.L1Object;

public class Crown extends L1Object {
  private Kingdom k;
  
  private L1Object o;
  
  public Crown(Kingdom k, L1Object o) {
    this.k = k;
    this.o = o;
  }
  
  public void pickup(Character c, int x, int y, long count) {
    // Byte code:
    //   0: aload_1
    //   1: instanceof net/world/instance/PcInstance
    //   4: ifeq -> 252
    //   7: aload_1
    //   8: checkcast net/world/instance/PcInstance
    //   11: astore #6
    //   13: aload #6
    //   15: invokevirtual getClassType : ()I
    //   18: ifne -> 252
    //   21: aload #6
    //   23: invokevirtual getGfx : ()I
    //   26: aload #6
    //   28: invokevirtual getClassGfx : ()I
    //   31: if_icmpne -> 252
    //   34: aload #6
    //   36: invokevirtual getLevel : ()I
    //   39: bipush #25
    //   41: if_icmplt -> 252
    //   44: aload_0
    //   45: getfield k : Lnet/world/kingdom/Kingdom;
    //   48: invokevirtual getClanID : ()I
    //   51: aload #6
    //   53: invokevirtual getClanId : ()I
    //   56: if_icmpeq -> 252
    //   59: invokestatic getInstance : ()Lnet/world/function/ClanSystem;
    //   62: aload #6
    //   64: invokevirtual getKingdom : (Lnet/world/instance/PcInstance;)Lnet/world/kingdom/Kingdom;
    //   67: ifnonnull -> 252
    //   70: invokestatic getInstance : ()Lnet/world/function/AgitSystem;
    //   73: aload #6
    //   75: invokevirtual getClanId : ()I
    //   78: invokevirtual CheckAgit : (I)Z
    //   81: ifne -> 252
    //   84: invokestatic getInstance : ()Lnet/world/function/ClanSystem;
    //   87: invokestatic getInstance : ()Lnet/world/function/ClanSystem;
    //   90: aload #6
    //   92: invokevirtual getClanId : ()I
    //   95: invokevirtual getClan : (I)Lnet/world/function/bean/Clan;
    //   98: invokevirtual WarCheck : (Lnet/world/function/bean/Clan;)Ljava/lang/String;
    //   101: astore #7
    //   103: iconst_0
    //   104: istore #8
    //   106: aload #7
    //   108: ifnull -> 126
    //   111: aload #7
    //   113: aload_0
    //   114: getfield k : Lnet/world/kingdom/Kingdom;
    //   117: invokevirtual getClanNAME : ()Ljava/lang/String;
    //   120: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   123: ifne -> 146
    //   126: aload #7
    //   128: ifnonnull -> 252
    //   131: aload_0
    //   132: getfield k : Lnet/world/kingdom/Kingdom;
    //   135: invokevirtual getClanNAME : ()Ljava/lang/String;
    //   138: ldc ''
    //   140: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   143: ifeq -> 252
    //   146: aload #7
    //   148: ifnonnull -> 169
    //   151: aload_0
    //   152: getfield k : Lnet/world/kingdom/Kingdom;
    //   155: invokevirtual getClanNAME : ()Ljava/lang/String;
    //   158: ldc ''
    //   160: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   163: ifeq -> 169
    //   166: iconst_1
    //   167: istore #8
    //   169: aload_0
    //   170: invokevirtual toDelete : ()V
    //   173: aload_0
    //   174: getfield o : Lnet/world/object/L1Object;
    //   177: invokevirtual toDelete : ()V
    //   180: aload_0
    //   181: getfield k : Lnet/world/kingdom/Kingdom;
    //   184: invokevirtual restoreTop : ()V
    //   187: aload_0
    //   188: getfield k : Lnet/world/kingdom/Kingdom;
    //   191: invokevirtual WarEnd : ()V
    //   194: aload_0
    //   195: getfield k : Lnet/world/kingdom/Kingdom;
    //   198: invokevirtual ClearWarClan : ()V
    //   201: invokestatic getInstance : ()Lnet/world/WorldInstance;
    //   204: new net/network/server/S_War
    //   207: dup
    //   208: iconst_4
    //   209: aload #6
    //   211: invokevirtual getClanName : ()Ljava/lang/String;
    //   214: aload_0
    //   215: getfield k : Lnet/world/kingdom/Kingdom;
    //   218: invokevirtual getClanNAME : ()Ljava/lang/String;
    //   221: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   224: invokevirtual SendPacket : (Lnet/network/server/S_BasePacket;)V
    //   227: aload_0
    //   228: getfield k : Lnet/world/kingdom/Kingdom;
    //   231: aload #6
    //   233: iload #8
    //   235: invokevirtual update : (Lnet/world/object/L1Object;Z)V
    //   238: aload_0
    //   239: getfield k : Lnet/world/kingdom/Kingdom;
    //   242: invokevirtual updateDB : ()V
    //   245: aload_0
    //   246: getfield k : Lnet/world/kingdom/Kingdom;
    //   249: invokevirtual Teleport : ()V
    //   252: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #23	-> 0
    //   #24	-> 7
    //   #26	-> 13
    //   #27	-> 44
    //   #28	-> 59
    //   #29	-> 84
    //   #30	-> 103
    //   #31	-> 106
    //   #33	-> 126
    //   #34	-> 146
    //   #35	-> 166
    //   #37	-> 169
    //   #38	-> 173
    //   #40	-> 180
    //   #42	-> 187
    //   #44	-> 194
    //   #46	-> 201
    //   #48	-> 227
    //   #49	-> 238
    //   #51	-> 245
    //   #57	-> 252
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	253	0	this	Lnet/world/kingdom/function/Crown;
    //   0	253	1	c	Lnet/world/object/Character;
    //   0	253	2	x	I
    //   0	253	3	y	I
    //   0	253	4	count	J
    //   13	239	6	cha	Lnet/world/instance/PcInstance;
    //   103	149	7	target_clan_name	Ljava/lang/String;
    //   106	146	8	flag	Z
  }
}
