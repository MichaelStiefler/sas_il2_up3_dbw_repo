// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode 
// Source File Name:   ActorPosMovePob.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Time;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.engine:
//            ActorPos, Loc, Engine, InterpolateAdapter, 
//            Actor, DrawEnv, CollideEnv, DreamEnv, 
//            Orient

public class ActorPosMovePob extends ActorPos
{

    public void inValidate(boolean flag)
    {
        if(!absChanged)
            Engine.cur.posChanged.add(actor);
        absChanged = true;
    }

    public Actor actor()
    {
        return actor;
    }

    public Actor homeBase()
    {
        return null;
    }

    public Actor base()
    {
        return null;
    }

    public Object baseHook()
    {
        return null;
    }

    public void getRel(Loc loc)
    {
        loc.set(Labs);
    }

    public void getRel(Point3d point3d, Orient orient)
    {
        Labs.get(point3d, orient);
    }

    public void getRel(Point3d point3d)
    {
        Labs.get(point3d);
    }

    public void getRel(Orient orient)
    {
        Labs.get(orient);
    }

    public Loc getRel()
    {
        return Labs;
    }

    public Point3d getRelPoint()
    {
        return Labs.getPoint();
    }

    public Orient getRelOrient()
    {
        return Labs.getOrient();
    }

    public void setRel(Point3d point3d)
    {
        Labs.set(point3d);
        inValidate(false);
    }

    public void getAbs(Loc loc)
    {
        loc.set(Labs);
    }

    public void getAbs(Point3d point3d, Orient orient)
    {
        Labs.get(point3d, orient);
    }

    public void getAbs(Point3d point3d)
    {
        Labs.get(point3d);
    }

    public void getAbs(Orient orient)
    {
        Labs.get(orient);
    }

    public Loc getAbs()
    {
        return Labs;
    }

    public Point3d getAbsPoint()
    {
        return Labs.getPoint();
    }

    public Orient getAbsOrient()
    {
        return Labs.getOrient();
    }

    public void setAbs(Point3d point3d)
    {
        Labs.set(point3d);
        inValidate(false);
    }

    public boolean isChanged()
    {
        return absChanged;
    }

    public void getCurrent(Loc loc)
    {
        loc.set(curLabs);
    }

    public void getCurrent(Point3d point3d)
    {
        curLabs.get(point3d);
    }

    public Loc getCurrent()
    {
        return curLabs;
    }

    public Point3d getCurrentPoint()
    {
        return curLabs.getPoint();
    }

    public Orient getCurrentOrient()
    {
        return curLabs.getOrient();
    }

    public void getPrev(Loc loc)
    {
        loc.set(prevLabs);
    }

    public Loc getPrev()
    {
        return prevLabs;
    }

    public Loc getRender()
    {
        getRender(__l);
        return __l;
    }

    public void getRender(Loc loc)
    {
        loc.interpolate(curLabs, Labs, Time.tickOffset());
    }

    public void getRender(Point3d point3d, Orient orient)
    {
        getRender(__l);
        __l.get(point3d, orient);
    }

    public void getTime(long l, Loc loc)
    {
        if(InterpolateAdapter.isProcess())
            loc.interpolate(prevLabs, curLabs, ((float)(l - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
        else
        if(l == Time.tick())
            loc.set(curLabs);
        else
        if(l < Time.tick())
            loc.interpolate(prevLabs, curLabs, ((float)(l - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
        else
        if(l == Time.tickNext())
            loc.set(Labs);
        else
            loc.interpolate(curLabs, Labs, (float)(l - Time.tick()) / Time.tickLenFms());
    }

    public void getTime(long l, Point3d point3d)
    {
        if(InterpolateAdapter.isProcess())
            point3d.interpolate(prevLabs.getPoint(), curLabs.getPoint(), ((float)(l - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
        else
        if(l == Time.tick())
            curLabs.get(point3d);
        else
        if(l < Time.tick())
            point3d.interpolate(prevLabs.getPoint(), curLabs.getPoint(), ((float)(l - Time.tick()) + Time.tickLenFms()) / Time.tickLenFms());
        else
        if(l == Time.tickNext())
            Labs.get(point3d);
        else
            point3d.interpolate(curLabs.getPoint(), Labs.getPoint(), (float)(l - Time.tick()) / Time.tickLenFms());
    }

    public void resetAsBase()
    {
        reset();
    }

    public void reset()
    {
        updateCurrent();
        prevLabs.set(curLabs);
        int i = Engine.cur.posChanged.indexOf(actor);
        if(i >= 0)
            Engine.cur.posChanged.remove(i);
    }

    protected void updateCurrent()
    {
        prevLabs.set(curLabs);
        if(absChanged)
        {
            getAbs(curLabs);
            int i = actor.flags;
            if((i & 3) == 1)
                Engine.cur.drawEnv.changedPos(actor, prevLabs.getPoint(), curLabs.getPoint());
            if((i & 0x30) == 16)
                Engine.cur.collideEnv.changedPos(actor, prevLabs.getPoint(), curLabs.getPoint());
            if((i & 0x200) == 512)
                Engine.cur.dreamEnv.changedListenerPos(actor, prevLabs.getPoint(), curLabs.getPoint());
            if((i & 0x100) == 256)
                Engine.cur.dreamEnv.changedFirePos(actor, prevLabs.getPoint(), curLabs.getPoint());
            absChanged = false;
        }
    }

    public double speed(Vector3d vector3d)
    {
        if(InterpolateAdapter.isProcess())
        {
            Point3d point3d = prevLabs.getPoint();
            Point3d point3d2 = curLabs.getPoint();
            double d = point3d2.x - point3d.x;
            double d2 = point3d2.y - point3d.y;
            double d4 = point3d2.z - point3d.z;
            double d6 = 1.0F / Time.tickLenFs();
            if(vector3d != null)
                vector3d.set(d * d6, d2 * d6, d4 * d6);
            return Math.sqrt(d * d + d2 * d2 + d4 * d4) * d6;
        }
        Point3d point3d1 = Labs.getPoint();
        Point3d point3d3 = curLabs.getPoint();
        double d1 = point3d1.x - point3d3.x;
        double d3 = point3d1.y - point3d3.y;
        double d5 = point3d1.z - point3d3.z;
        double d7 = 1.0F / Time.tickLenFs();
        if(vector3d != null)
            vector3d.set(d1 * d7, d3 * d7, d5 * d7);
        return Math.sqrt(d1 * d1 + d3 * d3 + d5 * d5) * d7;
    }

    protected void drawingChange(boolean flag)
    {
        if(flag)
            Engine.cur.drawEnv.add(actor);
        else
            Engine.cur.drawEnv.remove(actor);
    }

    protected void collideChange(boolean flag)
    {
//      System.out.println("ActorPosMovePob collideChange " + actor.getClass().getName() + " flag=" + flag);
      if ((actor instanceof com.maddox.il2.objects.vehicles.artillery.RocketryRocket)
              || (actor instanceof com.maddox.il2.objects.weapons.MissileInterceptable)) {
        System.out.println("ActorPosMovePob collideChange " + actor.getClass().getName() + " flag=" + flag);
//        Exception testExc = new Exception();
//        testExc.printStackTrace();
      }
        if(flag)
            Engine.cur.collideEnv.add(actor);
        else
            Engine.cur.collideEnv.remove(actor);
    }

    public void destroy()
    {
        if(actor == null)
        {
            return;
        } else
        {
            clearEnvs(actor);
            actor = null;
            return;
        }
    }

    public ActorPosMovePob(Actor actor1)
    {
        actor = null;
        Labs = new Loc();
        absChanged = false;
        curLabs = new Loc();
        prevLabs = new Loc();
        actor = actor1;
        initEnvs(actor);
    }

    public ActorPosMovePob(Actor actor1, Point3d point3d, Orient orient)
    {
        actor = null;
        Labs = new Loc();
        absChanged = false;
        curLabs = new Loc();
        prevLabs = new Loc();
        actor = actor1;
        Labs.set(point3d, orient);
        curLabs.set(Labs);
        prevLabs.set(Labs);
        initEnvs(actor);
    }

    public ActorPosMovePob(Actor actor1, Loc loc)
    {
        actor = null;
        Labs = new Loc();
        absChanged = false;
        curLabs = new Loc();
        prevLabs = new Loc();
        actor = actor1;
        Labs.set(loc);
        curLabs.set(Labs);
        prevLabs.set(Labs);
        initEnvs(actor);
    }

    private Actor actor;
    private Loc Labs;
    private boolean absChanged;
    private Loc curLabs;
    private Loc prevLabs;
    private static Loc __l = new Loc();

}
