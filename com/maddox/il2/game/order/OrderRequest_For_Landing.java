// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OrderRequest_For_Landing.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;
import java.io.PrintStream;

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
        try
        {
            com.maddox.il2.fm.FlightModel flightmodel = Player().FM;
            com.maddox.il2.ai.Airport airport = null;
            com.maddox.il2.ai.Way way = null;
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            {
                WP = Player().FM.actor.pos.getAbsPoint();
                com.maddox.il2.net.BornPlace bornplace = com.maddox.il2.game.ZutiSupportMethods.getPlayerBornPlace(WP, Player().getArmy());
                if(bornplace != null)
                    airport = com.maddox.il2.game.ZutiSupportMethods.getAirport(bornplace.place.x, bornplace.place.y);
            } else
            {
                way = flightmodel.AP.way;
                way.get(way.size() - 1).getP(WP);
            }
            flightmodel.BarometerZ = (float)com.maddox.il2.ai.World.land().HQ(WP.x, WP.y);
            int i = 0;
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight() && airport != null)
                i = airport.landingFeedback(WP, (com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
            else
            if(com.maddox.il2.engine.Actor.isAlive(way.landingAirport))
                i = way.landingAirport.landingFeedback(WP, (com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
            if(i == 0)
                com.maddox.il2.objects.sounds.Voice.speakLandingPermited((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
            if(i == 1)
                com.maddox.il2.objects.sounds.Voice.speakLandingDenied((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
            if(i == 2)
                com.maddox.il2.objects.sounds.Voice.speakWaveOff((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("OrderRequest_For_Landing error, ID_01: " + exception.toString());
        }
    }

    private static com.maddox.JGP.Point3d WP = new Point3d();

}
