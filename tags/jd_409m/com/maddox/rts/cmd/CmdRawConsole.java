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
    if (Cmd.exist(paramMap, "LOG")) {
      if (Cmd.nargs(paramMap, "LOG") == 1) {
        str = Cmd.arg(paramMap, "LOG", 0);
        RTSConf.cur.console.log("on".equals(str));
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "LOG" + " is " + (RTSConf.cur.console.isLog() ? "on" : "off"));
      }
    }

    if (Cmd.exist(paramMap, "LOGFILE")) {
      if (Cmd.nargs(paramMap, "LOGFILE") == 1) {
        str = Cmd.arg(paramMap, "LOGFILE", 0);
        RTSConf.cur.console.setLogFileName(str);
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "LOGFILE" + " is " + RTSConf.cur.console.logFileName());
      }
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdRawConsole()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOG", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOGFILE", null);
    this.jdField__properties_of_type_JavaUtilHashMap.put("NAME", "console");
    this._levelAccess = 1;
  }
}