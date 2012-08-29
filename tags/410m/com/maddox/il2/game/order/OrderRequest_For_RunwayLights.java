// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OrderRequest_For_RunwayLights.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportGround;
import com.maddox.il2.ai.War;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

public class OrderRequest_For_RunwayLights extends com.maddox.il2.game.order.Order
{

    public OrderRequest_For_RunwayLights()
    {
        super("Request_Runway_Lights");
    }

    public void run()
    {
        com.maddox.il2.objects.air.Aircraft aircraft = Player();
        com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(aircraft.FM.Loc, -1, 1);
        if(airport != null && (airport instanceof com.maddox.il2.ai.AirportGround) && airport.isAlive() && ((com.maddox.il2.ai.AirportGround)airport).hasLights() && com.maddox.il2.gui.GUI.pad.getAirportArmy(airport) == aircraft.getArmy() && airport.distance(aircraft) < 6000D)
        {
            com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.War.GetNearestEnemy(aircraft, -1, 12000F, 16);
            if(actor == null)
            {
                com.maddox.il2.objects.sounds.Voice.speakRooger(aircraft);
                ((com.maddox.il2.ai.AirportGround)airport).turnOnLights(aircraft);
            } else
            if(actor.getArmy() == aircraft.getArmy())
            {
                com.maddox.il2.objects.sounds.Voice.speakRooger(aircraft);
                ((com.maddox.il2.ai.AirportGround)airport).turnOnLights(aircraft);
            } else
            {
                com.maddox.il2.objects.sounds.Voice.speakNegative(aircraft);
            }
        }
    }
}
