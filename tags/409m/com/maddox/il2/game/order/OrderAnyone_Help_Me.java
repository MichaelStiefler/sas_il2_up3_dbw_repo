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
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

class OrderAnyone_Help_Me extends com.maddox.il2.game.order.Order
{

    public OrderAnyone_Help_Me()
    {
        super("Anyone_Help_Me");
    }

    public void run()
    {
        cset(PlayerSquad());
        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
        for(int i = 0; i < CommandSet().length; i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
            if(com.maddox.il2.engine.Actor.isAlive(aircraft) && (aircraft.FM instanceof com.maddox.il2.ai.air.Pilot) && com.maddox.il2.engine.Actor.isAlive(aircraft.FM.actor))
            {
                com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)aircraft.FM;
                if(pilot.Group != null)
                {
                    com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
                    pilot.airClient = Player().FM;
                    boolean flag = false;
                    if(maneuver.danger != null)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)maneuver.danger;
                        pilot.target = maneuver1;
                        if(maneuver1.Group != null)
                        {
                            if(isEnableVoice() && aircraft != Player() && pilot.canAttack() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                                com.maddox.il2.objects.sounds.Voice.speakAttackFighters(aircraft);
                            pilot.Group.targetGroup = maneuver1.Group;
                            pilot.Group.setGroupTask(3);
                            flag = true;
                        }
                    } else
                    if(maneuver.Group != null)
                    {
                        maneuver.Group.setATargMode(7);
                        com.maddox.il2.ai.air.AirGroup airgroup = maneuver.Group.chooseTargetGroup();
                        if(airgroup != null)
                        {
                            if(isEnableVoice() && aircraft != Player() && pilot.canAttack() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                                com.maddox.il2.objects.sounds.Voice.speakAttackFighters(aircraft);
                            pilot.Group.targetGroup = airgroup;
                            pilot.Group.setGroupTask(3);
                            flag = true;
                        }
                    }
                    if(isEnableVoice() && aircraft != Player() && !flag)
                        com.maddox.il2.objects.sounds.Voice.speakYouAreClear(CommandSet()[i]);
                }
            }
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
