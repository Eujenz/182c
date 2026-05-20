package net.network.server;

public final class S_PartyList extends S_BasePacket {
  public S_PartyList(int objId, String html, String partyName, String partyMembers) {
    this(objId, html, partyName, partyMembers, 1);
  }
  
  public S_PartyList(int objId, String html, String partyName, String partyMembers, int type) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeH(type);
    writeH(2);
    writeS(partyName);
    writeS(partyMembers);
  }
}
