// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

class OrderRequest_For_Landing extends com.maddox.il2.game.order.Order
{

    public OrderRequest_For_Landing()
    {
        super("Request_For_Landing");
    }

    public void run()
    {
        com.maddox.il2.fm.FlightModel flightmodel = Player().FM;
        com.maddox.il2.ai.Way way = flightmodel.AP.way;
        way.get(way.size() - 1).getP(WP);
        flightmodel.BarometerZ = (float)com.maddox.il2.ai.World.land().HQ(WP.x, WP.y);
        int i = 0;
        if(com.maddox.il2.engine.Actor.isAlive(way.landingAirport))
            i = way.landingAirport.landingFeedback(WP, (com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
        if(i == 0)
            com.maddox.il2.objects.sounds.Voice.speakLandingPermited((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
        if(i == 1)
            com.maddox.il2.objects.sounds.Voice.speakLandingDenied((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
        if(i == 2)
            com.maddox.il2.objects.sounds.Voice.speakWaveOff((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
    }

    private static com.maddox.JGP.Point3d WP = new Point3d();

}
