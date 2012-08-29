// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BornPlace.java

package com.maddox.il2.net;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import java.util.ArrayList;

public class BornPlace
{
    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return actor instanceof com.maddox.il2.objects.ships.BigshipGeneric;
        }

        ClipFilter()
        {
        }
    }


    public com.maddox.il2.engine.Loc getAircraftPlace(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        com.maddox.il2.engine.Loc loc = null;
        if(i < 0 || i > com.maddox.il2.ai.World.cur().airdrome.stay.length)
        {
            double d = com.maddox.il2.ai.World.land().HQ(place.x, place.y);
            loc = new Loc(place.x, place.y, d, 0.0F, 0.0F, 0.0F);
        } else
        if(i >= com.maddox.il2.ai.World.cur().airdrome.stayHold.length)
        {
            com.maddox.il2.ai.air.Point_Stay point_stay = com.maddox.il2.ai.World.cur().airdrome.stay[i][0];
            loc = new Loc(point_stay.x, point_stay.y, 0.0D, 0.0F, 0.0F, 0.0F);
            if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing)
                return loc;
            com.maddox.il2.ai.AirportCarrier airportcarrier = (com.maddox.il2.ai.AirportCarrier)com.maddox.il2.ai.Airport.nearest(loc.getPoint(), -1, 4);
            if(airportcarrier != null)
            {
                corn.set(loc.getPoint());
                corn1.set(loc.getPoint());
                corn.z = com.maddox.il2.engine.Engine.cur.land.HQ(loc.getPoint().x, loc.getPoint().y);
                corn1.z = corn.z + 40D;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(corn, corn1, false, clipFilter, pship);
                if(airportcarrier.ship() != actor)
                    airportcarrier = null;
            }
            if(airportcarrier != null)
                airportcarrier.getTakeoff(aircraft, loc);
            else
                loc = new Loc(point_stay.x, point_stay.y, 1000D, 0.0F, 0.0F, 0.0F);
        } else
        {
            com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
            com.maddox.il2.ai.air.Point_Stay point_stay1 = apoint_stay[i][apoint_stay[i].length - 1];
            com.maddox.il2.ai.World.land();
            double d1 = com.maddox.il2.engine.Landscape.HQ(point_stay1.x, point_stay1.y);
            loc = new Loc(point_stay1.x, point_stay1.y, d1, 0.0F, 0.0F, 0.0F);
            int j = apoint_stay[i].length - 2;
            if(j >= 0)
            {
                com.maddox.JGP.Point3d point3d = new Point3d(point_stay1.x, point_stay1.y, 0.0D);
                com.maddox.JGP.Point3d point3d1 = new Point3d(apoint_stay[i][j].x, apoint_stay[i][j].y, 0.0D);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.sub(point3d1, point3d);
                vector3d.normalize();
                com.maddox.il2.engine.Orient orient = new Orient();
                orient.setAT0(vector3d);
                if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing)
                {
                    loc.set(orient);
                    return loc;
                }
                loc.getPoint().z += aircraft.FM.Gears.H;
                com.maddox.il2.engine.Engine.land().N(loc.getPoint().x, loc.getPoint().y, v1);
                orient.orient(v1);
                orient.increment(0.0F, aircraft.FM.Gears.Pitch, 0.0F);
                loc.set(orient);
            }
        }
        return loc;
    }

    public BornPlace(double d, double d1, int i, float f)
    {
        place = new Point2d();
        bParachute = true;
        place.set(d, d1);
        army = i;
        r = f;
    }

    public com.maddox.JGP.Point2d place;
    public float r;
    public int army;
    public boolean bParachute;
    public java.util.ArrayList airNames;
    private static com.maddox.JGP.Vector3d v1 = new Vector3d();
    private static com.maddox.JGP.Point3d corn = new Point3d();
    private static com.maddox.JGP.Point3d corn1 = new Point3d();
    private static com.maddox.JGP.Point3d pship = new Point3d();
    static com.maddox.il2.net.ClipFilter clipFilter = new ClipFilter();
    public int tmpForBrief;

}
