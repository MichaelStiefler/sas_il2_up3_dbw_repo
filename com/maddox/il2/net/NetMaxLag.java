// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMaxLag.java

package com.maddox.il2.net;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;
import java.util.List;

// Referenced classes of package com.maddox.il2.net:
//            NetUser, NetServerParams, Chat

public class NetMaxLag
{

    public NetMaxLag()
    {
        warningCounter = 0;
        lastWarningTime = -1D;
    }

    private void checkLag(com.maddox.il2.objects.air.Aircraft aircraft, boolean flag)
    {
        double d = (double)com.maddox.rts.Time.real() / 1000D;
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        double d1 = netserverparams.cheaterWarningDelay();
        if(!flag)
            d1 += 3D;
        if(lastWarningTime > 0.0D && d - lastWarningTime < d1)
            return;
        int i = aircraft.getArmy();
        com.maddox.JGP.Point3d point3d = aircraft.pos.getAbsPoint();
        long l = -1L;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if(actor == aircraft || !(actor instanceof com.maddox.il2.objects.air.Aircraft) || !com.maddox.il2.engine.Actor.isAlive(actor) || actor.getArmy() == i || !((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer())
                continue;
            double d2 = point3d.distanceSquared(actor.pos.getAbsPoint());
            long l1;
            if(d2 > 4000000D)
            {
                if(flag)
                    l1 = aircraft.net.masterChannel().getMaxTimeout() / 2;
                else
                    l1 = netserverparams.masterChannel().getMaxTimeout() / 2;
            } else
            {
                double d3 = netserverparams.nearMaxLagTime();
                if(d2 > 10000D)
                {
                    double d4 = java.lang.Math.sqrt(d2);
                    d3 += ((d4 - 100D) / 1900D) * (double)(netserverparams.farMaxLagTime() - netserverparams.nearMaxLagTime());
                }
                l1 = (long)(d3 * 1000D);
            }
            if(l < 0L || l > l1)
                l = l1;
        }

        if(l < 0L)
            return;
        com.maddox.rts.NetChannel netchannel;
        if(flag)
            netchannel = aircraft.net.masterChannel();
        else
            netchannel = netserverparams.masterChannel();
        if(netchannel == null)
            return;
        if(l > (long)netchannel.getCurTimeoutOk())
            return;
        lastWarningTime = d;
        warningCounter++;
        boolean flag1 = false;
        if(netserverparams.cheaterWarningNum() >= 0)
            flag1 = warningCounter > netserverparams.cheaterWarningNum();
        if(flag1)
        {
            if(flag)
            {
                com.maddox.il2.net.Chat.sendLog(0, "user_cheatkick1", aircraft, null);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).kick(aircraft.netUser());
            } else
            {
                com.maddox.il2.net.Chat.sendLog(0, "user_cheatkick2", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), null);
                netchannel.destroy("You have been kicked from the server .");
            }
        } else
        if(flag)
        {
            java.lang.String s = "user_cheating" + (warningCounter % 3 + 1);
            com.maddox.il2.net.Chat.sendLog(0, s, aircraft, null);
        }
    }

    public void doServerCheck(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        checkLag(aircraft, true);
    }

    public void doClientCheck()
    {
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
        {
            return;
        } else
        {
            checkLag(com.maddox.il2.ai.World.getPlayerAircraft(), false);
            return;
        }
    }

    private static final double far = 2000D;
    private static final double near = 100D;
    private static final double far2 = 4000000D;
    private static final double near2 = 10000D;
    private int warningCounter;
    private double lastWarningTime;
}
