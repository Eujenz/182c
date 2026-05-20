package net.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import net.Config;
import net.util.bean.GfxFrameList;
import net.util.bean.GfxFrameMode;

public class ClientFileLoad {
  private HashMap<Integer, GfxFrameList> LIST = new HashMap<Integer, GfxFrameList>();
  
  private static class Holder {
    static ClientFileLoad instance = new ClientFileLoad();
  }
  
  public static ClientFileLoad getInstance() {
    return Holder.instance;
  }
  
  private ClientFileLoad() {
    System.out.println("[SERVER] 读取list.spr...");
    try {
      BufferedReader lnr = new BufferedReader(new FileReader("client/list.spr"));
      String line = null;
      int gfxID = 0;
      while ((line = lnr.readLine()) != null)
        dataTest(line, gfxID++); 
      lnr.close();
    } catch (Exception exception) {}
  }
  
  private void dataTest(String db, int gfxID) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new net/util/bean/GfxFrameList
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: astore #4
    //   11: aload_1
    //   12: invokevirtual length : ()I
    //   15: istore #5
    //   17: iconst_0
    //   18: istore #6
    //   20: ldc ''
    //   22: astore #7
    //   24: ldc ''
    //   26: astore #8
    //   28: iconst_0
    //   29: istore #9
    //   31: iconst_0
    //   32: istore #10
    //   34: iconst_0
    //   35: istore #11
    //   37: iload #5
    //   39: bipush #10
    //   41: if_icmpge -> 45
    //   44: return
    //   45: aload #4
    //   47: iload_2
    //   48: invokevirtual setGfxID : (I)V
    //   51: new java/lang/StringBuffer
    //   54: dup
    //   55: invokespecial <init> : ()V
    //   58: astore #12
    //   60: goto -> 107
    //   63: aload_1
    //   64: iload #6
    //   66: invokevirtual charAt : (I)C
    //   69: sipush #255
    //   72: iand
    //   73: istore #11
    //   75: iload #11
    //   77: bipush #9
    //   79: if_icmpeq -> 114
    //   82: iload #11
    //   84: bipush #32
    //   86: if_icmpne -> 92
    //   89: goto -> 114
    //   92: aload #12
    //   94: aload_1
    //   95: iload #6
    //   97: invokevirtual charAt : (I)C
    //   100: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   103: pop
    //   104: iinc #6, 1
    //   107: iload #6
    //   109: iload #5
    //   111: if_icmplt -> 63
    //   114: aload_1
    //   115: iload #6
    //   117: iinc #6, 1
    //   120: invokevirtual charAt : (I)C
    //   123: sipush #255
    //   126: iand
    //   127: istore #11
    //   129: iload #11
    //   131: bipush #9
    //   133: if_icmpeq -> 114
    //   136: iload #11
    //   138: bipush #32
    //   140: if_icmpeq -> 114
    //   143: iinc #6, -1
    //   146: new java/lang/StringBuffer
    //   149: dup
    //   150: invokespecial <init> : ()V
    //   153: astore #12
    //   155: goto -> 213
    //   158: aload_1
    //   159: iload #6
    //   161: invokevirtual charAt : (I)C
    //   164: sipush #255
    //   167: iand
    //   168: istore #11
    //   170: iload #11
    //   172: bipush #9
    //   174: if_icmpeq -> 184
    //   177: iload #11
    //   179: bipush #32
    //   181: if_icmpne -> 198
    //   184: aload #12
    //   186: invokevirtual toString : ()Ljava/lang/String;
    //   189: invokevirtual length : ()I
    //   192: ifle -> 198
    //   195: goto -> 267
    //   198: aload #12
    //   200: aload_1
    //   201: iload #6
    //   203: invokevirtual charAt : (I)C
    //   206: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   209: pop
    //   210: iinc #6, 1
    //   213: iload #6
    //   215: iload #5
    //   217: if_icmplt -> 158
    //   220: goto -> 267
    //   223: aload_1
    //   224: iload #6
    //   226: invokevirtual charAt : (I)C
    //   229: sipush #255
    //   232: iand
    //   233: istore #11
    //   235: iload #11
    //   237: bipush #48
    //   239: if_icmplt -> 252
    //   242: iload #11
    //   244: bipush #57
    //   246: if_icmpgt -> 252
    //   249: goto -> 274
    //   252: aload #12
    //   254: aload_1
    //   255: iload #6
    //   257: invokevirtual charAt : (I)C
    //   260: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   263: pop
    //   264: iinc #6, 1
    //   267: iload #6
    //   269: iload #5
    //   271: if_icmplt -> 223
    //   274: aload #4
    //   276: aload #12
    //   278: invokevirtual toString : ()Ljava/lang/String;
    //   281: invokevirtual setName : (Ljava/lang/String;)V
    //   284: goto -> 676
    //   287: new java/lang/StringBuffer
    //   290: dup
    //   291: invokespecial <init> : ()V
    //   294: astore #12
    //   296: goto -> 409
    //   299: aload_1
    //   300: iload #6
    //   302: invokevirtual charAt : (I)C
    //   305: sipush #255
    //   308: iand
    //   309: istore #11
    //   311: iload #11
    //   313: bipush #40
    //   315: if_icmpne -> 394
    //   318: iinc #6, 1
    //   321: aload #12
    //   323: invokevirtual toString : ()Ljava/lang/String;
    //   326: astore #7
    //   328: new java/lang/StringBuffer
    //   331: dup
    //   332: invokespecial <init> : ()V
    //   335: astore #12
    //   337: goto -> 384
    //   340: aload_1
    //   341: iload #6
    //   343: invokevirtual charAt : (I)C
    //   346: sipush #255
    //   349: iand
    //   350: istore #11
    //   352: iload #11
    //   354: bipush #41
    //   356: if_icmpne -> 369
    //   359: aload #12
    //   361: invokevirtual toString : ()Ljava/lang/String;
    //   364: astore #8
    //   366: goto -> 416
    //   369: aload #12
    //   371: aload_1
    //   372: iload #6
    //   374: invokevirtual charAt : (I)C
    //   377: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   380: pop
    //   381: iinc #6, 1
    //   384: iload #6
    //   386: iload #5
    //   388: if_icmplt -> 340
    //   391: goto -> 416
    //   394: aload #12
    //   396: aload_1
    //   397: iload #6
    //   399: invokevirtual charAt : (I)C
    //   402: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   405: pop
    //   406: iinc #6, 1
    //   409: iload #6
    //   411: iload #5
    //   413: if_icmplt -> 299
    //   416: new java/util/StringTokenizer
    //   419: dup
    //   420: aload #8
    //   422: ldc ':'
    //   424: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   427: astore_3
    //   428: aload_3
    //   429: invokevirtual nextToken : ()Ljava/lang/String;
    //   432: pop
    //   433: aload_3
    //   434: invokevirtual countTokens : ()I
    //   437: istore #10
    //   439: iconst_0
    //   440: istore #13
    //   442: goto -> 545
    //   445: aload_3
    //   446: invokevirtual nextToken : ()Ljava/lang/String;
    //   449: astore #14
    //   451: iconst_0
    //   452: istore #15
    //   454: iconst_0
    //   455: istore #16
    //   457: iconst_0
    //   458: istore #17
    //   460: aload #14
    //   462: iconst_0
    //   463: invokevirtual charAt : (I)C
    //   466: bipush #48
    //   468: isub
    //   469: istore #16
    //   471: goto -> 476
    //   474: astore #18
    //   476: aload #14
    //   478: iconst_1
    //   479: invokevirtual charAt : (I)C
    //   482: bipush #48
    //   484: isub
    //   485: istore #17
    //   487: goto -> 492
    //   490: astore #18
    //   492: iload #17
    //   494: iflt -> 531
    //   497: iload #17
    //   499: bipush #9
    //   501: if_icmpgt -> 531
    //   504: iload #16
    //   506: iload #17
    //   508: iadd
    //   509: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   512: invokevirtual intValue : ()I
    //   515: istore #15
    //   517: iload #15
    //   519: bipush #20
    //   521: if_icmplt -> 535
    //   524: iload #16
    //   526: istore #15
    //   528: goto -> 535
    //   531: iload #16
    //   533: istore #15
    //   535: iload #9
    //   537: iload #15
    //   539: iadd
    //   540: istore #9
    //   542: iinc #13, 1
    //   545: iload #13
    //   547: iload #10
    //   549: if_icmplt -> 445
    //   552: iload #9
    //   554: bipush #40
    //   556: imul
    //   557: istore #9
    //   559: iload_2
    //   560: sipush #1080
    //   563: if_icmpne -> 604
    //   566: iconst_0
    //   567: istore #9
    //   569: goto -> 604
    //   572: aload_1
    //   573: iload #6
    //   575: invokevirtual charAt : (I)C
    //   578: sipush #255
    //   581: iand
    //   582: istore #11
    //   584: iload #11
    //   586: bipush #48
    //   588: if_icmplt -> 601
    //   591: iload #11
    //   593: bipush #57
    //   595: if_icmpgt -> 601
    //   598: goto -> 611
    //   601: iinc #6, 1
    //   604: iload #6
    //   606: iload #5
    //   608: if_icmplt -> 572
    //   611: new net/util/bean/GfxFrameMode
    //   614: dup
    //   615: invokespecial <init> : ()V
    //   618: astore #13
    //   620: aload #13
    //   622: aload #7
    //   624: invokevirtual setMode1 : (Ljava/lang/String;)V
    //   627: aload #13
    //   629: iload #9
    //   631: invokevirtual setMode1frame : (I)V
    //   634: new java/util/StringTokenizer
    //   637: dup
    //   638: aload #7
    //   640: ldc '.'
    //   642: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   645: astore_3
    //   646: aload_3
    //   647: invokevirtual nextToken : ()Ljava/lang/String;
    //   650: invokestatic parseInt : (Ljava/lang/String;)I
    //   653: istore #14
    //   655: aload #4
    //   657: getfield mode : Ljava/util/Map;
    //   660: iload #14
    //   662: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   665: aload #13
    //   667: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   672: pop
    //   673: iconst_0
    //   674: istore #9
    //   676: iload #6
    //   678: iload #5
    //   680: if_icmplt -> 287
    //   683: goto -> 688
    //   686: astore #12
    //   688: aload_0
    //   689: getfield LIST : Ljava/util/HashMap;
    //   692: aload #4
    //   694: invokevirtual getGfxID : ()I
    //   697: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   700: aload #4
    //   702: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   705: pop
    //   706: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #43	-> 0
    //   #44	-> 2
    //   #45	-> 11
    //   #46	-> 17
    //   #48	-> 20
    //   #49	-> 24
    //   #50	-> 28
    //   #51	-> 31
    //   #52	-> 34
    //   #54	-> 37
    //   #55	-> 44
    //   #58	-> 45
    //   #60	-> 51
    //   #61	-> 60
    //   #62	-> 63
    //   #63	-> 75
    //   #64	-> 89
    //   #66	-> 92
    //   #61	-> 104
    //   #70	-> 114
    //   #71	-> 129
    //   #72	-> 143
    //   #74	-> 146
    //   #75	-> 155
    //   #76	-> 158
    //   #77	-> 170
    //   #78	-> 195
    //   #80	-> 198
    //   #75	-> 210
    //   #83	-> 220
    //   #84	-> 223
    //   #85	-> 235
    //   #86	-> 249
    //   #88	-> 252
    //   #83	-> 264
    //   #91	-> 274
    //   #93	-> 284
    //   #94	-> 287
    //   #95	-> 296
    //   #96	-> 299
    //   #97	-> 311
    //   #98	-> 318
    //   #99	-> 321
    //   #100	-> 328
    //   #101	-> 337
    //   #102	-> 340
    //   #103	-> 352
    //   #104	-> 359
    //   #105	-> 366
    //   #107	-> 369
    //   #101	-> 381
    //   #110	-> 391
    //   #112	-> 394
    //   #95	-> 406
    //   #115	-> 416
    //   #116	-> 428
    //   #117	-> 433
    //   #118	-> 439
    //   #119	-> 445
    //   #120	-> 451
    //   #121	-> 454
    //   #122	-> 457
    //   #124	-> 460
    //   #125	-> 471
    //   #128	-> 476
    //   #129	-> 487
    //   #131	-> 492
    //   #132	-> 504
    //   #133	-> 517
    //   #134	-> 524
    //   #135	-> 528
    //   #136	-> 531
    //   #138	-> 535
    //   #118	-> 542
    //   #140	-> 552
    //   #141	-> 559
    //   #142	-> 566
    //   #145	-> 569
    //   #146	-> 572
    //   #147	-> 584
    //   #148	-> 598
    //   #145	-> 601
    //   #151	-> 611
    //   #152	-> 620
    //   #153	-> 627
    //   #154	-> 634
    //   #155	-> 646
    //   #156	-> 655
    //   #157	-> 673
    //   #93	-> 676
    //   #159	-> 683
    //   #161	-> 688
    //   #162	-> 706
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	707	0	this	Lnet/util/ClientFileLoad;
    //   0	707	1	db	Ljava/lang/String;
    //   0	707	2	gfxID	I
    //   2	705	3	st	Ljava/util/StringTokenizer;
    //   11	696	4	l	Lnet/util/bean/GfxFrameList;
    //   17	690	5	size	I
    //   20	687	6	loc	I
    //   24	683	7	mode1	Ljava/lang/String;
    //   28	679	8	mode1f	Ljava/lang/String;
    //   31	676	9	mode1ff	I
    //   34	673	10	STsize	I
    //   37	670	11	type	I
    //   60	623	12	sb	Ljava/lang/StringBuffer;
    //   442	110	13	i	I
    //   451	91	14	mode1fff	Ljava/lang/String;
    //   454	88	15	speed	I
    //   457	85	16	a	I
    //   460	82	17	b	I
    //   620	56	13	m	Lnet/util/bean/GfxFrameMode;
    //   655	21	14	mode	I
    // Exception table:
    //   from	to	target	type
    //   45	683	686	java/lang/Exception
    //   460	471	474	java/lang/Exception
    //   476	487	490	java/lang/Exception
  }
  
  public int getGfxMode(int gfx, int mode) {
    if (Config.TEST && 
      gfx == 2356)
      return 450; 
    GfxFrameList l = this.LIST.get(Integer.valueOf(gfx));
    if (l != null) {
      GfxFrameMode m = (GfxFrameMode)l.mode.get(Integer.valueOf(mode));
      if (m != null)
        return m.getMode1frame(); 
    } 
    return 1000;
  }
}
