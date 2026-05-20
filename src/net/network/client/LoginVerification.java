package net.network.client;

import net.Config;
import net.LineageClient;

public final class LoginVerification extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    if (Config.LOGIN_VERIFICATION) {
      int lv = readC();
      int no = readC();
      if (lv == Config.LOGIN_LV && no == Config.LOGIN_NO) {
        lc.setLoginTime(System.currentTimeMillis());
        if (!lc.isLogin())
          lc.setLogin(true); 
      } 
    } 
  }
}
