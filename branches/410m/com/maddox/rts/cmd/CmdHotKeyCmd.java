// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdHotKeyCmd.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.util.HashMapExt;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdHotKeyCmd extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        java.lang.String s = com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "ENV") ? com.maddox.rts.cmd.CmdHotKeyCmd.args(map, "ENV") : null;
        if(com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "EXEC"))
        {
            java.lang.String s1 = com.maddox.rts.cmd.CmdHotKeyCmd.args(map, "EXEC");
            double d = com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "TIME") ? com.maddox.rts.cmd.CmdHotKeyCmd.arg(map, "TIME", 0, 0.0D, 0.0D, 60D) : 0.0D;
            int k = com.maddox.rts.HotKeyCmd.exec(d, s, s1);
            if(k > 0)
            {
                INFO_SOFT("  " + k + " " + s1 + " executed");
            } else
            {
                ERR_SOFT("command " + s1 + " not executed");
                return null;
            }
        } else
        if(com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "START"))
        {
            java.lang.String s2 = com.maddox.rts.cmd.CmdHotKeyCmd.args(map, "START");
            int i = com.maddox.rts.HotKeyCmd.exec(true, s, s2);
            if(i > 0)
            {
                INFO_SOFT("  " + i + " " + s2 + " started");
            } else
            {
                ERR_SOFT("command " + s2 + " not started");
                return null;
            }
        } else
        if(com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "STOP"))
        {
            java.lang.String s3 = com.maddox.rts.cmd.CmdHotKeyCmd.args(map, "STOP");
            int j = com.maddox.rts.HotKeyCmd.exec(false, s, s3);
            if(j > 0)
            {
                INFO_SOFT("  " + j + " " + s3 + " stoped");
            } else
            {
                ERR_SOFT("command " + s3 + " not stoped");
                return null;
            }
        } else
        if(com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "ENV") && 0 == com.maddox.rts.cmd.CmdHotKeyCmd.nargs(map, "ENV") || !com.maddox.rts.cmd.CmdHotKeyCmd.exist(map, "ENV"))
        {
            java.util.Iterator iterator = com.maddox.rts.HotKeyCmdEnv.allEnv().iterator();
            INFO_HARD("HotKeyCmd environments:");
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv;
            for(; iterator.hasNext(); INFO_HARD(hotkeycmdenv.name() + (hotkeycmdenv.isEnabled() ? " ENABLED" : " DISABLED")))
                hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)iterator.next();

        } else
        {
            java.lang.String s4 = com.maddox.rts.cmd.CmdHotKeyCmd.args(map, "ENV");
            if(s4 == null)
                s4 = "default";
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv1 = com.maddox.rts.HotKeyCmdEnv.env(s4);
            if(hotkeycmdenv1 == null)
            {
                ERR_SOFT("environment '" + s4 + "' not present");
                return null;
            }
            INFO_HARD("HotKeyCmd environment: " + s4);
            java.util.Map.Entry entry;
            for(java.util.Iterator iterator1 = hotkeycmdenv1.all().entrySet().iterator(); iterator1.hasNext(); INFO_HARD((java.lang.String)entry.getKey() + (((com.maddox.rts.HotKeyCmd)entry.getValue()).isRealTime() ? " REALTIME" : "")))
                entry = (java.util.Map.Entry)iterator1.next();

        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdHotKeyCmd()
    {
        param.put("ENV", null);
        param.put("EXEC", null);
        param.put("TIME", null);
        param.put("TIME", null);
        _properties.put("NAME", "hotkeycmd");
        _levelAccess = 1;
    }

    public static final java.lang.String ENV = "ENV";
    public static final java.lang.String EXEC = "EXEC";
    public static final java.lang.String TIME = "TIME";
    public static final java.lang.String START = "START";
    public static final java.lang.String STOP = "STOP";
}
