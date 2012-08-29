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

class OrderCover_Me extends com.maddox.il2.game.order.Order
{

    public OrderCover_Me()
    {
        super("Cover_Me");
    }

    public void run()
    {
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int i = 0; i < CommandSet().length; i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
            if(com.maddox.il2.engine.Actor.isAlive(aircraft) && (aircraft.FM instanceof com.maddox.il2.ai.air.Pilot) && com.maddox.il2.engine.Actor.isAlive(aircraft.FM.actor))
            {
                com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)aircraft.FM;
                com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
                if(pilot.Group != null)
                {
                    if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone())
                    {
                        if(pilot.Group.grTask != 3)
                        {
                            if(((com.maddox.il2.ai.air.Maneuver)Player().FM).Group == pilot.Group)
                            {
                                com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(pilot.Group);
                                pilot.Group.delAircraft(aircraft);
                                airgroup.addAircraft(aircraft);
                            }
                        } else
                        {
                            if(pilot.Group != maneuver.Group && maneuver.Group != null)
                                pilot.Group.rejoinToGroup(maneuver.Group);
                            pilot.aggressiveWingman = false;
                        }
                    } else
                    if(aircraft == Wingman() && pilot.Group != maneuver.Group && maneuver.Group != null)
                        pilot.Group.rejoinToGroup(maneuver.Group);
                    pilot.airClient = Player().FM;
                    if(maneuver.danger != null)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)maneuver.danger;
                        pilot.target = maneuver1;
                        if(maneuver1.Group != null)
                        {
                            pilot.Group.targetGroup = maneuver1.Group;
                            pilot.Group.setGroupTask(3);
                        }
                    } else
                    if(maneuver.Group != pilot.Group && maneuver.Group != null)
                    {
                        pilot.Group.clientGroup = maneuver.Group;
                        pilot.Group.setGroupTask(2);
                    } else
                    if(!pilot.isBusy() && aircraft != Player())
                    {
                        pilot.followOffset.set(300D, (float)(aircraft.aircIndex() - 2) * 50F, 20D);
                        pilot.set_task(5);
                        pilot.clear_stack();
                        pilot.set_maneuver(65);
                        pilot.setDumbTime(60000L);
                    }
                    if(isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                        if(pilot.canAttack() || aircraft == Wingman())
                            com.maddox.il2.objects.sounds.Voice.speakCoverMe(aircraft);
                        else
                            com.maddox.il2.objects.sounds.Voice.speakUnable(aircraft);
                }
            }
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
