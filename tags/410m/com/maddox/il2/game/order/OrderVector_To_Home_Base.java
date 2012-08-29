// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OrderVector_To_Home_Base.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.radios.BeaconGeneric;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

public class OrderVector_To_Home_Base extends com.maddox.il2.game.order.Order
{
    private class DelayedOrder extends java.util.TimerTask
    {

        public void run()
        {
            com.maddox.il2.objects.sounds.Voice.speakHeadingToHome(ac, com.maddox.il2.game.order.OrderVector_To_Home_Base.V1);
        }

        private java.util.Timer timer;
        private com.maddox.il2.objects.air.Aircraft ac;

        public DelayedOrder(com.maddox.il2.objects.air.Aircraft aircraft, double d)
        {
            timer = null;
            ac = null;
            ac = aircraft;
            timer = new Timer();
            long l = (long)java.lang.Math.random() * (long)(d / 7D);
            timer.schedule(this, 10000L + l);
        }
    }


    public OrderVector_To_Home_Base()
    {
        super("Vector_To_Home_Base");
    }

    public void run()
    {
        if(com.maddox.il2.game.Main.cur().mission.zutiRadar_DisableVectoring)
            return;
        Player().FM.moveCarrier();
        com.maddox.JGP.Point3d point3d = Player().FM.actor.pos.getAbsPoint();
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
        {
            com.maddox.il2.net.BornPlace bornplace = com.maddox.il2.game.ZutiSupportMethods.getPlayerBornPlace(point3d, Player().getArmy());
            if(bornplace != null)
                V2 = new Point3d(bornplace.place.x, bornplace.place.y, 0.0D);
            else
                return;
            break MISSING_BLOCK_LABEL_141;
        }
        com.maddox.il2.ai.Way way = Player().FM.AP.way;
        com.maddox.il2.ai.WayPoint waypoint = way.get(way.size() - 1);
        waypoint.getP(V2);
        double d;
        Player().FM.actor.pos.getAbs(V1);
        V1.sub(V2);
        d = java.lang.Math.sqrt(V1.x * V1.x + V1.y * V1.y);
        double d1 = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.lineOfSightDelta(Player().FM.getAltitude(), V2.z, d);
        if((d1 < 0.0D || d > 90000D) && com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
            return;
        try
        {
            if(isEnableVoice())
                if(com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
                    new DelayedOrder(Player(), d);
                else
                    com.maddox.il2.objects.sounds.Voice.speakHeadingToHome(Player(), V1);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("OrderVector_To_Home_Base error, ID_01: " + exception.toString());
        }
        return;
    }

    private static com.maddox.JGP.Point3d V1 = new Point3d();
    private static com.maddox.JGP.Point3d V2 = new Point3d();


}
