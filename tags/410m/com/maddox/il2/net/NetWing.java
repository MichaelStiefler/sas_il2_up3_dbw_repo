// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetWing.java

package com.maddox.il2.net;

import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;

// Referenced classes of package com.maddox.il2.net:
//            NetAirGroup

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
        int j = netaircraft.getArmy();
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)netaircraft;
        airc[0] = aircraft;
        aircraft.setArmy(j);
        aircraft.setOwner(this);
        if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.ai.World.setPlayerRegiment();
        aircraft.preparePaintScheme(i);
        aircraft.prepareCamouflage();
        if(com.maddox.il2.game.Mission.isDogfight() && com.maddox.il2.game.Mission.isServer())
        {
            com.maddox.il2.ai.air.AirGroup airgroup = null;
            int k = com.maddox.il2.ai.air.AirGroupList.length(com.maddox.il2.ai.War.Groups[j - 1 & 1]);
            for(int l = 0; l < k; l++)
            {
                airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(com.maddox.il2.ai.War.Groups[j - 1 & 1], l);
                if((airgroup instanceof com.maddox.il2.net.NetAirGroup) && airgroup != null && airgroup.nOfAirc < 16)
                {
                    airgroup.addAircraft(aircraft);
                    return;
                }
            }

            if(!(airgroup instanceof com.maddox.il2.net.NetAirGroup) || airgroup.nOfAirc >= 16)
            {
                com.maddox.il2.net.NetAirGroup netairgroup = new NetAirGroup();
                netairgroup.addAircraft(aircraft);
                com.maddox.il2.ai.air.AirGroupList.addAirGroup(com.maddox.il2.ai.War.Groups, j - 1 & 1, netairgroup);
            }
        }
    }

    public void destroy()
    {
        com.maddox.il2.net.NetWing.destroy(((com.maddox.rts.Destroy) (squadron())));
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
