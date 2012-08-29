// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OrderVector_To_Target.java

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
import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

public class OrderVector_To_Target extends com.maddox.il2.game.order.Order
{
    private class DelayedOrder extends java.util.TimerTask
    {

        public void run()
        {
            com.maddox.il2.objects.sounds.Voice.speakHeadingToTarget(ac, com.maddox.il2.game.order.OrderVector_To_Target.V1);
        }

        private java.util.Timer timer;
        private com.maddox.il2.objects.air.Aircraft ac;

        public DelayedOrder(com.maddox.il2.objects.air.Aircraft aircraft)
        {
            timer = null;
            ac = null;
            ac = aircraft;
            timer = new Timer();
            long l = (long)java.lang.Math.random() * (long)(dist / 6D);
            timer.schedule(this, 10000L + l);
        }
    }


    public OrderVector_To_Target()
    {
        super("Vector_To_Target");
        dist = 0.0D;
    }

    public void run()
    {
        if(com.maddox.il2.game.Main.cur().mission.zutiRadar_DisableVectoring)
            return;
        if(com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments && !isLosToHomeBase())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            return;
        com.maddox.il2.ai.Way way = Player().FM.AP.way;
        com.maddox.il2.ai.WayPoint waypoint = way.curr();
        waypoint.getP(V2);
        for(int i = way.Cur(); i < way.size(); i++)
        {
            com.maddox.il2.ai.WayPoint waypoint1 = way.get(i);
            if(waypoint1.Action == 3 || waypoint1.getTarget() != null && waypoint1.Action != 2)
                waypoint1.getP(V2);
        }

        Player().FM.actor.pos.getAbs(V1);
        V1.sub(V2);
        if(isEnableVoice())
            if(com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
                new DelayedOrder(Player());
            else
                com.maddox.il2.objects.sounds.Voice.speakHeadingToTarget(Player(), V1);
    }

    private boolean isLosToHomeBase()
    {
        Player().FM.moveCarrier();
        com.maddox.JGP.Point3d point3d = Player().FM.actor.pos.getAbsPoint();
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
        {
            com.maddox.il2.net.BornPlace bornplace = com.maddox.il2.game.ZutiSupportMethods.getPlayerBornPlace(point3d, Player().getArmy());
            if(bornplace != null)
                V2 = new Point3d(bornplace.place.x, bornplace.place.y, 0.0D);
            else
                return false;
        } else
        {
            com.maddox.il2.ai.Way way = Player().FM.AP.way;
            com.maddox.il2.ai.WayPoint waypoint = way.get(way.size() - 1);
            waypoint.getP(V2);
        }
        Player().FM.actor.pos.getAbs(V1);
        V1.sub(V2);
        dist = java.lang.Math.sqrt(V1.x * V1.x + V1.y * V1.y);
        double d = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.lineOfSightDelta(Player().FM.getAltitude(), V2.z, dist);
        return d >= 0.0D && dist <= 90000D;
    }

    private static com.maddox.JGP.Point3d V1 = new Point3d();
    private static com.maddox.JGP.Point3d V2 = new Point3d();
    private double dist;



}
