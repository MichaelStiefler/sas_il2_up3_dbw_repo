// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order, OrdersTree

class OrderTarget_All extends com.maddox.il2.game.order.Order
{

    public OrderTarget_All()
    {
        super("Target_All");
        Pd = new Point3d();
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
            pilot.targetAll();
            boolean flag = false;
            if(pilot.Group != null)
            {
                Pd.set(pilot.Group.Pos);
                if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone())
                    if(pilot.Group.grTask != 3 && ((com.maddox.il2.ai.air.Maneuver)Player().FM).Group == pilot.Group)
                    {
                        com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(pilot.Group);
                        pilot.Group.delAircraft(aircraft);
                        airgroup.addAircraft(aircraft);
                    } else
                    {
                        pilot.aggressiveWingman = true;
                    }
                pilot.Group.setATargMode(9);
                com.maddox.il2.ai.air.AirGroup airgroup1 = pilot.Group.chooseTargetGroup();
                if(airgroup1 != null)
                {
                    pilot.Group.targetGroup = airgroup1;
                    pilot.Group.setGroupTask(3);
                    flag = true;
                    if(isEnableVoice() && aircraft != Player() && pilot.canAttack())
                        if(aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0)
                            com.maddox.il2.objects.sounds.Voice.speakTargetAll(aircraft);
                        else
                            com.maddox.il2.objects.sounds.Voice.speakOk(aircraft);
                }
                if(!flag)
                {
                    if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone() && pilot.Group.grTask != 4 && ((com.maddox.il2.ai.air.Maneuver)Player().FM).Group == pilot.Group)
                    {
                        com.maddox.il2.ai.air.AirGroup airgroup2 = new AirGroup(pilot.Group);
                        pilot.Group.delAircraft(aircraft);
                        airgroup2.addAircraft(aircraft);
                    }
                    pilot.Group.setGTargMode(0);
                    pilot.Group.setGTargMode(Pd, 5000F);
                    com.maddox.il2.engine.Actor actor = pilot.Group.setGAttackObject(pilot.Group.numInGroup(aircraft));
                    if(actor != null)
                    {
                        pilot.Group.setGroupTask(4);
                        flag = true;
                        if(isEnableVoice() && aircraft != Player())
                            if(aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0)
                                com.maddox.il2.objects.sounds.Voice.speakTargetAll(aircraft);
                            else
                                com.maddox.il2.objects.sounds.Voice.speakOk(aircraft);
                    }
                }
            }
            if(isEnableVoice() && aircraft != Player() && !flag)
                com.maddox.il2.objects.sounds.Voice.speakUnable(aircraft);
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }

    private com.maddox.JGP.Point3d Pd;
}
