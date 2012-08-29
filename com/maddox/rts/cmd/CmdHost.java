// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdHost.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdHost extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.rts.Cmd.nargs(map, "_$$") == 1 && cmdenv.levelAccess() == 0)
        {
            java.lang.String s = com.maddox.rts.Cmd.arg(map, "_$$", 0);
            if(s != null && s.length() > 0)
                if(s.charAt(0) >= '0' && s.charAt(0) <= '9')
                {
                    ERR_HARD("Bad host name");
                    return null;
                } else
                {
                    com.maddox.rts.NetEnv.host().setShortName(s);
                    return s;
                }
        }
        boolean flag = com.maddox.rts.Cmd.exist(map, "PATH");
        INFO_HARD("localhost: " + com.maddox.rts.NetEnv.host().shortName());
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.NetHost nethost = (com.maddox.rts.NetHost)list.get(j);
            com.maddox.rts.NetChannel netchannel = nethost.masterChannel();
            java.lang.String s1 = "";
            if(netchannel != null)
                s1 = "[" + netchannel.id() + "]" + netchannel.remoteAddress().toString() + ":" + netchannel.remotePort();
            if(flag)
                INFO_HARD(" " + (j + 1) + ": " + nethost.fullName() + " " + s1);
            else
                INFO_HARD(" " + (j + 1) + ": " + nethost.shortName() + " " + s1);
        }

        return com.maddox.rts.NetEnv.host().shortName();
    }

    public CmdHost()
    {
        param.put("PATH", null);
        _properties.put("NAME", "host");
        _levelAccess = 2;
    }

    public static final java.lang.String PATH = "PATH";
}
