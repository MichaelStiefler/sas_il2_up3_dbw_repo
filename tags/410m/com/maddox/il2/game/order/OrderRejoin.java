// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order, OrdersTree

class OrderRejoin extends com.maddox.il2.game.order.Order
{

    public OrderRejoin()
    {
        super("Rejoin");
    }

    public void run()
    {
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int i = 0; i < CommandSet().length; i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft) || !(aircraft.FM instanceof com.maddox.il2.ai.air.Pilot) || !com.maddox.il2.engine.Actor.isAlive(aircraft.FM.actor))
                continue;
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)(com.maddox.il2.ai.air.Pilot)aircraft.FM;
            if(pilot.Group == null)
                continue;
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
            if(aircraft.getWing() == Player().getWing() && pilot.Group != maneuver.Group && maneuver.Group != null)
                pilot.Group.rejoinToGroup(maneuver.Group);
            if(isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
                com.maddox.il2.objects.sounds.Voice.speakRejoin(aircraft);
            if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone())
            {
                pilot.aggressiveWingman = false;
                pilot.followOffset.set(200D, 0.0D, 20D);
                pilot.set_task(5);
                pilot.clear_stack();
                pilot.set_maneuver(65);
            } else
            {
                pilot.Group.setFormationAndScale((byte)0, 1.0F, false);
                pilot.Group.setGroupTask(1);
                pilot.Group.timeOutForTaskSwitch = 480;
            }
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
