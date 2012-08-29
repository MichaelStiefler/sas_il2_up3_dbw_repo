package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.RTSConf;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdConsole extends Cmd
{
  public static final String WRAP = "WRAP";
  public static final String CLEAR = "CLEAR";
  public static final String HISTORY = "HISTORY";
  public static final String HISTORYCMD = "HISTORYCMD";
  public static final String PAGE = "PAGE";
  public static final String PAUSE = "PAUSE";
  public static final String LOG = "LOG";
  public static final String LOGTIME = "LOGTIME";
  public static final String LOGFILE = "LOGFILE";
  public static final String LOAD = "LOAD";
  public static final String SAVE = "SAVE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    String str;
    if (exist(paramMap, "WRAP")) {
      if (nargs(paramMap, "WRAP") == 1) {
        str = arg(paramMap, "WRAP", 0);
        RTSConf.cur.console.bWrap = "on".equals(str);
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "WRAP" + " is " + (RTSConf.cur.console.bWrap ? "on" : "off"));
      }
    }

    if (exist(paramMap, "PAUSE")) {
      if (nargs(paramMap, "PAUSE") == 1) {
        str = arg(paramMap, "PAUSE", 0);
        RTSConf.cur.console.setPause("on".equals(str));
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "PAUSE" + " is " + (RTSConf.cur.console.isPaused() ? "on" : "off"));
      }
    }

    if (exist(paramMap, "LOG")) {
      if (nargs(paramMap, "LOG") == 1) {
        str = arg(paramMap, "LOG", 0);
        RTSConf.cur.console.log("on".equals(str));
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "LOG" + " is " + (RTSConf.cur.console.isLog() ? "on" : "off"));
      }
    }

    if (exist(paramMap, "LOGTIME")) {
      if (nargs(paramMap, "LOGTIME") == 1) {
        str = arg(paramMap, "LOGTIME", 0);
        Console.setTypeTimeInLogFile("on".equals(str));
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "LOGTIME" + " is " + (Console.isTypeTimeInLogFile() ? "on" : "off"));
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

    if (exist(paramMap, "LOAD")) {
      if (nargs(paramMap, "LOAD") == 1) {
        str = arg(paramMap, "LOAD", 0);
        RTSConf.cur.console.load(str);
      } else {
        ERR_HARD((String)this._properties.get("NAME") + " " + "LOAD" + " ???");
      }
    }

    if (exist(paramMap, "SAVE"))
      if (nargs(paramMap, "SAVE") == 1) {
        str = arg(paramMap, "SAVE", 0);
        RTSConf.cur.console.save(str);
      } else {
        ERR_HARD((String)this._properties.get("NAME") + " " + "SAVE" + " ???");
      }
    int i;
    if (exist(paramMap, "HISTORY")) {
      if (nargs(paramMap, "HISTORY") == 1) {
        i = arg(paramMap, "HISTORY", 0, 128, 0, 10000);
        RTSConf.cur.console.setMaxHistoryOut(i);
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "HISTORY" + " size = " + RTSConf.cur.console.maxHistoryOut());
      }
    }

    if (exist(paramMap, "HISTORYCMD")) {
      if (nargs(paramMap, "HISTORYCMD") == 1) {
        i = arg(paramMap, "HISTORYCMD", 0, 128, 0, 10000);
        RTSConf.cur.console.setMaxHistoryCmd(i);
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "HISTORYCMD" + " size = " + RTSConf.cur.console.maxHistoryCmd());
      }
    }

    if (exist(paramMap, "PAGE")) {
      if (nargs(paramMap, "PAGE") == 1) {
        i = arg(paramMap, "PAGE", 0, 20, 1, 100);
        RTSConf.cur.console.setPageHistoryOut(i);
      } else {
        INFO_HARD((String)this._properties.get("NAME") + " " + "PAGE" + " size = " + RTSConf.cur.console.pageHistoryOut());
      }
    }

    if (exist(paramMap, "CLEAR")) {
      RTSConf.cur.console.clear();
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdConsole()
  {
    this.param.put("WRAP", null);
    this.param.put("CLEAR", null);
    this.param.put("HISTORY", null);
    this.param.put("HISTORYCMD", null);
    this.param.put("PAGE", null);
    this.param.put("PAUSE", null);
    this.param.put("LOG", null);
    this.param.put("LOGTIME", null);
    this.param.put("LOGFILE", null);
    this.param.put("LOAD", null);
    this.param.put("SAVE", null);
    this._properties.put("NAME", "console");
    this._levelAccess = 2;
  }
}