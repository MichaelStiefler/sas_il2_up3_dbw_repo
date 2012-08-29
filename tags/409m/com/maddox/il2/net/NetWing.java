// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetWing.java

package com.maddox.il2.net;

import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.rts.ObjState;

public class NetWing extends com.maddox.il2.ai.Wing
{

    public int aircReady()
    {
        return !com.maddox.il2.engine.Actor.isValid(airc[0]) ? 0 : 1;
    }

    public int aircIndex(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        return airc[0] != aircraft ? -1 : 0;
    }

    public int indexInSquadron()
    {
        return indexInSquadron;
    }

    public void setPlane(com.maddox.il2.objects.air.NetAircraft netaircraft, int i)
    {
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)netaircraft;
        airc[0] = aircraft;
        aircraft.setArmy(getArmy());
        aircraft.setOwner(this);
        if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.ai.World.setPlayerRegiment();
        aircraft.preparePaintScheme(i);
        aircraft.prepareCamouflage();
    }

    public void destroy()
    {
        com.maddox.rts.ObjState.destroy(squadron());
        super.destroy();
    }

    public NetWing(com.maddox.il2.ai.Squadron squadron, int i)
    {
        indexInSquadron = i;
        setOwner(squadron);
        squadron.wing[i] = this;
        setArmy(squadron.getArmy());
    }

    private int indexInSquadron;
}
