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

class OrderGT extends com.maddox.il2.game.order.Order
{

    public OrderGT(java.lang.String s)
    {
        super(s);
        Pd = new Point3d();
    }

    public void run(int i)
    {
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int j = 0; j < CommandSet().length; j++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[j];
            if(com.maddox.il2.engine.Actor.isAlive(aircraft) && (aircraft.FM instanceof com.maddox.il2.ai.air.Pilot) && com.maddox.il2.engine.Actor.isAlive(aircraft.FM.actor))
            {
                com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)aircraft.FM;
                pilot.attackGround(i);
                boolean flag = false;
                if(pilot.Group != null)
                {
                    Pd.set(pilot.Group.Pos);
                    if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.alone() && (pilot.Group.grTask != 4 || pilot.Group.gTargetPreference != i) && ((com.maddox.il2.ai.air.Maneuver)Player().FM).Group == pilot.Group)
                    {
                        com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(pilot.Group);
                        pilot.Group.delAircraft(aircraft);
                        airgroup.addAircraft(aircraft);
                    }
                    pilot.Group.setGTargMode(i);
                    pilot.Group.setGTargMode(Pd, 3000F);
                    com.maddox.il2.engine.Actor actor = pilot.Group.setGAttackObject(pilot.Group.numInGroup(aircraft));
                    if(actor != null)
                    {
                        if(isEnableVoice() && CommandSet()[j] != Player())
                            if(CommandSet()[j].getWing() == Player().getWing() || CommandSet()[j].aircIndex() == 0)
                                com.maddox.il2.objects.sounds.Voice.speakAttackGround(CommandSet()[j]);
                            else
                                com.maddox.il2.objects.sounds.Voice.speakOk(CommandSet()[j]);
                        pilot.target_ground = null;
                        pilot.Group.setGroupTask(4);
                        flag = true;
                    }
                }
                if(isEnableVoice() && CommandSet()[j] != Player() && !flag)
                    com.maddox.il2.objects.sounds.Voice.speakUnable(CommandSet()[j]);
            }
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }

    private com.maddox.JGP.Point3d Pd;
}
