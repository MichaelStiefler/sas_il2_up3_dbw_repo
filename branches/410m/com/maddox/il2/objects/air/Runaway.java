// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Runaway.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Locator;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.rts.Message;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft

public class Runaway extends com.maddox.il2.engine.ActorMesh
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.engine.Actor actor = actorspawnarg.set(new Runaway());
            if(actor != null)
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                actor.pos.setAbs(new Point3d(point3d.x, point3d.y, com.maddox.il2.ai.World.land().HQ(point3d.x, point3d.y)));
            }
            return actor;
        }

        public SPAWN()
        {
        }
    }


    public com.maddox.il2.objects.air.Runaway next()
    {
        return next;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Runaway()
    {
        super("3do/Airfield/runaway/mono.sim");
        PlaneShift = new float[32];
        Planes = new com.maddox.il2.objects.air.Aircraft[32];
        curPlaneShift = 0;
        oldTickCounter = 0;
        zeroSpeed = new Vector3d();
        tmpLoc = new Loc();
        collide(false);
        drawing(bDrawing);
        com.maddox.il2.objects.air.Runaway runaway = com.maddox.il2.ai.World.cur().runawayList;
        if(runaway == null)
        {
            com.maddox.il2.ai.World.cur().runawayList = this;
            return;
        }
        for(; runaway.next != null; runaway = runaway.next);
        runaway.next = this;
    }

    public static com.maddox.il2.objects.air.Runaway nearest(com.maddox.JGP.Point3d point3d, int i)
    {
        double d = 0.0D;
        double d2 = 1.7976931348623157E+308D;
        com.maddox.il2.objects.air.Runaway runaway1 = com.maddox.il2.ai.World.cur().runawayList;
        if(runaway1 == null)
            return null;
        runaway1.pos.getAbs(pcur);
        if(i == 0 || !com.maddox.il2.objects.air.Runaway.isMaritime(runaway1) && i == 1 || com.maddox.il2.objects.air.Runaway.isMaritime(runaway1) && i == 2)
            d2 = point3d.distanceSquared(pcur);
        com.maddox.il2.objects.air.Runaway runaway = runaway1;
        for(runaway1 = runaway1.next; runaway1 != null; runaway1 = runaway1.next)
        {
            runaway1.pos.getAbs(pcur);
            double d1 = point3d.distanceSquared(pcur);
            if(d1 < d2 && (i == 0 || !com.maddox.il2.objects.air.Runaway.isMaritime(runaway1) && i == 1 || com.maddox.il2.objects.air.Runaway.isMaritime(runaway1) && i == 2))
            {
                d2 = d1;
                runaway = runaway1;
            }
        }

        if(d2 > 225000000D)
            return null;
        if(com.maddox.il2.objects.air.Runaway.isMaritime(runaway) && i == 1 || !com.maddox.il2.objects.air.Runaway.isMaritime(runaway) && i == 2)
            return null;
        else
            return runaway;
    }

    public static com.maddox.il2.objects.air.Runaway opposite(com.maddox.il2.engine.Loc loc)
    {
        com.maddox.il2.objects.air.Runaway runaway = com.maddox.il2.ai.World.cur().runawayList;
        if(runaway == null)
            return null;
        runaway.pos.getAbs(pcur);
        loc.transformInv(pcur);
        if(java.lang.Math.abs(pcur.y) < 15D && pcur.x < -800D && pcur.x > -2500D)
            return runaway;
        for(runaway = runaway.next; runaway != null; runaway = runaway.next)
        {
            runaway.pos.getAbs(pcur);
            loc.transformInv(pcur);
            if(java.lang.Math.abs(pcur.y) < 15D && pcur.x < -800D && pcur.x > -2500D)
                return runaway;
        }

        return null;
    }

    public static com.maddox.il2.objects.air.Runaway nearest(com.maddox.JGP.Point3f point3f, int i)
    {
        pd.set(point3f);
        return com.maddox.il2.objects.air.Runaway.nearest(pd, i);
    }

    public static boolean isMaritime(com.maddox.il2.objects.air.Runaway runaway)
    {
        return com.maddox.il2.engine.Engine.cur.land.isWater(runaway.pos.getAbsPoint().x, runaway.pos.getAbsPoint().y);
    }

    public void setAircrafts(com.maddox.il2.objects.air.Aircraft aaircraft[])
    {
        com.maddox.il2.objects.air.Runaway runaway = com.maddox.il2.objects.air.Runaway.opposite(pos.getAbs());
        float f = 1000F;
        if(runaway != null)
            f = (float)pos.getAbs().getPoint().distance(runaway.pos.getAbs().getPoint());
        if(com.maddox.rts.Time.tickCounter() != oldTickCounter)
        {
            oldTickCounter = com.maddox.rts.Time.tickCounter();
            curPlaneShift = 0;
        }
        for(int i = 0; i < aaircraft.length; i++)
        {
            if(!com.maddox.il2.engine.Actor.isValid(aaircraft[i]))
                continue;
            float f1 = aaircraft[i].collisionR() * 2.0F + 20F;
            for(int j = curPlaneShift; j > 0; j--)
            {
                PlaneShift[j] = PlaneShift[j - 1] + f1;
                Planes[j] = Planes[j - 1];
            }

            PlaneShift[0] = 0.0F;
            Planes[0] = aaircraft[i];
            curPlaneShift++;
            if(curPlaneShift > 31)
                throw new RuntimeException("Too many planes on airdrome");
            for(int k = 0; k < curPlaneShift; k++)
            {
                if(!com.maddox.il2.engine.Actor.isValid(Planes[k]))
                    continue;
                tmpLoc.set(PlaneShift[k] - f, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                tmpLoc.add(pos.getAbs());
                Planes[k].pos.setAbs(tmpLoc);
                Planes[k].setSpeed(zeroSpeed);
                Planes[k].pos.reset();
                if(Planes[k].FM instanceof com.maddox.il2.ai.air.Maneuver)
                    ((com.maddox.il2.ai.air.Maneuver)Planes[k].FM).direction = Planes[k].pos.getAbsOrient().getAzimut();
            }

        }

    }

    public com.maddox.il2.ai.Way landWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        com.maddox.il2.ai.Way way = new Way();
        RW.set(pos.getAbs());
        float f = (float)com.maddox.il2.engine.Engine.land().HQ_Air(RW.getX(), RW.getY());
        float f1 = flightmodel.M.massEmpty / 3000F;
        if(f1 > 1.0F)
            f1 = 1.0F;
        for(int i = x.length - 1; i >= 0; i--)
        {
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint();
            pd.set(x[i] * f1, y[i] * f1, z[i] * f1);
            waypoint.set(java.lang.Math.min(v[i] * 0.278F, flightmodel.Vmax * 0.6F));
            waypoint.Action = 2;
            RW.transform(pd);
            float f2 = (float)com.maddox.il2.engine.Engine.land().HQ_Air(pd.x, pd.y);
            pd.z -= f2 - f;
            pf.set(pd);
            waypoint.set(pf);
            way.add(waypoint);
        }

        way.setLanding(true);
        return way;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean bDrawing = false;
    public static final int RW_SELECTION_ANY = 0;
    public static final int RW_SELECTION_GROUND = 1;
    public static final int RW_SELECTION_MARITIME = 2;
    private com.maddox.il2.objects.air.Runaway next;
    private float PlaneShift[];
    private com.maddox.il2.objects.air.Aircraft Planes[];
    private int curPlaneShift;
    private int oldTickCounter;
    private static float x[] = {
        -500F, 0.0F, 210F, 2000F, 4000F, 5000F, 4000F, 0.0F, 0.0F
    };
    private static float y[] = {
        0.0F, 0.0F, 0.0F, 0.0F, -500F, -2000F, -4000F, -4000F, -4000F
    };
    private static float z[] = {
        0.0F, 0.0F, 10F, 130F, 300F, 500F, 500F, 500F, 500F
    };
    private static float v[] = {
        0.0F, 170F, 180F, 225F, 250F, 250F, 250F, 250F, 250F
    };
    private static com.maddox.il2.engine.Locator RW = new Locator();
    private static com.maddox.JGP.Point3d pcur = new Point3d();
    private static com.maddox.JGP.Point3d pd = new Point3d();
    private static com.maddox.JGP.Point3f pf = new Point3f();
    private com.maddox.JGP.Vector3d zeroSpeed;
    private com.maddox.il2.engine.Loc tmpLoc;
    private int craftNo;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.Runaway.class;
        com.maddox.rts.Spawn.add(class1, new SPAWN());
        com.maddox.rts.Property.set(class1, "iconName", "icons/runaway.mat");
    }
}
