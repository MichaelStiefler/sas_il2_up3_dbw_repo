// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdExtraOcclusion.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdExtraOcclusion extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster() && !com.maddox.il2.game.Main.cur().netServerParams.isSingle())
        {
            if(map.containsKey("ON"))
            {
                com.maddox.il2.game.Main.cur().netServerParams.setExtraOcclusion(true);
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
            if(map.containsKey("OFF"))
            {
                com.maddox.il2.game.Main.cur().netServerParams.setExtraOcclusion(false);
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
        }
        INFO_HARD("  ExtraOcclusion is " + (com.maddox.il2.game.Main.cur().netServerParams.isExtraOcclusion() ? "ON" : "OFF"));
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdExtraOcclusion()
    {
        param.put("ON", null);
        param.put("OFF", null);
        _properties.put("NAME", "extraocclusion");
        _levelAccess = 1;
    }

    public static final java.lang.String ON = "ON";
    public static final java.lang.String OFF = "OFF";
}
