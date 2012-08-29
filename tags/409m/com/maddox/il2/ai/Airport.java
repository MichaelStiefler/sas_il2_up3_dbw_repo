// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Airport.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Point_Runaway;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgDreamListener;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeAmphibiousPlane;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.rts.Message;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai:
//            AirportGround, AirportMaritime, AirportCarrier, World, 
//            Way, WayPoint

public abstract class Airport extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.engine.MsgDreamListener
{
    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            update();
            return true;
        }

        Interpolater()
        {
        }
    }


    public static com.maddox.il2.ai.Airport nearest(com.maddox.JGP.Point3f point3f, int i, int j)
    {
        pd.set(point3f.x, point3f.y, point3f.z);
        return com.maddox.il2.ai.Airport.nearest(pd, i, j);
    }

    public static com.maddox.il2.ai.Airport nearest(com.maddox.JGP.Point3d point3d, int i, int j)
    {
        com.maddox.il2.ai.Airport airport = null;
        double d = 0.0D;
        pd.set(point3d.x, point3d.y, point3d.z);
        int k = com.maddox.il2.ai.World.cur().airports.size();
        for(int l = 0; l < k; l++)
        {
            com.maddox.il2.ai.Airport airport1 = (com.maddox.il2.ai.Airport)com.maddox.il2.ai.World.cur().airports.get(l);
            if(((j & 1) == 0 || !(airport1 instanceof com.maddox.il2.ai.AirportGround)) && ((j & 2) == 0 || !(airport1 instanceof com.maddox.il2.ai.AirportMaritime)) && ((j & 4) == 0 || !(airport1 instanceof com.maddox.il2.ai.AirportCarrier)) || !com.maddox.il2.engine.Actor.isAlive(airport1))
                continue;
            if(i >= 0)
            {
                int i1 = airport1.getArmy();
                if(i1 != 0 && i1 != i)
                    continue;
            }
            pd.z = airport1.pos.getAbsPoint().z;
            double d1 = pd.distanceSquared(airport1.pos.getAbsPoint());
            if(airport == null || d1 < d)
            {
                airport = airport1;
                d = d1;
            }
        }

        if(d > 225000000D)
            airport = null;
        return airport;
    }

    public Airport()
    {
        takeoffRequest = 0;
        landingRequest = 0;
        com.maddox.il2.ai.Airport _tmp = this;
        flags |= 0x200;
        com.maddox.il2.ai.World.cur().airports.add(this);
    }

    public static double distToNearestAirport(com.maddox.JGP.Point3d point3d)
    {
        return com.maddox.il2.ai.Airport.distToNearestAirport(point3d, -1, 7);
    }

    public static double distToNearestAirport(com.maddox.JGP.Point3d point3d, int i, int j)
    {
        com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(point3d, i, j);
        if(airport == null)
            return 225000000D;
        else
            return airport.pos.getAbsPoint().distance(point3d);
    }

    public static com.maddox.il2.ai.Airport makeLandWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        flightmodel.AP.way.curr().getP(PlLoc);
        int i = 0;
        com.maddox.il2.ai.Airport airport = null;
        int j = flightmodel.actor.getArmy();
        if(flightmodel.actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
        {
            i = 2;
            airport = com.maddox.il2.ai.Airport.nearest(PlLoc, j, i);
        } else
        if(flightmodel.AP.way.isLandingOnShip())
        {
            i = 4;
            airport = com.maddox.il2.ai.Airport.nearest(PlLoc, j, i);
            if(!com.maddox.il2.engine.Actor.isAlive(airport))
            {
                i = 1;
                airport = com.maddox.il2.ai.Airport.nearest(PlLoc, j, i);
            }
        } else
        {
            i = 3;
            if(!(flightmodel.actor instanceof com.maddox.il2.objects.air.TypeAmphibiousPlane))
                i &= -3;
            airport = com.maddox.il2.ai.Airport.nearest(PlLoc, j, i);
            if(!com.maddox.il2.engine.Actor.isAlive(airport))
            {
                i = 4;
                airport = com.maddox.il2.ai.Airport.nearest(PlLoc, j, i);
            }
        }
        com.maddox.il2.objects.air.Aircraft.debugprintln(flightmodel.actor, "Searching a place to land - Selecting RWY Type " + i);
        if(com.maddox.il2.engine.Actor.isAlive(airport))
        {
            if(airport.landWay(flightmodel))
            {
                flightmodel.AP.way.landingAirport = airport;
                return airport;
            } else
            {
                return null;
            }
        } else
        {
            return null;
        }
    }

    public boolean landWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        return false;
    }

    public void rebuildLandWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
    }

    public void rebuildLastPoint(com.maddox.il2.fm.FlightModel flightmodel)
    {
    }

    public double ShiftFromLine(com.maddox.il2.fm.FlightModel flightmodel)
    {
        return 0.0D;
    }

    public int landingFeedback(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft.FM.CT.GearControl > 0.0F)
            return 0;
        if(landingRequest > 0)
            return 1;
        double d = 640000D;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor != aircraft)
            {
                com.maddox.il2.objects.air.Aircraft aircraft1 = (com.maddox.il2.objects.air.Aircraft)actor;
                com.maddox.JGP.Point3d point3d1 = aircraft1.pos.getAbsPoint();
                double d1 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                if(d1 < d)
                {
                    if(((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).get_maneuver() == 25)
                    {
                        if(((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).wayCurPos instanceof com.maddox.il2.ai.air.Point_Runaway)
                            return 2;
                        if(aircraft1.FM.AP.way.isLanding() && aircraft1.FM.AP.way.Cur() > 5)
                            return 1;
                    }
                    if(((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).get_maneuver() == 26 || ((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).get_maneuver() == 64)
                        return 2;
                }
            }
        }

        landingRequest = 2000;
        return 0;
    }

    public abstract boolean nearestRunway(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Loc loc);

    public abstract void setTakeoff(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aaircraft[]);

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    protected void update()
    {
        if(takeoffRequest > 0)
            takeoffRequest--;
        if(landingRequest > 0)
            landingRequest--;
    }

    public void msgDream(boolean flag)
    {
        if(flag)
        {
            if(interpGet("AirportTicker") == null)
                interpPut(new Interpolater(), "AirportTicker", com.maddox.rts.Time.current(), null);
        } else
        {
            interpEnd("AirportTicker");
        }
    }

    public static final int TYPE_ANY = 7;
    public static final int TYPE_GROUND = 1;
    public static final int TYPE_MARITIME = 2;
    public static final int TYPE_CARRIER = 4;
    private static com.maddox.JGP.Point3f PlLoc = new Point3f();
    public int takeoffRequest;
    public int landingRequest;
    private static com.maddox.JGP.Point3d pd = new Point3d();

}
