package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.net.SocksUdpSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdSocks extends Cmd
{
  public static final String HOST = "HOST";
  public static final String PORT = "PORT";
  public static final String USER = "USER";
  public static final String PWD = "PWD";
  public static final String ON = "ON";
  public static final String OFF = "OFF";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 1;

    if (exist(paramMap, "ON")) {
      SocksUdpSocket.setProxyEnable(true);
      i = 0;
    } else if (exist(paramMap, "OFF")) {
      SocksUdpSocket.setProxyEnable(false);
      i = 0;
    }
    if (exist(paramMap, "HOST")) {
      if (nargs(paramMap, "HOST") > 0)
        SocksUdpSocket.setProxyHost(arg(paramMap, "HOST", 0));
      else {
        SocksUdpSocket.setProxyHost(null);
      }
      i = 0;
    }
    if ((exist(paramMap, "PORT")) && (nargs(paramMap, "PORT") > 0)) {
      SocksUdpSocket.setProxyPort(arg(paramMap, "PORT", 0, 1080));
      i = 0;
    }
    if (exist(paramMap, "USER")) {
      if (nargs(paramMap, "USER") > 0)
        SocksUdpSocket.setProxyUser(arg(paramMap, "USER", 0));
      else {
        SocksUdpSocket.setProxyUser(null);
      }
      i = 0;
    }
    if (exist(paramMap, "PWD")) {
      if (nargs(paramMap, "PWD") > 0)
        SocksUdpSocket.setProxyPassword(arg(paramMap, "PWD", 0));
      else {
        SocksUdpSocket.setProxyPassword(null);
      }
      i = 0;
    }
    if (i != 0) {
      INFO_HARD(" Proxy socks is " + (SocksUdpSocket.isProxyEnable() ? "enable" : "disable"));
      INFO_HARD("   HOST " + (SocksUdpSocket.getProxyHost() != null ? SocksUdpSocket.getProxyHost() : "UNKNOWN"));
      INFO_HARD("   PORT " + SocksUdpSocket.getProxyPort());
      if (SocksUdpSocket.getProxyUser() != null)
        INFO_HARD("   USER " + SocksUdpSocket.getProxyUser());
      if (SocksUdpSocket.getProxyPassword() != null)
        INFO_HARD("   PWD " + SocksUdpSocket.getProxyPassword());
    }
    return null;
  }

  public CmdSocks() {
    this.param.put("HOST", null);
    this.param.put("PORT", null);
    this.param.put("USER", null);
    this.param.put("PWD", null);
    this.param.put("ON", null);
    this.param.put("OFF", null);
    this._properties.put("NAME", "socks");
    this._levelAccess = 1;
  }
}