// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AirportStatic.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFastJet;
import com.maddox.il2.objects.sounds.SndAircraft;
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

        public Runway(com.maddox.il2.engine.Loc loc)
        {
            this.loc = new Loc();
            planeShift = new float[32];
            planes = new com.maddox.il2.objects.air.Aircraft[32];
            curPlaneShift = 0;
            oldTickCounter = 0;
            this.loc.set(loc.getX(), loc.getY(), com.maddox.il2.ai.World.land().HQ(loc.getX(), loc.getY()), loc.getAzimut(), 0.0F, 0.0F);
        }
    }


    public AirportStatic()
    {
        runway = new ArrayList();
    }

    public static void make(java.util.ArrayList arraylist, com.maddox.JGP.Point2f point2fs[][], com.maddox.JGP.Point2f point2fs_0_[][], com.maddox.JGP.Point2f point2fs_1_[][])
    {
        if(arraylist != null)
        {
            java.util.ArrayList arraylist_2_ = new ArrayList();
            double d = 4000000D;
            if(arraylist.size() == 4)
                d = 2890000D;
            while(arraylist.size() > 0) 
            {
                com.maddox.il2.engine.Loc loc = (com.maddox.il2.engine.Loc)arraylist.remove(0);
                boolean bool = false;
                com.maddox.il2.ai.AirportStatic airportstatic = null;
                for(int i = 0; i < arraylist_2_.size(); i++)
                {
                    airportstatic = (com.maddox.il2.ai.AirportStatic)arraylist_2_.get(i);
                    if(airportstatic.oppositeRunway(loc) == null)
                        continue;
                    bool = true;
                    break;
                }

                if(bool)
                {
                    airportstatic.runway.add(((java.lang.Object) (new Runway(loc))));
                    int i = airportstatic.runway.size();
                    ((com.maddox.JGP.Tuple3d) (p3d)).set(0.0D, 0.0D, 0.0D);
                    for(int i_3_ = 0; i_3_ < i; i_3_++)
                    {
                        loc = ((com.maddox.il2.ai.Runway)airportstatic.runway.get(i_3_)).loc;
                        p3d.x += ((com.maddox.JGP.Tuple3d) (loc.getPoint())).x;
                        p3d.y += ((com.maddox.JGP.Tuple3d) (loc.getPoint())).y;
                        p3d.z += ((com.maddox.JGP.Tuple3d) (loc.getPoint())).z;
                    }

                    p3d.x /= i;
                    p3d.y /= i;
                    p3d.z /= i;
                    ((com.maddox.il2.engine.Actor) (airportstatic)).pos.setAbs(p3d);
                } else
                {
                    if(com.maddox.il2.engine.Engine.cur.land.isWater(((com.maddox.JGP.Tuple3d) (loc.getPoint())).x, ((com.maddox.JGP.Tuple3d) (loc.getPoint())).y))
                        airportstatic = ((com.maddox.il2.ai.AirportStatic) (new AirportMaritime()));
                    else
                        airportstatic = ((com.maddox.il2.ai.AirportStatic) (new AirportGround()));
                    airportstatic.pos = ((com.maddox.il2.engine.ActorPos) (new ActorPosStatic(((com.maddox.il2.engine.Actor) (airportstatic)), loc)));
                    airportstatic.runway.add(((java.lang.Object) (new Runway(loc))));
                    arraylist_2_.add(((java.lang.Object) (airportstatic)));
                }
            }
        }
    }

    public boolean landWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).AP.way.curr().getP(pWay);
        com.maddox.il2.ai.Runway runway = nearestRunway(pWay);
        if(runway == null)
            return false;
        com.maddox.il2.ai.Way way = new Way();
        float f = (float)com.maddox.il2.engine.Engine.land().HQ_Air(runway.loc.getX(), runway.loc.getY());
        float f_4_ = ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).M.massEmpty / 3000F;
        if(f_4_ > 1.0F)
            f_4_ = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain) (flightmodel)).EI.engines[0].getType() > 1)
            f_4_ = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain) (flightmodel)).EI.engines[0].getType() == 3)
            f_4_ = 1.5F;
        float f_5_ = f_4_;
        if(f_5_ > 1.0F)
            f_5_ = 1.0F;
        if(((com.maddox.il2.engine.Interpolate) (flightmodel)).actor instanceof com.maddox.il2.objects.air.TypeFastJet)
            f_4_ = 3F;
        for(int i = x.length - 1; i >= 0; i--)
        {
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint();
            ((com.maddox.JGP.Tuple3d) (pd)).set(x[i] * f_4_, y[i] * f_4_, z[i] * f_5_);
            waypoint.set(java.lang.Math.min(v[i] * 0.278F, ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).Vmax * 0.7F));
            waypoint.Action = 2;
            runway.loc.transform(pd);
            float f_6_ = (float)com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (pd)).x, ((com.maddox.JGP.Tuple3d) (pd)).y);
            pd.z -= f_6_ - f;
            ((com.maddox.JGP.Tuple3f) (pf)).set(((com.maddox.JGP.Tuple3d) (pd)));
            waypoint.set(pf);
            way.add(waypoint);
        }

        way.setLanding(true);
        ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).AP.way = way;
        return true;
    }

    public void setTakeoff(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircrafts[])
    {
        com.maddox.il2.ai.Runway runway = nearestRunway(point3d);
        if(runway != null)
        {
            com.maddox.il2.ai.Runway runway_7_ = oppositeRunway(runway.loc);
            double d = 1000D;
            if(runway_7_ != null)
                d = runway.loc.getPoint().distance(runway_7_.loc.getPoint());
            if(com.maddox.rts.Time.tickCounter() != runway.oldTickCounter)
            {
                runway.oldTickCounter = com.maddox.rts.Time.tickCounter();
                runway.curPlaneShift = 0;
            }
            for(int i = 0; i < aircrafts.length; i++)
                if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (aircrafts[i]))))
                {
                    float f = ((com.maddox.il2.engine.ActorHMesh) (aircrafts[i])).collisionR() * 2.0F + 20F;
                    for(int i_8_ = runway.curPlaneShift; i_8_ > 0; i_8_--)
                    {
                        runway.planeShift[i_8_] = runway.planeShift[i_8_ - 1] + f;
                        runway.planes[i_8_] = runway.planes[i_8_ - 1];
                    }

                    runway.planeShift[0] = 0.0F;
                    runway.planes[0] = aircrafts[i];
                    runway.curPlaneShift++;
                    if(runway.curPlaneShift > 31)
                        throw new RuntimeException("Too many planes on airdrome");
                    for(int i_9_ = 0; i_9_ < runway.curPlaneShift; i_9_++)
                        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (runway.planes[i_9_]))))
                        {
                            tmpLoc.set((double)runway.planeShift[i_9_] - d, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                            tmpLoc.add(runway.loc);
                            com.maddox.JGP.Point3d point3d_10_ = tmpLoc.getPoint();
                            com.maddox.il2.engine.Orient orient = tmpLoc.getOrient();
                            point3d_10_.z = com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (point3d_10_)).x, ((com.maddox.JGP.Tuple3d) (point3d_10_)).y) + (double)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (runway.planes[i_9_])).FM)).Gears.H;
                            com.maddox.il2.engine.Engine.land().N(((com.maddox.JGP.Tuple3d) (point3d_10_)).x, ((com.maddox.JGP.Tuple3d) (point3d_10_)).y, v1);
                            orient.orient(v1);
                            orient.increment(0.0F, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (runway.planes[i_9_])).FM)).Gears.Pitch, 0.0F);
                            runway.planes[i_9_].setOnGround(point3d_10_, orient, zeroSpeed);
                            if(((com.maddox.il2.objects.sounds.SndAircraft) (runway.planes[i_9_])).FM instanceof com.maddox.il2.ai.air.Maneuver)
                            {
                                ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft) (runway.planes[i_9_])).FM).direction = ((com.maddox.il2.engine.Actor) (runway.planes[i_9_])).pos.getAbsOrient().getAzimut();
                                ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft) (runway.planes[i_9_])).FM).rwLoc = runway.loc;
                            }
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (runway.planes[i_9_])).FM)).AP.way.takeoffAirport = ((com.maddox.il2.ai.Airport) (this));
                        }

                }

            if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (aircrafts[0]))) && (((com.maddox.il2.objects.sounds.SndAircraft) (aircrafts[0])).FM instanceof com.maddox.il2.ai.air.Maneuver))
            {
                com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft) (aircrafts[0])).FM;
                if(maneuver.Group != null && maneuver.Group.w != null)
                    maneuver.Group.w.takeoffAirport = ((com.maddox.il2.ai.Airport) (this));
            }
        }
    }

    public double ShiftFromLine(com.maddox.il2.fm.FlightModel flightmodel)
    {
        tmpLoc.set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (flightmodel)).Loc)));
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
        com.maddox.il2.ai.Runway runway = nearestRunway(point3d);
        if(runway != null)
        {
            loc.set(runway.loc);
            return true;
        } else
        {
            return false;
        }
    }

    private com.maddox.il2.ai.Runway nearestRunway(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.il2.ai.Runway runway = null;
        double d = 0.0D;
        ((com.maddox.JGP.Tuple3d) (np)).set(((com.maddox.JGP.Tuple3d) (point3d)));
        int i = this.runway.size();
        for(int i_11_ = 0; i_11_ < i; i_11_++)
        {
            com.maddox.il2.ai.Runway runway_12_ = (com.maddox.il2.ai.Runway)this.runway.get(i_11_);
            np.z = ((com.maddox.JGP.Tuple3d) (runway_12_.loc.getPoint())).z;
            double d_13_ = runway_12_.loc.getPoint().distanceSquared(np);
            if(runway == null || d_13_ < d)
            {
                runway = runway_12_;
                d = d_13_;
            }
        }

        if(d > 225000000D)
            runway = null;
        return runway;
    }

    private com.maddox.il2.ai.Runway oppositeRunway(com.maddox.il2.engine.Loc loc)
    {
        int i = this.runway.size();
        for(int i_14_ = 0; i_14_ < i; i_14_++)
        {
            com.maddox.il2.ai.Runway runway = (com.maddox.il2.ai.Runway)this.runway.get(i_14_);
            ((com.maddox.JGP.Tuple3d) (pcur)).set(((com.maddox.JGP.Tuple3d) (runway.loc.getPoint())));
            loc.transformInv(pcur);
            if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (pcur)).y) < 15D && ((com.maddox.JGP.Tuple3d) (pcur)).x < -800D && ((com.maddox.JGP.Tuple3d) (pcur)).x > -2500D)
            {
                ((com.maddox.JGP.Tuple3d) (p1)).set(1.0D, 0.0D, 0.0D);
                ((com.maddox.JGP.Tuple3d) (p2)).set(1.0D, 0.0D, 0.0D);
                runway.loc.getOrient().transform(((com.maddox.JGP.Tuple3d) (p1)));
                loc.getOrient().transform(((com.maddox.JGP.Tuple3d) (p2)));
                if(p1.dot(((com.maddox.JGP.Tuple3d) (p2))) < -0.90000000000000002D)
                    return runway;
            }
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
