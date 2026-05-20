package net.network.server;

public final class S_ShopConsignmentPrice extends S_BasePacket {
  public S_ShopConsignmentPrice(int objId, String htmlId, String command) {
    writeC(91);
    writeD(objId);
    writeD(0);
    writeD(10);
    writeD(20);
    writeD(2000);
    writeH(0);
    writeS(htmlId);
    writeS(command);
  }
}
