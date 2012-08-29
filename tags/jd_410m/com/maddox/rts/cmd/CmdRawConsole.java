package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.RTSConf;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdRawConsole extends Cmd
{
  public static final String LOG = "LOG";
  public static final String LOGFILE = "LOGFILE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    String str;
    if (exist(paramMap, "LOG")) {
      if (nargs(paramMap, "LOG") == 1) {
        str = arg(paramMap, "LOG", 0);
        RTSConf.cur.console.log("on".equals(str));
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "LOG" + " is " + (RTSConf.cur.console.isLog() ? "on" : "off"));
      }
    }

    if (exist(paramMap, "LOGFILE")) {
      if (nargs(paramMap, "LOGFILE") == 1) {
        str = arg(paramMap, "LOGFILE", 0);
        RTSConf.cur.console.setLogFileName(str);
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "LOGFILE" + " is " + RTSConf.cur.console.logFileName());
      }
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdRawConsole()
  {
    this.param.put("LOG", null);
    this.param.put("LOGFILE", null);
    this._properties.put("NAME", "console");
    this._levelAccess = 1;
  }
}