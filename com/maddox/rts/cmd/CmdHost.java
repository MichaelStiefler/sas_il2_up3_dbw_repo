package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdHost extends Cmd
{
  public static final String PATH = "PATH";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if ((Cmd.nargs(paramMap, "_$$") == 1) && (paramCmdEnv.levelAccess() == 0)) {
      String str1 = Cmd.arg(paramMap, "_$$", 0);
      if ((str1 != null) && (str1.length() > 0)) {
        if ((str1.charAt(0) >= '0') && (str1.charAt(0) <= '9')) {
          ERR_HARD("Bad host name");
          return null;
        }
        NetEnv.host().setShortName(str1);
        return str1;
      }
    }
    boolean bool = Cmd.exist(paramMap, "PATH");
    INFO_HARD("localhost: " + NetEnv.host().shortName());
    List localList = NetEnv.hosts();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetHost localNetHost = (NetHost)localList.get(j);
      NetChannel localNetChannel = localNetHost.masterChannel();
      String str2 = "";
      if (localNetChannel != null)
        str2 = "[" + localNetChannel.id() + "]" + localNetChannel.remoteAddress().toString() + ":" + localNetChannel.remotePort();
      if (bool) INFO_HARD(" " + (j + 1) + ": " + localNetHost.fullName() + " " + str2); else
        INFO_HARD(" " + (j + 1) + ": " + localNetHost.shortName() + " " + str2);
    }
    return NetEnv.host().shortName();
  }

  public CmdHost() {
    this.param.put("PATH", null);
    this._properties.put("NAME", "host");
    this._levelAccess = 2;
  }
}