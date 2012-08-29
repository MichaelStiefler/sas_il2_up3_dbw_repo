// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdSpeedBar.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdSpeedBar extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            if(map.containsKey("SHOW"))
            {
                com.maddox.il2.game.Main.cur().netServerParams.setShowSpeedBar(true);
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
            if(map.containsKey("HIDE"))
            {
                com.maddox.il2.game.Main.cur().netServerParams.setShowSpeedBar(false);
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
        }
        INFO_HARD("  SpeedBar is " + (com.maddox.il2.game.Main.cur().netServerParams.isShowSpeedBar() ? "SHOW" : "HIDE"));
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdSpeedBar()
    {
        param.put("SHOW", null);
        param.put("HIDE", null);
        _properties.put("NAME", "speedbar");
        _levelAccess = 1;
    }

    public static final java.lang.String SHOW = "SHOW";
    public static final java.lang.String HIDE = "HIDE";
}
