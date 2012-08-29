// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdServer.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.USGS;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdServer extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur() == null)
            return null;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(!com.maddox.il2.game.Main.cur().netServerParams.isDedicated())
            return null;
        boolean flag = true;
        if(!com.maddox.il2.net.USGS.isUsed() && com.maddox.rts.Cmd.nargs(map, "NAME") > 0)
        {
            java.lang.String s = com.maddox.rts.Cmd.args(map, "NAME");
            com.maddox.il2.game.Main.cur().netServerParams.setServerName(s);
            com.maddox.il2.engine.Config.cur.netServerName = s;
            flag = false;
        }
        if(!com.maddox.il2.net.USGS.isUsed() && map.containsKey("PWD"))
        {
            flag = false;
            if(com.maddox.rts.Cmd.nargs(map, "PWD") > 0)
            {
                java.lang.String s1 = com.maddox.rts.Cmd.args(map, "PWD");
                com.maddox.il2.game.Main.cur().netServerParams.setPassword(s1);
            } else
            {
                com.maddox.il2.game.Main.cur().netServerParams.setPassword(null);
            }
        }
        if(com.maddox.rts.Cmd.nargs(map, "DESCRIPTION") > 0)
        {
            java.lang.String s2 = com.maddox.rts.Cmd.args(map, "DESCRIPTION");
            com.maddox.il2.game.Main.cur().netServerParams.serverDescription = s2;
            com.maddox.il2.engine.Config.cur.netServerDescription = s2;
            com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
            flag = false;
        }
        if(flag)
        {
            int i = com.maddox.il2.game.Main.cur().netServerParams.getType() >> 4;
            if(i == 0)
                INFO_HARD("Type: Local server");
            INFO_HARD("Name: " + com.maddox.il2.game.Main.cur().netServerParams.serverName());
            INFO_HARD("Description: " + com.maddox.il2.game.Main.cur().netServerParams.serverDescription);
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdServer()
    {
        param.put("NAME", null);
        param.put("DESCRIPTION", null);
        param.put("PWD", null);
        _properties.put("NAME", "server");
        _levelAccess = 1;
    }

    private static final boolean DEBUG = true;
    public static final java.lang.String NAME = "NAME";
    public static final java.lang.String DESCRIPTION = "DESCRIPTION";
    public static final java.lang.String PWD = "PWD";
}
