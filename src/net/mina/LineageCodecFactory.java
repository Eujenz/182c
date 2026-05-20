package net.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class LineageCodecFactory implements ProtocolCodecFactory {
  private final ProtocolEncoder encoder = new LineagePacketEncoder();
  
  private final ProtocolDecoder decoder = (ProtocolDecoder)new LineagePacketDecoder();
  
  public ProtocolDecoder getDecoder(IoSession client) throws Exception {
    return this.decoder;
  }
  
  public ProtocolEncoder getEncoder(IoSession client) throws Exception {
    return this.encoder;
  }
}
