// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order, OrdersTree

class OrderBreak extends com.maddox.il2.game.order.Order
{

    public OrderBreak()
    {
        super("Break");
    }

    public void run()
    {
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int i = 0; i < CommandSet().length; i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
            if(com.maddox.il2.engine.Actor.isAlive(aircraft) && (aircraft.FM instanceof com.maddox.il2.ai.air.Pilot))
            {
                com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)aircraft.FM;
                if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone())
                    pilot.aggressiveWingman = true;
                if(pilot.get_task() == 2)
                {
                    pilot.Leader = null;
                    pilot.set_task(3);
                    pilot.push(8);
                    pilot.push(8);
                    pilot.push(8);
                    pilot.push(8);
                    for(int j = 0; j < 3 - aircraft.aircIndex(); j++)
                        pilot.push(48);

                    pilot.pop();
                    pilot.setDumbTime(0L);
                    if(isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                        com.maddox.il2.objects.sounds.Voice.speakBreak(aircraft);
                }
            }
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
