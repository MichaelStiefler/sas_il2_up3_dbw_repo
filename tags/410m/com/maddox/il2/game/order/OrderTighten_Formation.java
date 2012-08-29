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

class OrderTighten_Formation extends com.maddox.il2.game.order.Order
{

    public OrderTighten_Formation()
    {
        super("Tighten_Formation");
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
            if(isEnableVoice() && CommandSet()[i] != Player() && (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
                com.maddox.il2.objects.sounds.Voice.speakTightFormation(CommandSet()[i]);
            if(pilot.Group == null || pilot.Group.nOfAirc <= 0 || pilot != pilot.Group.airc[0].FM)
                continue;
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)pilot.Group.airc[pilot.Group.nOfAirc - 1].FM;
            float f = maneuver.formationScale * 0.667F;
            if(f < 0.9F)
                f = 0.9F;
            pilot.Group.setFormationAndScale(maneuver.formationType, f, true);
        }

        com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
    }
}
