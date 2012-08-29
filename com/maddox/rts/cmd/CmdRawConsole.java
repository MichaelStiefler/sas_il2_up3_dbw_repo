// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdRawConsole.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.RTSConf;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdRawConsole extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.rts.Cmd.exist(map, "LOG"))
            if(com.maddox.rts.Cmd.nargs(map, "LOG") == 1)
            {
                java.lang.String s = com.maddox.rts.Cmd.arg(map, "LOG", 0);
                com.maddox.rts.RTSConf.cur.console.log("on".equals(s));
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "LOG" + " is " + (com.maddox.rts.RTSConf.cur.console.isLog() ? "on" : "off"));
            }
        if(com.maddox.rts.Cmd.exist(map, "LOGFILE"))
            if(com.maddox.rts.Cmd.nargs(map, "LOGFILE") == 1)
            {
                java.lang.String s1 = com.maddox.rts.Cmd.arg(map, "LOGFILE", 0);
                com.maddox.rts.RTSConf.cur.console.setLogFileName(s1);
            } else
            {
                INFO_HARD((java.lang.String)_properties.get("NAME") + " " + "LOGFILE" + " is " + com.maddox.rts.RTSConf.cur.console.logFileName());
            }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdRawConsole()
    {
        param.put("LOG", null);
        param.put("LOGFILE", null);
        _properties.put("NAME", "console");
        _levelAccess = 1;
    }

    public static final java.lang.String LOG = "LOG";
    public static final java.lang.String LOGFILE = "LOGFILE";
}
