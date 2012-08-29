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
    if (Cmd.exist(paramMap, "WRAP")) {
      if (Cmd.nargs(paramMap, "WRAP") == 1) {
        str = Cmd.arg(paramMap, "WRAP", 0);
        RTSConf.cur.console.bWrap = "on".equals(str);
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "WRAP" + " is " + (RTSConf.cur.console.bWrap ? "on" : "off"));
      }
    }

    if (Cmd.exist(paramMap, "PAUSE")) {
      if (Cmd.nargs(paramMap, "PAUSE") == 1) {
        str = Cmd.arg(paramMap, "PAUSE", 0);
        RTSConf.cur.console.setPause("on".equals(str));
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "PAUSE" + " is " + (RTSConf.cur.console.isPaused() ? "on" : "off"));
      }
    }

    if (Cmd.exist(paramMap, "LOG")) {
      if (Cmd.nargs(paramMap, "LOG") == 1) {
        str = Cmd.arg(paramMap, "LOG", 0);
        RTSConf.cur.console.log("on".equals(str));
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "LOG" + " is " + (RTSConf.cur.console.isLog() ? "on" : "off"));
      }
    }

    if (Cmd.exist(paramMap, "LOGTIME")) {
      if (Cmd.nargs(paramMap, "LOGTIME") == 1) {
        str = Cmd.arg(paramMap, "LOGTIME", 0);
        Console.setTypeTimeInLogFile("on".equals(str));
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "LOGTIME" + " is " + (Console.isTypeTimeInLogFile() ? "on" : "off"));
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

    if (Cmd.exist(paramMap, "LOAD")) {
      if (Cmd.nargs(paramMap, "LOAD") == 1) {
        str = Cmd.arg(paramMap, "LOAD", 0);
        RTSConf.cur.console.load(str);
      } else {
        ERR_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "LOAD" + " ???");
      }
    }

    if (Cmd.exist(paramMap, "SAVE"))
      if (Cmd.nargs(paramMap, "SAVE") == 1) {
        str = Cmd.arg(paramMap, "SAVE", 0);
        RTSConf.cur.console.save(str);
      } else {
        ERR_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "SAVE" + " ???");
      }
    int i;
    if (Cmd.exist(paramMap, "HISTORY")) {
      if (Cmd.nargs(paramMap, "HISTORY") == 1) {
        i = Cmd.arg(paramMap, "HISTORY", 0, 128, 0, 10000);
        RTSConf.cur.console.setMaxHistoryOut(i);
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "HISTORY" + " size = " + RTSConf.cur.console.maxHistoryOut());
      }
    }

    if (Cmd.exist(paramMap, "HISTORYCMD")) {
      if (Cmd.nargs(paramMap, "HISTORYCMD") == 1) {
        i = Cmd.arg(paramMap, "HISTORYCMD", 0, 128, 0, 10000);
        RTSConf.cur.console.setMaxHistoryCmd(i);
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "HISTORYCMD" + " size = " + RTSConf.cur.console.maxHistoryCmd());
      }
    }

    if (Cmd.exist(paramMap, "PAGE")) {
      if (Cmd.nargs(paramMap, "PAGE") == 1) {
        i = Cmd.arg(paramMap, "PAGE", 0, 20, 1, 100);
        RTSConf.cur.console.setPageHistoryOut(i);
      } else {
        INFO_HARD((String)this.jdField__properties_of_type_JavaUtilHashMap.get("NAME") + " " + "PAGE" + " size = " + RTSConf.cur.console.pageHistoryOut());
      }
    }

    if (Cmd.exist(paramMap, "CLEAR")) {
      RTSConf.cur.console.clear();
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdConsole()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("WRAP", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("CLEAR", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("HISTORY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("HISTORYCMD", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PAGE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PAUSE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOG", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOGTIME", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOGFILE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOAD", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SAVE", null);
    this.jdField__properties_of_type_JavaUtilHashMap.put("NAME", "console");
    this._levelAccess = 2;
  }
}