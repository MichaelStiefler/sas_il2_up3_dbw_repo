// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdHotKey.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdRun;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.VK;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdHotKey extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.rts.Cmd.exist(map, "_$$"))
        {
            int i = 0;
            int k = 0;
            if(com.maddox.rts.Cmd.nargs(map, "_$$") > 1)
            {
                i = com.maddox.rts.VK.getKeyFromText(com.maddox.rts.Cmd.arg(map, "_$$", 0));
                k = 1;
            }
            int j = com.maddox.rts.VK.getKeyFromText(com.maddox.rts.Cmd.arg(map, "_$$", k));
            if(j == 0)
            {
                ERR_SOFT("unknown HotKey - " + com.maddox.rts.Cmd.arg(map, "_$$", k));
                return null;
            }
            java.lang.String s1 = com.maddox.rts.Cmd.args(map, "ENV");
            if(s1 == null)
                s1 = "default";
            if(com.maddox.rts.Cmd.nargs(map, "CMD") > 0)
                com.maddox.rts.HotKeyEnv.addHotKey(s1, i, j, com.maddox.rts.Cmd.args(map, "CMD"));
            else
            if(com.maddox.rts.Cmd.nargs(map, "CMDRUN") > 0)
            {
                java.lang.String s2 = com.maddox.rts.Cmd.args(map, "CMDRUN");
                com.maddox.rts.HotKeyEnv.addHotKey(s1, i, j, s2);
                com.maddox.rts.HotKeyCmdEnv.addCmd("cmdrun", new HotKeyCmdRun(cmdenv, com.maddox.rts.Cmd.exist(map, "REALTIME"), s2));
            } else
            {
                ERR_SOFT("empty HotKey command name");
                return null;
            }
        } else
        if(com.maddox.rts.Cmd.exist(map, "ENV") && 0 == com.maddox.rts.Cmd.nargs(map, "ENV") || !com.maddox.rts.Cmd.exist(map, "ENV"))
        {
            java.util.Iterator iterator = com.maddox.rts.HotKeyEnv.allEnv().iterator();
            INFO_HARD("HotKey environments:");
            com.maddox.rts.HotKeyEnv hotkeyenv;
            for(; iterator.hasNext(); INFO_HARD(hotkeyenv.name() + (hotkeyenv.isEnabled() ? " ENABLED" : " DISABLED")))
                hotkeyenv = (com.maddox.rts.HotKeyEnv)iterator.next();

        } else
        {
            java.lang.String s = com.maddox.rts.Cmd.args(map, "ENV");
            if(s == null)
                s = "default";
            com.maddox.rts.HotKeyEnv hotkeyenv1 = com.maddox.rts.HotKeyEnv.env(s);
            if(hotkeyenv1 == null)
            {
                ERR_SOFT("environment '" + s + "' not present");
                return null;
            }
            INFO_HARD("HotKey environment: " + s);
            showHotKeys(hotkeyenv1.all());
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private void showHotKeys(com.maddox.util.HashMapInt hashmapint)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
        {
            int i = hashmapintentry.getKey();
            int j = i >> 16 & 0xffff;
            int k = i & 0xffff;
            java.lang.String s = (java.lang.String)hashmapintentry.getValue();
            if(j != 0)
                INFO_HARD(com.maddox.rts.VK.getKeyText(j) + " " + com.maddox.rts.VK.getKeyText(k) + " = " + s);
            else
                INFO_HARD(com.maddox.rts.VK.getKeyText(k) + " = " + s);
        }

    }

    public CmdHotKey()
    {
        param.put("CMD", null);
        param.put("ENV", null);
        param.put("CMDRUN", null);
        param.put("REALTIME", null);
        _properties.put("NAME", "hotkey");
        _levelAccess = 1;
    }

    public static final java.lang.String CMD = "CMD";
    public static final java.lang.String ENV = "ENV";
    public static final java.lang.String CMDRUN = "CMDRUN";
    public static final java.lang.String REALTIME = "REALTIME";
}
