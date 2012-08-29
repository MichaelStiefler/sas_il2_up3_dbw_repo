// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

class OrderDrop_Tanks extends com.maddox.il2.game.order.Order
{

    public OrderDrop_Tanks()
    {
        super("Drop_Tanks");
    }

    public void run()
    {
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int i = 0; i < CommandSet().length; i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
            if(com.maddox.il2.engine.Actor.isAlive(aircraft) && (aircraft.FM instanceof com.maddox.il2.ai.air.Pilot))
                if(((com.maddox.il2.ai.air.Pilot)aircraft.FM).CT.dropFuelTanks())
                {
                    if(isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                        com.maddox.il2.objects.sounds.Voice.speakDropTanks(aircraft);
                } else
                if(isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                    com.maddox.il2.objects.sounds.Voice.speakUnable(aircraft);
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
