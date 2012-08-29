// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdConsole.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.RTSConf;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdConsole extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "WRAP"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "WRAP") == 1)
            {
                java.lang.String s = com.maddox.rts.cmd.CmdConsole.arg(map, "WRAP", 0);
                com.maddox.rts.RTSConf.cur.console.bWrap = "on".equals(s);
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "WRAP" + " is " + (com.maddox.rts.RTSConf.cur.console.bWrap ? "on" : "off"));
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "PAUSE"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "PAUSE") == 1)
            {
                java.lang.String s1 = com.maddox.rts.cmd.CmdConsole.arg(map, "PAUSE", 0);
                com.maddox.rts.RTSConf.cur.console.setPause("on".equals(s1));
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "PAUSE" + " is " + (com.maddox.rts.RTSConf.cur.console.isPaused() ? "on" : "off"));
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "LOG"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "LOG") == 1)
            {
                java.lang.String s2 = com.maddox.rts.cmd.CmdConsole.arg(map, "LOG", 0);
                com.maddox.rts.RTSConf.cur.console.log("on".equals(s2));
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "LOG" + " is " + (com.maddox.rts.RTSConf.cur.console.isLog() ? "on" : "off"));
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "LOGTIME"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "LOGTIME") == 1)
            {
                java.lang.String s3 = com.maddox.rts.cmd.CmdConsole.arg(map, "LOGTIME", 0);
                com.maddox.rts.Console.setTypeTimeInLogFile("on".equals(s3));
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "LOGTIME" + " is " + (com.maddox.rts.Console.isTypeTimeInLogFile() ? "on" : "off"));
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "LOGFILE"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "LOGFILE") == 1)
            {
                java.lang.String s4 = com.maddox.rts.cmd.CmdConsole.arg(map, "LOGFILE", 0);
                com.maddox.rts.RTSConf.cur.console.setLogFileName(s4);
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "LOGFILE" + " is " + com.maddox.rts.RTSConf.cur.console.logFileName());
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "LOAD"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "LOAD") == 1)
            {
                java.lang.String s5 = com.maddox.rts.cmd.CmdConsole.arg(map, "LOAD", 0);
                com.maddox.rts.RTSConf.cur.console.load(s5);
            } else
            {
                ERR_HARD((java.lang.String)_properties.get("NAME") + " " + "LOAD" + " ???");
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "SAVE"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "SAVE") == 1)
            {
                java.lang.String s6 = com.maddox.rts.cmd.CmdConsole.arg(map, "SAVE", 0);
                com.maddox.rts.RTSConf.cur.console.save(s6);
            } else
            {
                ERR_HARD((java.lang.String)_properties.get("NAME") + " " + "SAVE" + " ???");
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "HISTORY"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "HISTORY") == 1)
            {
                int i = com.maddox.rts.cmd.CmdConsole.arg(map, "HISTORY", 0, 128, 0, 10000);
                com.maddox.rts.RTSConf.cur.console.setMaxHistoryOut(i);
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "HISTORY" + " size = " + com.maddox.rts.RTSConf.cur.console.maxHistoryOut());
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "HISTORYCMD"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "HISTORYCMD") == 1)
            {
                int j = com.maddox.rts.cmd.CmdConsole.arg(map, "HISTORYCMD", 0, 128, 0, 10000);
                com.maddox.rts.RTSConf.cur.console.setMaxHistoryCmd(j);
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "HISTORYCMD" + " size = " + com.maddox.rts.RTSConf.cur.console.maxHistoryCmd());
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "PAGE"))
            if(com.maddox.rts.cmd.CmdConsole.nargs(map, "PAGE") == 1)
            {
                int k = com.maddox.rts.cmd.CmdConsole.arg(map, "PAGE", 0, 20, 1, 100);
                com.maddox.rts.RTSConf.cur.console.setPageHistoryOut(k);
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "PAGE" + " size = " + com.maddox.rts.RTSConf.cur.console.pageHistoryOut());
            }
        if(com.maddox.rts.cmd.CmdConsole.exist(map, "CLEAR"))
            com.maddox.rts.RTSConf.cur.console.clear();
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdConsole()
    {
        param.put("WRAP", null);
        param.put("CLEAR", null);
        param.put("HISTORY", null);
        param.put("HISTORYCMD", null);
        param.put("PAGE", null);
        param.put("PAUSE", null);
        param.put("LOG", null);
        param.put("LOGTIME", null);
        param.put("LOGFILE", null);
        param.put("LOAD", null);
        param.put("SAVE", null);
        _properties.put("NAME", "console");
        _levelAccess = 2;
    }

    public static final java.lang.String WRAP = "WRAP";
    public static final java.lang.String CLEAR = "CLEAR";
    public static final java.lang.String HISTORY = "HISTORY";
    public static final java.lang.String HISTORYCMD = "HISTORYCMD";
    public static final java.lang.String PAGE = "PAGE";
    public static final java.lang.String PAUSE = "PAUSE";
    public static final java.lang.String LOG = "LOG";
    public static final java.lang.String LOGTIME = "LOGTIME";
    public static final java.lang.String LOGFILE = "LOGFILE";
    public static final java.lang.String LOAD = "LOAD";
    public static final java.lang.String SAVE = "SAVE";
}
