package com.maddox.il2.game.cmd;

import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetControlLock;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import java.io.PrintStream;
import java.util.HashMap;

public class CmdMission extends Cmd
{
  public static final String LOAD = "LOAD";
  public static final String BEGIN = "BEGIN";
  public static final String END = "END";
  public static final String DESTROY = "DESTROY";

  public Object exec(CmdEnv paramCmdEnv, String paramString)
  {
    int i = paramString.indexOf("LOAD");
    int j = paramString.indexOf("BEGIN");
    int k = paramString.indexOf("END");
    int m = paramString.indexOf("DESTROY");
    if (i >= 0) {
      if (!NetEnv.isActive()) {
        ERR_HARD("net environment NOT active");
        return null;
      }
      if (NetEnv.cur().control != null) {
        if ((NetEnv.cur().control instanceof NetControlLock)) {
          ERR_HARD("net in progress connecting");
          return null;
        }
        if (NetEnv.cur().control.isMirror()) {
          ERR_HARD("this host is alredy client");
          return null;
        }
      } else {
        ERR_HARD("this host is alredy client");
        return null;
      }
      String str = "";
      int n = paramString.length();
      if ((j > i) && (j < n)) n = j;
      if ((k > i) && (k < n)) n = k;
      if ((m > i) && (m < n)) n = m;
      if (n > i + "LOAD".length()) {
        str = paramString.substring(i + "LOAD".length(), n);
        int i1 = 0;
        while ((i1 < str.length()) && (str.charAt(i1) <= ' ')) i1++;
        n = str.length();
        while ((n > 0) && (str.charAt(n - 1) <= ' ')) n--;
        if ((i1 > 0) || (n < str.length()))
          if (i1 < n)
            str = str.substring(i1, n);
          else
            str = "";
      }
      try
      {
        Mission.load(str);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        ERR_HARD(str + "  NOT loaded [" + localException + "]");
        return null;
      }
    }

    if (j >= 0) {
      if ((Mission.cur() == null) || (Mission.cur().isDestroyed())) {
        ERR_HARD("Mission NOT loaded");
        return null;
      }
      if ((Mission.isNet()) && (!Mission.isServer())) {
        ERR_HARD("Mission begining only from server");
        return null;
      }
      Mission.cur().doBegin();
    }

    if (k >= 0) {
      if ((Mission.cur() == null) || (Mission.cur().isDestroyed())) {
        ERR_HARD("Mission NOT loaded");
        return null;
      }
      if ((Mission.isNet()) && (Config.isUSE_RENDER())) {
        return null;
      }
      Mission.cur().doEnd();
      return CmdEnv.RETURN_OK;
    }

    if (m >= 0) {
      if ((Mission.cur() == null) || (Mission.cur().isDestroyed())) {
        ERR_HARD("Mission NOT loaded");
        return null;
      }
      if ((Mission.isNet()) && (Config.isUSE_RENDER())) {
        return null;
      }
      Mission.cur().destroy();
      return CmdEnv.RETURN_OK;
    }

    if ((Mission.cur() == null) || (Mission.cur().isDestroyed())) {
      INFO_HARD("Mission NOT loaded");
    } else {
      Mission.cur(); if (Mission.isPlaying())
        INFO_HARD("Mission: " + Mission.cur().name() + " is Playing");
      else {
        INFO_HARD("Mission: " + Mission.cur().name() + " is Loaded");
      }
    }
    return CmdEnv.RETURN_OK;
  }
  public boolean isRawFormat() {
    return true;
  }
  public CmdMission() {
    this._properties.put("NAME", "mission");
    this._levelAccess = 1;
  }
}