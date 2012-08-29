// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

class OrderRequest_Assistance extends com.maddox.il2.game.order.Order
{

    public OrderRequest_Assistance()
    {
        super("Request_Assistance");
        tmpV = new Vector3d();
    }

    public void run()
    {
        int i = Player().getArmy();
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
        boolean flag = false;
        if(maneuver.Group != null)
        {
            int j = com.maddox.il2.ai.air.AirGroupList.length(com.maddox.il2.ai.War.Groups[i - 1 & 1]);
            for(int k = 0; k < j; k++)
            {
                com.maddox.il2.ai.air.AirGroup airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(com.maddox.il2.ai.War.Groups[i - 1 & 1], k);
                if(airgroup == null || airgroup.nOfAirc <= 0 || !(airgroup.airc[0] instanceof com.maddox.il2.objects.air.TypeFighter) || airgroup.grTask != 1)
                    continue;
                tmpV.sub(maneuver.Group.Pos, airgroup.Pos);
                if(tmpV.lengthSquared() < 1000000000D)
                {
                    flag = true;
                    airgroup.clientGroup = maneuver.Group;
                    airgroup.setGroupTask(2);
                }
            }

        }
        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
        com.maddox.il2.objects.sounds.Voice.speakHelpNeededFromBase(Player(), flag);
    }

    com.maddox.JGP.Vector3d tmpV;
}
