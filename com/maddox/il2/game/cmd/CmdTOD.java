// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdTOD.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;

public class CmdTOD extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(map.containsKey("_$$"))
        {
            if(cmdenv.levelAccess() == 0 || com.maddox.il2.game.Mission.isDogfight() && com.maddox.il2.game.Mission.isServer())
            {
                float f = com.maddox.rts.Cmd.arg(map, "_$$", 0, 12F, 0.0F, 24F);
                com.maddox.il2.ai.World.setTimeofDay(f);
                if(com.maddox.il2.engine.Config.isUSE_RENDER())
                    com.maddox.il2.ai.World.land().cubeFullUpdate();
                if(com.maddox.il2.game.Mission.cur() != null)
                    com.maddox.il2.game.Mission.cur().replicateTimeofDay();
            }
        } else
        {
            float f1 = com.maddox.il2.ai.World.getTimeofDay();
            INFO_HARD("Time Of Day: " + f1);
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdTOD()
    {
        _properties.put("NAME", "tod");
        _levelAccess = 2;
    }
}
