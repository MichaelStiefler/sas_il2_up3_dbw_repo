package com.maddox.il2.game;

import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;

public class NetSafeLog
{

    private NetSafeLog()
    {
    }

    private static boolean isLocalActor(com.maddox.il2.engine.Actor theActor)
    {
        return theActor == com.maddox.il2.ai.World.getPlayerAircraft() && !theActor.isNetMirror();
    }

    public static void log(com.maddox.il2.engine.Actor logActor, java.lang.String logLine)
    {
        if(com.maddox.il2.game.NetSafeLog.isLocalActor(logActor))
            com.maddox.il2.game.HUD.log(logLine);
    }

    public static void training(com.maddox.il2.engine.Actor logActor, java.lang.String logLine)
    {
        if(com.maddox.il2.game.NetSafeLog.isLocalActor(logActor))
            com.maddox.il2.game.HUD.training(logLine);
    }

    public static void type(com.maddox.il2.engine.Actor logActor, java.lang.String logLine)
    {
        if(com.maddox.il2.game.NetSafeLog.isLocalActor(logActor))
            com.maddox.il2.ai.EventLog.type(logLine);
    }
}
