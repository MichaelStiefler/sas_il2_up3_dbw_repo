// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdBanned.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.Connect;
import com.maddox.il2.net.NetBanned;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdBanned extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        boolean flag = map.containsKey("NAME") || map.containsKey("PATTERN") || map.containsKey("IP");
        com.maddox.il2.net.NetBanned netbanned = ((com.maddox.il2.net.Connect)com.maddox.rts.NetEnv.cur().connect).banned;
        boolean flag1 = true;
        if(map.containsKey("CLEAR"))
        {
            flag1 = false;
            if(flag)
            {
                if(map.containsKey("NAME"))
                    netbanned.name.clear();
                if(map.containsKey("PATTERN"))
                    netbanned.patt.clear();
                if(map.containsKey("IP"))
                    netbanned.ip.clear();
            } else
            {
                netbanned.name.clear();
                netbanned.patt.clear();
                netbanned.ip.clear();
            }
        }
        if(map.containsKey("LOAD"))
        {
            flag1 = false;
            com.maddox.il2.net.NetBanned _tmp = netbanned;
            java.lang.String s = com.maddox.il2.net.NetBanned.fileName;
            if(com.maddox.rts.Cmd.nargs(map, "LOAD") > 0)
                s = com.maddox.rts.Cmd.arg(map, "LOAD", 0);
            netbanned.load(s);
        }
        if(map.containsKey("ADD") && flag)
        {
            if(map.containsKey("NAME"))
            {
                flag1 = false;
                int i = com.maddox.rts.Cmd.nargs(map, "NAME");
                for(int j2 = 0; j2 < i; j2++)
                {
                    java.lang.String s2 = com.maddox.rts.Cmd.arg(map, "NAME", j2);
                    if(!netbanned.name.contains(s2))
                        netbanned.name.add(s2);
                }

            }
            if(map.containsKey("PATTERN"))
            {
                flag1 = false;
                int j = com.maddox.rts.Cmd.nargs(map, "PATTERN");
                for(int k2 = 0; k2 < j; k2++)
                {
                    java.lang.String s3 = com.maddox.rts.Cmd.arg(map, "PATTERN", k2);
                    if(!netbanned.patt.contains(s3))
                        netbanned.patt.add(s3);
                }

            }
            if(map.containsKey("IP"))
            {
                flag1 = false;
                int k = com.maddox.rts.Cmd.nargs(map, "IP");
                for(int l2 = 0; l2 < k; l2++)
                {
                    java.lang.String s4 = com.maddox.rts.Cmd.arg(map, "IP", l2);
                    int ai[][] = netbanned.ipItem(s4);
                    if(ai != null)
                    {
                        if(netbanned.findIpItem(ai) == -1)
                            netbanned.ip.add(ai);
                    } else
                    {
                        ERR_HARD("Unknown format: " + s4);
                    }
                }

            }
        }
        if(map.containsKey("REM") && flag)
        {
            if(map.containsKey("NAME"))
            {
                flag1 = false;
                int l = com.maddox.rts.Cmd.nargs(map, "NAME");
                for(int i3 = 0; i3 < l; i3++)
                {
                    java.lang.String s5 = com.maddox.rts.Cmd.arg(map, "NAME", i3);
                    int l3 = netbanned.name.indexOf(s5);
                    if(l3 >= 0)
                        netbanned.name.remove(l3);
                }

            }
            if(map.containsKey("PATTERN"))
            {
                flag1 = false;
                int i1 = com.maddox.rts.Cmd.nargs(map, "PATTERN");
                for(int j3 = 0; j3 < i1; j3++)
                {
                    java.lang.String s6 = com.maddox.rts.Cmd.arg(map, "PATTERN", j3);
                    int i4 = netbanned.patt.indexOf(s6);
                    if(i4 >= 0)
                        netbanned.patt.remove(i4);
                }

            }
            if(map.containsKey("IP"))
            {
                flag1 = false;
                int j1 = com.maddox.rts.Cmd.nargs(map, "IP");
                for(int k3 = 0; k3 < j1; k3++)
                {
                    java.lang.String s7 = com.maddox.rts.Cmd.arg(map, "IP", k3);
                    int ai1[][] = netbanned.ipItem(s7);
                    if(ai1 != null)
                    {
                        int j4 = netbanned.findIpItem(ai1);
                        if(j4 != -1)
                            netbanned.ip.remove(j4);
                    } else
                    {
                        ERR_HARD("Unknown format: " + s7);
                    }
                }

            }
        }
        if(map.containsKey("SAVE"))
        {
            flag1 = false;
            com.maddox.il2.net.NetBanned _tmp1 = netbanned;
            java.lang.String s1 = com.maddox.il2.net.NetBanned.fileName;
            if(com.maddox.rts.Cmd.nargs(map, "SAVE") > 0)
                s1 = com.maddox.rts.Cmd.arg(map, "SAVE", 0);
            netbanned.save(s1);
        }
        if(flag1)
        {
            if((map.containsKey("NAME") || !flag) && netbanned.name.size() > 0)
            {
                INFO_HARD("Name:");
                for(int k1 = 0; k1 < netbanned.name.size(); k1++)
                    INFO_HARD("  " + (java.lang.String)netbanned.name.get(k1));

            }
            if((map.containsKey("PATTERN") || !flag) && netbanned.patt.size() > 0)
            {
                INFO_HARD("Pattern:");
                for(int l1 = 0; l1 < netbanned.patt.size(); l1++)
                    INFO_HARD("  " + (java.lang.String)netbanned.patt.get(l1));

            }
            if((map.containsKey("IP") || !flag) && netbanned.ip.size() > 0)
            {
                INFO_HARD("IP:");
                for(int i2 = 0; i2 < netbanned.ip.size(); i2++)
                    INFO_HARD("  " + netbanned.ipItem(i2));

            }
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdBanned()
    {
        param.put("NAME", null);
        param.put("PATTERN", null);
        param.put("IP", null);
        param.put("ADD", null);
        param.put("REM", null);
        param.put("LOAD", null);
        param.put("SAVE", null);
        param.put("CLEAR", null);
        _properties.put("NAME", "banned");
        _levelAccess = 1;
    }

    public static final java.lang.String NAME = "NAME";
    public static final java.lang.String PATTERN = "PATTERN";
    public static final java.lang.String IP = "IP";
    public static final java.lang.String ADD = "ADD";
    public static final java.lang.String REM = "REM";
    public static final java.lang.String LOAD = "LOAD";
    public static final java.lang.String SAVE = "SAVE";
    public static final java.lang.String CLEAR = "CLEAR";
}
