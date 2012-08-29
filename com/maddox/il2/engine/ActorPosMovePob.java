// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
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

public class ActorPosMovePob extends com.maddox.il2.engine.ActorPos
{

    public void inValidate(boolean flag)
    {
        if(!absChanged)
            com.maddox.il2.engine.Engine.cur.posChanged.add(actor);
        absChanged = true;
    }

    public com.maddox.il2.engine.Actor actor()
    {
        return actor;
    }

    public com.maddox.il2.engine.Actor homeBase()
    {
        return null;
    }

    public com.maddox.il2.engine.Actor base()
    {
        return null;
    }

    public java.lang.Object baseHook()
    {
        return null;
    }

    public void getRel(com.maddox.il2.engine.Loc loc)
    {
        loc.set(Labs);
    }

    public void getRel(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        Labs.get(point3d, orient);
    }

    public void getRel(com.maddox.JGP.Point3d point3d)
    {
        Labs.get(point3d);
    }

    public void getRel(com.maddox.il2.engine.Orient orient)
    {
        Labs.get(orient);
    }

    public com.maddox.il2.engine.Loc getRel()
    {
        return Labs;
    }

    public com.maddox.JGP.Point3d getRelPoint()
    {
        return Labs.getPoint();
    }

    public com.maddox.il2.engine.Orient getRelOrient()
    {
        return Labs.getOrient();
    }

    public void setRel(com.maddox.JGP.Point3d point3d)
    {
        Labs.set(point3d);
        inValidate(false);
    }

    public void getAbs(com.maddox.il2.engine.Loc loc)
    {
        loc.set(Labs);
    }

    public void getAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        Labs.get(point3d, orient);
    }

    public void getAbs(com.maddox.JGP.Point3d point3d)
    {
        Labs.get(point3d);
    }

    public void getAbs(com.maddox.il2.engine.Orient orient)
    {
        Labs.get(orient);
    }

    public com.maddox.il2.engine.Loc getAbs()
    {
        return Labs;
    }

    public com.maddox.JGP.Point3d getAbsPoint()
    {
        return Labs.getPoint();
    }

    public com.maddox.il2.engine.Orient getAbsOrient()
    {
        return Labs.getOrient();
    }

    public void setAbs(com.maddox.JGP.Point3d point3d)
    {
        Labs.set(point3d);
        inValidate(false);
    }

    public boolean isChanged()
    {
        return absChanged;
    }

    public void getCurrent(com.maddox.il2.engine.Loc loc)
    {
        loc.set(curLabs);
    }

    public void getCurrent(com.maddox.JGP.Point3d point3d)
    {
        curLabs.get(point3d);
    }

    public com.maddox.il2.engine.Loc getCurrent()
    {
        return curLabs;
    }

    public com.maddox.JGP.Point3d getCurrentPoint()
    {
        return curLabs.getPoint();
    }

    public com.maddox.il2.engine.Orient getCurrentOrient()
    {
        return curLabs.getOrient();
    }

    public void getPrev(com.maddox.il2.engine.Loc loc)
    {
        loc.set(prevLabs);
    }

    public com.maddox.il2.engine.Loc getPrev()
    {
        return prevLabs;
    }

    public com.maddox.il2.engine.Loc getRender()
    {
        getRender(__l);
        return __l;
    }

    public void getRender(com.maddox.il2.engine.Loc loc)
    {
        loc.interpolate(curLabs, Labs, com.maddox.rts.Time.tickOffset());
    }

    public void getRender(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        getRender(__l);
        __l.get(point3d, orient);
    }

    public void getTime(long l, com.maddox.il2.engine.Loc loc)
    {
        if(com.maddox.il2.engine.InterpolateAdapter.isProcess())
            loc.interpolate(prevLabs, curLabs, ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        else
        if(l == com.maddox.rts.Time.tick())
            loc.set(curLabs);
        else
        if(l < com.maddox.rts.Time.tick())
            loc.interpolate(prevLabs, curLabs, ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        else
        if(l == com.maddox.rts.Time.tickNext())
            loc.set(Labs);
        else
            loc.interpolate(curLabs, Labs, (float)(l - com.maddox.rts.Time.tick()) / com.maddox.rts.Time.tickLenFms());
    }

    public void getTime(long l, com.maddox.JGP.Point3d point3d)
    {
        if(com.maddox.il2.engine.InterpolateAdapter.isProcess())
            point3d.interpolate(prevLabs.getPoint(), curLabs.getPoint(), ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        else
        if(l == com.maddox.rts.Time.tick())
            curLabs.get(point3d);
        else
        if(l < com.maddox.rts.Time.tick())
            point3d.interpolate(prevLabs.getPoint(), curLabs.getPoint(), ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        else
        if(l == com.maddox.rts.Time.tickNext())
            Labs.get(point3d);
        else
            point3d.interpolate(curLabs.getPoint(), Labs.getPoint(), (float)(l - com.maddox.rts.Time.tick()) / com.maddox.rts.Time.tickLenFms());
    }

    public void resetAsBase()
    {
        reset();
    }

    public void reset()
    {
        updateCurrent();
        prevLabs.set(curLabs);
        int i = com.maddox.il2.engine.Engine.cur.posChanged.indexOf(actor);
        if(i >= 0)
            com.maddox.il2.engine.Engine.cur.posChanged.remove(i);
    }

    protected void updateCurrent()
    {
        prevLabs.set(curLabs);
        if(absChanged)
        {
            getAbs(curLabs);
            int i = actor.flags;
            if((i & 3) == 1)
                com.maddox.il2.engine.Engine.cur.drawEnv.changedPos(actor, prevLabs.getPoint(), curLabs.getPoint());
            if((i & 0x30) == 16)
                com.maddox.il2.engine.Engine.cur.collideEnv.changedPos(actor, prevLabs.getPoint(), curLabs.getPoint());
            if((i & 0x200) == 512)
                com.maddox.il2.engine.Engine.cur.dreamEnv.changedListenerPos(actor, prevLabs.getPoint(), curLabs.getPoint());
            if((i & 0x100) == 256)
                com.maddox.il2.engine.Engine.cur.dreamEnv.changedFirePos(actor, prevLabs.getPoint(), curLabs.getPoint());
            absChanged = false;
        }
    }

    public double speed(com.maddox.JGP.Vector3d vector3d)
    {
        if(com.maddox.il2.engine.InterpolateAdapter.isProcess())
        {
            com.maddox.JGP.Point3d point3d = prevLabs.getPoint();
            com.maddox.JGP.Point3d point3d2 = curLabs.getPoint();
            double d = point3d2.x - point3d.x;
            double d2 = point3d2.y - point3d.y;
            double d4 = point3d2.z - point3d.z;
            double d6 = 1.0F / com.maddox.rts.Time.tickLenFs();
            if(vector3d != null)
                vector3d.set(d * d6, d2 * d6, d4 * d6);
            return java.lang.Math.sqrt(d * d + d2 * d2 + d4 * d4) * d6;
        }
        com.maddox.JGP.Point3d point3d1 = Labs.getPoint();
        com.maddox.JGP.Point3d point3d3 = curLabs.getPoint();
        double d1 = point3d1.x - point3d3.x;
        double d3 = point3d1.y - point3d3.y;
        double d5 = point3d1.z - point3d3.z;
        double d7 = 1.0F / com.maddox.rts.Time.tickLenFs();
        if(vector3d != null)
            vector3d.set(d1 * d7, d3 * d7, d5 * d7);
        return java.lang.Math.sqrt(d1 * d1 + d3 * d3 + d5 * d5) * d7;
    }

    protected void drawingChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.drawEnv.add(actor);
        else
            com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor);
    }

    protected void collideChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.collideEnv.add(actor);
        else
            com.maddox.il2.engine.Engine.cur.collideEnv.remove(actor);
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

    public ActorPosMovePob(com.maddox.il2.engine.Actor actor1)
    {
        actor = null;
        Labs = new Loc();
        absChanged = false;
        curLabs = new Loc();
        prevLabs = new Loc();
        actor = actor1;
        initEnvs(actor);
    }

    public ActorPosMovePob(com.maddox.il2.engine.Actor actor1, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
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

    public ActorPosMovePob(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Loc loc)
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

    private com.maddox.il2.engine.Actor actor;
    private com.maddox.il2.engine.Loc Labs;
    private boolean absChanged;
    private com.maddox.il2.engine.Loc curLabs;
    private com.maddox.il2.engine.Loc prevLabs;
    private static com.maddox.il2.engine.Loc __l = new Loc();

}
