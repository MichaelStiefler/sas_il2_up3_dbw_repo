// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Selector;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order, OrdersTree

class OrderAttack_My_Target extends com.maddox.il2.game.order.Order
{

    public OrderAttack_My_Target()
    {
        super("Attack_My_Target");
    }

    public void run()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.game.Selector.getTarget();
        if(actor == null)
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
            if(maneuver.target != null)
                actor = maneuver.target.actor;
            else
            if(maneuver.target_ground != null)
                actor = maneuver.target_ground;
        }
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int i = 0; i < CommandSet().length; i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft) || !(aircraft.FM instanceof com.maddox.il2.ai.air.Pilot) || !com.maddox.il2.engine.Actor.isAlive(aircraft.FM.actor))
                continue;
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)(com.maddox.il2.ai.air.Pilot)aircraft.FM;
            boolean flag = false;
            if(actor != null && (actor instanceof com.maddox.il2.objects.air.Aircraft))
            {
                pilot.Group.setATargMode(9);
                com.maddox.il2.ai.air.AirGroup airgroup = ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).Group;
                if(pilot.Group != null && airgroup != null)
                {
                    if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone())
                        if(pilot.Group.grTask != 3 && ((com.maddox.il2.ai.air.Maneuver)Player().FM).Group == pilot.Group)
                        {
                            com.maddox.il2.ai.air.AirGroup airgroup2 = new AirGroup(pilot.Group);
                            pilot.Group.delAircraft(aircraft);
                            airgroup2.addAircraft(aircraft);
                        } else
                        {
                            pilot.aggressiveWingman = true;
                        }
                    pilot.targetAll();
                    pilot.Group.targetGroup = airgroup;
                    pilot.target = null;
                    pilot.Group.setGroupTask(3);
                    flag = true;
                    if(isEnableVoice() && aircraft != Player() && pilot.canAttack())
                        if(aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0)
                            com.maddox.il2.objects.sounds.Voice.speakAttackMyTarget(aircraft);
                        else
                            com.maddox.il2.objects.sounds.Voice.speakOk(aircraft);
                }
            } else
            if(actor != null)
            {
                if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone() && pilot.Group.grTask != 4 && ((com.maddox.il2.ai.air.Maneuver)Player().FM).Group == pilot.Group)
                {
                    com.maddox.il2.ai.air.AirGroup airgroup1 = new AirGroup(pilot.Group);
                    pilot.Group.delAircraft(aircraft);
                    airgroup1.addAircraft(aircraft);
                }
                pilot.Group.setGTargMode(0);
                pilot.Group.setGTargMode(actor);
                com.maddox.il2.engine.Actor actor1 = pilot.Group.setGAttackObject(pilot.Group.numInGroup(aircraft));
                if(actor1 != null)
                {
                    if(isEnableVoice() && aircraft != Player())
                        if(aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0)
                            com.maddox.il2.objects.sounds.Voice.speakAttackMyTarget(aircraft);
                        else
                            com.maddox.il2.objects.sounds.Voice.speakOk(aircraft);
                    pilot.target_ground = null;
                    pilot.Group.setGroupTask(4);
                    flag = true;
                }
            }
            if(isEnableVoice() && aircraft != Player() && !flag)
                com.maddox.il2.objects.sounds.Voice.speakUnable(aircraft);
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
