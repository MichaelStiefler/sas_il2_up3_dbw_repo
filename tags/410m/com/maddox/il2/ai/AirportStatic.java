// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AirportStatic.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Time;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai:
//            Airport, AirportMaritime, AirportGround, Way, 
//            WayPoint, World

public abstract class AirportStatic extends com.maddox.il2.ai.Airport
{
    private static class Runway
    {

        public com.maddox.il2.engine.Loc loc;
        public float planeShift[];
        public com.maddox.il2.objects.air.Aircraft planes[];
        public int curPlaneShift;
        public int oldTickCounter;

        public Runway(com.maddox.il2.engine.Loc loc1)
        {
            loc = new Loc();
            planeShift = new float[32];
            planes = new com.maddox.il2.objects.air.Aircraft[32];
            curPlaneShift = 0;
            oldTickCounter = 0;
            loc.set(loc1.getX(), loc1.getY(), com.maddox.il2.ai.World.land().HQ(loc1.getX(), loc1.getY()), loc1.getAzimut(), 0.0F, 0.0F);
        }
    }


    public AirportStatic()
    {
        runway = new ArrayList();
    }

    public static void make(java.util.ArrayList arraylist, com.maddox.JGP.Point2f apoint2f[][], com.maddox.JGP.Point2f apoint2f1[][], com.maddox.JGP.Point2f apoint2f2[][])
    {
        if(arraylist == null)
            return;
        java.util.ArrayList arraylist1 = new ArrayList();
        double d = 4000000D;
        if(arraylist.size() == 4)
            d = 2890000D;
        while(arraylist.size() > 0) 
        {
            com.maddox.il2.engine.Loc loc = (com.maddox.il2.engine.Loc)arraylist.remove(0);
            boolean flag = false;
            com.maddox.il2.ai.AirportStatic airportstatic = null;
            int i = 0;
            do
            {
                if(i >= arraylist1.size())
                    break;
                airportstatic = (com.maddox.il2.ai.AirportStatic)arraylist1.get(i);
                if(airportstatic.oppositeRunway(loc) != null)
                {
                    flag = true;
                    break;
                }
                i++;
            } while(true);
            if(flag)
            {
                airportstatic.runway.add(new Runway(loc));
                int j = airportstatic.runway.size();
                p3d.set(0.0D, 0.0D, 0.0D);
                for(int k = 0; k < j; k++)
                {
                    loc = ((com.maddox.il2.ai.Runway)airportstatic.runway.get(k)).loc;
                    p3d.x += loc.getPoint().x;
                    p3d.y += loc.getPoint().y;
                    p3d.z += loc.getPoint().z;
                }

                p3d.x /= j;
                p3d.y /= j;
                p3d.z /= j;
                airportstatic.pos.setAbs(p3d);
            } else
            {
                java.lang.Object obj;
                if(com.maddox.il2.engine.Engine.cur.land.isWater(loc.getPoint().x, loc.getPoint().y))
                    obj = new AirportMaritime();
                else
                    obj = new AirportGround();
                obj.pos = new ActorPosStatic(((com.maddox.il2.engine.Actor) (obj)), loc);
                ((com.maddox.il2.ai.AirportStatic) (obj)).runway.add(new Runway(loc));
                arraylist1.add(obj);
            }
        }
    }

    public boolean landWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        flightmodel.AP.way.curr().getP(pWay);
        com.maddox.il2.ai.Runway runway1 = nearestRunway(pWay);
        if(runway1 == null)
            return false;
        com.maddox.il2.ai.Way way = new Way();
        float f = (float)com.maddox.il2.engine.Engine.land().HQ_Air(runway1.loc.getX(), runway1.loc.getY());
        float f1 = flightmodel.M.massEmpty / 3000F;
        if(f1 > 1.0F)
            f1 = 1.0F;
        if(flightmodel.EI.engines[0].getType() > 1)
            f1 = 1.0F;
        if(flightmodel.EI.engines[0].getType() == 3)
            f1 = 1.5F;
        float f2 = f1;
        if(f2 > 1.0F)
            f2 = 1.0F;
        for(int i = x.length - 1; i >= 0; i--)
        {
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint();
            pd.set(x[i] * f1, y[i] * f1, z[i] * f2);
            waypoint.set(java.lang.Math.min(v[i] * 0.278F, flightmodel.Vmax * 0.7F));
            waypoint.Action = 2;
            runway1.loc.transform(pd);
            float f3 = (float)com.maddox.il2.engine.Engine.land().HQ_Air(pd.x, pd.y);
            pd.z -= f3 - f;
            pf.set(pd);
            waypoint.set(pf);
            way.add(waypoint);
        }

        way.setLanding(true);
        flightmodel.AP.way = way;
        return true;
    }

    public void setTakeoff(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aaircraft[])
    {
        com.maddox.il2.ai.Runway runway1 = nearestRunway(point3d);
        if(runway1 == null)
            return;
        com.maddox.il2.ai.Runway runway2 = oppositeRunway(runway1.loc);
        double d = 1000D;
        if(runway2 != null)
            d = runway1.loc.getPoint().distance(runway2.loc.getPoint());
        if(com.maddox.rts.Time.tickCounter() != runway1.oldTickCounter)
        {
            runway1.oldTickCounter = com.maddox.rts.Time.tickCounter();
            runway1.curPlaneShift = 0;
        }
        for(int i = 0; i < aaircraft.length; i++)
        {
            if(!com.maddox.il2.engine.Actor.isValid(aaircraft[i]))
                continue;
            float f = aaircraft[i].collisionR() * 2.0F + 20F;
            for(int j = runway1.curPlaneShift; j > 0; j--)
            {
                runway1.planeShift[j] = runway1.planeShift[j - 1] + f;
                runway1.planes[j] = runway1.planes[j - 1];
            }

            runway1.planeShift[0] = 0.0F;
            runway1.planes[0] = aaircraft[i];
            runway1.curPlaneShift++;
            if(runway1.curPlaneShift > 31)
                throw new RuntimeException("Too many planes on airdrome");
            for(int k = 0; k < runway1.curPlaneShift; k++)
            {
                if(!com.maddox.il2.engine.Actor.isValid(runway1.planes[k]))
                    continue;
                tmpLoc.set((double)runway1.planeShift[k] - d, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                tmpLoc.add(runway1.loc);
                com.maddox.JGP.Point3d point3d1 = tmpLoc.getPoint();
                com.maddox.il2.engine.Orient orient = tmpLoc.getOrient();
                point3d1.z = com.maddox.il2.ai.World.land().HQ(point3d1.x, point3d1.y) + (double)runway1.planes[k].FM.Gears.H;
                com.maddox.il2.engine.Engine.land().N(point3d1.x, point3d1.y, v1);
                orient.orient(v1);
                orient.increment(0.0F, runway1.planes[k].FM.Gears.Pitch, 0.0F);
                runway1.planes[k].setOnGround(point3d1, orient, zeroSpeed);
                if(runway1.planes[k].FM instanceof com.maddox.il2.ai.air.Maneuver)
                {
                    ((com.maddox.il2.ai.air.Maneuver)runway1.planes[k].FM).direction = runway1.planes[k].pos.getAbsOrient().getAzimut();
                    ((com.maddox.il2.ai.air.Maneuver)runway1.planes[k].FM).rwLoc = runway1.loc;
                }
                runway1.planes[k].FM.AP.way.takeoffAirport = this;
            }

        }

        if(com.maddox.il2.engine.Actor.isValid(aaircraft[0]) && (aaircraft[0].FM instanceof com.maddox.il2.ai.air.Maneuver))
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)aaircraft[0].FM;
            if(maneuver.Group != null && maneuver.Group.w != null)
                maneuver.Group.w.takeoffAirport = this;
        }
    }

    public double ShiftFromLine(com.maddox.il2.fm.FlightModel flightmodel)
    {
        tmpLoc.set(flightmodel.Loc);
        if(flightmodel instanceof com.maddox.il2.ai.air.Maneuver)
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)flightmodel;
            if(maneuver.rwLoc != null)
            {
                tmpLoc.sub(maneuver.rwLoc);
                return tmpLoc.getY();
            }
        }
        return 0.0D;
    }

    public boolean nearestRunway(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Loc loc)
    {
        com.maddox.il2.ai.Runway runway1 = nearestRunway(point3d);
        if(runway1 != null)
        {
            loc.set(runway1.loc);
            return true;
        } else
        {
            return false;
        }
    }

    private com.maddox.il2.ai.Runway nearestRunway(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.il2.ai.Runway runway1 = null;
        double d = 0.0D;
        np.set(point3d);
        int i = runway.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Runway runway2 = (com.maddox.il2.ai.Runway)runway.get(j);
            np.z = runway2.loc.getPoint().z;
            double d1 = runway2.loc.getPoint().distanceSquared(np);
            if(runway1 == null || d1 < d)
            {
                runway1 = runway2;
                d = d1;
            }
        }

        if(d > 225000000D)
            runway1 = null;
        return runway1;
    }

    private com.maddox.il2.ai.Runway oppositeRunway(com.maddox.il2.engine.Loc loc)
    {
        int i = runway.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Runway runway1 = (com.maddox.il2.ai.Runway)runway.get(j);
            pcur.set(runway1.loc.getPoint());
            loc.transformInv(pcur);
            if(java.lang.Math.abs(pcur.y) >= 15D || pcur.x >= -800D || pcur.x <= -2500D)
                continue;
            p1.set(1.0D, 0.0D, 0.0D);
            p2.set(1.0D, 0.0D, 0.0D);
            runway1.loc.getOrient().transform(p1);
            loc.getOrient().transform(p2);
            if(p1.dot(p2) < -0.90000000000000002D)
                return runway1;
        }

        return null;
    }

    private java.util.ArrayList runway;
    public static final int PT_RUNWAY = 1;
    public static final int PT_TAXI = 2;
    public static final int PT_STAY = 4;
    private static com.maddox.JGP.Point3d p3d = new Point3d();
    private static float x[] = {
        -500F, 0.0F, 220F, 2000F, 4000F, 5000F, 4000F, 0.0F, 0.0F
    };
    private static float y[] = {
        0.0F, 0.0F, 0.0F, 0.0F, -500F, -2000F, -4000F, -4000F, -4000F
    };
    private static float z[] = {
        0.0F, 6F, 20F, 160F, 500F, 600F, 700F, 700F, 700F
    };
    private static float v[] = {
        0.0F, 180F, 220F, 240F, 270F, 280F, 300F, 300F, 300F
    };
    private static com.maddox.JGP.Point3d pWay = new Point3d();
    private static com.maddox.JGP.Point3d pd = new Point3d();
    private static com.maddox.JGP.Point3f pf = new Point3f();
    private static com.maddox.JGP.Vector3d v1 = new Vector3d();
    private static com.maddox.JGP.Vector3d zeroSpeed = new Vector3d();
    private static com.maddox.il2.engine.Loc tmpLoc = new Loc();
    private static com.maddox.JGP.Point3d pcur = new Point3d();
    private static com.maddox.JGP.Point3d np = new Point3d();
    private static com.maddox.JGP.Vector3d p1 = new Vector3d();
    private static com.maddox.JGP.Vector3d p2 = new Vector3d();

}
